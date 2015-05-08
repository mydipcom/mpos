package com.mpos.commons;
import java.util.ResourceBundle;

public final class LoadConfig { 
	private static final ResourceBundle bundle = java.util.ResourceBundle.getBundle("config");
	/**
	 * 通过键获取值
	 * @param key
	 * @return
	 */
	public static final String get(String key) {
		return bundle.getString(key);
	}

    public static void main(String args[]){ 
        System.out.println(get("pid")); 
    } 
}