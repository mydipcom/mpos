package com.mpos.service;

import java.util.List;

import com.mpos.dto.Tcategory;
import com.mpos.dto.Tlanguage;
import com.mpos.model.DataTableParamter;
import com.mpos.model.PagingData;

public interface CategoryService {

	void createCategory(Tcategory category);
	void deleteCategory(Tcategory category);
	void updateCategory(Tcategory category);
	Tcategory getCategory(Integer categoryId);
	PagingData loadCategoryList(DataTableParamter dtp);
	void deleteCategoryByIds(Integer[] idArr);
	void cloneCategoryByIds(Integer[] ids);
	public List<Tcategory> getallCategory(Integer storeId);
	public List<Tcategory> getallCategory(Integer type,Tlanguage language,Integer storeId);
	
}
