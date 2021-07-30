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
import com.clinbrain.mq.common.conf.YanTaiProperties;
import com.clinbrain.mq.common.properties.AliyunSmsProp;
import com.clinbrain.mq.common.properties.QiRuiYunProp;
import com.clinbrain.mq.common.rabbitmq.QueueConfig;
import com.clinbrain.mq.mapper.custom.UMqMessageMapper;
import com.clinbrain.mq.model.custom.UMqMessage;
import com.clinbrain.mq.model.custom.sms.YanTaiRequest;
import com.rabbitmq.client.Channel;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Slf4j
@Component
public class SmsHandler {

    @Autowired
    private AliyunSmsProp aliyunSmsProp;

    @Autowired
    private QiRuiYunProp qiRuiYunProp;
    @Autowired
    private UMqMessageMapper uMqMessageMapper;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    YanTaiProperties yanTaiProperties;

    @RabbitListener(queues = {QueueConfig.CLINBRAIN_SMS_DEFAULT_QUEUE})
    public void receiveSms(Message message, Channel channel) throws IOException {
        try {
            byte[] body = message.getBody();
            String msg = new String(body,"utf-8");
            log.info("queue sms inform receive msg={}",msg);
            JSONObject jsonObject = JSON.parseObject(msg);
            //sendSms(jsonObject);
            UMqMessage uMqMessage = JSON.parseObject(jsonObject.getString("uMqMessage"), UMqMessage.class);
            String[] resp = sendSmsOfYanTai(jsonObject).split("&");
            String code = null,desc = null;
            for(String item : resp){
                String[] temp = item.split("=");
                if(item.contains("result")){
                    code = temp[1];
                }else if(item.contains("description")){
                    desc = temp[1];
                }
            }
            if("0".equals(code)){
                uMqMessage.setLog("消息发送成功");
            }else{
                uMqMessage.setLog(desc);
            }
            uMqMessage.setStatus("发送成功");
            uMqMessage.setUpdateTime(new Date());
            uMqMessageMapper.updateById(uMqMessage);

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

    private String sendSmsOfYanTai(JSONObject jsonObject) throws UnsupportedEncodingException {
        String phoneNumbers = jsonObject.getString("phoneNumbers");
        String uMqMessageString = jsonObject.getString("uMqMessage");
        UMqMessage uMqMessage = JSON.parseObject(uMqMessageString, UMqMessage.class);
        String content = uMqMessage.getContent();
        // 检查参数是否正确
        String log = check(uMqMessage,yanTaiProperties);
        if(log != null){
            uMqMessage.setLog(log);
            uMqMessage.setStatus("发送失败");
            uMqMessage.setUpdateTime(new Date());
            uMqMessageMapper.updateById(uMqMessage);
            return log;
        }
        String requestUrl = yanTaiProperties.getApiUrl();
        YanTaiRequest  yanTaiRequest = new YanTaiRequest();
        yanTaiRequest.setF(yanTaiProperties.getF());
        yanTaiRequest.setLoginName(yanTaiProperties.getLoginName());
        yanTaiRequest.setPassword(yanTaiProperties.getPassword());
        yanTaiRequest.setScheduleTime(StrUtil.isEmpty(yanTaiProperties.getScheduleTime())?null:yanTaiProperties.getScheduleTime());
        yanTaiRequest.setSpCode(yanTaiProperties.getSpCode());
        yanTaiRequest.setMessageContent(new String(content.getBytes(),"GBK"));
        yanTaiRequest.setUserNumber(new String(phoneNumbers.getBytes(),"GBK"));
        yanTaiRequest.setSerialNumber(new String(UUID.randomUUID().toString().getBytes(),"GBK"));
        // 将UTF8 编码装换为GBK 或 GB2312
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(JSON.toJSONString(yanTaiRequest),headers);
        String status = restTemplate.postForEntity(requestUrl,entity,String.class).getBody();
        return new String(status.getBytes(),"UTF-8");
    }

    private String check(UMqMessage uMqMessage, YanTaiProperties yanTaiProperties) {
        String log = null;
        if(StrUtil.isEmpty(yanTaiProperties.getF())){
            log = "提交时检测方式参数错误";
        }
        if(StrUtil.isEmpty(yanTaiProperties.getLoginName())){
            log = "用户名称参数错误";
        }
        if(StrUtil.isEmpty(yanTaiProperties.getPassword())){
            log = "用户密码参数错误";
        }
        if(StrUtil.isEmpty(yanTaiProperties.getSpCode())){
            log = "企业编号参数错误";
        }
        if(StrUtil.isEmpty(uMqMessage.getAssignTo())){
            log = "短信接受人参数错误";
        }
        if(StrUtil.isEmpty(uMqMessage.getContent())){
            log = "短信内容参数错误";
        }
        return log;
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
