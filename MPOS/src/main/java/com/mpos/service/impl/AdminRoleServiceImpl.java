/**   
 * @Title: AdminUserServiceImpl.java 
 * @Package com.uswop.service 
 *
 * @Description: User Points Management System
 * 
 * @date Sep 11, 2014 7:21:25 PM
 * @version V1.0   
 */ 
package com.mpos.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.mpos.dao.AdminRoleDao;
import com.mpos.dto.TadminRole;
import com.mpos.model.DataTableParamter;
import com.mpos.model.PagingData;
import com.mpos.service.AdminRoleService;

/** 
 * <p>Types Description</p>
 * @ClassName: AdminUserServiceImpl 
 * @author Phills Li 
 * 
 */
@Service
public class AdminRoleServiceImpl implements AdminRoleService {
	@Autowired
	private AdminRoleDao adminRoleDao;
		
	
	/**
	 * (non-Javadoc)
	 * <p>Title: getAdminRoleById</p> 
	 * <p>Description: </p> 
	 * @param userId
	 * @return TadminRole
	 * @see com.bps.service.AdminRoleService#getAdminUserById(int) 
	 */
	public TadminRole getAdminRoleById(int roleId) {
		return adminRoleDao.get(roleId);
	}
	
	/**
	 * (non-Javadoc)
	 * <p>Title: getAllAdminRoles</p> 
	 * <p>Description: </p> 
	 * @return 
	 * @see com.bps.service.AdminRoleService#getAllAdminRoles()
	 */
	public List<TadminRole> getAllAdminRoles(){
		return adminRoleDao.findBy("status", true);
	}

	/**
	 * (non-Javadoc)
	 * <p>Title: createAdminRole</p> 
	 * <p>Description: </p> 
	 * @param adminRole 
	 * @see com.bps.service.AdminRoleService#createAdminUser(com.bps.dto.TadminUser) 
	 */
	public void createAdminRole(TadminRole adminRole) {
		adminRoleDao.create(adminRole);
	}

	/**
	 * (non-Javadoc)
	 * <p>Title: updateAdminRole</p> 
	 * <p>Description: </p> 
	 * @param adminRole 
	 * @see com.bps.service.AdminRoleService#updateAdminUser(com.bps.dto.TadminUser) 
	 */
	public void updateAdminRole(TadminRole adminRole) {
		adminRoleDao.update(adminRole);

	}	
	
	/**
	 * (non-Javadoc)
	 * <p>Title: deleteAdminRole</p> 
	 * <p>Description: </p> 
	 * @param adminNodes 
	 * @see com.bps.service.AdminRoleService#deleteAdminRole(com.bps.dto.TadminNodes) 
	 */
	public void deleteAdminRole(TadminRole adminRole) {
		adminRoleDao.delete(adminRole);		
	}

	/**
	 * (non-Javadoc)
	 * <p>Title: deleteAdminRoleById</p> 
	 * <p>Description: </p> 
	 * @param id 
	 * @see com.bps.service.AdminRoleService#deleteAdminRoleById(int)
	 */
	public void deleteAdminRoleById(int id){
		adminRoleDao.delete(id);
	}
	
	/**
	 * (non-Javadoc)
	 * <p>Title: deleteAdminRolesByIds</p> 
	 * <p>Description: </p> 
	 * @param ids 
	 * @see com.bps.service.AdminRoleService#deleteAdminNodesByIds(int[])
	 */
	public void deleteAdminRolesByIds(Integer[] ids){		
		adminRoleDao.deleteAll(ids);
	}
	
	/**
	 * (non-Javadoc)
	 * <p>Title: loadAdminRolesList</p> 
	 * <p>Description: </p> 
	 * @param rdtp
	 * @return 
	 * @see com.bps.service.AdminRoleService#loadAdminRolesList(com.bps.model.DataTableParamter)
	 */
	public PagingData loadAdminRolesList(DataTableParamter dtp) {
		String searchJsonStr = dtp.getsSearch();
		Criteria criteria = adminRoleDao.createCriteria();
		criteria.add(Restrictions.ne("roleId", 1));
		//criteria.add(Restrictions.ne("roleId", 4));
		if (searchJsonStr != null && !searchJsonStr.isEmpty()) {
			List<Criterion> criterionList = new ArrayList<Criterion>();
			JSONObject json = (JSONObject) JSONObject.parse(searchJsonStr);
			Set<String> keys = json.keySet();
			for (String key : keys) {
				String value = json.getString(key);
				if (value != null && !value.isEmpty()) {
					if(key=="roleName"){
						criterionList.add(Restrictions.like(key, json.getString(key), MatchMode.ANYWHERE));
					}else if(key=="status"){
						criterionList.add(Restrictions.eq(key, json.getBoolean(key)));
					}else{
						criterionList.add(Restrictions.eq(key, json.get(key)));
					}					
				}
			}
			for (Criterion criterion : criterionList) {
				criteria.add(criterion);
			}
			return adminRoleDao.findPage(criteria, dtp.iDisplayStart,dtp.iDisplayLength);
		}
		return adminRoleDao.findPage(criteria,dtp.iDisplayStart, dtp.iDisplayLength);
	}

}
