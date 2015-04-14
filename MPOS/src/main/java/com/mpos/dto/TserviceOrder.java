package com.mpos.dto;

import java.io.Serializable;

public class TserviceOrder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4698996227062389909L;
	private Integer serviceOrderId;
	private Integer serviceId;
	private Float price;
	private String email;
	private Long createTime;
	private Integer status;
	@Override
	public String toString() {
		return "TserviceOrder [serviceOrderId=" + serviceOrderId
				+ ", serviceId=" + serviceId + ", price=" + price + ", email="
				+ email + ", createTime=" + createTime + ", status=" + status
				+ "]";
	}
	public Integer getServiceOrderId() {
		return serviceOrderId;
	}
	public void setServiceOrderId(Integer serviceOrderId) {
		this.serviceOrderId = serviceOrderId;
	}
	public Integer getServiceId() {
		return serviceId;
	}
	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	

}
