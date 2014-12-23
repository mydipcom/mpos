package com.mpos.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.mpos.dao.base.BaseDao;
import com.mpos.dto.TlocalizedField;
@Repository
public class LocalizedFieldDao extends BaseDao<TlocalizedField>{
	
	public List<TlocalizedField> find(String hql, Map<String, Object> params) {
		Query query = currentSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				query.setParameter(key, params.get(key));
			}
		}
		return query.list();
	}

}
