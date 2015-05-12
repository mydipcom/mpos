package com.mpos.action;

import java.util.List;

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
import com.mpos.commons.LogManageTools;
import com.mpos.commons.MposException;
import com.mpos.dto.Tstore;
import com.mpos.dto.Ttable;
import com.mpos.model.DataTableParamter;
import com.mpos.model.PagingData;
import com.mpos.service.StoreService;
import com.mpos.service.TableService;

@Controller
@RequestMapping("/table")
public class TableController extends BaseController {
	@Autowired
	private TableService tableService;
	@Autowired
	private StoreService storeService;
	
	private Boolean ok = true;
	/**
	 * 操作内容
	 */
	private String handleContent = "";
	/**
	 * 日志级别
	 */
	private short level = LogManageTools.NOR_LEVEL;
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView table(HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		String hql = "select new Tstore(storeId,storeName) from Tstore";
		List<Tstore> stores = storeService.select(hql, null);
		mav.addObject("stores",stores);
		mav.addObject("role", getSessionUser(request).getAdminRole().getRoleId());
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
			addStore(table,request,table.getStoreId());
			table.setCreateTime(System.currentTimeMillis());
			tableService.create(table);
			handleContent = "添加桌号:"+table.getTableName()+"成功;新增ID为:"+table.getId();
			res.put("info", getMessage(request,"operate.success"));
		} catch (MposException be) {
			ok = false;
			res.put("info", getMessage(request,be.getErrorID(),be.getMessage()));
			handleContent = "添加桌号:"+table.getTableName()+"失败;";
			level = LogManageTools.FAIL_LEVEL;
		}		
		LogManageTools.writeAdminLog(handleContent,level, request);
		res.put("status", ok);
		return JSON.toJSONString(res);
	}
	
	@RequestMapping(value="/{ids}",method=RequestMethod.GET)
	@ResponseBody
	public String deleteTable(HttpServletRequest request,@PathVariable String ids){
		JSONObject res = new JSONObject();
		Integer[] idArr=null;
		try {
			String[] idstrArr=ids.split(",");		
			idArr=ConvertTools.stringArr2IntArr(idstrArr);
			tableService.deleteAll(idArr,getSessionStoreId(request));
			handleContent = "删除桌号:"+idArr.toString()+"成功;";
			res.put("info", getMessage(request,"operate.success"));
		} catch (MposException be) {
			ok = false;
			res.put("info", getMessage(request,be.getErrorID(),be.getMessage()));
			handleContent = "删除桌号:"+idArr.toString()+"失败;";
			level = LogManageTools.FAIL_LEVEL;
		}		
		LogManageTools.writeAdminLog(handleContent,level, request);
		res.put("status", ok);
		return JSON.toJSONString(res);
	}
	
	@RequestMapping(value="/editTable",method=RequestMethod.POST)
	@ResponseBody
	public String editTable(HttpServletRequest request,Ttable table){
		JSONObject res = new JSONObject();
		try {
			tableService.update(table);
			handleContent = "修改桌号:"+table.getTableName()+"成功";
			res.put("info", getMessage(request,"operate.success"));
		} catch (MposException be) {
			ok = false;
			res.put("info", getMessage(request,be.getErrorID(),be.getMessage()));
			handleContent = "修改桌号:"+table.getTableName()+"失败";
			level = LogManageTools.FAIL_LEVEL;
		}		
		LogManageTools.writeAdminLog(handleContent,level, request);
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
