package com.clinbrain.mq.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.clinbrain.mq.common.message.SMSMessage;
import com.clinbrain.mq.common.rabbitmq.BindingsConfig;
import com.clinbrain.mq.common.rabbitmq.ExchangeConfig;
import com.clinbrain.mq.mapper.custom.UContactDao;
import com.clinbrain.mq.mapper.custom.UGroupsDao;
import com.clinbrain.mq.mapper.custom.UMqMessageMapper;
import com.clinbrain.mq.mapper.custom.UMsgTemplateDao;
import com.clinbrain.mq.model.custom.MqMessageObject;
import com.clinbrain.mq.model.custom.UMqMessage;
import com.clinbrain.mq.model.custom.sms.ContactVo;
import com.clinbrain.mq.model.custom.sms.UMsgTemplate;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 组装短信内容发送到MQ
 * Created by Liaopan on 2021-12-06.
 */
@Service
@Slf4j
public abstract class BaseSmsService implements ISmsTemplateService{

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private UGroupsDao uGroupsDao;

    @Autowired
    private UMsgTemplateDao uMsgTemplateDao;

    @Autowired
    private UContactDao uContactDao;

    @Autowired
    private UMqMessageMapper uMqMessageMapper;

    /**
     * 解析SMSMessage消息格式，并将消息发送到MQ中
     * @param smsMessage
     */
    @Override
    public void sendSms(SMSMessage smsMessage){
        //手机号
        String[] assign = smsMessage.getAssign();
        if (assign != null){
            log.info("指定手机号发送短信" + StrUtil.join("," ,assign));
            //查询模板
            UMsgTemplate template = getTemplate(smsMessage.getTemplateId());
            if (null != template){
                for (int i=0;i<assign.length;i++){
                    saveAndSendMessage(assign[i],smsMessage.getTraceId(),smsMessage.getTemplateParams(),template);
                }
            }
        }else {
            String[] assignId = smsMessage.getAssignId();
            if (assignId != null){
                UMsgTemplate template = getTemplate(smsMessage.getTemplateId());
                if (null != template){
                    //根据 assignId 查询联系人信息
                    List<ContactVo> contactList = uContactDao.getContactList(assignId);
                    dealMessage(contactList,smsMessage.getTraceId(),smsMessage.getTemplateParams(),template);
                }
            }else {
                String[] assignGroup = smsMessage.getAssignGroup();
                if (null != assignGroup){
                    UMsgTemplate template = getTemplate(smsMessage.getTemplateId());
                    if (null != template){
                        //根据 assignGroup 查询联系人信息
                        List<ContactVo> contactList = uGroupsDao.getContactList(assignGroup);
                        dealMessage(contactList,smsMessage.getTraceId(),smsMessage.getTemplateParams(),template);
                    }
                }
            }
        }
    }

    /**
     * 处理消息
     * @param contactList
     * @param template
     */
    private void dealMessage(List<ContactVo> contactList,String traceId,List<String> templateParams,UMsgTemplate template){
        Optional.ofNullable(contactList).ifPresent(cList -> cList.forEach(cv -> {
            saveAndSendMessage(cv.getContactValue(),traceId,templateParams,template);
        }));
    }

    /**
     * 消息持久化，同时将消息发送到MQ中
     * @param phoneNumber 手机号
     * @param template 模板对象
     */
    private void saveAndSendMessage(String phoneNumber,String traceId,List<String> templateParams,UMsgTemplate template){
        String originalData = template.getTemplateContent();
        String formatContent = parseTemplate(originalData, templateParams);
        UMqMessage uMqMessage = UMqMessage.builder()
                .traceId(StrUtil.emptyToDefault(traceId,StrUtil.uuid()))
                .messageGenre("sms")
                .status("准备发送")
                .assignTo(phoneNumber)
                .content(formatContent)
                .originalData(originalData)
                .templateId(template.getId())
                .createTime(new Date())
                .updateTime(new Date())
                .build();
        //保存uMqMessage
        if(uMqMessageMapper.insertSelective(uMqMessage) > 0) {
            String jsonString = JSONUtil.toJsonStr(MqMessageObject.builder().phoneNumber(phoneNumber)
                    .templateCode(template.getTemplateCode())
                    .templateParams(templateParams)
                    .uMqMessage(uMqMessage).build());
            //发送消息到MQ
            rabbitTemplate.convertAndSend(ExchangeConfig.CLINBRAIN_AMQ_SMS_DIRECT,
                    BindingsConfig.INFORM_SMS_DEFAULT, jsonString);
        }
    }

    /**
     * 根据参数templateId获取templateCode和templateParams
     * @param smsTemplateId
     * @return
     */
    public UMsgTemplate getTemplate(Long smsTemplateId){
        log.info("获取指定ID[ "+smsTemplateId+" ]的模板内容");
        if(smsTemplateId == null) {
            log.error("获取指定ID[ "+smsTemplateId+" ]的模板内容为空");
            return null;
        }
        return uMsgTemplateDao.getTemplate(smsTemplateId,"sms");
    }

    protected abstract String parseTemplate(String templateContent, List<String> templateParams);
}
