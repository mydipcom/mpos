package com.mpos.action;

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
import com.mpos.dto.Ttable;
import com.mpos.model.DataTableParamter;
import com.mpos.model.PagingData;
import com.mpos.service.TableService;

@Controller
@RequestMapping("/table")
public class TableController extends BaseController {
	@Autowired
	TableService tableService;
	
	private Boolean ok = true;
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView table(){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("table/table");
		return mav;
	}
	
	@RequestMapping(value="/tableList",method=RequestMethod.GET)
	@ResponseBody
	public String tableList(DataTableParamter dtp,HttpServletRequest request){
		addStoreCondition(request, dtp);
		PagingData pagingData=tableService.loadTableList(dtp);
		if(pagingData.getAaData()==null){
			Object[] objs=new Object[]{};
			pagingData.setAaData(objs);
		}
		pagingData.setSEcho(dtp.sEcho);
		return JSON.toJSONString(pagingData);
	}
	
	@RequestMapping(value="/addTable",method=RequestMethod.POST)
	@ResponseBody
	public String addTable(HttpServletRequest request,Ttable table){
		JSONObject res = new JSONObject();
		try {
			addStore(table,request);
			table.setCreateTime(System.currentTimeMillis());
			tableService.create(table);
			res.put("info", getMessage(request,"operate.success"));
		} catch (MposException be) {
			ok = false;
			res.put("info", getMessage(request,be.getErrorID(),be.getMessage()));
		}
		res.put("status", ok);
		return JSON.toJSONString(res);
	}
	
	@RequestMapping(value="/{ids}",method=RequestMethod.DELETE)
	@ResponseBody
	public String deleteTable(HttpServletRequest request,@PathVariable String ids){
		JSONObject res = new JSONObject();
		try {
			String[] idstrArr=ids.split(",");		
			Integer[] idArr=ConvertTools.stringArr2IntArr(idstrArr);
			tableService.deleteAll(idArr);
			res.put("info", getMessage(request,"operate.success"));
		} catch (MposException be) {
			ok = false;
			res.put("info", getMessage(request,be.getErrorID(),be.getMessage()));
		}
		res.put("status", ok);
		return JSON.toJSONString(res);
	}
	
	@RequestMapping(value="/editTable",method=RequestMethod.POST)
	@ResponseBody
	public String editTable(HttpServletRequest request,Ttable table){
		JSONObject res = new JSONObject();
		try {
			addStore(table,request);
			tableService.update(table);
			res.put("info", getMessage(request,"operate.success"));
		} catch (MposException be) {
			ok = false;
			res.put("info", getMessage(request,be.getErrorID(),be.getMessage()));
		}
		res.put("status", ok);
		return JSON.toJSONString(res);
	}
	/**
	 * 添加验证tableName
	 * @param tableName 桌号
	 * @return
	 */
	@RequestMapping(value="/checkTableName",method=RequestMethod.POST)
	@ResponseBody
	public String checkTableName(String tableName,HttpServletRequest request){
		Integer storeId = getSessionUser(request).getStoreId();
		return JSON.toJSONString(!tableService.tableNameIsExist(tableName,storeId));
	}
	/**
	 * 修改验证tableName
	 * @param tableName 桌号
	 * @return
	 */
	@RequestMapping(value="/verification",method=RequestMethod.POST)
	@ResponseBody
	public String updateVerification(String tableName,HttpServletRequest request){
		Integer storeId = getSessionUser(request).getStoreId();
		return JSON.toJSONString(tableService.updateVerification(tableName,storeId));
	}
	
}
