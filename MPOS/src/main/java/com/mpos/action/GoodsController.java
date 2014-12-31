package com.mpos.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mpos.commons.ConvertTools;
import com.mpos.commons.MposException;
import com.mpos.commons.SystemConfig;
import com.mpos.dto.Tcategory;
import com.mpos.dto.TcategoryAttribute;
import com.mpos.dto.Tlanguage;
import com.mpos.dto.Tmenu;
import com.mpos.dto.Tproduct;
import com.mpos.dto.TproductAttribute;
import com.mpos.dto.TproductAttributeId;
import com.mpos.dto.TproductImage;
import com.mpos.model.AddAttributevaleModel;
import com.mpos.model.AddGoodsModel;
import com.mpos.model.AddgoodsLocal;
import com.mpos.model.DataTableParamter;
import com.mpos.model.PagingData;
import com.mpos.service.CategoryAttributeService;
import com.mpos.service.CategoryService;
import com.mpos.service.GoodsImageService;
import com.mpos.service.GoodsService;
import com.mpos.service.LanguageService;
import com.mpos.service.LocalizedFieldService;
import com.mpos.service.MenuService;
import com.mpos.service.ProductAttributeService;
import com.mpos.service.ProductReleaseService;
import com.mysql.fabric.xmlrpc.base.Value;

@Controller
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
	private ProductReleaseService productReleaseService;
	
	@Autowired
	private LocalizedFieldService localizedFieldService;
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView Goods(HttpServletRequest request){
		ModelAndView mav=new ModelAndView();
		List<Tcategory> categorys=categoryService.getallCategory();
		List<Tmenu> menus=menuService.getAllMenu();
		mav.addObject("category", categorys);
		mav.addObject("menu", menus);
		mav.setViewName("goods/goods");
		return mav;
	}
	@RequestMapping(value="/goodslist",method=RequestMethod.GET)
	@ResponseBody
	public String goodsList(HttpServletRequest request,DataTableParamter dtp){		
		PagingData pagingData=goodsService.loadGoodsList(dtp);
		
		pagingData.setSEcho(dtp.sEcho);
		if(pagingData.getAaData()==null){
			Object[] objs=new Object[]{};
			pagingData.setAaData(objs);
		}
		String rightsListJson= JSON.toJSONString(pagingData);
		return rightsListJson;	
	}
	@RequestMapping(value="addgoods",method=RequestMethod.GET)
	public ModelAndView addGoodsPage(HttpServletRequest request){
		ModelAndView mav=new ModelAndView();
		List<Tcategory> categorys=categoryService.getallCategory();
		List<Tmenu> menus=menuService.getAllMenu();
		List<Tlanguage> languages = languageService.loadAllTlanguage();
		mav.addObject("lanList", languages);
		mav.addObject("category", categorys);
		mav.addObject("menu", menus);
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
	@RequestMapping(value="/getcategorybyid/{ids}",method=RequestMethod.POST)
	@ResponseBody
	public String getgoodscategory(@PathVariable String ids,HttpServletRequest request){
		Integer id=Integer.parseInt(ids);	
		JSONObject respJson = new JSONObject();
		try {
		//Tcategory category=categoryService.getCategory(id);
		List<TcategoryAttribute> list=CategoryAttributeService.getCategoryAttributeByCategoryid(id);
		respJson.put("status", true);
		respJson.put("list", list);
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
	public ModelAndView addGoods(HttpServletRequest request,AddGoodsModel model,AddgoodsLocal value,
			@RequestParam(value = "files", required = false) MultipartFile[] file)throws IOException{
		Tproduct product=new Tproduct();
		MultipartFile[] files;
		JSONObject respJson = new JSONObject();
		List<TproductAttribute> tproductAttributelist=new ArrayList<TproductAttribute>(); 
		TproductAttribute tproductAttribute=new TproductAttribute();
		product.setShortDescr(model.getShortDescr());
		product.setFullDescr(model.getFullDescr());
		product.setPrice(model.getPrice());
		product.setOldPrice(model.getOldPrice());
		product.setProductName(model.getProductName());
		product.setUnitName(model.getUnitName());
		Tcategory catefory=categoryService.getCategory(model.getCategoryId());
		product.setTcategory(catefory);
		Tmenu menu=menuService.getMenu(model.getMenuId());
		product.setTmenu(menu);
		product.setRecommend(model.isRecommend());
		product.setSort(model.getSort());
		try {
			
		try {
			goodsService.createGoods(product);
		//	localizedFieldService.createLocalizedFieldList(value.setValue(product));
			productReleaseService.createOrupdateProductRelease(product.getId());
			localizedFieldService.createLocalizedFieldList(value.setValue(product));
		} catch (MposException be) {
			
		}
		//�����Ʒ��������
		Iterator it = SystemConfig.product_AttributeModel_Map.keySet().iterator(); 
		   while (it.hasNext()){ 
		    String key; 
		    key=(String)it.next(); 
		    AddAttributevaleModel models= SystemConfig.product_AttributeModel_Map.get(key);
		    TcategoryAttribute categoryAttribute=CategoryAttributeService.getCategoryAttribute(models.getAttributeId());
		    tproductAttribute.setContent(models.getContent());
		    tproductAttribute.setPrice(models.getPrice());
		    TproductAttributeId productAttributeid=new TproductAttributeId();
		    productAttributeid.setCategoryAttribute(categoryAttribute);
		    productAttributeid.setProduct(product);
		    tproductAttribute.setId(productAttributeid);
		    tproductAttributelist.add(tproductAttribute);
		   } 
		   for (int i = 0; i < tproductAttributelist.size(); i++) {
			   try {
				   productAttributeService.createProductAttribute(tproductAttributelist.get(i));
				   
			} catch (MposException be) {
					
			}
			
		}
		   SystemConfig.product_AttributeModel_Map.clear();
		//Tproduct products=goodsService.findbyProductName(product.getProductName());
		   //�����ƷͼƬ
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
					goodsImageService.CreateImages(productImage);
					//File file2=new File(request.getSession().getServletContext().getRealPath("/")+File.separator+"static"+File.separator+"upload"+File.separator+productImage.getId()+"."+productImage.getImageSuffix());
					File file2=new File(request.getSession().getServletContext().getRealPath("/")+File.separator+"static"+File.separator+"upload"
											+File.separator+productImage.getId()+"."+productImage.getImageSuffix());
					
					if(!file2.exists()){
					ImageOutputStream ios= ImageIO.createImageOutputStream(file2);
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
		//List<TcategoryAttribute> categorytitle=CategoryAttributeService.getCategoryAttributeByCategoryid(product.getId());
		//mav.addObject("categorytitle", categorytitle);
		
		return new ModelAndView("redirect:/goods");
		
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
}
