package com.mpos.service;

import java.util.List;

import com.mpos.dto.TproductAttribute;
import com.mpos.dto.TproductAttributeId;
import com.mpos.model.AddAttributevaleModel;

public interface ProductAttributeService {
	public void cachedSystemSettingData(AddAttributevaleModel model);
	void createProductAttribute(TproductAttribute productAttribute);
	TproductAttribute getAttributes(TproductAttributeId productAttributeId);
}
