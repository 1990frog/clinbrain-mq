package com.clinbrain.mq.message.send;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.clinbrain.mq.message.conf.YanTaiProperties;
import com.clinbrain.mq.message.ISmsSender;
import com.clinbrain.mq.message.SMSException;
import com.clinbrain.mq.model.custom.MqMessageObject;
import com.clinbrain.mq.model.custom.UMqMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * 烟台发送短信的方法
 * @author Liaopan
 * @date 2021-12-06
 */
@Service("yantai")
@Slf4j
@ConditionalOnProperty(prefix = "spring.profile", name = "active", havingValue = "yantai")
public class YantaiSender implements ISmsSender {

    @Autowired
    private YanTaiProperties yanTaiProperties;

    @Override
    public void sendSms(MqMessageObject mqMessageObject) throws SMSException {
        String phoneNumbers = mqMessageObject.getPhoneNumber();
        UMqMessage uMqMessage = mqMessageObject.getUMqMessage();
        String content = uMqMessage.getContent();
        // 检查参数是否正确
        String logStr = check(uMqMessage,yanTaiProperties);
        if(logStr != null){
            throw new SMSException(logStr);
        }
        String requestUrl = yanTaiProperties.getApiUrl();
        String uuid = null;
        if(uMqMessage.getTraceId() != null &&
                NumberUtil.isNumber(uMqMessage.getTraceId()) && uMqMessage.getTraceId().length() == 20){
            uuid = uMqMessage.getTraceId();
        }else{
            String timestamp = System.currentTimeMillis()+"";
            timestamp = timestamp.substring(3);
            String random = new Random().nextInt(9999)+"";
            // 6位企业编号 + 10位时间戳 + 4位随机数
            uuid = yanTaiProperties.getSpCode() + timestamp + random;
        }
        try{
            HttpClient httpclient = new HttpClient();
            PostMethod post = new PostMethod(requestUrl);
            post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"gbk");
            post.addParameter("SpCode", yanTaiProperties.getSpCode());
            post.addParameter("LoginName", yanTaiProperties.getLoginName());
            post.addParameter("Password", yanTaiProperties.getPassword());
            post.addParameter("MessageContent", content);
            post.addParameter("UserNumber", phoneNumbers);
            post.addParameter("SerialNumber", uuid);
            post.addParameter("ScheduleTime", "");
            post.addParameter("ExtendAccessNum", "");
            post.addParameter("f", yanTaiProperties.getF());
            httpclient.executeMethod(post);
            String info = new String(post.getResponseBody(),"gbk");
            if(StrUtil.isNotEmpty(info)){
                String[] resp = info.split("&");
                String code = null,desc = null;
                for(String item : resp){
                    String[] temp = item.split("=");
                    if(item.contains("result")){
                        code = temp[1];
                    }else if(item.contains("description")){
                        desc = temp[1];
                    }
                }
                if(!"0".equals(code)){
                    throw new SMSException("调用短信接口返回信息["+desc+"]");
                }
            }
            log.info("短信接口响应内容：[{}]",info);
        }catch (Exception e) {
            throw new SMSException(e.getMessage());
        }
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
}
