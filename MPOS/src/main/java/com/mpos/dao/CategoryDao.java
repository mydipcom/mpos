package com.mpos.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.mpos.dao.base.BaseDao;
import com.mpos.dto.Tcategory;
@Repository
public class CategoryDao extends BaseDao<Tcategory> {
	
    public List<Tcategory> getAll(){
    	String hql="from Tcategory";
    	Query query = currentSession().createQuery(hql);
		List<Tcategory> list=query.list();
		return list;
    }
	
}
