package com.clinbrain.mq.common.rabbitmq;

import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConnectionConfig {

    private final RabbitProperties rabbitProperties;

    public RabbitConnectionConfig(RabbitProperties rabbitProperties) {
        this.rabbitProperties = rabbitProperties;
    }

    @Bean
    @ConditionalOnMissingBean
    public ConnectionFactory connectionFactory(){
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(rabbitProperties.getHost());
        factory.setPort(rabbitProperties.getPort());
        factory.setUsername(rabbitProperties.getUsername());
        factory.setPassword(rabbitProperties.getPassword());
        factory.setVirtualHost(rabbitProperties.getVirtualHost());
        return factory;
    }

//    @Bean
//    public RabbitTemplate emailTemplate(){
//        RabbitTemplate template = new RabbitTemplate();
//
//        template.setBeanName("emailTemplate");
//        template.setExchange(ExchangeConfig.CLINBRAIN_AMQ_EMAIL_DIRECT);
//        template.setRoutingKey(BindingsConfig.INFORM_EMAIL_DEFAULT);
//        return template;
//    }

}
