package com.clinbrain.mq.mapper.custom;


import com.clinbrain.mq.model.custom.UContact;
import com.clinbrain.mq.model.custom.UContactExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *  UContactMapper
 * @author fuce_自动生成
 * @email ${email}
 * @date 2021-12-09 13:37:41
 */
public interface UContactMapper {
      	   	      	      	      	      	      	      	      
    long countByExample(UContactExample example);

    int deleteByExample(UContactExample example);
		
    int deleteByPrimaryKey(Integer id);
		
    int insert(UContact record);

    int insertSelective(UContact record);

    List<UContact> selectByExample(UContactExample example);
		
    UContact selectByPrimaryKey(Integer id);
		
    int updateByExampleSelective(@Param("record") UContact record, @Param("example") UContactExample example);

    int updateByExample(@Param("record") UContact record, @Param("example") UContactExample example); 
		
    int updateByPrimaryKeySelective(UContact record);

    int updateByPrimaryKey(UContact record);
  	  	
}