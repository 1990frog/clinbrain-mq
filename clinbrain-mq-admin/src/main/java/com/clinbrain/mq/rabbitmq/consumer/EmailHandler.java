package com.clinbrain.mq.rabbitmq.consumer;

import cn.hutool.core.util.StrUtil;
import com.clinbrain.mq.common.rabbitmq.QueueConfig;
import com.clinbrain.mq.mapper.custom.UContactDetailsMapper;
import com.clinbrain.mq.mapper.custom.UMqMessageMapper;
import com.clinbrain.mq.model.custom.EmailSendEntity;
import com.clinbrain.mq.model.custom.MessageModel;
import com.clinbrain.mq.model.custom.UMqMessage;
import com.clinbrain.mq.util.EmailHandUtil;
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
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class EmailHandler {

    @Autowired
    private EmailHandUtil emailHandUtil;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private UMqMessageMapper uMqMessageMapper;

    @Value("${spring.mail.username}")
    private String username;

    @RabbitListener(queues = {QueueConfig.CLINBRAIN_EMAIL_DEFAULT_QUEUE})
    public void receiveEmail(Message message, Channel channel) throws IOException {
        String emailStr = new String(message.getBody(), "utf-8");
        log.info("queue email inform receive msg={}",emailStr);
        MessageModel model = new ObjectMapper().readValue(emailStr, MessageModel.class);
        List<UMqMessage> uMqMessages = uMqMessageMapper.selectListByIds(model.getUMqMessageIds());

        uMqMessages.forEach(messageItem ->{
            EmailSendEntity entity = new EmailSendEntity();
            entity.setFrom(username);
            entity.setTo(Arrays.asList("hexun@clinbrain.com"));
            entity.setTitle("附件测试标题");
            try{
                String resp;
                if(StrUtil.isBlank(messageItem.getAttachPaths())){
                    emailHandUtil.sendMixedEmail(entity, Optional.empty());
                }else{
                    List<String> paths = Arrays.asList(messageItem.getAttachPaths().split("\\|\\|"));
                    emailHandUtil.sendMixedEmail(entity,Optional.ofNullable(paths));
                }

                messageItem.setLog("邮件发送成功");
                messageItem.setUpdateTime(new Date());
                messageItem.setStatus("发送成功");
                uMqMessageMapper.updateById(messageItem);
            }catch (Exception e){
                messageItem.setLog("消息发送失败:" + e.getMessage());
                messageItem.setUpdateTime(new Date());
                messageItem.setStatus("发送失败");
                uMqMessageMapper.updateById(messageItem);
            }
        });

        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }
}
