package com.mpos.action;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.mpos.dto.TadminRole;
import com.mpos.dto.TadminUser;
import com.mpos.dto.Ttable;
import com.mpos.model.DataTableParamter;
import com.mpos.model.PagingData;
import com.mpos.service.AdminInfoService;
import com.mpos.service.AdminNodesService;
import com.mpos.service.AdminRoleService;
import com.mpos.service.AdminUserService;
import com.mpos.service.ServiceService;
import com.mpos.service.StoreService;
import com.mpos.service.TableService;

@Controller
@RequestMapping(value="manager")
public class ManagerController extends BaseController {
	@SuppressWarnings("unused")
	private Logger logger = Logger.getLogger(ManagerController.class);	
	
	public List<Ttable> tables = new ArrayList<Ttable>();
	
	@Resource
	private AdminUserService adminUserService;
	
	@Resource
	private AdminNodesService adminNodesService;
	
	@Resource
	private AdminRoleService adminRoleService;
	@Autowired
	private StoreService storeService;
	@Autowired
	private ServiceService serviceService;
	@Autowired
	private TableService tableService;
	@Autowired
	private AdminInfoService adminInfoService;
	
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView adminusers(HttpServletRequest request){
		ModelAndView mav=new ModelAndView();		
		mav.addObject("rolesList", adminRoleService.getAllAdminRoles());
		mav.addObject("storeList", storeService.loadStoreNameAndId());
		//mav.addObject("serviceList", serviceService.load());
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
				adminuser.setRoleId(adminuser.getAdminRole().getRoleId());
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
	@RequestMapping(value="getStoreName/{storeId}",method=RequestMethod.GET)
	@ResponseBody
	public String getStoreName(@PathVariable Integer storeId){
		Map<String, Object> res = getHashMap();
		String storeName = "";
		res.put("status", true);
		try {
			storeName = storeService.get(storeId).getStoreName();
		} catch (Exception e) {
			res.put("status", false);
		}
		res.put("info", storeName);
		return JSON.toJSONString(res);
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
	public String addAdmins(HttpServletRequest request,TadminUser user){
		Map<String, Object> res = getHashMap();
		try {
			user.setAdminId(user.getEmail());
			user.setCreatedBy(getSessionUser(request).getAdminId());
			user.setAdminRole(new TadminRole(3));
			user.setPassword(SecurityTools.MD5(user.getPassword()));
			adminUserService.saveStoreUser(user);
			res.put("status", true);
			res.put("info", getMessage(request,"operate.success"));
		} catch (MposException be) {
			be.printStackTrace();
			res.put("status", false);
			res.put("info", getMessage(request,be.getErrorID(),be.getMessage()));
		}
		return JSON.toJSONString(res);
	}
	
	@RequestMapping(value="editUsers",method=RequestMethod.POST)
	@ResponseBody
	public String updateAdmin(HttpServletRequest request,TadminUser adminuser){		
		JSONObject respJson = new JSONObject();
		try{
			TadminUser user = adminUserService.getAdminUserById(adminuser.getAdminId());
			if(adminuser.getPassword()!=null&&!adminuser.getPassword().isEmpty()){
				user.setPassword(SecurityTools.MD5(adminuser.getPassword()));
			}
			user.setStoreId(adminuser.getStoreId());
			user.setStatus(adminuser.getStatus());
			user.setUpdatedTime(System.currentTimeMillis());
			adminUserService.updateAdminUser(user);
			respJson.put("status", true);
			respJson.put("info", getMessage(request,"operate.success"));
		}
		catch(MposException be){
			respJson.put("status", false);
			be.printStackTrace();
			respJson.put("info", getMessage(request,be.getErrorID(),be.getMessage()));
		}	
		String str=JSON.toJSONString(respJson);		
		return str;
	}

	@RequestMapping(value="{ids}/managers",method=RequestMethod.DELETE)
	@ResponseBody
	public String deleteAdmins(@PathVariable String ids,HttpServletRequest request){
		String[] idstrArr=ids.split(",");		
	//	Integer[] idArr=ConvertTools.stringArr2IntArr(idstrArr);		
		JSONObject respJson = new JSONObject();
		try{
			adminUserService.deleteAdminUserByIds(idstrArr);
			respJson.put("status", true);
			respJson.put("info", getMessage(request,"operate.success"));
		}
		catch(MposException be){
			respJson.put("status", false);
			respJson.put("info", getMessage(request,be.getErrorID(),be.getMessage()));
		}	
		return JSON.toJSONString(respJson);	
	}
	@RequestMapping(value="{ids}/activateusers",method=RequestMethod.GET)
	@ResponseBody
	public String activateRules(@PathVariable String ids,HttpServletRequest request){
		String[] idstrArr=ids.split(",");		
	
		JSONObject respJson = new JSONObject();
		try{
			adminUserService.updateUserStatus(idstrArr, true);
			respJson.put("status", true);
			respJson.put("info", getMessage(request,"operate.success"));
		}
		catch(MposException be){
			respJson.put("status", false);
			respJson.put("info", getMessage(request,be.getErrorID(),be.getMessage()));
		}	
		return JSON.toJSONString(respJson);	
	}
	
	@RequestMapping(value="{ids}/deactivateusers",method=RequestMethod.GET)
	@ResponseBody
	public String deactivateRules(@PathVariable String ids,HttpServletRequest request){
		String[] idstrArr=ids.split(",");				
		JSONObject respJson = new JSONObject();
		try{
			adminUserService.updateUserStatus(idstrArr, false);
			respJson.put("status", true);
			respJson.put("info", getMessage(request,"operate.success"));
		}
		catch(MposException be){
			respJson.put("status", false);
			respJson.put("info", getMessage(request,be.getErrorID(),be.getMessage()));
		}	
		return JSON.toJSONString(respJson);	
	}

}
