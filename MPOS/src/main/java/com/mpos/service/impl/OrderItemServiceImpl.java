package com.mpos.service.impl;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mpos.commons.ConvertTools;
import com.mpos.dao.AttributeValueDao;
import com.mpos.dao.CategoryAttributeDao;
import com.mpos.dao.OrderItemDao;
import com.mpos.dto.TcategoryAttribute;
import com.mpos.dto.TorderItem;
import com.mpos.model.DataTableParamter;
import com.mpos.model.PagingData;
import com.mpos.service.OrderItemService;
public class OrderItemServiceImpl implements OrderItemService {
    
	@Autowired
	private OrderItemDao orderItemDao;
	@Autowired
	private CategoryAttributeDao categoryAttributeDao;
	@Autowired
	private AttributeValueDao attributeValueDao;
	
	public PagingData loadPagingDataByOrderId(DataTableParamter dtp) {
		// TODO Auto-generated method stub
		return orderItemDao.findPage(Restrictions.eq("orderId", dtp.getOrder_id()), dtp.iDisplayStart, dtp.iDisplayLength);
	}

	public void createOrderItem(TorderItem orderItem) {
		// TODO Auto-generated method stub
		orderItemDao.create(orderItem);
	}

	@Override
	public String get(Integer orderItemId) {
		// TODO Auto-generated method stub
		StringBuffer attributes = new StringBuffer();
		TorderItem orderItem = orderItemDao.get(orderItemId);
		if(orderItem!=null&&orderItem.getAttributes()!=null&&!orderItem.getAttributes().isEmpty()){
			String attrs = orderItem.getAttributes();
			JSONArray attributeList = JSON.parseArray(attrs);
			if(attributeList!=null&&attributeList.size()>0){
				for (Object o : attributeList) {
					JSONObject attribute = (JSONObject) o;
					if(attribute!=null){
						Integer attributeId = attribute.getInteger("attributeId");
						String valueId = attribute.getString("valueIds");
						TcategoryAttribute attr = categoryAttributeDao.get(attributeId);
						if(attr!=null){
							attributes.append(";"+attr.getTitle()+":");
							if(valueId!=null&&!valueId.isEmpty()){
								Integer[] ids = ConvertTools.stringArr2IntArr(valueId.split(","));
								int i = 0;
								for (Integer id : ids) {
									if(i==ids.length||ids.length==1){
										attributes.append(attributeValueDao.get(id).getValue());
									}else{
										attributes.append(attributeValueDao.get(id).getValue()+",");
									}
									i++;
								}
							}
						}
					}
				}
			}
		}
		return attributes.toString().isEmpty()?"":attributes.substring(1);
	}

}
