package com.mpos.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mpos.commons.ConvertTools;
import com.mpos.commons.MposException;
import com.mpos.dto.Tcategory;
import com.mpos.dto.TcategoryAttribute;
import com.mpos.dto.Tlanguage;
import com.mpos.dto.TlocalizedField;
import com.mpos.model.DataTableParamter;
import com.mpos.model.LocalizedField;
import com.mpos.model.PagingData;
import com.mpos.model.ParamWrapper;
import com.mpos.service.CategoryAttributeService;
import com.mpos.service.CategoryService;
import com.mpos.service.LanguageService;
import com.mpos.service.LocalizedFieldService;


@Controller
@RequestMapping(value="/category")
public class CategoryController extends BaseController {

	private Logger logger = Logger.getLogger(CategoryController.class);
	
	@Resource
	private CategoryService categoryService;
	@Resource
	private CategoryAttributeService attributeService;
	@Resource
	private LanguageService languageService;
	@Resource
	private LocalizedFieldService localizedFieldService;
		

	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView category(HttpServletRequest request){
		ModelAndView mav=new ModelAndView();
		logger.info("category");
		List<Tlanguage> languages = languageService.loadAllTlanguage();
		mav.addObject("lanList", languages);
		mav.setViewName("category/category");
		return mav;
	}
	
	@RequestMapping(value="/categoryList",method=RequestMethod.GET)
	@ResponseBody
	public String categoryList(HttpServletRequest request,DataTableParamter dtp){		
		PagingData pagingData=categoryService.loadCategoryList(dtp);
		if(pagingData.getAaData()==null){
			Object[] objs=new Object[]{};
			pagingData.setAaData(objs);
		}
		pagingData.setSEcho(dtp.sEcho);
		
		String rightsListJson= JSON.toJSONString(pagingData);
		return rightsListJson;
	}
		
	@RequestMapping(value="/getCategory",method=RequestMethod.POST)
	@ResponseBody
	public String getCategory(HttpServletRequest request,Integer categoryId){		
		JSONObject respJson = new JSONObject();
		try{
			List<TlocalizedField> list = localizedFieldService.getListByEntityIdAndEntityName(categoryId, Tcategory.class.getSimpleName());
			respJson.put("status", true);
			respJson.put("list", LocalizedField.setValues(list));
			logger.info(list.toString());
			logger.info(JSON.toJSONString(respJson));
		}
		catch(MposException be){
			respJson.put("status", false);
			respJson.put("info", getMessage(request,be.getErrorID(),be.getMessage()));
		}	
		return JSON.toJSONString(respJson);	
	}

	@RequestMapping(value="/addCategory",method=RequestMethod.POST)
	@ResponseBody
	public String addCategory(HttpServletRequest request,Tcategory category,@ModelAttribute ParamWrapper value){			
		JSONObject respJson = new JSONObject();
		try{
			categoryService.createCategory(category);
			localizedFieldService.createLocalizedFieldList(value.setValue(category));
			respJson.put("status", true);
		}
		catch(MposException be){
			respJson.put("status", false);
			respJson.put("info", getMessage(request,be.getErrorID(),be.getMessage()));
		}		
		return JSON.toJSONString(respJson);
	}
	
	@RequestMapping(value="/editCategory",method=RequestMethod.POST)
	@ResponseBody
	public String updateCategory(HttpServletRequest request,Tcategory category,@ModelAttribute ParamWrapper value){		

		JSONObject respJson = new JSONObject();
		try{
			categoryService.updateCategory(category);
			localizedFieldService.updateLocalizedFieldList(value.setValue(category));
			respJson.put("status", true);
		}
		catch(MposException be){
			respJson.put("status", false);
			respJson.put("info", getMessage(request,be.getErrorID(),be.getMessage()));
		}	
		return JSON.toJSONString(respJson);		
	}

	@RequestMapping(value="/category/{ids}",method=RequestMethod.DELETE)
	@ResponseBody
	public String deleteCategory(@PathVariable String ids,HttpServletRequest request){
		String[] idstrArr=ids.split(",");		
		Integer[] idArr=ConvertTools.stringArr2IntArr(idstrArr);		
		JSONObject respJson = new JSONObject();
		try{
			categoryService.deleteCategoryByIds(idArr);
			respJson.put("status", true);
		}
		catch(MposException be){
			respJson.put("status", false);
			respJson.put("info", getMessage(request,be.getErrorID(),be.getMessage()));
		}	
		return JSON.toJSONString(respJson);	
	}
	
	@RequestMapping(value="/attribute/{ids}",method=RequestMethod.DELETE)
	@ResponseBody
	public String deleteAttribute(@PathVariable String ids,HttpServletRequest request){
		String[] idstrArr=ids.split(",");		
		Integer[] idArr=ConvertTools.stringArr2IntArr(idstrArr);		
		JSONObject respJson = new JSONObject();
		try{
			attributeService.deleteAttributeByIds(idArr);
			respJson.put("status", true);
		}
		catch(MposException be){
			respJson.put("status", false);
			respJson.put("info", getMessage(request,be.getErrorID(),be.getMessage()));
		}	
		return JSON.toJSONString(respJson);	
	}
	
	@RequestMapping(value="/attributeList/{id}",method=RequestMethod.GET)
	@ResponseBody
	public String categoryAttributeList(HttpServletRequest request,@PathVariable String id,DataTableParamter dtp){		
		
		PagingData pagingData=attributeService.loadAttributeList(id, dtp);
		pagingData.setSEcho(dtp.sEcho);	
		if(pagingData.getAaData()==null){
			Object[] objs=new Object[]{};
			pagingData.setAaData(objs);
		}	
		String rightsListJson= JSON.toJSONString(pagingData);
		return rightsListJson;
			
		}
	
	@RequestMapping(value="/getAttribute",method=RequestMethod.POST)
	@ResponseBody
	public String getAttribute(HttpServletRequest request,Integer attributeId){		
		JSONObject respJson = new JSONObject();
		try{
			List<TlocalizedField> list = localizedFieldService.getListByEntityIdAndEntityName(attributeId, TcategoryAttribute.class.getSimpleName());
			respJson.put("status", true);
			respJson.put("list", LocalizedField.setValues(list));
			logger.info(list.toString());
			logger.info(JSON.toJSONString(respJson));
		}
		catch(MposException be){
			respJson.put("status", false);
			respJson.put("info", getMessage(request,be.getErrorID(),be.getMessage()));
		}	
		return JSON.toJSONString(respJson);	
	}
	
	@RequestMapping(value="/addAttribute",method=RequestMethod.POST)
	@ResponseBody
	public String addAttribute(HttpServletRequest request,TcategoryAttribute attribute,@ModelAttribute ParamWrapper value){			
		JSONObject respJson = new JSONObject();
		try{
			attributeService.createCategoryAttribute(attribute);
			localizedFieldService.createLocalizedFieldList(value.setValue(attribute));
			respJson.put("status", true);
		}
		catch(MposException be){
			respJson.put("status", false);
			respJson.put("info", getMessage(request,be.getErrorID(),be.getMessage()));
		}		
		return JSON.toJSONString(respJson);
	}
	
	@RequestMapping(value="/editAttribute",method=RequestMethod.POST)
	@ResponseBody
	public String editAttribute(HttpServletRequest request,TcategoryAttribute attribute,@ModelAttribute ParamWrapper value){		

		JSONObject respJson = new JSONObject();
		try{
			attributeService.updateCategoryAttribute(attribute);
			localizedFieldService.updateLocalizedFieldList(value.setValue(attribute));
			respJson.put("status", true);
		}
		catch(MposException be){
			respJson.put("status", false);
			respJson.put("info", getMessage(request,be.getErrorID(),be.getMessage()));
		}	
		return JSON.toJSONString(respJson);		
	}
}
