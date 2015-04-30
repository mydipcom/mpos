package com.mpos.dto;

import java.io.Serializable;

import com.mpos.commons.ConvertTools;

public class Tstore implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7112236530171602975L;
	/**
	 * 打印一联
	 */
	public static final int  ONE = 1;
	/**
	 * 打印两联
	 */
	public static final int  TWO = 2;
	/**
	 * ID
	 */
	private Integer storeId;
	/**
	 * 店铺名称
	 */
	private String storeName="CampRay";
	/**
	 * 店铺logo
	 */
	private byte[] storeLogo;
	/**
	 * 店铺背景图
	 */
	private byte[] storeBackground;
	/**
	 * 客户端密码
	 */
	private String clientPwd="CampRay";
	/**
	 * 货币符号
	 */
	private String storeCurrency="$";
	/**
	 * 是否自动同步数据
	 */
	private Boolean autoSyncStatus=false;
	/**
	 * 状态
	 */
	private Boolean status=true;
	/**
	 * 简介
	 */
	private String content;
	/**
	 * 公钥
	 */
	private String publicKey="CampRay";
	/**
	 * 店铺多语言ID，多个以“,”分隔
	 */
	private String storeLangId="1";
	/**
	 * 订阅服务套餐ID
	 */
	private Integer serviceId;
	/**
	 * 服务到期时间
	 */
	private Long serviceDate = 0L;
	/**
	 * 注册时间
	 */
	private Long createTime = System.currentTimeMillis();
	/**
	 * 打印类型
	 */
	private Integer printType = 1;
	
	@SuppressWarnings("unused")
	private String createTimeStr;
	
	private String logoPath;
	private String backgroundPath;
	
	
	public String getCreateTimeStr() {
		return ConvertTools.longToDateString(createTime);
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	public String getLogoPath() {
		return logoPath;
	}
	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}
	public String getBackgroundPath() {
		return backgroundPath;
	}
	public void setBackgroundPath(String backgroundPath) {
		this.backgroundPath = backgroundPath;
	}
	public Tstore(Integer serviceId, Long serviceDate) {
		this.serviceId = serviceId;
		this.serviceDate = serviceDate;
	}
	public Tstore(Integer storeId, String storeName) {
		this.storeId = storeId;
		this.storeName = storeName;
	}
	public Tstore(String publicKey) {
		this.publicKey = publicKey;
	}
	public Tstore() {
	}
	public Integer getStoreId() {
		return storeId;
	}
	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public byte[] getStoreLogo() {
		return storeLogo;
	}
	public void setStoreLogo(byte[] storeLogo) {
		this.storeLogo = storeLogo;
	}
	public byte[] getStoreBackground() {
		return storeBackground;
	}
	public void setStoreBackground(byte[] storeBackground) {
		this.storeBackground = storeBackground;
	}
	public String getClientPwd() {
		return clientPwd;
	}
	public void setClientPwd(String clientPwd) {
		this.clientPwd = clientPwd;
	}
	public String getStoreCurrency() {
		return storeCurrency;
	}
	public void setStoreCurrency(String storeCurrency) {
		this.storeCurrency = storeCurrency;
	}
	public Boolean getAutoSyncStatus() {
		return autoSyncStatus;
	}
	public void setAutoSyncStatus(Boolean autoSyncStatus) {
		this.autoSyncStatus = autoSyncStatus;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPublicKey() {
		return publicKey;
	}
	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}
	public String getStoreLangId() {
		return storeLangId;
	}
	public void setStoreLangId(String storeLangId) {
		this.storeLangId = storeLangId;
	}
	public Integer getServiceId() {
		return serviceId;
	}
	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}
	public Long getServiceDate() {
		return serviceDate;
	}
	public void setServiceDate(Long serviceDate) {
		this.serviceDate = serviceDate;
	}
	public Integer getPrintType() {
		return printType;
	}
	public void setPrintType(Integer printType) {
		this.printType = printType;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	

}
