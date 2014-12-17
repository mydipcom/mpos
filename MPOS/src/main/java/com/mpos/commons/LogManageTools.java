package com.mpos.commons;
import com.mpos.dto.TadminLog;
import com.mpos.service.AdminuserLogService;


public class LogManageTools {
	private static long time;
	private static String clazz;
	private static String method;
	
	
	
	public static void writeAdminLog(String content,Object obj){
		StackTraceElement[] ste = new Exception().getStackTrace();
		AdminuserLogService adminuserLogService = (AdminuserLogService) MyApplicationContextUtil.getContext().getBean("adminuserLogService");
	    clazz=ste[1].getClassName();
		method=ste[1].getMethodName();
		time=System.currentTimeMillis();
	    TadminLog adminLog = (TadminLog) obj;
		adminLog.setCreatedTime(time);
	    adminLog.setContent(clazz+SystemConstants.LOG_SEPARATOR+method+SystemConstants.LOG_SEPARATOR+content);
	    adminuserLogService.createAdminLog(adminLog);
	}		

}
