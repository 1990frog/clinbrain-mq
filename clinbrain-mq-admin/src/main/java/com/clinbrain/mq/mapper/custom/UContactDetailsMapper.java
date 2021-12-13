package com.clinbrain.mq.mapper.custom;

import com.clinbrain.mq.model.custom.UContactDetails;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UContactDetailsMapper {
    /**
     * @description 根据联系人ID列表查询具体的联系方式
     *
     * @author hexun
     * @param users 联系人ID列表
     * @param contactType 联系方式
     * @return
     */
    List<UContactDetails> selectListByUsers(@Param("users")List<Integer> users, @Param("contactType")String contactType);

    /**
     * @description
     *
     * @author hexun
     * @param groups 分组ID列表
     * @param contactType 联系方式
     * @return
     */
    List<UContactDetails> selectListByGroups(@Param("groups")List<Integer> groups, @Param("contactType")String contactType);

    /**
     *  根据联系人ID查找联系方式
     * @param contactId
     * @return
     */
    List<UContactDetails> selectListByContactId(@Param("contactId") Integer contactId);

    /**
     * 批量插入保存
     * @param details
     * @return
     */
    long insertBatch(@Param("details") List<UContactDetails> details);

    long deleteById(@Param("id") Integer id);
}
