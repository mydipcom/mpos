package com.mpos.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mpos.dao.LocalizedFieldDao;
import com.mpos.dto.TlocalizedField;
import com.mpos.service.LocalizedFieldService;
@Service
public class LocalizedFieldServiceImpl implements LocalizedFieldService{
	
	@Autowired
	LocalizedFieldDao localizedFieldDao;
	@Override
	public void createLocalizedFieldList(List<TlocalizedField> locals) {
		// TODO Auto-generated method stub
		if(locals!=null&&locals.size()>0){
			for (TlocalizedField tlocalizedField : locals) {
				if(tlocalizedField!=null&&tlocalizedField.getEntityId()!=null){
					localizedFieldDao.save(tlocalizedField);
				}
			}
		}
		
	}

	@Override
	public void updateLocalizedFieldList(List<TlocalizedField> locals) {
		// TODO Auto-generated method stub
		if(locals!=null&&locals.size()>0){
			for (TlocalizedField tlocalizedField : locals) {
				localizedFieldDao.update(tlocalizedField);
			}
		}
	}

	@Override
	public void createLocalizedField(TlocalizedField local) {
		// TODO Auto-generated method stub
		localizedFieldDao.save(local);
	}

	@Override
	public void updateLocalizedField(TlocalizedField local) {
		// TODO Auto-generated method stub
		localizedFieldDao.update(local);
	}

	
	@Override
	public void createLocalizedFieldList(TlocalizedField[] locals) {
		// TODO Auto-generated method stub
		if(locals!=null&&locals.length>0){
			for (TlocalizedField tlocalizedField : locals) {
				if(tlocalizedField!=null&&tlocalizedField.getEntityId()!=null){
					localizedFieldDao.save(tlocalizedField);
				}
			}
		}
	}

	@Override
	public List<TlocalizedField> getListByEntityIdAndEntityName(
			Integer entityId, String tableName) {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("entityId", entityId);
		params.put("tableName", tableName);
		String hql = "from TlocalizedField loc where loc.entityId = :entityId and loc.tableName = :tableName";
		return localizedFieldDao.find(hql, params);
	}
	
	@SuppressWarnings("unchecked")
	public List<TlocalizedField> getLocalizedField(Integer entityId, String tableName, String fieldName) {
		Criteria criteria=localizedFieldDao.createCriteria();
		return criteria.add(Restrictions.eq("entityId", entityId))
				.add(Restrictions.eq("tableName", tableName))
				.add(Restrictions.eq("tableField", fieldName))
				.list();		
	}

}
