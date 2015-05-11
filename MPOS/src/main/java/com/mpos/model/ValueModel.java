package com.mpos.model;

import java.io.Serializable;

public class ValueModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9206568158829346184L;
	private Integer valueId;
	private Float price;
	private String value;
	private Integer sort;
	private Object valueLocale;
	public Integer getValueId() {
		return valueId;
	}
	public void setValueId(Integer valueId) {
		this.valueId = valueId;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Object getValueLocale() {
		return valueLocale;
	}
	public void setValueLocale(Object valueLocale) {
		this.valueLocale = valueLocale;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	
}
