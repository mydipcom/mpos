package com.mpos.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mpos.commons.ConvertTools;
import com.mpos.commons.MposException;
import com.mpos.commons.SecurityTools;
import com.mpos.commons.SystemConstants;
import com.mpos.dto.Tsetting;
import com.mpos.model.DataTableParamter;
import com.mpos.model.PagingData;
import com.mpos.service.SystemSettingService;


@Controller
@RequestMapping(value="settings")
public class SettingController extends BaseController {

	private Logger logger = Logger.getLogger(RightsController.class);
	
	@Resource
	private SystemSettingService systemSettingService;
		

	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView settings(HttpServletRequest request){
		ModelAndView mav=new ModelAndView();		
		mav.setViewName("settings/systemsetting");
		return mav;
	}
	
	@RequestMapping(value="settingsList",method=RequestMethod.GET)
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
	@RequestMapping(value="addsetting",method=RequestMethod.POST)
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
	
	@RequestMapping(value="editsetting",method=RequestMethod.POST)
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

	@RequestMapping(value="setting/{ids}",method=RequestMethod.DELETE)
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
	
	@RequestMapping(value="locale",method=RequestMethod.GET)
	public void setLocale(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String referer = request.getHeader("referer");
		response.sendRedirect(referer);
	}
	
	@RequestMapping(value="storesetting",method=RequestMethod.GET)
	public ModelAndView storeSettings(HttpServletRequest request){
		List<String> list = null;
		Map<String, List<String>> store_setting =new LinkedHashMap<String, List<String>>();
		ModelAndView mav=new ModelAndView();
		PagingData pagingData = systemSettingService.getStoreSetting();
		Object[] objs = pagingData.getAaData();
		for(int i=0;i<objs.length;i++){
			list = new ArrayList<String>();
			Tsetting tsetting = (Tsetting)objs[i];
			list.add(tsetting.getValue());
			list.add(tsetting.getDescr());
			store_setting.put(tsetting.getName(), list);
		}
		mav.addObject("store_setting",store_setting);
	    mav.setViewName("settings/storesetting");
		return mav;
	}
	
	@RequestMapping(value="editstoresetting",method=RequestMethod.POST)
	@ResponseBody
	public String editStoreSettings(HttpServletRequest request,String name,String value){
		JSONObject resp = new JSONObject();
		Tsetting tsetting = systemSettingService.getSystemSettingByName(name);		
		if(tsetting != null){
			if(SystemConstants.ACCESS_PASSWORD.equals(name) || SystemConstants.TOKEN.equals(name)){
			    tsetting.setValue(SecurityTools.MD5(value));
			}else{
				tsetting.setValue(value);
			}
            systemSettingService.updateSystemsetting(tsetting);
		}
		resp.put("status", true);
		return JSON.toJSONString(resp);
	}
	
	 @RequestMapping(value="editstoreimage",method=RequestMethod.POST)
     @ResponseBody
     public String editStoreImage(HttpServletRequest request,@RequestParam(value="images",required=true)MultipartFile file,@RequestParam int flag) throws IOException{
    	JSONObject resp = new JSONObject();
    	String realPath = request.getSession().getServletContext().getRealPath("/");
    	File image;
    	if(flag == 1){
    		image = new File(realPath,File.separator+"upload"+File.separator+"temp"+File.separator+"settingimage"+File.separator+"store_background.jpg");
    		if(image.exists()){
        		image.delete();
        	}
        }else{
        	image = new File(realPath,File.separator+"upload"+File.separator+"temp"+File.separator+"settingimage"+File.separator+"store_logo.png");
    		if(image.exists()){
        		image.delete();
        	}
        }
    	FileUtils.copyInputStreamToFile(file.getInputStream(), image);
    	resp.put("status", true);
 	    return JSON.toJSONString(resp);
    	
    	}
	 
}
