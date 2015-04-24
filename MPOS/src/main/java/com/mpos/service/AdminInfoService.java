package com.mpos.service;

import com.mpos.dto.TadminInfo;

public interface AdminInfoService{
	TadminInfo getAdminInfoById(String adminId);
	void updateAdminInfo(TadminInfo adminInfo);
	void updateAdminInfoAvatar(TadminInfo adminInfo);
	void createAdminInfo(TadminInfo adminInfo);
	
}
