package com.clinbrain.mq.controller.custom;

import com.clinbrain.mq.common.conf.SmsServiceBeanConfig;
import com.clinbrain.mq.common.conf.V2Config;
import com.clinbrain.mq.common.message.SMSMessage;
import com.clinbrain.mq.service.BaseSmsService;
import com.clinbrain.mq.service.ISmsTemplateService;
import com.clinbrain.mq.service.custom.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * 短信相关控制器
 *
 * @author zhuangxupeng
 * @date 2021年7月20日 13:38:47
 */
@RestController
@RequestMapping("sms")
public class SmsController {

    @Autowired
    private ISmsTemplateService smsService;

    @Autowired
    private V2Config config;

    /**
     * 发送短信
     * @param smsMessage
     * @return
     */
    @PostMapping("/send")
    public String send(@RequestBody SMSMessage smsMessage){
        if (null != smsMessage){
            smsService.sendSms(smsMessage);
            return "success";
        }
        return "fail";
    }

}
