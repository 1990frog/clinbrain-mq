package com.clinbrain.mq.rabbitmq.consumer;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
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
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.HttpClient;
import org.slf4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.regex.Pattern;

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
            if(resp.length > 1){
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
            }

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

    /**
     * demo
     */
    private String yanTaiHttpclient(JSONObject jsonObject)throws UnsupportedEncodingException{
        String phoneNumbers = jsonObject.getString("phoneNumbers");
        String uMqMessageString = jsonObject.getString("uMqMessage");
        UMqMessage uMqMessage = JSON.parseObject(uMqMessageString, UMqMessage.class);
        String content = uMqMessage.getContent();
        // 检查参数是否正确
        String logStr = check(uMqMessage,yanTaiProperties);
        if(logStr != null){
            uMqMessage.setLog(logStr);
            uMqMessage.setStatus("发送失败");
            uMqMessage.setUpdateTime(new Date());
            uMqMessageMapper.updateById(uMqMessage);
            return logStr;
        }
        String requestUrl = yanTaiProperties.getApiUrl();
        YanTaiRequest  yanTaiRequest = new YanTaiRequest();
        yanTaiRequest.setSpCode(yanTaiProperties.getSpCode());
        yanTaiRequest.setF(yanTaiProperties.getF());
        yanTaiRequest.setLoginName(yanTaiProperties.getLoginName());
        yanTaiRequest.setPassword(yanTaiProperties.getPassword());
        yanTaiRequest.setScheduleTime(yanTaiProperties.getScheduleTime());
        yanTaiRequest.setExtendAccessNum(yanTaiProperties.getExtendAccessNum());
        if(yanTaiProperties.isMustFill()){
            StringBuilder msg = new StringBuilder(new String(content.getBytes(), "GBK"));
            if(msg.length() > yanTaiProperties.getCount()){
                msg = new StringBuilder(msg.substring(0, yanTaiProperties.getCount()));
            }else{
                int count = yanTaiProperties.getCount() - msg.length();
                for (int i = 0; i < count; i++) {
                    msg.append(new String(yanTaiProperties.getFillChar().getBytes(), "GBK"));
                }
            }
            yanTaiRequest.setMessageContent(msg.toString());
        }else{
            yanTaiRequest.setMessageContent(new String(content.getBytes(),"GBK"));
        }
        yanTaiRequest.setUserNumber(new String(phoneNumbers.getBytes(),"GBK"));
        String uuid = null;
        if(isNumeric(uMqMessage.getTraceId()) && uMqMessage.getTraceId().length() == 20){
            uuid = new String(uMqMessage.getTraceId().getBytes(),"GBK");
        }else{
            String timestamp = new String((System.currentTimeMillis()+"").getBytes(),"GBK");
            timestamp = timestamp.substring(3);
            String random = new String((new Random().nextInt(9999)+"").getBytes(),"GBK");
            // 6位企业编号 + 10位时间戳 + 4位随机数
            uuid = new String(yanTaiProperties.getSpCode().getBytes(),"GBK") + timestamp + random;
        }
        yanTaiRequest.setSerialNumber(uuid);
        String info = null;
        try{
            HttpClient httpclient = new HttpClient();
            PostMethod post = new PostMethod(requestUrl);
            post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"gbk");
            post.addParameter("SpCode", yanTaiRequest.getSpCode());
            post.addParameter("LoginName", yanTaiRequest.getLoginName());
            post.addParameter("Password", yanTaiRequest.getPassword());
            post.addParameter("MessageContent", yanTaiRequest.getMessageContent());
            post.addParameter("UserNumber", yanTaiRequest.getUserNumber());
            post.addParameter("SerialNumber", new String("".getBytes(),"GBK"));
            post.addParameter("ScheduleTime", new String("".getBytes(),"GBK"));
            post.addParameter("f", yanTaiRequest.getF());
            httpclient.executeMethod(post);
            info = new String(post.getResponseBody(),"gbk");
            log.info("短信接口响应内容：[{}]",info);
        }catch (Exception e) {
            e.printStackTrace();
            info = "result=9999&description=调用短信接口出错["+e.getMessage()+"]";
        }
        return info;
    }

    private String sendSmsOfYanTai(JSONObject jsonObject) throws UnsupportedEncodingException {
        String phoneNumbers = jsonObject.getString("phoneNumbers");
        String uMqMessageString = jsonObject.getString("uMqMessage");
        UMqMessage uMqMessage = JSON.parseObject(uMqMessageString, UMqMessage.class);
        String content = uMqMessage.getContent();
        // 检查参数是否正确
        String logStr = check(uMqMessage,yanTaiProperties);
        if(logStr != null){
            uMqMessage.setLog(logStr);
            uMqMessage.setStatus("发送失败");
            uMqMessage.setUpdateTime(new Date());
            uMqMessageMapper.updateById(uMqMessage);
            return logStr;
        }
        String requestUrl = yanTaiProperties.getApiUrl();
        YanTaiRequest  yanTaiRequest = new YanTaiRequest();
        yanTaiRequest.setSpCode(yanTaiProperties.getSpCode());
        yanTaiRequest.setF(yanTaiProperties.getF());
        yanTaiRequest.setLoginName(yanTaiProperties.getLoginName());
        yanTaiRequest.setPassword(yanTaiProperties.getPassword());
        yanTaiRequest.setScheduleTime(yanTaiProperties.getScheduleTime());
        yanTaiRequest.setExtendAccessNum(yanTaiProperties.getExtendAccessNum());
        if(yanTaiProperties.isMustFill()){
            StringBuilder msg = new StringBuilder(new String(content.getBytes(), "GBK"));
            if(msg.length() > yanTaiProperties.getCount()){
                msg = new StringBuilder(msg.substring(0, yanTaiProperties.getCount()));
            }else{
                int count = yanTaiProperties.getCount() - msg.length();
                for (int i = 0; i < count; i++) {
                    msg.append(new String(yanTaiProperties.getFillChar().getBytes(), "GBK"));
                }
            }
            yanTaiRequest.setMessageContent(msg.toString());
        }else{
            yanTaiRequest.setMessageContent(new String(content.getBytes(),"GBK"));
        }
        //yanTaiRequest.setMessageContent(new String(content.getBytes(),"GBK"));
        yanTaiRequest.setUserNumber(new String(phoneNumbers.getBytes(),"GBK"));
        String uuid = null;
        if(isNumeric(uMqMessage.getTraceId()) && uMqMessage.getTraceId().length() == 20){
            uuid = new String(uMqMessage.getTraceId().getBytes(),"GBK");
        }else{
            String timestamp = new String((System.currentTimeMillis()+"").getBytes(),"GBK");
            timestamp = timestamp.substring(3);
            String random = new String((new Random().nextInt(9999)+"").getBytes(),"GBK");
            // 6位企业编号 + 10位时间戳 + 4位随机数
            uuid = new String(yanTaiProperties.getSpCode().getBytes(),"GBK") + timestamp + random;
        }
        yanTaiRequest.setSerialNumber(uuid);
        HttpHeaders headers = new HttpHeaders();
        //headers.setContentType(MediaType.APPLICATION_JSON);
        //String request = JSON.toJSONString(yanTaiRequest, SerializerFeature.WriteMapNullValue);
        //HttpEntity<String> entity = new HttpEntity<>(request,headers);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("SpCode", yanTaiRequest.getSpCode());
        params.add("LoginName", yanTaiRequest.getLoginName());
        params.add("Password", yanTaiRequest.getPassword());
        params.add("MessageContent", yanTaiRequest.getMessageContent());
        params.add("UserNumber", yanTaiRequest.getSerialNumber());
        params.add("SerialNumber", yanTaiRequest.getSerialNumber());
        params.add("ScheduleTime", yanTaiRequest.getScheduleTime());
        params.add("ExtendAccessNum", yanTaiRequest.getExtendAccessNum());
        params.add("f", yanTaiRequest.getF());
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(params, headers);

        log.info("调用第三方平台接口请求参数:[{}]",JSON.toJSONString(requestEntity, SerializerFeature.WriteMapNullValue));
        String status = restTemplate.postForEntity(requestUrl,requestEntity,String.class).getBody();
        String respMsg = new String(status.getBytes("ISO-8859-1"),"GBK");
        log.info("调用第三方平台接口返回:[{}]",respMsg);
        return respMsg;
    }

    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
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
