package com.mpos.dto;

import java.io.Serializable;
/**
 * 商品菜单信息
 * @author DavePu
 *
 */
public class Tmenu implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1815805538175828577L;
	/**
	 * 商品菜单ID
	 */
	private Integer menuId;
	/**
	 * 商品菜单显示名称
	 */
	private String title;
	
	
	private Object titleLocale;
	/**
	 * 父菜单ID
	 */
	private Integer pid;
	/**
	 * 用户当前状态：0禁用；1启用
	 */
	private Boolean status;
	/**
	 * 显示排序值，越小越先显示
	 */
	private Integer sort;
	
	private Integer styleType;
	private Integer storeId;
	
	public Integer getMenuId() {
		return menuId;
	}
	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
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
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	public Object getTitleLocale() {
		return titleLocale;
	}
	public void setTitleLocale(Object titleLocale) {
		this.titleLocale = titleLocale;
	}
	public Tmenu() {}
	public Tmenu(Integer menuId) {
		this.menuId = menuId;
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
	@Override
	public String toString() {
		return "Tmenu [menuId=" + menuId + ", title=" + title
				+ ", titleLocale=" + titleLocale + ", pid=" + pid + ", status="
				+ status + ", sort=" + sort + ", styleType=" + styleType + "]";
	}
	
}
