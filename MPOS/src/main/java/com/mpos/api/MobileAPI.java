package com.mpos.api;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mpos.commons.MposException;
import com.mpos.commons.SystemConfig;
import com.mpos.commons.SystemConstants;
import com.mpos.dto.TlocalizedField;
import com.mpos.dto.Tmenu;
import com.mpos.dto.Torder;
import com.mpos.dto.TproductRelease;
import com.mpos.service.GoodsService;
import com.mpos.service.LocalizedFieldService;
import com.mpos.service.MenuService;
import com.mpos.service.OrderService;
import com.mpos.service.ProductReleaseService;


@Controller
@RequestMapping("/api")
public class MobileAPI {
	
	@Autowired
	private GoodsService goodsService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private MenuService menuService;
	@Autowired
	private LocalizedFieldService localizedFieldService;	
	@Autowired
	private ProductReleaseService productReleaseService;
	
	/**
	 * Get the system setting parameter
	 * @param response
	 * @return String
	 */
	@RequestMapping(value="getSetting",method=RequestMethod.GET)
	@ResponseBody
	public String getSetting(HttpServletResponse response) {		
				
		JSONObject respJson = new JSONObject();												
		try{
			Map<String,String> setting=SystemConfig.Admin_Setting_Map;
			String pwd=setting.get(SystemConstants.CONFIG_CLIENT_PWD);
			String token=setting.get(SystemConstants.CONFIG_API_TOKEN);
			String currency=setting.get(SystemConstants.CONFIG_DISPLAY_CURRENCY);
			String logo=setting.get(SystemConstants.CONFIG_CLIENT_LOGO);
			
			JSONObject dataJson = new JSONObject();	
			dataJson.put("pwd", pwd);
			dataJson.put("token", token);
			dataJson.put("currency", currency);
			dataJson.put("logo", logo);			
			
			respJson.put("status", true);
			respJson.put("info", "OK");
			respJson.put("data", dataJson);
			
			return JSON.toJSONString(respJson);
		}
		catch(MposException e){
			respJson.put("status", false);
			respJson.put("info", e.getMessage());			
			return JSON.toJSONString(respJson);
		}		
	}
	
	/**
	 * Query order status by heartbeat
	 * @param response
	 * @param apiKey
	 * @param jsonStr
	 * @return String
	 */
	@RequestMapping(value="orderCheck",method=RequestMethod.POST)
	@ResponseBody
	public String orderCheck(HttpServletResponse response,@RequestHeader("Authorization") String apiKey,@RequestBody String jsonStr) {		
		String apiToken=SystemConfig.Admin_Setting_Map.get(SystemConstants.CONFIG_API_TOKEN);
		JSONObject jsonObj=null;
		JSONObject respJson = new JSONObject();						
		if(apiKey==null||!apiKey.equalsIgnoreCase(apiToken)){
			respJson.put("status", false);
			respJson.put("info", "Error API token.");			
			return JSON.toJSONString(respJson);
		}
		
		if(jsonStr == null||jsonStr.isEmpty()) {
			respJson.put("status", false);
			respJson.put("info", "The request parameter is required.");			
			return JSON.toJSONString(respJson);
		}
				
		try{
			jsonObj= (JSONObject)JSON.parse(jsonStr);
			int appid=jsonObj.getIntValue("appId");
			int orderid=jsonObj.getIntValue("orderId");
			if(appid==0||orderid==0){
				respJson.put("status", false);
				respJson.put("info", "The parameter appId and orderId is required.");				
				return JSON.toJSONString(respJson);				
			}
			Torder order=orderService.getTorderById(orderid);
			
			JSONObject dataJson = new JSONObject();	
			dataJson.put("orderStatus", order.getOrderStatus());
			respJson.put("status", true);
			respJson.put("info", "OK");
			respJson.put("data", dataJson);
			
			return JSON.toJSONString(respJson);
		}
		catch(MposException e){
			respJson.put("status", false);
			respJson.put("info", e.getMessage());			
			return JSON.toJSONString(respJson);
		}		
	}
	
	/**
	 * Get the menu list
	 * @param response
	 * @param apiKey
	 * @return String
	 */
	@RequestMapping(value="getMenu",method=RequestMethod.GET)
	@ResponseBody
	public String getMenu(HttpServletResponse response,@RequestHeader("Authorization") String apiKey) {		
				
		JSONObject respJson = new JSONObject();	
		String apiToken=SystemConfig.Admin_Setting_Map.get(SystemConstants.CONFIG_API_TOKEN);
		if(apiKey==null||!apiKey.equalsIgnoreCase(apiToken)){
			respJson.put("status", false);
			respJson.put("info", "Error API token.");			
			return JSON.toJSONString(respJson);
		}
								
		try{
			List<Tmenu> menuList=menuService.getAllMenu();
			
			for (Tmenu menu : menuList) {
				JSONArray langJsonArr = null;
				List<TlocalizedField> list=localizedFieldService.getLocalizedField(menu.getMenuId(), SystemConstants.TABLE_NAME_MENU, SystemConstants.TABLE_FIELD_TITLE);
				if(list!=null&&list.size()>0){
					langJsonArr = new JSONArray();
					for (TlocalizedField localizedField : list) {
						JSONObject jsonObj = new JSONObject();
						jsonObj.put("language", localizedField.getLanguage().getLocal());
						jsonObj.put("value", localizedField.getLocaleValue());
						langJsonArr.add(jsonObj);
					}															
				}
				menu.setTitleLocale(langJsonArr);
			}						
			respJson.put("status", true);
			respJson.put("info", "OK");
			respJson.put("data", menuList);
			
			return JSON.toJSONString(respJson);
		}
		catch(MposException e){
			respJson.put("status", false);
			respJson.put("info", e.getMessage());			
			return JSON.toJSONString(respJson);
		}		
	}
	
	/**
	 * Query the latest products change version
	 * @param response
	 * @param apiKey
	 * @param jsonStr
	 * @return
	 */
	@RequestMapping(value="getProductsVer",method=RequestMethod.POST)
	@ResponseBody
	public String getProductsVer(HttpServletResponse response,@RequestHeader("Authorization") String apiKey,@RequestBody String jsonStr) {		
		String apiToken=SystemConfig.Admin_Setting_Map.get(SystemConstants.CONFIG_API_TOKEN);		
		JSONObject respJson = new JSONObject();						
		if(apiKey==null||!apiKey.equalsIgnoreCase(apiToken)){
			respJson.put("status", false);
			respJson.put("info", "Error API token.");			
			return JSON.toJSONString(respJson);
		}
		
		if(jsonStr == null||jsonStr.isEmpty()) {
			respJson.put("status", false);
			respJson.put("info", "The request parameter is required.");			
			return JSON.toJSONString(respJson);
		}
				
		try{
			
			JSONObject jsonObj= (JSONObject)JSON.parse(jsonStr);
			int verid=jsonObj.getIntValue("verId");						
			int latestVerID=0;
			List<TproductRelease> productReleaseList=productReleaseService.getUpdatedRelease(verid);
			JSONArray productsJsonArr = new JSONArray();
			for (TproductRelease productRelease : productReleaseList) {
				if(productRelease.getId()>latestVerID){
					latestVerID=productRelease.getId();
				}
				String productStr=productRelease.getProducts();
				String[] productArr=productStr.split(",");
				for (String str : productArr) {
					int productId=Integer.parseInt(str);
					if(!productsJsonArr.contains(productId)){
						productsJsonArr.add(productId);
					}
				}												
			}
			JSONObject dataJson = new JSONObject();
			dataJson.put("id", latestVerID);
			dataJson.put("products", productsJsonArr);
			respJson.put("status", true);
			respJson.put("info", "OK");
			respJson.put("data", dataJson);
			
			return JSON.toJSONString(respJson);
		}
		catch(MposException e){
			respJson.put("status", false);
			respJson.put("info", e.getMessage());			
			return JSON.toJSONString(respJson);
		}		
	}
}
