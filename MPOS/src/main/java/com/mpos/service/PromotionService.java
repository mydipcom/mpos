package com.mpos.service;

import com.mpos.dto.Tpromotion;
import com.mpos.model.DataTableParamter;
import com.mpos.model.PagingData;

public interface PromotionService {
	
	PagingData loadPromotionList(DataTableParamter dtp);
	
	void updatePromtion(Tpromotion tPromotion);
    
	Tpromotion getPromtionById(int id);
}
