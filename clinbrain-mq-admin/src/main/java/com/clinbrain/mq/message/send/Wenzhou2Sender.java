package com.clinbrain.mq.message.send;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import com.clinbrain.mq.message.ISmsSender;
import com.clinbrain.mq.message.SMSException;
import com.clinbrain.mq.message.conf.ShiyanProperties;
import com.clinbrain.mq.message.conf.Wenzhou2Properties;
import com.clinbrain.mq.model.custom.MqMessageObject;
import com.clinbrain.mq.model.custom.UMqMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * 温州2院发送短信的方法
 *
 * @author yuehl
 * @date 2022-11-16
 */
@Service("wenzhou2")
@Slf4j
@ConditionalOnProperty(prefix = "spring.profiles.", name = "active", havingValue = "wenzhou2")
public class Wenzhou2Sender implements ISmsSender {

    @Autowired
    private Wenzhou2Properties wenzhou2Properties;

    @Override
    public void sendSms(MqMessageObject mqMessageObject) throws SMSException {
        String sendSmsUrl = wenzhou2Properties.getSendSmsUrl();
        String company = wenzhou2Properties.getCompany();
        String actionV = wenzhou2Properties.getActionV();
        String classV = wenzhou2Properties.getClassV();
        String token = wenzhou2Properties.getToken();
        String mbid = wenzhou2Properties.getMbid();


        String phoneNumber = mqMessageObject.getPhoneNumber();
        UMqMessage uMqMessage = mqMessageObject.getUMqMessage();
        String msg = uMqMessage.getContent();

        JSONObject paramMap = new JSONObject();
        paramMap.set("action", actionV);
        paramMap.set("class", classV);


        JSONObject inputMap = new JSONObject();
        inputMap.set("MBID",mbid);
        inputMap.set("MBCS",msg);
        inputMap.set("LXDH",phoneNumber);
        inputMap.set("YGDM","");
        String inputStr = inputMap.toString();


        try {
            String timeStampStr = String.valueOf(System.currentTimeMillis()/1000);
            String message = company + token + timeStampStr + inputStr;
            String sign = makeMD5(message);
            paramMap.set("token", company + "_" + timeStampStr + "|" + sign);
            paramMap.set("input", inputMap);
            log.info("发送报文为：{}",paramMap.toString());
            final String smsPostBody =  HttpUtil.post(sendSmsUrl, paramMap.toString());
            if(StrUtil.isNotEmpty(smsPostBody)) {
                JSONObject smsResultObj = new JSONObject(smsPostBody);
                if(!"1".equalsIgnoreCase(smsResultObj.getStr("result"))) {
                    throw new SMSException("短信平台返回错误: 错误信息->" + smsResultObj.toString());
                }
            }else {
                throw new SMSException("获取短信平台信息出错: 系统未知错误->" + smsPostBody);
            }
        } catch (Exception e) {
            throw new SMSException("短信发送失败: " + ExceptionUtil.getRootCauseMessage(e));
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
