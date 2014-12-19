package com.mpos.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.mpos.dao.base.BaseDao;
import com.mpos.dto.Tcategory;
import com.mpos.dto.Tmenu;
@Repository
public class MenuDao extends BaseDao<Tmenu> {
	public List<Tmenu> getAll(){
		String hql="from Tmenu";
		Query query=currentSession().createQuery(hql);
		List<Tmenu> list=query.list();
		return list;
	}
}
