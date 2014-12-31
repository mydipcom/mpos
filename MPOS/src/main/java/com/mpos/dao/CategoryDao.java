package com.mpos.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.mpos.dao.base.BaseDao;
import com.mpos.dto.Tcategory;
@Repository
public class CategoryDao extends BaseDao<Tcategory> {
	
    public List<Tcategory> getAll(){
    	String hql="from Tcategory where status=?";
    	Query query = currentSession().createQuery(hql);
    	query.setParameter(0, true);
		List<Tcategory> list=query.list();
		return list;
    }
	
}
