package com.mpos.service.impl;

import java.text.ParseException;
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
import com.mpos.commons.ConvertTools;
import com.mpos.dao.ServiceOrderDao;
import com.mpos.dto.TserviceOrder;
import com.mpos.model.DataTableParamter;
import com.mpos.model.PagingData;
import com.mpos.service.ServiceOrderService;
@Service
public class ServiceOrderServiceImpl implements ServiceOrderService{
	@Autowired
	private ServiceOrderDao serviceOrderDao;
	public void save(TserviceOrder serviceOrder) {
		// TODO Auto-generated method stub
		serviceOrderDao.save(serviceOrder);
	}

	public void delete(TserviceOrder serviceOrder) {
		// TODO Auto-generated method stub
		serviceOrderDao.delete(serviceOrder);
	}

	public void delete(Integer serviceOrderId) {
		// TODO Auto-generated method stub
		serviceOrderDao.delete(serviceOrderId);
	}

	public void update(TserviceOrder serviceOrder) {
		// TODO Auto-generated method stub
		serviceOrderDao.update(serviceOrder);
	}

	public PagingData loadList(DataTableParamter rdtp) {
		String searchJsonStr = rdtp.getsSearch();
		Criteria criteria = serviceOrderDao.createCriteria();
		criteria.addOrder(Order.desc("serviceOrderId"));
		criteria.add(Restrictions.eq("status", true));
		if(searchJsonStr!=null&&!searchJsonStr.isEmpty()){
			List<Criterion> criterionList = new ArrayList<Criterion>();
			JSONObject json = (JSONObject) JSONObject.parse(searchJsonStr);
			Set<String> keys = json.keySet();
			for(String key:keys){
				String value = json.getString(key);
				if(value!=null&&!value.isEmpty()){
					if(key.equals("email")){
						criterionList.add(Restrictions.like(key, json.getString(key), MatchMode.ANYWHERE));
					}else if(key.equals("status")){
						criterionList.add(Restrictions.eq(key, json.getBoolean(key)));
					}else if(key.equals("startTime")||key.equals("endTime")){
					}else{
						criterionList.add(Restrictions.eq(key, json.get(key)));
					}
				}
			}
			String startTime = json.getString("startTime");
			String endTime = json.getString("endTime");
			if((startTime!=null&&!startTime.isEmpty())&&(endTime!=null&&!endTime.isEmpty())){
				try {
					criterionList.add(Restrictions.between("createTime", ConvertTools.dateString2Long(startTime), ConvertTools.dateString2Long(endTime)));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			for (Criterion criterion : criterionList) {
				criteria.add(criterion);
			}
			return serviceOrderDao.findPage(criteria,rdtp.iDisplayStart, rdtp.iDisplayLength);
		}
		return serviceOrderDao.findPage(criteria,rdtp.iDisplayStart, rdtp.iDisplayLength);
	}

	public void delete(String hql, Map<String, Object> params) {
		// TODO Auto-generated method stub
		serviceOrderDao.delete(hql, params);
	}

	public void update(String hql, Map<String, Object> params) {
		// TODO Auto-generated method stub
		serviceOrderDao.update(hql,params);
	}

	public List<TserviceOrder> select(String hql, Map<String, Object> params) {
		// TODO Auto-generated method stub
		return serviceOrderDao.select(hql, params);
	}

}
