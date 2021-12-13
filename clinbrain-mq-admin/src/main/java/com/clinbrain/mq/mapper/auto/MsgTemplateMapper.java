package com.clinbrain.mq.mapper.auto;


import java.util.List;

import com.clinbrain.mq.model.custom.UMsgTemplateExample;
import com.clinbrain.mq.model.custom.sms.UMsgTemplate;
import org.apache.ibatis.annotations.Param;

/**
 * 信息模板管理 MsgTemplateMapper
 * @author clinbrain_自动生成
 * @email ${email}
 * @date 2021-12-09 14:28:10
 */
public interface MsgTemplateMapper {
      	   	      	      	      	      
    long countByExample(UMsgTemplateExample example);

    int deleteByExample(UMsgTemplateExample example);
		
    int deleteByPrimaryKey(Integer id);
		
    int insert(UMsgTemplate record);

    int insertSelective(UMsgTemplate record);

    List<UMsgTemplate> selectByExample(UMsgTemplateExample example);
		
    UMsgTemplate selectByPrimaryKey(Integer id);
		
    int updateByExampleSelective(@Param("record") UMsgTemplate record, @Param("example") UMsgTemplateExample example);

    int updateByExample(@Param("record") UMsgTemplate record, @Param("example") UMsgTemplateExample example);
		
    int updateByPrimaryKeySelective(UMsgTemplate record);

    int updateByPrimaryKey(UMsgTemplate record);
  	  	
}