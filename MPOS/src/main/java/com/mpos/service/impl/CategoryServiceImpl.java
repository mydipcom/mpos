package com.mpos.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.mpos.dao.CategoryDao;
import com.mpos.dto.Tcategory;
import com.mpos.model.DataTableParamter;
import com.mpos.model.PagingData;
import com.mpos.service.CategoryService;
@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	CategoryDao categoryDao;
	
	@Override
	public void createCategory(Tcategory category) {
		// TODO Auto-generated method stub
		categoryDao.create(category);
	}

	@Override
	public void deleteCategory(Tcategory category) {
		// TODO Auto-generated method stub
		categoryDao.delete(category);
	}

	@Override
	public void updateCategory(Tcategory category) {
		// TODO Auto-generated method stub
		categoryDao.update(category);
	}

	@Override
	public Tcategory getCategory(Integer categoryId) {
		// TODO Auto-generated method stub
		return categoryDao.get(categoryId);
	}

	@Override
	public PagingData loadCategoryList(DataTableParamter rdtp) {
		String searchJsonStr = rdtp.getsSearch();
		if(searchJsonStr!=null&&!searchJsonStr.isEmpty()){
			List<Criterion> criterionList = new ArrayList<Criterion>();
			JSONObject json = (JSONObject) JSONObject.parse(searchJsonStr);
			Set<String> keys = json.keySet();
			for(String key:keys){
				String value = json.getString(key);
				if(value!=null&&!value.isEmpty()){
					if(key.equals("status")){
						criterionList.add(Restrictions.eq(key, json.getInteger(key)));
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
			return categoryDao.findPage(criterions,rdtp.iDisplayStart, rdtp.iDisplayLength);
		}
		
		try {
			return categoryDao.findPage(rdtp.iDisplayStart, rdtp.iDisplayLength);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void deleteCategoryByIds(Integer[] ids) {
		// TODO Auto-generated method stub
		if(ids!=null&&ids.length>0){
			for (Integer id : ids) {
				Tcategory cate = categoryDao.get(id);
				cate.setStatus(false);
				categoryDao.update(cate);
			}
		}
	}

}
