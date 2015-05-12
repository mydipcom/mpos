package com.mpos.dto;

import java.io.Serializable;

import com.mpos.commons.ConvertTools;


/**
 * The persistent class for the admin_user database table.
 * 
 */
public class TadminUser implements Serializable {
	private static final long serialVersionUID = 1L;

	private String adminId;	

	private String email;	
	
	private Integer roleId;

	private String password;	

	private boolean status;
	
	private String createdBy;
	
	private Long createdTime = System.currentTimeMillis();

	private String updatedBy;
	
	private Long updatedTime;
	
	private String code;
	
	@SuppressWarnings("unused")
	private String createdTimeStr;	
	
	@SuppressWarnings("unused")
	private String updatedTimeStr;
	
	@SuppressWarnings("unused")
	private String roleName;

	private TadminRole adminRole;
	
	private TadminInfo adminInfo; 
	
	private Integer storeId;
	

	public TadminUser(String email, Integer storeId) {
		this.email = email;
		this.storeId = storeId;
	}

	public TadminUser() {
	}
	public TadminUser(Long updatedTime, String code) {
		this.updatedTime = updatedTime;
		this.code = code;
	}
	public TadminUser(String adminId,String email,String password,TadminRole adminRole,boolean status) {
		this.adminId=adminId;
		this.email=email;
		this.password=password;
		this.adminRole=adminRole;
		this.status=status;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getRoleName() {
		return adminRole.getRoleName();
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getCreatedTimeStr() {
		return ConvertTools.longToDateString(createdTime);
	
	}

	public void setCreatedTimeStr(String createdTimeStr) {
		this.createdTimeStr = createdTimeStr;
	}

	public String getUpdatedTimeStr() {
		return ConvertTools.longToDateString(updatedTime);
	}
	
	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public void setUpdatedTimeStr(String updatedTimeStr) {
		this.updatedTimeStr = updatedTimeStr;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean getStatus() {
		return this.status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Long getUpdatedTime() {
		return this.updatedTime;
	}

	public void setUpdatedTime(Long updatedTime) {
		this.updatedTime = updatedTime;
	}

	public TadminRole getAdminRole() {
		return this.adminRole;
	}

	public void setAdminRole(TadminRole adminRole) {
		this.adminRole = adminRole;
	}

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}

	/**
	 * @return the adminId
	 */
	public String getAdminId() {
		return adminId;
	}

	/**
	 * @param adminId the adminId to set
	 */
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	/**
	 * @return the createdTime
	 */
	public Long getCreatedTime() {
		return createdTime;
	}

	/**
	 * @param createdTime the createdTime to set
	 */
	public void setCreatedTime(Long createdTime) {
		this.createdTime = createdTime;
	}

	/**
	 * @return the adminInfo
	 */
	public TadminInfo getAdminInfo() {
		return adminInfo;
	}

	/**
	 * @param adminInfo the adminInfo to set
	 */
	public void setAdminInfo(TadminInfo adminInfo) {
		this.adminInfo = adminInfo;
	}

}