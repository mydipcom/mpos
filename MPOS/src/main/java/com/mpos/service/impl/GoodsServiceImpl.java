package com.mpos.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mpos.dao.GoodsDao;
import com.mpos.dto.Tcategory;
import com.mpos.dto.Tproduct;
import com.mpos.model.DataTableParamter;
import com.mpos.model.PagingData;
import com.mpos.service.GoodsService;
@Service
public class GoodsServiceImpl implements GoodsService{

	@Autowired
	private GoodsDao goodsDao;
	
	public PagingData loadGoodsList(DataTableParamter rdtp) {
		String searchJsonStr=rdtp.getsSearch();
		if(searchJsonStr!=null&&!searchJsonStr.isEmpty()){
			List<Criterion> criterionsList=new ArrayList<Criterion>();
			JSONObject jsonObj= (JSONObject)JSON.parse(searchJsonStr);
			Set<String> keys=jsonObj.keySet();						
			for (String key : keys) {
				String val=jsonObj.getString(key);
				if(val!=null&&!val.isEmpty()){
					if(key=="recommend"){
						criterionsList.add(Restrictions.eq(key, jsonObj.getBoolean(key)));
					}
					else if(key=="tcategory.categoryId"){
						criterionsList.add(Restrictions.eq(key, jsonObj.getInteger(key)));
					}
					else if(key=="tmenu.menuId"){
						criterionsList.add(Restrictions.eq(key, jsonObj.getInteger(key)));
					}else if(key=="status"){
						criterionsList.add(Restrictions.eq(key, jsonObj.getBoolean(key)));
					}
					else {
						criterionsList.add(Restrictions.eq(key, jsonObj.get(key)));
					}
				}
			}
			Criterion[] criterions=new Criterion[criterionsList.size()];
			int i=0;
			for (Criterion criterion : criterionsList) {
				criterions[i]=criterion;	
				i++;
			}
			return goodsDao.findPage(criterions,rdtp.iDisplayStart, rdtp.iDisplayLength);
		}
		return goodsDao.findPage(rdtp.iDisplayStart, rdtp.iDisplayLength);
		
	}

	public void deletegoodsByids(Integer[] ids) {
		if(ids!=null&&ids.length>0){
			for (Integer id : ids) {
				Tproduct goods=getTproductByid(id);
				goods.setStatus(false);
				goodsDao.update(goods);
			}
		}	
	}

	
	public Tproduct getTproductByid(Integer id) {
	
		return goodsDao.get(id);
	}

	public void activegoodsByids(Integer[] ids) {
		if(ids!=null&&ids.length>0){
			for (Integer id : ids) {
				Tproduct goods=getTproductByid(id);
				goods.setStatus(true);
				goodsDao.update(goods);
			}
		}	
		
	}

	public void createGoods(Tproduct product) {
		goodsDao.create(product);
		
	}

	public Tproduct findbyProductName(String productName) {
		
		return goodsDao.findprodctbyname(productName);
	}

	public void updateGoods(Tproduct product) {
		
		goodsDao.update(product);
	}
	
		public List<Tproduct> loadAll() {
		// TODO Auto-generated method stub
		return goodsDao.LoadAll();
	}

}
