package com.mpos.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mpos.dao.GoodsAttributeDao;
import com.mpos.dao.GoodsDao;
import com.mpos.dao.GoodsImageDao;
import com.mpos.dao.LocalizedFieldDao;
import com.mpos.dao.ProductReleaseDao;
import com.mpos.dto.Tcategory;
import com.mpos.dto.TgoodsAttribute;
import com.mpos.dto.TlocalizedField;
import com.mpos.dto.Tproduct;
import com.mpos.dto.TproductImage;
import com.mpos.dto.TproductRelease;
import com.mpos.model.AddProductModel;
import com.mpos.model.DataTableParamter;
import com.mpos.model.FileMeta;
import com.mpos.model.PagingData;
import com.mpos.service.GoodsService;
@Service
public class GoodsServiceImpl implements GoodsService{

	@Autowired
	private GoodsDao goodsDao;
	
	@Autowired
	private GoodsAttributeDao goodsAttributeDao;
	@Autowired
	private LocalizedFieldDao localizedFieldDao;
	@Autowired
	private GoodsImageDao goodsImageDao;
	@Autowired
	private  ProductReleaseDao productReleaseDao;
	
	public PagingData loadGoodsList(DataTableParamter rdtp) {
		String searchJsonStr=rdtp.getsSearch();
		if(searchJsonStr!=null&&!searchJsonStr.isEmpty()){
			List<Criterion> criterionsList=new ArrayList<Criterion>();
			criterionsList.add(Restrictions.eq("status",true));
			JSONObject jsonObj= (JSONObject)JSON.parse(searchJsonStr);
			Set<String> keys=jsonObj.keySet();						
			for (String key : keys) {
				String val=jsonObj.getString(key);
				if(val!=null&&!val.isEmpty()){
					if(key=="recommend"){
						criterionsList.add(Restrictions.eq(key, jsonObj.getBoolean(key)));
					}
					else if(key=="tcategory.categoryId"){
						criterionsList.add(Restrictions.eq(key, jsonObj.getInteger(key)));
					}
					else if(key=="tmenu.menuId"){
						criterionsList.add(Restrictions.eq(key, jsonObj.getInteger(key)));
					}else if(key=="status"){
						criterionsList.add(Restrictions.eq(key, jsonObj.getBoolean(key)));
					}
					else {
						criterionsList.add(Restrictions.eq(key, jsonObj.get(key)));
					}
				}
			}
			Criterion[] criterions=new Criterion[criterionsList.size()];
			int i=0;
			for (Criterion criterion : criterionsList) {
				criterions[i]=criterion;	
				i++;
			}
			return goodsDao.findPage("id", false, criterions, rdtp.iDisplayStart, rdtp.iDisplayLength);
		}
			return goodsDao.findPage("id", false, Restrictions.eq("status",true),rdtp.iDisplayStart, rdtp.iDisplayLength);
		
		
		
	}

	public void deletegoodsByids(Integer[] ids) {
		if(ids!=null&&ids.length>0){
			for (Integer id : ids) {
				Tproduct goods=getTproductByid(id);
				goods.setStatus(false);
				goodsDao.update(goods);
			}
		}	
	}

	
	public Tproduct getTproductByid(Integer id) {
	
		return goodsDao.get(id);
	}

	public void activegoodsByids(Integer[] ids) {
		if(ids!=null&&ids.length>0){
			for (Integer id : ids) {
				Tproduct goods=getTproductByid(id);
				goods.setStatus(true);
				goodsDao.update(goods);
			}
		}	
		
	}

	public void createGoods(Tproduct product) {
		goodsDao.create(product);
		
	}

	public Tproduct findbyProductName(String productName) {
		
		return goodsDao.findprodctbyname(productName);
	}

	public void updateGoods(Tproduct product) {
		
		goodsDao.update(product);
	}
	
		public List<Tproduct> loadAll() {
		// TODO Auto-generated method stub
		return goodsDao.LoadAll();
	}

		
		public void createproduct(AddProductModel model,LinkedHashMap<Integer,FileMeta> filesMap,HttpServletRequest request) {
			
			Tproduct product=new Tproduct();
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
			goodsDao.create(product);
			List<TgoodsAttribute> productAttributesList=model.getAttributes();
			for (TgoodsAttribute goodsAttribute : productAttributesList) {
				goodsAttribute.setProductId(product.getId());
				goodsAttributeDao.create(goodsAttribute);
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
					localizedFieldDao.save(localizedField);
				}				
			}
			for (TlocalizedField localizedField : shortDescrLocaleList) {
				if(localizedField.getLocaleValue()!=null&&!localizedField.getLocaleValue().isEmpty()){
					localizedField.setEntityId(product.getId());
					localizedField.setTableName("Tproduct");
					localizedField.setTableField("shortDescr");
					localizedFieldDao.save(localizedField);
				}
			}
			for (TlocalizedField localizedField : fullDescrLocaleList) {
				if(localizedField.getLocaleValue()!=null&&!localizedField.getLocaleValue().isEmpty()){
					localizedField.setEntityId(product.getId());
					localizedField.setTableName("Tproduct");
					localizedField.setTableField("fullDescr");
					localizedFieldDao.save(localizedField);
				}
			}
			for (TlocalizedField localizedField : unitNameLocaleList) {
				if(localizedField.getLocaleValue()!=null&&!localizedField.getLocaleValue().isEmpty()){
					localizedField.setEntityId(product.getId());
					localizedField.setTableName("Tproduct");
					localizedField.setTableField("unitName");
					localizedFieldDao.save(localizedField);
				}
			}
			//set images 
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
            	String fileUrl="/upload/product/"+filename;
                try {
					FileCopyUtils.copy(fileMeta.getBytes(), new FileOutputStream(filePath));
				} catch (FileNotFoundException e) {
					
					e.printStackTrace();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
				productImage.setImageUrl(fileUrl);
				i++;
				goodsImageDao.create(productImage);
			}
			filesMap.clear();
			//add productReleaseService
			Integer verId=productReleaseDao.getMaxintergerValue("id");
			TproductRelease productrelease;
			if (verId!=0) {
				 productrelease=productReleaseDao.get(verId);
				 if(productrelease!=null&&!productrelease.isIsPublic()){
					 String ids=productrelease.getProducts();
					 String products[]=ids.split(",");
					 for (int j = 0; j < products.length; j++) {
						if(products[j].equals(product.getId().toString())){
							return;
						}
					}
					 productrelease.setProducts(ids+","+product.getId());
					 productReleaseDao.update(productrelease);	 
				 }else {
					 TproductRelease newproductrelease=new TproductRelease();
					 newproductrelease.setProducts(product.getId().toString());
					 newproductrelease.setIsPublic(false);
					 productReleaseDao.create(newproductrelease);
				}
			}
			else {
				TproductRelease productrelease1=new TproductRelease();
				productrelease1.setProducts(product.getId().toString());
				productrelease1.setIsPublic(false);
				productReleaseDao.create(productrelease1);
			}
			
		}

		
		public void updateproduct(AddProductModel model,LinkedHashMap<Integer, FileMeta> filesMap,HttpServletRequest request) {
			Tproduct product=new Tproduct();
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
			product.setId(model.getProductId());
			goodsDao.update(product);
			/*List<TgoodsAttribute> productAttributesList=model.getAttributes();
			for (TgoodsAttribute goodsAttribute : productAttributesList) {
				goodsAttribute.setProductId(product.getId());
				goodsAttributeDao.update(goodsAttribute);
			}*/
			
			//local language
			List<TlocalizedField> productNameLocaleList=model.getProductName_locale();
			List<TlocalizedField> shortDescrLocaleList=model.getShortDescr_locale();
			List<TlocalizedField> fullDescrLocaleList=model.getFullDescr_locale();
			List<TlocalizedField> unitNameLocaleList=model.getUnitName_locale();
			for (TlocalizedField localizedField : productNameLocaleList) {
				if(localizedField.getLocaleValue()!=null&&!localizedField.getLocaleValue().isEmpty()){
					localizedField.setEntityId(product.getId());
					localizedField.setTableName("Tproduct");
					localizedField.setTableField("productName");
					localizedFieldDao.save(localizedField);
				}				
			}
			for (TlocalizedField localizedField : shortDescrLocaleList) {
				if(localizedField.getLocaleValue()!=null&&!localizedField.getLocaleValue().isEmpty()){
					localizedField.setEntityId(product.getId());
					localizedField.setTableName("Tproduct");
					localizedField.setTableField("shortDescr");
					localizedFieldDao.save(localizedField);
				}
			}
			for (TlocalizedField localizedField : fullDescrLocaleList) {
				if(localizedField.getLocaleValue()!=null&&!localizedField.getLocaleValue().isEmpty()){
					localizedField.setEntityId(product.getId());
					localizedField.setTableName("Tproduct");
					localizedField.setTableField("fullDescr");
					localizedFieldDao.save(localizedField);
				}
			}
			for (TlocalizedField localizedField : unitNameLocaleList) {
				if(localizedField.getLocaleValue()!=null&&!localizedField.getLocaleValue().isEmpty()){
					localizedField.setEntityId(product.getId());
					localizedField.setTableName("Tproduct");
					localizedField.setTableField("unitName");
					localizedFieldDao.save(localizedField);
				}
			}
			//set images 
			Set<Integer> keys=filesMap.keySet();
			int i=0;
			for (Integer key : keys) {
				FileMeta fileMeta=filesMap.get(key);
				TproductImage productImage=new TproductImage();
				if(fileMeta.getFileId()!=null){
				productImage.setId(fileMeta.getFileId());
				}
				productImage.setProduct(product);
				productImage.setImage(fileMeta.getBytes());
				productImage.setImageSuffix(fileMeta.getSuffix());
				String filename=product.getId()+"_"+i+"."+fileMeta.getSuffix();
				String filePath=request.getSession().getServletContext().getRealPath("/")+File.separator+"upload"+File.separator+"product"+File.separator+filename;
                //String fileUrl=request.getContextPath()+"/goods/getCachedImg/"+imgIndex;
            	String fileUrl="/upload/product/"+filename;
                try {
					FileCopyUtils.copy(fileMeta.getBytes(), new FileOutputStream(filePath));
				} catch (FileNotFoundException e) {
					
					e.printStackTrace();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
				productImage.setImageUrl(fileUrl);
				i++;
				//goodsImageDao.create(productImage);
				goodsImageDao.saveOrUpdate(productImage);
			}
			filesMap.clear();
			//add productReleaseService
			Integer verId=productReleaseDao.getMaxintergerValue("id");
			TproductRelease productrelease;
			if (verId!=0) {
				 productrelease=productReleaseDao.get(verId);
				 if(productrelease!=null&&!productrelease.isIsPublic()){
					 String ids=productrelease.getProducts();
					 String products[]=ids.split(",");
					 for (int j = 0; j < products.length; j++) {
						if(products[j].equals(product.getId().toString())){
							return;
						}
					}
					 productrelease.setProducts(ids+","+product.getId());
					 productReleaseDao.update(productrelease);	 
				 }else {
					 TproductRelease newproductrelease=new TproductRelease();
					 newproductrelease.setProducts(product.getId().toString());
					 newproductrelease.setIsPublic(false);
					 productReleaseDao.create(newproductrelease);
				}
			}
			else {
				TproductRelease productrelease1=new TproductRelease();
				productrelease1.setProducts(product.getId().toString());
				productrelease1.setIsPublic(false);
				productReleaseDao.create(productrelease1);
			}
		}
		
		

}
