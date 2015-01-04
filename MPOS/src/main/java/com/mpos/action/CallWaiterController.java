package com.mpos.action;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mpos.commons.SystemConfig;
import com.mpos.model.CallWaiterInfo;

@Controller
@RequestMapping("/callWaiter")
public class CallWaiterController extends BaseController {

	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody  
	public String checkCall(){
		JSONObject respJson = new JSONObject();
		respJson.put("status", true);
		respJson.put("callList", SystemConfig.Call_Waiter_Map);
		return JSON.toJSONString(respJson);
	}
	@RequestMapping("/callWaiter/{appId}")
	@ResponseBody
	public String dealCall(@PathVariable String appId){
		JSONObject respJson = new JSONObject();
		CallWaiterInfo info = SystemConfig.Call_Waiter_Map.get(appId);
		Date nowTime = new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		String timeString = sdf.format(nowTime);
		info.setStatus(0);
		info.setType(0);
		info.setCallTime(timeString);
		respJson.put("status", true);
		return JSON.toJSONString(respJson);
	}
}
