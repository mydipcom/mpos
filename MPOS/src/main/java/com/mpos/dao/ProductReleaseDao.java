package com.mpos.dao;

// Generated Oct 29, 2014 11:20:20 AM by Hibernate Tools 3.4.0.CR1

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.mpos.dao.base.BaseDao;
import com.mpos.dto.TproductRelease;

/**
 * Home object for domain model class Tsetting.
 * @see com.bps.dto.Tsetting
 * @author Hibernate Tools
 */
@Repository
public class ProductReleaseDao extends BaseDao<TproductRelease> {
	public TproductRelease getLatestPublished(){
		String hql="from TproductRelease where id=(select max(id) from TproductRelease where isPublic=?)";
		Query query=currentSession().createQuery(hql);
		query.setParameter(0, true);
		return (TproductRelease) query.uniqueResult();  
	}
	public TproductRelease getUnPublish(){
		String hql="from TproductRelease where id=(select max(id) from TproductRelease where isPublic=?)";
		Query query=currentSession().createQuery(hql);
		query.setParameter(0, false);
		return (TproductRelease) query.uniqueResult();  
	}
}
