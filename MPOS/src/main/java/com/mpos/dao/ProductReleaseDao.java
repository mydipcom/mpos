package com.mpos.dao;

// Generated Oct 29, 2014 11:20:20 AM by Hibernate Tools 3.4.0.CR1

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
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
	public TproductRelease getLatestPublished(Integer storeId){
		String hql="from TproductRelease where id=(select max(id) from TproductRelease where isPublic=:status and storeId=:storeId)";
		Query query=currentSession().createQuery(hql);
		query.setParameter("status", true);
		query.setParameter("storeId", storeId);
		return (TproductRelease) query.uniqueResult();  
	}
	public TproductRelease getUnPublish(Integer storeId){
		String hql="from TproductRelease where id=(select max(id) from TproductRelease where isPublic=:status and storeId=:storeId)";
		Query query=currentSession().createQuery(hql);
		query.setParameter("status", false);
		query.setParameter("storeId", storeId);
		return (TproductRelease) query.uniqueResult();  
	}
	public Integer getMaxId(String propertyName,Integer storeId){
		Criteria criteria =createCriteria(); 
		criteria.add(Restrictions.eq("storeId", storeId));
		criteria.setProjection(Projections.max(propertyName));
		return (Integer)criteria.uniqueResult();
	}
}
