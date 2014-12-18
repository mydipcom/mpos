package com.mpos.dto;

import java.io.Serializable;
/**
 * 商品分类信息
 * @author DavePu
 *
 */
public class Tcategory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5661624063398310122L;
	/**
	 * 商品分类ID
	 */
	private Integer categoryId;
	/**
	 * 商品分类名称
	 */
	private String name;
	/**
	 * 分类描述
	 */
	private String content;
	/**
	 * 分类当前状态：0禁用；1启用
	 */
	private Boolean status = true;
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public Tcategory(Integer categoryId, String name, String content,
			Boolean status) {
		this.categoryId = categoryId;
		this.name = name;
		this.content = content;
		this.status = status;
	}
	public Tcategory(Integer categoryId) {
		this.categoryId = categoryId;
	}
	
	public Tcategory() {}
	
	@Override
	public String toString() {
		return "Tcategory [categoryId=" + categoryId + ", name=" + name
				+ ", content=" + content + ", status=" + status + "]";
	}
	
	
	
}
