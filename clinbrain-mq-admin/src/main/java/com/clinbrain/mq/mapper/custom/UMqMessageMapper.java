package com.clinbrain.mq.mapper.custom;

import com.clinbrain.mq.model.custom.UMqMessage;

import java.util.List;

public interface UMqMessageMapper {
    int insertSelective(UMqMessage uMqMessage);

    List<UMqMessage> selectList(UMqMessage uMqMessage);

    List<UMqMessage> selectListByIds(List<Long> ids);

    int updateById(UMqMessage uMqMessage);
}
