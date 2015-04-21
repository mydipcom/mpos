package com.mpos.action;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.mpos.model.DataTableParamter;
import com.mpos.model.PagingData;
import com.mpos.service.ServiceOrderService;

/**
 * 服务交易日志
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value="trade")
public class ServiceOrderController extends BaseController {
	@Autowired
	private ServiceOrderService serviceOrderService;
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView trade(HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("mylog/tradelog");
		return mav;
	}
	
	@RequestMapping(value="logList",method=RequestMethod.GET)
	@ResponseBody
	public String serviceOrderList(HttpServletRequest request, DataTableParamter dtp){
		PagingData pagingData = serviceOrderService.loadList(dtp);
		if (pagingData.getAaData() == null) {
			Object[] objs = new Object[] {};
			pagingData.setAaData(objs);
		}
		pagingData.setSEcho(dtp.sEcho);
		return JSON.toJSONString(pagingData);
	}
}
