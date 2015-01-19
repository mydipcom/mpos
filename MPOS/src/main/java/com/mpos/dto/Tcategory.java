package com.mpos.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
	 * 商品属性组类型：0规格属性， 1订单属性
	 */
	private Integer type;
	
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
	
	
	private List<TlocalizedField> categoryName_locale=new ArrayList<TlocalizedField>();
	private List<TlocalizedField> categoryDescr_locale=new ArrayList<TlocalizedField>();
	
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
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}		
	
	public List<TlocalizedField> getCategoryName_locale() {
		return categoryName_locale;
	}
	public void setCategoryName_locale(List<TlocalizedField> categoryName_locale) {
		this.categoryName_locale = categoryName_locale;
	}
	public List<TlocalizedField> getCategoryDescr_locale() {
		return categoryDescr_locale;
	}
	public void setCategoryDescr_locale(List<TlocalizedField> categoryDescr_locale) {
		this.categoryDescr_locale = categoryDescr_locale;
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
