package com.mpos.service;

import java.util.List;

import com.mpos.dto.TgoodsAttribute;
import com.mpos.dto.TproductAttribute;

public interface GoodsAttributeService {
	public void createProductAttribute(TgoodsAttribute goodsAttribute);
	
	public TproductAttribute getTproductAttribute(Integer productid,Integer AttributeId);
	
	public List<TproductAttribute> getTproductAttribute(Integer productid);
		
	
}
