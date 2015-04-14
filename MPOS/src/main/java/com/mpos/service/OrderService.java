package com.mpos.service;


import java.util.List;
import java.util.Map;

import com.mpos.dto.Torder;
import com.mpos.model.DataTableParamter;
import com.mpos.model.PagingData;

public interface OrderService {
	
	PagingData loadOrderList(DataTableParamter dtp);
	
	Torder getTorderById(int id);
	
	void update(Torder torder);
	
	void createOrder(Torder torder);
	
	void deleteOrder(Torder torder);
	
	List<Torder> select(String hql,Map<String, Object> params);
}
