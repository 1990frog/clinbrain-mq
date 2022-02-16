package com.clinbrain.mq.message.conf;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Liaopan on 2021-12-22.
 */
@ConfigurationProperties(prefix = "sms")
@Data
@Configuration
@ConditionalOnProperty(prefix = "spring.profiles.", name = "active", havingValue = "linshu")
public class LinshuProperties {

    private String apiUrl;
    private String cpCode;
    private String key;

    private String exCode = "";
}
