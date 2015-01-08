package com.mpos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mpos.dao.GoodsImageDao;
import com.mpos.dto.TproductImage;
import com.mpos.service.GoodsImageService;
import com.mpos.service.GoodsService;
@Service
public class GoodsImageServiceImpl implements GoodsImageService{

	@Autowired
	private GoodsImageDao goodsImageDao;
	
	public void CreateImages(TproductImage image) {
		goodsImageDao.create(image);
		
	}

	public void updeteImages(TproductImage image) {
		goodsImageDao.update(image);
		
	}

	public List<TproductImage> getByProductid(Integer id) {
		
		return goodsImageDao.getByproductid(id);
	}

	
	public void deleteImagebyid(Integer id) {
		goodsImageDao.delete(id);
		
	}

}
