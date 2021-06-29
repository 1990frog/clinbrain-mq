package com.clinbrain.mq.rabbitmq.consumer;

import com.clinbrain.mq.common.config.rabbitmq.QueueConfig;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SmsHandler {

    @RabbitListener(queues = {QueueConfig.QUEUE_SMS_INFORM})
    public void receiveSms(Message message, Channel channel) {
        try {
            byte[] body = message.getBody();
            String msg = new String(body,"utf-8");

            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);

            log.info("queue sms inform receive msg={}",msg);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
