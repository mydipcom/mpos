package com.mpos.model;

/**
 * The persistent class for the admin_user database table.
 * @author SivaSong
 */
public class PromotionModel {

	private int promotionId;

	private String promotionName;

	private String promotionRule;

	private String promotionType;
    
	private int way;
	
	private String startTime;

	private String endTime;

	private int paramOne;

	private String paramTwo;

	private boolean shared;

	private int priority;

	private int bindType;

	private String bindId;
    
	private String claId;
	
	private String gooId;
	
	private String menId;
	
	private boolean status;

	public int getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(int promotionId) {
		this.promotionId = promotionId;
	}

	public String getPromotionName() {
		return promotionName;
	}

	public void setPromotionName(String promotionName) {
		this.promotionName = promotionName;
	}

	public String getPromotionRule() {
		return promotionRule;
	}

	public void setPromotionRule(String promotionRule) {
		this.promotionRule = promotionRule;
	}

	public String getPromotionType() {
		return promotionType;
	}

	public void setPromotionType(String promotionType) {
		this.promotionType = promotionType;
	}
    
	public int getWay() {
		return way;
	}

	public void setWay(int way) {
		this.way = way;
	}
	
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
    
	public int getParamOne() {
		return paramOne;
	}

	public void setParamOne(int paramOne) {
		this.paramOne = paramOne;
	}

	public String getParamTwo() {
		return paramTwo;
	}

	public void setParamTwo(String paramTwo) {
		this.paramTwo = paramTwo;
	}

	public boolean isShared() {
		return shared;
	}

	public void setShared(boolean shared) {
		this.shared = shared;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getBindType() {
		return bindType;
	}

	public void setBindType(int bindType) {
		this.bindType = bindType;
	}

	public String getBindId() {
		return bindId;
	}

	public void setBindId(String bindId) {
		this.bindId = bindId;
	}
    
	public String getClaId() {
		return claId;
	}

	public void setClaId(String claId) {
		this.claId = claId;
	}

	public String getGooId() {
		return gooId;
	}

	public void setGooId(String gooId) {
		this.gooId = gooId;
	}

	public String getMenId() {
		return menId;
	}

	public void setMenId(String menId) {
		this.menId = menId;
	}
	
	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

}