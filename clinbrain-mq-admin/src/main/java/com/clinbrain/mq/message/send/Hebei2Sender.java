package com.clinbrain.mq.message.send;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.clinbrain.mq.message.ISmsSender;
import com.clinbrain.mq.message.SMSException;
import com.clinbrain.mq.message.conf.Hebei2Properties;
import com.clinbrain.mq.model.custom.MqMessageObject;
import com.clinbrain.mq.model.custom.UMqMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

/**
 * 河北2院短信平台对接
 *
 * @author Liaopan
 * @date 2022-03-31
 */
@Service("hebei2")
@Slf4j
@ConditionalOnProperty(prefix = "spring.profiles.", name = "active", havingValue = "hebei2")
public class Hebei2Sender implements ISmsSender {

    @Autowired
    private Hebei2Properties hebei2Properties;

    @Override
    public void sendSms(MqMessageObject mqMessageObject) throws SMSException {
        String phoneNumber = mqMessageObject.getPhoneNumber();
        UMqMessage uMqMessage = mqMessageObject.getUMqMessage();
        String content = uMqMessage.getContent();

        if (StrUtil.isEmpty(content)) {
            throw new SMSException("短信内容为空!");
        }

        String requestUrl = hebei2Properties.getApiUrl();

        final String timestamp = String.valueOf(System.currentTimeMillis());

        JSONObject jsonParams = new JSONObject();

        jsonParams.set("appKey", hebei2Properties.getAppKey());
        jsonParams.set("timestamp", timestamp);
        jsonParams.set("mobile", phoneNumber);
        jsonParams.set("content", content);
        jsonParams.set("spNumber", hebei2Properties.getSpNumber());
        jsonParams.set("sendTime", "");
        jsonParams.set("reportUrl", hebei2Properties.getReportUrl());
        jsonParams.set("moUrl", hebei2Properties.getMoUrl());
        jsonParams.set("attach", hebei2Properties.getAttach());
        jsonParams.set("sign", SecureUtil.md5(hebei2Properties.getAppKey()
        +timestamp+phoneNumber+content+hebei2Properties.getSpNumber()+""+hebei2Properties.getAppSecret()));

        try (CloseableHttpClient httpClient = HttpClients.createDefault()){
            HttpPost post = new HttpPost(requestUrl);
            post.setEntity(new StringEntity(jsonParams.toString(), ContentType.APPLICATION_JSON));
            final String resp = IoUtil.read(httpClient.execute(post).getEntity().getContent(),
                    StandardCharsets.UTF_8);
            log.info("调用短信接口返回:" + resp);
            if(StrUtil.isNotEmpty(resp)) {
                JSONObject resultObj = JSONUtil.parseObj(resp);
                int status = resultObj.getInt("status");
                if(status != 1) {
                    throw new SMSException(resultObj.getStr("message", "短信发送出现未知错误"));
                }
            }
        } catch (Exception e) {
            throw new SMSException(ExceptionUtil.getRootCauseMessage(e));
        }
    }

}
