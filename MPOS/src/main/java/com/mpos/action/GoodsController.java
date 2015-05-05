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
import com.mpos.commons.LogManageTools;
import com.mpos.commons.MposException;
import com.mpos.commons.SystemConfig;
import com.mpos.dto.TattributeValue;
import com.mpos.dto.Tcategory;
import com.mpos.dto.TcategoryAttribute;
import com.mpos.dto.Tlanguage;
import com.mpos.dto.Tproduct;
import com.mpos.dto.TproductAttribute;
import com.mpos.dto.Tstore;
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
import com.mpos.service.StoreService;

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
	@Autowired
	private StoreService storeService;
	
	/**
	 * 操作内容
	 */
	private String handleContent = "";
	/**
	 * 日志级别
	 */
	private short level = LogManageTools.NOR_LEVEL;
	
	private LinkedHashMap<Integer,FileMeta> filesMap = new LinkedHashMap<Integer,FileMeta>();
	private int imgIndex=0;	
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView Goods(HttpServletRequest request){
		ModelAndView mav=new ModelAndView();
		List<Tcategory> categorys=categoryService.getallCategory(getSessionUser(request).getStoreId());
		Object successstring=request.getSession().getAttribute("addsussess");
		if(successstring!=null){
			mav.addObject("Msg", successstring);
		}
		String local=getLocale(request);
		Tlanguage language=languageService.getLanguageBylocal(local);
		List<MenuModel> menus=menuService.getNoChildrenMenus(language,getSessionUser(request).getStoreId());
	//	List<Tmenu> menus=menuService.getAllMenu();
		request.getSession().setAttribute("addsussess", null);
		String hql = "select new Tstore(storeId,storeName) from Tstore";
		List<Tstore> stores = storeService.select(hql, null);
		mav.addObject("role", getSessionUser(request).getAdminRole().getRoleId());
		mav.addObject("stores",stores);
		mav.addObject("category", categorys);
		mav.addObject("menu", menus);
		mav.setViewName("goods/goods");
		return mav;
	}
	@SuppressWarnings("unused")
	@RequestMapping(value="/goodslist",method=RequestMethod.GET)
	@ResponseBody
	public String goodsList(HttpServletRequest request,DataTableParamter dtp){
		addStoreCondition(request, dtp);
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
	public ModelAndView addGoodsPage(HttpServletRequest request,Integer storeId){
		imgIndex=0;
		filesMap.clear();
		ModelAndView mav=new ModelAndView();
		String local=getLocale(request);
		Tlanguage language=languageService.getLanguageBylocal(local);
		if(storeId == null || storeId ==-1){
			 storeId = getSessionUser(request).getStoreId();
		}
		Object errorstring=request.getSession().getAttribute("adderrorMsg");
		List<Tcategory> ordercategoryList=categoryService.getallCategory(1,language,storeId);
		List<Tcategory> speccategoryList=categoryService.getallCategory(0,language,storeId);
		Map<Integer, String> ordercategoryMap = new HashMap<Integer, String>();  
		Map<Integer, String> speccategoryMap = new HashMap<Integer, String>();
		for (Tcategory tcategory : ordercategoryList) {
			ordercategoryMap.put(tcategory.getCategoryId(), tcategory.getName());
		}
		for(Tcategory tcategory : speccategoryList){
			speccategoryMap.put(tcategory.getCategoryId(), tcategory.getName());
		}
		
		List<MenuModel> menus=menuService.getNoChildrenMenus(language,getSessionUser(request).getStoreId());
		List<Tlanguage> languages = languageService.getLangListByStoreId(getSessionUser(request).getStoreId());
		if(errorstring!=null){
			mav.addObject("Msg", errorstring);
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
	
	@RequestMapping(value="/putgoods/{ids}",method=RequestMethod.GET)
	@ResponseBody
	public String putGoods(@PathVariable String ids,HttpServletRequest request,Integer type){
		String[] idstrArr=ids.split(",");		
		Integer[] idArr=ConvertTools.stringArr2IntArr(idstrArr);		
		JSONObject respJson = new JSONObject();
		try{
			Integer storeId = getSessionStoreId(request);
			if(type==0){
				goodsService.outGoods(ids, storeId);
			}else{
				goodsService.putGoods(ids, storeId);
			}
			handleContent = "操作商品成功;操作ID为:"+idArr.toString();
			respJson.put("status", true);
			respJson.put("info", getMessage(request,"operate.success"));
		}
		catch(MposException be){
			respJson.put("status", false);
			respJson.put("info", getMessage(request,be.getErrorID(),be.getMessage()));
			handleContent = "操作商品失败;操作ID为:"+idArr.toString();
			level = LogManageTools.FAIL_LEVEL;
		}	
		LogManageTools.writeAdminLog(handleContent,level, request);
		return JSON.toJSONString(respJson);	
	}
	
	@RequestMapping(value="/deletegoods/{ids}",method=RequestMethod.GET)
	@ResponseBody
	public String deleteGoods(@PathVariable String ids,HttpServletRequest request){
		String[] idstrArr=ids.split(",");		
		Integer[] idArr=ConvertTools.stringArr2IntArr(idstrArr);		
		JSONObject respJson = new JSONObject();
		try{
			Integer storeId = getSessionStoreId(request);
			goodsService.deletegoodsByids(idArr,storeId);
			//goodsService.deleteLanguageByIds(idArr);
			handleContent = "删除商品成功;操作ID为:"+idArr.toString();
			respJson.put("status", true);
			respJson.put("info", getMessage(request,"operate.success"));
		}
		catch(MposException be){
			respJson.put("status", false);
			respJson.put("info", getMessage(request,be.getErrorID(),be.getMessage()));
			handleContent = "删除商品失败;操作ID为:"+idArr.toString();
			level = LogManageTools.FAIL_LEVEL;
		}	
		LogManageTools.writeAdminLog(handleContent,level, request);
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
	public ModelAndView addGoods(HttpServletRequest request,@ModelAttribute("product") AddProductModel model,Integer storeId){
		ModelAndView mav=new ModelAndView();
		try{
			addStore(model, request, storeId);
			goodsService.createproduct(model, filesMap, request);
			handleContent = "添加商品:"+model.getProductName()+"成功";
			request.getSession().setAttribute("addsussess","operate.success");
			mav.setViewName("redirect:/goods");
		} catch (MposException  e) {
		
			/*mav.addObject("errorMsg", e.getMessage());*/
			handleContent = "添加商品:"+model.getProductName()+"失败";
			level = LogManageTools.FAIL_LEVEL;
			
			request.getSession().setAttribute("adderrorMsg", getMessage(request,e.getErrorID(),e.getMessage()));
			mav.setViewName("redirect:/goods/addgoods");
		}	
		LogManageTools.writeAdminLog(handleContent,level, request);
		return mav;
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
