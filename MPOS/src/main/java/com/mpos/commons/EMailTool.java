package com.mpos.commons;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.mpos.dto.TemaiMessage;
public class EMailTool {


	
	 public static void send(TemaiMessage temaiMessage){
		try {
			JavaMailSender javaMailSenderImpl=getJavaMailSenderImpl();
			MimeMessage message = javaMailSenderImpl.createMimeMessage();
			/*String nick = "";
			try {
				nick = MimeUtility.decodeText("凯瑞时代云服务");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			String from = nick+"<"+SystemConfig.Admin_Setting_Map.get(SystemConstants.EMAIL_NAME)+">";
			message.setSubject(temaiMessage.getSubject());
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO,new InternetAddress(temaiMessage.getTo()));
			MimeMultipart multipart = new MimeMultipart("related");
	        // first part  (the html)
	        BodyPart messageBodyPart = new MimeBodyPart();
	        messageBodyPart.setContent(temaiMessage.getText(), "text/html");
	        // add it
	        multipart.addBodyPart(messageBodyPart);
	        // second part (the image)
	        messageBodyPart = new MimeBodyPart();
	        if(temaiMessage.getFiles()!=null&&temaiMessage.getFiles().size()>0){
            	for (File file : temaiMessage.getFiles()) {
            		  DataSource fds = new FileDataSource (file);
          	          messageBodyPart.setDataHandler(new DataHandler(fds));
          	         messageBodyPart.setHeader("Content-ID","<image>");
          	        // add it
          	        multipart.addBodyPart(messageBodyPart);
				}
            }
	        // put everything together
	        message.setContent(multipart);*/

			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "utf-8");
			String nick = "";
			try {
				nick = MimeUtility.decodeText("凯瑞时代云服务");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			String from = nick+"<"+SystemConfig.Admin_Setting_Map.get(SystemConstants.EMAIL_NAME)+">";
            messageHelper.setFrom(from);
            if(temaiMessage.getTo() !=null && !temaiMessage.getTo().isEmpty()){
            	messageHelper.setTo(temaiMessage.getTo());
            }
            if(temaiMessage.getSubject() !=null){
            	messageHelper.setSubject(temaiMessage.getSubject());
            }
            messageHelper.setText(temaiMessage.getText(), temaiMessage.getIsHtml());
	        if(temaiMessage.getFiles()!=null&&temaiMessage.getFiles().size()>0){
	        	 MimeMultipart multipart =messageHelper.getMimeMultipart();
	 	        BodyPart messageBodyPart = new MimeBodyPart();
            	for (File file : temaiMessage.getFiles()) {
            		  DataSource fds = new FileDataSource (file);
          	          messageBodyPart.setDataHandler(new DataHandler(fds));
          	         messageBodyPart.setHeader("Content-ID","<image>");
          	        multipart.addBodyPart(messageBodyPart);
				}
            }
			javaMailSenderImpl.send(message);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	 
	public static JavaMailSenderImpl getJavaMailSenderImpl(){
	    JavaMailSenderImpl javaMailSenderImpl = new JavaMailSenderImpl();
	    javaMailSenderImpl.setHost(SystemConfig.Admin_Setting_Map.get(SystemConstants.EMAIL_HOST));
	    javaMailSenderImpl.setUsername(SystemConfig.Admin_Setting_Map.get(SystemConstants.EMAIL_NAME));
	    javaMailSenderImpl.setPassword(SystemConfig.Admin_Setting_Map.get(SystemConstants.EMAIl_PASSWORD));
	    Properties properties = new Properties();
	    properties.setProperty("mail.smtp.auth", "true");
	    properties.setProperty("mail.smtp.timeout", "30000");
	    properties.setProperty("mail.transport.protocol", "smtp");  
	    properties.setProperty("mail.smtp.port", "25");  
	    properties.setProperty("mail.smtp.auth", "true");  
	    //properties.setProperty("mail.debug","true");  

	    javaMailSenderImpl.setJavaMailProperties(properties);
	    return javaMailSenderImpl;
     }

}
