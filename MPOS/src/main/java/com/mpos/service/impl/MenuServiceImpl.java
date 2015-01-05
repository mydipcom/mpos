package com.mpos.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.mpos.commons.MposException;
import com.mpos.dao.MenuDao;
import com.mpos.dto.Tmenu;
import com.mpos.model.DataTableParamter;
import com.mpos.model.PagingData;
import com.mpos.service.MenuService;

@Service
public class MenuServiceImpl implements MenuService {
	@Autowired
	MenuDao menuDao;

	public void saveMenu(Tmenu menu) {
		// TODO Auto-generated method stub
		if (menu.getPid() != 0
				&& menuDao.get(menu.getPid()) == null) {
			throw new MposException("error.MenuServiceImpl.saveMenu.pid");
		}
		menuDao.save(menu);
	}

	public void deleteMenu(Tmenu menu) {
		// TODO Auto-generated method stub
		menuDao.delete(menu);
	}

	public void updateMenu(Tmenu menu) {
		// TODO Auto-generated method stub
		if (menu.getPid() != 0
				&& menuDao.get(menu.getPid()) == null) {
			throw new MposException("error.MenuServiceImpl.updateMenu.pid");
		}
		menuDao.update(menu);
	}

	public Tmenu getMenu(Integer menuId) {
		// TODO Auto-generated method stub
		return menuDao.get(menuId);
	}

	public PagingData loadMenuList(DataTableParamter rdtp) {
		String searchJsonStr = rdtp.getsSearch();
		Criteria criteria = menuDao.createCriteria();
		criteria.addOrder(Order.desc("menuId"));
		criteria.add(Restrictions.eq("status", true));
		if (searchJsonStr != null && !searchJsonStr.isEmpty()) {
			List<Criterion> criterionList = new ArrayList<Criterion>();
			JSONObject json = (JSONObject) JSONObject.parse(searchJsonStr);
			Set<String> keys = json.keySet();
			for (String key : keys) {
				String value = json.getString(key);
				if (value != null && !value.isEmpty()) {
					if (key.equals("status")) {
						criterionList.add(Restrictions.eq(key,
								json.getBoolean(key)));
					} else {
						criterionList.add(Restrictions.eq(key, json.get(key)));
					}
				}
			}
			for (Criterion criterion : criterionList) {
				criteria.add(criterion);
			}
			return menuDao.findPage(criteria, rdtp.iDisplayStart,
					rdtp.iDisplayLength);
		}
		return menuDao.findPage(criteria,rdtp.iDisplayStart, rdtp.iDisplayLength);
	}

	public void deleteMenuByIds(Integer[] idArr) {
		// TODO Auto-generated method stub
		if (idArr != null && idArr.length > 0) {
			for (Integer integer : idArr) {
					List<Tmenu> menus = menuDao.findBy("pid", integer);
					if (menus != null && menus.size() > 0) {
						deleteAll(menus);
					}
					Tmenu menu = menuDao.get(integer);
					menu.setStatus(false);
					menuDao.update(menu);
			}
		}

	}

	public List<Tmenu> getAllMenu() {
		Criteria criteria = menuDao.createCriteria();
		return criteria.add(Restrictions.eq("status", true)).list();
		// return menuDao.LoadAll();
	}

	public Tmenu getParentMenu(Tmenu menu) {
		// TODO Auto-generated method stub
		return menuDao.get(menu.getPid());
	}
	
	private void deleteAll(List<Tmenu> menus){
		if (menus != null && menus.size() > 0) {
			for (Tmenu tmenu : menus) {
				tmenu.setStatus(false);
				menuDao.update(tmenu);
				deleteAll(menuDao.findBy("pid", tmenu.getMenuId()));
			}
		}
	}

}
