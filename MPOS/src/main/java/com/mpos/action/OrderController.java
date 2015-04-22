package com.mpos.action;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mpos.commons.ConvertTools;
import com.mpos.commons.MposException;
import com.mpos.commons.SystemConstants;
import com.mpos.dto.Torder;
import com.mpos.dto.TorderItem;
import com.mpos.dto.Tpromotion;
import com.mpos.dto.Tstore;
import com.mpos.model.DataTableParamter;
import com.mpos.model.OrderModel;
import com.mpos.model.PagingData;
import com.mpos.service.GoodsService;
import com.mpos.service.OrderItemService;
import com.mpos.service.OrderService;
import com.mpos.service.PromotionService;
import com.mpos.service.StoreService;
@Controller
@RequestMapping("order")
public class OrderController extends BaseController{
    @SuppressWarnings("unused")
	private Logger logger = Logger.getLogger(OrderController.class);
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private PromotionService promotionService;
    
    @Autowired
    private OrderItemService orderItemService;
    
    @Autowired
   private GoodsService goodsService;
    @Resource
    private StoreService storeService;
    
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView promotion(HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		String hql = "select new Tstore(storeId,storeName) from Tstore";
		List<Tstore> stores = storeService.select(hql, null);
		mav.addObject("role", getSessionUser(request).getAdminRole().getRoleId());
		mav.addObject("stores",stores);
		mav.setViewName("order/order");
		return mav;
	}
	
	@RequestMapping(value="orderlist",method=RequestMethod.GET)
	@ResponseBody
	public String orderList(HttpServletRequest request,DataTableParamter dtp){
		addStoreCondition(request, dtp);
		PagingData pagingData = orderService.loadOrderList(dtp);
		pagingData.setSEcho(dtp.getsEcho());
		Object[]objs = pagingData.getAaData();
		if(objs == null){
			objs=new Object[]{};
			pagingData.setAaData(objs);
		}else{
			for(int i=0;i<objs.length;i++){
				Torder torder = (Torder)objs[i];
				OrderModel orderModel = new OrderModel();
				if(torder.getComment() == null){
					torder.setComment("");
				}
				orderModel.setOrderId(torder.getOrderId());
				orderModel.setOrderStatus(SystemConstants.ORDER_STATUS.get(torder.getOrderStatus()));
				orderModel.setOrderTotal(torder.getOrderTotal());
				orderModel.setOrderDiscount(torder.getOrderDiscount());
				orderModel.setCreateTime(ConvertTools.longToDateString(torder.getCreateTime()));
				orderModel.setCreater(torder.getCreater());
				orderModel.setOrderPromotion(torder.getOrderPromotion());
				orderModel.setComment(torder.getComment());
				orderModel.setPeopleNum(torder.getPeopleNum());
				objs[i]=orderModel;
			}
			pagingData.setAaData(objs);
		}
		String jsonString = JSON.toJSONString(pagingData);
		return jsonString;
	}
	
	@RequestMapping(value="paymentOrder/{ids}",method=RequestMethod.POST)
	@ResponseBody
	public String paymentOrder(HttpServletRequest request,@PathVariable String ids){
		JSONObject resp = new JSONObject();
		try{
			String orderId[] = ids.split(",");
			Integer id[]=ConvertTools.stringArr2IntArr(orderId);
			for(int order_id:id){
				Torder torder = orderService.getTorderById(order_id);
				if(torder !=null){
					torder.setOrderStatus(1);
				}
				orderService.update(torder);
				
			}
			resp.put("status", true);
			resp.put("info", getMessage(request,"operate.success"));
		}catch(MposException be){
			resp.put("status", false);
			resp.put("info", getMessage(request,be.getErrorID(),be.getMessage()));
		}
		
		return JSON.toJSONString(resp);
	}
	
	@RequestMapping(value="cancelOrder/{ids}",method=RequestMethod.POST)
	@ResponseBody
	public String cancelOrder(HttpServletRequest request,@PathVariable String ids){
		JSONObject resp = new JSONObject();
		try{
			String orderId[] = ids.split(",");
			Integer id[]=ConvertTools.stringArr2IntArr(orderId);
			for(int order_id:id){
				Torder torder = orderService.getTorderById(order_id);
				if(torder !=null){
					torder.setOrderStatus(2);
				}
				orderService.update(torder);
				
			}
			resp.put("status", true);
			resp.put("info", getMessage(request,"operate.success"));
		}catch(MposException be){
			resp.put("status", false);
			resp.put("info", getMessage(request,be.getErrorID(),be.getMessage()));
		}
		
		return JSON.toJSONString(resp);
	}
	
	@RequestMapping(value="order_details",method=RequestMethod.GET)
	public ModelAndView orderDetails(HttpServletRequest request,@RequestParam int order_id){
		ModelAndView mav = new ModelAndView();		
		Torder torder = orderService.getTorderById(order_id);
		Map<String, Object> order_details = new LinkedHashMap<String, Object>();
		Map<String, String> order_promotion = new LinkedHashMap<String, String>();
		order_details.put("order_id", torder.getOrderId());
		order_details.put("create_time", ConvertTools.longToDateString(torder.getCreateTime()));
		order_details.put("discount_total", torder.getOrderDiscount());
		order_details.put("payment_total", torder.getOrderTotal());
		order_details.put("creater", torder.getCreater());
		order_details.put("peopleNum", torder.getPeopleNum());
		order_details.put("order_status", SystemConstants.ORDER_STATUS.get(torder.getOrderStatus()));
		order_details.put("order_status_id", torder.getOrderStatus());
		String orderPromotion = torder.getOrderPromotion();
		if(orderPromotion !=null && !orderPromotion.isEmpty()){
			String orderPromotionIds[]=orderPromotion.split(",");
			Integer ids[]=ConvertTools.stringArr2IntArr(orderPromotionIds);
			for(int promotion_id : ids){
				Tpromotion tpromotion = promotionService.getPromtionById(promotion_id);
				if(tpromotion != null){
					order_promotion.put(tpromotion.getPromotionName(), tpromotion.getPromotionRule());
				}
			}
			if(order_promotion.size()>0){
				order_details.put("order_promotion", order_promotion);
			}
		}
		
		mav.addObject("order_details", order_details);
		mav.setViewName("order/order_details");
		return mav;
	}
	
	@SuppressWarnings("unused")
	@RequestMapping(value="order_product",method=RequestMethod.GET)
	@ResponseBody
	public String orderProductList(HttpServletRequest request,DataTableParamter dtp){
		JSONObject jsonObject = null;
		String attrString = null;
		PagingData pagingData = orderItemService.loadPagingDataByOrderId(dtp);
		pagingData.setSEcho(dtp.getsEcho());
		Object[]objs = pagingData.getAaData();
		if(objs == null){
			objs = new Object[]{};
			pagingData.setAaData(objs);
		}
		else{
			for(int i=0;i<objs.length;i++){
				attrString="";
				TorderItem torderItem = (TorderItem) objs[i];
				if(torderItem.getProductPromotion() == null){
					torderItem.setProductPromotion("");
				}
				/*jsonObject = JSONObject.parseObject(torderItem.getAttributes());
				Set<String> keys= jsonObject.keySet();
				for(String key:keys){
					attrString +=key+":"+jsonObject.getString(key)+" ";
				}
				torderItem.setAttributes();*/
				objs[i]=torderItem;
			}
		}
		return JSON.toJSONString(pagingData);
	}
	@RequestMapping(value="getAtts/{orderItemId}",method=RequestMethod.GET)
	@ResponseBody
	public String getProductAttributes(HttpServletRequest request,@PathVariable Integer orderItemId){
		JSONObject resp = new JSONObject();
		try {
			resp.put("status", true);
			resp.put("attributes", orderItemService.get(orderItemId));
			resp.put("info", getMessage(request,"operate.success"));
		} catch (MposException be) {
			resp.put("status", false);
			resp.put("info", getMessage(request,be.getErrorID(),be.getMessage()));
		}
		return JSON.toJSONString(resp);
	}
	@RequestMapping(value="getProName/{productId}",method=RequestMethod.GET)
	@ResponseBody
	public String getProductName(HttpServletRequest request,@PathVariable Integer productId){
		JSONObject resp = new JSONObject();
		try {
			resp.put("status", true);
			resp.put("name", goodsService.getTproductByid(productId).getProductName());
			resp.put("info", getMessage(request,"operate.success"));
		} catch (MposException be) {
			resp.put("status", false);
			resp.put("info", getMessage(request,be.getErrorID(),be.getMessage()));
		}
		return JSON.toJSONString(resp);
	}
}
