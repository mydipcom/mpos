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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mpos.commons.MposException;
import com.mpos.commons.SecurityTools;
import com.mpos.commons.SystemConstants;
import com.mpos.dto.Tsetting;
import com.mpos.model.PagingData;
import com.mpos.service.SystemSettingService;


@Controller
@RequestMapping(value="settings")
public class SettingController extends BaseController {

	@Resource
	private SystemSettingService systemSettingService;
		

	@RequestMapping(value="systemsetting",method=RequestMethod.GET)
	public ModelAndView settings(HttpServletRequest request){
		List<String> list = null;
		Map<String, List<String>> system_setting =new LinkedHashMap<String, List<String>>();
		ModelAndView mav=new ModelAndView();
		try{
			PagingData pagingData = systemSettingService.loadSystemsetting();
			Object[] objs = pagingData.getAaData();
			for(int i=0;i<objs.length;i++){
				list = new ArrayList<String>();
				Tsetting tsetting = (Tsetting)objs[i];
				list.add(tsetting.getValue());
				list.add(tsetting.getDescr());
				system_setting.put(tsetting.getName(), list);
			}
		}catch(MposException m){
			
		} 
		mav.addObject("system_setting",system_setting);
	    mav.setViewName("settings/systemsetting");
		return mav;
	}
	
    @RequestMapping(value="storesetting",method=RequestMethod.GET)
	public ModelAndView storeSettings(HttpServletRequest request){
		List<String> list = null;
		Map<String, List<String>> store_setting =new LinkedHashMap<String, List<String>>();
		ModelAndView mav=new ModelAndView();
		try{
			PagingData pagingData = systemSettingService.getStoreSetting();
			Object[] objs = pagingData.getAaData();
			for(int i=0;i<objs.length;i++){
				list = new ArrayList<String>();
				Tsetting tsetting = (Tsetting)objs[i];
				list.add(tsetting.getValue());
				list.add(tsetting.getDescr());
				store_setting.put(tsetting.getName(), list);
			}
		}catch(MposException m){
			
		} 
		mav.addObject("store_setting",store_setting);
		mav.setViewName("settings/storesetting");
		return mav;
	}
	
	@RequestMapping(value="editsetting",method=RequestMethod.POST)
	@ResponseBody
	public String editStoreSettings(HttpServletRequest request,String name,String value){
		JSONObject resp = new JSONObject();
		
		try{
			Tsetting tsetting = systemSettingService.getSystemSettingByName(name);
			if(tsetting != null){
				if(SystemConstants.ACCESS_PASSWORD.equals(name)|| SystemConstants.TOKEN.equals(name)){
				    tsetting.setValue(SecurityTools.MD5(value));
				}else{
					tsetting.setValue(value);
				}
	            systemSettingService.updateSystemsetting(tsetting);
	            systemSettingService.cachedSystemSet();
	            resp.put("status", true);
	            resp.put("info", getMessage(request,"operate.success"));
			}
		}catch(MposException be){
			resp.put("status", false);
			resp.put("info", getMessage(request,be.getErrorID(),be.getMessage()));
		} 
		return JSON.toJSONString(resp);
	}
	
	 @RequestMapping(value="editstoreimage",method=RequestMethod.POST)
     @ResponseBody
     public String editStoreImage(HttpServletRequest request,@RequestParam(value="images",required=true)MultipartFile file,@RequestParam int flag) throws IOException{
    	JSONObject resp = new JSONObject();
    	String realPath = request.getSession().getServletContext().getRealPath("/");
    	Tsetting tsetting = null;
    	File image;
    	if(flag == 1){
    		image = new File(realPath,File.separator+"upload"+File.separator+"store"+File.separator+"store_background.jpg");
    		if(image.exists()){
        		image.delete();
        	}
    		tsetting = systemSettingService.getSystemSettingByName(SystemConstants.PAGE_BACKGROUND_File);
        }else{
        	image = new File(realPath,File.separator+"upload"+File.separator+"store"+File.separator+"store_logo.png");
    		if(image.exists()){
        		image.delete();
        	}
    		tsetting = systemSettingService.getSystemSettingByName(SystemConstants.RESTAURANT_LOGO_File);
        }
    	//tsetting.setValue(file.getBytes());
    	FileUtils.copyInputStreamToFile(file.getInputStream(), image);
    	try {
    		systemSettingService.updateSystemsetting(tsetting);
    		resp.put("status", true);
    		resp.put("info", getMessage(request,"operate.success"));
		} catch (MposException be) {
			resp.put("status", false);
			resp.put("info", getMessage(request,be.getErrorID(),be.getMessage()));
		}
    	
 	    return JSON.toJSONString(resp);
    	
    	}
	 
	    @RequestMapping(value="locale",method=RequestMethod.GET)
		public void setLocale(HttpServletRequest request,HttpServletResponse response) throws IOException{
			String referer = request.getHeader("referer");
			response.sendRedirect(referer);
		}
	 
}
