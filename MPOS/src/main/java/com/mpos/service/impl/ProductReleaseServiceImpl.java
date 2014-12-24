package com.mpos.service.impl;

import java.util.List;

import org.hibernate.Criteria;
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
		return null;
	}

}
