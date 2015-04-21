package com.mpos.action;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.mpos.commons.ConvertTools;
import com.mpos.commons.MposException;
import com.mpos.commons.SecurityTools;
import com.mpos.commons.SystemConfig;
import com.mpos.commons.SystemConstants;
import com.mpos.dto.ImageModel;
import com.mpos.dto.TadminUser;
import com.mpos.dto.Tservice;
import com.mpos.dto.TserviceOrder;
import com.mpos.dto.Tstore;
import com.mpos.service.ServiceOrderService;
import com.mpos.service.ServiceService;
import com.mpos.service.StoreService;
/**
 * 店铺控制器
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value="storeSetting")
public class StoreContrller extends BaseController {
	
	@Autowired
	private StoreService storeService;
	@Autowired
	private ServiceService serviceService;
	@Autowired
	private ServiceOrderService serviceOrderService;
	/**
	 * 返回页面状态
	 */
	private boolean status = true;
	/**
	 * 返回消息
	 */
	private String info ="";
	@RequestMapping(value="getStoreInfo",method=RequestMethod.GET)
	public ModelAndView getStoreSetting(HttpServletRequest request){
		Integer storeId = getSessionStoreId(request);
		ModelAndView mav = new ModelAndView();
		try {
			Tstore store = storeService.get(storeId);
			String logoPath = getImagePath(store.getStoreLogo(), storeId, request, "logo");
			String backgroundPath = getImagePath(store.getStoreLogo(), storeId, request, "background");
			store.setStoreLogo(null);
			store.setStoreBackground(null);
			store.setLogoPath(logoPath);
			store.setBackgroundPath(backgroundPath);
			mav.addObject("store", store);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mav.setViewName("store/storesetting");
		return mav;
	}
	/**
	 * 上传或者跟新logo
	 * @param request
	 * @param file
	 * @return
	 */
	@RequestMapping(value="/uploadLogo",method=RequestMethod.POST)
	@ResponseBody
	public String uploadLogo(HttpServletRequest request,@RequestParam(value="images",required=true)MultipartFile file){
		//更新参数
		//Map<String, Object> params = getHashMap();
		//返回结果
		Map<String, Object> res = getHashMap();
		//获取店铺ID
		Integer storeId = getSessionUser(request).getStoreId();
		//修改HQL
		//String updateLogoHql = "update Tstore set storeLogo=:storeLogo where storeId=:storeId";
		//上传文件后缀名
		String logoSuffix = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
		//上传pathName
		String realPath = request.getSession().getServletContext().getRealPath("/");
		String logoPath = SystemConstants.STORE_SET_PATH+"logo_"+storeId+logoSuffix;
		File  logoFile=null;
		try {
			ImageModel model = new ImageModel();
			model.setType(ImageModel.LOGO);
			model.setStoreId(storeId);
			model.setImage(file.getBytes());
			storeService.updateImage(model);
			/*params.put("storeLogo", file.getBytes());
			params.put("storeId", storeId);
			storeService.updateImage(updateLogoHql, params);*/
			logoFile = new File(realPath+logoPath);
			if(logoFile.exists()){
				logoFile.delete();
			}
			FileUtils.copyInputStreamToFile(file.getInputStream(), logoFile);
			res.put("path", logoPath);
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
	/**
	 * 
	 * @param image
	 * @param logoPath
	 * @param storeId
	 * @param request
	 * @return
	 */
	private String getImagePath(byte[] image,Integer storeId,HttpServletRequest request,String name){
		InputStream is = null;
		String logoPath = SystemConstants.STORE_SET_PATH+name+"_"+storeId+"."+"jpg";
		String realPath = request.getSession().getServletContext().getRealPath("/");
		if(image!=null){
			File	logo = new File(realPath+logoPath);
			if(!logo.exists()){
				is = new ByteArrayInputStream(image);
				try {
					FileUtils.copyInputStreamToFile(is, logo);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			logoPath = SystemConstants.STORE_UP_PATH+name+"_"+storeId+"."+"jpg";
		}else{
			logoPath = SystemConstants.STORE_UP_PATH+name+"_"+0+"."+"jpg";
		}
		return logoPath.substring(logoPath.indexOf("/")+1);
	}
	
	/**
	 * 上传或者跟新background
	 * @param request
	 * @param file
	 * @return
	 */
	@RequestMapping(value="/uploadBackground",method=RequestMethod.POST)
	@ResponseBody
	public String uploadBackground(HttpServletRequest request,@RequestParam(value="images",required=true)MultipartFile file){
		//更新参数
		//Map<String, Object> params = getHashMap();
		//返回结果
		Map<String, Object> res = getHashMap();
		//获取店铺ID
		Integer storeId = getSessionUser(request).getStoreId();
		//修改HQL
		//String updateBackHql = "update Tstore set storeBackground=:storeBackground where storeId=:storeId";
		//上传文件后缀名
		String logoSuffix = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
		//上传pathName
		String realPath = request.getSession().getServletContext().getRealPath("/");
		String backPath = SystemConstants.STORE_SET_PATH+"background_"+storeId+logoSuffix;
		//realPath += realPath+SystemConstants.STORE_SET_PATH+"background_"+storeId+"."+logoSuffix;
		File  logoFile=null;
		try {
			ImageModel model = new ImageModel();
			model.setType(ImageModel.LOGO);
			model.setStoreId(storeId);
			model.setImage(file.getBytes());
			storeService.updateImage(model);
			//params.put("storeBackground", file.getBytes());
			//params.put("storeId", storeId);
			//storeService.update(updateBackHql, params);
			logoFile = new File(realPath+backPath);
			if(logoFile.exists()){
				logoFile.delete();
			}
			FileUtils.copyInputStreamToFile(file.getInputStream(), logoFile);
			res.put("path", backPath);
			info = getMessage(request,"operate.success");
		} catch (Exception e) {
			status = false;
			info = e.getMessage();
		}
		res.put("status", status);
		res.put("info", info);
		return JSON.toJSONString(res);
	}
	/**
	 * 修改key
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/changeKey",method=RequestMethod.POST)
	@ResponseBody
	public String changePublicKey(HttpServletRequest request,String value){
		//更新参数
		Map<String, Object> params = getHashMap();
		//返回结果
		Map<String, Object> res = getHashMap();
		TadminUser user = getSessionUser(request);
		//修改HQL
		String updatePublicKeyHql = "update Tstore set publicKey=:publicKey where storeId=:storeId";
		String query = "select publicKey from Tstore where storeId=:storeId";
		try {
			params.put("storeId", user.getStoreId());
			String oldKey = (String) storeService.getObject(query, params);
			String key = SecurityTools.MD5(user.getEmail()+oldKey);
			params.put("storeBackground", value);
			storeService.update(updatePublicKeyHql, params);
			SystemConfig.STORE_TAKEN_MAP.remove(key);
			SystemConfig.STORE_TAKEN_MAP.put(SecurityTools.MD5(user.getEmail()+value), user.getStoreId());
			info = getMessage(request,"operate.success");
		} catch (MposException be) {
			info = getMessage(request, be.getErrorID(), be.getMessage());
			status = false;
		}
		res.put("status", status);
		res.put("info", info);
		return JSON.toJSONString(res);
	}
	/**
	 * 修改客户语言配置
	 * @return
	 */
	@RequestMapping(value="/changeLangSet",method=RequestMethod.POST)
	@ResponseBody
	public String changeLangSet(HttpServletRequest request,String storeLangIds){
		Map<String, Object> res = getHashMap();
		//更新参数
		Map<String, Object> params = getHashMap();
		params.put("storeId", getSessionStoreId(request));
		params.put("storeLangId", storeLangIds);
		String updateLangHql = "update Tstore set storeLangId=:storeLangId where storeId=:storeId";
		try {
			storeService.update(updateLangHql, params);
			info = getMessage(request,"operate.success");
		} catch (MposException be) {
			info = getMessage(request, be.getErrorID(), be.getMessage());
			status = false;
		}
		return JSON.toJSONString(res);
	}
	
	/**
	 * 修改店铺名称
	 * @return
	 */
	@RequestMapping(value="changeStoreName",method=RequestMethod.POST)
	@ResponseBody
	public String changeStoreName(HttpServletRequest request,String value){
		Map<String, Object> res = getHashMap();
		//更新参数
		Map<String, Object> params = getHashMap();
		params.put("storeId", getSessionStoreId(request));
		params.put("storeName", value);
		String updateLangHql = "update Tstore set storeName=:storeName where storeId=:storeId";
		try {
			storeService.update(updateLangHql, params);
			info = getMessage(request,"operate.success");
			res.put("msg", value);
		} catch (MposException be) {
			info = getMessage(request, be.getErrorID(), be.getMessage());
			status = false;
		}
		res.put("status", status);
		res.put("info", info);
		return JSON.toJSONString(res);
	}
	
	/**
	 * 修改店铺货币符号
	 * @return
	 */
	@RequestMapping(value="/changeStoreCurrency",method=RequestMethod.POST)
	@ResponseBody
	public String changeStoreCurrency(HttpServletRequest request,String value){
		Map<String, Object> res = getHashMap();
		//更新参数storeCurrency
		Map<String, Object> params = getHashMap();
		params.put("storeId", getSessionStoreId(request));
		params.put("storeCurrency", value);
		String updateLangHql = "update Tstore set storeCurrency=:storeCurrency where storeId=:storeId";
		try {
			storeService.update(updateLangHql, params);
			info = getMessage(request,"operate.success");
		} catch (MposException be) {
			info = getMessage(request, be.getErrorID(), be.getMessage());
			status = false;
		}
		return JSON.toJSONString(res);
	}
	
	/**
	 * 修改订阅服务
	 * @return
	 */
	@RequestMapping(value="/changeService",method=RequestMethod.POST)
	@ResponseBody
	public String changeService(HttpServletRequest request,Integer serviceId){
		Map<String, Object> res = getHashMap();
		//更新参数
		Map<String, Object> params = getHashMap();
		Integer storeId = getSessionStoreId(request);
		params.put("storeId", storeId);
		//查询以前的服务日期
		String queryStoreHql = "select newTstore(serviceId,serviceDate) from Tstore where storeId=:storeId";
		//更新新的订阅服务
		String updateServiceHql = "update Tstore set serviceId=:serviceId,serviceDate=:serviceDate where storeId=:storeId";
		try {
			Tstore store = storeService.selectOne(queryStoreHql, params);
			long serviceDate = store.getServiceDate();
			Tservice service = serviceService.get(serviceId);
			Integer validDays = service.getValidDays();
			
			
			params.put("serviceId", serviceId);
			params.put("serviceDate", ConvertTools.longTimeAIntDay(serviceDate, validDays));
			storeService.update(updateServiceHql, params);
			TserviceOrder serviceOrder = new TserviceOrder();
			serviceOrder.setCreateTime(System.currentTimeMillis());
			serviceOrder.setPrice(service.getServicePrice());
			serviceOrder.setServiceId(serviceId);
			serviceOrder.setEmail(getSessionUser(request).getEmail());
			serviceOrder.setStatus(false);
			info = getMessage(request,"operate.success");
		} catch (MposException be) {
			info = getMessage(request, be.getErrorID(), be.getMessage());
			status = false;
		}
		return JSON.toJSONString(res);
	}
}
