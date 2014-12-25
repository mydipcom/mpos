package com.mpos.dao;


import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.mpos.dao.base.BaseDao;
import com.mpos.dto.Tcategory;
import com.mpos.dto.Tproduct;
@Repository
public class GoodsDao extends BaseDao<Tproduct>{

	public Tproduct findprodctbyname(String productName){
		String hql="from Tproduct where productName=?";
		Query   query= currentSession().createQuery(hql);
		query.setParameter(0, productName);
		List<Tproduct> list=query.list();
		Tproduct product=list.get(0);
		return product;
	}
}
