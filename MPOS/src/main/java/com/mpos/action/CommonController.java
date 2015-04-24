/**   
 * @Title: UserController.java 
 * @Package com.uswop.action 
 * @Description: TODO
 * @author Phills Li    
 * @date Sep 2, 2014 7:25:22 PM 
 * @version V1.0   
 */
package com.mpos.action;

import java.util.Calendar;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.mpos.commons.ConvertTools;
import com.mpos.commons.EMailTool;
import com.mpos.commons.MposException;
import com.mpos.commons.SecurityTools;
import com.mpos.commons.SystemConfig;
import com.mpos.dto.TadminRole;
import com.mpos.dto.TadminUser;
import com.mpos.dto.TemaiMessage;
import com.mpos.dto.Tservice;
import com.mpos.dto.Tstore;
import com.mpos.service.AdminUserService;
import com.mpos.service.ServiceService;
import com.mpos.service.StoreService;

/**
 * @ClassName: UserController
 * @Description: TODO
 * @author Phills Li
 * @date Sep 2, 2014 7:25:22 PM
 * 
 */

@Controller
@RequestMapping("/common")
public class CommonController extends BaseController {

	@SuppressWarnings("unused")
	private Logger logger = Logger.getLogger(CommonController.class);
	
	@Autowired
	private AdminUserService adminUserService;
	@Autowired
	private StoreService storeService;
	@Autowired
	private ServiceService serviceService;
	
	@RequestMapping(value="header",method=RequestMethod.GET)
	public ModelAndView header(HttpServletRequest request){
		ModelAndView mav=new ModelAndView();
		TadminUser tUser=getSessionUser(request);
		mav.addObject("user", tUser);
		mav.setViewName("common/header");
		return mav;
	}
	
	@RequestMapping(value="left",method=RequestMethod.GET)
	public ModelAndView left(HttpServletRequest request){
		ModelAndView mav=new ModelAndView();		
		mav.addObject("menus", SystemConfig.Admin_Nodes_Menu_Map);
		mav.setViewName("common/left");
		return mav;
	}
	
	
	@RequestMapping(value="footer",method=RequestMethod.GET)
	public ModelAndView footer(HttpServletRequest request){
		ModelAndView mav=new ModelAndView();
		mav.setViewName("common/footer");
		return mav;
	}
	
	@RequestMapping(value="noRights",method=RequestMethod.GET)
	public ModelAndView noRights(HttpServletRequest request){
		ModelAndView mav=new ModelAndView();
		mav.setViewName("error/errpage");
		return mav;
	}
	
	@RequestMapping(value="notice",method=RequestMethod.GET)
	public ModelAndView notice(HttpServletRequest request){
		ModelAndView mav=new ModelAndView();
		mav.setViewName("common/notice");
		return mav;
	}
	@RequestMapping(value="register",method=RequestMethod.POST)
	@ResponseBody
	public String register(HttpServletRequest request,TadminUser user,Integer serviceId){
		Map<String, Object> res = getHashMap();
		try {
			Tstore store = new Tstore();
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
			store.setStoreCurrency("￥");
			store.setStoreLangId("1");
			storeService.save(store);
			user.setStoreId(store.getStoreId());
			user.setCreatedTime(System.currentTimeMillis());
			user.setCreatedBy("admin");
			user.setAdminRole(new TadminRole(service.getRoleId()));
			if(user.getPassword().isEmpty()){
				String random = UUID.randomUUID().toString().trim().replace("-","").substring(0,6);
				TemaiMessage message = new TemaiMessage();
				message.setTo(user.getEmail());
				message.setText("your account:  "+user.getAdminId()+"|register time:  "+Calendar.getInstance().getTime()+" |password:  "+random);
				message.setSubject("MPOS Password");
				EMailTool.send(message);
				user.setPassword(random);
			}
			user.setPassword(SecurityTools.MD5(user.getPassword()));
			adminUserService.createAdminUser(user);
			res.put("status", true);
			res.put("info", "Register Success");
		} catch (MposException e) {
			e.printStackTrace();
			res.put("status", false);
			res.put("info", "Register Failure");
		}
		return JSON.toJSONString(res);
	}
}
