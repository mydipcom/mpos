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
import com.mpos.commons.SecurityTools;
import com.mpos.commons.SystemConfig;
import com.mpos.dao.AdminUserDao;
import com.mpos.dao.StoreDao;
import com.mpos.dto.TadminUser;
import com.mpos.dto.Tstore;
import com.mpos.model.DataTableParamter;
import com.mpos.model.PagingData;
import com.mpos.service.StoreService;
@Service
public class StoreServiceImpl implements StoreService {

	@Autowired
	private StoreDao storeDao;
	@Autowired
	private AdminUserDao adminUserDao;
	public void save(Tstore store) {
		// TODO Auto-generated method stub
		storeDao.save(store);
	}

	public void delete(Tstore store) {
		// TODO Auto-generated method stub
		storeDao.delete(store);
	}

	public void update(Tstore store) {
		// TODO Auto-generated method stub
		storeDao.update(store);
	}

	public PagingData loadList(DataTableParamter rdtp) {
		String searchJsonStr = rdtp.getsSearch();
		Criteria criteria = storeDao.createCriteria();
		criteria.addOrder(Order.desc("storeId"));
		criteria.add(Restrictions.eq("status", true));
		if(searchJsonStr!=null&&!searchJsonStr.isEmpty()){
			List<Criterion> criterionList = new ArrayList<Criterion>();
			JSONObject json = (JSONObject) JSONObject.parse(searchJsonStr);
			Set<String> keys = json.keySet();
			for(String key:keys){
				String value = json.getString(key);
				if(value!=null&&!value.isEmpty()){
					if(key.equals("storeName")){
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
			return storeDao.findPage(criteria,rdtp.iDisplayStart, rdtp.iDisplayLength);
		}
		return storeDao.findPage(criteria,rdtp.iDisplayStart, rdtp.iDisplayLength);
	}

	public void delete(String hql, Map<String, Object> params) {
		// TODO Auto-generated method stub
		storeDao.delete(hql, params);
	}

	public void update(String hql, Map<String, Object> params) {
		// TODO Auto-generated method stub
		storeDao.update(hql, params);
	}

	public List<Tstore> select(String hql, Map<String, Object> params) {
		// TODO Auto-generated method stub
		return storeDao.select(hql, params);
	}

	public void cacheStoreTaken() {
		Map<String, Object> params = new HashMap<String, Object>();
		String userHql = "select new TadminUser(email,storeId) from TadminUser where status =:status";
		params.put("status", true);
		List<TadminUser> users = adminUserDao.select(userHql, params);
		if(users!=null&&users.size()>0){
			for (TadminUser user : users) {
				params.clear();
				params.put("status", true);
				params.put("storeId", user.getStoreId());
				String storeHql = "select new Tstore(publicKey) from Tstore where status=:status and storeId=:storeId";
				Tstore store = storeDao.getOne(storeHql, params);
				if(store!=null){
					String email = user.getEmail();
					String publicKey = store.getPublicKey();
					if(!email.isEmpty()&&!publicKey.isEmpty()){
						SystemConfig.STORE_TAKEN_MAP.put(SecurityTools.MD5(email+publicKey), user.getStoreId());
					}
				}
			}
		}
	}

	public void delete(Integer storeId) {
		// TODO Auto-generated method stub
		storeDao.delete(storeId);
	}

	public Object getObject(String hql, Map<String, Object> params) {
		// TODO Auto-generated method stub
		return storeDao.getObject(hql, params);
	}

	public Tstore get(Integer storeId) {
		// TODO Auto-generated method stub
		return storeDao.get(storeId);
	}

}
