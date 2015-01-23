package com.mpos.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
		Map<String, CallWaiterInfo> map = SystemConfig.Call_Waiter_Map;
		List<CallWaiterInfo> infos = new ArrayList<CallWaiterInfo>();
		boolean status = false;
		respJson.put("info", "no call");
		for (String key : map.keySet()) {
			CallWaiterInfo info = map.get(key);
			if(info!=null&&info.getStatus()==1){
				if(infos.size()<5){
					if(!infos.contains(info)){
						infos.add(info);
					}
				}else{
					break;
				}
			}
		}
		status = true;
		respJson.put("info", infos);
		respJson.put("status", status);
		return JSON.toJSONString(respJson);
	}
	@RequestMapping("/callWaiter/{appId}")
	@ResponseBody
	public String dealCall(@PathVariable String appId){
		JSONObject respJson = new JSONObject();
		CallWaiterInfo info = SystemConfig.Call_Waiter_Map.get(appId);
		if(info!=null){
			Date nowTime = new Date();
			SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
			String timeString = sdf.format(nowTime);
			info.setStatus(0);
			info.setType(0);
			info.setCallTime(timeString);
			respJson.put("status", true);
			respJson.put("info", "OK");
		}else{
			respJson.put("status", false);
			respJson.put("info", "appId is not exist");
		}
		
		return JSON.toJSONString(respJson);
	}
}
