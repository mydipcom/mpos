package com.mpos.dto;

import java.io.Serializable;
/**
 * 分类属性信息
 * @author DavePu
 *
 */
public class TcategoryAttribute implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8295926324141271224L;
	/**
	 * ID 主键唯一标识
	 */
	private Integer attributeId;
	/**
	 * 分类属性关联的分类ID
	 */
	private Tcategory categoryId;
	/**
	 *分类属性页面显示名称
	 */
	private String title;
	/**
	 * 类型 0 输入框，1单选框，2复选框，3 下拉框
	 */
	private Integer type;
	/**
	 * 属性内容，当type值为0时，可设置分类属性的默认值，
	 * 如果type值不为0，则为多个选项的值的联合字符串，
	 * 以分号分隔。
	 */
	private String content;
	/**
	 * 分类属性显示排序值，越小越先显示
	 */
	private Integer sort;

	public Integer getAttributeId() {
		return attributeId;
	}

	public void setAttributeId(Integer attributeId) {
		this.attributeId = attributeId;
	}

	public Tcategory getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Tcategory categoryId) {
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	

	public TcategoryAttribute() {}

	public TcategoryAttribute(Integer attributeId, Tcategory categoryId,
			String title, Integer type, String content, Integer sort) {
		this.attributeId = attributeId;
		this.categoryId = categoryId;
		this.title = title;
		this.type = type;
		this.content = content;
		this.sort = sort;
	}

	@Override
	public String toString() {
		return "TcategoryAttribute [attributeId=" + attributeId
				+ ", categoryId=" + categoryId + ", title=" + title + ", type="
				+ type + ", content=" + content + ", sort=" + sort + "]";
	}

}
