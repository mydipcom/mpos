package com.mpos.dao;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.mpos.dao.base.BaseDao;
import com.mpos.dto.TadminUser;

@Repository
public class AdminUserDao extends BaseDao<TadminUser> {
	public void activateusers(String[] ids){
			Query   query= currentSession().createQuery("update TadminUser set status=:status where adminId in (:ids)");
			query.setParameter("status",true);
			query.setParameterList("ids", ids);
			query.executeUpdate();
	}
	public void deactivateusers(String[] ids){
		Query   query= currentSession().createQuery("update TadminUser set status=:status where adminId in (:ids)");
		query.setParameter("status",false);
		query.setParameterList("ids", ids);
		query.executeUpdate();
	}			
}
