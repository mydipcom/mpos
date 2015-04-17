package com.mpos.commons;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baidu.yun.core.log.YunLogEvent;
import com.baidu.yun.core.log.YunLogHandler;
import com.baidu.yun.push.auth.PushKeyPair;
import com.baidu.yun.push.client.BaiduPushClient;
import com.baidu.yun.push.constants.BaiduPushConstants;
import com.baidu.yun.push.exception.PushClientException;
import com.baidu.yun.push.exception.PushServerException;
import com.baidu.yun.push.model.AddDevicesToTagRequest;
import com.baidu.yun.push.model.AddDevicesToTagResponse;
import com.baidu.yun.push.model.CreateTagRequest;
import com.baidu.yun.push.model.CreateTagResponse;
import com.baidu.yun.push.model.DeleteDevicesFromTagRequest;
import com.baidu.yun.push.model.DeleteDevicesFromTagResponse;
import com.baidu.yun.push.model.DeleteTagRequest;
import com.baidu.yun.push.model.DeleteTagResponse;
import com.baidu.yun.push.model.PushMsgToSingleDeviceRequest;
import com.baidu.yun.push.model.PushMsgToSingleDeviceResponse;
import com.baidu.yun.push.model.PushMsgToTagRequest;
import com.baidu.yun.push.model.PushMsgToTagResponse;

/**
 * 百度SDK 推送工具
 * @author Administrator
 *
 */
public class BaiduPushTool {
	/**
	 * 苹果设备服务端api_key
	 */
	public final static String IOS_API_KEY = SystemConfig.Admin_Setting_Map.get(SystemConstants.PUSH_IOS_API_KEY);
	/**
	 * 苹果设备服务端secret_key
	 */
	public final static String IOS_SECRET_KEY = SystemConfig.Admin_Setting_Map.get(SystemConstants.PUSH_IOS_SECRET_KEY);
	/**
	 * 安卓设备服务端api_key  "LXkMQ0p54mg4jGOtKkPC5A0F";//
	 */
	public final static String ANDROID_API_KEY = "LXkMQ0p54mg4jGOtKkPC5A0F";//SystemConfig.Admin_Setting_Map.get(SystemConstants.PUSH_ANDROID_API_KEY);
	/**
	 * 安卓设备服务端secret_key "xGRxrDaO51MQK599kGYTttqFaiTZwAwK";// 
	 */
	public final static String ANDROID_SECRET_KEY ="xGRxrDaO51MQK599kGYTttqFaiTZwAwK";// SystemConfig.Admin_Setting_Map.get(SystemConstants.PUSH_ANDROID_SECRET_KEY);
	/**
	 * 苹果设备
	 */
	public final static Integer IOS_TYPE = 4;
	
	/**
	 * 安卓设备
	 */
	public final static Integer ANDROID_TYPE = 3;
	
	public static class Notification{
		/**
		 * 标题
		 */
		private String title;
		/**
		 * 描述
		 */
		private String description;
		private String notification_builder_id;
		private String notification_basic_style;
		private String open_type;
		private String url;
		/**
		 * 自定义内容
		 */
		private Object custom_content;
		
		public Notification() {
		}
		
		public Notification(String title, String description) {
			this.title = title;
			this.description = description;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getNotification_builder_id() {
			return notification_builder_id;
		}
		public void setNotification_builder_id(String notification_builder_id) {
			this.notification_builder_id = notification_builder_id;
		}
		public String getNotification_basic_style() {
			return notification_basic_style;
		}
		public void setNotification_basic_style(String notification_basic_style) {
			this.notification_basic_style = notification_basic_style;
		}
		public String getOpen_type() {
			return open_type;
		}
		public void setOpen_type(String open_type) {
			this.open_type = open_type;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public Object getCustom_content() {
			return custom_content;
		}
		public void setCustom_content(Object custom_content) {
			this.custom_content = custom_content;
		}
	}
	
	public static void main(String[] args) {
		//String channelId = "4346705795496960498";
		Integer deviceType = 3;
		Notification notification = new Notification("title", "Hello Android");
		pushMsgToTag(notification, "0", deviceType);
		//deleteTag(3,"0");
		//deleteTag(3,"ios1");
	}
	
	private static PushKeyPair getPushKeyPair(Integer deviceType){
		 PushKeyPair pair = null;
		if(deviceType==IOS_TYPE){
			pair = new PushKeyPair(IOS_API_KEY, IOS_SECRET_KEY);
		}else if(deviceType==ANDROID_TYPE){
			pair = new PushKeyPair(ANDROID_API_KEY, ANDROID_SECRET_KEY);
		}else{
			throw new MposException("1", "no support type");
		}
		return pair;
	}
	/**
	 *  从标签组删除设备
	 * @param channelIds
	 * @param tagName
	 * @param deviceType
	 * @return
	 */
	public static boolean deleteDevicesFromTag(String[] channelIds,String tagName,Integer deviceType){
		BaiduPushClient pushClient = new BaiduPushClient(getPushKeyPair(deviceType),BaiduPushConstants.CHANNEL_REST_URL);
		DeleteDevicesFromTagRequest addreRequest = new DeleteDevicesFromTagRequest();
		addreRequest.addChannelIds(channelIds).addDeviceType(deviceType).addTagName(tagName);
		try {
			DeleteDevicesFromTagResponse response = pushClient.deleteDevicesFromTag(addreRequest);
			if(response!=null){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return false;
	}
	/**
	 * 向标签组添加设备
	 * @param channelIds 通道ID集合
	 * @param tagName 标签组名
	 * @param deviceType 设备类型  3:android;4:IOS
	 * @return
	 */
	public static boolean addDevicesToTag(String[] channelIds,String tagName,Integer deviceType){
		BaiduPushClient pushClient = new BaiduPushClient(getPushKeyPair(deviceType),BaiduPushConstants.CHANNEL_REST_URL);
		AddDevicesToTagRequest addreRequest = new AddDevicesToTagRequest();
		addreRequest.addChannelIds(channelIds).addDeviceType(deviceType).addTagName(tagName);
		try {
			AddDevicesToTagResponse response = pushClient.addDevicesToTag(addreRequest);
			if(response!=null){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return false;
	}
	
	/**
	 * 创建标签组
	 * @param deviceType设备类型 3:android;4:IOS
	 * @param tagName 标签组名称
	 * @return
	 */
	public static boolean createTag (Integer deviceType,String tagName){
		BaiduPushClient pushClient = new BaiduPushClient(getPushKeyPair(deviceType),BaiduPushConstants.CHANNEL_REST_URL);
		CreateTagRequest request = new CreateTagRequest();
		request.addDeviceType(deviceType).addTagName(tagName);
		//QueryTagsRequest queryRequest  = new QueryTagsRequest();
		//queryRequest.addTagName(tagName).addDeviceType(deviceType);
		try {
		//	QueryTagsResponse queryResponse = pushClient.queryTags(queryRequest);
		/*	if(queryResponse.getTotalNum()==0){
				
			}else{
				throw new MposException("2", "tag is exist");
			}*/
			CreateTagResponse response = pushClient.createTag(request);
			if(response.getResult()==0){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return false;
	} 
	
	/**
	 * 删除标签组
	 * @param deviceType设备类型 3:android;4:IOS
	 * @param tagName 标签组名称
	 * @return
	 */
	public static boolean deleteTag (Integer deviceType,String tagName){
		BaiduPushClient pushClient = new BaiduPushClient(getPushKeyPair(deviceType),BaiduPushConstants.CHANNEL_REST_URL);
		DeleteTagRequest request = new DeleteTagRequest();
		request.addDeviceType(deviceType).addTagName(tagName);
		//QueryTagsRequest queryRequest  = new QueryTagsRequest();
	//	queryRequest.addTagName(tagName).addDeviceType(deviceType);
		try {
			//QueryTagsResponse queryResponse = pushClient.queryTags(queryRequest);
			//if(queryResponse.getTotalNum()==1){
				DeleteTagResponse response = pushClient.deleteTag(request);
				if(response.getResult()==0){
					return true;
			//	}
			}else{
				throw new MposException("2", "tag is exist");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return false;
	}
	/**
	 *  组播
	 * @param notification 推送消息
	 * @param tagName 标签组名称
	 * @param deviceType 设备类型 3:android;4:IOS
	 * @return
	 */
	public static boolean pushMsgToTag(Notification notification,String tagName,Integer deviceType){
		BaiduPushClient pushClient = new BaiduPushClient(getPushKeyPair(deviceType),BaiduPushConstants.CHANNEL_REST_URL);
		PushMsgToTagRequest request = new PushMsgToTagRequest();
		request.addDeviceType(deviceType).addTagName(tagName).addMessageType(1).addMessage(JSON.toJSONString(notification)).addMsgExpires(86400);
		try {
			PushMsgToTagResponse response = pushClient. pushMsgToTag(request);
			if(response.getMsgId()!=null){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return false;
	}
	/**
	 * 向单个设备推送消息
	 * @param deviceType 设备类型  3:android;4:IOS
	 * @param channelId 通道ID
	 * @param notification 消息
	 */
	public static boolean pushMsgToSingleDevice(Integer deviceType,String channelId,Notification notification){
		 PushMsgToSingleDeviceResponse response = null;
		 // 2. build a BaidupushClient object to access released interfaces
        BaiduPushClient pushClient = new BaiduPushClient(getPushKeyPair(deviceType),BaiduPushConstants.CHANNEL_REST_URL);
        // 3. register a YunLogHandler to get detail interacting information
        // in this request.
        pushClient.setChannelLogHandler (new YunLogHandler () {
            public void onHandle (YunLogEvent event) {
                System.out.println(event.getMessage());
            }
        });
        PushMsgToSingleDeviceRequest request = new PushMsgToSingleDeviceRequest().
                addChannelId(channelId).
                addMsgExpires(new Integer(3600)). //message有效时间
                addMessageType(1).//1：通知,0:消息.默认为0  注：IOS只有通知.
                addMessage(JSON.toJSONString(notification)).
                //addDeployStatus(2). //IOS, DeployStatus => 1: Developer 2: Production.
                addDeviceType(3);// deviceType => 3:android, 4:ios
            // 5. http request
			try {
				response = pushClient.pushMsgToSingleDevice(request);
				 // Http请求结果解析打印
	            System.out.println("msgId: " + response.getMsgId()+ ",sendTime: " + response.getSendTime());
	            if(response.getMsgId()!=null){
	            	return true;
	            }
			} catch (PushClientException e) {
				e.printStackTrace();
			} catch (PushServerException e) {
				e.printStackTrace();
			}
			return false;
	}
	/**
	 * 推送消息到一个设备
	 * @throws PushClientException
	 * @throws PushServerException
	 */
	 public void pushMsgToSingleDevice () throws PushClientException,
	    PushServerException{
	        // 1. get apiKey and secretKey from developer console
	        String apiKey = "xxxxxxxxxxxxxxxxxxxxxxxxx";
	        String secretKey = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
	        PushKeyPair pair = new PushKeyPair(apiKey, secretKey);
	        
	        // 2. build a BaidupushClient object to access released interfaces
	        BaiduPushClient pushClient = new BaiduPushClient(pair,
	        		BaiduPushConstants.CHANNEL_REST_URL);

	        // 3. register a YunLogHandler to get detail interacting information
	        // in this request.
	        pushClient.setChannelLogHandler (new YunLogHandler () {
	            public void onHandle (YunLogEvent event) {
	                System.out.println(event.getMessage());
	            }
	        });

	        try {
	            // 4. specify request arguments
	        	// make Android Notification
	        	JSONObject notification = new JSONObject();
	        	notification.put("title", "TEST");
	        	notification.put("description","Hello Baidu Push");
	        	notification.put("notification_builder_id", 0);
	        	notification.put("notification_basic_style", 4);
	        	notification.put("open_type", 1);
	        	notification.put("url", "http://push.baidu.com");
	        	JSONObject jsonCustormCont = new JSONObject();
	        	jsonCustormCont.put("key", "value"); //自定义内容，key-value
	        	notification.put("custom_content", jsonCustormCont);
	        	
	            PushMsgToSingleDeviceRequest request = new PushMsgToSingleDeviceRequest().
	                addChannelId("3569104444463414374").
	                addMsgExpires(new Integer(3600)). //message有效时间
	                addMessageType(1).//1：通知,0:消息.默认为0  注：IOS只有通知.
	                addMessage(notification.toString()).
	                //addDeployStatus(2). //IOS, DeployStatus => 1: Developer 2: Production.
	                addDeviceType(3);// deviceType => 3:android, 4:ios
	            // 5. http request
	            PushMsgToSingleDeviceResponse response = pushClient.
	                pushMsgToSingleDevice(request);
	            // Http请求结果解析打印
	            System.out.println("msgId: " + response.getMsgId()
	            		+ ",sendTime: " + response.getSendTime());
	        } catch (PushClientException e) {
	            /*ERROROPTTYPE 用于设置异常的处理方式 -- 抛出异常和捕获异常,
	             *'true' 表示抛出, 'false' 表示捕获。
	             */
	        	if (BaiduPushConstants.ERROROPTTYPE) {
	            	throw e;
	            } else {
	                e.printStackTrace();
	            }
	        } catch (PushServerException e) {
	        	if (BaiduPushConstants.ERROROPTTYPE) {
	        		throw e;
	        	} else {
	                System.out.println(String.format(
	                        "requestId: %d, errorCode: %d, errorMessage: %s",
	                        e.getRequestId(), e.getErrorCode(), e.getErrorMsg()));
	        	}
	        }
	    }
}
