package com.mpos.action;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mpos.commons.ConvertTools;
import com.mpos.commons.MposException;
import com.mpos.commons.SystemConfig;
import com.mpos.commons.SystemConstants;
import com.mpos.dto.TattributeValue;
import com.mpos.dto.Tcategory;
import com.mpos.dto.TcategoryAttribute;
import com.mpos.dto.TgoodsAttribute;
import com.mpos.dto.Tlanguage;
import com.mpos.dto.TlocalizedField;
import com.mpos.dto.Tmenu;
import com.mpos.dto.Tproduct;
import com.mpos.dto.TproductAttribute;
import com.mpos.dto.TproductImage;
import com.mpos.model.AddAttributevaleModel;
import com.mpos.model.AddProductModel;
import com.mpos.model.CategoryAttributeModel;
import com.mpos.model.DataTableParamter;
import com.mpos.model.FileMeta;
import com.mpos.model.MenuModel;
import com.mpos.model.PagingData;
import com.mpos.service.AttributeValueService;
import com.mpos.service.CategoryAttributeService;
import com.mpos.service.CategoryService;
import com.mpos.service.GoodsAttributeService;
import com.mpos.service.GoodsImageService;
import com.mpos.service.GoodsService;
import com.mpos.service.LanguageService;
import com.mpos.service.LocalizedFieldService;
import com.mpos.service.MenuService;
import com.mpos.service.ProductAttributeService;
import com.mpos.service.ProductReleaseService;

@Controller
@Scope("session")
@RequestMapping("/goods")
public class GoodsController extends BaseController{
	
	@Autowired
	private GoodsService goodsService;
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private LanguageService languageService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private CategoryAttributeService CategoryAttributeService;
	
	@Autowired
	private GoodsImageService goodsImageService;
	
	@Autowired
	private ProductAttributeService productAttributeService;
	
	@Autowired
	private GoodsAttributeService goodsAttributeService;
	
	@Autowired
	private ProductReleaseService productReleaseService;
	
	@Autowired
	private LocalizedFieldService localizedFieldService;
	
	@Autowired
	private AttributeValueService attributeValueService;
	
	private LinkedHashMap<Integer,FileMeta> filesMap = new LinkedHashMap<Integer,FileMeta>();
	private int imgIndex=0;	
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView Goods(HttpServletRequest request){
		ModelAndView mav=new ModelAndView();
		List<Tcategory> categorys=categoryService.getallCategory();
		
		String local=getLocale(request);
		Tlanguage language=languageService.getLanguageBylocal(local);
		List<MenuModel> menus=menuService.getNoChildrenMenus(language);
	//	List<Tmenu> menus=menuService.getAllMenu();
		mav.addObject("category", categorys);
		mav.addObject("menu", menus);
		mav.setViewName("goods/goods");
		return mav;
	}
	@RequestMapping(value="/goodslist",method=RequestMethod.GET)
	@ResponseBody
	public String goodsList(HttpServletRequest request,DataTableParamter dtp){		
		PagingData pagingData=goodsService.loadGoodsList(dtp);
		String local=getLocale(request);
		if(pagingData.getITotalRecords()!=0){
			Object[] objArr=pagingData.getAaData();
			for (int i = 0; i < objArr.length; i++) {
				Tproduct product=(Tproduct)objArr[i];
				if(product.getOldPrice()==null){
					float price=(float) 0.0;
					product.setOldPrice(price);
				}
				if(product.getSort()==null){
					product.setSort(0);
				}
				objArr[i]=product;		
			}
			pagingData.setAaData(objArr);
		}
		
		/*if(pagingData.getAaData()==null){
			Object[] objs=new Object[]{};
			pagingData.setAaData(objs);
		}*/
		pagingData.setSEcho(dtp.sEcho);
		String rightsListJson= JSON.toJSONString(pagingData);
		return rightsListJson;	
	}
	@RequestMapping(value="addgoods",method=RequestMethod.GET)
	@ResponseBody
	public ModelAndView addGoodsPage(HttpServletRequest request){
		imgIndex=0;
		filesMap.clear();
		ModelAndView mav=new ModelAndView();
		String local=getLocale(request);
		Tlanguage language=languageService.getLanguageBylocal(local);
		Object string=request.getSession().getAttribute("adderrorMsg");
		List<Tcategory> ordercategoryList=categoryService.getallCategory(1,language);
		List<Tcategory> speccategoryList=categoryService.getallCategory(0,language);
		Map<Integer, String> ordercategoryMap = new HashMap<Integer, String>();  
		Map<Integer, String> speccategoryMap = new HashMap<Integer, String>();
		for (Tcategory tcategory : ordercategoryList) {
			ordercategoryMap.put(tcategory.getCategoryId(), tcategory.getName());
		}
		for(Tcategory tcategory : speccategoryList){
			speccategoryMap.put(tcategory.getCategoryId(), tcategory.getName());
		}
		
		List<MenuModel> menus=menuService.getNoChildrenMenus(language);
		List<Tlanguage> languages = languageService.loadAllTlanguage();
		if(string!=null){
			mav.addObject("errorMsg", string);
		}
		mav.addObject("lanList", languages);
		mav.addObject("ordercategory", ordercategoryMap);
		mav.addObject("speccategory", speccategoryMap);
		mav.addObject("menu", menus);
		mav.addObject("product", new AddProductModel());
		request.getSession().setAttribute("adderrorMsg",null);
		mav.setViewName("goods/addgoods");
		return mav;
		
	}
	@RequestMapping(value="/deletegoods/{ids}",method=RequestMethod.DELETE)
	@ResponseBody
	public String deleteGoods(@PathVariable String ids,HttpServletRequest request){
		String[] idstrArr=ids.split(",");		
		Integer[] idArr=ConvertTools.stringArr2IntArr(idstrArr);		
		JSONObject respJson = new JSONObject();
		try{
			goodsService.deletegoodsByids(idArr);
			//goodsService.deleteLanguageByIds(idArr);
			respJson.put("status", true);
		}
		catch(MposException be){
			respJson.put("status", false);
			respJson.put("info", getMessage(request,be.getErrorID(),be.getMessage()));
		}	
		return JSON.toJSONString(respJson);	
	}
	@RequestMapping(value="/activegoods/{ids}",method=RequestMethod.POST)
	@ResponseBody
	public String activegood(@PathVariable String ids,HttpServletRequest request){
		String[] idstrArr=ids.split(",");		
		Integer[] idArr=ConvertTools.stringArr2IntArr(idstrArr);		
		JSONObject respJson = new JSONObject();
	
		try{
			goodsService.activegoodsByids(idArr);
			//goodsService.deleteLanguageByIds(idArr);
			respJson.put("status", true);
		}
		catch(MposException be){
			respJson.put("status", false);
			respJson.put("info", getMessage(request,be.getErrorID(),be.getMessage()));
		}	
		return JSON.toJSONString(respJson);		
	}
	
	@RequestMapping(value="/getAttributesGroupById/{ids}",method=RequestMethod.GET)
	@ResponseBody
	public String getAttributesGroup(@PathVariable String ids,HttpServletRequest request){			
		JSONObject respJson = new JSONObject();
		String[] idstrArr=ids.split(",");		
		Integer[] idArr=ConvertTools.stringArr2IntArr(idstrArr);
		List<CategoryAttributeModel>  categoryAttributeModels=new ArrayList<CategoryAttributeModel>();
		String local=getLocale(request);
		Tlanguage language=languageService.getLanguageBylocal(local);
		try {
			List<TcategoryAttribute> categoryAttributelist=CategoryAttributeService.getCategoryAttributeByCategoryid(idArr[0],language);
			for(int i=0;i<categoryAttributelist.size();i++){
				CategoryAttributeModel categoryAttributeModel=new CategoryAttributeModel();
				categoryAttributeModel.setAttributeId(categoryAttributelist.get(i).getAttributeId());
				categoryAttributeModel.setCategoryId(categoryAttributelist.get(i).getCategoryId().getCategoryId());
				categoryAttributeModel.setRequired(categoryAttributelist.get(i).getRequired());
				categoryAttributeModel.setSort(categoryAttributelist.get(i).getSort());
				categoryAttributeModel.setTitle(categoryAttributelist.get(i).getTitle());
				categoryAttributeModel.setType(categoryAttributelist.get(i).getType());
				TproductAttribute productAttribute=productAttributeService.getAttributeByproductidAndattributeid(idArr[1], categoryAttributelist.get(i).getAttributeId());
				categoryAttributeModel.setProductAttribute(productAttribute);
				List<TattributeValue> attributeValuelist=attributeValueService.getattributeValuesbyattributeid(categoryAttributelist.get(i).getAttributeId(),language);
				categoryAttributeModel.setAttributeValue(attributeValuelist);
				categoryAttributeModels.add(categoryAttributeModel);
			}
			respJson.put("status", true);
			respJson.put("list", categoryAttributeModels);
			SystemConfig.product_AttributeModel_Map.clear();
		}
		catch(MposException be){
			respJson.put("status", false);
			respJson.put("info", getMessage(request,be.getErrorID(),be.getMessage()));
		}
		return JSON.toJSONString(respJson);
	}

	@RequestMapping(value="/getAttributesGroupByid/{id}",method=RequestMethod.GET)
	@ResponseBody
	public String getAttributesGroup(@PathVariable Integer id,HttpServletRequest request){			
		JSONObject respJson = new JSONObject();
		List<CategoryAttributeModel>  categoryAttributeModels=new ArrayList<CategoryAttributeModel>();
		String local=getLocale(request);
		Tlanguage language=languageService.getLanguageBylocal(local);
		try {
			List<TcategoryAttribute> categoryAttributelist=CategoryAttributeService.getCategoryAttributeByCategoryid(id,language);
			for(int i=0;i<categoryAttributelist.size();i++){
				CategoryAttributeModel categoryAttributeModel=new CategoryAttributeModel();
				categoryAttributeModel.setAttributeId(categoryAttributelist.get(i).getAttributeId());
				categoryAttributeModel.setCategoryId(categoryAttributelist.get(i).getCategoryId().getCategoryId());
				categoryAttributeModel.setRequired(categoryAttributelist.get(i).getRequired());
				categoryAttributeModel.setSort(categoryAttributelist.get(i).getSort());
				categoryAttributeModel.setTitle(categoryAttributelist.get(i).getTitle());
				categoryAttributeModel.setType(categoryAttributelist.get(i).getType());
				List<TattributeValue> attributeValuelist=attributeValueService.getattributeValuesbyattributeid(categoryAttributelist.get(i).getAttributeId(),language);
				categoryAttributeModel.setAttributeValue(attributeValuelist);
				categoryAttributeModels.add(categoryAttributeModel);
			}
			respJson.put("status", true);
			respJson.put("list", categoryAttributeModels);
			SystemConfig.product_AttributeModel_Map.clear();
		}
		catch(MposException be){
			respJson.put("status", false);
			respJson.put("info", getMessage(request,be.getErrorID(),be.getMessage()));
		}
		return JSON.toJSONString(respJson);
	}
	@RequestMapping(value="/getcategoryattribbyid/{ids}",method=RequestMethod.POST)
	@ResponseBody
	public String getgoodscategoryAttribute(@PathVariable String ids,HttpServletRequest request){
		Integer id=Integer.parseInt(ids);	
		JSONObject respJson = new JSONObject();
		try {
		//Tcategory category=categoryService.getCategory(id);
		TcategoryAttribute list=CategoryAttributeService.getCategoryAttribute(id);
		
		respJson.put("status", true);
		respJson.put("list", list);
		}
		catch(MposException be){
			respJson.put("status", false);
			respJson.put("info", getMessage(request,be.getErrorID(),be.getMessage()));
		}
		return JSON.toJSONString(respJson);
	}
	@RequestMapping(value="/setgoods",method=RequestMethod.POST)
	@ResponseBody
	public ModelAndView addGoods(HttpServletRequest request,@ModelAttribute("product") AddProductModel model){
		try{
			goodsService.createproduct(model, filesMap, request);
			
/*		Tproduct product=new Tproduct();
		product.setProductName(model.getProductName());
		product.setShortDescr(model.getShortDescr());
		product.setFullDescr(model.getFullDescr());
		product.setPrice(model.getPrice());
		product.setOldPrice(model.getOldPrice());		
		product.setUnitName(model.getUnitName());
		product.setRecommend(model.isRecommend());
		product.setSku(model.getSku());
		product.setSort(model.getSort());
		product.setStatus(true);		
		product.setTmenu(model.getMenu());
		if(model.getAttributeGroup().getCategoryId()!=0){
			product.setTcategory(model.getAttributeGroup());
		}
		try {
			//Save product basic information			
			goodsService.createGoods(product);
			
			//Save the product attribute
			List<TgoodsAttribute> productAttributesList=model.getAttributes();
			for (TgoodsAttribute goodsAttribute : productAttributesList) {
				goodsAttribute.setProductId(product.getId());
				goodsAttributeService.createProductAttribute(goodsAttribute);
			}
			
			//Set the product fields language model
			List<TlocalizedField> productNameLocaleList=model.getProductName_locale();
			List<TlocalizedField> shortDescrLocaleList=model.getShortDescr_locale();
			List<TlocalizedField> fullDescrLocaleList=model.getFullDescr_locale();
			List<TlocalizedField> unitNameLocaleList=model.getUnitName_locale();
			//Save product language information
			for (TlocalizedField localizedField : productNameLocaleList) {
				if(localizedField.getLocaleValue()!=null&&!localizedField.getLocaleValue().isEmpty()){
					localizedField.setEntityId(product.getId());
					localizedField.setTableName("Tproduct");
					localizedField.setTableField("productName");
					localizedFieldService.createLocalizedField(localizedField);
				}				
			}
			for (TlocalizedField localizedField : shortDescrLocaleList) {
				if(localizedField.getLocaleValue()!=null&&!localizedField.getLocaleValue().isEmpty()){
					localizedField.setEntityId(product.getId());
					localizedField.setTableName("Tproduct");
					localizedField.setTableField("shortDescr");
					localizedFieldService.createLocalizedField(localizedField);
				}
			}
			for (TlocalizedField localizedField : fullDescrLocaleList) {
				if(localizedField.getLocaleValue()!=null&&!localizedField.getLocaleValue().isEmpty()){
					localizedField.setEntityId(product.getId());
					localizedField.setTableName("Tproduct");
					localizedField.setTableField("fullDescr");
					localizedFieldService.createLocalizedField(localizedField);
				}
			}
			for (TlocalizedField localizedField : unitNameLocaleList) {
				if(localizedField.getLocaleValue()!=null&&!localizedField.getLocaleValue().isEmpty()){
					localizedField.setEntityId(product.getId());
					localizedField.setTableName("Tproduct");
					localizedField.setTableField("unitName");
					localizedFieldService.createLocalizedField(localizedField);
				}
			}						
			
			//Save product images
			Set<Integer> keys=filesMap.keySet();
			int i=0;
			for (Integer key : keys) {
				FileMeta fileMeta=filesMap.get(key);
				TproductImage productImage=new TproductImage();
				productImage.setProduct(product);
				productImage.setImage(fileMeta.getBytes());
				productImage.setImageSuffix(fileMeta.getSuffix());
				String filename=product.getId()+"_"+i+"."+fileMeta.getSuffix();
				String filePath=request.getSession().getServletContext().getRealPath("/")+File.separator+"upload"+File.separator+"product"+File.separator+filename;
                //String fileUrl=request.getContextPath()+"/goods/getCachedImg/"+imgIndex;
            	String fileUrl=request.getContextPath()+"/upload/product/"+filename;
                FileCopyUtils.copy(fileMeta.getBytes(), new FileOutputStream(filePath));
				productImage.setImageUrl(fileUrl);
				i++;
				goodsImageService.CreateImages(productImage);
			}
			filesMap.clear();*/			
			return new ModelAndView("redirect:/goods");
		} catch (MposException  e) {
			ModelAndView mav=new ModelAndView();
			/*mav.addObject("errorMsg", e.getMessage());*/
			request.getSession().setAttribute("adderrorMsg", getMessage(request,e.getErrorID(),e.getMessage()));
			mav.setViewName("redirect:/goods/addgoods");
			return mav;
		}							
	}
	@RequestMapping(value="/addattributes",method=RequestMethod.POST)
	@ResponseBody
	public String test(HttpServletRequest request,AddAttributevaleModel attributeModel){
		JSONObject respJson = new JSONObject();
		try {
			if(attributeModel.getContent()!=null){
		productAttributeService.cachedSystemSettingData(attributeModel);
		respJson.put("status", true);
		respJson.put("attributeModel", attributeModel);
		}else{
		productAttributeService.cachedSystemclearData(attributeModel);
		respJson.put("status", true);
		respJson.put("attributeModel", attributeModel);
		}
			
		}catch(MposException be){
			respJson.put("status", false);
			respJson.put("info", getMessage(request,be.getErrorID(),be.getMessage()));
		}
		return JSON.toJSONString(respJson);
	}
	/*
	@RequestMapping(value="/editgoods/{ids}",method=RequestMethod.GET)
	
	public ModelAndView eidtgoods(@PathVariable String ids,HttpServletRequest request){
		Integer id=Integer.parseInt(ids);
		ModelAndView mav=new ModelAndView();
		JSONObject respJson = new JSONObject();
		mav.setViewName("goods/editgoods");
		try {
			Tproduct product=goodsService.getTproductByid(id);
			Tcategory tcategory=product.getTcategory();
			String string=tcategory.getContent();
			request.setAttribute("product", product);
			respJson.put("status", true);
		} catch (MposException be ) {	
			
		}
		return mav;
	}
	*/
	
	@RequestMapping(value="/uploadImages",method=RequestMethod.POST)
	@ResponseBody
	public String uploadImages(MultipartHttpServletRequest request){		
		
        Iterator<String> itr =  request.getFileNames();
        MultipartFile mpf = null;
		JSONObject respJson = new JSONObject();
		JSONArray jsonArray=new JSONArray();  
		
        //Get each file		
        while(itr.hasNext()){
        	JSONObject jsonObj = new JSONObject();
            //Get next MultipartFile        	
            mpf = request.getFile(itr.next());                      
            //String fileName=this.getSessionUser(request).getAdminId()+mpf.getOriginalFilename();
            String originalName=mpf.getOriginalFilename();
            String[] strArr=originalName.split("[.]");
            String fileName=this.getSessionUser(request).getAdminId()+"_"+imgIndex+"."+strArr[strArr.length-1];
            String fileSize=mpf.getSize()/1024+" Kb";            
            FileMeta filemeta=new FileMeta();
        	filemeta.setFileName(fileName);
        	filemeta.setFileSize(mpf.getSize()/1024+" Kb");
        	filemeta.setFileType(mpf.getContentType());
        	filemeta.setSuffix(strArr[strArr.length-1]);
        	jsonObj.put("id",imgIndex);
        	jsonObj.put("fileName",mpf.getOriginalFilename());
        	jsonObj.put("fileSize",fileSize); 	
        		
        	//Save file to cache     	
            try {
            	filemeta.setBytes(mpf.getBytes());
            	String filePath=request.getSession().getServletContext().getRealPath("/")+File.separator+"upload"+File.separator+"temp"+File.separator+fileName;
                //String fileUrl=request.getContextPath()+"/goods/getCachedImg/"+imgIndex;
            	String fileUrl=request.getContextPath()+"/upload/temp/"+fileName;
                FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(filePath));
                filemeta.setUrl(fileUrl);                
                //filemeta.setThumbnailUrl(request.getContextPath()+"/static/upload/"+fileName); 
                jsonObj.put("url",fileUrl);
                jsonArray.add(jsonObj);
                filesMap.put(imgIndex,filemeta);
           } catch (IOException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
           } 
           imgIndex++;
        }
        respJson.put("files", jsonArray);
        respJson.put("status", true);
        respJson.put("info", "OK");
        return JSON.toJSONString(respJson);		
	}
	
	@RequestMapping(value="/deleteImage/{id}",method=RequestMethod.GET)
	@ResponseBody
	public String deleteImage(@PathVariable int id,HttpServletRequest request){	
		FileMeta fileMeta = filesMap.get(id);
		String filename=fileMeta.getFileName();
		filesMap.remove(id);
		JSONObject respJson = new JSONObject();               	        	                
    	//Delete file        	                      
    	String filePath=request.getSession().getServletContext().getRealPath("/")+File.separator+"upload"+File.separator+"temp"+File.separator+filename;
        File file=new File(filePath);
        file.deleteOnExit();            
                                   
        respJson.put("status", true);
        respJson.put("info", "OK");
        return JSON.toJSONString(respJson);		
	}
	
	@RequestMapping(value = "/getCachedImg/{id}", method = RequestMethod.GET)
    public void getCachedImg(HttpServletResponse response,@PathVariable int id){
        FileMeta fileMeta = filesMap.get(id);        
        try {      
               response.setContentType(fileMeta.getFileType());
               response.setHeader("Content-disposition", "attachment; filename=\""+fileMeta.getFileName()+"\"");
               FileCopyUtils.copy(fileMeta.getBytes(), response.getOutputStream());
        }catch (IOException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
        }
    }

}
