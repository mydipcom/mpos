/**   
 * @Title: ObjectPeropertyMapping.java 
 * @Package com.bps.commons 
 *
 * @Description: User Points Management System
 * 
 * @date Nov 8, 2014 4:32:19 PM
 * @version V1.0   
 */ 
package com.mpos.commons;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.alibaba.fastjson.JSONObject;

/** 
 * <p>Types Description</p>
 * @ClassName: ObjectPeropertyMapping 
 * @author Phills Li 
 * 
 */
public class ConvertTools {
	
	public static  SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	/**
     * 当月第一天
     * @return
	 * @throws ParseException 
     */
    public static long getFirstDay(){
        Calendar calendar = Calendar.getInstance();
        Date theDate = calendar.getTime();
        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(theDate);
        gcLast.set(Calendar.DAY_OF_MONTH, 1);
        String day_first = df.format(gcLast.getTime());
        StringBuffer str = new StringBuffer().append(day_first).append(" 00:00:00");
        try {
			return sdf.parse(str.toString()).getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return 0L;
    }
    
    /**
     * 当月最后一天
     * @return
     */
    public static long getLastDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);    //加一个月
        calendar.set(Calendar.DATE, 1);        //设置为该月第一天
        calendar.add(Calendar.DATE, -1);   
        Date theDate = calendar.getTime();
        String s = df.format(theDate);
        StringBuffer str = new StringBuffer().append(s).append(" 23:59:59");
        try {
			return  sdf.parse(str.toString()).getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return 0L;
    }
    
	
	public static void json2Model(JSONObject jsonObj,Object model){
		Field[] fields=model.getClass().getDeclaredFields(); 		
		for (Field field : fields) {
			String name=field.getName();
			if(jsonObj.containsKey(name)){
				try {
					if(field.get(model) instanceof String){
						field.set(model, jsonObj.getString(name));
					}
					else if(field.get(model) instanceof Integer){
						field.setInt(model, jsonObj.getInteger(name));
					}
					else if(field.get(model) instanceof Long){
						field.setLong(model, jsonObj.getLong(name));
					}
					else if(field.get(model) instanceof Short){
						field.setShort(model, jsonObj.getShort(name));
					}
					else if(field.get(model) instanceof Float){
						field.setFloat(model, jsonObj.getFloat(name));
					}
					else if(field.get(model) instanceof Double){
						field.setDouble(model, jsonObj.getDouble(name));
					}
					else if(field.get(model) instanceof Byte){
						field.setByte(model, jsonObj.getByte(name));
					}
					else if(field.get(model) instanceof Boolean){
						field.setBoolean(model, jsonObj.getBoolean(name));
					}
					else if(field.get(model) instanceof Object){
						field.set(model, jsonObj.get(name));
					}
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}catch (IllegalAccessException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			
		}
	}
	
	public static Integer[] stringArr2IntArr(String[] strArr){
		Integer[] idArr=new Integer[strArr.length];
		for (int i = 0; i < idArr.length; i++) {
			idArr[i]=Integer.parseInt(strArr[i]);
		}
		return idArr;
	}
	
	public static String longToDateString(Long dateLong){
		if(dateLong==null){
			return "";
		}
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
		return sdf.format(new Date(dateLong));
	}
	
	public static long dateStringToLong(String date) throws ParseException{
		SimpleDateFormat sdf =new SimpleDateFormat("dd/MM/yyyy hh:mm");
		return sdf.parse(date).getTime();
	}
	
	public static long dateString2Long(String date) throws ParseException{
		SimpleDateFormat sdf =new SimpleDateFormat("dd-MM-yyyy");
		return sdf.parse(date).getTime();
	}
	
	public static long dateString2Long(String date,String format) throws ParseException{
		SimpleDateFormat sdf =new SimpleDateFormat(format);
		return sdf.parse(date).getTime();
	}
	
	public static long longTimeAIntDay(long time,int days){
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		calendar.add(Calendar.DAY_OF_YEAR, days);
		return calendar.getTimeInMillis();
	}
	
    /**  
     * 对象转数组  
     * @param obj  
     * @return  
     */  
    public static byte[] toByteArray (Object obj) {      
        byte[] bytes = null;      
        ByteArrayOutputStream bos = new ByteArrayOutputStream();      
        try {        
            ObjectOutputStream oos = new ObjectOutputStream(bos);         
            oos.writeObject(obj);        
            oos.flush();         
            bytes = bos.toByteArray ();      
            oos.close();         
            bos.close();        
        } catch (IOException ex) {        
            ex.printStackTrace();   
        }      
        return bytes;    
    }   
       
    /**  
     * 数组转对象  
     * @param bytes  
     * @return  
     */  
    public static Object toObject (byte[] bytes) {      
    	Object obj = null;      
        try {        
            ByteArrayInputStream bis = new ByteArrayInputStream (bytes);        
            ObjectInputStream ois = new ObjectInputStream (new BufferedInputStream(bis));        
            obj =  ois.readObject();      
            ois.close();   
            bis.close();   
        } catch (IOException ex) {        
            ex.printStackTrace();   
        } catch (ClassNotFoundException ex) {        
            ex.printStackTrace();   
        }      
        return obj;    
    }
    /**
     * 
     * @param id待补位数字
     * @param ws 一共多少位
     * @param qz 前缀
     * @return
     */
    public static String bw(Integer id,int ws,String qz){
    	String ids = id+"";
    	int length = ids.length();
    	int cw = ws-qz.length();
    	if(length<cw){
    		int xx = cw-length;
    		for (int i = 0; i < xx; i++) {
    			qz+="0";
			}
    		qz+=ids;
    	}
    	return qz;
    }
    
    public static boolean xml2String(String xml){
		SAXReader reader = new SAXReader();  
		Document document = null;
		try {
			InputStream in = new ByteArrayInputStream(xml.getBytes("UTF-8"));
			document = reader.read(in);
			Element rootElm = document.getRootElement();  
			Element root1Elm = rootElm.element("is_success");  
			String status = root1Elm.getTextTrim();
			if(status!=null&&xml.equals("T")){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}  
		return false;
		    
	}
    
    public static void main(String[] args) {
		System.out.println(bw(12,8,"S"));
	}
    
}
