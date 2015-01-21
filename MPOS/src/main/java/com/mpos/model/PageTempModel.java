package com.mpos.model;

import java.io.Serializable;
import java.util.List;

import com.mpos.dto.TattributeValue;
import com.mpos.dto.Tcategory;
import com.mpos.dto.TcategoryAttribute;
import com.mpos.dto.Tmenu;

public class PageTempModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6897772615427788928L;
	
	public static final String T_CATEGORY = Tcategory.class.getSimpleName();
	public static final String LOCAL_CATE_NAME = "name";
	public static final String LOCAL_CATE_CONTENT = "content";
	
	public static final String T_MENU = Tmenu.class.getSimpleName();
	public static final String LOCAL_MENU_TITLE = "title";
	
	public static final String T_CATEGORY_ATTRIBUTE = TcategoryAttribute.class.getSimpleName();
	public static final String LOCAL_CATE_ATTRIBUTE_TITLE = "title";
	
	public static final String T_ATTRIBUTE_VALUE = TattributeValue.class.getSimpleName();
	public static final String LOCAL_ATTRIBUTE_VALUE_VALUE = "value";
	
	private int totalCount;
	private List<MenuModel> menus;
	
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public List<MenuModel> getMenus() {
		return menus;
	}
	public void setMenus(List<MenuModel> menus) {
		this.menus = menus;
	}
	
	
}
