/**   
* @Title: LoginController.java 
* @Package com.uswop.action 
*
* @Description: TODO
* 
* @date Sep 10, 2014 3:06:32 PM
* @version V1.0   
*/ 
package com.mpos.action;

import java.util.Calendar;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mpos.commons.EMailTool;
import com.mpos.commons.LogManageTools;
import com.mpos.commons.SecurityTools;
import com.mpos.commons.SystemConfig;
import com.mpos.commons.SystemConstants;
import com.mpos.dto.TadminLog;
import com.mpos.dto.TadminUser;
import com.mpos.dto.TemaiMessage;
import com.mpos.service.AdminUserService;
import com.mpos.service.AdminuserLogService;

/** 
 * @ClassName: LoginController 
 * @Description: 
 * @author Phills Li
 * @date Sep 10, 2014 3:06:32 PM 
 *  
 */
@Controller
public class LoginController extends BaseController {	
	@Autowired
	private AdminUserService adminUserService;		
	
    @Autowired
    private AdminuserLogService adminuserLogService;
	/** 
	 * <p>Open the login page</p>
	 * @Title: login 
	 * @return String
	 * @throws 
	 */ 
    private String log_content;
    
    @RequestMapping(value="/login",method=RequestMethod.GET)
	public ModelAndView login() {
		ModelAndView mav=new ModelAndView();
		TadminUser tUser=new TadminUser();		
		mav.addObject("user", tUser);
		mav.setViewName("login");
		return mav;
	}
		
	
	/** 
	 * <p>User Login</p>
	 * @Title: login 
	 * @param request
	 * @param user
	 * @return ModelAndView
	 * @throws 
	 */ 
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public ModelAndView login(HttpServletRequest request,TadminUser user){
		TadminLog adminLog = new TadminLog();
		adminLog.setAdminId(user.getAdminId());
		TadminUser tUser=(TadminUser)adminUserService.getAdminUserById(user.getAdminId());
		ModelAndView mav=new ModelAndView();
		Long time = (Long) request.getSession().getAttribute(SystemConstants.LOGIN_STATUS);
		if(time != null && System.currentTimeMillis()-time<60000*Integer.parseInt(SystemConfig.Admin_Setting_Map.get(SystemConstants.LOGIN_ERROR_LOCK))){
			    mav.addObject(ERROR_MSG_KEY,getMessage(request,"login.failed.locked",Integer.parseInt(SystemConfig.Admin_Setting_Map.get(SystemConstants.LOGIN_ERROR_LOCK))));
				if(tUser != null){
				  mav.addObject("user", tUser);
				}else{
					mav.addObject("user", new TadminUser());
				}
				adminLog.setLevel((short)1);
				log_content=SystemConstants.LOG_FAILURE+":userid locked.";
		}else if(tUser==null){
			mav.addObject(ERROR_MSG_KEY, getMessage(request,"login.failed.pwd.user"));
			mav.addObject("user", new TadminUser());
			saveLoginErrorTims(request);
			adminLog.setLevel((short)1);
			log_content=SystemConstants.LOG_FAILURE+":userid error";
		}else if(!tUser.getStatus() && !"admin".equals(tUser.getAdminId())){
			mav.addObject(ERROR_MSG_KEY, getMessage(request,"login.failed.pwd.user"));
			mav.addObject("user", new TadminUser());
			saveLoginErrorTims(request);
			adminLog.setLevel((short)1);
			log_content=SystemConstants.LOG_FAILURE+":userid error";
		}else if(!tUser.getAdminRole().getStatus() && !"admin".equals(tUser.getAdminId())){
			mav.addObject(ERROR_MSG_KEY, getMessage(request,"login.failed.pwd.user"));
			mav.addObject("user", new TadminUser());
			saveLoginErrorTims(request);
			adminLog.setLevel((short)1);
			log_content=SystemConstants.LOG_FAILURE+":userid error";
		}
		else if(!SecurityTools.MD5(user.getPassword()).equalsIgnoreCase(tUser.getPassword())){
			mav.addObject(ERROR_MSG_KEY, getMessage(request,"login.failed.pwd.user"));
			mav.addObject("user", tUser);
			saveLoginErrorTims(request);
			adminLog.setLevel((short)1);
			log_content=SystemConstants.LOG_FAILURE+":password error.";
		}else{
			String toUrl=(String)request.getSession().getAttribute(LOGIN_TO_URL);
			request.getSession().removeAttribute(LOGIN_TO_URL);
			request.getSession().removeAttribute(SystemConstants.LOGIN_ERROR);
			request.getSession().removeAttribute(SystemConstants.LOGIN_STATUS);
			if(StringUtils.isEmpty(toUrl)&&(tUser.getAdminRole().getRoleId()==1)){
				setSessionUser(request, tUser);
				toUrl="/home";
			}else if (StringUtils.isEmpty(toUrl)&&tUser.getAdminRole().getRoleId()!=1) {
				Long right = adminUserService.getRightByEmail(tUser.getEmail());
				setSessionUser(request, tUser,right);
				toUrl="home/storeHome";	
			}			
			mav.setViewName("redirect:"+toUrl);
			log_content=SystemConstants.LOG_SUCCESS+":login success.";
		}
		adminLog.setAdminId(user.getAdminId());
		LogManageTools.writeAdminLog(log_content,adminLog,request);
		return mav;
	}
	
	@RequestMapping(value="/ResetPassword",method=RequestMethod.POST)
	public ModelAndView resetPassword(HttpServletRequest request ,String email){
		ModelAndView mav = new ModelAndView();
		TadminLog adminLog = new TadminLog();
		TadminUser adminUser = adminUserService.getTadminUsersByEmail(email.toLowerCase());
		if(adminUser == null){
			 adminUser = new TadminUser();
			 
			 mav.addObject(ERROR_MSG_KEY,"email not exist.");
		}else{
			adminLog.setAdminId(adminUser.getAdminId());
		   
			String random = UUID.randomUUID().toString().trim().replace("-","").substring(0,6);
			adminUser.setPassword(SecurityTools.SHA1(random));
			adminUser.setUpdatedBy(adminUser.getAdminId());
			adminUser.setUpdatedTime(System.currentTimeMillis());
			TemaiMessage message = new TemaiMessage();
			message.setTo(email);
			message.setText("your account: "+adminUser.getAdminId()+" reset password :"+Calendar.getInstance().getTime()+" new password:"+random);
			message.setSubject("MPOS Password Reset");
			EMailTool.send(message);
			adminUserService.updateAdminUserPassword(adminUser);
			mav.addObject(ERROR_MSG_KEY,"password reset success.");
			LogManageTools.writeAdminLog(log_content, adminLog,request);
		}
		mav.addObject("user",adminUser);
		mav.setViewName("login");
		log_content="success:resert password success.";
		
		return mav;
	}
	
	/** 
	 * <p></p>
	 * @Title: logout 
	 * @param session
	 * @return String
	 * @throws 
	 */ 
	@RequestMapping(value="/logout")
	public String logout(HttpSession session,HttpServletRequest request){
		TadminLog adminLog = new TadminLog();
		if((TadminUser)session.getAttribute(SystemConstants.LOGINED)!=null){
		adminLog.setAdminId(((TadminUser)session.getAttribute(SystemConstants.LOGINED)).getAdminId());
		}
		log_content="success:login out.";
		LogManageTools.writeAdminLog(log_content, adminLog,request);
		session.removeAttribute(SystemConstants.LOGINED);
		session.invalidate();
		return "forward:login";
	}
}
