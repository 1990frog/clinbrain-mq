package com.clinbrain.mq.message.conf;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 *  * @author yuehl
 *  * @date 2022-02-24
 */
@ConfigurationProperties(prefix = "sms")
@Data
@Configuration
@ConditionalOnProperty(prefix = "spring.profiles.", name = "active", havingValue = "pengzhou")
public class PengzhouProperties {

    private String authorizeUrl;
    private String sendSmsUrl;
    private String ec_name;
    private String user_name;
    private String user_passwd;
    private String sign;

    private String serial;
}
