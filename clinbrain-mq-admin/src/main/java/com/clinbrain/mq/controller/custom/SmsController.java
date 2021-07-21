package com.clinbrain.mq.controller.custom;

import com.clinbrain.mq.common.message.SMSMessage;
import com.clinbrain.mq.service.custom.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private SmsService smsService;

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
