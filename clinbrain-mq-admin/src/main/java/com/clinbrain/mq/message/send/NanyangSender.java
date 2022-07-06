package com.clinbrain.mq.message.send;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.net.URLDecoder;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import com.clinbrain.mq.message.ISmsSender;
import com.clinbrain.mq.message.SMSException;
import com.clinbrain.mq.message.conf.LinshuProperties;
import com.clinbrain.mq.message.conf.NanyangProperties;
import com.clinbrain.mq.model.custom.MqMessageObject;
import com.clinbrain.mq.model.custom.UMqMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 烟台发送短信的方法
 *
 * @author Liaopan
 * @date 2021-12-06
 */
@Service("nanyang")
@Slf4j
@ConditionalOnProperty(prefix = "spring.profiles.", name = "active", havingValue = "nanyang")
public class NanyangSender implements ISmsSender {

    @Autowired
    private NanyangProperties nanyangProperties;

    @Override
    public void sendSms(MqMessageObject mqMessageObject) throws SMSException {

        String phoneNumber = mqMessageObject.getPhoneNumber();
        UMqMessage uMqMessage = mqMessageObject.getUMqMessage();
        JSONObject paramMap = new JSONObject();
        final long time = System.currentTimeMillis();
        final Long smsId = uMqMessage.getId();
        final String authCode = nanyangProperties.getAuthCode();
        final String spId = nanyangProperties.getSpId();
        String content = "";
        try {
            content = URLEncoder.encode(uMqMessage.getContent(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new SMSException("短信内容重编码失败: " + ExceptionUtil.getRootCauseMessage(e));
        }

        paramMap.set("auth_code", authCode);
        paramMap.set("spid", spId); // 输出参数
        paramMap.set("smsid", smsId+"");
        paramMap.set("mobiles", phoneNumber);
        paramMap.set("sendtime", "");
        paramMap.set("timestamp", time+"");
        paramMap.set("content", content);
        try {
            paramMap.set("sign", SHA1(authCode + spId + nanyangProperties.getExtport()
            +smsId+phoneNumber+""+content+time+nanyangProperties.getAuthKey()));
            final String postBody = HttpUtil.createPost(nanyangProperties.getApiUrl())
                    .header("X-APP-ID", nanyangProperties.getXAppID())
                    .header("X-APP-KEY", nanyangProperties.getXAppKey()).body(paramMap.toString())
                    .execute().body();
            if(StrUtil.isNotEmpty(postBody)) {
                JSONObject resultObj = new JSONObject(URLDecoder.decode(postBody, StandardCharsets.UTF_8));
                if(!"1".equalsIgnoreCase(resultObj.getStr("code"))) {
                    throw new SMSException("短信平台返回错误: 错误代码["+resultObj.getStr("code")+"]信息:" + resultObj.getStr("text"));
                }
            }else {
                throw new SMSException("获取短信平台信息出错: 系统未知错误:" + postBody);
            }
        } catch (Exception e) {
            throw new SMSException("短信发送失败: " + ExceptionUtil.getRootCauseMessage(e));
        }

    }

    public static String SHA1(String plainText) {

        return SecureUtil.sha1(plainText);
    }

}
