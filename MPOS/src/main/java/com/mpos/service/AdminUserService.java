package com.mpos.service;

import com.mpos.dto.TadminUser;
import com.mpos.model.DataTableParamter;
import com.mpos.model.PagingData;

public interface AdminUserService {
	
	TadminUser getAdminUserById(String userId);
	
	void createAdminUser(TadminUser adminUser);
	
	void updateAdminUser(TadminUser adminUser);
	
	void updateAdminUserPassword(TadminUser adminUser);
	
	void deleteAdminUser(TadminUser adminUser);
	
	void deleteAdminUserById(int id);
	
	void deleteAdminUserByIds(String[] ids);
	
	void activateUsersByIds(String[] ids);
	
	void deactivateUsersByIds(String[] ids);
	
	public PagingData loadAdminUserList(DataTableParamter rdtp);
	
	public int getAdminUserAmount();
	
	public TadminUser getTadminUsersByEmail(String email);
		
}
