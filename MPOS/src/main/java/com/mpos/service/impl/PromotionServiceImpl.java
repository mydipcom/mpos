package com.mpos.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mpos.commons.MposException;
import com.mpos.dao.PromotionDao;
import com.mpos.model.DataTableParamter;
import com.mpos.model.PagingData;
import com.mpos.service.PromotionService;

public class PromotionServiceImpl implements PromotionService {
	
	@Autowired
    private PromotionDao promtionDao;
	
	@Override
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

}
