package com.mpos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mpos.dao.LanguageDao;
import com.mpos.dto.Tlanguage;
import com.mpos.dto.Tproduct;
import com.mpos.model.DataTableParamter;
import com.mpos.model.PagingData;
import com.mpos.service.LanguageService;
@Service
public class LanguageServiceImpl implements LanguageService{
	
	@Autowired
	private LanguageDao languageDao;
	
	public Tlanguage getLanguageById(Integer Id) {
		
		return languageDao.get(Id);
	}

	public void createLanguage(Tlanguage language) {
	languageDao.create(language);
		
	}

	public void updateLanguage(Tlanguage language) {
	languageDao.update(language);
		
	}

	public void deleteLanguage(Tlanguage language) {
	languageDao.delete(language);
		
	}

	public void deleteLanguageByIds(Integer[] ids) {
		if(ids!=null&&ids.length>0){
			for (Integer id : ids) {
				Tlanguage language=getLanguageById(id);
					language.setStatus(false);
					languageDao.update(language);
			}
		}	
	}

	public PagingData loadLanguageList(DataTableParamter rdtp) {
		return languageDao.findPage(rdtp.iDisplayStart, rdtp.iDisplayLength);
	
		
	}

	public void activeLanguageByids(Integer[] ids) {
		if(ids!=null&&ids.length>0){
			for (Integer id : ids) {
				Tlanguage language=getLanguageById(id);
					language.setStatus(true);
					languageDao.update(language);
			}
		}	
		
	}

	@Override
	public List<Tlanguage> loadAllTlanguage() {
		// TODO Auto-generated method stub
		return languageDao.LoadAll();
	}

}
