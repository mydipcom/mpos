package com.mpos.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.mpos.commons.ConvertTools;
import com.mpos.commons.MposException;
import com.mpos.dto.Tservice;
import com.mpos.model.DataTableParamter;
import com.mpos.model.PagingData;
import com.mpos.service.ServiceService;

/**
 * 服务管理
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "service")
public class ServiceController extends BaseController {
	
	@Autowired
	private ServiceService serviceService;
	
	/**
	 * 返回页面状态
	 */
	private boolean status = true;
	/**
	 * 返回消息
	 */
	private String info ="";
	
	@RequestMapping(value = "/serviceList", method = RequestMethod.GET)
	@ResponseBody
	public String serviceList(HttpServletRequest request, DataTableParamter dtp) {
		//addStoreCondition(request, dtp);
		PagingData pagingData = serviceService.loadList(dtp);
		if (pagingData.getAaData() == null) {
			Object[] objs = new Object[] {};
			pagingData.setAaData(objs);
		}
		pagingData.setSEcho(dtp.sEcho);
		return JSON.toJSONString(pagingData);
	}
	/**
	 * 创建服务
	 * @param request
	 * @param service
	 * @return
	 */
	@RequestMapping(value = "/addService", method = RequestMethod.POST)
	@ResponseBody
	public String addService(HttpServletRequest request,Tservice service){
		Map<String, Object> res = getHashMap();
		try {
			serviceService.save(service);
			info = getMessage(request,"operate.success");
		} catch (MposException e) {
			status = false;
			info = e.getMessage();
		}
		res.put("status", status);
		res.put("info", info);
		return JSON.toJSONString(res);
	}
	
	/**
	 * 修改服务
	 * @param request
	 * @param service
	 * @return
	 */
	@RequestMapping(value = "/editService", method = RequestMethod.POST)
	@ResponseBody
	public String editService(HttpServletRequest request,Tservice service){
		Map<String, Object> res = getHashMap();
		try {
			serviceService.update(service);
			info = getMessage(request,"operate.success");
		} catch (MposException e) {
			status = false;
			info = e.getMessage();
		}
		res.put("status", status);
		res.put("info", info);
		return JSON.toJSONString(res);
	}
	
	/**
	 * 删除服务
	 * @param request
	 * @param service
	 * @return
	 */
	@RequestMapping(value = "/deleteService/{serviceIds}", method = RequestMethod.POST)
	@ResponseBody
	public String deleteService(HttpServletRequest request,@PathVariable String serviceIds){
		Map<String, Object> res = getHashMap();
		Map<String, Object> params = getHashMap();
		String deleteHql = "delete from Tservice where serviceId in(:ids)";
		try {
			String[] idStr = serviceIds.split(",");
			Integer[] ids = ConvertTools.stringArr2IntArr(idStr);
			params.put("ids", ids);
			serviceService.delete(deleteHql, params);
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
