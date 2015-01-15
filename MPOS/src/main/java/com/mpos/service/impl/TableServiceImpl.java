package com.mpos.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.mpos.commons.MposException;
import com.mpos.dao.TableDao;
import com.mpos.dto.Ttable;
import com.mpos.model.DataTableParamter;
import com.mpos.model.PagingData;
import com.mpos.service.TableService;
@Service
public class TableServiceImpl implements TableService {
	@Autowired
	TableDao tableDao;

	@Override
	public void create(Ttable table) {
		Ttable tt = tableDao.findUnique("tableName", table.getTableName());
		if(tt == null){
			tableDao.create(table);
		}else{
			throw new MposException("Table Name is exist!");
		}
		
	}

	@Override
	public void delete(Ttable table) {
		tableDao.delete(table);
	}

	@Override
	public void delete(Integer id) {
		tableDao.delete(id);
	}

	@Override
	public void update(Ttable table) {
		Ttable tt = tableDao.findUnique("tableName", table.getTableName());
		if(tt==null||tt.getId()==table.getId()){
			try {
				BeanUtils.copyProperties(table, tt);
				tableDao.update(tt);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else{
			throw new MposException("Table Name is exist!");
		}
	}

	@Override
	public Ttable get(Integer id) {
		return tableDao.get(id);
	}

	@Override
	public List<Ttable> loadAll() {
		return tableDao.LoadAll();
	}

	@Override
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
					criterionList.add(Restrictions.eq(key, json.get(key)));
				}
			}
			for (Criterion criterion : criterionList) {
				criteria.add(criterion);
			}
			return tableDao.findPage(criteria, dtp.iDisplayStart,dtp.iDisplayLength);
		}
		return tableDao.findPage(criteria,dtp.iDisplayStart, dtp.iDisplayLength);
	}

	@Override
	public Boolean tableNameIsExist(String tableName) {
		Ttable tt = tableDao.findUnique("tableName", tableName);
		if(tt==null){
			return false;
		}
		return true;
	}
	

	@Override
	public void deleteAll(Integer[] ids) {
		tableDao.deleteAll(ids);
	}

	@Override
	public Boolean updateVerification(String tableName) {
		String[] table = tableName.split(",");
		Integer id = Integer.valueOf(table[1]);
		Ttable tt = tableDao.findUnique("tableName", table[0]);
		if(tt==null||tt.getId()==id){
			return true;
		}
		return false;
	}

	@Override
	public Ttable get(String tableName) {
		// TODO Auto-generated method stub
		return tableDao.findUnique("tableName", tableName);
	}

}
