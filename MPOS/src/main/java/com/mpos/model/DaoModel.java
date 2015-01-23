package com.mpos.model;

import java.io.Serializable;
import java.util.List;

public class DaoModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3822574541019536986L;
	private int totalCount;
	private List list;
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}
	public DaoModel(int totalCount, List list) {
		this.totalCount = totalCount;
		this.list = list;
	}
	public DaoModel() {
	}
	
}
