package com.clinbrain.mq.service;

import com.clinbrain.mq.common.message.EmailMessage;
import com.clinbrain.mq.common.message.SMSMessage;
import com.clinbrain.mq.common.rabbitmq.BindingsConfig;
import com.clinbrain.mq.common.rabbitmq.ExchangeConfig;
import com.clinbrain.mq.mapper.custom.UContactDetailsMapper;
import com.clinbrain.mq.mapper.custom.UMqMessageMapper;
import com.clinbrain.mq.model.custom.MessageModel;
import com.clinbrain.mq.model.custom.UContactDetails;
import com.clinbrain.mq.model.custom.UMqMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.ConnectionFactory;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MassageService {
    @Autowired
    private UMqMessageMapper uMqMessageMapper;
    @Autowired
    private UContactDetailsMapper uContactDetailsMapper;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @SneakyThrows
    public void sendEmail(EmailMessage emailMessage) {
        // 消息入库
        Date now = new Date();
        UMqMessage uMqMessage = new UMqMessage();;
        uMqMessage.setCreateTime(now);
        uMqMessage.setUpdateTime(now);
        uMqMessage.setContent(uMqMessage.getContent());
        uMqMessage.setStatus("准备发送");
        uMqMessage.setTemplateId(emailMessage.getTemplateId());
        uMqMessage.setMessageGenre("email");
        String msg = new ObjectMapper().writeValueAsString(emailMessage);
        uMqMessage.setOriginalData(msg);

        if(emailMessage.getAssignGroup() != null && emailMessage.getAssignGroup().length != 0){
            List<Integer> groups = new ArrayList<>();
            for(String str : emailMessage.getAssignGroup()){
                groups.add(Integer.parseInt(str));
            }
            List<UContactDetails> detailsByGroups = uContactDetailsMapper.selectListByGroups(groups,"EMAIL");
            uMqMessage.setAssignTo(detailsByGroups.stream().map(UContactDetails::getContactValue).collect(Collectors.joining(",")));
        }

        if(emailMessage.getAssignId() != null && emailMessage.getAssignId().length != 0){
            List<Integer> users = new ArrayList<>();
            for(String str : emailMessage.getAssignId()){
                users.add(Integer.parseInt(str));
            }
            List<UContactDetails> detailsByUsers = uContactDetailsMapper.selectListByUsers(users,"EMAIL");
            uMqMessage.setAssignTo(detailsByUsers.stream().map(UContactDetails::getContactValue).collect(Collectors.joining(",")));
        }

        if(emailMessage.getAssign() != null && emailMessage.getAssign().length != 0){
            uMqMessage.setAssignTo(String.join(",",emailMessage.getAssign()));
        }

        uMqMessageMapper.insertSelective(uMqMessage);
        // 发送消息
        try {
            rabbitTemplate.convertAndSend(ExchangeConfig.CLINBRAIN_AMQ_EMAIL_DIRECT,
                    BindingsConfig.INFORM_EMAIL_DEFAULT,
                    new ObjectMapper().writeValueAsString(
                            new MessageModel(Arrays.asList(uMqMessage.getId()))));
        } catch (AmqpException e) {
            log.error("消息发送失败:[{}]",e.getMessage());
            uMqMessage.setUpdateTime(new Date());
            uMqMessage.setLog("消息发送失败");
            uMqMessage.setStatus("发送失败");
            uMqMessageMapper.updateById(uMqMessage);
            e.printStackTrace();
        }
    }

    @SneakyThrows
    public void sendSms(SMSMessage smsMessage) {
        // 消息入库
        Date now = new Date();
        UMqMessage uMqMessage = new UMqMessage();;
        uMqMessage.setCreateTime(now);
        uMqMessage.setUpdateTime(now);
        uMqMessage.setContent(uMqMessage.getContent());
        uMqMessage.setStatus("准备发送");
        uMqMessage.setTemplateId(smsMessage.getTemplateId());
        uMqMessage.setMessageGenre("sms");
        String msg = new ObjectMapper().writeValueAsString(smsMessage);
        uMqMessage.setOriginalData(msg);

        if(smsMessage.getAssignGroup() != null && smsMessage.getAssignGroup().length != 0){
            List<Integer> groups = new ArrayList<>();
            for(String str : smsMessage.getAssignGroup()){
                groups.add(Integer.parseInt(str));
            }
            List<UContactDetails> detailsByGroups = uContactDetailsMapper.selectListByGroups(groups,"EMAIL");
            uMqMessage.setAssignTo(detailsByGroups.stream().map(UContactDetails::getContactValue).collect(Collectors.joining(",")));
        }

        if(smsMessage.getAssignId() != null && smsMessage.getAssignId().length != 0){
            List<Integer> users = new ArrayList<>();
            for(String str : smsMessage.getAssignId()){
                users.add(Integer.parseInt(str));
            }
            List<UContactDetails> detailsByUsers = uContactDetailsMapper.selectListByUsers(users,"EMAIL");
            uMqMessage.setAssignTo(detailsByUsers.stream().map(UContactDetails::getContactValue).collect(Collectors.joining(",")));
        }

        if(smsMessage.getAssign() != null && smsMessage.getAssign().length != 0){
            uMqMessage.setAssignTo(String.join(",",smsMessage.getAssign()));
        }
        uMqMessageMapper.insertSelective(uMqMessage);
        // 发送消息
        try {
            rabbitTemplate.convertAndSend(ExchangeConfig.CLINBRAIN_AMQ_SMS_DIRECT,
                    BindingsConfig.INFORM_SMS_DEFAULT,
                    new ObjectMapper().writeValueAsString(
                            new MessageModel(Arrays.asList(uMqMessage.getId()))));
        } catch (AmqpException e) {
            log.error("消息发送失败:[{}]",e.getMessage());
            uMqMessage.setUpdateTime(new Date());
            uMqMessage.setLog("消息发送失败");
            uMqMessage.setStatus("发送失败");
            uMqMessageMapper.updateById(uMqMessage);
            e.printStackTrace();
        }
    }
}
