package com.mpos.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mpos.commons.ConvertTools;
import com.mpos.commons.MposException;
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
	public String promotionList(HttpServletRequest request,DataTableParamter dtp){
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
	
	@RequestMapping(value="activaOrDeactiva/{promotionId}",method=RequestMethod.POST)
	@ResponseBody
	public String activaOrDeactiva(HttpServletRequest request,@RequestParam int flag,@PathVariable String promotionId){
		JSONObject resp = new JSONObject();
		try{
			
			String promotionIds[] = promotionId.split(",");
			Integer ids[]=ConvertTools.stringArr2IntArr(promotionIds);
			if(flag == 1){
				for(int id:ids){
					Tpromotion tPromotion = promotionService.getPromtionById(id);
					if(tPromotion !=null && !tPromotion.isStatus()){
						tPromotion.setStatus(true);
					    promotionService.updatePromtion(tPromotion);	
					}
				}
			}else{
				for(int id:ids){
					Tpromotion tPromotion = promotionService.getPromtionById(id);
					if(tPromotion !=null && tPromotion.isStatus()){
						tPromotion.setStatus(false);
					    promotionService.updatePromtion(tPromotion);	
					}
				}
			}
			resp.put("status", true);
		}catch(MposException m){
			resp.put("status", false);
		}
        return JSON.toJSONString(resp);
	}
	
	@RequestMapping(value="add_promotion",method=RequestMethod.GET)
	public ModelAndView addPromotion(){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("promotion/add_promotion");
		return mav;
	}
	
	@RequestMapping(value="promotion_bind_product",method=RequestMethod.GET)
	public String getPromotionBindProduct(){
		JSONObject resp  = new JSONObject();
		JSONObject [] jsonObjects = new JSONObject[2];
		JSONObject jsonObject = new JSONObject();
		return JSON.toJSONString(resp);
	}

}
