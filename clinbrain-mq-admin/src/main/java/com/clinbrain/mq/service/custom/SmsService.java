package com.clinbrain.mq.service.custom;

import com.alibaba.fastjson.JSON;
import com.clinbrain.mq.common.message.SMSMessage;
import com.clinbrain.mq.common.rabbitmq.BindingsConfig;
import com.clinbrain.mq.common.rabbitmq.ExchangeConfig;
import com.clinbrain.mq.mapper.custom.UContactDao;
import com.clinbrain.mq.mapper.custom.UMsgTemplateDao;
import com.clinbrain.mq.mapper.custom.UGroupsDao;
import com.clinbrain.mq.model.custom.sms.ContactVo;
import com.clinbrain.mq.model.custom.sms.UMsgTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class SmsService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private UGroupsDao uGroupsDao;

    @Autowired
    private UMsgTemplateDao uMsgTemplateDao;

    @Autowired
    private UContactDao uContactDao;

    /**
     * 解析SMSMessage消息格式，并将消息发送到MQ中
     * @param smsMessage
     */
    public void sendSms(SMSMessage smsMessage){
        //手机号
        String[] assign = smsMessage.getAssign();
        if (assign != null){
            //查询模板
            Pair<String, List<String>> template = getTemplate(smsMessage);
            if (null != template){
                for (int i=0;i<assign.length;i++){
                    sendMQ(assign[i],template.getLeft(),template.getRight());
                }
            }
        }else {
            String[] assignId = smsMessage.getAssignId();
            if (assignId != null){
                Pair<String, List<String>> template = getTemplate(smsMessage);
                if (null != template){
                    //根据 assignId 查询联系人信息
                    List<ContactVo> contactList = uContactDao.getContactList(assignId);
                    sendMQ(contactList,template);
                }
            }else {
                String[] assignGroup = smsMessage.getAssignGroup();
                if (null != assignGroup){
                    Pair<String, List<String>> template = getTemplate(smsMessage);
                    if (null != template){
                        //根据 assignGroup 查询联系人信息
                        List<ContactVo> contactList = uGroupsDao.getContactList(assignGroup);
                        sendMQ(contactList,template);
                    }
                }
            }
        }
    }

    private void sendMQ(List<ContactVo> contactList,Pair<String, List<String>> template){
        contactList.stream().forEach(cv -> {
            sendMQ(cv.getContactValue(),template.getLeft(),template.getRight());
        });
    }

    private void sendMQ(String phoneNumbers,String templateCode,List<String> templateParams){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("phoneNumbers",phoneNumbers);
        paramMap.put("templateCode",templateCode);
        paramMap.put("templateParams",templateParams);
        String jsonString = JSON.toJSONString(paramMap);
        rabbitTemplate.convertAndSend(ExchangeConfig.CLINBRAIN_AMQ_SMS_DIRECT,
                BindingsConfig.INFORM_SMS_DEFAULT,jsonString);
    }

    public Pair<String, List<String>> getTemplate(SMSMessage smsMessage){
        Long templateId = smsMessage.getTemplateId();
        UMsgTemplate template = uMsgTemplateDao.getTemplate(templateId);
        if (null != template){
            String templateCode = template.getTemplateCode();
            List<String> templateParams = smsMessage.getTemplateParams();
            Pair<String, List<String>> pair = new ImmutablePair<>(templateCode, templateParams);
            return pair;
        }
        return null;
    }
}
