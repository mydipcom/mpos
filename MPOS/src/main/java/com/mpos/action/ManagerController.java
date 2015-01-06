package com.mpos.action;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mpos.commons.MposException;
import com.mpos.commons.SecurityTools;
import com.mpos.dto.TadminUser;
import com.mpos.model.DataTableParamter;
import com.mpos.model.PagingData;
import com.mpos.service.AdminNodesService;
import com.mpos.service.AdminRoleService;
import com.mpos.service.AdminUserService;

@Controller
@RequestMapping(value="manager")
public class ManagerController extends BaseController {
	private Logger logger = Logger.getLogger(ManagerController.class);	
	
	@Resource
	private AdminUserService adminUserService;
	
	@Resource
	private AdminNodesService adminNodesService;
	
	@Resource
	private AdminRoleService adminRoleService;
	
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView adminusers(HttpServletRequest request){
		ModelAndView mav=new ModelAndView();		
		mav.addObject("rolesList", adminRoleService.getAllAdminRoles());
		mav.setViewName("manager/Adminusers");		
		return mav;
	}
	
	@RequestMapping(value="managersList",method=RequestMethod.GET)
	@ResponseBody
	public String AdminusersList(HttpServletRequest request,DataTableParamter dtp){		
		PagingData pagingData=adminUserService.loadAdminUserList(dtp);
		
		pagingData.setSEcho(dtp.sEcho);
		if(pagingData.getAaData()==null){
			Object[] objs=new Object[]{};
			pagingData.setAaData(objs);
		}
		else{
			Object[] aaData=pagingData.getAaData();
			for(int i=0;i<aaData.length;i++){
				TadminUser adminuser=(TadminUser)aaData[i];
				if(adminuser.getCreatedBy()==null){
					adminuser.setCreatedBy("");
					adminuser.setCreatedTimeStr("");
				}
				if(adminuser.getUpdatedBy()==null){
					adminuser.setUpdatedBy("");
					adminuser.setUpdatedTimeStr("");
				}
				aaData[i]=adminuser;
			}
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
	@RequestMapping(value="addUsers",method=RequestMethod.POST)
	@ResponseBody
	public String addAdmins(HttpServletRequest request,TadminUser adminuser){
		TadminUser ad=getSessionUser(request);
		JSONObject respJson = new JSONObject();
		try{
			adminuser.setCreatedBy(ad.getAdminId());
			adminuser.setStatus(true);
			adminuser.setPassword(SecurityTools.SHA1(adminuser.getPassword()));
			adminuser.setCreatedTime(System.currentTimeMillis());
			String email = adminuser.getEmail();
			adminuser.setEmail(email.toLowerCase());
			adminUserService.createAdminUser(adminuser);
			respJson.put("status", true);
		}
		catch(MposException be){
			respJson.put("status", false);
			respJson.put("info", getMessage(request,be.getErrorID(),be.getMessage()));
		}		
		return JSON.toJSONString(respJson);
	}
	
	@RequestMapping(value="editUsers",method=RequestMethod.POST)
	@ResponseBody
	public String updateAdmin(HttpServletRequest request,TadminUser adminuser){		

		SimpleDateFormat simpleDateFormat =new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		TadminUser ad=getSessionUser(request);
		JSONObject respJson = new JSONObject();
		Date sdate = null;
		try{
			try {
				String ss=adminuser.getCreatedTimeStr();
				sdate = simpleDateFormat.parse(ss);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			adminuser.setCreatedTime(sdate.getTime());
			adminuser.setUpdatedBy(ad.getAdminId());
			adminuser.setUpdatedTime(System.currentTimeMillis());
			String email = adminuser.getEmail();
			adminuser.setEmail(email.toLowerCase());
			if(adminuser.getPassword()!=null){
			adminuser.setPassword(SecurityTools.SHA1(adminuser.getPassword()));
			}else {
			adminuser.setPassword(adminUserService.getAdminUserById(adminuser.getAdminId()).getPassword());
			}
			adminUserService.updateAdminUser(adminuser);
			respJson.put("status", true);
		}
		catch(MposException be){
			respJson.put("status", false);
			respJson.put("info", getMessage(request,be.getErrorID(),be.getMessage()));
		}	
		String str=JSON.toJSONString(respJson);		
		return str;
	}

	@RequestMapping(value="managers/{ids}",method=RequestMethod.DELETE)
	@ResponseBody
	public String deleteAdmins(@PathVariable String ids,HttpServletRequest request){
		String[] idstrArr=ids.split(",");		
	//	Integer[] idArr=ConvertTools.stringArr2IntArr(idstrArr);		
		JSONObject respJson = new JSONObject();
		try{
			adminUserService.deleteAdminUserByIds(idstrArr);
			respJson.put("status", true);
		}
		catch(MposException be){
			respJson.put("status", false);
			respJson.put("info", getMessage(request,be.getErrorID(),be.getMessage()));
		}	
		return JSON.toJSONString(respJson);	
	}
	@RequestMapping(value="activateusers/{ids}",method=RequestMethod.POST)
	@ResponseBody
	public String activateRules(@PathVariable String ids,HttpServletRequest request){
		String[] idstrArr=ids.split(",");		
	
		JSONObject respJson = new JSONObject();
		try{
			adminUserService.activateUsersByIds(idstrArr);
			respJson.put("status", true);
		}
		catch(MposException be){
			respJson.put("status", false);
			respJson.put("info", getMessage(request,be.getErrorID(),be.getMessage()));
		}	
		return JSON.toJSONString(respJson);	
	}
	
	@RequestMapping(value="deactivateusers/{ids}",method=RequestMethod.POST)
	@ResponseBody
	public String deactivateRules(@PathVariable String ids,HttpServletRequest request){
		String[] idstrArr=ids.split(",");				
		JSONObject respJson = new JSONObject();
		try{
			adminUserService.deactivateUsersByIds(idstrArr);
			respJson.put("status", true);
		}
		catch(MposException be){
			respJson.put("status", false);
			respJson.put("info", getMessage(request,be.getErrorID(),be.getMessage()));
		}	
		return JSON.toJSONString(respJson);	
	}

}
