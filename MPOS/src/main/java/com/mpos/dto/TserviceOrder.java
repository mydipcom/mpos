package com.mpos.dto;

import java.io.Serializable;

import com.mpos.commons.ConvertTools;

public class TserviceOrder implements Serializable {
	/**
	 * 创建订单成功
	 */
	public static final int CREATE_ORDER =0;
	
	/**
	 * 等待客户付款
	 */
	public static final int WAIT_BUYER_PAY =1;
	/**
	 * 等待发货
	 */
	public static final int WAIT_SELLER_SEND_GOODS =4;
	
	/**
	 * 等待客户确认收货
	 */
	public static final int WAIT_BUYER_CONFIRM_GOODS =2;
	
	/**
	 * 交易完成
	 */
	public static final int TRADE_FINISHED =3;

	/**
	 * 
	 */
	private static final long serialVersionUID = -4698996227062389909L;
	private Integer serviceOrderId;
	private String orderNum;
	private Tservice serviceId;
	private Float price;
	private String email;
	private Long createTime;
	private Integer status = CREATE_ORDER;
	@SuppressWarnings("unused")
	private String createTimeStr;
	@SuppressWarnings("unused")
	private String serviceName;
	
	public String getCreateTimeStr() {
		return ConvertTools.longToDateString(createTime);
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

}
