package com.mpos.service;

import java.util.List;

import com.mpos.dto.TproductRelease;

public interface ProductReleaseService {

	
	List<TproductRelease> getUpdatedRelease(Integer verId);
	
	void createOrupdateProductRelease(Integer id);

	TproductRelease getLatestPublished();
	
	TproductRelease getUnPublished();
	void publicreleasebyid(Integer ids);
	
}
