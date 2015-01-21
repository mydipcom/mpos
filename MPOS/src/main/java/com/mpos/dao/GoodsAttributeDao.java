package com.mpos.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.mpos.dao.base.BaseDao;
import com.mpos.dto.TgoodsAttribute;
import com.mpos.dto.TproductAttribute;
@Repository
public class GoodsAttributeDao extends BaseDao<TgoodsAttribute>{
	public TproductAttribute getAttribute(Integer productid,Integer AttributeId){
		String hql="from TproductAttribute where attribute_id=? and product_id=?";
		Query query=currentSession().createQuery(hql);
		query.setParameter(0, AttributeId);
		query.setParameter(1, productid);
		List<TproductAttribute> list=query.list();
		TproductAttribute productAttribute=new TproductAttribute();
		if(list.size()==0){
			return null;
		}
		else {
			productAttribute=list.get(0);
			 return productAttribute;
		}
	}
	public List<TproductAttribute> getAttributebyproductid(Integer productid){
		String hql="from TproductAttribute where product_id=?";
		Query query=currentSession().createQuery(hql);
		query.setParameter(0, productid);
		List<TproductAttribute> list=query.list();
		if(list.size()==0){
			return null;
		}
		else {
			 return list;
		}
	}
	public void  DeleteAttributebyproductid(Integer productid){
		String hql="delete TproductAttribute where product_id=?";
		Query query=currentSession().createQuery(hql);
		query.setParameter(0, productid);
		query.executeUpdate();
		System.out.println();
		
	}
}
