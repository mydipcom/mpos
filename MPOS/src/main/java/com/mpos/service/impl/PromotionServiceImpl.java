package com.mpos.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mpos.commons.MposException;
import com.mpos.dao.PromotionDao;
import com.mpos.dto.Tpromotion;
import com.mpos.model.DataTableParamter;
import com.mpos.model.PagingData;
import com.mpos.service.PromotionService;

public class PromotionServiceImpl implements PromotionService {
	
	@Autowired
    private PromotionDao promtionDao;
	
	
	public PagingData loadPromotionList(DataTableParamter dtp){
		// TODO Auto-generated method stub
		SimpleDateFormat sdf =new SimpleDateFormat("dd/mm/yyyy hh:mm");
		String searchJsonStr = dtp.getsSearch();
		if(searchJsonStr!=null&&!searchJsonStr.isEmpty()){
			List<Criterion> criterionsList=new ArrayList<Criterion>();
			JSONObject jsonObj= (JSONObject)JSON.parse(searchJsonStr);
			Set<String> keys=jsonObj.keySet();						
			for (String key : keys) {
				String val=jsonObj.getString(key);
				if(val!=null&&!val.isEmpty()){
					if(key=="promotionName"){
						criterionsList.add(Restrictions.eq(key, jsonObj.getString(key)));
					}
					else if(key=="promotionType"){
						criterionsList.add(Restrictions.eq(key, jsonObj.getInteger(key)));
					}
				}
			}
			
			try{
			if(jsonObj.getString("startTimeOne") != null && !jsonObj.getString("startTimeOne").isEmpty() 
				    && jsonObj.getString("startTimeTwo") != null && !jsonObj.getString("startTimeTwo").isEmpty()){
				criterionsList.add(Restrictions.between("startTime",sdf.parse(jsonObj.getString("startTimeOne")).getTime(), sdf.parse(jsonObj.getString("startTimeTwo")).getTime()));
				}
			}catch(MposException m){
				
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Criterion[] criterions=new Criterion[criterionsList.size()];
			int i=0;
			for (Criterion criterion : criterionsList) {
				criterions[i]=criterion;	
				i++;
			}
			return promtionDao.findPage(criterions,dtp.iDisplayStart, dtp.iDisplayLength);
		}
		return promtionDao.findPage(dtp.iDisplayStart, dtp.iDisplayLength);
	}


	public void updatePromtion(Tpromotion tPromotion) {
		// TODO Auto-generated method stub
		promtionDao.update(tPromotion);
	}


	public Tpromotion getPromtionById(int id) {
		// TODO Auto-generated method stub
		return promtionDao.get(id);
	}
	
	public List<Tpromotion> selectPromotion(Integer bindType) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		long time = new Date().getTime();
		map.put("status", true);
		map.put("time", time);
		map.put("time1", time);
		map.put("bindType", bindType);
		String hql = "from Tpromotion tp where tp.status=:status and tp.startTime <=:time and tp.endTime>=:time1 and tp.bindType=:bindType order by tp.priority";
		return promtionDao.find(hql, map);
	}

}
