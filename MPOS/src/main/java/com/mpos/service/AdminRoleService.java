package com.mpos.service;

import java.util.List;

import com.mpos.dto.TadminRole;
import com.mpos.model.DataTableParamter;
import com.mpos.model.PagingData;

public interface AdminRoleService {
	
	TadminRole getAdminRoleById(int roleId);
	
	List<TadminRole> getAllAdminRoles();
	
	void createAdminRole(TadminRole adminRole);
	
	void updateAdminRole(TadminRole adminRole);
	
	void deleteAdminRole(TadminRole adminRole);
	
	void deleteAdminRoleById(int id);
	
	void deleteAdminRolesByIds(Integer[] ids);
	
	PagingData loadAdminRolesList(DataTableParamter rdtp);
		
}
