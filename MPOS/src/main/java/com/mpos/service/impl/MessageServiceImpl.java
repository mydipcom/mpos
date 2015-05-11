package com.mpos.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.mpos.commons.ConvertTools;
import com.mpos.dao.MessageDao;
import com.mpos.dto.Tmessage;
import com.mpos.model.DataTableParamter;
import com.mpos.model.PagingData;
import com.mpos.service.MessageService;
@Service
public class MessageServiceImpl implements MessageService {
	
	@Autowired
	MessageDao messageDao;

	public void create(Tmessage message) {
		// TODO Auto-generated method stub
		messageDao.save(message);
	}

	public void delete(Tmessage message) {
		// TODO Auto-generated method stub
		messageDao.delete(message);
	}

	public void delete(Integer id) {
		// TODO Auto-generated method stub
		messageDao.delete(id);
	}

	public PagingData loadList(DataTableParamter rdtp) {
		Criteria criteria = messageDao.createCriteria();
		//confine(criteria);
		String searchJsonStr = rdtp.getsSearch();
		criteria.addOrder(Order.desc("id"));
		if(searchJsonStr!=null&&!searchJsonStr.isEmpty()){
			List<Criterion> criterionList = new ArrayList<Criterion>();
			JSONObject json = (JSONObject) JSONObject.parse(searchJsonStr);
			String name = json.getString("userName");
			if(name!=null&&!json.isEmpty()){
				criterionList.add(Restrictions.like("userName",name,MatchMode.ANYWHERE));
			}
			String startTime = json.getString("startTime");
			String endTime = json.getString("endTime");
			if((startTime!=null&&!startTime.isEmpty())&&(endTime!=null&&!endTime.isEmpty())){
				try {
					criterionList.add(Restrictions.between("createTime", ConvertTools.dateString2Long(startTime), ConvertTools.dateString2Long(endTime)));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			for (Criterion criterion : criterionList) {
				criteria.add(criterion);
			}
			return messageDao.findPage(criteria,rdtp.iDisplayStart, rdtp.iDisplayLength);
		}
		return messageDao.findPage(criteria,rdtp.iDisplayStart, rdtp.iDisplayLength);
	}

}
