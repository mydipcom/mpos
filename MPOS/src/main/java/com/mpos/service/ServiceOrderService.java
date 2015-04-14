package com.mpos.service;

import java.util.List;
import java.util.Map;

import com.mpos.dto.TserviceOrder;
import com.mpos.model.DataTableParamter;
import com.mpos.model.PagingData;

public interface ServiceOrderService {
	public void save(TserviceOrder serviceOrder);
	public void delete(TserviceOrder serviceOrder);
	public void delete(Integer storeId);
	public void update(TserviceOrder serviceOrder);
	public PagingData loadList(DataTableParamter rdtp);
	
	public void delete(String hql,Map<String, Object> params);
	public void update(String hql,Map<String, Object> params);
	public List<TserviceOrder> select(String hql,Map<String, Object> params);
}
