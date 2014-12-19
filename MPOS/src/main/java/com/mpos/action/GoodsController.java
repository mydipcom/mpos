package com.mpos.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mpos.commons.ConvertTools;
import com.mpos.commons.MposException;
import com.mpos.dto.Tcategory;
import com.mpos.dto.Tmenu;
import com.mpos.model.DataTableParamter;
import com.mpos.model.PagingData;
import com.mpos.service.CategoryService;
import com.mpos.service.GoodsService;
import com.mpos.service.MenuService;

@Controller
@RequestMapping("/goods")
public class GoodsController extends BaseController{
	
	@Autowired
	private GoodsService goodsService;
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView Goods(HttpServletRequest request){
		ModelAndView mav=new ModelAndView();
		List<Tcategory> categorys=categoryService.getallCategory();
		List<Tmenu> menus=menuService.getAllMenu();
		mav.addObject("category", categorys);
		mav.addObject("menu", menus);
		mav.setViewName("goods/goods");
		return mav;
	}
	@RequestMapping(value="/goodslist",method=RequestMethod.GET)
	@ResponseBody
	public String goodsList(HttpServletRequest request,DataTableParamter dtp){		
		PagingData pagingData=goodsService.loadGoodsList(dtp);
		
		pagingData.setSEcho(dtp.sEcho);
		if(pagingData.getAaData()==null){
			Object[] objs=new Object[]{};
			pagingData.setAaData(objs);
		}
		String rightsListJson= JSON.toJSONString(pagingData);
		return rightsListJson;	
	}
	@RequestMapping(value="addgoods",method=RequestMethod.GET)
	public ModelAndView addGoodsPage(HttpServletRequest request){
		ModelAndView mav=new ModelAndView();
		List<Tcategory> categorys=categoryService.getallCategory();
		List<Tmenu> menus=menuService.getAllMenu();
		mav.addObject("category", categorys);
		mav.addObject("menu", menus);
		mav.setViewName("goods/addgoods");
		return mav;
		
	}
	@RequestMapping(value="/deletegoods/{ids}",method=RequestMethod.DELETE)
	@ResponseBody
	public String deleteGoods(@PathVariable String ids,HttpServletRequest request){
		String[] idstrArr=ids.split(",");		
		Integer[] idArr=ConvertTools.stringArr2IntArr(idstrArr);		
		JSONObject respJson = new JSONObject();
		try{
			goodsService.deletegoodsByids(idArr);
			//goodsService.deleteLanguageByIds(idArr);
			respJson.put("status", true);
		}
		catch(MposException be){
			respJson.put("status", false);
			respJson.put("info", getMessage(request,be.getErrorID(),be.getMessage()));
		}	
		return JSON.toJSONString(respJson);	
	}
	@RequestMapping(value="/activegoods/{ids}",method=RequestMethod.POST)
	@ResponseBody
	public String activegood(@PathVariable String ids,HttpServletRequest request){
		String[] idstrArr=ids.split(",");		
		Integer[] idArr=ConvertTools.stringArr2IntArr(idstrArr);		
		JSONObject respJson = new JSONObject();
	
		try{
			goodsService.activegoodsByids(idArr);
			//goodsService.deleteLanguageByIds(idArr);
			respJson.put("status", true);
		}
		catch(MposException be){
			respJson.put("status", false);
			respJson.put("info", getMessage(request,be.getErrorID(),be.getMessage()));
		}	
		return JSON.toJSONString(respJson);	
	
		
	}
}
