package com.clinbrain.mq.common.message;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.clinbrain.mq.common.enums.MessagesGenreEnum;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Liaopan
 * @date 2021-07-15
 */
public abstract class BaseMessage implements IClinMqMessage{

    /**
     * 指定人联系方式,指定到用户的手机号，邮箱号 等等具体的。 多个用，分隔
     */
    private String[] assign;

    /**
     * 指定管理系统中的用户ID。 多个用，分隔
     */
    private String[] assignId;

    /**
     *  指定用户组。多个用，分隔
     */
    private String[] assignGroup;

    /**
     * 引用模板ID 或 code
     */
    private Long templateId;

    /**
     * 模板中的参数与数据
     */
    private List<String> templateParams;

    /**
     * 设置消息类型
     * @return MessagesGenreEnum 消息枚举类
     */
    @Override
    public abstract MessagesGenreEnum messageGenre();

    public byte[] serialize() {
        return ObjectUtil.serialize(this);
    }

    public List<String> getAssigns() {
        return new ArrayList<>();
    }

    public String[] getAssign() {
        return assign;
    }

    public void setAssign(String[] assign) {
        this.assign = assign;
    }

    public String[] getAssignId() {
        return assignId;
    }

    public void setAssignId(String[] assignId) {
        this.assignId = assignId;
    }

    public String[] getAssignGroup() {
        return assignGroup;
    }

    public void setAssignGroup(String[] assignGroup) {
        this.assignGroup = assignGroup;
    }

    public List<String> getTemplateParams() {
        return templateParams;
    }

    public void setTemplateParams(List<String> templateParams) {
        this.templateParams = templateParams;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }
}
