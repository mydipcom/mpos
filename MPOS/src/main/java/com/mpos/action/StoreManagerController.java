package com.mpos.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.mpos.commons.ConvertTools;
import com.mpos.commons.EMailTool;
import com.mpos.commons.MposException;
import com.mpos.commons.SecurityTools;
import com.mpos.dto.TadminRole;
import com.mpos.dto.TadminUser;
import com.mpos.dto.TemaiMessage;
import com.mpos.dto.Tservice;
import com.mpos.dto.Tstore;
import com.mpos.dto.Ttable;
import com.mpos.model.DataTableParamter;
import com.mpos.model.PagingData;
import com.mpos.service.AdminUserService;
import com.mpos.service.ServiceService;
import com.mpos.service.StoreService;
import com.mpos.service.TableService;

/**
 * 店铺管理
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value="storeManager")
public class StoreManagerController extends BaseController {
	@Autowired
	private StoreService storeService;
	@Autowired
	private ServiceService serviceService;
	@Autowired
	private TableService tableService;
	@Autowired
	private AdminUserService adminUserService;
	/**
	 * 返回页面状态
	 */
	private boolean status = true;
	
	public List<Ttable> tables = new ArrayList<Ttable>();
	/**
	 * 返回消息
	 */
	private String info ="";
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView store(HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		List<Tservice> servicelist = serviceService.load();
		mav.addObject("serviceList", servicelist);
		mav.setViewName("store/store");
		return mav;
	}
	
	
	@RequestMapping(value="addStore",method = RequestMethod.POST)
	@ResponseBody
	public String addStore(HttpServletRequest request,Tstore store,TadminUser user){
		Map<String, Object> res = getHashMap();
		try {
			if(store.getStoreId()==null){
				store.setServiceId(0);
			}
			Tservice service=serviceService.get(store.getServiceId());
			store.setStoreLangId("1");
			store.setServiceDate(ConvertTools.longTimeAIntDay(System.currentTimeMillis(), service.getValidDays()));
			storeService.save(store);
			tables.add(new Ttable("A01", 4, store.getStoreId()));
			tables.add(new Ttable("A02", 2, store.getStoreId()));
			tables.add(new Ttable("A03", 6, store.getStoreId()));
			for (Ttable table : tables) {
				tableService.create(table);
			}
			user.setStoreId(store.getStoreId());
			user.setCreatedTime(System.currentTimeMillis());
			user.setAdminId(user.getEmail());
			user.setCreatedBy("admin");
			user.setStatus(true);
			user.setAdminRole(new TadminRole(service.getRoleId()));
			if(user.getPassword()==null||user.getPassword().isEmpty()){
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
			info = getMessage(request,"operate.success");
		} catch (Exception e) {
			e.printStackTrace();
			status = false;
			info = e.getMessage();
		}
		res.put("status", status);
		res.put("info", info);
		return JSON.toJSONString(res);
	}
	
	@RequestMapping(value = "/storeList", method = RequestMethod.GET)
	@ResponseBody
	public String storeList(HttpServletRequest request, DataTableParamter dtp) {
		PagingData pagingData = storeService.load(dtp);
		if (pagingData.getAaData() == null) {
			Object[] objs = new Object[] {};
			pagingData.setAaData(objs);
		}else{
			Object[] objects = pagingData.getAaData();
			List<Tstore> stores = new ArrayList<Tstore>();
			for (Object object : objects) {
				Tstore store = (Tstore)object;
				store.setStoreBackground(null);
				store.setStoreLogo(null);
				store.setCreateTimeStr(ConvertTools.longToDateString(store.getCreateTime()));
				stores.add(store);
			}
			pagingData.setAaData(stores.toArray());
		}
		pagingData.setSEcho(dtp.sEcho);
		String rightsListJson = JSON.toJSONString(pagingData);
		return rightsListJson;
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
	/**
	 * 禁用店铺
	 * @param request
	 * @param storeIds 
	 * @return
	 */
	@RequestMapping(value = "/{storeIds}", method = RequestMethod.GET)
	@ResponseBody
	public String deleteStore(HttpServletRequest request,@PathVariable String storeIds){
		Map<String, Object> params = getHashMap();
		Map<String, Object> res = getHashMap();
		String hql = "update Tstore set status=:status where storeId in (:storeIds)";
		String[] idstr = storeIds.split(",");
		try {
			params.put("status", false);
			params.put("storeIds", ConvertTools.stringArr2IntArr(idstr));
			storeService.update(hql, params);
			info = getMessage(request,"operate.success");
		} catch (MposException e) {
			status = false;
			info = e.getMessage();
		}
		res.put("status", status);
		res.put("info", info);
		return JSON.toJSONString(res);
	}
}
