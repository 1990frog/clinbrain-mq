package com.clinbrain.mq.mapper.custom;

import com.clinbrain.mq.model.custom.sms.UMsgTemplate;
import org.apache.ibatis.annotations.Param;

public interface UMsgTemplateDao {

    UMsgTemplate getTemplate(@Param("templateId") Long templateId);

}
