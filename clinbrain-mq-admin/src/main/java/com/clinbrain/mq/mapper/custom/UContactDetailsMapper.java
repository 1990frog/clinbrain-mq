package com.clinbrain.mq.mapper.custom;

import com.clinbrain.mq.model.custom.UContactDetails;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UContactDetailsMapper {
    /**
     * @description 根据联系人ID列表查询具体的联系方式
     *
     * @author hexun
     * @param ids 联系人ID列表
     * @param contactType 联系方式
     * @return
     */
    List<UContactDetails> selectListByUsers(@Param("users")List<Integer> users, @Param("contactType")String contactType);

    /**
     * @description
     *
     * @author hexun
     * @param ids 分组ID列表
     * @param contactType 联系方式
     * @return
     */
    List<UContactDetails> selectListByGroups(@Param("groups")List<Integer> groups, @Param("contactType")String contactType);

}
