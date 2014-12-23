package com.mpos.service.impl;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.mpos.dao.OrderItemDao;
import com.mpos.model.DataTableParamter;
import com.mpos.model.PagingData;
import com.mpos.service.OrderItemService;
public class OrderItemServiceImpl implements OrderItemService {
    
	@Autowired
	private OrderItemDao orderItemDao;
	
	public PagingData loadPagingDataByOrderId(DataTableParamter dtp) {
		// TODO Auto-generated method stub
		return orderItemDao.findPage(Restrictions.eq("orderId", dtp.getOrder_id()), dtp.iDisplayStart, dtp.iDisplayLength);
	}


}
