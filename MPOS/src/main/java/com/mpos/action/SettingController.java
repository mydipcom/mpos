package com.mpos.action;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
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
import com.mpos.dto.Tsetting;
import com.mpos.model.DataTableParamter;
import com.mpos.model.PagingData;
import com.mpos.service.SystemSettingService;


@Controller
public class SettingController extends BaseController {

	private Logger logger = Logger.getLogger(RightsController.class);
	
	@Resource
	private SystemSettingService systemSettingService;
		

	@RequestMapping(value="/settings",method=RequestMethod.GET)
	public ModelAndView settings(HttpServletRequest request){
		ModelAndView mav=new ModelAndView();		
		mav.setViewName("settings/systemsetting");
		return mav;
	}
	
	@RequestMapping(value="/settingsList",method=RequestMethod.GET)
	@ResponseBody
	public String SystemsettingsList(HttpServletRequest request,DataTableParamter dtp){		
		PagingData pagingData=systemSettingService.loadSystemsettingList(dtp);
		pagingData.setSEcho(dtp.sEcho);
		if(pagingData.getAaData()==null){
			Object[] objs=new Object[]{};
			pagingData.setAaData(objs);
		}
		String rightsListJson= JSON.toJSONString(pagingData);
		return rightsListJson;
	}
		
	
	/**
	 * <p>Description: 处理新增数据的ajax请求</p>
	 * @Title: addRights 
	 * @param jsonStr
	 * @param request
	 * @return String
	 * @throws
	 */
	@RequestMapping(value="/addsetting",method=RequestMethod.POST)
	@ResponseBody
	public String addSettings(HttpServletRequest request,Tsetting setting){
		
		JSONObject respJson = new JSONObject();
		try{
			systemSettingService.createSystemsetting(setting);
			respJson.put("status", true);
		}
		catch(MposException be){
			respJson.put("status", false);
			respJson.put("info", be.getMessage());
		}
		systemSettingService.cachedSystemSettingData();
		return JSON.toJSONString(respJson);
	}
	
	@RequestMapping(value="/editsetting",method=RequestMethod.POST)
	@ResponseBody
	public String updateSettings(HttpServletRequest request,Tsetting setting){		


		JSONObject respJson = new JSONObject();
		try{
			systemSettingService.updateSystemsetting(setting);
			respJson.put("status", true);
		}
		catch(MposException be){
			respJson.put("status", false);
			respJson.put("info", be.getMessage());
		}
		systemSettingService.cachedSystemSettingData();
		return JSON.toJSONString(respJson);		
	}

	@RequestMapping(value="/setting/{ids}",method=RequestMethod.DELETE)
	@ResponseBody
	public String deleteSettings(@PathVariable String ids,HttpServletRequest request){
		String[] idstrArr=ids.split(",");		
		Integer[] idArr=ConvertTools.stringArr2IntArr(idstrArr);		
		JSONObject respJson = new JSONObject();
		try{
			systemSettingService.deleteSystemsettingByIds(idArr);
			respJson.put("status", true);
		}
		catch(MposException be){
			respJson.put("status", false);
			respJson.put("info", be.getMessage());
		}
		systemSettingService.cachedSystemSettingData();
		return JSON.toJSONString(respJson);	
	}
	
	@RequestMapping(value="/locale",method=RequestMethod.GET)
	public void setLocale(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String referer = request.getHeader("referer");
		response.sendRedirect(referer);
	}
}
