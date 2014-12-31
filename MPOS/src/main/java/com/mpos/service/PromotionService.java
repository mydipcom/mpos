package com.mpos.service;

import java.util.List;

import com.mpos.dto.Tpromotion;
import com.mpos.model.DataTableParamter;
import com.mpos.model.PagingData;

public interface PromotionService {
	
	PagingData loadPromotionList(DataTableParamter dtp);
	
	void updatePromtion(Tpromotion tPromotion);
    
	Tpromotion getPromtionById(int id);
	
	List<Tpromotion> selectPromotion(Integer bindType);
}
