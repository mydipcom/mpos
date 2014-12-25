package com.mpos.service;



import java.util.List;

import com.mpos.dto.TproductImage;

public interface GoodsImageService {
	
	void CreateImages(TproductImage image);
	
	void updeteImages(TproductImage image);
	
	List<TproductImage> getByProductid(Integer id);

}
