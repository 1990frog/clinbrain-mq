package com.clinbrain.mq.mapper.custom;

import com.clinbrain.mq.model.custom.sms.ContactVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UGroupsDao {

    /**
     * 根据组id获取联系人信息
     * @param groupId
     * @return
     */
    List<ContactVo> getContactList(@Param("groupId") String[] groupId);

}
