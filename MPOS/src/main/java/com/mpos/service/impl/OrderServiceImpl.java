package com.mpos.service.impl;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mpos.commons.ConvertTools;
import com.mpos.commons.MposException;
import com.mpos.dao.OrderDao;
import com.mpos.dto.Torder;
import com.mpos.model.DataTableParamter;
import com.mpos.model.PagingData;
import com.mpos.service.OrderService;
public class OrderServiceImpl implements OrderService {
	
    @Autowired
	private OrderDao orderDao;
    
	public PagingData loadOrderList(DataTableParamter dtp) {
		// TODO Auto-generated method stub
		String searchJsonStr = dtp.getsSearch();
		if(searchJsonStr!=null&&!searchJsonStr.isEmpty()){
			List<Criterion> criterionsList=new ArrayList<Criterion>();
			JSONObject jsonObj= (JSONObject)JSON.parse(searchJsonStr);
			Set<String> keys=jsonObj.keySet();						
			for (String key : keys) {
				String val=jsonObj.getString(key);
				if(val!=null&&!val.isEmpty()){
					if(key=="creater"){
						criterionsList.add(Restrictions.eq(key, jsonObj.getString(key)));
					}
					else if(key=="orderStatus"){
						if(jsonObj.getInteger(key)==0){
						 criterionsList.add(Restrictions.eq(key, jsonObj.getInteger(key)));
						}else{
							criterionsList.add(Restrictions.ne(key,0));	
						}
						
					}else if(key=="orderId"){
						criterionsList.add(Restrictions.eq(key, jsonObj.getInteger(key)));
					}
				}
			}
			
			if(jsonObj.getString("startTime") != null && !jsonObj.getString("startTime").isEmpty() 
				    && jsonObj.getString("endTime") != null && !jsonObj.getString("endTime").isEmpty()){
				   try {
					criterionsList.add(Restrictions.between("createTime", ConvertTools.dateStringToLong(jsonObj.getString("startTime")), ConvertTools.dateStringToLong(jsonObj.getString("endTime"))));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			Criterion[] criterions=new Criterion[criterionsList.size()];
			int i=0;
			for (Criterion criterion : criterionsList) {
				criterions[i]=criterion;	
				i++;
			}
			return orderDao.findPage(criterions,dtp.iDisplayStart, dtp.iDisplayLength);
		}
		return orderDao.findPage(Restrictions.eq("orderStatus", 0),dtp.getiDisplayStart(), dtp.getiDisplayLength());
	}

	public Torder getTorderById(int id) {
		// TODO Auto-generated method stub
		return orderDao.get(id);
	}

	public void update(Torder torder) {
		// TODO Auto-generated method stub
		orderDao.update(torder);
	}

	public void createOrder(Torder torder) {
		// TODO Auto-generated method stub
		orderDao.create(torder);
	}

	public void deleteOrder(Torder torder) {
		// TODO Auto-generated method stub
		orderDao.delete(torder);
	}
}
