package com.mpos.service;

import com.mpos.dto.TproductAttribute;
import com.mpos.dto.TproductAttributeId;
import com.mpos.model.AddAttributevaleModel;

public interface ProductAttributeService {
	public void cachedSystemSettingData(AddAttributevaleModel model);
	public void cachedSystemclearData(AddAttributevaleModel model);
	void createProductAttribute(TproductAttribute productAttribute);
	TproductAttribute getAttributes(TproductAttributeId productAttributeId);
	void updattProductAttribute(TproductAttribute productAttribute);
	void createOrProductAttribute(TproductAttribute productAttribute);
	 public TproductAttribute getAttributeByproductidAndattributeid(Integer productid,Integer attributeid);
}
