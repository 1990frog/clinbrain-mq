package com.clinbrain.mq.message.send;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.clinbrain.mq.message.ISmsSender;
import com.clinbrain.mq.message.SMSException;
import com.clinbrain.mq.message.conf.DeyangProperties;
import com.clinbrain.mq.model.custom.MqMessageObject;
import com.clinbrain.mq.model.custom.UMqMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 德阳发送短信的方法
 *
 * @author Liaopan
 * @date 2022-03-31
 */
@Service("deyang")
@Slf4j
@ConditionalOnProperty(prefix = "spring.profiles.", name = "active", havingValue = "deyang")
public class DeyangSender implements ISmsSender {

    @Autowired
    private DeyangProperties deyangProperties;

    @Override
    public void sendSms(MqMessageObject mqMessageObject) throws SMSException {
        String phoneNumber = mqMessageObject.getPhoneNumber();
        UMqMessage uMqMessage = mqMessageObject.getUMqMessage();
        String content = uMqMessage.getContent();

        if (StrUtil.isEmpty(content)) {
            throw new SMSException("短信内容为空!");
        }

        String requestUrl = deyangProperties.getApiUrl();

        JSONObject jsonParams = new JSONObject();
        jsonParams.set("ecName", deyangProperties.getEcName());
        jsonParams.set("apId", deyangProperties.getApId());
        jsonParams.set("mobiles", phoneNumber);
        jsonParams.set("content", content);
        jsonParams.set("sign", deyangProperties.getSign());
        jsonParams.set("addSerial", deyangProperties.getAddSerial());
        jsonParams.set("mac", SecureUtil.md5(deyangProperties.getEcName() + deyangProperties.getApId() + deyangProperties.getSecretKey()
                + phoneNumber + content + deyangProperties.getSign() + deyangProperties.getAddSerial()));


        try {
            /**
             * 返回值状态
             * 名称	类型	说明
             * rspcod	String	响应状态，详见下表。
             * mgsGroup	String	消息批次号，由云MAS平台生成，用于关联短信发送请求与状态报告，注：若数据验证不通过，该参数值为空。
             * success	boolean	数据校验结果。
             */
            String resp = HttpUtil.post(requestUrl, Base64.encode(jsonParams.toString()));
            if(StrUtil.isNotEmpty(resp)) {
                JSONObject resultObj = JSONUtil.parseObj(resp);
                String rspcodValue = String.valueOf(resultObj.get("rspcod"));
                if(!"success".equalsIgnoreCase(rspcodValue)) {
                    throw new SMSException(codeMap.getOrDefault("rspcodValue", rspcodValue));
                }
            }
            log.info("调用短信接口返回:" + resp);
        } catch (Exception e) {
            throw new SMSException(e.getMessage());
        }
    }

    static Map<String,String> codeMap = new HashMap<>();
    static {
        codeMap.put("IllegalMac","mac校验不通过。");
        codeMap.put("IllegalSignId","无效的签名编码。");
        codeMap.put("InvalidMessage","非法消息，请求数据解析失败。");
        codeMap.put("InvalidUsrOrPwd","非法用户名/密码。");
        codeMap.put("NoSignId","未匹配到对应的签名信息。");
        codeMap.put("success","数据验证通过。");
        codeMap.put("TooManyMobiles","手机号数量超限（>5000），应≤5000。");
    }

}
