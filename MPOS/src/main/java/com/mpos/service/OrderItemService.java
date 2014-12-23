package com.mpos.service;

import com.mpos.model.DataTableParamter;
import com.mpos.model.PagingData;

public interface OrderItemService {
	
	PagingData loadPagingDataByOrderId(DataTableParamter dtp);
	
	
	
}
