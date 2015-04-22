package com.mpos.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mpos.commons.ConvertTools;
import com.mpos.dao.LanguageDao;
import com.mpos.dto.Tlanguage;
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

	
	@SuppressWarnings("unchecked")
	public List<Tlanguage> loadAllTlanguage() {
		Criteria criteria=languageDao.createCriteria();
		return criteria.add(Restrictions.eq("status",true))				
				.list();
	}

	
	public Tlanguage get(String locale) {
		// TODO Auto-generated method stub
		return languageDao.findUnique("local", locale);
	}
		public Tlanguage getLanguageBylocal(String local) {
		// TODO Auto-generated method stub
		 
		return 	languageDao.getlanguagebylocal(local);	
	
	}

		public List<Tlanguage> getLangListByStoreId(Integer storeId) {
			List<Tlanguage> langs = new ArrayList<Tlanguage>();
			Map<String, Object> params = new HashMap<String, Object>();
			String query = "select storeLangId from Tstore where storeId=:storeId";
			String queryList = "from Tlanguage where status=:status and id in(:ids)";
			params.put("storeId", storeId);
			String storeLangIds = (String) languageDao.getObject(query, params);
			params.clear();
			if(!storeLangIds.isEmpty()&&!storeLangIds.equals(",")){
				params.put("status", true);
				String[] ids =  storeLangIds.split(",");
				params.put("ids",ConvertTools.stringArr2IntArr(ids));
				langs= languageDao.select(queryList, params);
			}
			
			return langs;
		}


}
