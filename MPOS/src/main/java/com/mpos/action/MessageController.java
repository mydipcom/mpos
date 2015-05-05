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
import com.mpos.service.MessageService;

@Controller
@RequestMapping(value="msg")
public class MessageController extends BaseController {
	@Autowired
	MessageService messageService;
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView msg(HttpServletRequest request){
		ModelAndView mav=new ModelAndView();					
		mav.setViewName("msg/msg");
		return mav;
	}
	
	@RequestMapping(value="msgList",method=RequestMethod.GET)
	@ResponseBody
	public String mylogsList(HttpServletRequest request,DataTableParamter dtp){	
		PagingData pagingData=messageService.loadList(dtp);
		if(pagingData.getAaData()==null){
			Object[] objs=new Object[]{};
			pagingData.setAaData(objs);
		}
		pagingData.setSEcho(dtp.sEcho);		
		String rightsListJson= JSON.toJSONString(pagingData);
		return rightsListJson;
			
		}
}
