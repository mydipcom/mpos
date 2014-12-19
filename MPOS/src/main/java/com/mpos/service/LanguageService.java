package com.mpos.service;

import com.mpos.dto.Tlanguage;
import com.mpos.model.DataTableParamter;
import com.mpos.model.PagingData;

public interface LanguageService {

	
	Tlanguage getLanguageById(Integer Id);
	
	void createLanguage(Tlanguage setting);
	
	void updateLanguage(Tlanguage setting);
	
	void deleteLanguage(Tlanguage setting);
	
	void deleteLanguageByIds(Integer[] ids);
	
	public PagingData loadLanguageList(DataTableParamter rdtp);
}
