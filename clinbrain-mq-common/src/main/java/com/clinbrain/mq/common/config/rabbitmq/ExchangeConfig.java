package com.clinbrain.mq.common.config.rabbitmq;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ Exchange
 */
@Configuration
public class ExchangeConfig {

    public static final String EXCHANGE_TOPICS_INFORM = "exchange.topics.inform";

    @Bean
    public Exchange exchangeTopicsInform(){
        return new TopicExchange(EXCHANGE_TOPICS_INFORM);
    }

}
