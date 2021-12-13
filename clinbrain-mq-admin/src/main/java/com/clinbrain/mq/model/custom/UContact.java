package com.clinbrain.mq.model.custom;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.date.DateUtil;
import java.util.Date;

public class UContact implements Serializable {
    private static final long serialVersionUID = 1L;

	
	@ApiModelProperty(value = "")
	private Integer id;
	
	@ApiModelProperty(value = "用户名")
	private String contactName;
	
	@ApiModelProperty(value = "昵称，称呼")
	private String nickname;
	
	@ApiModelProperty(value = "")
	private String sex;
	
	@ApiModelProperty(value = "是否可用，1：可用")
	private Integer enabled;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	@ApiModelProperty(value = "")
	private Date createTime;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	@ApiModelProperty(value = "")
	private Date updateTime;
	
	@JsonProperty("id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id =  id;
	}
	@JsonProperty("contactName")
	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName =  contactName;
	}
	@JsonProperty("nickname")
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname =  nickname;
	}
	@JsonProperty("sex")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex =  sex;
	}
	@JsonProperty("enabled")
	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled =  enabled;
	}
	@JsonProperty("createTime")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime =  createTime;
	}
	@JsonProperty("updateTime")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime =  updateTime;
	}

														
	public UContact(Integer id,String contactName,String nickname,String sex,Integer enabled,Date createTime,Date updateTime) {
				
		this.id = id;
				
		this.contactName = contactName;
				
		this.nickname = nickname;
				
		this.sex = sex;
				
		this.enabled = enabled;
				
		this.createTime = createTime;
				
		this.updateTime = updateTime;
				
	}

	public UContact() {
	    super();
	}

	public String dateToStringConvert(Date date) {
		if(date!=null) {
			return DateUtil.format(date, "yyyy-MM-dd HH:mm:ss");
		}
		return "";
	}
	

}