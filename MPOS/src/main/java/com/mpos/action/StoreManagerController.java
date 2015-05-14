package com.mpos.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
	public String addStore(HttpServletRequest request,Tstore store){
		Map<String, Object> res = getHashMap();
		try {
			if(store.getServiceId()==null){
				store.setServiceId(0);
			}
			storeService.save(store);
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
	
	@RequestMapping(value="editStore",method=RequestMethod.POST)
	@ResponseBody
	public String editStore(HttpServletRequest request,Tstore store){
		JSONObject respJson=new JSONObject();
		try {
			Tstore oldStore=storeService.get(store.getStoreId());
			if(oldStore!=null){
				oldStore.setStoreName(store.getStoreName());
				oldStore.setServiceId(store.getServiceId());
				oldStore.setPublicKey(store.getPublicKey());
				oldStore.setStoreCurrency(store.getStoreCurrency());
				oldStore.setPrintType(store.getPrintType());
			}
			storeService.update(oldStore);							
			respJson.put("status", true);
			respJson.put("info", getMessage(request,"operate.success"));
		} catch (MposException be) {
			respJson.put("status", false);
			respJson.put("info", be.getMessage());
		}
		return JSON.toJSONString(respJson);
	}
	
	@RequestMapping(value = "/storeList", method = RequestMethod.GET)
	@ResponseBody
	public String storeList(HttpServletRequest request, DataTableParamter dtp) {
		System.out.println(System.currentTimeMillis());
		PagingData pagingData = storeService.load(dtp);
		if (pagingData.getAaData() == null) {
			Object[] objs = new Object[] {};
			pagingData.setAaData(objs);
		}else{
			Object[] objects = pagingData.getAaData();
			List<Map<String, Object>> maps = new ArrayList<Map<String,Object>>();
		//	List<Tstore> stores = new ArrayList<Tstore>();
			for (Object object : objects) {
				Tstore store = (Tstore)object;
				Map<String, Object> params = getHashMap();
				String sql = "select service.service_name,admin.email from mpos_cloud.mpos_admin as admin,mpos_cloud.mpos_service as service where admin.store_id =:storeId and service.service_id=:serviceId";
				//Tservice service = serviceService.get(store.getServiceId());
				//List<TadminUser> users = adminUserService.getUserByStoreId(store.getStoreId());
				params.put("storeId", store.getStoreId());
				params.put("serviceId", store.getServiceId());
				List<Object[]> res = storeService.getBySql(sql, params);
				Map<String, Object> o = getHashMap();
				o.put("storeId", store.getStoreId());
				o.put("storeName", store.getStoreName());
				o.put("createTimeStr", ConvertTools.longToDateString(store.getCreateTime()));
				o.put("date",ConvertTools.longToDateString(store.getServiceDate()));
				o.put("status", store.getCreateTimeStr());
				o.put("serviceId", store.getServiceId());
				o.put("publicKey", store.getPublicKey());
				o.put("printType", store.getPrintType());
				o.put("storeCurrency", store.getStoreCurrency());
				StringBuffer storeAdmin = new StringBuffer();
				storeAdmin.append("");
				if(res!=null&&res.size()>0){
					o.put("serviceName", res.get(0)[0]);
					for (Object[] user : res) {
						if(user.length==2){
							storeAdmin.append(user[1]+",");
						}
						}
					}else{
					o.put("serviceName", "");
				}
				o.put("email", storeAdmin.toString());
				o.put("status", store.getStatus());
				maps.add(o);
			}
			pagingData.setAaData(maps.toArray());
		}
		pagingData.setSEcho(dtp.sEcho);
		String rightsListJson = JSON.toJSONString(pagingData);
		System.out.println(System.currentTimeMillis());
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
	public String deleteStore(HttpServletRequest request,@PathVariable String storeIds,Integer type){
		Map<String, Object> params = getHashMap();
		Map<String, Object> res = getHashMap();
		String hql = "update Tstore set status=:status where storeId in (:storeIds)";
		String[] idstr = storeIds.split(",");
		try {
			if(type==1){
				params.put("status", true);
			}else{
				params.put("status", false);
			}
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
