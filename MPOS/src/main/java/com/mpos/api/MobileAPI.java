package com.mpos.api;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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
import com.mpos.commons.MposException;
import com.mpos.commons.SystemConfig;
import com.mpos.commons.SystemConstants;
import com.mpos.dto.TgoodsAttribute;
import com.mpos.dto.TlocalizedField;
import com.mpos.dto.Tmenu;
import com.mpos.dto.Torder;
import com.mpos.dto.TorderItem;
import com.mpos.dto.Tproduct;
import com.mpos.dto.TproductImage;
import com.mpos.dto.TproductRelease;
import com.mpos.dto.Tpromotion;
import com.mpos.model.AttributeModel;
import com.mpos.model.ProductModel;
import com.mpos.service.CategoryAttributeService;
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
	 * ������Ϊ����
	 */
	public static final int BIN_TYPE_ALL = 2;
	/**
	 * ������Ϊ����
	 */
	public static final int BIN_TYPE_CATE = 1;
	/**
	 * ������Ϊ�˵�
	 */
	public static final int BIN_TYPE_MENU = 2;
	/**
	 * ����� ֱ��
	 */
	public static final int PROMOTION_TYPE_STRAIGHT_DOWN = 1;
	/**
	 * ����� �ۿ�
	 */
	public static final int PROMOTION_TYPE_DISCOUNT = 2;
	/**
	 * ����� ����
	 */
	public static final int PROMOTION_TYPE_FULL_CUT = 3;
	@Autowired
	private GoodsService goodsService;
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
		// ��ȡ����apiToken
		String apiToken = SystemConfig.Admin_Setting_Map.get(SystemConstants.CONFIG_API_TOKEN);
		JSONObject respJson = new JSONObject();
		// �ж�apiToken�Ƿ�һ��
		if (apiKey == null || !apiKey.equalsIgnoreCase(apiToken)) {
			respJson.put("status", false);
			respJson.put("info", "Error API token.");
			return JSON.toJSONString(respJson);
		}
		// �ж��������
		if (productId == null || productId == 0) {
			respJson.put("status", false);
			respJson.put("info", "The request parameter productId is required");
			return JSON.toJSONString(respJson);
		}
		try {
			// ��ѯ��Ʒ
			Tproduct product = goodsService.getTproductByid(productId);
			// �½���������model
			ProductModel model = new ProductModel();
			model.setProductId(product.getId());
			model.setMenuId(product.getTmenu().getMenuId());
			BeanUtils.copyProperties(product, model, "attributes", "promotions", "images");
			//װ����Ҫ�����Ի����ֶ�
			model = localLoad(model, SystemConstants.TABLE_NAME_PRODUCT, SystemConstants.TABLE_FIELD_PRODUCTNAME, SystemConstants.TABLE_FIELD_SHORTDESCR, SystemConstants.TABLE_FIELD_FULLDESCR);
			//װ����Ʒ����
			model = loadAttribute(model, product);
			//װ����ƷͼƬ
			model = loadImage(model, request, product);
			//װ����Ʒ�Żݻ�б�
			List<Tpromotion> pros = loadProductPromotion(product);
			//�õ�ͨ�����ȼ�����Ŀɵ����Ż��б�
			List<Tpromotion> isShareList = getPromotionList(pros,true);
			//�õ�ͨ�����ȼ�����Ĳ��ɵ����Ż��б�
			List<Tpromotion> noShareList = getPromotionList(pros,false);
			//ͨ���ȽϿɵ����벻�ɵ��ӵ����ȼ��õ����յ��Ż��б�
			List<Tpromotion> promotions = compareToPriority(isShareList,noShareList);
			//ͨ���Ż��б������Ʒ�۸�
			float price = calculatePrice(product.getOldPrice(), promotions);
			model = loadPromotion(model, promotions);
			model.setPrice(price);
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
		// ��ȡ����apiToken
				String apiToken = SystemConfig.Admin_Setting_Map.get(SystemConstants.CONFIG_API_TOKEN);
				JSONObject respJson = new JSONObject();
				// �ж�apiToken�Ƿ�һ��
				if (apiKey == null || !apiKey.equalsIgnoreCase(apiToken)) {
					respJson.put("status", false);
					respJson.put("info", "Error API token.");
					return JSON.toJSONString(respJson);
				}

				// �ж��������
				if (jsonStr == null || jsonStr.isEmpty()) {
					respJson.put("status", false);
					respJson.put("info", "The request parameter is required.");
					return JSON.toJSONString(respJson);
				}
				try {
					// �μ�������б�
					List<Tpromotion> productPromotionList = new ArrayList<Tpromotion>();
					//
					JSONObject jsonObj = (JSONObject) JSON.parse(jsonStr);
					// ����
					String appId = jsonObj.getString("appId");
					JSONArray products = jsonObj.getJSONArray("products");
					float totalMoney = 0;
					float oneMoney = 0;
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
						// ��ƷID
						Integer productId = pro.getInteger("productId");
						// ����
						Integer count = pro.getInteger("quantity");
						Tproduct product = goodsService.getTproductByid(productId);
						//װ����Ʒ�Żݻ�б�
						List<Tpromotion> pros = loadProductPromotion(product);
						//�õ�ͨ�����ȼ�����Ŀɵ����Ż��б�
						List<Tpromotion> isShareList = getPromotionList(pros,true);
						//�õ�ͨ�����ȼ�����Ĳ��ɵ����Ż��б�
						List<Tpromotion> noShareList = getPromotionList(pros,false);
						//ͨ���ȽϿɵ����벻�ɵ��ӵ����ȼ��õ����յ��Ż��б�
						List<Tpromotion> promotions = compareToPriority(isShareList,noShareList);
						for (Tpromotion tpromotion : promotions) {
							if(!productPromotionList.contains(tpromotion)){
								productPromotionList.add(tpromotion);
							}
						}
						//ͨ���Ż��б������Ʒ�۸�
						float price = calculatePrice(product.getOldPrice(), promotions);
						oneMoney = price*count;
						float oneDis = (product.getOldPrice()-price)*count;
						TorderItem orderItem = new TorderItem();
						orderItem.setOrderId(order.getOrderId()+"");
						orderItem.setProductId(productId);
						orderItem.setUnitPrice(product.getOldPrice());
						orderItem.setQuantity(count);
						orderItem.setCurrPrice(price);
						orderItem.setDiscount(oneDis);
						orderItem.setAttributes(JSON.toJSONString(product.getAttributes()));
						orderItem.setIsGift(false);
						orderItemService.createOrderItem(orderItem);
						totalMoney += oneMoney;
						oldMoey += product.getOldPrice()*count;
					}
					List<Tpromotion> promotionLast = compareToPriority(getPromotionList(productPromotionList,true),getPromotionList(productPromotionList,false));
					for (Tpromotion tpromotion : promotionLast) {
						if(tpromotion.getPromotionType()==PROMOTION_TYPE_FULL_CUT){
							if(tpromotion.getParamOne()<=totalMoney){
								totalMoney = totalMoney - Float.valueOf(tpromotion.getParamTwo());
							}
						}
					}
					order.setOrderDiscount(oldMoey-totalMoney);
					order.setOrderTotal(totalMoney);
					JSONObject data = new JSONObject();
					data.put("orderId ", 1);
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
	
	@RequestMapping(value = "callWaiter/{appId}", method = RequestMethod.GET)
	@ResponseBody
	private String callWaiter(HttpServletResponse response, @RequestHeader("Authorization") String apiKey, @PathVariable String appId) {
		// ��ȡ����apiToken
		String apiToken = SystemConfig.Admin_Setting_Map.get(SystemConstants.CONFIG_API_TOKEN);
		JSONObject respJson = new JSONObject();
		// �ж�apiToken�Ƿ�һ��
		if (apiKey == null || !apiKey.equalsIgnoreCase(apiToken)) {
			respJson.put("status", false);
			respJson.put("info", "Error API token.");
			return JSON.toJSONString(respJson);
		}
		// �ж��������
		if (appId == null || appId.isEmpty()) {
			respJson.put("status", false);
			respJson.put("info", "The request parameter appId is required");
			return JSON.toJSONString(respJson);
		}
		if (SystemConfig.Call_Waiter_Map.get(appId)!=null&&SystemConfig.Call_Waiter_Map.get(appId).equals("1")) {
			respJson.put("info", "The Waiter will come");
		} else {
			SystemConfig.Call_Waiter_Map.put(appId, "1");
			respJson.put("info", "The call waiter success");
		}
		respJson.put("status", true);
		return JSON.toJSONString(respJson);
	}
	
	/**
	 * װ���Ż���Ϣ ����Ʒ�μӵ������Żݻװ�ص�model��
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
	 * װ��ͼƬ��ַ
	 * 
	 * @param model
	 * @param request
	 * @param product
	 * @return
	 */
	private ProductModel loadImage(ProductModel model, HttpServletRequest request, Tproduct product) {
		List<TproductImage> images = new ArrayList<TproductImage>();
		String[] imageList = null;
		images.addAll(product.getImages());
		if (images != null && images.size() > 0) {
			String qian = request.getSession().getServletContext().getRealPath("/") + File.separator + "static" + File.separator + "upload" + File.separator;
			String z = "/static/upload/";
			File file = null;
			for (TproductImage tproductImage : images) {
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
			}

			imageList = new String[images.size()];
			for (int i = 0; i < images.size(); i++) {
				imageList[i] = images.get(i).getImageUrl();
			}
		}
		model.setImages(imageList);
		return model;
	}

	/**
	 * װ������
	 * 
	 * @param model
	 * @param product
	 * @return
	 */
	private ProductModel loadAttribute(ProductModel model, Tproduct product) {
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
	 * �����Լ���
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
	 * ������Ʒ�Ż��б�
	 * 
	 * @param product
	 * @return
	 */
	private List<Tpromotion> loadProductPromotion(Tproduct product) {
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
		// ��ѯ�󶨷�����Żݻ
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
		// ��ѯ�󶨲˵����Żݻ
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
		// ��ѯ�����е��Żݻ
		List<Tpromotion> bindAll = promotionService.selectPromotion(BIN_TYPE_ALL);
		for (Tpromotion promotion : bindAll) {
			if (!promotionList.contains(promotion)) {
				promotionList.add(promotion);
			}
		}

		return promotionList;
	}

	/**
	 * �ж�id�Ƿ�������ids��
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
	 * �����Ż��Ƿ���Ե��ӻ�ȡͨ�����ȼ�����Ļ�б�
	 * 
	 * @param pros
	 *             �Ż��б�
	 * @param isShare
	 *             true �ɵ��� false ���ɵ���
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
	 * �Ƚϵ��ӺͲ��ɵ��ӵ����ȼ� 
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
	 * ������Ʒ�μӻ��ĵ���
	 * @param oldPrice ԭ��
	 * @param promotions ��б�
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
