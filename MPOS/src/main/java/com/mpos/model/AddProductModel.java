package com.mpos.model;

import java.util.ArrayList;
import java.util.List;

import com.mpos.dto.Tcategory;
import com.mpos.dto.TgoodsAttribute;
import com.mpos.dto.TlocalizedField;
import com.mpos.dto.Tmenu;

public class AddProductModel implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int productId;
	private String productName;
	private String shortDescr;
	private String fullDescr;
	private String unitName;
	private Float oldPrice;
	private Float price;
	private Integer sku;
	private boolean recommend=false;
	private Integer sort;
	private boolean status=true;
	private Tmenu menu;
	private Tcategory attributeGroup;
	private Integer specid;
	private Integer storeId;
	
	private List<TlocalizedField> productName_locale=new ArrayList<TlocalizedField>();
	private List<TlocalizedField> shortDescr_locale=new ArrayList<TlocalizedField>();
	private List<TlocalizedField> fullDescr_locale=new ArrayList<TlocalizedField>();
	private List<TlocalizedField> unitName_locale=new ArrayList<TlocalizedField>();
		
	private List<TgoodsAttribute> attributes=new ArrayList<TgoodsAttribute>();
			
	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	
	public Integer getSpecid() {
		return specid;
	}

	public void setSpecid(Integer specid) {
		this.specid = specid;
	}

	

	public Integer getSku() {
		return sku;
	}

	public void setSku(Integer sku) {
		this.sku = sku;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}	

	public Tcategory getAttributeGroup() {
		return attributeGroup;
	}

	public void setAttributeGroup(Tcategory attributeGroup) {
		this.attributeGroup = attributeGroup;
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

	public boolean isRecommend() {
		return recommend;
	}

	public void setRecommend(boolean recommend) {
		this.recommend = recommend;
	}	

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
			
	public Tmenu getMenu() {
		return menu;
	}

	public void setMenu(Tmenu menu) {
		this.menu = menu;
	}

	public List<TlocalizedField> getProductName_locale() {
		return productName_locale;
	}

	public void setProductName_locale(List<TlocalizedField> productName_locale) {
		this.productName_locale = productName_locale;
	}

	public List<TlocalizedField> getShortDescr_locale() {
		return shortDescr_locale;
	}

	public void setShortDescr_locale(List<TlocalizedField> shortDescr_locale) {
		this.shortDescr_locale = shortDescr_locale;
	}

	public List<TlocalizedField> getFullDescr_locale() {
		return fullDescr_locale;
	}

	public void setFullDescr_locale(List<TlocalizedField> fullDescr_locale) {
		this.fullDescr_locale = fullDescr_locale;
	}

	public List<TlocalizedField> getUnitName_locale() {
		return unitName_locale;
	}

	public void setUnitName_locale(List<TlocalizedField> unitName_locale) {
		this.unitName_locale = unitName_locale;
	}

	public List<TgoodsAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<TgoodsAttribute> attributes) {
		this.attributes = attributes;
	}

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}	

		
	
}
