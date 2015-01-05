package com.mpos.model;

import java.io.Serializable;

public class MenuModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8847670497753749910L;
	private Integer id;
	private String title;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
}
