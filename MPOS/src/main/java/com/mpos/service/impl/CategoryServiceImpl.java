package com.mpos.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.mpos.commons.SystemConstants;
import com.mpos.dao.AttributeValueDao;
import com.mpos.dao.CategoryAttributeDao;
import com.mpos.dao.CategoryDao;
import com.mpos.dao.LocalizedFieldDao;
import com.mpos.dto.TattributeValue;
import com.mpos.dto.Tcategory;
import com.mpos.dto.TcategoryAttribute;
import com.mpos.dto.Tlanguage;
import com.mpos.dto.TlocalizedField;
import com.mpos.model.DataTableParamter;
import com.mpos.model.PagingData;
import com.mpos.service.CategoryService;
@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	CategoryDao categoryDao;
	@Autowired
	CategoryAttributeDao attributeDao;
	@Autowired
	AttributeValueDao attributeValueDao;
	@Autowired
	LocalizedFieldDao localizedFieldDao;
	
	public void createCategory(Tcategory category) {
		// TODO Auto-generated method stub
		categoryDao.create(category);
		List<TlocalizedField> categoryNameLocaleList=category.getCategoryName_locale();			
		for (TlocalizedField localizedField : categoryNameLocaleList) {
			if(localizedField.getLocaleValue()!=null&&!localizedField.getLocaleValue().isEmpty()){
				localizedField.setEntityId(category.getCategoryId());
				localizedField.setTableName(SystemConstants.TABLE_NAME_CATEGORY);
				localizedField.setTableField(SystemConstants.TABLE_FIELD_NAME);
				localizedFieldDao.saveOrUpdate(localizedField);
			}				
		}
		List<TlocalizedField> categoryDescrLocaleList=category.getCategoryDescr_locale();			
		for (TlocalizedField localizedField : categoryDescrLocaleList) {
			if(localizedField.getLocaleValue()!=null&&!localizedField.getLocaleValue().isEmpty()){
				localizedField.setEntityId(category.getCategoryId());
				localizedField.setTableName(SystemConstants.TABLE_NAME_CATEGORY);
				localizedField.setTableField(SystemConstants.TABLE_FIELD_DESCR);
				localizedFieldDao.saveOrUpdate(localizedField);
			}				
		}
	}

	
	public void deleteCategory(Tcategory category) {
		TlocalizedField localizedField=new TlocalizedField();
		localizedField.setEntityId(category.getCategoryId());
		categoryDao.delete(category);		
		localizedFieldDao.delete(localizedField);
	}

	
	public void updateCategory(Tcategory category) {
		// TODO Auto-generated method stub
		categoryDao.update(category);		
		List<TlocalizedField> categoryNameLocaleList=category.getCategoryName_locale();			
		for (TlocalizedField localizedField : categoryNameLocaleList) {
			if(localizedField.getLocaleValue()!=null&&!localizedField.getLocaleValue().isEmpty()){
				localizedField.setEntityId(category.getCategoryId());
				localizedField.setTableName(SystemConstants.TABLE_NAME_CATEGORY);
				localizedField.setTableField(SystemConstants.TABLE_FIELD_NAME);
				localizedFieldDao.saveOrUpdate(localizedField);
			}				
		}
		List<TlocalizedField> categoryDescrLocaleList=category.getCategoryDescr_locale();			
		for (TlocalizedField localizedField : categoryDescrLocaleList) {
			if(localizedField.getLocaleValue()!=null&&!localizedField.getLocaleValue().isEmpty()){
				localizedField.setEntityId(category.getCategoryId());
				localizedField.setTableName(SystemConstants.TABLE_NAME_CATEGORY);
				localizedField.setTableField(SystemConstants.TABLE_FIELD_DESCR);
				localizedFieldDao.saveOrUpdate(localizedField);
			}				
		}
	}		
	
	public Tcategory getCategory(Integer categoryId) {
		// TODO Auto-generated method stub
		return categoryDao.get(categoryId);
	}

	
	public PagingData loadCategoryList(DataTableParamter rdtp) {
		String searchJsonStr = rdtp.getsSearch();
		Criteria criteria = categoryDao.createCriteria();
		criteria.addOrder(Order.desc("categoryId"));
		criteria.add(Restrictions.eq("status", true));
		if(searchJsonStr!=null&&!searchJsonStr.isEmpty()){
			List<Criterion> criterionList = new ArrayList<Criterion>();
			JSONObject json = (JSONObject) JSONObject.parse(searchJsonStr);
			Set<String> keys = json.keySet();
			for(String key:keys){
				String value = json.getString(key);
				if(value!=null&&!value.isEmpty()){
					if(key.equals("name")){
						criterionList.add(Restrictions.like(key, json.getString(key), MatchMode.ANYWHERE));
					}else if(key.equals("status")){
						criterionList.add(Restrictions.eq(key, json.getBoolean(key)));
					}else{
						criterionList.add(Restrictions.eq(key, json.get(key)));
					}
				}
			}
			for (Criterion criterion : criterionList) {
				criteria.add(criterion);
			}
			return categoryDao.findPage(criteria,rdtp.iDisplayStart, rdtp.iDisplayLength);
		}
		return categoryDao.findPage(criteria,rdtp.iDisplayStart, rdtp.iDisplayLength);
	}

	
	public void deleteCategoryByIds(Integer[] ids) {		
		if(ids!=null&&ids.length>0){
			for (Integer id : ids) {
				Tcategory cate = categoryDao.get(id);
				cate.setStatus(false);
				categoryDao.update(cate);
			}
		}
	}
	
	public void cloneCategoryByIds(Integer[] ids) {		
		
			for (Integer id : ids) {
				Tcategory category = categoryDao.get(id);									
				List<TcategoryAttribute> categoryAttributeList=attributeDao.findBy("categoryId", category);
				if(categoryAttributeList!=null&&categoryAttributeList.size()>0){
					//Clone all of the category
					Tcategory newCategory=new Tcategory();
					BeanUtils.copyProperties(category, newCategory);
					newCategory.setName("Clone of "+category.getName());
					newCategory.setCategoryId(null);
					categoryDao.create(newCategory);
				
				
				
				List<TlocalizedField> localeFieldList=localizedFieldDao.findBy(new String[]{"entityId","tableName"},new Object[]{category.getCategoryId(),SystemConstants.TABLE_NAME_CATEGORY});
				if(localeFieldList!=null&&localeFieldList.size()>0){
					for (TlocalizedField tlocalizedField : localeFieldList) {
						TlocalizedField newLocalizedField=new TlocalizedField();
						BeanUtils.copyProperties(tlocalizedField, newLocalizedField);
						newLocalizedField.setEntityId(newCategory.getCategoryId());
						newLocalizedField.setLocaleId(null);
						if(tlocalizedField.getTableField().equals(SystemConstants.TABLE_FIELD_NAME)){
							newLocalizedField.setLocaleValue("Clone of " +tlocalizedField.getLocaleValue());
						}
						localizedFieldDao.create(newLocalizedField);
					}
				}
				
				
				for (TcategoryAttribute categoryAttribute : categoryAttributeList) {						
					List<TattributeValue> attributeValueList=attributeValueDao.findBy("attributeId", categoryAttribute.getAttributeId());
					List<TlocalizedField> titleLocaleFieldList=localizedFieldDao.findBy(new String[]{"entityId","tableName","tableField"},
							new Object[]{categoryAttribute.getAttributeId(),SystemConstants.TABLE_NAME_CATE_ATTRIBUTE,SystemConstants.TABLE_FIELD_TITLE});	
					//Clone all of the category attributes
					TcategoryAttribute newCategoryAttribute=new TcategoryAttribute();
					BeanUtils.copyProperties(categoryAttribute, newCategoryAttribute);
					newCategoryAttribute.setAttributeId(null);
					newCategoryAttribute.setCategoryId(newCategory);
					attributeDao.create(newCategoryAttribute);
					
					for (TattributeValue attributeValue : attributeValueList) {											
						List<TlocalizedField> valueLocaleList=localizedFieldDao.findBy(new String[]{"entityId","tableName","tableField"},
								new Object[]{attributeValue.getValueId(),SystemConstants.TABLE_NAME_ATTRIBUTE_VALUE,SystemConstants.TABLE_FIELD_VALUE});
						//Clone all of the attribute values
						TattributeValue newAttributeValue=new TattributeValue();
						BeanUtils.copyProperties(attributeValue, newAttributeValue);
						newAttributeValue.setValueId(null);
						newAttributeValue.setAttributeId(newCategoryAttribute.getAttributeId());
						attributeValueDao.create(newAttributeValue);
						
						for (TlocalizedField localizedField : valueLocaleList) {
							//Clone all of the localized attribute values
							TlocalizedField newLocalizedField=new TlocalizedField();
							BeanUtils.copyProperties(localizedField, newLocalizedField);
							newLocalizedField.setLocaleId(null);
							newLocalizedField.setEntityId(newAttributeValue.getValueId());
							localizedFieldDao.create(newLocalizedField);
						}
					}
									
					
					for (TlocalizedField localizedField : titleLocaleFieldList) {
						TlocalizedField newLocalizedField=new TlocalizedField();
						BeanUtils.copyProperties(localizedField, newLocalizedField);
						newLocalizedField.setLocaleId(null);
						newLocalizedField.setEntityId(newCategoryAttribute.getAttributeId());
						localizedFieldDao.create(newLocalizedField);
					}
					
				}
				
				}
			}
		
	}


	public List<Tcategory> getallCategory(Integer storeId) {
	
		return categoryDao.findBy("storeId", storeId);
	}
	
		public List<Tcategory> getallCategory(Integer type,Tlanguage language,Integer storeId) {
		
		List<Tcategory> Tcategory=categoryDao.getAll(type,storeId);
		if(Tcategory!=null&&Tcategory.size()>0){
			for (Tcategory category : Tcategory) {
				Map<String, Object> ssMap=new HashMap<String, Object>();
				ssMap.put("language", language);
				ssMap.put("entityId", category.getCategoryId());
				ssMap.put("tableName",SystemConstants.TABLE_NAME_CATEGORY);
				ssMap.put("tableField",SystemConstants.TABLE_FIELD_NAME);
				
				List<TlocalizedField> localizedFields= localizedFieldDao.find("from TlocalizedField where language=:language and entityId=:entityId and tableName=:tableName and tableField=:tableField", ssMap);
				if (localizedFields.size()>0) {
					TlocalizedField localizedField=localizedFields.get(0);
					if(!localizedField.getLocaleValue().isEmpty()&&localizedField.getLocaleValue()!=null){
						category.setName(localizedField.getLocaleValue());
				}
				}
				
			}
		
		}
		return Tcategory;
	}

}
