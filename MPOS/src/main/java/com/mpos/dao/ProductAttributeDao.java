package com.mpos.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.mpos.dao.base.BaseDao;
import com.mpos.dto.TproductAttribute;
import com.mpos.dto.TproductAttributeId;
@Repository
public class ProductAttributeDao extends BaseDao<TproductAttribute>{

	public TproductAttribute getByAttributeid(TproductAttributeId productAttributeId){
		String hql="from TproductAttribute where attribute_id=? and product_id=?";
		Query query=currentSession().createQuery(hql);
		query.setParameter(0, productAttributeId.getCategoryAttribute().getAttributeId());
		query.setParameter(1, productAttributeId.getProduct().getId());
		List<TproductAttribute> list=query.list();
		TproductAttribute productAttribute=list.get(0);
		System.out.print("1");
		return null;
	}
}
