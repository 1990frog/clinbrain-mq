package com.clinbrain.mq.model.custom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageModel {

    /**
     * 要处理的消息列表
     */
    private List<Long> uMqMessageIds;
}
