package com.mpos.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.mpos.commons.ConvertTools;
import com.mpos.commons.SecurityTools;
import com.mpos.dao.AdminInfoDao;
import com.mpos.dao.AdminUserDao;
import com.mpos.dao.ServiceDao;
import com.mpos.dao.ServiceOrderDao;
import com.mpos.dao.StoreDao;
import com.mpos.dao.TableDao;
import com.mpos.dto.TadminInfo;
import com.mpos.dto.TadminRole;
import com.mpos.dto.TadminUser;
import com.mpos.dto.Tservice;
import com.mpos.dto.TserviceOrder;
import com.mpos.dto.Tstore;
import com.mpos.model.DataTableParamter;
import com.mpos.model.PagingData;
import com.mpos.service.ServiceService;
@Service
public class ServiceServiceImpl implements ServiceService {

	@Autowired
	private ServiceDao serviceDao;
	@Autowired
	private StoreDao storeDao;
	@Autowired
	private AdminUserDao adminUserDao;
	@Autowired
	private AdminInfoDao adminInfoDao;
	@Autowired
	private TableDao tableDao;
	@Autowired
	private ServiceOrderDao serviceOrderDao;
	public void save(Tservice service) {
		// TODO Auto-generated method stub
		serviceDao.save(service);
	}

	public void delete(Tservice service) {
		// TODO Auto-generated method stub
		serviceDao.delete(service);
	}

	public void delete(Integer serviceId) {
		// TODO Auto-generated method stub
		serviceDao.delete(serviceId);
	}

	public void update(Tservice service) {
		// TODO Auto-generated method stub
		serviceDao.update(service);
	}

	public PagingData loadList(DataTableParamter rdtp) {
		String searchJsonStr = rdtp.getsSearch();
		Criteria criteria = serviceDao.createCriteria();
		criteria.addOrder(Order.desc("serviceId"));
		//criteria.add(Restrictions.eq("status", true));
		if(searchJsonStr!=null&&!searchJsonStr.isEmpty()){
			List<Criterion> criterionList = new ArrayList<Criterion>();
			JSONObject json = (JSONObject) JSONObject.parse(searchJsonStr);
			Set<String> keys = json.keySet();
			for(String key:keys){
				String value = json.getString(key);
				if(value!=null&&!value.isEmpty()){
					if(key.equals("servieName")){
						criterionList.add(Restrictions.like(key, json.getString(key), MatchMode.ANYWHERE));
					}else if(key.equals("status")){
						criterionList.add(Restrictions.eq(key, json.getBoolean(key)));
					}else{
						criterionList.add(Restrictions.eq(key, json.get(key)));
					}
				}
			}
			for (Criterion criterion : criterionList) {
				criteria.add(criterion);
			}
			return serviceDao.findPage(criteria,rdtp.iDisplayStart, rdtp.iDisplayLength);
		}
		return serviceDao.findPage(criteria,rdtp.iDisplayStart, rdtp.iDisplayLength);
	}

	public void delete(String hql, Map<String, Object> params) {
		// TODO Auto-generated method stub
		serviceDao.delete(hql, params);
	}

	public void update(String hql, Map<String, Object> params) {
		// TODO Auto-generated method stub
		serviceDao.update(hql, params);
	}

	public List<Tservice> select(String hql, Map<String, Object> params) {
		// TODO Auto-generated method stub
		return serviceDao.select(hql, params);
	}

	public Tservice get(Integer serviceId) {
		return serviceDao.get(serviceId);
	}

	@SuppressWarnings("unchecked")
	public List<Tservice> load() {
		Criteria criteria = serviceDao.createCriteria();
		criteria.add(Restrictions.eq("status", true));
		return criteria.list();
	}

	public Map<String,String> register(TadminUser user, Integer serviceId, String mobile,Boolean status) {
		Map<String, String> res = new HashMap<String, String>();
		Tstore store = new Tstore();
		if(serviceId==null){
			serviceId=0;
		}
		Tservice service= serviceDao.get(serviceId);
		store.setServiceId(serviceId);
		store.setPublicKey("888888");
		store.setClientPwd("888888");
		store.setStoreName(" ");
		if(service.getServiceId()>0&&service.getServicePrice()>1){
			store.setStatus(false);
		}else{
			store.setStatus(true);
		}
		store.setAutoSyncStatus(false);
		store.setServiceDate(ConvertTools.longTimeAIntDay(System.currentTimeMillis(), service.getValidDays()));
		store.setStoreCurrency("$");
		store.setStoreLangId("2");
		store.setPrintType(1);
		storeDao.save(store);
		//tableDao.create(new Ttable("A01", 4, store.getStoreId()));
		user.setStoreId(store.getStoreId());
		user.setCreatedTime(System.currentTimeMillis());
		user.setCreatedBy("admin");
		user.setAdminId(user.getEmail());
		user.setAdminRole(new TadminRole(3));
		user.setStatus(status);
		user.setPassword(SecurityTools.MD5(user.getPassword()));
		adminUserDao.create(user);
		TadminInfo info = new TadminInfo();
		info.setAdminId(user.getAdminId());
		info.setMobile(mobile);
		adminInfoDao.create(info);
		if(service.getServiceId()>0&&service.getServicePrice()>1){
			String orderNum= "CampRay"+System.currentTimeMillis();
			TserviceOrder order = new TserviceOrder();
			order.setCreateTime(System.currentTimeMillis());
			order.setEmail(user.getEmail());
			order.setStatus(0);
			order.setPrice(service.getServicePrice());
			order.setServiceId(service);
			order.setServiceName(service.getServiceName());
			order.setOrderNum(orderNum);
			serviceOrderDao.create(order);
			res.put("price", service.getServicePrice()+"");
			res.put("orderNum", orderNum);
			res.put("subject", service.getServiceName());
		}
		res.put("status", "1");
		res.put("email", user.getEmail());
		res.put("serviceName", service.getServiceName());
		res.put("startTime", ConvertTools.longToDateString(System.currentTimeMillis()));
		res.put("endTime",  ConvertTools.longToDateString(store.getServiceDate()));
		
		return res;
	}

}
