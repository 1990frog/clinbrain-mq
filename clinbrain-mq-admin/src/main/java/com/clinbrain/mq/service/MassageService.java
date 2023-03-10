package com.clinbrain.mq.service;

import cn.hutool.core.collection.CollUtil;
import com.clinbrain.mq.common.exception.MessageException;
import com.clinbrain.mq.common.message.EmailMessage;
import com.clinbrain.mq.common.rabbitmq.BindingsConfig;
import com.clinbrain.mq.common.rabbitmq.ExchangeConfig;
import com.clinbrain.mq.mapper.custom.UContactDetailsMapper;
import com.clinbrain.mq.mapper.custom.UMqMessageMapper;
import com.clinbrain.mq.mapper.custom.UMsgTemplateDao;
import com.clinbrain.mq.model.custom.MessageModel;
import com.clinbrain.mq.model.custom.UContactDetails;
import com.clinbrain.mq.model.custom.UMqMessage;
import com.clinbrain.mq.model.custom.sms.UMsgTemplate;
import com.clinbrain.mq.util.FileUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MassageService {
    @Autowired
    private UMqMessageMapper uMqMessageMapper;
    @Autowired
    private UContactDetailsMapper uContactDetailsMapper;
    @Autowired
    private UMsgTemplateDao uMsgTemplateDao;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static final String GENRE_EMAIL= "email";

    public Object sendEmail(EmailMessage emailMessage, Optional<List<String>> attach) {
        // 消息入库
        Date now = new Date();
        UMqMessage uMqMessage = new UMqMessage();;
        uMqMessage.setTraceId(emailMessage.getTraceId());
        uMqMessage.setCreateTime(now);
        uMqMessage.setUpdateTime(now);
        uMqMessage.setMessageGenre(GENRE_EMAIL);
        uMqMessage.setTitle(emailMessage.getTitle());
        if(attach.isPresent() && CollUtil.isNotEmpty(attach.get())){
            uMqMessage.setAttachPaths(String.join("\\|\\|",attach.get()));
        }

        try {
            checkArguments(emailMessage);
            String msg = new ObjectMapper().writeValueAsString(emailMessage);
            uMqMessage.setOriginalData(msg);

            // 处理模板内容
            UMsgTemplate uMsgTemplate = uMsgTemplateDao.getTemplate(emailMessage.getTemplateId(),GENRE_EMAIL);
            if(uMsgTemplate == null){
                uMqMessage.setLog("找不到指定Email类型模板ID=["+emailMessage.getTemplateId()+"]");
                uMqMessage.setStatus("处理失败");
                uMqMessageMapper.insertSelective(uMqMessage);
                return "fail";
            }

            String content = uMsgTemplate.getTemplateContent();
            for (int i = 0; i < emailMessage.getTemplateParams().size(); i++) {
                content = content.replace("{"+i+"}", emailMessage.getTemplateParams().get(i));
            }
            uMqMessage.setContent(content);

            if(emailMessage.getAssignGroup() != null && emailMessage.getAssignGroup().length != 0){
                List<Integer> groups = new ArrayList<>();
                for(String str : emailMessage.getAssignGroup()){
                    groups.add(Integer.parseInt(str));
                }
                List<UContactDetails> detailsByGroups = uContactDetailsMapper.selectListByGroups(groups,GENRE_EMAIL);
                uMqMessage.setAssignTo(detailsByGroups.stream().map(UContactDetails::getContactValue).collect(Collectors.joining(",")));
            }

            if(emailMessage.getAssignId() != null && emailMessage.getAssignId().length != 0){
                List<Integer> users = new ArrayList<>();
                for(String str : emailMessage.getAssignId()){
                    users.add(Integer.parseInt(str));
                }
                List<UContactDetails> detailsByUsers = uContactDetailsMapper.selectListByUsers(users,GENRE_EMAIL);
                uMqMessage.setAssignTo(detailsByUsers.stream().map(UContactDetails::getContactValue).collect(Collectors.joining(",")));
            }

            if(emailMessage.getAssign() != null && emailMessage.getAssign().length != 0){
                uMqMessage.setAssignTo(String.join(",",emailMessage.getAssign()));
            }

            uMqMessage.setStatus("准备发送");
            uMqMessage.setTemplateId(emailMessage.getTemplateId());
            uMqMessageMapper.insertSelective(uMqMessage);
            // 发送消息
            try {
                rabbitTemplate.convertAndSend(ExchangeConfig.CLINBRAIN_AMQ_EMAIL_DIRECT,
                        BindingsConfig.INFORM_EMAIL_DEFAULT,
                        new ObjectMapper().writeValueAsString(
                                new MessageModel(Arrays.asList(uMqMessage.getId()))));
            } catch (AmqpException | JsonProcessingException e) {
                log.error("消息发送至MQ失败:[{}]",e.getMessage());
                uMqMessage.setUpdateTime(new Date());
                uMqMessage.setLog("消息发送至MQ失败");
                uMqMessage.setStatus("处理失败");
                uMqMessageMapper.updateById(uMqMessage);
                e.printStackTrace();
                return "fail";
            }
        }catch (MessageException | JsonProcessingException e){
            uMqMessage.setLog(e.getMessage());
            uMqMessage.setStatus("处理失败");
            uMqMessageMapper.insertSelective(uMqMessage);
            return "fail";
        }


        return "success";
    }

    private void checkArguments(EmailMessage emailMessage) {
        if(emailMessage.getTemplateId() == null){
            throw new MessageException("未配置模板ID");
        }
        if(CollUtil.isEmpty(emailMessage.getTemplateParams())){
            throw new MessageException("未配置模板内容");
        }
        if((emailMessage.getAssign() == null || emailMessage.getAssign().length == 0)
            &&(emailMessage.getAssignId() == null || emailMessage.getAssignId().length == 0)
            &&(emailMessage.getAssignGroup() == null || emailMessage.getAssignGroup().length == 0)){
            throw new MessageException("未指定消息接收者");
        }
    }
}
