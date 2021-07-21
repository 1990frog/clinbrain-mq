package com.clinbrain.mq.model.custom;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MessageModel {

    /**
     * 要处理的消息列表
     */
    private List<Long> uMqMessageIds;
}
