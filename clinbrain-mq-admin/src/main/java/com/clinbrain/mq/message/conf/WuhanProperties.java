package com.clinbrain.mq.message.conf;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 *  湖北省人民医院短信配置
 * Created by Liaopan on 2021-12-08.
 */
@ConfigurationProperties(prefix = "sms")
@Data
@Configuration
@ConditionalOnProperty(prefix = "spring.profiles.", name = "active", havingValue = "wuhan")
public class WuhanProperties {

    private String jdbcUrl;
    private String username;
    private String password;

    public static final String ADD_SQL = "insert into smsdb.dbo.SendSms values(?,?,getdate(),1,null,null,0,null,null,null,null)";
}
