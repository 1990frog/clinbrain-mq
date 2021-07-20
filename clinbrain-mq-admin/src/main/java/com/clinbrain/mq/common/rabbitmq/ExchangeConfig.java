package com.clinbrain.mq.common.rabbitmq;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ Exchange
 */
@Configuration
public class ExchangeConfig {

    public static final String CLINBRAIN_AMQ_SMS_DIRECT = "clinbrain.amq.sms.direct";
    public static final String CLINBRAIN_AMQ_EMAIL_DIRECT = "clinbrain.amq.email.direct";

    @Bean(name = "clinbrain_amq_sms_direct")
    public Exchange clinbrainAmqSmsDirect(){
        return new DirectExchange(CLINBRAIN_AMQ_SMS_DIRECT);
    }

    @Bean(name = "clinbrain_amq_email_direct")
    public Exchange clinbrainAmqEmailDirect(){
        return new DirectExchange(CLINBRAIN_AMQ_EMAIL_DIRECT);
    }

}
