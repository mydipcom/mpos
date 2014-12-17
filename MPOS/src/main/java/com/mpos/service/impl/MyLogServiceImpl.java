package com.mpos.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mpos.dao.MyLogDao;
import com.mpos.dto.TadminLog;
import com.mpos.dto.TadminUser;
import com.mpos.model.DataTableParamter;
import com.mpos.model.PagingData;
import com.mpos.service.MyLogService;
@Service
public class MyLogServiceImpl implements MyLogService{
	
	@Autowired
	private  MyLogDao myLogDao;
	
	
	public TadminLog getAdminLogById(Integer Id) {
		
		return  myLogDao.get(Id);
	}

	public PagingData loadadminlogList(DataTableParamter rdtp,
			TadminUser adminuser) {
		String searchJsonStr=rdtp.getsSearch();
		List<Criterion> criterionsList=new ArrayList<Criterion>();
		SimpleDateFormat simpleDateFormat =new SimpleDateFormat("dd-MM-yyyy");
		if(searchJsonStr!=null&&!searchJsonStr.isEmpty()){
			JSONObject jsonObj= (JSONObject)JSON.parse(searchJsonStr);
			if(jsonObj.getString("startTime") != null && !jsonObj.getString("startTime").isEmpty()&&
					 jsonObj.getString("endTime") != null && !jsonObj.getString("endTime").isEmpty()){
				
					Date sdate = null;
					Date edate = null;
					try {
						sdate = simpleDateFormat.parse(jsonObj.getString("startTime"));
						edate = simpleDateFormat.parse(jsonObj.getString("endTime"));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Long startLong=sdate.getTime();
					Long endLong=edate.getTime();
					criterionsList.add(Restrictions.between("createdTime", startLong, endLong));
			}
		}
	
		if(adminuser!=null){
		criterionsList.add(Restrictions.eq("adminId", adminuser.getAdminId()));
		}
		Criterion[] criterions=new Criterion[criterionsList.size()];
		int i=0;
		for (Criterion criterion : criterionsList) {
			criterions[i]=criterion;	
			i++;
		}
		return myLogDao.findPage(criterions,rdtp.iDisplayStart, rdtp.iDisplayLength);
		
	}

}
