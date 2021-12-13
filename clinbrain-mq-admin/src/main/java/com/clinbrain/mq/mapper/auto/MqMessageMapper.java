package com.clinbrain.mq.mapper.auto;

import com.clinbrain.mq.model.custom.UMqMessage;
import com.clinbrain.mq.model.custom.UMqMessageExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *  MqMessageMapper
 * @author clinbrain_自动生成
 * @email ${email}
 * @date 2021-12-09 14:27:38
 */
public interface MqMessageMapper {

    long countByExample(UMqMessageExample example);

    int deleteByExample(UMqMessageExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UMqMessage record);

    int insertSelective(UMqMessage record);

    List<UMqMessage> selectByExample(UMqMessageExample example);

    UMqMessage selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UMqMessage record, @Param("example") UMqMessageExample example);

    int updateByExample(@Param("record") UMqMessage record, @Param("example") UMqMessageExample example);
		
    int updateByPrimaryKeySelective(UMqMessage record);

    int updateByPrimaryKey(UMqMessage record);
  	  	
}