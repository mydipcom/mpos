package com.mpos.service;

import java.util.List;

import com.mpos.dto.Ttable;
import com.mpos.model.DataTableParamter;
import com.mpos.model.PagingData;

public interface TableService {
	
	void create(Ttable table);
	void delete(Ttable table);
	void delete(Integer id);
	void deleteAll(Integer[] ids);
	void update(Ttable table);
	Ttable get(String tableName);
	Ttable get(Integer id);
	List<Ttable> loadAll();
	PagingData loadTableList(DataTableParamter dtp);
	Boolean tableNameIsExist(String tableName);
	Boolean updateVerification(String tableName);
}
