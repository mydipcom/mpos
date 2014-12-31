package com.mpos.dto;
import java.io.Serializable;
import java.util.Set;
/**
 * The persistent class for the admin_user database table.
 * 
 */
public class Tpromotion implements Serializable {

	private static final long serialVersionUID = 1L;

	private int promotionId;

	private String promotionName;
	
	private String promotionRule;

	private int promotionType;

	private long startTime;

	private long endTime;

	private int paramOne;

	private String paramTwo;

	private boolean shared;

	private Integer priority;

	private int bindType;

	private String bindId;

	private boolean status;

	private Set<Tproduct> tProducts;

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
	
	public int getPromotionType() {
		return promotionType;
	}

	public void setPromotionType(int promotionType) {
		this.promotionType = promotionType;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
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

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
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

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Set<Tproduct> gettProducts() {
		return tProducts;
	}

	public void settProducts(Set<Tproduct> tProducts) {
		this.tProducts = tProducts;
	}

}