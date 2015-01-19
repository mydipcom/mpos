package com.mpos.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * 分类属性信息
 * @author DavePu
 *
 */
public class TattributeValue implements Serializable {

	private static final long serialVersionUID = -1165577294139963628L;
	/**
	 * ID 主键唯一标识
	 */
	private Integer valueId;
	/**
	 * 分类属性关联的分类属性ID
	 */
	private Integer attributeId;	
	/**
	 * 属性内容
	 */
	private String value;
	/**
	 * 分类属性显示排序值，越小越先显示
	 */
	private Integer sort;
		
	
	private List<TlocalizedField> value_locale=new ArrayList<TlocalizedField>();	

	public TattributeValue() {}

	public TattributeValue(Integer attributeId,  String value, Integer sort) {
		this.attributeId = attributeId;
		this.value = value;		
		this.sort = sort;
	}

	public Integer getValueId() {
		return valueId;
	}

	public void setValueId(Integer valueId) {
		this.valueId = valueId;
	}

	public Integer getAttributeId() {
		return attributeId;
	}

	public void setAttributeId(Integer attributeId) {
		this.attributeId = attributeId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public List<TlocalizedField> getValue_locale() {
		return value_locale;
	}

	public void setValue_locale(List<TlocalizedField> value_locale) {
		this.value_locale = value_locale;
	}
		

}
