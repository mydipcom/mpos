package com.mpos.model;

import java.io.Serializable;

public class MenuModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8847670497753749910L;
	private Integer id;
	private String title;
	private Integer pid;
	private String name;
	private Integer sort;
	private Integer styleType;
	private Integer storeId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public Integer getStyleType() {
		return styleType;
	}
	public void setStyleType(Integer styleType) {
		this.styleType = styleType;
	}
	public Integer getStoreId() {
		return storeId;
	}
	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}
	
	
}
