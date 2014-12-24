/**   
* @Title: SystemConstants.java 
* @Package com.uswop.commons 
*
* @Description: 系统全局常量类
* 
* @date Sep 9, 2014 7:14:02 PM
* @version V1.0   
*/ 
package com.mpos.commons;

import java.util.HashMap;
import java.util.Map;

/** 
 * @ClassName: SystemConstants 
 * @Description: TODO
 * @author Phills Li
 * @date Sep 9, 2014 7:14:02 PM 
 *  
 */
public class SystemConstants {
	public static final String LOGINED="Logined";
	
	public static final String RIGHTS="rights";
	
	public static final String LOGIN_ERROR="Login_Error";
	
	public static final String LOGIN_STATUS="Login_Status";
	
	public static final String LOG_SUCCESS="success";
	
	public static final String LOG_FAILURE="failure";
	
	public static final String LOG_SEPARATOR="-";
	
	public static final String EMAIL_HOST="email_host";
	
	public static final String EMAIL_NAME="email_username";
	
	public static final String EMAIl_PASSWORD="email_password";
	
	public static final String MAX_LOGIN_ERROR_TIMES="max_login_error_times";
	
	public static final String LOGIN_ERROR_LOCK="login_error_locked";	
	
	public static final String TABLE_NAME_MENU="menu";
	public static final String TABLE_NAME_ATTRIBUTE="attribute";
	public static final String TABLE_NAME_PRODUCT="product";
	
	public static final String TABLE_FIELD_TITLE="title";
	public static final String TABLE_FIELD_CONTENT="content";
	public static final String TABLE_FIELD_PRODUCTNAME="productName";
	public static final String TABLE_FIELD_SHORTDESCR="shortDescr";
	public static final String TABLE_FIELD_FULLDESCR="fullDescr";
	
	public static final String CONFIG_CLIENT_PWD="config_client_pwd";
	public static final String CONFIG_CLIENT_LOGO="config_client_logo";
	public static final String CONFIG_API_TOKEN="config_api_token";
	public static final String CONFIG_DISPLAY_CURRENCY="config_display_currency";
	
	public static final Map<Integer, String> PROMOTION_TYPE=new HashMap<Integer, String>(){
	     private static final long serialVersionUID = 1L;
    {
		put(0, "Straight Down");
		put(1, "The Full Reduction");
		put(2, "Discount");
		put(3, "Combination");
		put(4, "X Give Y");
		put(5, "M For Y Discount");
		put(6, "Custom");
	}
    };
    
    public static final Map<Integer, String> ORDER_STATUS=new HashMap<Integer, String>(){
	     private static final long serialVersionUID = 1L;
   {
		put(0, "Waiting for Payment");
		put(1, "Payment Complete");
		put(2, "Rejected");
		
	}
   };
}
