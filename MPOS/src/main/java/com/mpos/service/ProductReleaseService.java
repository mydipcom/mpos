package com.mpos.service;

import java.util.List;

import com.mpos.dto.TproductRelease;

public interface ProductReleaseService {

	
	List<TproductRelease> getUpdatedRelease(Integer verId,Integer storeId);
	
	void createOrupdateProductRelease(Integer id,Integer storeId);

	TproductRelease getLatestPublished(Integer storeId);
	void save(TproductRelease release);
	TproductRelease getUnPublished(Integer storeId);
	void publicreleasebyid(Integer ids,Integer storeId);
	
}
