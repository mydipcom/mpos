package com.mpos.action;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
@Controller
@RequestMapping("/index")
public class IndexContronller extends BaseController {
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView home(HttpServletRequest request){
		ModelAndView mav=new ModelAndView();		
		mav.setViewName("index");
		return mav;
	}
}
