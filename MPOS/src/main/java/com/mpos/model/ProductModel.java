package com.mpos.model;

import java.io.Serializable;
import java.util.List;

public class ProductModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2319296435459960534L;
	/**
	 * 
	 */
	private Integer productId;
	private String productName;
	private String shortDescr;
	private String fullDescr;
	private String unitName;
	private Float oldPrice;
	private Float price;
	private Integer sku;
	private boolean recommend;
	private Integer sort;
	private boolean status;
	private Integer menuId;
	
	private Object productNameLocale;
	private Object shortDescrLocale;
	private Object fullDescrLocale;
	
	private List<AttributeModel> attributes;
	
	private String[] promotions;
	
	private String[] images;

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getShortDescr() {
		return shortDescr;
	}

	public void setShortDescr(String shortDescr) {
		this.shortDescr = shortDescr;
	}

	public String getFullDescr() {
		return fullDescr;
	}

	public void setFullDescr(String fullDescr) {
		this.fullDescr = fullDescr;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public Float getOldPrice() {
		return oldPrice;
	}

	public void setOldPrice(Float oldPrice) {
		this.oldPrice = oldPrice;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Integer getSku() {
		return sku;
	}

	public void setSku(Integer sku) {
		this.sku = sku;
	}

	public boolean isRecommend() {
		return recommend;
	}

	public void setRecommend(boolean recommend) {
		this.recommend = recommend;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public Object getProductNameLocale() {
		return productNameLocale;
	}

	public void setProductNameLocale(Object productNameLocale) {
		this.productNameLocale = productNameLocale;
	}

	public Object getShortDescrLocale() {
		return shortDescrLocale;
	}

	public void setShortDescrLocale(Object shortDescrLocale) {
		this.shortDescrLocale = shortDescrLocale;
	}

	public Object getFullDescrLocale() {
		return fullDescrLocale;
	}

	public void setFullDescrLocale(Object fullDescrLocale) {
		this.fullDescrLocale = fullDescrLocale;
	}

	public List<AttributeModel> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<AttributeModel> attributes) {
		this.attributes = attributes;
	}

	public String[] getPromotions() {
		return promotions;
	}

	public void setPromotions(String[] promotions) {
		this.promotions = promotions;
	}

	public String[] getImages() {
		return images;
	}

	public void setImages(String[] images) {
		this.images = images;
	}
	
	
}
