package com.clinbrain.mq.common.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "yantai")
@Data
public class YanTaiProperties {
    private String apiUrl;          //接口地址
    private String spCode;          //企业编号
    private String loginName;       //用户名称
    private String password;        //用户密码
    private String scheduleTime;    //预约发送时间，格式:yyyyMMddHHmmss,如‘20090901010101’，立即发送请填空
    private String f;               //提交时检测方式 1---提交号码中有效的号码仍正常发出短信，无效的号码在返回参数faillist中列出不为1或该参数不存在---提交号码中只要有无效的号码，那么所有的号码都不发出短信，所有的号码在返回参数faillist中列出
}
