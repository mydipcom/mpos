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
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.mpos.commons.BaiduPushTool;
import com.mpos.commons.BaiduPushTool.Notification;
import com.mpos.commons.ConvertTools;
import com.mpos.commons.SecurityTools;
import com.mpos.commons.SystemConfig;
import com.mpos.dao.AdminUserDao;
import com.mpos.dao.StoreDao;
import com.mpos.dto.ImageModel;
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
	
	public void confine(Criteria criteria){
		String alias = "store_";
		ProjectionList pList = Projections.projectionList();  
		pList.add(Projections.property(alias + "." + "storeId").as("storeId"));  
		pList.add(Projections.property(alias + "." + "storeName").as("storeName"));  
		pList.add(Projections.property(alias + "." + "clientPwd").as("clientPwd"));  
		pList.add(Projections.property(alias + "." + "storeCurrency").as("storeCurrency"));  
		pList.add(Projections.property(alias + "." + "status").as("status"));  
		pList.add(Projections.property(alias + "." + "publicKey").as("publicKey"));  
		pList.add(Projections.property(alias + "." + "storeLangId").as("storeLangId"));  
		pList.add(Projections.property(alias + "." + "status").as("status"));  
		pList.add(Projections.property(alias + "." + "serviceId").as("serviceId"));  
		pList.add(Projections.property(alias + "." + "printType").as("printType"));  
		pList.add(Projections.property(alias + "." + "serviceDate").as("serviceDate"));  
		pList.add(Projections.property(alias + "." + "createTime").as("createTime"));  
		criteria.setProjection(pList);  
	}
	
	public PagingData load(DataTableParamter rdtp){
		List<Criterion> criterionList = new ArrayList<Criterion>();
		Criteria criteria = storeDao.createCriteria();
		String searchJsonStr = rdtp.getsSearch();
		criteria.add(Restrictions.ne("storeId", 0));
		criteria.addOrder(Order.desc("storeId"));
		if(searchJsonStr!=null&&!searchJsonStr.isEmpty()){
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
		params.put("status", true);
		String storeHql = "select new Tstore(storeId,publicKey,serviceId) from Tstore where status=:status";
		List<Tstore> stores = storeDao.select(storeHql, params);
		if(stores!=null&&stores.size()>0){
			for (Tstore store : stores) {
				String key = store.getPublicKey();
				if(key!=null&&!key.isEmpty()){
					String store_code= ConvertTools.bw(store.getStoreId(), 8, "S");
					SystemConfig.STORE_TAKEN_MAP.put(SecurityTools.MD5(store_code+key), store.getStoreId());
				}
			}
		}
		/*String userHql = "select new TadminUser(email,storeId) from TadminUser where status =:status";
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
		}*/
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

	public Tstore selectOne(String hql, Map<String, Object> params) {
		// TODO Auto-generated method stub
		return storeDao.getOne(hql, params);
	}

	public void updateImage(String hql, Map<String, Object> params) {
		// TODO Auto-generated method stub
		storeDao.updateImage(hql, params);
	}

	public void updateImage(ImageModel model) {
		// TODO Auto-generated method stub
		storeDao.updateImage(model);
	}

	public List<Tstore> loadAll() {
		// TODO Auto-generated method stub
		return storeDao.LoadAll();
	}

	public void deleteByStoreId(Integer storeId,String adminId) {
		// TODO Auto-generated method stub
		storeDao.delete(storeId);
		if(adminId!=null){
			String infoHql = "delete from TadminInfo where adminId=:adminId";
			Map<String, Object> aa = new HashMap<String, Object>();
			aa.put("adminId", adminId);
			storeDao.delete(infoHql, aa);
		}
		String tableHql = "delete from Ttable where storeId=:storeId";
		String adminHql = "delete from TadminUser where storeId=:storeId";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("storeId", storeId);
		storeDao.delete(tableHql, params);
		storeDao.delete(adminHql, params);
	}

	public List<Tstore> loadStoreNameAndId() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", true);
		params.put("storeId", 0);
		String hql = "select new Tstore(storeId,storeName) from Tstore where status=:status and storeId !=:storeId";
		return storeDao.select(hql, params);
	}
	
	public void validStoreDate(){
		long now = System.currentTimeMillis();
		List<Tstore> stores = storeDao.select("select new Tstore(storeId,serviceDate,status) from Tstore where status=true", null);
		Map<String, Object> params = new HashMap<String, Object>();
		if(stores!=null&&stores.size()>0){
			for (Tstore store : stores) {
				if(store.getServiceDate()>now){
					params.clear();
					params.put("status", false);
					params.put("storeId", store.getStoreId());
					storeDao.update("update Tstore set status=:status where storeId=:storeId", params);
					BaiduPushTool.pushMsgToTag(new Notification(10002), store.getStoreId()+"", BaiduPushTool.IOS_TYPE);
				}
				}
			}
		}

}
