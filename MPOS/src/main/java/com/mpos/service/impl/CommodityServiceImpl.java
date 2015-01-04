package com.mpos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mpos.dao.CommodityDao;
import com.mpos.dto.Tcommodity;
import com.mpos.service.CommodityService;
@Service
public class CommodityServiceImpl implements CommodityService {

	@Autowired
	CommodityDao commodityDao;
	
	public Tcommodity getTproductByid(Integer id) {
		// TODO Auto-generated method stub
		return commodityDao.get(id);
	}

}
