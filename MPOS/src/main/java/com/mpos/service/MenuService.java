package com.mpos.service;

import java.util.List;

import com.mpos.dto.Tmenu;
import com.mpos.model.DataTableParamter;
import com.mpos.model.PagingData;

public interface MenuService {

	void saveMenu(Tmenu menu);
	void deleteMenu(Tmenu menu);
	void updateMenu(Tmenu menu);
	Tmenu getMenu(Integer menuId);
	PagingData loadMenuList(DataTableParamter rdtp);
	void deleteMenuByIds(Integer[] idArr);
	public List<Tmenu> getAllMenu();
	
}
