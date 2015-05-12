package com.mpos.service.impl;

import java.util.ArrayList;
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
import com.mpos.dao.ProductReleaseDao;
import com.mpos.dao.TableDao;
import com.mpos.dto.TproductRelease;
import com.mpos.dto.Ttable;
import com.mpos.model.DataTableParamter;
import com.mpos.model.PagingData;
import com.mpos.service.TableService;
@Service
public class TableServiceImpl implements TableService {
	@Autowired
	TableDao tableDao;
	@Autowired
	ProductReleaseDao productReleaseDao;

	
	public void create(Ttable table) {
		//tableDao.create(table);
		//Ttable tt = tableDao.findUnique("tableName", table.getTableName());
		Criteria criteria = tableDao.createCriteria();
		criteria.add(Restrictions.eq("tableName",table.getTableName())).add(Restrictions.eq("storeId", table.getStoreId()));
		Ttable tt = (Ttable) criteria.uniqueResult();
		if(tt == null){
			tableDao.create(table);
			updateVersion(table.getStoreId());
		}else{                       
			throw new MposException("error.TableServiceImpl.tableName");
		}
	}

	
	public void delete(Ttable table) {
		tableDao.delete(table);
	}

	
	public void delete(Integer id) {
		tableDao.delete(id);
	}
	
	public void updateVersion(Integer storeId){
		Integer verId = productReleaseDao.getMaxId("id", storeId);
		TproductRelease productrelease;
		if (verId != null && verId != 0) {
			productrelease = productReleaseDao.get(verId);
			String ids = productrelease.getProducts();
			if (productrelease != null && !productrelease.isIsPublic()) {
				productrelease.setProducts(ids);
				productReleaseDao.update(productrelease);
			} else {
				TproductRelease newproductrelease = new TproductRelease();
				newproductrelease.setProducts(ids);
				newproductrelease.setStoreId(storeId);
				newproductrelease.setIsPublic(false);
				productReleaseDao.create(newproductrelease);
			}
		} else {
			TproductRelease productrelease1 = new TproductRelease();
			productrelease1.setProducts("");
			productrelease1.setIsPublic(false);
			productrelease1.setStoreId(storeId);
			productReleaseDao.create(productrelease1);
		}
	}

	
	public void update(Ttable table) {
		//Ttable tt = tableDao.findUnique("tableName", table.getTableName());
		Criteria criteria = tableDao.createCriteria();
		criteria.add(Restrictions.eq("tableName",table.getTableName())).add(Restrictions.eq("storeId", table.getStoreId()));
		Ttable tt = (Ttable) criteria.uniqueResult();
		if(tt==null||tt.getId()==table.getId()){
			try {
				BeanUtils.copyProperties(table, tt);
				tableDao.update(tt);
				updateVersion(table.getStoreId());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else{
			throw new MposException("error.TableServiceImpl.tableName");
		}
	}

	
	public Ttable get(Integer id) {
		return tableDao.get(id);
	}

	
	public List<Ttable> loadAll(Integer storeId) {
		return tableDao.LoadAll();
	}

	
	public PagingData loadTableList(DataTableParamter dtp) {
		String searchJsonStr = dtp.getsSearch();
		Criteria criteria = tableDao.createCriteria();
		criteria.addOrder(Order.asc("tableName"));
		if (searchJsonStr != null && !searchJsonStr.isEmpty()) {
			List<Criterion> criterionList = new ArrayList<Criterion>();
			JSONObject json = (JSONObject) JSONObject.parse(searchJsonStr);
			Set<String> keys = json.keySet();
			for (String key : keys) {
				String value = json.getString(key);
				if (value != null && !value.isEmpty()) {
					if(key=="tableName"){
						criterionList.add(Restrictions.like(key, json.getString(key), MatchMode.ANYWHERE));
					}else if(key=="storeId"){
						criterionList.add(Restrictions.eq(key, json.getInteger(key)));
					}else{
						criterionList.add(Restrictions.eq(key, json.get(key)));
					}					
				}
			}
			for (Criterion criterion : criterionList) {
				criteria.add(criterion);
			}
			return tableDao.findPage(criteria, dtp.iDisplayStart,dtp.iDisplayLength);
		}
		return tableDao.findPage(criteria,dtp.iDisplayStart, dtp.iDisplayLength);
	}

	
	public Boolean tableNameIsExist(String tableName,Integer storeId) {
		Criteria criteria = tableDao.createCriteria();
		criteria.add(Restrictions.eq("tableName",tableName)).add(Restrictions.eq("storeId", storeId));
		Ttable tt = (Ttable) criteria.uniqueResult();//tableDao.findUnique(new String[]{"tableName","storeId"}, new String[]{tableName,storeId+""});;
		if(tt==null){
			return false;
		}
		return true;
	}
	

	
	public void deleteAll(Integer[] ids,Integer storeId) {
		tableDao.deleteAll(ids);
		updateVersion(storeId);
	}

	
	public Boolean updateVerification(String tableName,Integer storeId) {
		String[] table = tableName.split(",");
		Integer id = Integer.valueOf(table[1]);
		//Ttable tt = tableDao.findUnique("tableName", table[0]);
		Criteria criteria = tableDao.createCriteria();
		criteria.add(Restrictions.eq("tableName", table[0])).add(Restrictions.eq("storeId", Integer.valueOf(table[2])));
		Ttable tt = (Ttable) criteria.uniqueResult();//tableDao.findUnique(new String[]{"tableName","storeId"}, new String[]{tableName,storeId+""});;
		if(tt==null||tt.getId()==id){
			return true;
		}
		return false;
	}

	
	public Ttable get(String tableName) {
		// TODO Auto-generated method stub
		return tableDao.findUnique("tableName", tableName);
	}


	public List<Ttable> select(String hql, Map<String, Object> params) {
		// TODO Auto-generated method stub
		return tableDao.select(hql, params);
	}


	public void deleteByStoreId(Integer storeId) {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("storeId", storeId);
		String hql = "delete from Ttable where storeId=:storeId";
		tableDao.delete(hql, params);
	}

}
