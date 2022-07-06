package com.clinbrain.mq.message.conf;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 *  * @author yuehl
 *  * @date 2022-03-16
 */
@ConfigurationProperties(prefix = "sms")
@Data
@Configuration
@ConditionalOnProperty(prefix = "spring.profiles.", name = "active", havingValue = "shiyan")
public class ShiyanProperties {

    private String sendSmsUrl;
    private String ecName;
    private String apId;
    private String secretKey;
    private String sign;

    private String addSerial;
}
