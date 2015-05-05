package com.mpos.service.impl;

import java.util.ArrayList;
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
import com.mpos.dao.ServiceDao;
import com.mpos.dto.Tservice;
import com.mpos.model.DataTableParamter;
import com.mpos.model.PagingData;
import com.mpos.service.ServiceService;
@Service
public class ServiceServiceImpl implements ServiceService {

	@Autowired
	private ServiceDao serviceDao;
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
		// TODO Auto-generated method stub
		return serviceDao.get(serviceId);
	}

	@SuppressWarnings("unchecked")
	public List<Tservice> load() {
		// TODO Auto-generated method stub
		Criteria criteria = serviceDao.createCriteria();
		criteria.add(Restrictions.eq("status", true));
		return criteria.list();
	}

}
