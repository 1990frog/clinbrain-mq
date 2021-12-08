package com.clinbrain.mq.message;

import cn.hutool.core.lang.Pair;
import com.clinbrain.mq.common.message.SMSMessage;
import com.clinbrain.mq.model.custom.MqMessageObject;
import com.google.gson.JsonObject;

/**
 * Created by Liaopan on 2021-12-06.
 * 定义一个通用的发送短信的接口模板
 */
public interface ISmsSender {

    /**
     * 短信发送接口
     * @param smsMessage
     * @return 键值对，boolean:发送成功或失败，string: 失败后的错误信息
     */
    void sendSms(MqMessageObject mqMessageObject) throws SMSException;
}
