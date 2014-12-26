package com.mpos.service;

import com.mpos.dto.Tsetting;
import com.mpos.model.DataTableParamter;
import com.mpos.model.PagingData;

public interface SystemSettingService {
	
	Tsetting getSystemsettingById(Integer Id);
	
	void createSystemsetting(Tsetting setting);
	
	void updateSystemsetting(Tsetting setting);
	
	void deleteSystemsetting(Tsetting setting);
	
	void deleteSystemsetting(int id);
	
	void deleteSystemsettingByIds(Integer[] ids);
	
	public PagingData loadSystemsettingList(DataTableParamter rdtp);

	public void cachedSystemSettingData();
	
	public PagingData getStoreSetting();
	
	Tsetting getSystemSettingByName(String name);

}
