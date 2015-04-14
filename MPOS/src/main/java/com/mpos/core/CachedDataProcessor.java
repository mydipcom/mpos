/**   
 * @Title: CachedDataProcessor.java 
 * @Package com.bps.core 
 *
 * @Description: User Points Management System
 * 
 * @date Nov 13, 2014 5:23:42 PM
 * @version V1.0   
 */ 
package com.mpos.core;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.mpos.commons.SystemConfig;
import com.mpos.service.AdminNodesService;
import com.mpos.service.StoreService;
import com.mpos.service.SystemSettingService;

/** 
 * <p>Sping容器初始化完成后的缓存甚而数据的处理方法</p>
 * @ClassName: CachedDataProcessor 
 * @author Phills Li 
 * 
 */
public class CachedDataProcessor implements ApplicationListener<ContextRefreshedEvent> {
	
    public void onApplicationEvent(ContextRefreshedEvent event) {
		//root application context
    	if(event.getApplicationContext().getParent() == null){
    		AdminNodesService adminNodesService=(AdminNodesService) event.getApplicationContext().getBean("adminNodesService");
    		
    		SystemSettingService systemSettingService =(SystemSettingService)event.getApplicationContext().getBean("systemSettingService");
    		
    		StoreService storeService = (StoreService)event.getApplicationContext().getBean("storeService");
    		
    		if(adminNodesService!=null){
    			adminNodesService.cachedNodesData();    			
    		}
    		if(storeService !=null){
    			storeService.cacheStoreTaken();
    			for (String key : SystemConfig.STORE_TAKEN_MAP.keySet()) {
					System.out.println("key:"+key+":-------------------->value:"+SystemConfig.STORE_TAKEN_MAP.get(key));
				}
    		}
    		if(systemSettingService != null){
    			systemSettingService.cachedSystemSet();
    			for (String key : SystemConfig.Admin_Setting_Map.keySet()) {
					System.out.println("key:"+key+":-------------------->value:"+SystemConfig.Admin_Setting_Map.get(key));
				}
    		}
    	}
    	else{//projectName-servlet  context
    		
    	}
    }
}
