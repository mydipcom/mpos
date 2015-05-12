package com.mpos.service;

import java.util.List;
import java.util.Map;

import com.mpos.dto.Tdevice;
import com.mpos.model.DataTableParamter;
import com.mpos.model.PagingData;

public interface DeviceService {
	
	void create(Tdevice device);
	void delete(Tdevice device);
	void delete(Integer id);
	void deleteAll(Integer[] ids);
	void update(Tdevice device);
	void updateStatus();
	public void delete(String hql,Map<String, Object> params);
	Tdevice get(String tableName);
	Tdevice get(Integer id);
	List<Tdevice> loadAll();
	PagingData loadDeviceList(DataTableParamter dtp);
	Integer getCountByStoreIdAndDeviceType(Integer storeId,Integer deviceType);
	Tdevice get(Integer deviceType,String channelId);
	Integer getCount(Integer deviceType,String channelId);
}
