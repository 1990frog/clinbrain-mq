package com.clinbrain.mq.message.conf;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 *  * @author yuehl
 *  * @date 2022-11-16
 */
@ConfigurationProperties(prefix = "sms")
@Data
@Configuration
@ConditionalOnProperty(prefix = "spring.profiles.", name = "active", havingValue = "wenzhou2")
public class Wenzhou2Properties {

    private String sendSmsUrl;
    private String actionV;
    private String classV;
    private String company;
    private String token;
    private String mbid;
    private String qzdx;

}
