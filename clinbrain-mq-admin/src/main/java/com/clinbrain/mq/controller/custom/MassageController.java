package com.clinbrain.mq.controller.custom;

import com.clinbrain.mq.common.message.EmailMessage;
import com.clinbrain.mq.common.message.SMSMessage;
import com.clinbrain.mq.service.MassageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MassageController {

    @Autowired
    private MassageService massageService;

    //@JsonCreator
    @PostMapping("/email")
    public void sendEmail(@RequestBody EmailMessage emailMessage){
        massageService.sendEmail(emailMessage);
    }

    @PostMapping("/sms")
    public void sendSms(@RequestBody SMSMessage smsMessage){
        massageService.sendSms(smsMessage);
    }

}
