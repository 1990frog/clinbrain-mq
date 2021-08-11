package com.clinbrain.mq.service.custom;

import com.alibaba.fastjson.JSON;
import com.clinbrain.mq.common.message.SMSMessage;
import com.clinbrain.mq.common.rabbitmq.BindingsConfig;
import com.clinbrain.mq.common.rabbitmq.ExchangeConfig;
import com.clinbrain.mq.mapper.custom.UContactDao;
import com.clinbrain.mq.mapper.custom.UMqMessageMapper;
import com.clinbrain.mq.mapper.custom.UMsgTemplateDao;
import com.clinbrain.mq.mapper.custom.UGroupsDao;
import com.clinbrain.mq.model.custom.UMqMessage;
import com.clinbrain.mq.model.custom.sms.ContactVo;
import com.clinbrain.mq.model.custom.sms.UMsgTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @Autowired
    private UMqMessageMapper uMqMessageMapper;

    /**
     * 解析SMSMessage消息格式，并将消息发送到MQ中
     * @param smsMessage
     */
    public void sendSms(SMSMessage smsMessage){
        //手机号
        String[] assign = smsMessage.getAssign();
        if (assign != null){
            //查询模板
            UMsgTemplate template = getTemplate(smsMessage);
            if (null != template){
                for (int i=0;i<assign.length;i++){
                    saveAndSendMessage(assign[i],smsMessage.getTraceId(),smsMessage.getTemplateParams(),template);
                }
            }
        }else {
            String[] assignId = smsMessage.getAssignId();
            if (assignId != null){
                UMsgTemplate template = getTemplate(smsMessage);
                if (null != template){
                    //根据 assignId 查询联系人信息
                    List<ContactVo> contactList = uContactDao.getContactList(assignId);
                    dealMessage(contactList,smsMessage.getTraceId(),smsMessage.getTemplateParams(),template);
                }
            }else {
                String[] assignGroup = smsMessage.getAssignGroup();
                if (null != assignGroup){
                    UMsgTemplate template = getTemplate(smsMessage);
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
        contactList.stream().forEach(cv -> {
            saveAndSendMessage(cv.getContactValue(),traceId,templateParams,template);
        });
    }

    /**
     * 消息持久化，同时将消息发送到MQ中
     * @param phoneNumbers 手机号
     * @param template 模板对象
     */
    private void saveAndSendMessage(String phoneNumbers,String traceId,List<String> templateParams,UMsgTemplate template){
        String originalData = template.getTemplateContent();
        Object[] objectsParams = templateParams.toArray();
        //String formatContent = MessageFormat.format(originalData, objectsParams);
        String formatContent = parseTemplate(originalData, templateParams);
        UMqMessage uMqMessage = UMqMessage.builder()
                .traceId(traceId)
                .messageGenre("sms")
                .status("准备发送")
                .assignTo(phoneNumbers)
                .content(formatContent)
                .originalData(originalData)
                .templateId(template.getId())
                .createTime(new Date())
                .updateTime(new Date())
                .build();
        //保存uMqMessage
        uMqMessageMapper.insertSelective(uMqMessage);

        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("phoneNumbers",phoneNumbers);
        paramMap.put("templateCode",template.getTemplateCode());
        paramMap.put("templateParams",templateParams);
        paramMap.put("uMqMessage",uMqMessage);
        String jsonString = JSON.toJSONString(paramMap);
        //发送消息到MQ
        rabbitTemplate.convertAndSend(ExchangeConfig.CLINBRAIN_AMQ_SMS_DIRECT,
                BindingsConfig.INFORM_SMS_DEFAULT,jsonString);
    }

    /**
     * 根据参数templateId获取templateCode和templateParams
     * @param smsMessage
     * @return
     */
    public UMsgTemplate getTemplate(SMSMessage smsMessage){
        Long templateId = smsMessage.getTemplateId();
        UMsgTemplate template = uMsgTemplateDao.getTemplate(templateId,"sms");
        if (null != template){
            return template;
        }
        return null;
    }

    // 短信模板：报警服务器地址{xxxxxxxxxxxxxx}报警类型{xxxxxxxxxx}报警时间{xxxxxxxxxxxxxxxxxxxx}报警内容进程{xxxxxxxxxxxxxxxxxxxx}
    private String parseTemplate(String content, List<String> templateParams) {
        String smsContent = content;
        Pattern pattern = Pattern.compile("(\\{[x]*\\})");
        Matcher matcher = pattern.matcher(smsContent);
        int group = 0;
        while (matcher.find()){
            String item = matcher.group();
            //去掉前后{}
            item = item.substring(1,item.length() -1);
            String target = templateParams.get(group);
            // 超过模板定义的部分截断，不足模板长度用空格追加
            if(item.length() > target.length()){
                for (int i = 0; i < item.length() - target.length(); i++) {
                    target += " ";
                }
            }else{
                target = target.substring(0,item.length());
            }
            smsContent = smsContent.replaceFirst(item, target);
            group++;
        }
        return smsContent.replaceAll("\\{","").replaceAll("}","");
    }
}
