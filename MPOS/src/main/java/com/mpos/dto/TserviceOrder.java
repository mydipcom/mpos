package com.mpos.dto;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TserviceOrder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4698996227062389909L;
	private Integer serviceOrderId;
	private Tservice serviceId;
	private Float price;
	private String email;
	private Long createTime;
	private Boolean status;
	@SuppressWarnings("unused")
	private String createTimeStr;
	@SuppressWarnings("unused")
	private String serviceName;
	
	public String getCreateTimeStr() {
		if(createTime!=null){
			Date date=new Date(createTime);
			SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			return sdf.format(date);		
			}else
			return null;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public String getServiceName() {
		return serviceId.getServiceName();
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public Integer getServiceOrderId() {
		return serviceOrderId;
	}
	public void setServiceOrderId(Integer serviceOrderId) {
		this.serviceOrderId = serviceOrderId;
	}

	public Tservice getServiceId() {
		return serviceId;
	}
	public void setServiceId(Tservice serviceId) {
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
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	

}
