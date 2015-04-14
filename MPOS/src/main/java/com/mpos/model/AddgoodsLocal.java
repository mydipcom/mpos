package com.mpos.model;

import java.util.ArrayList;
import java.util.List;

import com.mpos.dto.TlocalizedField;
import com.mpos.dto.Tproduct;

public class AddgoodsLocal {
	private int level;
   // private List<TlocalizedField> locals;
    private List<TlocalizedField> productNames;
    private List<TlocalizedField> shortDescrs;
    private List<TlocalizedField> fullDescrs;
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}

	public List<TlocalizedField> getProductNames() {
		return productNames;
	}
	public void setProductNames(List<TlocalizedField> productNames) {
		this.productNames = productNames;
	}
	public List<TlocalizedField> getShortDescrs() {
		return shortDescrs;
	}
	public void setShortDescrs(List<TlocalizedField> shortDescrs) {
		this.shortDescrs = shortDescrs;
	}
	public List<TlocalizedField> getFullDescrs() {
		return fullDescrs;
	}
	public void setFullDescrs(List<TlocalizedField> fullDescrs) {
		this.fullDescrs = fullDescrs;
	}
	public List<TlocalizedField> setValue(Tproduct product){
		List<TlocalizedField> list = new ArrayList<TlocalizedField>();
		for (TlocalizedField tlocalizedField : productNames) {
			tlocalizedField.setEntityId(product.getId());
			tlocalizedField.setTableName(Tproduct.class.getSimpleName());
			list.add(tlocalizedField);
		}
		for (TlocalizedField tlocalizedField : shortDescrs) {
			tlocalizedField.setEntityId(product.getId());
			tlocalizedField.setTableName(Tproduct.class.getSimpleName());
			list.add(tlocalizedField);
		}
		for (TlocalizedField tlocalizedField : fullDescrs) {
			tlocalizedField.setEntityId(product.getId());
			tlocalizedField.setTableName(Tproduct.class.getSimpleName());
			list.add(tlocalizedField);
		}
		return list;
		
	}
}
