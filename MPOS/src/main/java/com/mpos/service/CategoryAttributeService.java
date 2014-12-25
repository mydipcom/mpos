package com.mpos.service;

import java.util.List;

import com.mpos.dto.TcategoryAttribute;
import com.mpos.model.DataTableParamter;
import com.mpos.model.PagingData;

public interface CategoryAttributeService {

	void createCategoryAttribute(TcategoryAttribute attribute);
	void deleteCategoryAttribute(TcategoryAttribute attribute);
	void updateCategoryAttribute(TcategoryAttribute attribute);
	TcategoryAttribute getCategoryAttribute(Integer attributeId);
	public List<TcategoryAttribute> getCategoryAttributeByCategoryid(Integer id);
	PagingData loadAttributeList(String id, DataTableParamter dtp);
	void deleteAttributeByIds(Integer[] idArr);
}
