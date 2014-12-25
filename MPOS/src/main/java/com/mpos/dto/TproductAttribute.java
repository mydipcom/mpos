package com.mpos.dto;

public class TproductAttribute implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private TproductAttributeId id;
	
	private String content;
	
	private String price;



	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPrice() {
		return this.price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public TproductAttributeId getId() {
		return id;
	}

	public void setId(TproductAttributeId id) {
		this.id = id;
	}
	
	
	
}
