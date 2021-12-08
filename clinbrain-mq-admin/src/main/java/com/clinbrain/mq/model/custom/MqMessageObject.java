package com.clinbrain.mq.model.custom;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 发送到rabbitmq上的数据结构
 * @author Liaopan
 * @date 2021-12-06
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MqMessageObject {

    /**
     * 电话号码
     */
    private String phoneNumber;
    /**
     * 模板code
     */
    private String templateCode;
    /**
     * 模板参数
     */
    private List<String> templateParams;
    /**
     * 数据库日志记录
     */
    @JsonProperty("uMqMessage")
    private UMqMessage uMqMessage;
}
