package com.mpos.dto;

import java.io.Serializable;

public class ImageModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4561805743289423254L;
	public static final int LOGO = 1;
	public static final int BACK = 2;
	private Integer storeId;
	private byte[] image;
	private Integer type;
	public Integer getStoreId() {
		return storeId;
	}
	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	

}
