package com.clinbrain.mq.message.send;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import com.clinbrain.mq.message.ISmsSender;
import com.clinbrain.mq.message.SMSException;
import com.clinbrain.mq.message.conf.PengzhouProperties;
import com.clinbrain.mq.message.conf.ShiyanProperties;
import com.clinbrain.mq.model.custom.MqMessageObject;
import com.clinbrain.mq.model.custom.UMqMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 彭州发送短信的方法
 *
 * @author yuehl
 * @date 2022-03-16
 */
@Service("shiyan")
@Slf4j
@ConditionalOnProperty(prefix = "spring.profiles.", name = "active", havingValue = "shiyan")
public class ShiyanSender implements ISmsSender {

    @Autowired
    private ShiyanProperties shiyanProperties;

    @Override
    public void sendSms(MqMessageObject mqMessageObject) throws SMSException {
        String sendSmsUrl = shiyanProperties.getSendSmsUrl();
        String ecName = shiyanProperties.getEcName();
        String apId = shiyanProperties.getApId();
        String secretKey = shiyanProperties.getSecretKey();
        String sign = shiyanProperties.getSign();
        String addSerial = shiyanProperties.getAddSerial();

        String phoneNumber = mqMessageObject.getPhoneNumber();
        UMqMessage uMqMessage = mqMessageObject.getUMqMessage();
        String msg = uMqMessage.getContent();

        JSONObject paramMap = new JSONObject();
        paramMap.set("ecName", ecName);
        paramMap.set("apId", apId);
        paramMap.set("secretKey", secretKey);
        paramMap.set("mobiles", phoneNumber);
        paramMap.set("content", msg); // 输出参数
        paramMap.set("sign", sign);
        paramMap.set("addSerial", addSerial);

        try {

            paramMap.set("mac", makeMD5(ecName+apId+secretKey+phoneNumber+msg+sign+addSerial));
            final String smsPostBody =  HttpUtil.post(sendSmsUrl, paramMap.toString());
            if(StrUtil.isNotEmpty(smsPostBody)) {
                JSONObject smsResultObj = new JSONObject(smsPostBody);
                if(!"success".equalsIgnoreCase(smsResultObj.getStr("rspcod"))) {
                    throw new SMSException("短信平台返回错误: 错误信息->" + smsResultObj.getStr("rspcod"));
                }
            }else {
                throw new SMSException("获取短信平台信息出错: 系统未知错误->" + smsPostBody);
            }
        } catch (Exception e) {
            throw new SMSException("短信发送失败: "  + ExceptionUtil.getRootCauseMessage(e));
        }

    }

    /**
     * 计算字符串md5
     * @param plainText
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String makeMD5(String plainText) throws NoSuchAlgorithmException {
        String re_md5 = "";
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(plainText.getBytes());
        byte[] b = md.digest();
        int i;
        StringBuilder buf = new StringBuilder();
        for (byte value : b) {
            i = value;
            if (i < 0)
                i += 256;
            if (i < 16)
                buf.append("0");
            buf.append(Integer.toHexString(i));
        }
        re_md5 = buf.toString().toLowerCase();
        return re_md5;
    }

}
