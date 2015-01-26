package com.mpos.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.mpos.commons.ConvertTools;
import com.mpos.commons.MposException;
import com.mpos.commons.SystemConstants;
import com.mpos.dto.TattributeValue;
import com.mpos.dto.Tcategory;
import com.mpos.dto.TcategoryAttribute;
import com.mpos.dto.Tlanguage;
import com.mpos.dto.TlocalizedField;
import com.mpos.model.DataTableParamter;
import com.mpos.model.LocalizedField;
import com.mpos.model.PagingData;
import com.mpos.service.AttributeValueService;
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
	@Resource
	private AttributeValueService attributeValueService;
		

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
		if(pagingData.getITotalRecords()!=0){
			Object[] objArr=pagingData.getAaData();
			for (int i = 0; i < objArr.length; i++) {
				Tcategory category=(Tcategory)objArr[i];
				List<TlocalizedField> categoryNameLocaleList=localizedFieldService.getLocalizedField(category.getCategoryId(), SystemConstants.TABLE_NAME_CATEGORY, SystemConstants.TABLE_FIELD_NAME);
				if(categoryNameLocaleList!=null){
					category.setCategoryName_locale(categoryNameLocaleList);
				}
				List<TlocalizedField> categoryDescrLocaleList=localizedFieldService.getLocalizedField(category.getCategoryId(), SystemConstants.TABLE_NAME_CATEGORY, SystemConstants.TABLE_FIELD_DESCR);
				if(categoryDescrLocaleList!=null){
					category.setCategoryDescr_locale(categoryDescrLocaleList);
				}
				changeLocal(getLocale(request),category,SystemConstants.TABLE_NAME_CATEGORY, SystemConstants.TABLE_FIELD_NAME, SystemConstants.TABLE_FIELD_DESCR);
			}			
		}else{
			pagingData.setAaData(new Object[]{});
		}
		pagingData.setSEcho(dtp.sEcho);
		
		String rightsListJson= JSON.toJSONString(pagingData,SerializerFeature.DisableCircularReferenceDetect);
		return rightsListJson;
	}
		
	@RequestMapping(value="/addCategory",method=RequestMethod.POST)
	@ResponseBody
	public String addCategory(HttpServletRequest request,Tcategory category){			
		JSONObject respJson = new JSONObject();
		try{
			categoryService.createCategory(category);			
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
	public String updateCategory(HttpServletRequest request,Tcategory category){		

		JSONObject respJson = new JSONObject();
		try{
			categoryService.updateCategory(category);			
			respJson.put("status", true);
		}
		catch(MposException be){
			respJson.put("status", false);
			respJson.put("info", getMessage(request,be.getErrorID(),be.getMessage()));
		}	
		return JSON.toJSONString(respJson);		
	}
	
	@RequestMapping(value="/clone/{ids}",method=RequestMethod.GET)
	@ResponseBody
	public String cloneCategory(@PathVariable String ids,HttpServletRequest request){
		String[] idstrArr=ids.split(",");		
		Integer[] idArr=ConvertTools.stringArr2IntArr(idstrArr);		
		JSONObject respJson = new JSONObject();
		try{
			categoryService.cloneCategoryByIds(idArr);
			respJson.put("status", true);
		}
		catch(MposException be){
			respJson.put("status", false);
			respJson.put("info", getMessage(request,be.getErrorID(),be.getMessage()));
		}	
		return JSON.toJSONString(respJson);	
	}
	
	@RequestMapping(value="/delete/{ids}",method=RequestMethod.DELETE)
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
		if(pagingData.getITotalRecords()!=0){
			Object[] objArr=pagingData.getAaData();
			for (int i = 0; i < objArr.length; i++) {
				TcategoryAttribute categoryAttribute=(TcategoryAttribute)objArr[i];
				List<TlocalizedField> titleLocaleList=localizedFieldService.getLocalizedField(categoryAttribute.getAttributeId(), SystemConstants.TABLE_NAME_CATE_ATTRIBUTE, SystemConstants.TABLE_FIELD_TITLE);
				if(titleLocaleList!=null){
					categoryAttribute.setTitle_locale(titleLocaleList);
				}
				
				//Get localized field content list for the attribute values
				List<TlocalizedField> valuesLocaleList=new ArrayList<TlocalizedField>();								
				Map<Integer, TlocalizedField> valuesLocaleMap=new HashMap<Integer,TlocalizedField>();
				StringBuffer values=new StringBuffer();
				
				List<TattributeValue> attributeValues=attributeValueService.loadAttributeValuesByAttrId(categoryAttribute.getAttributeId());
				for (TattributeValue attributeValue : attributeValues) {										
					values.append(","+attributeValue.getValue());					
					List<TlocalizedField> valueLocaleList=localizedFieldService.getLocalizedField(attributeValue.getValueId(), SystemConstants.TABLE_NAME_ATTRIBUTE_VALUE, SystemConstants.TABLE_FIELD_VALUE);
					for (TlocalizedField localizedField : valueLocaleList) {						
						TlocalizedField valueLocalizedField=valuesLocaleMap.get(localizedField.getLanguage().getId());
						if(valueLocalizedField==null){
							valueLocalizedField=new TlocalizedField(); 
							valueLocalizedField.setLocaleValue("");
						}
						valueLocalizedField.setLanguage(localizedField.getLanguage());
						valueLocalizedField.setLocaleValue(valueLocalizedField.getLocaleValue()+","+localizedField.getLocaleValue());
						valuesLocaleMap.put(localizedField.getLanguage().getId(), valueLocalizedField);
					}					
				}
				
				Set<Integer> keys=valuesLocaleMap.keySet();
				for (Integer key : keys) {
					TlocalizedField valueLocalizedField=valuesLocaleMap.get(key);
					valueLocalizedField.setLocaleValue(valueLocalizedField.getLocaleValue().substring(1));
					valuesLocaleList.add(valueLocalizedField);
				}
				categoryAttribute.setValues(values.toString().isEmpty()?"":values.toString().substring(1));
				categoryAttribute.setValues_locale(valuesLocaleList);
				
				
				changeTitleLocal(getLocale(request),categoryAttribute,SystemConstants.TABLE_NAME_CATE_ATTRIBUTE, SystemConstants.TABLE_FIELD_TITLE);
				
				changeLocal(getLocale(request),categoryAttribute,SystemConstants.TABLE_NAME_ATTRIBUTE_VALUE, SystemConstants.TABLE_FIELD_VALUE);
			}			
		}
		pagingData.setSEcho(dtp.sEcho);			
		String rightsListJson= JSON.toJSONString(pagingData,SerializerFeature.DisableCircularReferenceDetect);
		return rightsListJson;			
	}
	
	@RequestMapping(value="/addAttribute",method=RequestMethod.POST)
	@ResponseBody
	public String addAttribute(HttpServletRequest request,TcategoryAttribute attribute){			
		JSONObject respJson = new JSONObject();
		try{
			attributeService.createCategoryAttribute(attribute);			
			respJson.put("status", true);
			respJson.put("cateId", attribute.getCategoryId());
		}
		catch(MposException be){
			respJson.put("status", false);
			respJson.put("info", getMessage(request,be.getErrorID(),be.getMessage()));
		}		
		return JSON.toJSONString(respJson);
	}
	
	@RequestMapping(value="/editAttribute",method=RequestMethod.POST)
	@ResponseBody
	public String editAttribute(HttpServletRequest request,TcategoryAttribute attribute){		

		JSONObject respJson = new JSONObject();
		try{
			attributeService.updateCategoryAttribute(attribute);			
			respJson.put("status", true);
			respJson.put("cateId", attribute.getCategoryId());
		}
		catch(MposException be){
			respJson.put("status", false);
			respJson.put("info", getMessage(request,be.getErrorID(),be.getMessage()));
		}	
		return JSON.toJSONString(respJson);		
	}
	
	private void changeTitleLocal(String local,TcategoryAttribute attribute,String tableName,String... fieldNames){
		if(fieldNames!=null&&fieldNames.length>0&&tableName!=null){
			for (String fieldName : fieldNames) {
				TlocalizedField localValue = localizedFieldService.getLocalizedField(attribute.getAttributeId(),local,tableName, fieldName);
				if(fieldName!=null&&!fieldName.isEmpty()){
					if(fieldName.equals(SystemConstants.TABLE_FIELD_TITLE)){
						if(localValue!=null&&!localValue.getLocaleValue().isEmpty()){
							attribute.setTitleLocal(localValue.getLocaleValue());
						}else{
							attribute.setTitleLocal(attribute.getTitle());
						}
					}
				}
			}
		}
	}
	
	private void changeLocal(String local,TcategoryAttribute attribute,String tableName,String fieldName){
		List<TattributeValue> attributeValues=attributeValueService.loadAttributeValuesByAttrId(attribute.getAttributeId());
		StringBuffer values=new StringBuffer();
		for (TattributeValue attributeValue : attributeValues) {
			TlocalizedField localValue = localizedFieldService.getLocalizedField(attributeValue.getValueId(), local,tableName, fieldName);
			if(fieldName!=null&&!fieldName.isEmpty()){
					if(fieldName.equals(SystemConstants.TABLE_FIELD_VALUE)){
						if(localValue!=null&&!localValue.getLocaleValue().isEmpty()){
							values.append(","+localValue.getLocaleValue());
						}else{
							values.append(","+attributeValue.getValue());
						}
				}
			}
								
		}
		attribute.setContent(values.toString().isEmpty()?"":values.toString().substring(1));	
	}
	
	private void changeLocal(String local,Tcategory category,String tableName,String... fieldNames){
		if(fieldNames!=null&&fieldNames.length>0&&tableName!=null){
			for (String fieldName : fieldNames) {
				TlocalizedField localValue = localizedFieldService.getLocalizedField(category.getCategoryId(), local,tableName, fieldName);
				if(fieldName!=null&&!fieldName.isEmpty()){
					if(fieldName.equals(SystemConstants.TABLE_FIELD_NAME)){
						if(localValue!=null&&!localValue.getLocaleValue().isEmpty()){
							category.setNameLocal(localValue.getLocaleValue());
						}else{
							category.setNameLocal(category.getName());
						}
					}else if(fieldName.equals(SystemConstants.TABLE_FIELD_DESCR)){
						if(localValue!=null&&!localValue.getLocaleValue().isEmpty()){
							category.setContentLocal(localValue.getLocaleValue());
						}else{
							category.setContentLocal(category.getContent());
						}
					}
				}
			}
		}
	}
}
