package com.mpos.action;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

import javassist.expr.NewArray;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.mpos.commons.SystemConstants;
import com.mpos.dto.Tpromotion;
import com.mpos.model.DataTableParamter;
import com.mpos.model.PagingData;
import com.mpos.model.PromotionModel;
import com.mpos.service.PromotionService;
@Controller
@RequestMapping("promotion")
public class PromotionController extends BaseController{
	
	private Logger logger = Logger.getLogger(PromotionController.class);	
	
	@Autowired
	private PromotionService promotionService;
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView promotion(){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("promotion/promotion");
		return mav;
	}
	
	@RequestMapping(value="promotionList",method=RequestMethod.GET)
	@ResponseBody
	public String PromotionList(HttpServletRequest request,DataTableParamter dtp){
		SimpleDateFormat sdf =new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		PagingData pagingData = promotionService.loadPromotionList(dtp);
		pagingData.setSEcho(dtp.getsEcho());
		Object[] objs = pagingData.getAaData();
		if(objs == null){
			objs=new Object[]{};
			pagingData.setAaData(objs);
		}else{
          for(int i=0;i<objs.length;i++){
                Tpromotion tPromotion = (Tpromotion)objs[i];
                PromotionModel promotionModel = new PromotionModel();
				promotionModel.setPromotionId(tPromotion.getPromotionId());
				promotionModel.setPromotionName(tPromotion.getPromotionName());
				promotionModel.setPromotionType(SystemConstants.PROMOTION_TYPE.get(tPromotion.getPromotionType()));
				promotionModel.setPromotionRule(tPromotion.getPromotionRule());
				promotionModel.setStartTime(sdf.format(new Date(tPromotion.getStartTime())));
				promotionModel.setEndTime(sdf.format(new Date(tPromotion.getEndTime())));
				promotionModel.setBindType(tPromotion.getBindType());
				promotionModel.setShared(tPromotion.isShared());
				promotionModel.setPriority(tPromotion.getPriority());
				promotionModel.setStatus(tPromotion.isStatus());
				objs[i]=promotionModel;
			}
			
		}
		String jsonString = JSON.toJSONString(pagingData);
		return jsonString;
	}

}
