package com.clinbrain.mq.common.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "aliyun-sms")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AliyunSmsProp {
    private String accessKeyId;
    private String secret;
    private String signName;
}
