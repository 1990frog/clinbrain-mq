package com.clinbrain.mq.common.config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ Exchange Bindings Queue
 */
@Configuration
public class BindingsConfig {

    public static final String INFORM_SMS_DEFAULT = "inform.sms.default";
    public static final String INFORM_EMAIL_DEFAULT = "inform.email.default";

    @Bean
    public Binding bindingSms(@Qualifier("clinbrain_sms_default_queue") Queue queue,
                                              @Qualifier("clinbrain_amq_sms_direct") Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(INFORM_SMS_DEFAULT).noargs();
    }

    @Bean
    public Binding bindingEmail(@Qualifier("clinbrain_email_default_queue") Queue queue,
                                              @Qualifier("clinbrain_amq_email_direct") Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(INFORM_EMAIL_DEFAULT).noargs();
    }


}
