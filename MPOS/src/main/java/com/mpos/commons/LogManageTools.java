package com.mpos.commons;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.mpos.dto.TadminLog;
import com.mpos.dto.TadminUser;
import com.mpos.service.AdminuserLogService;


public class LogManageTools {
	private static long time;
	private static String clazz;
	private static String method;
	private static String adminId;
	private static String reqMethod;
	private static String reqUrl;
	public static final short NOR_LEVEL = 1;
	public static final short FAIL_LEVEL = 2;
	
	public static void writeAdminLog(String content,short level,HttpServletRequest request){
		StackTraceElement[] ste = new Exception().getStackTrace();
		AdminuserLogService adminuserLogService = (AdminuserLogService) MyApplicationContextUtil.getContext().getBean("adminuserLogService");
	    clazz=ste[1].getClassName();
		method=ste[1].getMethodName();
		time=System.currentTimeMillis();
		adminId=((TadminUser)request.getSession().getAttribute(SystemConstants.LOGINED)).getAdminId();
		//request.getp
		reqMethod=request.getMethod().toUpperCase();
		reqUrl=request.getRequestURI();
		reqUrl=reqUrl.split("/")[2];
		String reqPath=reqMethod+"@"+"/"+reqUrl;
		
		Integer nodeId = SystemConfig.STORE_NODES_URL_MAP.get(reqPath);
	    TadminLog adminLog = new TadminLog();
	    if(nodeId==null){
	    	Set<String> keys=SystemConfig.STORE_NODES_URL_MAP.keySet();
			for (String key : keys) {
				if(key.endsWith("*")){
					if(reqPath.startsWith(key.substring(0, key.length()-2))){
						nodeId=SystemConfig.STORE_NODES_URL_MAP.get(key);
						break;
					}
				}
			}
			if(nodeId==null){
				nodeId=-1;
			}
	    }
	    adminLog.setNodeId(nodeId);
	    adminLog.setLevel(level);
		adminLog.setCreatedTime(time);
		adminLog.setAdminId(adminId);
	    adminLog.setContent(clazz+SystemConstants.LOG_SEPARATOR+method+SystemConstants.LOG_SEPARATOR+content);
	    adminuserLogService.createAdminLog(adminLog);
	}	
	
	
	public static void writeAdminLog(String content,Object obj,HttpServletRequest request){
		StackTraceElement[] ste = new Exception().getStackTrace();
		AdminuserLogService adminuserLogService = (AdminuserLogService) MyApplicationContextUtil.getContext().getBean("adminuserLogService");
	    clazz=ste[1].getClassName();
		method=ste[1].getMethodName();
		time=System.currentTimeMillis();
	    TadminLog adminLog = (TadminLog) obj;
	    adminId=((TadminUser)request.getSession().getAttribute(SystemConstants.LOGINED)).getAdminId();
	    adminLog.setAdminId(adminId);
		adminLog.setCreatedTime(time);
	    adminLog.setContent(clazz+SystemConstants.LOG_SEPARATOR+method+SystemConstants.LOG_SEPARATOR+content);
	    adminuserLogService.createAdminLog(adminLog);
	}		

}
