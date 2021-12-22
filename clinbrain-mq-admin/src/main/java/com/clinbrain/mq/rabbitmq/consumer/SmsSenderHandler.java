package com.clinbrain.mq.rabbitmq.consumer;

import cn.hutool.json.JSONUtil;
import com.clinbrain.mq.common.conf.SmsSenderBeanConfig;
import com.clinbrain.mq.common.conf.V2Config;
import com.clinbrain.mq.message.conf.YanTaiProperties;
import com.clinbrain.mq.common.properties.QiRuiYunProp;
import com.clinbrain.mq.common.rabbitmq.QueueConfig;
import com.clinbrain.mq.mapper.custom.UMqMessageMapper;
import com.clinbrain.mq.message.ISmsSender;
import com.clinbrain.mq.message.MessageSendStatus;
import com.clinbrain.mq.message.SMSException;
import com.clinbrain.mq.model.custom.MqMessageObject;
import com.clinbrain.mq.model.custom.UMqMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Component
public class SmsSenderHandler {

    @Autowired
    private SmsSenderBeanConfig smsSenderBeanConfig;

    @Autowired
    private UMqMessageMapper uMqMessageMapper;

    @Value("${spring.profiles.active}")
    private String activeConfig;

    ObjectMapper objectMapper = new ObjectMapper();


    @RabbitListener(queues = {QueueConfig.CLINBRAIN_SMS_DEFAULT_QUEUE})
    public void receiveSms(Message message, Channel channel) throws IOException {
        try {
            byte[] body = message.getBody();
            String msg = new String(body, StandardCharsets.UTF_8);
            log.info("queue sms inform receive msg: {}",msg);
            MqMessageObject messageObject = objectMapper.readValue(msg, MqMessageObject.class);
            log.info("transform umessage: {}", messageObject);

            final ISmsSender smsSender = smsSenderBeanConfig.createSmsSender(activeConfig);
            final UMqMessage uMqMessage = messageObject.getUMqMessage();
            try {
                smsSender.sendSms(messageObject);
                uMqMessage.setStatus(MessageSendStatus.SUCCESS.getMsg());
            }catch (Exception e) {
                log.error("发送短信失败", e);
                uMqMessage.setStatus(MessageSendStatus.ERROR.getMsg());
                uMqMessage.setLog(e.getMessage());
            }finally {
                saveMesageInfo(uMqMessage);
            }
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (Exception e) {
            if (message.getMessageProperties().getRedelivered()) {
                log.error("消息已重复处理失败,拒绝再次接收,error msg={}",e.getMessage());
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
            } else {
                log.error("消息处理失败,再次返回队列,error msg={}",e.getMessage());
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            }

        }
    }

    private void saveMesageInfo(UMqMessage message) {
        message.setUpdateTime(new Date());
        uMqMessageMapper.updateById(message);
    }
}
