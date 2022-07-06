package com.clinbrain.mq.message.conf;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 南阳医专配置
 * Created by Liaopan on 2022-07-05.
 */
@ConfigurationProperties(prefix = "sms")
@Data
@Configuration
@ConditionalOnProperty(prefix = "spring.profiles.", name = "active", havingValue = "nanyang")
public class NanyangProperties {

    private String apiUrl;
    private String spId;
    private String authCode;
    private String authKey;
    private String xAppID;
    private String xAppKey;
    private String apiName;

    private String extport = "";
}
