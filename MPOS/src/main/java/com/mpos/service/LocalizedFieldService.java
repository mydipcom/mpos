package com.mpos.service;

import java.util.List;

import com.mpos.dto.TlocalizedField;

public interface LocalizedFieldService {
	
	void createLocalizedFieldList(TlocalizedField[] locals);
	
	void createLocalizedFieldList(List<TlocalizedField> locals);
	
	void updateLocalizedFieldList(List<TlocalizedField> locals);
	
	void createLocalizedField(TlocalizedField local);
	
	void updateLocalizedField(TlocalizedField local);
	
	List<TlocalizedField> getListByEntityIdAndEntityName(Integer entityId,String tableName);
	
}
