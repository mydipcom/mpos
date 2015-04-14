package com.mpos.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mpos.commons.ConvertTools;
import com.mpos.commons.MposException;
import com.mpos.commons.SystemConstants;
import com.mpos.dto.Tcategory;
import com.mpos.dto.Tmenu;
import com.mpos.dto.Tproduct;
import com.mpos.dto.Tpromotion;
import com.mpos.model.DataTableParamter;
import com.mpos.model.PagingData;
import com.mpos.model.PromotionModel;
import com.mpos.service.CategoryService;
import com.mpos.service.GoodsService;
import com.mpos.service.MenuService;
import com.mpos.service.PromotionService;

@Controller
@RequestMapping("promotion")
public class PromotionController extends BaseController{
	
	@SuppressWarnings("unused")
	private Logger logger = Logger.getLogger(PromotionController.class);	
	
	@Autowired
	private PromotionService promotionService;
	
	@Autowired
	private GoodsService goodsService;
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private CategoryService categoryService;
	
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
	public ModelAndView add_Promotion(){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("promotion/add_promotion");
		return mav;
	}
	
	@RequestMapping(value="promotion_bind_product",method=RequestMethod.GET)
	@ResponseBody
	public String getPromotionBindProduct(HttpServletRequest request){
		JSONObject resp  = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;
		List<Tproduct> products = goodsService.loadAll();
		List<Tcategory> categorys = categoryService.getallCategory(getSessionUser(request).getStoreId());
		List<Tmenu> menus = menuService.getAllMenu(getSessionUser(request).getStoreId());
		for(Tproduct product : products){
			jsonObject = new JSONObject();
			jsonObject.put("id",product.getId().toString());
			jsonObject.put("text", product.getProductName());
			jsonArray.add(jsonObject);
		}
		resp.put("product", jsonArray);
		jsonArray = new JSONArray();
		for(Tcategory category:categorys){
			jsonObject = new JSONObject();
			jsonObject.put("id", category.getCategoryId().toString());
			jsonObject.put("text", category.getName());
			jsonArray.add(jsonObject);
		}
		resp.put("category", jsonArray);
		jsonArray= new JSONArray();
		for(Tmenu menu:menus){
			jsonObject = new JSONObject();
			jsonObject.put("id",menu.getMenuId().toString());
			jsonObject.put("text",menu.getTitle());
			jsonArray.add(jsonObject);
		}
		resp.put("menu", jsonArray);
		String jsongString = JSON.toJSONString(resp);
		return jsongString;
	}
    
	@RequestMapping(value="addPromotion",method=RequestMethod.POST)
	@ResponseBody
	String addPromotion(HttpServletRequest request,PromotionModel pm){
		JSONObject resp = new JSONObject();
		try{
			Tpromotion tpromotion= new Tpromotion();
			Set <Tproduct> tproducts = null;
			tpromotion.setPromotionName(pm.getPromotionName());
			tpromotion.setBindType(pm.getWay());
			tpromotion.setShared(pm.isShared());
			tpromotion.setPriority(pm.getPriority());
			tpromotion.setStatus(pm.isStatus());
			tpromotion.setStartTime(ConvertTools.dateStringToLong(pm.getStartTime()));
			tpromotion.setEndTime(ConvertTools.dateStringToLong(pm.getEndTime()));
			tpromotion.setBindType(pm.getBindType());
			tpromotion.setParamOne(pm.getParamOne());
			tpromotion.setParamTwo(pm.getParamTwo());
			if(pm.getBindType() == 1){
				tpromotion.setBindId(pm.getClaId());
			}else if(pm.getBindType() == 2){
				tpromotion.setBindId(pm.getMenId());
			}else if(pm.getBindType() == 3){
				String []strArr = pm.getGooId().split(",");
                Integer [] ids = ConvertTools.stringArr2IntArr(strArr);
                tproducts = new HashSet<Tproduct>();
                for(int id : ids){
                	Tproduct tproduct = new Tproduct();
                	tproduct.setId(id);
                	tproducts.add(tproduct);
                }
                tpromotion.settProducts(tproducts);
			}
			tpromotion.setPromotionRule("XXXXXXXXX");
			promotionService.createPromtion(tpromotion);
		}catch(MposException m){
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return JSON.toJSONString(resp);
	}

}
