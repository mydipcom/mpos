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
import com.mpos.model.DataTableParamter;
import com.mpos.model.PagingData;
import com.mpos.service.StoreService;

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
	/**
	 * 返回页面状态
	 */
	private boolean status = true;
	/**
	 * 返回消息
	 */
	private String info ="";
	
	@RequestMapping(value = "/storeList", method = RequestMethod.GET)
	@ResponseBody
	public String storeList(HttpServletRequest request, DataTableParamter dtp) {
		addStoreCondition(request, dtp);
		PagingData pagingData = storeService.loadList(dtp);
		if (pagingData.getAaData() == null) {
			Object[] objs = new Object[] {};
			pagingData.setAaData(objs);
		}
		pagingData.setSEcho(dtp.sEcho);
		String rightsListJson = JSON.toJSONString(pagingData);
		return rightsListJson;
	}
	/**
	 * 禁用店铺
	 * @param request
	 * @param storeIds 
	 * @return
	 */
	@RequestMapping(value = "/deleteStore/{storeIds}", method = RequestMethod.GET)
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
