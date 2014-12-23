package com.mpos.model;

import java.util.ArrayList;
import java.util.List;

import com.mpos.dto.Tcategory;
import com.mpos.dto.TcategoryAttribute;
import com.mpos.dto.TlocalizedField;
import com.mpos.dto.Tmenu;

public class ParamWrapper {
	private int level;
    private List<TlocalizedField> locals;
    private List<TlocalizedField> titles;
    private List<TlocalizedField> contents;
    private List<TlocalizedField> names;
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public List<TlocalizedField> getLocals() {
		return locals;
	}
	public void setLocals(List<TlocalizedField> locals) {
		this.locals = locals;
	}
	
	public List<TlocalizedField> getTitles() {
		return titles;
	}
	public void setTitles(List<TlocalizedField> titles) {
		this.titles = titles;
	}
	public List<TlocalizedField> getContents() {
		return contents;
	}
	public void setContents(List<TlocalizedField> contents) {
		this.contents = contents;
	}
	
	public List<TlocalizedField> getNames() {
		return names;
	}
	public void setNames(List<TlocalizedField> names) {
		this.names = names;
	}
	public List<TlocalizedField> setValue(Tmenu menu){
		List<TlocalizedField> list = new ArrayList<TlocalizedField>();
		for (TlocalizedField tlocalizedField : locals) {
			tlocalizedField.setEntityId(menu.getMenuId());
			tlocalizedField.setTableName(Tmenu.class.getSimpleName());
			list.add(tlocalizedField);
		}
		return list;
	}
	
	public List<TlocalizedField> setValue(TcategoryAttribute attribute){
		List<TlocalizedField> list = new ArrayList<TlocalizedField>();
		for (TlocalizedField tlocalizedField : titles) {
			tlocalizedField.setEntityId(attribute.getAttributeId());
			tlocalizedField.setTableName(TcategoryAttribute.class.getSimpleName());
			list.add(tlocalizedField);
		}
		for (TlocalizedField tlocalizedField : contents) {
			tlocalizedField.setEntityId(attribute.getAttributeId());
			tlocalizedField.setTableName(TcategoryAttribute.class.getSimpleName());
			list.add(tlocalizedField);
		}
		return list;
	}
	
	public List<TlocalizedField> setValue(Tcategory category){
		List<TlocalizedField> list = new ArrayList<TlocalizedField>();
		for (TlocalizedField tlocalizedField : names) {
			tlocalizedField.setEntityId(category.getCategoryId());
			tlocalizedField.setTableName(Tcategory.class.getSimpleName());
			list.add(tlocalizedField);
		}
		for (TlocalizedField tlocalizedField : contents) {
			tlocalizedField.setEntityId(category.getCategoryId());
			tlocalizedField.setTableName(Tcategory.class.getSimpleName());
			list.add(tlocalizedField);
		}
		return list;
	}

	
}
