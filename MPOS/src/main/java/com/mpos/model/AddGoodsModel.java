package com.mpos.model;


public class AddGoodsModel {

	private Integer porductid;
	private Integer goodsnumber;
	private String productName;
	private String shortDescr;
	private String fullDescr;
	
	private Float oldPrice;
	private float price;
	private boolean recommend;
	private int sort;
	
	private String unitName;
	private Integer menuId;
	private Integer categoryId;
//	private MultipartFile[] files;
	public String getProductName() {
		return this.productName;
	}
	public Integer getPorductid() {
		return porductid;
	}
	public void setPorductid(Integer porductid) {
		this.porductid = porductid;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getShortDescr() {
		return this.shortDescr;
	}
	public void setShortDescr(String shortDescr) {
		this.shortDescr = shortDescr;
	}
	public String getFullDescr() {
		return this.fullDescr;
	}
	public void setFullDescr(String fullDescr) {
		this.fullDescr = fullDescr;
	}
	public Float getOldPrice() {
		return this.oldPrice;
	}
	public void setOldPrice(Float oldPrice) {
		this.oldPrice = oldPrice;
	}
	public float getPrice() {
		return this.price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public boolean isRecommend() {
		return this.recommend;
	}
	public void setRecommend(boolean recommend) {
		this.recommend = recommend;
	}
	public int getSort() {
		return this.sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	
	public Integer getMenuId() {
		return this.menuId;
	}
	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}
	public Integer getCategoryId() {
		return this.categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	/*
	public MultipartFile getFiles() {
		return this.files;
	}
	public void setFiles(MultipartFile files) {
		this.files = files;
	}

	*/
	public Integer getGoodsnumber() {
		return goodsnumber;
	}
	public void setGoodsnumber(Integer goodsnumber) {
		this.goodsnumber = goodsnumber;
	}
	/*
	public MultipartFile[] getFiles() {
		return files;
	}
	public void setFiles(MultipartFile[] files) {
		this.files = files;
	}
	*/
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}



	
	
	
}
