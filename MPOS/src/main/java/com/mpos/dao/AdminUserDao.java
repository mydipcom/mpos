package com.mpos.dao;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.mpos.dao.base.BaseDao;
import com.mpos.dto.TadminUser;

@Repository
public class AdminUserDao extends BaseDao<TadminUser> {
	
	public void delete(String ids[]){
		Query   query= currentSession().createQuery("delete from TadminUser where email in (:ids)");
		Query   delete= currentSession().createQuery("delete from TadminInfo where adminId in (:ids)");
		delete.setParameterList("ids", ids);
		query.setParameterList("ids", ids);
		query.executeUpdate();
		delete.executeUpdate();
	}
	public void updateUserStatus(String[] ids,boolean status){
			Query   query= currentSession().createQuery("update TadminUser set status=:status where email in (:ids)");
			query.setParameter("status",status);
			query.setParameterList("ids", ids);
			query.executeUpdate();
	}
}
