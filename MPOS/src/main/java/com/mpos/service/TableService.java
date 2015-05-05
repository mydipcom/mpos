package com.mpos.service;

import java.util.List;
import java.util.Map;

import com.mpos.dto.Ttable;
import com.mpos.model.DataTableParamter;
import com.mpos.model.PagingData;

public interface TableService {
	
	void create(Ttable table);
	void delete(Ttable table);
	void delete(Integer id);
	void deleteAll(Integer[] ids);
	void update(Ttable table);
	void deleteByStoreId(Integer storeId);
	Ttable get(String tableName);
	Ttable get(Integer id);
	List<Ttable> loadAll(Integer storeId);
	PagingData loadTableList(DataTableParamter dtp);
	Boolean tableNameIsExist(String tableName,Integer storeId);
	Boolean updateVerification(String tableName,Integer storeId);
	
	public List<Ttable> select(String hql,Map<String, Object> params);
}
