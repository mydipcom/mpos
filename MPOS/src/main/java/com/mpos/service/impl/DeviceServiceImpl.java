package com.mpos.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.mpos.dao.DeviceDao;
import com.mpos.dto.Tdevice;
import com.mpos.model.DataTableParamter;
import com.mpos.model.PagingData;
import com.mpos.service.DeviceService;
@Service
public class DeviceServiceImpl implements DeviceService {
	@Autowired
	DeviceDao deviceDao;


	public void create(Tdevice device) {
		// TODO Auto-generated method stub
		deviceDao.create(device);
	}

	
	public void delete(Tdevice device) {
		// TODO Auto-generated method stub
		deviceDao.delete(device);
	}

	
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		deviceDao.delete(id);
	}

	
	public void update(Tdevice device) {
		// TODO Auto-generated method stub
		deviceDao.update(device);
	}

	
	public Tdevice get(Integer id) {
		// TODO Auto-generated method stub
		return deviceDao.get(id);
	}

	
	public List<Tdevice> loadAll() {
		// TODO Auto-generated method stub
		return deviceDao.LoadAll();
	}

	
	public PagingData loadDeviceList(DataTableParamter dtp) {
		// TODO Auto-generated method stub
		String searchJsonStr = dtp.getsSearch();
		Criteria criteria = deviceDao.createCriteria();
		criteria.addOrder(Order.asc("tableName"));
		criteria.add(Restrictions.gt("table", 0));
		if (searchJsonStr != null && !searchJsonStr.isEmpty()) {
			List<Criterion> criterionList = new ArrayList<Criterion>();
			JSONObject json = (JSONObject) JSONObject.parse(searchJsonStr);
			Set<String> keys = json.keySet();
			for (String key : keys) {
				String value = json.getString(key);
				if (value != null && !value.isEmpty()) {
					if (key.equals("onlineStatus")) {
						criterionList.add(Restrictions.eq(key, json.getBoolean(key)));
					}else{
						criterionList.add(Restrictions.eq(key, json.get(key)));
					}
				}
			}
			for (Criterion criterion : criterionList) {
				criteria.add(criterion);
			}
			return deviceDao.findPage(criteria, dtp.iDisplayStart,dtp.iDisplayLength);
		}
		return deviceDao.findPage(criteria,dtp.iDisplayStart, dtp.iDisplayLength);
	}

	
	public Tdevice get(String tableName) {
		// TODO Auto-generated method stub
		return deviceDao.findUnique("tableName", tableName);
	}

	
	public void updateStatus() {
		// TODO Auto-generated method stub
		long now = System.currentTimeMillis();
		long cha = 1000*60*2;
		List<Tdevice> devices = deviceDao.LoadAll();
		if(devices!=null&&devices.size()>0){
			for (Tdevice tdevice : devices) {
				long report = tdevice.getLastReportTime();
				long tem = now-report;
				if(tem>cha){
					tdevice.setOnlineStatus(false);
					deviceDao.update(tdevice);
				}
			}
		}
	}

	
	public void deleteAll(Integer[] ids) {
		// TODO Auto-generated method stub
		deviceDao.deleteAll(ids);
	}
	
}
