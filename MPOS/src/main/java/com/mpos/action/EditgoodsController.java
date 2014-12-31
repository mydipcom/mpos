package com.mpos.action;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

import com.alibaba.fastjson.JSONObject;
import com.mpos.commons.MposException;
import com.mpos.commons.SystemConfig;
import com.mpos.dto.Tcategory;
import com.mpos.dto.TcategoryAttribute;
import com.mpos.dto.Tmenu;
import com.mpos.dto.Tproduct;
import com.mpos.dto.TproductAttribute;
import com.mpos.dto.TproductAttributeId;
import com.mpos.dto.TproductImage;
import com.mpos.model.AddAttributevaleModel;
import com.mpos.model.AddGoodsModel;
import com.mpos.service.CategoryAttributeService;
import com.mpos.service.CategoryService;
import com.mpos.service.GoodsImageService;
import com.mpos.service.GoodsService;
import com.mpos.service.MenuService;
import com.mpos.service.ProductAttributeService;

@Controller

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


	@RequestMapping(value="/editgoods/{ids}",method=RequestMethod.GET)
	public ModelAndView eidtgoods(@PathVariable String ids,HttpServletRequest request){
		Integer id=Integer.parseInt(ids);
		ModelAndView mav=new ModelAndView();

		
		try {
			Tproduct product=goodsService.getTproductByid(id);
			List<TcategoryAttribute> categoryAttribute;
		//	List<TproductAttribute> productAttribute = null;
			categoryAttribute=CategoryAttributeService.getCategoryAttributeByCategoryid(product.getTcategory().getCategoryId());
			mav.addObject("product", product);
			List<Tcategory> categorys=categoryService.getallCategory();
			List<Tmenu> menus=menuService.getAllMenu();
			mav.addObject("category", categorys);
			mav.addObject("menu", menus);
			mav.addObject("categoryAttribute",categoryAttribute);
			List<TproductImage> productImage=goodsImageService.getByProductid(product.getId());
			mav.addObject("productImage",productImage.get(0));
			TproductAttributeId productAttributeId=new TproductAttributeId();
			for (int i = 0; i < categoryAttribute.size(); i++) {
				productAttributeId.setCategoryAttribute(categoryAttribute.get(i));
				productAttributeId.setProduct(product);
				//TproductAttribute TproductAttribute=productAttributeService.getAttributes(productAttributeId);
				//productAttribute.add(TproductAttribute);
				System.out.print("111");
			}
			
			
			
		} catch (MposException be ) {	
			
		}
		mav.setViewName("goods/editgoods");
		return mav;
	}
	@RequestMapping(value="/editgoods/editgoods",method=RequestMethod.POST)
	@ResponseBody
/*	public ModelAndView editGoods(HttpServletRequest request,AddGoodsModel model){
		System.out.print("111");
		
		
		
		return null;
	}*/
	
	public ModelAndView editGoods(HttpServletRequest request,AddGoodsModel model,@RequestParam(value = "files", required = false) MultipartFile[] file)throws IOException{
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
	
}