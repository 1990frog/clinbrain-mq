package com.clinbrain.mq.message.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix = "sms")
@Data
public class YanTaiProperties {
    private String apiUrl;          //接口地址
    private String spCode;          //企业编号
    private String loginName;       //用户名称
    private String password;        //用户密码
    private String scheduleTime;    //预约发送时间，格式:yyyyMMddHHmmss,如‘20090901010101’，立即发送请填空
    private String extendAccessNum; //接入号扩展号（默认不填，扩展号为数字，扩展位数由当前所配的接入号长度决定，整个接入号最长20位）
    private String f;               //提交时检测方式 1---提交号码中有效的号码仍正常发出短信，无效的号码在返回参数faillist中列出不为1或该参数不存在---提交号码中只要有无效的号码，那么所有的号码都不发出短信，所有的号码在返回参数faillist中列出
    private boolean mustFill;       //是否需要填充
    private int count;              //最大允许消息长度
    private String fillChar;        //长度不够时结尾填充字符
}
