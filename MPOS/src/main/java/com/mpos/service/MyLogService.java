package com.mpos.service;

import com.mpos.dto.TadminLog;
import com.mpos.dto.TadminUser;
import com.mpos.model.DataTableParamter;
import com.mpos.model.PagingData;

public interface MyLogService {

	TadminLog getAdminLogById(Integer Id);
	
	public PagingData loadadminlogList(DataTableParamter rdtp ,TadminUser adminuser );
}
