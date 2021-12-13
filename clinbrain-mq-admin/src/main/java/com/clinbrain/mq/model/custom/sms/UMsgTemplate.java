package com.clinbrain.mq.model.custom.sms;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.date.DateUtil;
import java.util.Date;

public class UMsgTemplate implements Serializable {
    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "模板ID,发送")
    private Long id;

    @ApiModelProperty(value = "模板code 标识")
    private String templateCode;

    @ApiModelProperty(value = "模板类型： 短信（sms），邮件（email）...")
    private String templateGenre;

    @ApiModelProperty(value = "模板内容，默认使用{0}，{1}占位符")
    private String templateContent;

    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id =  id;
    }
    @JsonProperty("templateCode")
    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode =  templateCode;
    }
    @JsonProperty("templateGenre")
    public String getTemplateGenre() {
        return templateGenre;
    }

    public void setTemplateGenre(String templateGenre) {
        this.templateGenre =  templateGenre;
    }
    @JsonProperty("templateContent")
    public String getTemplateContent() {
        return templateContent;
    }

    public void setTemplateContent(String templateContent) {
        this.templateContent =  templateContent;
    }


    public UMsgTemplate(Long id,String templateCode,String templateGenre,String templateContent) {

        this.id = id;

        this.templateCode = templateCode;

        this.templateGenre = templateGenre;

        this.templateContent = templateContent;

    }

    public UMsgTemplate() {
        super();
    }



}