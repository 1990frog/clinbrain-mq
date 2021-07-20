package com.clinbrain.mq.common.config.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ Queue
 */
@Configuration
public class QueueConfig {

    public static final String CLINBRAIN_SMS_DEFAULT_QUEUE = "clinbrain.sms.default.queue";
    public static final String CLINBRAIN_EMAIL_DEFAULT_QUEUE = "clinbrain.email.default.queue";

    @Bean(name = "clinbrain_sms_default_queue")
    public Queue clinbrainSmsDefaultQueue(){
        return new Queue(CLINBRAIN_SMS_DEFAULT_QUEUE);
    }

    @Bean(name = "clinbrain_email_default_queue")
    public Queue clinbrainEmailDefaultQueue(){
        return new Queue(CLINBRAIN_EMAIL_DEFAULT_QUEUE);
    }


}
