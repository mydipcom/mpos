package com.mpos.model;

import java.io.Serializable;
/**
 * 
 * @author DavePu
 * @ClassName  AttributeModel 
 * @date 2014年12月25日下午3:01:52
 */
public class AttributeModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5184544357972159681L;
	private Integer attributeId;
	private String attributeTitle;
	private String attributeValue;
	private String attributePrice;
	private Object attributeValueLocale;
	private Object attributeTitleLocale;
	public Integer getAttributeId() {
		return attributeId;
	}
	public void setAttributeId(Integer attributeId) {
		this.attributeId = attributeId;
	}
	public String getAttributeValue() {
		return attributeValue;
	}
	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}
	public String getAttributePrice() {
		return attributePrice;
	}
	public void setAttributePrice(String attributePrice) {
		this.attributePrice = attributePrice;
	}
	public Object getAttributeValueLocale() {
		return attributeValueLocale;
	}
	public void setAttributeValueLocale(Object attributeValueLocale) {
		this.attributeValueLocale = attributeValueLocale;
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
	
	
}
