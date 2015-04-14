package com.mpos.dto;

import java.io.Serializable;

public class Tservice implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4697663381252088922L;
	/**
	 * ID
	 */
	private Integer serviceId;
	/**
	 * 服务名称
	 */
	private String serviceName;
	/**
	 * 费用
	 */
	private Float servicePrice;
	/**
	 * 简介
	 */
	private String content;
	/**
	 * 有效时间 天数
	 */
	private Integer validDays;
	/**
	 * 状态
	 */
	private Integer status;
	/**
	 * 角色ID
	 */
	private Integer roleId;
	public Integer getServiceId() {
		return serviceId;
	}
	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public Float getServicePrice() {
		return servicePrice;
	}
	public void setServicePrice(Float servicePrice) {
		this.servicePrice = servicePrice;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getValidDays() {
		return validDays;
	}
	public void setValidDays(Integer validDays) {
		this.validDays = validDays;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	

}
