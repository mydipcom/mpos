/**   
 * @Title: UserController.java 
 * @Package com.uswop.action 
 * @Description: TODO
 * @author Phills Li    
 * @date Sep 2, 2014 7:25:22 PM 
 * @version V1.0   
 */
package com.mpos.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import com.mpos.commons.MposException;
import com.mpos.commons.SecurityTools;
import com.mpos.commons.SystemConfig;
import com.mpos.dto.TadminInfo;
import com.mpos.dto.TadminRole;
import com.mpos.dto.TadminUser;
import com.mpos.dto.Tservice;
import com.mpos.dto.Tstore;
import com.mpos.dto.Ttable;
import com.mpos.service.AdminInfoService;
import com.mpos.service.AdminUserService;
import com.mpos.service.ServiceService;
import com.mpos.service.StoreService;
import com.mpos.service.TableService;

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
	public List<Ttable> tables = new ArrayList<Ttable>();
	@Autowired
	private AdminUserService adminUserService;
	@Autowired
	private StoreService storeService;
	@Autowired
	private ServiceService serviceService;
	@Autowired
	private TableService tableService;
	@Autowired
	private AdminInfoService adminInfoService;
	
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
	@RequestMapping(value="getServices",method=RequestMethod.POST)
	@ResponseBody
	public String getService(HttpServletRequest request){
		Map<String, Object> res = getHashMap();
		try {
			List<Tservice> info = new ArrayList<Tservice>();
			List<Tservice> services = serviceService.load();
			for (Tservice tservice : services) {
				tservice.setContent(tservice.getServiceName()+"-"+tservice.getServicePrice()+"-"+tservice.getValidDays()+"-"+tservice.getContent());
				tservice.setRoleId(null);
				tservice.setServiceName(null);
				tservice.setServicePrice(null);
				tservice.setValidDays(null);
				info.add(tservice);
			}
			res.put("status", true);
			res.put("info", info);
		} catch (Exception e) {
			// TODO: handle exception
			res.put("status", false);
			res.put("info", e.getMessage());
		}
		return JSON.toJSONString(res);
	}
	@RequestMapping(value="register",method=RequestMethod.POST)
	@ResponseBody
	public String register(HttpServletRequest request,TadminUser user,Integer serviceId,String mobile){
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
			/*if(user.getPassword().isEmpty()){
				String random = UUID.randomUUID().toString().trim().replace("-","").substring(0,6);
				TemaiMessage message = new TemaiMessage();
				message.setTo(user.getEmail());
				message.setText("your account:  "+user.getAdminId()+"|register time:  "+Calendar.getInstance().getTime()+" |password:  "+random);
				message.setSubject("MPOS Password");
				EMailTool.send(message);
				user.setPassword(random);
			}*/
			user.setPassword(SecurityTools.MD5(user.getPassword()));
			adminUserService.createAdminUser(user);
			TadminInfo info = new TadminInfo();
			info.setAdminId(user.getAdminId());
			info.setMobile(mobile);
			adminInfoService.createAdminInfo(info);
			res.put("status", true);
			res.put("info", "Register Success");
		} catch (MposException e) {
			if(store.getStoreId()!=null){
				storeService.deleteByStoreId(store.getStoreId(),user.getAdminId());
			}
			e.printStackTrace();
			res.put("status", false);
			res.put("info", "Register Failure");
		}
		return JSON.toJSONString(res);
	}
}
