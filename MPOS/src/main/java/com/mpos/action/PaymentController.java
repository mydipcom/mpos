package com.mpos.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping(value = "payment")
public class PaymentController extends BaseController {
	
	@RequestMapping
	@ResponseBody
	public String test(HttpServletRequest request,String code){
		System.out.println(request.getRemoteAddr());
		Map< String, Object> re = request.getParameterMap();
		for (String key : re.keySet()) {
			System.out.println(re.get(key));
		}
		Map<String, Object> res = getHashMap();
		res.put("is_success", "T");
		res.put("out_trade_no", System.currentTimeMillis());
		return JSON.toJSONString("success");
	}
	
}
