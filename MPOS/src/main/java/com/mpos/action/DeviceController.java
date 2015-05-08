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
import com.mpos.commons.ConvertTools;
import com.mpos.commons.MposException;
import com.mpos.dto.TproductRelease;
import com.mpos.model.DataTableParamter;
import com.mpos.model.PagingData;
import com.mpos.service.DeviceService;
import com.mpos.service.ProductReleaseService;

@Controller
@RequestMapping("/device")
public class DeviceController extends BaseController {
	@Autowired
	DeviceService deviceService;
	@Autowired
	ProductReleaseService productReleaseService;
	private Boolean ok = true;
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView device(HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		TproductRelease re = productReleaseService.getLatestPublished(getSessionUser(request).getStoreId());
		TproductRelease unre=productReleaseService.getUnPublished(getSessionUser(request).getStoreId());
		String[] res = new String[3];
		String Flag;
		if(re!=null){
			res[0] = re.getId()+"";
			Long time = re.getPublicTime();
			if(time == null){
				time = System.currentTimeMillis();
			}
			res[1] = ConvertTools.longToDateString(time);
		}
		if(unre!=null){
			res[2]=unre.getId()+"";
			Flag="1";
		}else{
			res[2]="没有版本可以发布";
			Flag="2";
		}
		mav.addObject("res", res);
		mav.addObject("flag", Flag);
		mav.setViewName("device/device");
		return mav;
	}
	
	@RequestMapping(value="/deviceList",method=RequestMethod.GET)
	@ResponseBody
	public String deviceList(DataTableParamter dtp,HttpServletRequest request){
		addStoreCondition(request, dtp);
		PagingData pagingData = deviceService.loadDeviceList(dtp);
		if(pagingData.getAaData()==null){
			Object[] objs=new Object[]{};
			pagingData.setAaData(objs);
		}
		pagingData.setSEcho(dtp.sEcho);
		return JSON.toJSONString(pagingData);
	}
	
	@RequestMapping(value="/{ids}",method=RequestMethod.DELETE)
	@ResponseBody
	public String deleteDevice(HttpServletRequest request,@PathVariable String ids){
		JSONObject res = new JSONObject();
		try {
			String[] idstrArr=ids.split(",");		
			Integer[] idArr=ConvertTools.stringArr2IntArr(idstrArr);
			deviceService.deleteAll(idArr);
		} catch (MposException be) {
			ok = false;
			res.put("info", getMessage(request,be.getErrorID(),be.getMessage()));
		}
		res.put("status", ok);
		return JSON.toJSONString(res);
	}
	
}
