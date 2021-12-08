package com.clinbrain.mq.service;

import com.clinbrain.mq.common.message.SMSMessage;

/**
 * 作为统一的短信模板接口
 *
 * @author Liaopan
 * @date 2021-12-08
 */
public interface ISmsTemplateService {

    void sendSms(SMSMessage smsMessage);
}
