package com.mpos.service.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder.In;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mpos.dao.ProductReleaseDao;
import com.mpos.dto.TproductRelease;
import com.mpos.service.ProductReleaseService;

@Service
public class ProductReleaseServiceImpl implements ProductReleaseService {

	@Autowired
	private  ProductReleaseDao productReleaseDao;
	
	public List<TproductRelease> getUpdatedRelease(Integer verId) {
		Criteria criteria=productReleaseDao.createCriteria();
		return criteria.add(Restrictions.eq("isPublic", true))
				.add(Restrictions.gt("id", verId))				
				.list();		
	}

	public void createOrupdateProductRelease(Integer id) {
		TproductRelease productrelease;
		Integer verId=productReleaseDao.getMaxintergerValue("id");
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
				 productrelease.setProducts(id.toString());
				 productrelease.setIsPublic(false);
				 productReleaseDao.create(productrelease);
			}
		}
		else {
			TproductRelease productrelease1=new TproductRelease();
			productrelease1.setProducts(id.toString());
			productrelease1.setIsPublic(false);
			productReleaseDao.create(productrelease1);
		}
		
			
		
		
	}

	@Override
	public TproductRelease getLatestPublished() {
		// TODO Auto-generated method stub
		return productReleaseDao.getLatestPublished();
	}

}
