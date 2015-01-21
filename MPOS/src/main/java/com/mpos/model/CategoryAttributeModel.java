package com.mpos.model;

import java.util.List;

import com.mpos.dto.TattributeValue;
import com.mpos.dto.TproductAttribute;

public class CategoryAttributeModel {
	private Integer attributeId;
	private Integer categoryId;
	private String title;
	private Integer type;
	
	private Boolean required;
	private Integer sort;
	private List<TattributeValue>  attributeValue;
	
	private TproductAttribute productAttribute;
	
	public Integer getAttributeId() {
		return attributeId;
	}
	public void setAttributeId(Integer attributeId) {
		this.attributeId = attributeId;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	public Boolean getRequired() {
		return required;
	}
	public void setRequired(Boolean required) {
		this.required = required;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	public TproductAttribute getProductAttribute() {
		return productAttribute;
	}
	public void setProductAttribute(TproductAttribute productAttribute) {
		this.productAttribute = productAttribute;
	}
	public List<TattributeValue> getAttributeValue() {
		return attributeValue;
	}
	public void setAttributeValue(List<TattributeValue> attributeValue) {
		this.attributeValue = attributeValue;
	}
	
	
}
