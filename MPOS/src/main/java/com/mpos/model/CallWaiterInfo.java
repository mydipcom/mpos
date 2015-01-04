package com.mpos.model;

import java.io.Serializable;

public class CallWaiterInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1103615436998822111L;
	/**
	 * 
	 */
	private String callMan;
	private String callTime;
	private Integer status;
	private Integer type;
	public String getCallMan() {
		return callMan;
	}
	public void setCallMan(String callMan) {
		this.callMan = callMan;
	}    
	public String getCallTime() {
		return callTime;
	}
	public void setCallTime(String callTime) {
		this.callTime = callTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	
}
