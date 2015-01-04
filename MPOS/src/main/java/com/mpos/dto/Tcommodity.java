package com.mpos.dto;

import java.io.Serializable;
import java.util.Set;

public class Tcommodity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7716765041202361132L;
	private Integer id;
	private String productName;
	private String shortDescr;
	private String fullDescr;
	private String unitName;
	private Float oldPrice;
	private float price;
	private Integer sku;
	private boolean recommend;
	private int sort;
	private boolean status;
	private Tmenu tmenu;
	private Tcategory tcategory;
	
	private Set<TproductImage> images;
	
	private Set<TgoodsAttribute> attributes;
	
	private Set<Tpromotion> promotions;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
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

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Tmenu getTmenu() {
		return tmenu;
	}

	public void setTmenu(Tmenu tmenu) {
		this.tmenu = tmenu;
	}

	public Tcategory getTcategory() {
		return tcategory;
	}

	public void setTcategory(Tcategory tcategory) {
		this.tcategory = tcategory;
	}

	public Set<TproductImage> getImages() {
		return images;
	}

	public void setImages(Set<TproductImage> images) {
		this.images = images;
	}

	public Set<TgoodsAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(Set<TgoodsAttribute> attributes) {
		this.attributes = attributes;
	}

	public Set<Tpromotion> getPromotions() {
		return promotions;
	}

	public void setPromotions(Set<Tpromotion> promotions) {
		this.promotions = promotions;
	}
	
}
