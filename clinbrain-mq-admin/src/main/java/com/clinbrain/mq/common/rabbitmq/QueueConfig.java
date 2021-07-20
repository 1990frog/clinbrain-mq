package com.clinbrain.mq.common.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ Queue
 */
@Configuration
public class QueueConfig {

    public static final String QUEUE_SMS_INFORM = "queue.sms.inform";
    public static final String QUEUE_EMAIL_INFORM = "queue.email.inform";

    @Bean
    public Queue queueSmsInform(){
        return new Queue(QUEUE_SMS_INFORM);
    }

    @Bean
    public Queue queueEmailInform(){
        return new Queue(QUEUE_EMAIL_INFORM);
    }


}
