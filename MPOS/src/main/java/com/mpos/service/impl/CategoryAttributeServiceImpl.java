package com.mpos.service.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mpos.dao.CategoryAttributeDao;
import com.mpos.dto.TcategoryAttribute;
import com.mpos.model.DataTableParamter;
import com.mpos.model.PagingData;
import com.mpos.service.CategoryAttributeService;
@Service
public class CategoryAttributeServiceImpl implements CategoryAttributeService {
	@Autowired
	CategoryAttributeDao attributeDao;
	@Override
	public void createCategoryAttribute(TcategoryAttribute attribute) {
		// TODO Auto-generated method stub
		attributeDao.create(attribute);
	}

	@Override
	public void deleteCategoryAttribute(TcategoryAttribute attribute) {
		// TODO Auto-generated method stub
		attributeDao.delete(attribute);
	}

	@Override
	public void updateCategoryAttribute(TcategoryAttribute attribute) {
		// TODO Auto-generated method stub
		attributeDao.update(attribute);
	}

	@Override
	public TcategoryAttribute getCategoryAttribute(Integer attributeId) {
		// TODO Auto-generated method stub
		return attributeDao.get(attributeId);
	}

	@Override
	public PagingData loadAttributeList(String id, DataTableParamter rdtp) {
		// TODO Auto-generated method stub
		Criteria criteria = attributeDao.createCriteria();
		Criterion criterion = Restrictions.eq("categoryId.categoryId", Integer.valueOf(id));
		criteria.add(criterion);
		criteria.addOrder(Order.asc("sort"));
		return attributeDao.findPage(criteria, rdtp.iDisplayStart, rdtp.iDisplayLength);
	}

	@Override
	public void deleteAttributeByIds(Integer[] idArr) {
		// TODO Auto-generated method stub
		attributeDao.deleteAll(idArr);
	}

}
