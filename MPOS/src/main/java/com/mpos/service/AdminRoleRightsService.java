package com.mpos.service;

import com.mpos.dto.TadminRoleRights;

public interface AdminRoleRightsService {
	
	TadminRoleRights getAdminRoleRightsById(int roleId);
	
	void createAdminRoleRights(TadminRoleRights adminRoleRights);
	
	void saveAdminRoleRights(TadminRoleRights adminRoleRights);
	
	void updateAdminRoleRights(TadminRoleRights adminRoleRights);
	
	void deleteAdminRoleRights(TadminRoleRights adminRoleRights);
		
}
