package com.clinbrain.mq.rabbitmq.consumer;

import com.clinbrain.mq.common.rabbitmq.QueueConfig;
import com.clinbrain.mq.mapper.custom.UContactDetailsMapper;
import com.clinbrain.mq.mapper.custom.UMqMessageMapper;
import com.clinbrain.mq.model.custom.MessageModel;
import com.clinbrain.mq.model.custom.UMqMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
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

        //uMqMessages.forEach(this::sendSimpleEmail);
        uMqMessages.forEach(this::sendHtmlEmail);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }

    /**
     * 发送普通文字邮件
     */
    private void sendSimpleEmail(UMqMessage uMqMessage){
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setSubject("信息提醒");
        msg.setFrom(username);
        msg.setTo(uMqMessage.getAssignTo().split(","));      // 设置邮件接收者，可以有多个接收者
        msg.setSentDate(new Date());                                // 设置邮件发送日期
        msg.setText(uMqMessage.getContent());                       // 设置邮件的正文
        try {
            javaMailSender.send(msg);
            uMqMessage.setLog("消息发送成功");
            uMqMessage.setUpdateTime(new Date());
            uMqMessage.setStatus("发送成功");
            uMqMessageMapper.updateById(uMqMessage);
        }catch (MailException e){
            uMqMessage.setLog("消息发送失败");
            uMqMessage.setUpdateTime(new Date());
            uMqMessage.setStatus("发送失败");
            uMqMessageMapper.updateById(uMqMessage);
        }
    }

    /**
     * 发送html模板邮件
     */
    private void sendHtmlEmail(UMqMessage uMqMessage){
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(uMqMessage.getAssignTo().split(","));
            helper.setFrom(username);
            helper.setSubject("信息提醒");
            helper.setText(uMqMessage.getContent(), true);

            String alarmIconName = "success.png";
            ClassPathResource img = new ClassPathResource(alarmIconName);
            helper.addInline("icon-alarm", img);
            javaMailSender.send(message);

            uMqMessage.setLog("消息发送成功");
            uMqMessage.setUpdateTime(new Date());
            uMqMessage.setStatus("发送成功");
            uMqMessageMapper.updateById(uMqMessage);
        }catch (MessagingException e){
            uMqMessage.setLog("消息发送失败");
            uMqMessage.setUpdateTime(new Date());
            uMqMessage.setStatus("发送失败");
            uMqMessageMapper.updateById(uMqMessage);
        }
    }
}
