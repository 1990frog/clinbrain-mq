package com.clinbrain.mq.admin.mapper.auto;

import com.clinbrain.mq.admin.model.auto.TsysPermission;
import com.clinbrain.mq.admin.model.auto.TsysPermissionExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface TsysPermissionMapper {
    long countByExample(TsysPermissionExample example);

    int deleteByExample(TsysPermissionExample example);

    int deleteByPrimaryKey(String id);

    int insert(TsysPermission record);

    int insertSelective(TsysPermission record);

    List<TsysPermission> selectByExample(TsysPermissionExample example);

    TsysPermission selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TsysPermission record, @Param("example") TsysPermissionExample example);

    int updateByExample(@Param("record") TsysPermission record, @Param("example") TsysPermissionExample example);

    int updateByPrimaryKeySelective(TsysPermission record);

    int updateByPrimaryKey(TsysPermission record);
}