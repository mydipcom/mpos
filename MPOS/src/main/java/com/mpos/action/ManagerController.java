package com.mpos.action;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.mpos.commons.ConvertTools;
import com.mpos.commons.MposException;
import com.mpos.commons.SecurityTools;
import com.mpos.dto.TadminInfo;
import com.mpos.dto.TadminRole;
import com.mpos.dto.TadminUser;
import com.mpos.dto.Tservice;
import com.mpos.dto.Tstore;
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
		mav.addObject("serviceList", serviceService.load());
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
	public String addAdmins(HttpServletRequest request,TadminUser user,Integer serviceId){
		Map<String, Object> res = getHashMap();
		Tstore store = new Tstore();
		try {
			if(serviceId==null){
				serviceId=0;
			}
			Tservice service=serviceService.get(serviceId);
			store.setServiceId(serviceId);
			store.setPublicKey("CampRay");
			store.setStoreName("CampRay");
			store.setStatus(true);
			store.setAutoSyncStatus(false);
			store.setServiceDate(ConvertTools.longTimeAIntDay(System.currentTimeMillis(), service.getValidDays()));
			store.setStoreCurrency("$");
			store.setStoreLangId("1");
			storeService.save(store);
			tables.add(new Ttable("A01", 4, store.getStoreId()));
			tables.add(new Ttable("A02", 2, store.getStoreId()));
			tables.add(new Ttable("A03", 6, store.getStoreId()));
			for (Ttable table : tables) {
				tableService.create(table);
			}
			user.setStoreId(store.getStoreId());
			user.setCreatedTime(System.currentTimeMillis());
			user.setCreatedBy("admin");
			user.setAdminId(user.getEmail());
			user.setAdminRole(new TadminRole(service.getRoleId()));
			user.setPassword(SecurityTools.MD5(user.getPassword()));
			adminUserService.createAdminUser(user);
			TadminInfo info = new TadminInfo();
			info.setAdminId(user.getAdminId());
			//info.setMobile(mobile);
			adminInfoService.createAdminInfo(info);
			res.put("status", true);
			res.put("info", getMessage(request,"operate.success"));
		} catch (MposException be) {
			if(store.getStoreId()!=null){
				storeService.deleteByStoreId(store.getStoreId(),user.getAdminId());
			}
			be.printStackTrace();
			res.put("status", false);
			res.put("info", getMessage(request,be.getErrorID(),be.getMessage()));
		}
		return JSON.toJSONString(res);
	}
	
	@RequestMapping(value="editUsers",method=RequestMethod.POST)
	@ResponseBody
	public String updateAdmin(HttpServletRequest request,TadminUser adminuser){		

		SimpleDateFormat simpleDateFormat =new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		TadminUser ad=getSessionUser(request);
		JSONObject respJson = new JSONObject();
		Date sdate = null;
		try{
			
			if(adminuser.getPassword()!=null && !adminuser.getPassword().isEmpty()){
				adminuser.setPassword(SecurityTools.SHA1(adminuser.getPassword()));
				}else {
				adminuser = adminUserService.getAdminUserById(adminuser.getAdminId());
				}
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
			
			adminUserService.updateAdminUser(adminuser);
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

	@RequestMapping(value="managers/{ids}",method=RequestMethod.DELETE)
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
