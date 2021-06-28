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

    public static final String BINGDINGS_KEY_SMS_INFORM = "inform.*.sms.#";
    public static final String BINGDINGS_KEY_EMAIL_INFORM = "inform.*.email.#";

    @Bean
    public Binding bindingQueueSmsInform(@Qualifier("queueSmsInform") Queue queue,
                                              @Qualifier("exchangeTopicsInform") Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(BINGDINGS_KEY_SMS_INFORM).noargs();
    }

    @Bean
    public Binding bindingQueueInform(@Qualifier("queueEmailInform") Queue queue,
                                              @Qualifier("exchangeTopicsInform") Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(BINGDINGS_KEY_EMAIL_INFORM).noargs();
    }


}
