package com.mpos.service;

import com.mpos.dto.TadminLog;
import com.mpos.model.DataTableParamter;
import com.mpos.model.PagingData;

public interface AdminuserLogService {

	

	TadminLog getRuleLogById(Integer Id);
	
	void createAdminLog(TadminLog adminlog);
	
	void deleteRuleLog(TadminLog adminlog);
	
	void deleteRuleLogById(int id);
	
	void deleteRuleLogById(Integer[] ids);
	
	public PagingData loadAdminLogList(DataTableParamter rdtp);
	
	public PagingData loadAdminLogList(String id,DataTableParamter rdtp);
	
}
