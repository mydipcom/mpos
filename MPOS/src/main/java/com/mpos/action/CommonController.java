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
import com.mpos.commons.EMailTool;
import com.mpos.commons.IpUtils;
import com.mpos.commons.MposException;
import com.mpos.commons.SystemConfig;
import com.mpos.dto.TadminUser;
import com.mpos.dto.TemaiMessage;
import com.mpos.dto.Tmessage;
import com.mpos.dto.Tservice;
import com.mpos.dto.Ttable;
import com.mpos.service.AdminInfoService;
import com.mpos.service.AdminUserService;
import com.mpos.service.MessageService;
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
	@Autowired
	private MessageService messageService;
	
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
	
	/**
	 * 添加验证tableName
	 * @param tableName 桌号
	 * @return
	 */
	@RequestMapping(value="/checkEmail",method=RequestMethod.POST)
	@ResponseBody
	public String checkTableName(String email,HttpServletRequest request){
		return JSON.toJSONString(!adminUserService.emailExist(email));
	}
	
	@RequestMapping(value="addMsg",method=RequestMethod.POST)
	@ResponseBody
	public String add(HttpServletRequest request,Tmessage message){
		Map<String, Object> res = getHashMap();
		try {
			messageService.create(message);
			res.put("status", true);
			res.put("info", "留言成功");
		} catch (Exception e) {
			res.put("status", false);
			res.put("info", e.getMessage());
		}
		return JSON.toJSONString(res);
	}
	@RequestMapping(value="getServices",method=RequestMethod.POST)
	@ResponseBody
	public String getService(HttpServletRequest request){
		Map<String, Object> res = getHashMap();
		try {
//			List<Tservice> info = new ArrayList<Tservice>();
			List<Tservice> services = serviceService.load();
//			for (Tservice tservice : services) {
//				tservice.setContent(tservice.getServiceName()+": "+tservice.getServicePrice()+"元-"+tservice.getValidDays()+"天-"+tservice.getContent());
//				tservice.setRoleId(null);
//				tservice.setServiceName(tservice.getServiceName());
//				tservice.setServicePrice(tservice.getServicePrice());
//				tservice.setValidDays(tservice.getValidDays());
//				info.add(tservice);
//			}
			res.put("status", true);
			res.put("info", services);
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
		try {
			boolean status = false;
			if(serviceId==null||serviceId==0){
				status = true;
			}
			Map<String,String> map = serviceService.register(user, serviceId, mobile,status);
			TemaiMessage message = TemaiMessage.getCreate(map);
			EMailTool.send(message);
			if(!status){
				res.put("payUrl","http://www.baidu.com");
			}
			res.put("isPay",!status);
			res.put("status", true);
			res.put("info", "注册成功");
		} catch (MposException e) {
			e.printStackTrace();
			res.put("status", false);
			res.put("info", "失败");
		}
		return JSON.toJSONString(res);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/pay")
	@ResponseBody
	public String test(HttpServletRequest request){
		String ip = IpUtils.getIpAddr(request);
		System.out.println(ip);
		Map< String, Object> re = request.getParameterMap();
		for (String key : re.keySet()) {
			System.out.println(key+":"+re.get(key));
		}
		Map<String, Object> res = getHashMap();
		res.put("is_success", "T");
		res.put("out_trade_no", System.currentTimeMillis());
		return JSON.toJSONString("success");
	}
}
