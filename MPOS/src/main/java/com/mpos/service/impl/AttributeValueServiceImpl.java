package com.mpos.service.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mpos.dao.AttributeValueDao;
import com.mpos.dto.TattributeValue;
import com.mpos.service.AttributeValueService;

@Service
public class AttributeValueServiceImpl implements AttributeValueService {
	@Autowired
	AttributeValueDao attributeValueDao;
	
	public void createAttributeValue(TattributeValue attributeValue) {
		attributeValueDao.create(attributeValue);

	}

	
	public void deleteAttributeValue(TattributeValue attributeValue) {
		attributeValueDao.delete(attributeValue);

	}

	
	public void updateAttributeValue(TattributeValue attributeValue) {
		attributeValueDao.update(attributeValue);

	}

	
	public TattributeValue getAttributeValue(Integer valueId) {
		return attributeValueDao.get(valueId);
	}

	
	@SuppressWarnings("unchecked")
	public List<TattributeValue> loadAttributeValuesByAttrId(Integer attrId) {
		Criteria criteria=attributeValueDao.createCriteria();
		criteria.add(Restrictions.eq("attributeId", attrId)).addOrder(Order.asc("sort"));
		return criteria.list();
	}

	
	public void deleteAttributeValueByIds(Integer[] idArr) {
		attributeValueDao.deleteAll(idArr);
	}

	
	public List<TattributeValue> getAllAttributeValue() {
		return attributeValueDao.LoadAll();		
	}	

}
