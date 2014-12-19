package com.mpos.service;

import com.mpos.dto.Tproduct;
import com.mpos.model.DataTableParamter;
import com.mpos.model.PagingData;
import com.sun.xml.bind.v2.model.core.ID;

public interface GoodsService {
	
	PagingData loadGoodsList(DataTableParamter dtp);
	
	public void deletegoodsByids(Integer ids[]);
	
	public void activegoodsByids(Integer ids[]);
	
	Tproduct getTproductByid(Integer id);

}
