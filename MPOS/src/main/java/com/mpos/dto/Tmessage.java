package com.mpos.dto;

import java.io.Serializable;

import com.mpos.commons.ConvertTools;

public class Tmessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1319645823601242987L;
	
	private Integer id;
	private String msg;
	private String userName="";
	private String phone="";
	private Long createTime = System.currentTimeMillis();
	@SuppressWarnings("unused")
	private String time;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public String getTime() {
		return ConvertTools.longToDateString(createTime);
	}
	public void setTime(String time) {
		this.time = time;
	}

}
