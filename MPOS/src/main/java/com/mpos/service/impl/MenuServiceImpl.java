package com.mpos.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.Criterion;
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
		if(menu.getPid()!=0&&menuDao.get(menu.getPid())==null){
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
		if(menu.getPid()!=0&&menuDao.get(menu.getPid())==null){
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
		if(searchJsonStr!=null&&!searchJsonStr.isEmpty()){
			List<Criterion> criterionList = new ArrayList<Criterion>();
			JSONObject json = (JSONObject) JSONObject.parse(searchJsonStr);
			Set<String> keys = json.keySet();
			for(String key:keys){
				String value = json.getString(key);
				if(value!=null&&!value.isEmpty()){
					if(key.equals("status")){
						criterionList.add(Restrictions.eq(key, json.getBoolean(key)));
					}else{
						criterionList.add(Restrictions.eq(key, json.get(key)));
					}
				}
			}
			Criterion[] criterions = new Criterion[criterionList.size()];
			int i=0;
			for (Criterion criterion : criterionList) {
				criterions[i]=criterion;	
				i++;
			}
			return menuDao.findPage(criterions,rdtp.iDisplayStart, rdtp.iDisplayLength);
		}
		return menuDao.findPage(rdtp.iDisplayStart, rdtp.iDisplayLength);
	}

	
	public void deleteMenuByIds(Integer[] idArr) {
		// TODO Auto-generated method stub
		menuDao.deleteAll(idArr);
	}


	public List<Tmenu> getAllMenu() {
		
		return menuDao.getAll();
	}

}
