package com.mpos.service;

import java.util.List;
import java.util.Map;

import com.mpos.dto.Tstore;
import com.mpos.model.DataTableParamter;
import com.mpos.model.PagingData;

public interface StoreService {
		public void save(Tstore store);
		public void delete(Tstore store);
		public void delete(Integer storeId);
		public void update(Tstore store);
		public PagingData loadList(DataTableParamter rdtp);
		
		public void delete(String hql,Map<String, Object> params);
		public void update(String hql,Map<String, Object> params);
		public List<Tstore> select(String hql,Map<String, Object> params);
		
		public void cacheStoreTaken();		
}