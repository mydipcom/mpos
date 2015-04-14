/**   
* @Title: BaseController.java 
* @Package com.uswop.action 
*
* @Description: 积分管理系统
* 
* @date Sep 10, 2014 3:27:05 PM
* @version V1.0   
*/ 
package com.mpos.action;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.Assert;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.support.RequestContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mpos.commons.SystemConfig;
import com.mpos.commons.SystemConstants;
import com.mpos.dto.TadminUser;
import com.mpos.dto.Tcategory;
import com.mpos.dto.Tmenu;
import com.mpos.dto.Tproduct;
import com.mpos.dto.TproductRelease;
import com.mpos.dto.Ttable;
import com.mpos.model.AddProductModel;
import com.mpos.model.DataTableParamter;
import com.mpos.model.LoginFailureModel;


/** 
 * <p>Spring控制器的基类</p>
 * <此类实现了一些控制器处理类通用的方法，实际的业务控制器可以根据需要继承此类>
 * @ClassName: BaseController 
 * @author Phills Li 
 *  
 */ 
public class BaseController {
	
	protected final String ERROR_MSG_KEY="errorMsg";
	protected final String LOGIN_TO_URL="LoginToUrl";
	protected final String Lift_Flag="liftflag";
	
	/** 
	 * <p>Description:Get the login user from session</p>
	 * @Title: getSessionUser 
	 * @param request
	 * @return User
	 * @throws 
	 */ 
	protected TadminUser getSessionUser(HttpServletRequest request){
		return (TadminUser)request.getSession().getAttribute(SystemConstants.LOGINED);
	}
		
	/** 
	 * <p>Description:Save the login user into session</p>
	 * @Title: setSessionUser 
	 * @param request
	 * @param user
	 * @throws 
	 */ 
	protected void setSessionUser(HttpServletRequest request,TadminUser user){
		request.getSession().setAttribute(SystemConstants.LOGINED, user);
		if(user.getAdminRole().getRoleId()!=1){
		setSessionRights(request,user.getAdminRole().getAdminRoleRights().getRoleRights());
		}
	}
	
	/**
	 * <p>Description:Get the user rights from session</p>
	 * @Title: getSessionRights 
	 * @param request
	 * @return
	 * @throws
	 */
	protected long getSessionRights(HttpServletRequest request){
		Object obj=request.getSession().getAttribute(SystemConstants.RIGHTS);
		if(obj==null){
			return 0;
		}
		else{
			return (Long)obj;
		}
	}
	
	/**
	 * <p>Description:Save the user rights into session</p>
	 * @Title: setSessionRights 
	 * @param request
	 * @param rights
	 * @throws
	 */
	protected void setSessionRights(HttpServletRequest request,long rights){
		request.getSession().setAttribute(SystemConstants.RIGHTS, rights);		
	}
	
		
	/** 
	 * <p>获取基于应用程序的url的绝对路径</p>
	 * @Title: getAppBaseUrl 
	 * @param request
	 * @param url
	 * @return String
	 * @throws 
	 */ 
	public final String getAppBaseUrl(HttpServletRequest request,String url){
		Assert.hasLength(url,"url不能为空");
		Assert.isTrue(url.startsWith("/"),"必须以/开头");
		return request.getContextPath()+url;
	}
	
	public Map<String,Object> getHashMap(){
		return new HashMap<String,Object>();
	}
	
	/**
	 * <p>Description:根据资源code获取资源文件中对应的消息</p>
	 * @Title: getMessage 
	 * @param request
	 * @param code
	 * @param args
	 * @return
	 * @throws
	 */
	public String getMessage(HttpServletRequest request,String code,Object[] args){
//		ApplicationContext ctx =WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());		
//		MessageSource ms=(MessageSource)ctx.getBean("messageSource");
		RequestContext requestContext = new RequestContext(request);
		if(args==null){
			return requestContext.getMessage(code);
		}
		return requestContext.getMessage(code, args);
	}
	
	public String getLocale(HttpServletRequest request){
		Locale locale=(Locale)request.getSession().getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);
		if(locale==null){
			return "en_US";
		}
		return locale.toString();
		
	}
	
	/**
	 * <p>Description:根据资源code获取资源文件中对应的消息</p>
	 * @Title: getMessage 
	 * @param request
	 * @param code
	 * @param arg
	 * @return
	 * @throws
	 */
	public String getMessage(HttpServletRequest request,String code,Object arg){				
		return getMessage(request,code, new Object[]{arg});
	}
	
	/**
	 * <p>Description:根据资源code获取资源文件中对应的消息</p>
	 * @Title: getMessage 
	 * @param request
	 * @param code
	 * @return
	 * @throws
	 */
	public String getMessage(HttpServletRequest request,String code){				
		return getMessage(request,code, null);
	}
	
	/**
	 * <p>Description:改变当前语言环境</p>
	 * @Title: changeLocale 
	 * @param request
	 * @param locale
	 * @throws
	 */
	public void changeLocale(HttpServletRequest request,Locale locale){
		RequestContext requestContext = new RequestContext(request);
		requestContext.changeLocale(locale);
	}
	
	/**
	 * <p>Description:改变当前语言环境</p>
	 * @Title: changeLocale 
	 * @param request
	 * @param locale
	 * @throws
	 */
	public void changeLocale(HttpServletRequest request,String localeStr){
		RequestContext requestContext = new RequestContext(request);
		if(localeStr!=null&&!localeStr.isEmpty()){
			String[] strs=localeStr.split("—");			
			if(strs.length==1){
				Locale locale=new Locale(strs[0]);
				requestContext.changeLocale(locale);
			}
			else if(strs.length==2){
				Locale locale=new Locale(strs[0],strs[1]);
				requestContext.changeLocale(locale);
			}	
		}		
	}
	/**
	 * 添加当前登录用户过滤条件
	 * @param request
	 * @param dtp
	 */
	public void addStoreCondition(HttpServletRequest request,DataTableParamter dtp){
		String jsonStr = dtp.getsSearch();
		Integer storeId = getSessionUser(request).getStoreId();
		JSONObject json = (JSONObject) JSON.parse(jsonStr);
		if(json==null){
			json = new JSONObject();
		}
		json.put("storeId", storeId);
		dtp.setsSearch(JSON.toJSONString(json));
	}
	/**
	 * 绑定当前登录用户店铺信息
	 * @param bean
	 * @param request
	 */
	public void addStore(Object bean,HttpServletRequest request){
		Integer storeId = getSessionUser(request).getStoreId();
		if(bean instanceof Tcategory){
			((Tcategory)bean).setStoreId(storeId);
		}else if(bean instanceof Tmenu){
			((Tmenu)bean).setStoreId(storeId);
		}else if(bean instanceof Tproduct){
			((Tproduct)bean).setStoreId(storeId);
		}else if(bean instanceof TproductRelease){
			((TproductRelease)bean).setStoreId(storeId);
		}else if(bean instanceof Ttable){
			((Ttable)bean).setStoreId(storeId);
		}else if(bean instanceof TadminUser){
			((TadminUser)bean).setStoreId(storeId);
		}else if(bean instanceof AddProductModel){
			((AddProductModel)bean).setStoreId(storeId);
		}
	}
	
	/**
	 * <p>Description:获取登录错误次数</p>
	 * @Title:getLoginFailureTimes
	 * @param request
	 * @return LoginFailureModel
	 */
	public LoginFailureModel getLoginFailureTimes(HttpServletRequest request){
		return (LoginFailureModel) request.getSession().getAttribute(SystemConstants.LOGIN_ERROR);
	}
	
	/**
	 * <p>Description:save login error info</p>
	 * @Title:saveLoginErrorTims
	 * @param request
	 * 
	 */
	public void saveLoginErrorTims(HttpServletRequest request){
		LoginFailureModel lfm = new LoginFailureModel();
		lfm = getLoginFailureTimes(request);
		int count = 1 ;
		if(lfm == null){
			lfm = new LoginFailureModel();
			lfm.setTime(System.currentTimeMillis());
			lfm.setCount(count);
			request.getSession().setAttribute(SystemConstants.LOGIN_ERROR, lfm);
			if(Integer.parseInt(SystemConfig.Admin_Setting_Map.get(SystemConstants.MAX_LOGIN_ERROR_TIMES))==1){
				request.getSession().removeAttribute(SystemConstants.LOGIN_ERROR);
				request.getSession().setAttribute(SystemConstants.LOGIN_STATUS, System.currentTimeMillis());
			}
		}else{
			Long time_interval = System.currentTimeMillis()-lfm.getTime();
			if(time_interval>(6000*Integer.parseInt(SystemConfig.Admin_Setting_Map.get(SystemConstants.LOGIN_ERROR_LOCK)))){
				lfm.setTime(System.currentTimeMillis());
				lfm.setCount(count);
				request.getSession().setAttribute(SystemConstants.LOGIN_ERROR, lfm);
			}else{
				int error_count=lfm.getCount();
				if(error_count == (Integer.parseInt(SystemConfig.Admin_Setting_Map.get(SystemConstants.MAX_LOGIN_ERROR_TIMES))-1)){
					request.getSession().removeAttribute(SystemConstants.LOGIN_ERROR);
					request.getSession().setAttribute(SystemConstants.LOGIN_STATUS, System.currentTimeMillis());
				}else{
					lfm.setTime(System.currentTimeMillis());
					lfm.setCount(error_count+1);
					request.getSession().setAttribute(SystemConstants.LOGIN_ERROR, lfm);
				}
			}
		}
		
	}
}
