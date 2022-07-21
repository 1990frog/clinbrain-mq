package com.clinbrain.mq.message.conf;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 *
 * @author Liaopan
 * @date 2021-12-22
 */
@ConfigurationProperties(prefix = "sms")
@Data
@Configuration
@Profile("hebei2")
public class Hebei2Properties {

    private String apiUrl;
    private String appKey;
    private String appSecret;
    private String spNumber = "";
    private String reportUrl = "";
    private String moUrl = "";
    private String attach = "";
}
