package com.mpos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.mpos.dao.GoodsAttributeDao;
import com.mpos.dto.TgoodsAttribute;
import com.mpos.dto.TproductAttribute;
import com.mpos.service.GoodsAttributeService;

public class GoodsAttributeServiceImpl implements GoodsAttributeService{
	
	@Autowired
	private GoodsAttributeDao goodsAttributeDao;

	public void createProductAttribute(TgoodsAttribute goodsAttribute) {
		goodsAttributeDao.create(goodsAttribute);
	}

	public TproductAttribute getTproductAttribute(Integer productid,Integer AttributeId) {
		
		return goodsAttributeDao.getAttribute(productid, AttributeId);
	}

	public List<TproductAttribute> getTproductAttribute(Integer productid) {
		
		return goodsAttributeDao.getAttributebyproductid(productid);
	}	

}
