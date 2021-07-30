package com.clinbrain.mq.rabbitmq.consumer;

import com.clinbrain.mq.common.rabbitmq.BindingsConfig;
import com.clinbrain.mq.common.rabbitmq.ExchangeConfig;
import com.clinbrain.mq.common.rabbitmq.QueueConfig;
import com.clinbrain.mq.mapper.custom.UContactDetailsMapper;
import com.clinbrain.mq.mapper.custom.UMqMessageMapper;
import com.clinbrain.mq.model.custom.MessageModel;
import com.clinbrain.mq.model.custom.UMqMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class EmailHandler {

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private UMqMessageMapper uMqMessageMapper;
    @Autowired
    private UContactDetailsMapper uContactDetailsMapper;

    @Value("${spring.mail.username}")
    private String username;

    @RabbitListener(queues = {QueueConfig.CLINBRAIN_EMAIL_DEFAULT_QUEUE})
    public void receiveEmail(Message message, Channel channel) throws IOException {
        String emailStr = new String(message.getBody(), "utf-8");
        log.info("queue email inform receive msg={}",emailStr);
        MessageModel model = new ObjectMapper().readValue(emailStr, MessageModel.class);
        List<UMqMessage> uMqMessages = uMqMessageMapper.selectListByIds(model.getUMqMessageIds());

        uMqMessages.forEach(item -> {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setSubject("邮件标题");
            msg.setFrom(username);
            msg.setTo(item.getAssignTo().split(","));    // 设置邮件接收者，可以有多个接收者
            msg.setSentDate(new Date());                        // 设置邮件发送日期
            msg.setText(item.getContent());                     // 设置邮件的正文
            try {
                javaMailSender.send(msg);
                item.setLog("消息发送成功");
                item.setUpdateTime(new Date());
                item.setStatus("发送成功");
                uMqMessageMapper.updateById(item);
            }catch (MailException e){
                item.setLog("消息发送失败");
                item.setUpdateTime(new Date());
                item.setStatus("发送失败");
                uMqMessageMapper.updateById(item);
            }
        });
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }

}
