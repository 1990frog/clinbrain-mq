package com.clinbrain.mq.controller.custom;

import com.alibaba.fastjson.JSON;
import com.clinbrain.mq.common.message.EmailMessage;
import com.clinbrain.mq.common.message.SMSMessage;
import com.clinbrain.mq.service.MassageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Slf4j
@RestController
public class MassageController {

    @Autowired
    private MassageService massageService;

    @PostMapping("/email")
    public Object sendEmail(@RequestBody EmailMessage emailMessage){
        log.info("[{}]-接收到消息:[{}]", LocalDateTime.now().toString(), JSON.toJSONString(emailMessage));
        return massageService.sendEmail(emailMessage);
    }

}
