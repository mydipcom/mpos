package com.mpos.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
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
import com.mpos.dto.TcategoryAttribute;
import com.mpos.dto.Tlanguage;
import com.mpos.dto.TlocalizedField;
import com.mpos.model.DataTableParamter;
import com.mpos.model.LocalizedField;
import com.mpos.model.PageModel;
import com.mpos.model.PagingData;
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
		
	@RequestMapping(value="/addCategory",method=RequestMethod.POST)
	@ResponseBody
	public String addCategory(HttpServletRequest request,PageModel page){			
		JSONObject respJson = new JSONObject();
		try{
			categoryService.createCategory(page.getCategory());
			localizedFieldService.createLocalizedFieldList(page.setTwoTlocalizedFieldValue(page.getCategory()));
			respJson.put("status", true);
		}
		catch(MposException be){
			respJson.put("status", false);
			respJson.put("info", getMessage(request,be.getErrorID(),be.getMessage()));
		}		
		return JSON.toJSONString(respJson);
	}
	
	@RequestMapping(value="/copyCategory",method=RequestMethod.POST)
	@ResponseBody
	public String copyCategory(HttpServletRequest request,PageModel page,Integer id){			
		JSONObject respJson = new JSONObject();
		try{
			categoryService.createCategory(page.getCategory());
			localizedFieldService.createLocalizedFieldList(page.setTwoTlocalizedFieldValue(page.getCategory()));
			List<TcategoryAttribute> attrs = attributeService.getCategoryAttributeByCategoryid(id);
			if(attrs!=null&&attrs.size()>0){
				for (TcategoryAttribute tcategoryAttribute : attrs) {
					tcategoryAttribute.setCategoryId(page.getCategory());
					List<TlocalizedField> local = localizedFieldService.getListByEntityIdAndEntityName(tcategoryAttribute.getAttributeId(), tcategoryAttribute.getClass().getSimpleName());
					tcategoryAttribute.setAttributeId(null);
					attributeService.createCategoryAttribute(tcategoryAttribute);
					if(local!=null&&local.size()>0){
						for (TlocalizedField tlocalizedField : local) {
							tlocalizedField.setLocaleId(null);
							tlocalizedField.setEntityId(tcategoryAttribute.getAttributeId());
						}
					}
					localizedFieldService.createLocalizedFieldList(local);
				}
			}
			
			
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
	public String updateCategory(HttpServletRequest request,PageModel page){		

		JSONObject respJson = new JSONObject();
		try{
			categoryService.updateCategory(page.getCategory());
			localizedFieldService.updateLocalizedFieldList(page.setTwoTlocalizedFieldValue(page.getCategory()));
			respJson.put("status", true);
		}
		catch(MposException be){
			respJson.put("status", false);
			respJson.put("info", getMessage(request,be.getErrorID(),be.getMessage()));
		}	
		return JSON.toJSONString(respJson);		
	}
	
	@RequestMapping(value = "/getLocal/{type}/{entityId}", method = RequestMethod.GET)
	@ResponseBody
	public String getLocal(HttpServletRequest request, @PathVariable Integer entityId,@PathVariable Integer type) {
		JSONObject respJson = new JSONObject();
		try {
			if(type==1){
				List<TlocalizedField> nameLocals = localizedFieldService.getLocalizedField(entityId,"Tcategory","name");
				List<TlocalizedField> contentLocals = localizedFieldService.getLocalizedField(entityId,"Tcategory","content");
				respJson.put("localName", LocalizedField.setValues(nameLocals));
				respJson.put("localContent", LocalizedField.setValues(contentLocals));
			}else if(type==2){
				List<TlocalizedField> titleLocals = localizedFieldService.getLocalizedField(entityId,"TcategoryAttribute","name");
				List<TlocalizedField> contentLocals = localizedFieldService.getLocalizedField(entityId,"TcategoryAttribute","content");
				respJson.put("localTitle", LocalizedField.setValues(titleLocals));
				respJson.put("localContent", LocalizedField.setValues(contentLocals));
			}
			respJson.put("status", true);
		} catch (MposException be) {
			respJson.put("status", false);
			respJson.put("info",getMessage(request, be.getErrorID(), be.getMessage()));
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
	
	@RequestMapping(value="/addAttribute",method=RequestMethod.POST)
	@ResponseBody
	public String addAttribute(HttpServletRequest request,PageModel page){			
		JSONObject respJson = new JSONObject();
		try{
			attributeService.createCategoryAttribute(page.getAttribute());
			localizedFieldService.createLocalizedFieldList(page.setTwoTlocalizedFieldValue(page.getAttribute()));
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
	public String editAttribute(HttpServletRequest request,PageModel page){		

		JSONObject respJson = new JSONObject();
		try{
			attributeService.updateCategoryAttribute(page.getAttribute());
			localizedFieldService.updateLocalizedFieldList(page.setTwoTlocalizedFieldValue(page.getAttribute()));
			respJson.put("status", true);
		}
		catch(MposException be){
			respJson.put("status", false);
			respJson.put("info", getMessage(request,be.getErrorID(),be.getMessage()));
		}	
		return JSON.toJSONString(respJson);		
	}
}
