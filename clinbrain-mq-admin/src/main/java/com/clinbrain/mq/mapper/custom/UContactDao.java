package com.clinbrain.mq.mapper.custom;

import com.clinbrain.mq.model.custom.sms.ContactVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UContactDao {
    /**
     * 根据联系人id获取联系人信息
     * @param assignId
     * @return
     */
    List<ContactVo> getContactList(@Param("assignId") String[] assignId);

}
