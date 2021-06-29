package com.clinbrain.mq.mapper.auto;

import com.clinbrain.mq.model.auto.SysCity;
import com.clinbrain.mq.model.auto.SysCityExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysCityMapper {
    long countByExample(SysCityExample example);

    int deleteByExample(SysCityExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SysCity record);

    int insertSelective(SysCity record);

    List<SysCity> selectByExample(SysCityExample example);

    SysCity selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SysCity record, @Param("example") SysCityExample example);

    int updateByExample(@Param("record") SysCity record, @Param("example") SysCityExample example);

    int updateByPrimaryKeySelective(SysCity record);

    int updateByPrimaryKey(SysCity record);
}