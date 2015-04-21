package com.mpos.dao;


import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.mpos.dao.base.BaseDao;
import com.mpos.dto.ImageModel;
import com.mpos.dto.Tstore;
@Repository
public class StoreDao extends BaseDao<Tstore>{
	
	public void updateImage(ImageModel model){
		Integer type = model.getType();
		String hql = "";
		if(ImageModel.BACK==type){
			hql = "update Tstore set storeBackground=:image where storeId=:storeId";
		}else{
			hql = "update Tstore set storeLogo=:image where storeId=:storeId";
		}
		Query query = currentSession().createQuery(hql);
		query.setBinary("image", model.getImage());
		query.setInteger("storeId", model.getStoreId());
		query.executeUpdate();
	}

}
