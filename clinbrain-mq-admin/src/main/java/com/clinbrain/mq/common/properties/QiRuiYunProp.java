package com.clinbrain.mq.common.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "qiruiyun-sms")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QiRuiYunProp {
    private String url;
    private String dc;
    private String un;
    private String pw;
    private String sm;
    //private String da;
    private String tf;
    private String rf;
    private String rd;
}
