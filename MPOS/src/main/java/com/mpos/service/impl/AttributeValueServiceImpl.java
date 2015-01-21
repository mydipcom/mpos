package com.mpos.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mpos.commons.SystemConstants;
import com.mpos.dao.AttributeValueDao;
import com.mpos.dao.LocalizedFieldDao;
import com.mpos.dto.TattributeValue;
import com.mpos.dto.Tcategory;
import com.mpos.dto.Tlanguage;
import com.mpos.dto.TlocalizedField;
import com.mpos.service.AttributeValueService;

@Service
public class AttributeValueServiceImpl implements AttributeValueService {
	@Autowired
	private AttributeValueDao attributeValueDao;
	
	@Autowired
	private LocalizedFieldDao localizedFieldDao;
	
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
	public List<TattributeValue> getattributeValuesbyattributeid(Integer id,Tlanguage language) {
		Criteria criteria=attributeValueDao.createCriteria();
		List<TattributeValue> list= criteria.add(Restrictions.eq("attributeId", id))				
				.addOrder(Order.asc("sort")).list();
		if (list!=null&&list.size()>0) {
				for (TattributeValue attributeValue : list) {
					Map<String, Object> ssMap=new HashMap<String, Object>();
					ssMap.put("language", language);
					ssMap.put("entityId", attributeValue.getValueId());
					ssMap.put("tableName",SystemConstants.TABLE_NAME_ATTRIBUTE_VALUE);
					ssMap.put("tableField",SystemConstants.TABLE_FIELD_VALUE);
					List<TlocalizedField> localizedFields= localizedFieldDao.find("from TlocalizedField where language=:language and entityId=:entityId and tableName=:tableName and tableField=:tableField", ssMap);
					if (localizedFields.size()>0) {
						TlocalizedField localizedField=localizedFields.get(0);
						if(!localizedField.getLocaleValue().isEmpty()&&localizedField.getLocaleValue()!=null){
							attributeValue.setValue(localizedField.getLocaleValue());
						
					}
					}	
				}
		}
		return list;
		
	}
	

}
