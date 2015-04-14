package com.mpos.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.mpos.dao.base.BaseDao;
import com.mpos.dto.Tcategory;
import com.mpos.dto.Tlanguage;
import com.mpos.dto.TlocalizedField;
import com.mpos.dto.Tmenu;
@Repository
public class CategoryDao extends BaseDao<Tcategory> {
	
    public List<Tcategory> getAll(){
    	String hql="from Tcategory where status=?";
    	Query query = currentSession().createQuery(hql);
    	query.setParameter(0, true);
		List<Tcategory> list=query.list();
		return list;
    }
    public List<Tcategory> getAll(Integer type,Integer storeId){
    	String hql="from Tcategory where status=? and type=? and storeId=?";
    	Query query = currentSession().createQuery(hql);
    	query.setParameter(0, true);
    	query.setParameter(1, type);
    	query.setParameter(2, storeId);
    	List<Tcategory> list=query.list();
		
		return list;
    }
}
