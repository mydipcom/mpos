package com.mpos.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.mpos.commons.MposException;
import com.mpos.dao.LanguageDao;
import com.mpos.dao.LocalizedFieldDao;
import com.mpos.dao.MenuDao;
import com.mpos.dto.Tlanguage;
import com.mpos.dto.TlocalizedField;
import com.mpos.dto.Tmenu;
import com.mpos.model.DaoModel;
import com.mpos.model.DataTableParamter;
import com.mpos.model.MenuModel;
import com.mpos.model.PageTempModel;
import com.mpos.model.PagingData;
import com.mpos.service.MenuService;
import com.mpos.commons.SystemConstants;
@Service
public class MenuServiceImpl implements MenuService {
	@Autowired
	MenuDao menuDao;
	@Autowired
	LanguageDao languageDao;
	@Autowired
	LocalizedFieldDao localizedFieldDao;

	public void saveMenu(Tmenu menu) {
		// TODO Auto-generated method stub
		if (menu.getPid() != 0
				&& menuDao.get(menu.getPid()) == null) {
			throw new MposException("error.MenuServiceImpl.saveMenu.pid");
		}
		menuDao.create(menu);
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
		if(menu.getMenuId() == menu.getPid()){
			throw new MposException("error.MenuServiceImpl.updateMenu.other.pid");
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
			return menuDao.findPageByNameSort(criteria, rdtp.iDisplayStart,
					rdtp.iDisplayLength);
		}
		return menuDao.findPageByNameSort(criteria,rdtp.iDisplayStart, rdtp.iDisplayLength);
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

	public List<MenuModel> getNoChildrenMenus() {
		// TODO Auto-generated method stub
		List<MenuModel> models = new ArrayList<MenuModel>();
		List<Tmenu> tmenus = menuDao.getNoChildren();
		if(tmenus!=null&&tmenus.size()>0){
			for (Tmenu tmenu : tmenus) {
				MenuModel model = new MenuModel();
				model.setId(tmenu.getMenuId());
				if (tmenu.getPid() == 0) {
					model.setTitle(tmenu.getTitle());
				} else {
					model.setTitle(loadTitle(tmenu, tmenu.getTitle()));
				}
				models.add(model);
			}
		}
		Collections.sort(models, new Comparator<MenuModel>() {
			public int compare(MenuModel arg0, MenuModel arg1) {
				return arg0.getTitle().compareTo(arg1.getTitle());
			}
		});
		return models;
	}
	
	
	public PagingData loadMenuList(DataTableParamter rdtp, String local) {
		PageTempModel model = new PageTempModel();
		model = loadData(rdtp);
		if(model.getTotalCount() == 0){
			return new PagingData();
		}
		Tlanguage language = languageDao.findUnique("local", local);
		List<MenuModel> values = model.getMenus();
		if(values!=null&&values.size()>0){
			/*for (MenuModel value : values) {
				TlocalizedField local_title = localizedFieldDao.getLocalizedValue(value.getId(),language.getId(),PageTempModel.T_MENU,PageTempModel.LOCAL_MENU_TITLE);
				if(local_title!=null&&!local_title.getLocaleValue().isEmpty()){
					value.setTitle(local_title.getLocaleValue());
				}
			}*/
			
			for (MenuModel value : values) {
				if (value.getPid() != 0) {
					value.setTitle(loadTitleLocal(value.getPid(), value.getTitle(),language));
				}
			}
		}
		Collections.sort(values, new Comparator<MenuModel>() {
			public int compare(MenuModel arg0, MenuModel arg1) {
				return arg0.getTitle().compareTo(arg1.getTitle());
			}
		});
		
		return new PagingData(model.getTotalCount(),model.getTotalCount(),values.toArray());
	}
	
	public PageTempModel loadData(DataTableParamter rdtp){
		PageTempModel model = new PageTempModel();
		DaoModel daoModel = new DaoModel();
		String searchJsonStr = rdtp.getsSearch();
		Criteria criteria = menuDao.createCriteria();
		criteria.add(Restrictions.eq("status", true));
		if(searchJsonStr!=null&&!searchJsonStr.isEmpty()){
			List<Criterion> criterionList = new ArrayList<Criterion>();
			JSONObject json = (JSONObject) JSONObject.parse(searchJsonStr);
			Set<String> keys = json.keySet();
			for(String key:keys){
				String value = json.getString(key);
				if(value!=null&&!value.isEmpty()){
					if(key=="title"){
						criterionList.add(Restrictions.like(key, json.getString(key), MatchMode.ANYWHERE));
					}else if(key=="status"){
						criterionList.add(Restrictions.eq(key, json.getBoolean(key)));
					}else{
						criterionList.add(Restrictions.eq(key, json.get(key)));
					}
				}
			}
			for (Criterion criterion : criterionList) {
				criteria.add(criterion);
			}
			daoModel =  menuDao.findPageList(criteria,rdtp.iDisplayStart,rdtp.iDisplayLength);
		}else{
			daoModel =  menuDao.findPageList(criteria,rdtp.iDisplayStart,rdtp.iDisplayLength);
		}
		List<MenuModel> menuModels = new ArrayList<MenuModel>();
		List<Tmenu> tas = (List<Tmenu>) daoModel.getList();
        if(tas!=null&&tas.size()>0){
        	for (Tmenu value : tas) {
        		MenuModel menu = new MenuModel();
        		menu.setId(value.getMenuId());
        		menu.setName(value.getTitle());
        		BeanUtils.copyProperties(value, menu,"titleLocale","menuId");
        		menuModels.add(menu);
			}
        } 
        model.setTotalCount(daoModel.getTotalCount());
        model.setMenus(menuModels);
		return model;
	}
	
	
	public List<MenuModel> loadMenu(String local) {
		List<Tmenu> menus = menuDao.LoadAll();
		Tlanguage language = languageDao.findUnique("local", local);
		List<MenuModel> models = new ArrayList<MenuModel>();
		if (menus != null && menus.size() > 0) {
			for (Tmenu tmenu : menus) {
				/*TlocalizedField local_title = localizedFieldDao.getLocalizedValue(tmenu.getMenuId(),language.getId(),PageTempModel.T_MENU,PageTempModel.LOCAL_MENU_TITLE);
				if(local_title!=null&&!local_title.getLocaleValue().isEmpty()){
					tmenu.setTitle(local_title.getLocaleValue());
				}*/
				MenuModel model = new MenuModel();
				model.setId(tmenu.getMenuId());
				if (tmenu.getPid() == 0) {
					model.setTitle(tmenu.getTitle());
				} else {
					model.setTitle(loadTitleLocal(tmenu.getPid(), tmenu.getTitle(),language));
				}
				models.add(model);
			}
		}
		return models;
	}
	
	private String loadTitle(Tmenu menu, String title) {
		Tmenu parent = menuDao.get(menu.getPid());
		// String res = "";
		if (parent != null && parent.getMenuId() != null) {
			title = parent.getTitle() + " >> " + title;
			return loadTitle(parent, title);
		}
		// System.out.println(title);
		return title;
	}
	public List<MenuModel> getNoChildrenMenus(Tlanguage language) {
		// TODO Auto-generated method stub
		List<MenuModel> models = new ArrayList<MenuModel>();
		List<Tmenu> tmenus = menuDao.getNoChildren();
		if(tmenus!=null&&tmenus.size()>0){
			for (Tmenu tmenu : tmenus) {
				/*Map<String, Object> ssMap=new HashMap<String, Object>();
				ssMap.put("language", language.getId());
				ssMap.put("entityId", tmenu.getMenuId());
				ssMap.put("tableName",SystemConstants.TABLE_NAME_MENU);
				ssMap.put("tableField",SystemConstants.TABLE_FIELD_TITLE);
				TlocalizedField localizedField=new TlocalizedField();
				List<TlocalizedField> localizedFieldlist= localizedFieldDao.find("from TlocalizedField where language=:language and entityId=:entityId and tableName=:tableName and tableField=:tableField", ssMap);*/
			//	TlocalizedField localizedField=localizedFieldDao.getLocalizedValue(tmenu.getMenuId(), language.getId(), SystemConstants.TABLE_NAME_MENU, SystemConstants.TABLE_FIELD_TITLE);
				MenuModel model = new MenuModel();
				model.setId(tmenu.getMenuId());
				if (tmenu.getPid() == 0) {
					/*if(localizedField!=null&&!localizedField.getLocaleValue().isEmpty()){
						model.setTitle(localizedField.getLocaleValue());
					}else{
						model.setTitle(tmenu.getTitle());
					}*/
					model.setTitle(tmenu.getTitle());
				} else {
					//model.setTitle(loadTitle(tmenu, localizedField.getLocaleValue(),language));
					model.setTitle(loadTitle(tmenu, tmenu.getTitle(),language));
				}
				models.add(model);
			}
		}
		Collections.sort(models, new Comparator<MenuModel>() {
			public int compare(MenuModel arg0, MenuModel arg1) {
				return arg0.getTitle().compareTo(arg1.getTitle());
			}
		});
		return models;
	}
	private String loadTitle(Tmenu menu, String title, Tlanguage language) {
		Tmenu parent = menuDao.get(menu.getPid());
		/*TlocalizedField local_title = localizedFieldDao.getLocalizedValue(parent.getMenuId(),language.getId(),PageTempModel.T_MENU,PageTempModel.LOCAL_MENU_TITLE);
		if(local_title!=null&&!local_title.getLocaleValue().isEmpty()){
			parent.setTitle(local_title.getLocaleValue());
		}*/
		if (parent != null && parent.getMenuId() != null) {
			if (!title.isEmpty()&&title!=null) {
				title = parent.getTitle() + " >> " + title;
			}else {
				
				title = parent.getTitle() + " >> " + menu.getTitle();
			}
			if(parent.getPid()!=0){
				return loadTitle(parent, title,language);
			}
			
		}
		// System.out.println(title);
		return title;
	}
	private String loadTitleLocal(Integer pid, String title,Tlanguage language) {
		Tmenu parent = menuDao.get(pid);
		//TlocalizedField local_title = localizedFieldDao.getLocalizedValue(parent.getMenuId(),language.getId(),PageTempModel.T_MENU,PageTempModel.LOCAL_MENU_TITLE);
		/*if(local_title!=null&&!local_title.getLocaleValue().isEmpty()){
			parent.setTitle(local_title.getLocaleValue());
		}*/
		if (parent != null && parent.getMenuId() != null) {
			title = parent.getTitle() + " >> " + title;
			if(parent.getPid()!=0){
				return loadTitleLocal(parent.getPid(),title,language);
			}
		}
		return title;
	}



}
