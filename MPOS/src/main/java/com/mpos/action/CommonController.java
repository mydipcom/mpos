/**   
 * @Title: UserController.java 
 * @Package com.uswop.action 
 * @Description: TODO
 * @author Phills Li    
 * @date Sep 2, 2014 7:25:22 PM 
 * @version V1.0   
 */
package com.mpos.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alipay.config.AlipayConfig;
import com.alipay.util.AlipayNotify;
import com.alipay.util.AlipaySubmit;
import com.mpos.commons.EMailTool;
import com.mpos.commons.MposException;
import com.mpos.commons.SystemConfig;
import com.mpos.dto.TadminUser;
import com.mpos.dto.TemaiMessage;
import com.mpos.dto.Tmessage;
import com.mpos.dto.Tservice;
import com.mpos.dto.TserviceOrder;
import com.mpos.dto.Ttable;
import com.mpos.service.AdminInfoService;
import com.mpos.service.AdminUserService;
import com.mpos.service.MessageService;
import com.mpos.service.ServiceOrderService;
import com.mpos.service.ServiceService;
import com.mpos.service.StoreService;
import com.mpos.service.TableService;

/**
 * @ClassName: UserController
 * @Description: TODO
 * @author Phills Li
 * @date Sep 2, 2014 7:25:22 PM
 * 
 */

@Controller
@RequestMapping("/common")
public class CommonController extends BaseController {

	@SuppressWarnings("unused")
	private Logger logger = Logger.getLogger(CommonController.class);
	public List<Ttable> tables = new ArrayList<Ttable>();
	@Autowired
	private AdminUserService adminUserService;
	@Autowired
	private StoreService storeService;
	@Autowired
	private ServiceService serviceService;
	@Autowired
	private TableService tableService;
	@Autowired
	private AdminInfoService adminInfoService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private ServiceOrderService serviceOrderService;
	
	private Map<String, String>  map = new HashMap<String, String>();
	
	@RequestMapping(value="header",method=RequestMethod.GET)
	public ModelAndView header(HttpServletRequest request){
		ModelAndView mav=new ModelAndView();
		TadminUser tUser=getSessionUser(request);
		mav.addObject("user", tUser);
		mav.setViewName("common/header");
		return mav;
	}
	
	@RequestMapping(value="left",method=RequestMethod.GET)
	public ModelAndView left(HttpServletRequest request){
		ModelAndView mav=new ModelAndView();		
		mav.addObject("menus", SystemConfig.Admin_Nodes_Menu_Map);
		mav.setViewName("common/left");
		return mav;
	}
	
	
	@RequestMapping(value="footer",method=RequestMethod.GET)
	public ModelAndView footer(HttpServletRequest request){
		ModelAndView mav=new ModelAndView();
		mav.setViewName("common/footer");
		return mav;
	}
	
	@RequestMapping(value="noRights",method=RequestMethod.GET)
	public ModelAndView noRights(HttpServletRequest request){
		ModelAndView mav=new ModelAndView();
		mav.setViewName("error/errpage");
		return mav;
	}
	
	@RequestMapping(value="notice",method=RequestMethod.GET)
	public ModelAndView notice(HttpServletRequest request){
		ModelAndView mav=new ModelAndView();
		mav.setViewName("common/notice");
		return mav;
	}
	
	/**
	 * 添加验证tableName
	 * @param tableName 桌号
	 * @return
	 */
	@RequestMapping(value="/checkEmail",method=RequestMethod.POST)
	@ResponseBody
	public String checkTableName(String email,HttpServletRequest request){
		return JSON.toJSONString(!adminUserService.emailExist(email));
	}
	
	@RequestMapping(value="addMsg",method=RequestMethod.POST)
	@ResponseBody
	public String add(HttpServletRequest request,Tmessage message){
		Map<String, Object> res = getHashMap();
		try {
			messageService.create(message);
			res.put("status", true);
			res.put("info", "留言成功");
		} catch (Exception e) {
			res.put("status", false);
			res.put("info", e.getMessage());
		}
		return JSON.toJSONString(res);
	}
	@RequestMapping(value="getServices",method=RequestMethod.POST)
	@ResponseBody
	public String getService(HttpServletRequest request){
		Map<String, Object> res = getHashMap();
		try {
//			List<Tservice> info = new ArrayList<Tservice>();
			List<Tservice> services = serviceService.load();
//			for (Tservice tservice : services) {
//				tservice.setContent(tservice.getServiceName()+": "+tservice.getServicePrice()+"元-"+tservice.getValidDays()+"天-"+tservice.getContent());
//				tservice.setRoleId(null);
//				tservice.setServiceName(tservice.getServiceName());
//				tservice.setServicePrice(tservice.getServicePrice());
//				tservice.setValidDays(tservice.getValidDays());
//				info.add(tservice);
//			}
			res.put("status", true);
			res.put("info", services);
		} catch (Exception e) {
			// TODO: handle exception
			res.put("status", false);
			res.put("info", e.getMessage());
		}
		return JSON.toJSONString(res);
	}
	@RequestMapping(value="register",method=RequestMethod.POST)
	@ResponseBody
	public String register(HttpServletRequest request,TadminUser user,Integer serviceId,String mobile){
		Map<String, Object> res = getHashMap();
		try {
			boolean status = false;
			if(serviceId==null||serviceId==0){
				status = true;
			}
			map = serviceService.register(user, serviceId, mobile,status);
			request.getSession().setAttribute("map", map);
			if(!status){
				res.put("html",getAlipaySubmit(map));
				res.put("data", map);
			}
			res.put("isPay",!status);
			res.put("status", true);
			res.put("info", "注册成功");
		} catch (MposException e) {
			e.printStackTrace();
			res.put("status", false);
			res.put("info", "失败");
		}
		return JSON.toJSONString(res);
	}
	@RequestMapping(value="/alipay",method=RequestMethod.GET)
	public ModelAndView alipay(HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		map = (Map<String, String>) request.getSession().getAttribute("map");
		mav.addObject("html", getAlipaySubmit(map));
		mav.setViewName("pay");
		return mav;
	}
	
	 
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/notify_url",method=RequestMethod.POST)
	public void notify_url(HttpServletRequest request,HttpServletResponse response) throws Exception{
		System.out.println(request.getRemoteAddr());
		//String ip = IpUtils.getIpAddr(request);
		//System.out.println(ip);
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr);
		}
		
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		//商户订单号
		String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
		//支付宝交易号
		String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
		//交易状态
		String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
		if(AlipayNotify.verify(params)){//验证成功
			if(trade_status.equals("WAIT_BUYER_PAY")){
				//该判断表示买家已在支付宝交易管理中产生了交易记录，但没有付款
					//判断该笔订单是否在商户网站中已经做过处理
						//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
						//如果有做过处理，不执行商户的业务程序
				TserviceOrder order = serviceOrderService.getOrderByOrderNum(out_trade_no);
				order.setStatus(TserviceOrder.WAIT_BUYER_PAY);
				serviceOrderService.update(order);
				System.out.println("---------------------创建订单成功----------------------------");
				System.out.println("success");	//请不要修改或删除
				} else if(trade_status.equals("WAIT_SELLER_SEND_GOODS")){
				//该判断表示买家已在支付宝交易管理中产生了交易记录且付款成功，但卖家没有发货
					//判断该笔订单是否在商户网站中已经做过处理
						//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
						//如果有做过处理，不执行商户的业务程序
					TserviceOrder order = serviceOrderService.getOrderByOrderNum(out_trade_no);
					if(order.getStatus()==TserviceOrder.WAIT_BUYER_PAY){
						String res = qrfh(trade_no);
						if(res.contains("T")){
							 serviceOrderService.active(out_trade_no);
							 order.setStatus(TserviceOrder.WAIT_BUYER_CONFIRM_GOODS);
							serviceOrderService.update(order);
							 TemaiMessage message = TemaiMessage.getCreate(serviceService.getInfoByEmail(order.getEmail()));
							 EMailTool.send(message);
							System.out.println("---------------------已发货，已发送邮件----------------------------");
						}
					}
					System.out.println("success");	//请不要修改或删除
				} else if(trade_status.equals("WAIT_BUYER_CONFIRM_GOODS")){
				//该判断表示卖家已经发了货，但买家还没有做确认收货的操作
					//判断该笔订单是否在商户网站中已经做过处理
						//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
						//如果有做过处理，不执行商户的业务程序
					TserviceOrder order = serviceOrderService.getOrderByOrderNum(out_trade_no);
					order.setStatus(TserviceOrder.WAIT_BUYER_CONFIRM_GOODS);
					serviceOrderService.update(order);
					System.out.println("---------------------等待确认收货----------------------------");
					System.out.println("success");	//请不要修改或删除
				} else if(trade_status.equals("TRADE_FINISHED")){
				//该判断表示买家已经确认收货，这笔交易完成
					//判断该笔订单是否在商户网站中已经做过处理
						//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
						//如果有做过处理，不执行商户的业务程序
					TserviceOrder order = serviceOrderService.getOrderByOrderNum(out_trade_no);
					request.getSession().removeAttribute("map");
					order.setStatus(TserviceOrder.TRADE_FINISHED);
					serviceOrderService.update(order);
					System.out.println("---------------------交易完成---------------------------");
					System.out.println("success");	//请不要修改或删除
				}
				else {
					System.out.println("success");	//请不要修改或删除
				}
		}else{//验证失败
			System.out.println("fail");
		}
		PrintWriter writer = response.getWriter();
		writer.print("success");
		writer.flush();
		writer.close();
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/return_url",method=RequestMethod.GET)
	public ModelAndView return_url(HttpServletRequest request) throws Exception{
		ModelAndView mav = new ModelAndView();
		System.out.println(request.getRemoteAddr());
		//获取支付宝GET过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		//商户订单号
		String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
		//支付宝交易号
		String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
		//交易状态
		String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
		//计算得出通知验证结果
		boolean verify_result = AlipayNotify.verify(params);
		
		if(verify_result){//验证成功
			if(trade_status.equals("WAIT_SELLER_SEND_GOODS")){
				//该判断表示买家已在支付宝交易管理中产生了交易记录且付款成功，但卖家没有发货
				TserviceOrder order = serviceOrderService.getOrderByOrderNum(out_trade_no);
				if(order.getStatus()==TserviceOrder.WAIT_BUYER_PAY){
					String res = qrfh(trade_no);
					if(res.contains("T")){
						 serviceOrderService.active(out_trade_no);
						 order.setStatus(TserviceOrder.WAIT_BUYER_CONFIRM_GOODS);
						serviceOrderService.update(order);
						 TemaiMessage message = TemaiMessage.getCreate(serviceService.getInfoByEmail(order.getEmail()));
						 EMailTool.send(message);
						mav.addObject("user", new TadminUser());
						mav.setViewName("login");
						mav.addObject("msg", "账号已激活");
						System.out.println("---------------------同步，发货 发邮件---------------------------");
						return mav;
					}
				}
				if(order.getStatus()==TserviceOrder.WAIT_BUYER_CONFIRM_GOODS){
					mav.addObject("user", new TadminUser());
					mav.setViewName("login");
					mav.addObject("msg", "账号已激活");
					return mav;
				}
			}
		}else{
			mav.addObject(ERROR_MSG_KEY, "支付异常");
		}
		mav.addObject("user", new TadminUser());
		mav.setViewName("login");
		return mav;
	}
	
	private String getAlipaySubmit(Map<String, String> map){
		 //支付类型
		String payment_type = "1";
		//必填，不能修改
		//服务器异步通知页面路径
		String notify_url = "http://222.209.232.126:8989/mpos/common/notify_url";
		//需http://格式的完整路径，不能加?id=123这类自定义参数
		//页面跳转同步通知页面路径
		String return_url = "http://222.209.232.126:8989/mpos/common/return_url";
		//需http://格式的完整路径，不能加?id=123这类自定义参数，不能写成http://localhost/
		//商户订单号
		String out_trade_no = map.get("orderNum");
		//商户网站订单系统中唯一订单号，必填
		//订单名称
		String subject = map.get("subject");
		//必填
		//付款金额
		String price = map.get("price");
		//必填
		//商品数量
		String quantity = "1";
		//必填，建议默认为1，不改变值，把一次交易看成是一次下订单而非购买一件商品
		//物流费用
		String logistics_fee = "0.00";
		//必填，即运费
		//物流类型
		String logistics_type = "EXPRESS";
		//必填，三个值可选：EXPRESS（快递）、POST（平邮）、EMS（EMS）
		//物流支付方式
		String logistics_payment = "SELLER_PAY";
		//必填，两个值可选：SELLER_PAY（卖家承担运费）、BUYER_PAY（买家承担运费）
		//把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "create_partner_trade_by_buyer");
        sParaTemp.put("partner", AlipayConfig.partner);
        sParaTemp.put("seller_email", AlipayConfig.seller_email);
        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		sParaTemp.put("payment_type", payment_type);
		sParaTemp.put("notify_url", notify_url);
		sParaTemp.put("return_url", return_url);
		sParaTemp.put("out_trade_no", out_trade_no);
		sParaTemp.put("subject", subject);
		sParaTemp.put("price", price);
		sParaTemp.put("quantity", quantity);
		sParaTemp.put("logistics_fee", logistics_fee);
		sParaTemp.put("logistics_type", logistics_type);
		sParaTemp.put("logistics_payment", logistics_payment);
		sParaTemp.put("receive_name", "无");
		sParaTemp.put("receive_address", "无");
		sParaTemp.put("receive_zip", "66666");
		sParaTemp.put("receive_mobile", "83361785");
		//建立请求
		String sHtmlText = AlipaySubmit.buildRequest(sParaTemp,"post","确认");
		System.out.println(sHtmlText);
		return sHtmlText;
	}
	
	private String qrfh(String trade_no){
		String sHtmlText ="";
		try {
		       //支付宝交易号
			//	String trade_no = new String(request.getParameter("WIDtrade_no").getBytes("ISO-8859-1"),"UTF-8");
				//必填
				//物流公司名称
				String logistics_name = "没有物流";
				//必填
				//物流发货单号
				String invoice_no = "000000";
				//物流运输类型
				String transport_type = "EXPRESS";
				//三个值可选：POST（平邮）、EXPRESS（快递）、EMS（EMS）
				//////////////////////////////////////////////////////////////////////////////////
				
				//把请求参数打包成数组
				Map<String, String> sParaTemp = new HashMap<String, String>();
				sParaTemp.put("service", "send_goods_confirm_by_platform");
		        sParaTemp.put("partner", AlipayConfig.partner);
		        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
				sParaTemp.put("trade_no", trade_no);
				sParaTemp.put("logistics_name", logistics_name);
				sParaTemp.put("invoice_no", invoice_no);
				sParaTemp.put("transport_type", transport_type);
				
				//建立请求
				sHtmlText = AlipaySubmit.buildRequest("", "", sParaTemp);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					sHtmlText=e.getMessage();
				}
		
		return sHtmlText;
	}
}
