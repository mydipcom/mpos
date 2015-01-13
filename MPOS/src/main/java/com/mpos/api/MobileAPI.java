package com.mpos.api;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mpos.commons.ConvertTools;
import com.mpos.commons.MposException;
import com.mpos.commons.SystemConfig;
import com.mpos.commons.SystemConstants;
import com.mpos.dto.Tcommodity;
import com.mpos.dto.TgoodsAttribute;
import com.mpos.dto.TlocalizedField;
import com.mpos.dto.Tmenu;
import com.mpos.dto.Torder;
import com.mpos.dto.TorderItem;
import com.mpos.dto.TproductImage;
import com.mpos.dto.TproductRelease;
import com.mpos.dto.Tpromotion;
import com.mpos.model.AttributeModel;
import com.mpos.model.CallWaiterInfo;
import com.mpos.model.ProductModel;
import com.mpos.service.CategoryAttributeService;
import com.mpos.service.CommodityService;
import com.mpos.service.GoodsImageService;
import com.mpos.service.GoodsService;
import com.mpos.service.LocalizedFieldService;
import com.mpos.service.MenuService;
import com.mpos.service.OrderItemService;
import com.mpos.service.OrderService;
import com.mpos.service.ProductReleaseService;
import com.mpos.service.PromotionService;


@Controller
@RequestMapping("/api")
public class MobileAPI {
	
	/**
	 * 绑定类型为所有
	 */
	public static final int BIN_TYPE_ALL = 2;
	/**
	 * 绑定类型为分类
	 */
	public static final int BIN_TYPE_CATE = 1;
	/**
	 * 绑定类型为菜单
	 */
	public static final int BIN_TYPE_MENU = 2;
	/**
	 * 活动类型 直降
	 */
	public static final int PROMOTION_TYPE_STRAIGHT_DOWN = 1;
	/**
	 * 活动类型 折扣
	 */
	public static final int PROMOTION_TYPE_DISCOUNT = 2;
	/**
	 * 活动类型 满减
	 */
	public static final int PROMOTION_TYPE_FULL_CUT = 3;
	@Autowired
	private GoodsService goodsService;
	@Autowired
	private CommodityService commodityService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private MenuService menuService;
	@Autowired
	private GoodsImageService goodsImageService;
	@Autowired
	private CategoryAttributeService attributeService;
	@Autowired
	private OrderItemService orderItemService;
	@Autowired
	private PromotionService promotionService;
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
	public String getSetting(HttpServletResponse response,HttpServletRequest request,@RequestHeader("InitialAuthCode") String initKey) {
		JSONObject respJson = new JSONObject();
		if(initKey==null||!initKey.equalsIgnoreCase(SystemConstants.INIT_AUTH_CODE)){
			respJson.put("status", false);
			respJson.put("info", "Error Init API token.");			
			return JSON.toJSONString(respJson);
		}
		try{
			//String path = request.getContextPath();
			Map<String,String> setting=SystemConfig.Admin_Setting_Map;
			String pwd=setting.get(SystemConstants.ACCESS_PASSWORD);
			String token=setting.get(SystemConstants.TOKEN);
			String currency=setting.get(SystemConstants.CURRENCY);
			String logo=setting.get(SystemConstants.RESTAURANT_LOGO);
			String backgroundImage=setting.get(SystemConstants.PAGE_BACKGROUND);
			String restaurantName=setting.get(SystemConstants.RESTAURANT_NAME);
			
			JSONObject dataJson = new JSONObject();	
			dataJson.put("pwd", pwd);
			dataJson.put("token", token);
			dataJson.put("currency", currency);
			dataJson.put("logo", "/"+ logo);	
			dataJson.put("backgroundImage", "/" + backgroundImage);
			dataJson.put("storeName", restaurantName);
			
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
			String appid = jsonObj.getString("appId");
			if(appid==null||appid.isEmpty()){
				respJson.put("status", false);
				respJson.put("info", "The parameter appId is required.");				
				return JSON.toJSONString(respJson);				
			}
			String orderIds = jsonObj.getString("orderIds");
			if(orderIds==null||orderIds.isEmpty()||orderIds.split(",").length==0){
				respJson.put("status", false);
				respJson.put("info", "The parameter orderIds is required.");				
				return JSON.toJSONString(respJson);	
			}
			
			String[] orderIdsStr = orderIds.split(",");
			Integer[] orderIdsInt = ConvertTools.stringArr2IntArr(orderIdsStr);
			List<Map<String,Integer>> data = new ArrayList<Map<String,Integer>>();
			for (Integer orderId : orderIdsInt) {
				Torder order = orderService.getTorderById(orderId);
				if(order !=null){
					Map<String,Integer> res = new HashMap<String, Integer>();
					res.put("orderId", order.getOrderId());
					res.put("orderStatus", order.getOrderStatus());
					data.add(res);
				}
			}
			respJson.put("status", true);
			respJson.put("info", "OK");
			respJson.put("data", data);
			
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
	
	/**
	 * 
	 * @param response
	 * @param apiKey
	 * @param productId
	 * @return
	 */
	@RequestMapping(value = "getProduct/{productId}", method = RequestMethod.GET)
	@ResponseBody
	public String getProduct(HttpServletRequest request, HttpServletResponse response, @RequestHeader("Authorization") String apiKey, @PathVariable Integer productId) {
		// 获取缓存apiToken
		String apiToken = SystemConfig.Admin_Setting_Map.get(SystemConstants.CONFIG_API_TOKEN);
		JSONObject respJson = new JSONObject();
		// 判断apiToken是否一致
		if (apiKey == null || !apiKey.equalsIgnoreCase(apiToken)) {
			respJson.put("status", false);
			respJson.put("info", "Error API token.");
			return JSON.toJSONString(respJson);
		}
		// 判断请求参数
		if (productId == null || productId == 0) {
			respJson.put("status", false);
			respJson.put("info", "The request parameter productId is required");
			return JSON.toJSONString(respJson);
		}
		try {
			// 查询商品
			Tcommodity product = commodityService.getTproductByid(productId);
			if(product==null){
				respJson.put("status", false);
				respJson.put("info", "product is not exist");
				return JSON.toJSONString(respJson);
			}
			// 新建返回数据model
			ProductModel model = new ProductModel();
			model.setProductId(product.getId());
		
			if(product.isStatus()){
				model.setProductId(product.getId());
				model.setMenuId(product.getTmenu().getMenuId());
				BeanUtils.copyProperties(product, model, "attributes", "promotions", "images");
				//装载需要多语言化得字段
				model = localLoad(model, SystemConstants.TABLE_NAME_PRODUCT, SystemConstants.TABLE_FIELD_PRODUCTNAME, SystemConstants.TABLE_FIELD_SHORTDESCR, SystemConstants.TABLE_FIELD_FULLDESCR,SystemConstants.TABLE_FIELD_UNITNAME);
				//装载商品属性
				model = loadAttribute(model, product);
				//装载商品图片
				model = loadImage(model, request, product);
				/*//装载商品优惠活动列表
				List<Tpromotion> pros = loadProductPromotion(product);
				//得到通过优先级排序的可叠加优惠列表
				List<Tpromotion> isShareList = getPromotionList(pros,true);
				//得到通过优先级排序的不可叠加优惠列表
				List<Tpromotion> noShareList = getPromotionList(pros,false);
				//通过比较可叠加与不可叠加的优先级得到最终的优惠列表
				List<Tpromotion> promotions = compareToPriority(isShareList,noShareList);*/
				//通过优惠列表计算商品价格
				Float price = product.getPrice();//calculatePrice(product.getOldPrice(), promotions);
				//model = loadPromotion(model, promotions);
				if(price==null){
					price = product.getOldPrice();
				}
				model.setPrice(price);
			} else{
				model.setStatus(false);	
			} 
			respJson.put("status", true);
			respJson.put("info", "OK");
			respJson.put("data", model);
			return JSON.toJSONString(respJson);
		} catch (MposException e) {
			respJson.put("status", false);
			respJson.put("info", e.getMessage());
			return JSON.toJSONString(respJson);
		}
	}
	
	@RequestMapping(value = "putOrder", method = RequestMethod.POST)
	@ResponseBody
	public String putOrder(HttpServletResponse response, @RequestHeader("Authorization") String apiKey, @RequestBody String jsonStr){
		// 获取缓存apiToken
				String apiToken = SystemConfig.Admin_Setting_Map.get(SystemConstants.CONFIG_API_TOKEN);
				JSONObject respJson = new JSONObject();
				// 判断apiToken是否一致
				if (apiKey == null || !apiKey.equalsIgnoreCase(apiToken)) {
					respJson.put("status", false);
					respJson.put("info", "Error API token.");
					return JSON.toJSONString(respJson);
				}

				// 判断请求参数
				if (jsonStr == null || jsonStr.isEmpty()) {
					respJson.put("status", false);
					respJson.put("info", "The request parameter is required.");
					return JSON.toJSONString(respJson);
				}
				try {
					// 参加满减活动列表
					//List<Tpromotion> productPromotionList = new ArrayList<Tpromotion>();
					//
					JSONObject jsonObj = (JSONObject) JSON.parse(jsonStr);
					// 桌号
					String appId = jsonObj.getString("appId");
					JSONArray products = jsonObj.getJSONArray("products");
					float totalMoney = 0;
					float oldMoey = 0;
					Torder order = new Torder();
					order.setCreater(appId);
					order.setCreateTime(new Date().getTime());
					order.setOrderStatus(0);
					order.setOrderDiscount(0);
					order.setOrderTotal(totalMoney);
					orderService.createOrder(order);
					for (Object object : products) {
						JSONObject pro = (JSONObject) object;
						// 商品ID
						Integer productId = pro.getInteger("productId");
						// 数量
						Integer count = pro.getInteger("quantity");
						JSONArray attributes = jsonObj.getJSONArray("attributes");
						Tcommodity product = commodityService.getTproductByid(productId);
						if(product == null){
							respJson.put("status", false);
							respJson.put("info", productId+" product is not exist");
							orderService.deleteOrder(order);
							return JSON.toJSONString(respJson);
						}
						/*//装载商品优惠活动列表
						List<Tpromotion> pros = loadProductPromotion(product);
						//得到通过优先级排序的可叠加优惠列表
						List<Tpromotion> isShareList = getPromotionList(pros,true);
						//得到通过优先级排序的不可叠加优惠列表
						List<Tpromotion> noShareList = getPromotionList(pros,false);
						//通过比较可叠加与不可叠加的优先级得到最终的优惠列表
						List<Tpromotion> promotions = compareToPriority(isShareList,noShareList);
						for (Tpromotion tpromotion : promotions) {
							if(!productPromotionList.contains(tpromotion)){
								productPromotionList.add(tpromotion);
							}
						}*/
						//通过优惠列表计算商品价格
						Float price = product.getPrice();//calculatePrice(product.getOldPrice(), promotions);
						if(price==null){
							price = product.getOldPrice();
						}
						float oneMoney = 0;
						float oneOldMoney = 0;
						oneMoney = price*count;
						oneOldMoney = product.getOldPrice()*count;
						float oneDis = (product.getOldPrice()-price)*count;
						TorderItem orderItem = new TorderItem();
						orderItem.setOrderId(order.getOrderId()+"");
						orderItem.setProductId(productId);
						orderItem.setUnitPrice(product.getOldPrice());
						orderItem.setQuantity(count);
						orderItem.setCurrPrice(price);
						orderItem.setDiscount(oneDis);
						orderItem.setAttributes(JSON.toJSONString(attributes));
						orderItem.setIsGift(false);
						orderItemService.createOrderItem(orderItem);
						totalMoney += oneMoney;
						oldMoey += oneOldMoney;
					}
					/*List<Tpromotion> promotionLast = compareToPriority(getPromotionList(productPromotionList,true),getPromotionList(productPromotionList,false));
					for (Tpromotion tpromotion : promotionLast) {
						if(tpromotion.getPromotionType()==PROMOTION_TYPE_FULL_CUT){
							if(tpromotion.getParamOne()<=totalMoney){
								totalMoney = totalMoney - Float.valueOf(tpromotion.getParamTwo());
							}
						}
					}*/
					order.setOrderDiscount(oldMoey-totalMoney);
					order.setOrderTotal(totalMoney);
					orderService.update(order);
					JSONObject data = new JSONObject();
					data.put("orderId", order.getOrderId());
					data.put("orderTotal", totalMoney);
					respJson.put("status", true);
					respJson.put("info", "OK");
					respJson.put("data", data);
					return JSON.toJSONString(respJson);
				} catch (MposException e) {
					respJson.put("status", false);
					respJson.put("info", e.getMessage());
					return JSON.toJSONString(respJson);
				}
	}
	
	@RequestMapping(value = "callWaiter/{appId}/{type}", method = RequestMethod.GET)
	@ResponseBody
	public String callWaiter(HttpServletResponse response, @RequestHeader("Authorization") String apiKey, @PathVariable String appId,@PathVariable String type) {
		// 获取缓存apiToken
		String apiToken = SystemConfig.Admin_Setting_Map.get(SystemConstants.CONFIG_API_TOKEN);
		JSONObject respJson = new JSONObject();
		// 判断apiToken是否一致
		if (apiKey == null || !apiKey.equalsIgnoreCase(apiToken)) {
			respJson.put("status", false);
			respJson.put("info", "Error API token.");
			return JSON.toJSONString(respJson);
		}
		// 判断请求参数
		if (appId == null || appId.isEmpty()) {
			respJson.put("status", false);
			respJson.put("info", "The request parameter appId is required");
			return JSON.toJSONString(respJson);
		}
		if (type == null || type.isEmpty()) {
			respJson.put("status", false);
			respJson.put("info", "The request parameter type is required");
			return JSON.toJSONString(respJson);
		}
		System.out.println(appId+"--------------"+type);
		Date nowTime = new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		String timeString = sdf.format(nowTime);
		Map<String, Object> data = new HashMap<String, Object>();
		String msg = "OK";
		Integer status = 0;
		if (SystemConfig.Call_Waiter_Map.get(appId)!=null&&SystemConfig.Call_Waiter_Map.get(appId).getStatus()==1) {
			CallWaiterInfo info = SystemConfig.Call_Waiter_Map.get(appId);
			info.setCallTime(timeString);
			SystemConfig.Call_Waiter_Map.put(appId, info);
		} else if(SystemConfig.Call_Waiter_Map.get(appId)!=null&&SystemConfig.Call_Waiter_Map.get(appId).getStatus()==0){
			CallWaiterInfo info = SystemConfig.Call_Waiter_Map.get(appId);
			info.setStatus(1);
			info.setType(Integer.valueOf(type));
			info.setCallTime(timeString);
			SystemConfig.Call_Waiter_Map.put(appId, info);
		}else{
			CallWaiterInfo info = new CallWaiterInfo();
			info.setCallMan(appId);
			info.setCallTime(timeString);
			info.setStatus(1);
			info.setType(Integer.valueOf(type));
			SystemConfig.Call_Waiter_Map.put(appId, info);
			status = 1;
		}
		data.put("type", status);
		respJson.put("data", data);
		respJson.put("info", msg);
		respJson.put("status", true);
		return JSON.toJSONString(respJson);
	}
	
	/**
	 * 装载优惠信息 将商品参加的所有优惠活动装载到model中
	 * 
	 * @param model
	 * @param product
	 * @return
	 */
	private ProductModel loadPromotion(ProductModel model, List<Tpromotion> promotions) {
		String[] promtionList = null;
		if (promotions != null && promotions.size() > 0) {
			promtionList = new String[promotions.size()];
			for (int i = 0; i < promtionList.length; i++) {
				promtionList[i] = promotions.get(i).getPromotionName();
			}
		}    
		model.setPromotions(promtionList);
		return model;
	}

	/**
	 * 装载图片地址
	 * 
	 * @param model
	 * @param request
	 * @param product
	 * @return
	 */
	private ProductModel loadImage(ProductModel model, HttpServletRequest request, Tcommodity product) {
		List<TproductImage> images = new ArrayList<TproductImage>();
		String[] imageList = null;
		images.addAll(product.getImages());
		Collections.sort(images, new Comparator<TproductImage>() {
			public int compare(TproductImage arg0, TproductImage arg1) {
				return arg0.getId().compareTo(arg1.getId());
			}
		});
		if (images != null && images.size() > 0) {
			String qian = request.getSession().getServletContext().getRealPath("/") + File.separator + "upload" + File.separator + "product"+ File.separator;
			String z =  "/upload/product/";
			File file = null;
			for (int i = 0; i < images.size(); i++) {
				TproductImage tproductImage = images.get(i);
				String filePath = tproductImage.getImageUrl();
				file = new File(qian + filePath.substring(filePath.lastIndexOf("/")));
				if (!file.exists()) {
					String newPath = qian + product.getId()+"_"+ i + "." + tproductImage.getImageSuffix();
					File newImgePath = new File(newPath);
					try {
						File uplaodDir = new File(qian);
						if (!uplaodDir.isDirectory()) {
							uplaodDir.mkdirs();
						}
						if (tproductImage.getImage() != null && !newImgePath.exists()) {
							ImageOutputStream ios = ImageIO.createImageOutputStream(newImgePath);
							ios.write(tproductImage.getImage());
							String loc = z + product.getId()+"_"+ i + "." + tproductImage.getImageSuffix();
							tproductImage.setImageUrl(loc);
							if(!filePath.equals(loc)){
								goodsImageService.updeteImages(tproductImage);
							}
						}  
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			/*for (TproductImage tproductImage : images) {
				String filePath = tproductImage.getImageUrl();
				file = new File(qian + filePath.substring(filePath.lastIndexOf("/")));
				if (!file.exists()) {
					String newPath = qian + tproductImage.getId() + "." + tproductImage.getImageSuffix();
					File newImgePath = new File(newPath);
					try {
						File uplaodDir = new File(qian);
						if (!uplaodDir.isDirectory()) {
							uplaodDir.mkdirs();
						}
						if (tproductImage.getImage() != null && !newImgePath.exists()) {
							ImageOutputStream ios = ImageIO.createImageOutputStream(newImgePath);
							ios.write(tproductImage.getImage());
							tproductImage.setImageUrl(z + tproductImage.getId() + "." + tproductImage.getImageSuffix());
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}*/

			imageList = new String[images.size()];
			for (int i = 0; i < images.size(); i++) {
				imageList[i] = images.get(i).getImageUrl();
			}
		}
		model.setImages(imageList);
		return model;
	}

	/**
	 * 装载属性
	 * 
	 * @param model
	 * @param product
	 * @return
	 */
	private ProductModel loadAttribute(ProductModel model, Tcommodity product) {
		List<AttributeModel> attributeModels = new ArrayList<AttributeModel>();
		List<TgoodsAttribute> attributes = new ArrayList<TgoodsAttribute>();
		attributes.addAll(product.getAttributes());
		if (attributes != null && attributes.size() > 0) {
			for (TgoodsAttribute tproductAttribute : attributes) {
				AttributeModel attribute = new AttributeModel();
				List<TlocalizedField> attributeValueList = localizedFieldService.getLocalizedField(tproductAttribute.getId(), SystemConstants.TABLE_NAME_PRODUCT_ATTRIBUTE, SystemConstants.TABLE_FIELD_ATTRIBUTE_VALUE);
				List<TlocalizedField> attributeTitleList = localizedFieldService.getLocalizedField(tproductAttribute.getId(), SystemConstants.TABLE_NAME_CATE_ATTRIBUTE, SystemConstants.TABLE_FIELD_TITLE);
				attribute.setAttributeId(tproductAttribute.getId());
				attribute.setAttributePrice(tproductAttribute.getAttributePrice());
				attribute.setAttributeValue(tproductAttribute.getAttributeValue());
				attribute.setAttributeValueLocale(setLocal(attributeValueList));
				attribute.setAttributeTitle(attributeService.getCategoryAttribute(tproductAttribute.getId()).getTitle());
				attribute.setAttributeTitleLocale(setLocal(attributeTitleList));
				attributeModels.add(attribute);
			}
		}
		model.setAttributes(attributeModels);
		return model;
	}

	/**
	 * 多语言加载
	 * 
	 * @param model
	 * @param tableName
	 * @param fields
	 * @return
	 */
	private ProductModel localLoad(ProductModel model, String tableName, String... fields) {
		if (fields != null && fields.length > 0) {
			for (String field : fields) {
				List<TlocalizedField> nameList = localizedFieldService.getLocalizedField(model.getProductId(), tableName, field);
				if (field.equals("productName")) {
					model.setProductNameLocale(setLocal(nameList));
				} else if (field.equals("shortDescr")) {
					model.setShortDescrLocale(setLocal(nameList));
				} else if (field.equals("fullDescr")) {
					model.setFullDescrLocale(setLocal(nameList));
				} else if (field.equals("unitName")) {
					model.setUnitNameLocale(setLocal(nameList));
				}
			}
		}
		return model;
	}

	private JSONArray setLocal(List<TlocalizedField> list) {
		JSONArray json = null;
		if (list != null && list.size() > 0) {
			json = new JSONArray();
			for (TlocalizedField localizedField : list) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("language", localizedField.getLanguage().getLocal());
				jsonObj.put("value", localizedField.getLocaleValue());
				json.add(jsonObj);
			}
		}
		return json;
	}

	/**
	 * 加载商品优惠列表
	 * 
	 * @param product
	 * @return
	 */
	private List<Tpromotion> loadProductPromotion(Tcommodity product) {
		List<Tpromotion> promotionList = new ArrayList<Tpromotion>();
		//
		Set<Tpromotion> binProduct = product.getPromotions();
		long now = new Date().getTime();
		if (binProduct != null && binProduct.size() > 0) {
			for (Tpromotion promotion : binProduct) {
				if (promotion.getStartTime() <= now && now <= promotion.getEndTime() && promotion.isStatus()) {
					if (!promotionList.contains(promotion)) {
						promotionList.add(promotion);
					}
				}
			}
		}

		String product_cate_id = product.getTcategory().getCategoryId() + "";
		// 查询绑定分类的优惠活动
		List<Tpromotion> bindType = promotionService.selectPromotion(BIN_TYPE_CATE);
		if (bindType != null && bindType.size() > 0) {
			for (Tpromotion promotion : bindType) {
				if (isExist(product_cate_id, promotion.getBindId().split(","))) {
					if (!promotionList.contains(promotion)) {
						promotionList.add(promotion);
					}
				}
			}
		}

		String product_menu_id = product.getTmenu().getMenuId() + "";
		// 查询绑定菜单的优惠活动
		List<Tpromotion> bindMenu = promotionService.selectPromotion(BIN_TYPE_MENU);
		if (bindMenu != null && bindMenu.size() > 0) {
			for (Tpromotion promotion : bindMenu) {
				if (isExist(product_menu_id, promotion.getBindId().split(","))) {
					if (!promotionList.contains(promotion)) {
						promotionList.add(promotion);
					}
				}
			}
		}
		// 查询绑定所有的优惠活动
		List<Tpromotion> bindAll = promotionService.selectPromotion(BIN_TYPE_ALL);
		for (Tpromotion promotion : bindAll) {
			if (!promotionList.contains(promotion)) {
				promotionList.add(promotion);
			}
		}

		return promotionList;
	}

	/**
	 * 判断id是否在数组ids内
	 * 
	 * @param id
	 * @param ids
	 * @return
	 */
	private boolean isExist(String id, String[] ids) {
		if (id != null && !id.isEmpty()) {
			if (ids != null && ids.length > 0) {
				for (String idStr : ids) {
					if (idStr != null && !idStr.isEmpty() && id.equals(idStr)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * 根据优惠是否可以叠加获取通过优先级排序的活动列表
	 * 
	 * @param pros
	 *             优惠列表
	 * @param isShare
	 *             true 可叠加 false 不可叠加
	 * @return
	 */
	private List<Tpromotion> getPromotionList(List<Tpromotion> pros, boolean isShare) {
		List<Tpromotion> promotions = new ArrayList<Tpromotion>();
		if (pros != null && pros.size() > 0) {
			for (Tpromotion tpromotion : pros) {
				if (isShare) {
					if (tpromotion.isShared()) {
						promotions.add(tpromotion);
					}
				} else {
					if (!tpromotion.isShared()) {
						promotions.add(tpromotion);
					}
				}

			}
		}
		Collections.sort(promotions, new Comparator<Tpromotion>() {
			public int compare(Tpromotion arg0, Tpromotion arg1) {
				return arg0.getPriority().compareTo(arg1.getPriority());
			}
		});
		return promotions;
	}
	/**
	 * 比较叠加和不可叠加的优先级 
	 * @param isShareList
	 * @param unShareList
	 * @return
	 */
	private List<Tpromotion> compareToPriority(List<Tpromotion> isShareList,List<Tpromotion> unShareList){
		if((isShareList==null||isShareList.size()==0)&&(unShareList!=null&&unShareList.size()>0)){
			Tpromotion un = unShareList.get(0);
			unShareList.clear();
			unShareList.add(un);
			return unShareList;
		}
		if(unShareList==null||unShareList.size()==0&&(isShareList!=null&&isShareList.size()>0)){
			return isShareList;
		}
		if((isShareList!=null&&isShareList.size()>0)&&(unShareList!=null&&unShareList.size()>0)){
			Tpromotion un = unShareList.get(0);
			Tpromotion is = isShareList.get(0);
			if(un.getPriority()<=is.getPriority()){
				unShareList.clear();
				unShareList.add(un);
				return unShareList;
			}else{
				return isShareList;
			}
		}
		return new ArrayList<Tpromotion>();
	}
	/**
	 * 计算商品参加活动后的单价
	 * @param oldPrice 原价
	 * @param promotions 活动列表
	 * @return
	 */
	private float calculatePrice(float oldPrice,List<Tpromotion> promotions){
		if(promotions!=null&&promotions.size()>0){
			for (Tpromotion promotion : promotions) {
				if(promotion.getPromotionType() == PROMOTION_TYPE_STRAIGHT_DOWN){
					oldPrice = oldPrice - promotion.getParamOne();
				}else if(promotion.getPromotionType() == PROMOTION_TYPE_DISCOUNT){
					oldPrice = oldPrice * (Float.valueOf(promotion.getParamOne()+"")/100);
				}
			}
		}
		return oldPrice;
	}
}
