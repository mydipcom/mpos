package com.mpos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.mpos.commons.SystemConfig;
import com.mpos.dao.ProductAttributeDao;
import com.mpos.dto.TproductAttribute;
import com.mpos.dto.TproductAttributeId;
import com.mpos.model.AddAttributevaleModel;
import com.mpos.service.ProductAttributeService;

public class ProductAttributeServiceImpl implements ProductAttributeService{

	
	@Autowired
	private ProductAttributeDao productAttributeDao;

	public void cachedSystemSettingData(AddAttributevaleModel model) {
		// TODO Auto-generated method stub
		SystemConfig.product_AttributeModel_Map.put(model.getTitle(), model);
	}

	

	public void createProductAttribute(TproductAttribute productAttribute) {
		// TODO Auto-generated method stub
		productAttributeDao.create(productAttribute);
	}



	public TproductAttribute getAttributes(TproductAttributeId productAttributeId) {
		
		return productAttributeDao.getByAttributeid(productAttributeId);
	}



	public void updattProductAttribute(TproductAttribute productAttribute) {
		productAttributeDao.update(productAttribute);
		
	}



	public void cachedSystemclearData(AddAttributevaleModel model) {
		SystemConfig.product_AttributeModel_Map.remove(model.getTitle());
		
	}

}
