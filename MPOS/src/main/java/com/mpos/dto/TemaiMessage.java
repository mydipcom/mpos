package com.mpos.dto;

import java.io.File;
import java.util.List;
import java.util.Map;

public class TemaiMessage {
	public static final String CREATE_SUBJECT = "欢迎使用凯瑞时代云菜单服务";
	public static final String RESET_SUBJECT = "凯瑞时代云菜单密码重置";
	private String to;
	private String cc[];
	private Boolean isHtml;
	private String subject;
	private String text;
	private List<File> files;
	
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String[] getCc() {
		return cc;
	}
	public void setCc(String[] cc) {
		this.cc = cc;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public List<File> getFiles() {
		return files;
	}
	public void setFiles(List<File> files) {
		this.files = files;
	}
	public Boolean getIsHtml() {
		return isHtml;
	}
	public void setIsHtml(Boolean isHtml) {
		this.isHtml = isHtml;
	}
	
	/*<div style="background-color:#323E4F;color:#FFFFFF">
	<h3>您好 381551030@qq.com</h3>
	<div style="margin-bottom:30px;margin-top:30px">欢迎注册凯瑞时代云菜单服务！以下为您注册的账号信息：</div>
	<div>登录账号：381551030@qq.com</div>
	<div>订购服务：免费体验版</div>
	<div>生效时间：2015-05-01</div>
	<div>到期时间：2015-11-11</div>
	<div style="margin-top:30px">谢谢</div>
	<div>凯瑞时代云菜单客服中心</div>
	<div>客服电话：028-83361785</div>
	<div style="border:solid #FFF 1px;margin-top:30px"></div>
	<div style="margin-top:20px">此邮件有系统自动发出，请勿直接回复！</div>
	</div>*/
	public static TemaiMessage getCreate(Map<String, String> map){
		StringBuffer sb = new StringBuffer();
		sb.append("<body style=\"background-color:#CFCDCD;font-color:#1A1A1A\">");
		sb.append("<div style=\"background-color:#CFCDCD;font-color:#1A1A1A\">");
		sb.append("<h3>您好: "+map.get("email")+"</h3>");
		sb.append("<div style=\"margin-bottom:30px;margin-top:30px\">欢迎注册凯瑞时代云菜单服务！以下为您注册的服务详情：</div>");
		sb.append("<div style=\"margin-top:30px\"><h3>账号信息：</h3></div>");
		sb.append("<div>后台登录地址：<a href='"+map.get("url")+"/login'>"+map.get("url")+"/login</a></div>");
		sb.append("<div>登录账号："+map.get("email")+"</div>");
		sb.append("<div>订购服务："+map.get("serviceName")+"</div>");
		sb.append("<div>生效时间："+map.get("startTime")+"</div>");
		sb.append("<div>到期时间："+map.get("endTime")+"</div>");
		sb.append("<div style=\"margin-top:30px\"><h3>店铺信息：</h3></div>");
		sb.append("<div>门店编号："+map.get("storeCode")+"    (不可更改)</div>");
		sb.append("<div>初始访问密码："+map.get("publicKey")+"  (请登录后台后，在店铺设置中进行更改)</div>");
		sb.append("<div style=\"margin-top:30px\"><h3>第一次使用时，需在iPad/Android客户端进行设置：</h3></div>");
		sb.append("<div><img src='cid:image'></div>");
		sb.append("<div style=\"margin-top:30px\">谢谢</div>");
		sb.append("<div>凯瑞时代云菜单客服中心</div>");
		sb.append("<div>客服电话：028-83361785</div>");
		sb.append("<div style=\"border:solid #FFFFFF 1px;margin-top:30px\"></div>");
		sb.append("<div style=\"margin-top:20px\">此邮件由系统自动发出，请勿直接回复！</div>");
		sb.append("</div>");
		sb.append("</body>");
		TemaiMessage meg = new TemaiMessage();
		meg.setIsHtml(true);
		meg.setText(sb.toString());
		meg.setSubject(CREATE_SUBJECT);
		meg.setTo(map.get("email"));
		return meg;
	}
	
	public static TemaiMessage getRest(Map<String, String> map){
		StringBuffer sb = new StringBuffer();
		sb.append("<body style=\"background-color:#CFCDCD;font-color:#1A1A1A\">");
		sb.append("<div style=\"background-color:#CFCDCD;font-color:#1A1A1A\">");
		sb.append("<h3>您好: "+map.get("email")+"</h3>");
		sb.append("<div style=\"margin-bottom:30px;margin-top:30px\">欢迎使用凯瑞时代云菜单服务！以下为您的账号密码重置地址：</div>");
		sb.append("<div  style=\"margin-top:30px\"><a href='"+map.get("url")+"/common/reset?code="+map.get("code")+"'>点击链接重置密码</a></div>");
		sb.append("<div  style=\"margin-top:30px;margin-bottom:30px\">或者复制以下地址在浏览器上打开</div>");
		sb.append("<div  style=\"margin-bottom:30px\">"+map.get("url")+"/common/reset?code="+map.get("code")+"</div>");
		sb.append("<div>此链接有效时间为10分钟，请及时重置密码</div>");
		sb.append("<div style=\"margin-top:30px\">谢谢</div>");
		sb.append("<div>凯瑞时代云菜单客服中心</div>");
		sb.append("<div>客服电话：028-83361785</div>");
		sb.append("<div style=\"border:solid #FFFFFF 1px;margin-top:30px\"></div>");
		sb.append("<div style=\"margin-top:20px\">此邮件由系统自动发出，请勿直接回复！</div>");
		sb.append("</div>");
		sb.append("</body>");
		TemaiMessage meg = new TemaiMessage();
		meg.setIsHtml(true);
		meg.setSubject(RESET_SUBJECT);
		meg.setTo(map.get("email"));
		meg.setText(sb.toString());
		return meg;
	}
	
}
