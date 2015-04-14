package com.mpos.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.mpos.dao.base.BaseDao;
import com.mpos.dto.TcategoryAttribute;
@Repository
public class CategoryAttributeDao extends BaseDao<TcategoryAttribute> {
	@SuppressWarnings("unchecked")
	public List<TcategoryAttribute> getAttributesbycategoryid(Integer id){
		String hql="from TcategoryAttribute where category_id=?";
		Query query=currentSession().createQuery(hql);
		query.setParameter(0, id);
		List<TcategoryAttribute> list=query.list();
		return list;
	}
}
