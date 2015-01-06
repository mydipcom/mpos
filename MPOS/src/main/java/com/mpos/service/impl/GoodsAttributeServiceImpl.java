package com.mpos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.mpos.dao.GoodsAttributeDao;
import com.mpos.dto.TgoodsAttribute;
import com.mpos.service.GoodsAttributeService;

public class GoodsAttributeServiceImpl implements GoodsAttributeService{
	
	@Autowired
	private GoodsAttributeDao goodsAttributeDao;

	public void createProductAttribute(TgoodsAttribute goodsAttribute) {
		goodsAttributeDao.create(goodsAttribute);
	}	

}
