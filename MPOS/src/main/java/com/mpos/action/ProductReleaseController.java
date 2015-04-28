package com.mpos.action;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.mpos.commons.BaiduPushTool;
import com.mpos.commons.BaiduPushTool.Notification;
import com.mpos.commons.MposException;
import com.mpos.dto.TproductRelease;
import com.mpos.service.ProductReleaseService;

@Controller
public class ProductReleaseController extends BaseController{
 
	@Autowired
	private ProductReleaseService productReleaseService;
	
	
	@RequestMapping(value="goods/publicrelease",method=RequestMethod.POST)
	@ResponseBody
	public String  Publicrelease(HttpServletRequest request){
		JSONObject respJson = new JSONObject();
	//	Integer id=Integer.parseInt(ids);
		try {
			TproductRelease productrelease=productReleaseService.getUnPublished(getSessionUser(request).getStoreId());
			productReleaseService.publicreleasebyid(productrelease.getId());
			respJson.put("status", true);
			respJson.put("info", getMessage(request,"operate.success"));
			Notification notification = new Notification("new data version",productrelease.getId()+"");
			BaiduPushTool.pushMsgToTag(notification, getSessionUser(request).getStoreId()+"", BaiduPushTool.IOS_TYPE);
		} catch (MposException be){
			respJson.put("status", false);
			respJson.put("info", getMessage(request,be.getErrorID(),be.getMessage()));
		}
		return respJson.toJSONString();
	}
	
}
