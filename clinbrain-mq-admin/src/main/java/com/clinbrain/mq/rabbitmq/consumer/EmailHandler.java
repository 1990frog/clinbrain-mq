package com.clinbrain.mq.rabbitmq.consumer;

import com.clinbrain.mq.common.config.rabbitmq.QueueConfig;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EmailHandler {

    @RabbitListener(queues = {QueueConfig.QUEUE_EMAIL_INFORM})
    public void receiveEmail(Object msg, Message message, Channel channel) {
        log.info("queue sms inform receive msg={}",msg);
    }

}
