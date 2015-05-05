package com.mpos.action;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.mpos.dto.Tcategory;
import com.mpos.dto.Tlanguage;
import com.mpos.dto.TlocalizedField;
import com.mpos.dto.Tproduct;
import com.mpos.dto.TproductAttribute;
import com.mpos.dto.TproductImage;
import com.mpos.model.AddProductModel;
import com.mpos.model.FileMeta;
import com.mpos.model.MenuModel;
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
public class EditgoodsController extends BaseController{
@Autowired
private GoodsService goodsService;


@Autowired
private ProductAttributeService productAttributeService;

@Autowired
private CategoryAttributeService CategoryAttributeService;

@Autowired
private MenuService menuService;

@Autowired
private CategoryService categoryService;

@Autowired
private GoodsImageService goodsImageService;

@Autowired
private LanguageService languageService;

@Autowired
private GoodsAttributeService goodsAttributeService;

@Autowired
private ProductReleaseService productReleaseService;

@Autowired
private LocalizedFieldService localizedFieldService;

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

	@SuppressWarnings("unused")
	@RequestMapping(value="/editgoods/{ids}",method=RequestMethod.GET)
	public ModelAndView eidtgoods(@PathVariable String ids,HttpServletRequest request,Integer storeId){
		imgIndex=0;
		filesMap.clear();
		Integer id=Integer.parseInt(ids);
		JSONObject respJson = new JSONObject();
		ModelAndView mav=new ModelAndView();
		try {
			Tproduct product=goodsService.getTproductByid(id);
			String local=getLocale(request);
			Tlanguage language=languageService.getLanguageBylocal(local);
			 List<TlocalizedField> productName_locale=new ArrayList<TlocalizedField>();
			 List<TlocalizedField> shortDescr_locale=new ArrayList<TlocalizedField>();
			 List<TlocalizedField> fullDescr_locale=new ArrayList<TlocalizedField>();
			 List<TlocalizedField> unitName_locale=new ArrayList<TlocalizedField>();
			 if(storeId==null||storeId==-1){
				 storeId = getSessionUser(request).getStoreId();
			 }
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
				mav.addObject("ordercategory", ordercategoryMap);
				mav.addObject("speccategory", speccategoryMap);
			//List<Tmenu> menus=menuService.getAllMenu();
				
			Map<Integer, String> menusMap = new HashMap<Integer, String>(); 
			List<MenuModel> menus=menuService.getNoChildrenMenus(language,getSessionUser(request).getStoreId());
		
			for (MenuModel menu : menus) {
				menusMap.put(menu.getId(), menu.getTitle());
			}
			mav.addObject("menu", menusMap);
			AddProductModel Productmodel=new AddProductModel();
			Productmodel.setFullDescr(product.getFullDescr());
			Productmodel.setMenu(product.getTmenu());
			Productmodel.setOldPrice(product.getOldPrice());
			Productmodel.setAttributeGroup(product.getTcategory());
			Productmodel.setPrice(product.getPrice());
			Productmodel.setShortDescr(product.getShortDescr());
			Productmodel.setSort(product.getSort());
			Productmodel.setSku(product.getSku());
			Productmodel.setProductName(product.getProductName());
			Productmodel.setUnitName(product.getUnitName());
			Productmodel.setMenu(product.getTmenu());
			Productmodel.setRecommend(product.isRecommend());
			Productmodel.setProductId(product.getId());
			Productmodel.setSpecid(product.getSpecid());
			Productmodel.setStoreId(product.getStoreId());
			Productmodel.setIsPut(product.getIsPut());
			productName_locale=localizedFieldService.getLocalizedField(product.getId(),Tproduct.class.getSimpleName() , "productName");
			shortDescr_locale=localizedFieldService.getLocalizedField(product.getId(),Tproduct.class.getSimpleName() , "shortDescr");
			fullDescr_locale=localizedFieldService.getLocalizedField(product.getId(),Tproduct.class.getSimpleName() , "fullDescr");
			unitName_locale=localizedFieldService.getLocalizedField(product.getId(),Tproduct.class.getSimpleName() , "unitName");
			Productmodel.setProductName_locale(productName_locale);
			Productmodel.setFullDescr_locale(fullDescr_locale);
			Productmodel.setShortDescr_locale(shortDescr_locale);
			Productmodel.setUnitName_locale(unitName_locale);
			Object string=request.getSession().getAttribute("editerrorMsg");
			if (string!=null) {
				mav.addObject("Msg", string);
				
			}
			request.getSession().setAttribute("editerrorMsg", null);
			//respJson.put("list", LocalizedField.setValues(list));
			List<Tlanguage> languages = languageService.loadAllTlanguage();
			mav.addObject("lanList", languages);
			//mav.addObject("menu", menus);
/*			List<TproductImage> productImage=goodsImageService.getByProductid(product.getId());
			if (productImage.size()>0) {
				mav.addObject("productImage",productImage.get(0));
			}
			if(product.getTcategory()!=null){
			categoryAttribute=CategoryAttributeService.getCategoryAttributeByCategoryid(product.getTcategory().getCategoryId());
			for (int i = 0; i < categoryAttribute.size(); i++) {
				TproductAttributeId productAttributeId=new TproductAttributeId();
				AddAttributevaleModel model=new AddAttributevaleModel();
				productAttributeId.setCategoryAttribute(categoryAttribute.get(i));
				productAttributeId.setProduct(product);
				TproductAttribute productAttribute=productAttributeService.getAttributes(productAttributeId);
				if (productAttribute!=null) {
					model.setAttributeId(categoryAttribute.get(i).getAttributeId());
					model.setContent(productAttribute.getContent());
//					model.setPrice(productAttribute.getPrice());
					model.setTitle(categoryAttribute.get(i).getTitle());
					addAttributevalemodels.add(model);
				}
				
			}
			}
			mav.addObject("categoryAttribute",categoryAttribute);
			mav.addObject("addAttributevalemodels", addAttributevalemodels);
			*/
			
			mav.addObject("product", Productmodel);
			mav.setViewName("goods/editgoods");
			
		} catch (MposException be ) {	
			
		}
		
		return mav;
	}
/*	@RequestMapping(value="/editgoods/editgoods",method=RequestMethod.POST)
	@ResponseBody
	public ModelAndView editGoods(HttpServletRequest request,AddGoodsModel model,AddgoodsLocal value,
			@RequestParam(value = "files", required = false) MultipartFile[] file)throws IOException{
		Tproduct product=new Tproduct();
		JSONObject respJson = new JSONObject();
		product.setId(model.getPorductid());
		List<TproductAttribute> tproductAttributelist=new ArrayList<TproductAttribute>(); 
		product.setShortDescr(model.getShortDescr());
		product.setFullDescr(model.getFullDescr());
		product.setPrice(model.getPrice());
		product.setOldPrice(model.getOldPrice());
		product.setProductName(model.getProductName());
		product.setUnitName(model.getUnitName());
		product.setStatus(true);
		Tcategory catefory=categoryService.getCategory(model.getCategoryId());
		product.setTcategory(catefory);
		Tmenu menu=menuService.getMenu(model.getMenuId());
		product.setTmenu(menu);
		product.setRecommend(model.isRecommend());
		product.setSort(model.getSort());
		try {
			
		try {
			goodsService.updateGoods(product);
			productReleaseService.createOrupdateProductRelease(product.getId());
			localizedFieldService.updateLocalizedFieldList(value.setValue(product));
		} catch (MposException be) {
			
		}
		
		Iterator it = SystemConfig.product_AttributeModel_Map.keySet().iterator(); 
		   while (it.hasNext()){ 
		    String key; 
		    key=(String)it.next(); 
		    AddAttributevaleModel models= SystemConfig.product_AttributeModel_Map.get(key);
		    TcategoryAttribute categoryAttribute=CategoryAttributeService.getCategoryAttribute(models.getAttributeId());
		    TproductAttribute tproductAttribute=new TproductAttribute();
		    tproductAttribute.setContent(models.getContent());
//		    tproductAttribute.setPrice(models.getPrice());
		    TproductAttributeId productAttributeid=new TproductAttributeId();
		    productAttributeid.setCategoryAttribute(categoryAttribute);
		    productAttributeid.setProduct(product);
		    tproductAttribute.setId(productAttributeid);
		    tproductAttributelist.add(tproductAttribute);
		   } 
		   for (int i = 0; i < tproductAttributelist.size(); i++) {
			   try {
				   productAttributeService.updattProductAttribute(tproductAttributelist.get(i));;
				   
			} catch (MposException be) {
					
			}
			
		}
		SystemConfig.product_AttributeModel_Map.clear();
		for(int i=0;i<file.length;i++){
			if (!(file[i].isEmpty())) {
				TproductImage productImage=new TproductImage();
				InputStream inputStream = file[i].getInputStream();
				byte [] image=new byte[1048576];
				inputStream.read(image);
				String filename=file[i].getOriginalFilename();
				String s[]=filename.split("\\.");
				productImage.setImageSuffix(s[s.length-1]);
				productImage.setImage(image);
				productImage.setProduct(product);
				try {
				//	goodsImageService.CreateImages(productImage);
					//File file2=new File(request.getSession().getServletContext().getRealPath("/")+File.separator+"static"+File.separator+"upload"+File.separator+productImage.getId()+"."+productImage.getImageSuffix());
					File file2=new File(request.getSession().getServletContext().getRealPath("/")+File.separator+"static"+File.separator+"upload"
											+File.separator+productImage.getId()+"."+productImage.getImageSuffix());
											//+File.separator+productImage.getId()+"."+productImage.getImageSuffix());
					File file3=new File(request.getSession().getServletContext().getRealPath("/")+File.separator+"static"+File.separator+"upload"+File.separator+"23.jpg");
					if(file3.exists()){
					ImageOutputStream ios= ImageIO.createImageOutputStream(file3);
					ios.write(image);
					String path="static/upload/"
							+productImage.getId()+"."+productImage.getImageSuffix();
					productImage.setImageUrl(path);
					goodsImageService.updeteImages(productImage);
					}
				} catch (MposException be) {
					System.out.print(getMessage(request,be.getErrorID(),be.getMessage()));
					
				}
			}
		}
		respJson.put("status", true);
		}catch(MposException be){
			
		}	
		return new ModelAndView("redirect:/goods");
		
	}*/
	@RequestMapping(value="editgoods/getAttributes/{ids}",method=RequestMethod.GET)
	@ResponseBody
	public String getattributes(HttpServletRequest request,@PathVariable String ids){
		String[] idstrArr=ids.split(",");		
		Integer[] idArr=ConvertTools.stringArr2IntArr(idstrArr);
		
		JSONObject respJson = new JSONObject();
		try {
			//TproductAttribute productAttribute=goodsAttributeService.getTproductAttribute(idArr[0], idArr[1]);
			List<TproductAttribute> list=goodsAttributeService.getTproductAttribute(idArr[0]);
			respJson.put("status", true);
			respJson.put("productAttribute", list);
		}
		catch(MposException be){
			respJson.put("status", false);
			respJson.put("info", getMessage(request,be.getErrorID(),be.getMessage()));
		}
		return JSON.toJSONString(respJson);
	}
	
	
	@RequestMapping(value="/editgoods/editgoods",method=RequestMethod.POST)
	@ResponseBody
	public ModelAndView editGoods(HttpServletRequest request,@ModelAttribute("product") AddProductModel model,Integer storeId){
		ModelAndView mav=new ModelAndView();
		try{
			addStore(model, request, storeId);
			goodsService.updateproduct(model, filesMap, request);
			handleContent = "修改商品:"+model.getProductName()+"成功;";
			request.getSession().setAttribute("addsussess","operate.success");
			mav.setViewName("redirect:/goods");
			//return new ModelAndView("redirect:/goods");
		} catch (MposException  e) {
			e.printStackTrace();
		/*	mav.addObject("errorMsg", e.getMessage());*/
			handleContent = "修改商品:"+model.getProductName()+"失败;";
			level = LogManageTools.FAIL_LEVEL;
			request.getSession().setAttribute("editerrorMsg", getMessage(request,e.getErrorID(),e.getMessage()));
			mav.setViewName("redirect:/editgoods/"+model.getProductId());
		}		
		LogManageTools.writeAdminLog(handleContent,level, request);
		return mav;
		}
	@RequestMapping(value="/editgoods/getImages/{ids}",method=RequestMethod.POST)
	@ResponseBody
	public String getproductImages(@PathVariable String ids,HttpServletRequest request){
		List<TproductImage> list= new ArrayList<TproductImage>();
		Integer id=Integer.parseInt(ids);
		//JSONObject jsonObj = new JSONObject();
		JSONObject respJson = new JSONObject();
		JSONArray jsonArray=new JSONArray();
		list=goodsImageService.getByProductid(id);
		
		for (int i = 0; i < list.size(); i++) {
			JSONObject jsonObj = new JSONObject();
			FileMeta filemeta=new FileMeta();
			String string=list.get(i).getImageUrl();
			String fileName=string.split("/")[3];
			File file=new File(request.getSession().getServletContext().getRealPath("/")+File.separator+"upload"+File.separator+"product"+File.separator+fileName);
			String s[]=fileName.split("\\.");
			filemeta.setBytes(list.get(i).getImage());
			filemeta.setFileId(list.get(i).getId());
			filemeta.setSuffix(s[s.length-1]);
			filemeta.setUrl(request.getSession().getServletContext().getRealPath("/")+File.separator+"upload"+File.separator+"product"+File.separator+fileName);
			jsonObj.put("id",imgIndex);
	    	jsonObj.put("fileName",fileName);
	    	jsonObj.put("fileSize", list.get(i).getImage().length/1024+"kb");
	    	jsonObj.put("url", list.get(i).getImageUrl());
	    	//jsonObj.put("fileSize",fileSize);
	    	File filemkdir=new File(request.getSession().getServletContext().getRealPath("/")+File.separator+"upload"+File.separator+"product");
        	
    		if (!filemkdir.isDirectory()) {
    			filemkdir.mkdirs();
    		}
	    	if(!file.exists()){
	    		 try {
						FileCopyUtils.copy(filemeta.getBytes(), file);
					} catch (FileNotFoundException e) {
						
						e.printStackTrace();
					} catch (IOException e) {
						
						e.printStackTrace();
					}
	    	}
	    	jsonArray.add(jsonObj);
	    	filesMap.put(imgIndex,filemeta);
	    
	    	imgIndex++;
		}
			respJson.put("files", jsonArray);
	        respJson.put("status", true);
	        respJson.put("info", "OK");
	        return JSON.toJSONString(respJson);	
	}
	@RequestMapping(value="/editgoods/deleteImage/{ids}",method=RequestMethod.GET)
	@ResponseBody
	public String deleteImage(@PathVariable Integer ids,HttpServletRequest request){
		
		FileMeta fileMeta = filesMap.get(ids);
		filesMap.remove(ids);
		JSONObject respJson = new JSONObject();
		if (fileMeta.getFileId()!=null) {
			//Delete old image
			File file=new File(fileMeta.getUrl());
			file.delete();
			goodsImageService.deleteImagebyid(fileMeta.getFileId());
			 respJson.put("status", true);
		     respJson.put("info", "OK");
		}else{
		String filename=fileMeta.getFileName();            	        	                
    	//Delete file        	                      
    	String filePath=request.getSession().getServletContext().getRealPath("/")+File.separator+"upload"+File.separator+"temp"+File.separator+filename;
        File file=new File(filePath);
        file.deleteOnExit();  
        respJson.put("status", true);
        respJson.put("info", "OK");
		}                            
       
        return JSON.toJSONString(respJson);
		
	}
	@RequestMapping(value="editgoods/uploadImages",method=RequestMethod.POST)
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
}
