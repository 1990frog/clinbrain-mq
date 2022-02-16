package com.clinbrain.mq.message.send;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import com.clinbrain.mq.message.ISmsSender;
import com.clinbrain.mq.message.SMSException;
import com.clinbrain.mq.message.conf.LinshuProperties;
import com.clinbrain.mq.model.custom.MqMessageObject;
import com.clinbrain.mq.model.custom.UMqMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * 烟台发送短信的方法
 *
 * @author Liaopan
 * @date 2021-12-06
 */
@Service("linshu")
@Slf4j
@ConditionalOnProperty(prefix = "spring.profiles.", name = "active", havingValue = "linshu")
public class LinshuSender implements ISmsSender {

    @Autowired
    private LinshuProperties linshuProperties;

    @Override
    public void sendSms(MqMessageObject mqMessageObject) throws SMSException {
        String cpCode = linshuProperties.getCpCode();
        String key = linshuProperties.getKey();
        String phoneNumber = mqMessageObject.getPhoneNumber();
        UMqMessage uMqMessage = mqMessageObject.getUMqMessage();
        String templateId = uMqMessage.getTemplateId()+"";
        String msg = StrUtil.join(",",mqMessageObject.getTemplateParams());
        JSONObject paramMap = new JSONObject();
        paramMap.set("cpcode", cpCode);
        paramMap.set("msg", msg); // 输出参数
        paramMap.set("mobiles", phoneNumber);
        paramMap.set("excode", "");
        paramMap.set("templetid", templateId);
        try {
            paramMap.set("sign", makeMD5(cpCode+msg+phoneNumber+""+templateId+key));
            final String postBody = HttpUtil.post(linshuProperties.getApiUrl(), paramMap.toString());
            if(StrUtil.isNotEmpty(postBody)) {
                JSONObject resultObj = new JSONObject(postBody);
                if(!"0".equalsIgnoreCase(resultObj.getStr("resultcode"))) {
                    throw new SMSException("短信平台返回错误: 错误信息->" + resultObj.getStr("resultmsg"));
                }
            }else {
                throw new SMSException("获取短信平台信息出错: 系统未知错误->" + postBody);
            }
        } catch (Exception e) {
            throw new SMSException("短信发送失败: " + ExceptionUtil.getRootCauseMessage(e));
        }

    }

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
