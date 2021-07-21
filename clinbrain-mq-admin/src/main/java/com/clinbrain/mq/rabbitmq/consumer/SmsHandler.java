package com.clinbrain.mq.rabbitmq.consumer;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.clinbrain.mq.common.properties.AliyunSmsProp;
import com.clinbrain.mq.common.properties.QiRuiYunProp;
import com.clinbrain.mq.common.rabbitmq.QueueConfig;
import com.clinbrain.mq.model.custom.UMqMessage;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;
import java.util.StringJoiner;

@Slf4j
@Component
public class SmsHandler {

    @Autowired
    private AliyunSmsProp aliyunSmsProp;

    @Autowired
    private QiRuiYunProp qiRuiYunProp;

    @Autowired
    private RestTemplate restTemplate;

    @RabbitListener(queues = {QueueConfig.CLINBRAIN_SMS_DEFAULT_QUEUE})
    public void receiveSms(Message message, Channel channel) throws IOException {
        try {
            byte[] body = message.getBody();
            String msg = new String(body,"utf-8");
            log.info("queue sms inform receive msg={}",msg);
            JSONObject jsonObject = JSON.parseObject(msg);
            sendSms(jsonObject);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (Exception e) {
            if (message.getMessageProperties().getRedelivered()) {
                log.error("消息已重复处理失败,拒绝再次接收,error msg={}",e.getMessage());
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
            } else {
                log.error("消息处理失败,再次返回队列,error msg={}",e.getMessage());
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            }

        }
    }

    private void sendSms(JSONObject jsonObject){
        String phoneNumbers = jsonObject.getString("phoneNumbers");
        String templateCode = jsonObject.getString("templateCode");
        String templateParams = jsonObject.getString("templateParams");
        String uMqMessageString = jsonObject.getString("uMqMessage");
        UMqMessage uMqMessage = JSON.parseObject(uMqMessageString, UMqMessage.class);
        String content = uMqMessage.getContent();

        if (StrUtil.isEmpty(phoneNumbers)){
            return;
        }
        StringBuffer url = new StringBuffer();
        url.append("http://api.qirui.com:7891/mt")
                .append("?dc=").append(qiRuiYunProp.getDc())
                .append("&un=").append(qiRuiYunProp.getUn())
                .append("&pw=").append(qiRuiYunProp.getPw())
                .append("&sm=").append(qiRuiYunProp.getSm()).append(content)
                .append("&da=").append(phoneNumbers)
                .append("&tf=").append(qiRuiYunProp.getTf())
                .append("&rf=").append(qiRuiYunProp.getRf())
                .append("&rd=").append(qiRuiYunProp.getRd());
        log.info("url={}",url);

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url.toString(), String.class);
        log.info("responseEntity={}",JSON.toJSONString(responseEntity));

        //阿里云短信服务
        /*DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", aliyunSmsProp.getAccessKeyId(), aliyunSmsProp.getSecret());
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("PhoneNumbers", phoneNumbers);
        request.putQueryParameter("SignName", aliyunSmsProp.getSignName());
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParam", templateParam);
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }*/
    }




}
