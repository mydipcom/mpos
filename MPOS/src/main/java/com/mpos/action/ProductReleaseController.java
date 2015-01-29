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
import com.mpos.commons.MposException;
import com.mpos.dto.TproductRelease;
import com.mpos.model.DataTableParamter;
import com.mpos.model.PagingData;
import com.mpos.service.ProductReleaseService;

@Controller
@RequestMapping("/productrelease")
public class ProductReleaseController extends BaseController{
 
	@Autowired
	private ProductReleaseService productReleaseService;
	
	
	@RequestMapping(value="/publicrelease",method=RequestMethod.POST)
	@ResponseBody
	public String  Publicrelease(HttpServletRequest request){
		JSONObject respJson = new JSONObject();
	//	Integer id=Integer.parseInt(ids);
		try {
			TproductRelease productrelease=productReleaseService.getUnPublished();
			productReleaseService.publicreleasebyid(productrelease.getId());
			respJson.put("status", true);
			respJson.put("info", getMessage(request,"operate.success"));
		} catch (MposException be){
			respJson.put("status", false);
			respJson.put("info", getMessage(request,be.getErrorID(),be.getMessage()));
		}
		return respJson.toJSONString();
	}
	
}
