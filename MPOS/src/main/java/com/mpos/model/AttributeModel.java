package com.mpos.model;

import java.io.Serializable;
import java.util.List;
/**
 * 
 * @author DavePu
 * @ClassName  AttributeModel 
 * @date 2014年12月25日下午3:01:52
 */
public class AttributeModel implements Serializable{
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -5184544357972159681L;
	private Boolean isRequired;
	private Integer attributeId;
	private Integer attributeType;
	private Integer sort;
	private String attributeTitle;
	private Object attributeTitleLocale;
	private List<ValueModel> attributeValue;
	public Integer getAttributeId() {
		return attributeId;
	}
	public void setAttributeId(Integer attributeId) {
		this.attributeId = attributeId;
	}
	public String getAttributeTitle() {
		return attributeTitle;
	}
	public void setAttributeTitle(String attributeTitle) {
		this.attributeTitle = attributeTitle;
	}
	public Object getAttributeTitleLocale() {
		return attributeTitleLocale;
	}
	public void setAttributeTitleLocale(Object attributeTitleLocale) {
		this.attributeTitleLocale = attributeTitleLocale;
	}
	public Boolean getIsRequired() {
		return isRequired;
	}
	public void setIsRequired(Boolean isRequired) {
		this.isRequired = isRequired;
	}
	public List<ValueModel> getAttributeValue() {
		return attributeValue;
	}
	public void setAttributeValue(List<ValueModel> attributeValue) {
		this.attributeValue = attributeValue;
	}
	public Integer getAttributeType() {
		return attributeType;
	}
	public void setAttributeType(Integer attributeType) {
		this.attributeType = attributeType;
	}
	
	
}
