package com.mpos.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mpos.dao.ProductReleaseDao;
import com.mpos.dto.TproductRelease;
import com.mpos.model.DataTableParamter;
import com.mpos.model.PagingData;
import com.mpos.service.ProductReleaseService;

@Service
public class ProductReleaseServiceImpl implements ProductReleaseService {

	@Autowired
	private  ProductReleaseDao productReleaseDao;
	
	@SuppressWarnings("unchecked")
	public List<TproductRelease> getUpdatedRelease(Integer verId,Integer storeId) {
		Criteria criteria=productReleaseDao.createCriteria();
		return criteria.add(Restrictions.eq("isPublic", true))
				.add(Restrictions.gt("id", verId))	.add(Restrictions.eq("storeId", storeId))	
				.list();		
	}

	public void createOrupdateProductRelease(Integer id,Integer storeId) {
		TproductRelease productrelease;
		Integer verId=productReleaseDao.getMaxId("id",storeId);
		if (verId!=0) {
			 productrelease=productReleaseDao.get(verId);
			 if(productrelease!=null&&!productrelease.isIsPublic()){
				 String ids=productrelease.getProducts();
				 String products[]=ids.split(",");
				 for (int i = 0; i < products.length; i++) {
					if(products[i].equals(id.toString())){
						return;
					}
				}
				 productrelease.setProducts(ids+","+id);
				 productReleaseDao.update(productrelease);	 
			 }else {
				 productrelease.setStoreId(storeId);
				 productrelease.setProducts(id.toString());
				 productrelease.setIsPublic(false);
				 productReleaseDao.create(productrelease);
			}
		}
		else {
			TproductRelease productrelease1=new TproductRelease();
			productrelease1.setProducts(id.toString());
			productrelease1.setIsPublic(false);
			productrelease1.setStoreId(storeId);
			productReleaseDao.create(productrelease1);
		}
		
			
		
		
	}

	
	public TproductRelease getLatestPublished(Integer storeId) {
		// TODO Auto-generated method stub
		return productReleaseDao.getLatestPublished(storeId);
	}
	
	public PagingData loadproductReleaseList(DataTableParamter rdtp) {
		// TODO Auto-generated method stub
		return productReleaseDao.findPage("id", false, rdtp.iDisplayStart, rdtp.iDisplayLength);
	}

	
	public void publicreleasebyid(Integer ids,Integer storeId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", ids);
		params.put("storeId", storeId);
		params.put("status", true);
		params.put("time", System.currentTimeMillis());
		String hqlUpdate = "update TproductRelease set isPublic=:status,publicTime=:time where storeId=:storeId and id<=:id";
		productReleaseDao.update(hqlUpdate, params);
	}

	
	public TproductRelease getbyId(Integer ids) {
		
		return productReleaseDao.get(ids);
	}

	public TproductRelease getUnPublished(Integer storeId) {
		
		return productReleaseDao.getUnPublish(storeId);
	}

	public void save(TproductRelease release) {
		// TODO Auto-generated method stub
		productReleaseDao.create(release);
	}


}
