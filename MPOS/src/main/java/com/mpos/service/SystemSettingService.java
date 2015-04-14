package com.mpos.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.mpos.dto.Tsetting;
import com.mpos.model.PagingData;

public interface SystemSettingService {
	
	Tsetting getSystemsettingById(Integer Id);
	
	void createSystemsetting(Tsetting setting);
	
	void updateSystemsetting(Tsetting setting);
	
	void deleteSystemsetting(Tsetting setting);
	
	void deleteSystemsetting(int id);
	
	void deleteSystemsettingByIds(Integer[] ids);
	
	public PagingData loadSystemsetting();

	public void cachedSystemSettingData() throws UnsupportedEncodingException, IOException;
	public void cachedSystemSet();
	
	public PagingData getStoreSetting();
	
	Tsetting getSystemSettingByName(String name);

}
