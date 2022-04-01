package com.clinbrain.mq.message.conf;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Liaopan
 * @date 2021-12-22
 */
@ConfigurationProperties(prefix = "sms")
@Data
@Configuration
@ConditionalOnProperty(prefix = "spring.profiles.", name = "active", havingValue = "deyang")
public class DeyangProperties {

    private String apiUrl;
    private String ecName;
    private String apId;
    private String sign;
    private String secretKey;
    private String addSerial = "";
}
