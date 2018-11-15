package com.centit.server.dingtalk.manager.uitls;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Properties;

@SuppressWarnings("rawtypes")

/**
 * <p>properties文件读写工具类<p>
 * @version 1.0
 * @author li_hao
 * @date 2017年6月12日
 */
public class PropertyUtil {
	
	
	/**  
    * properties文件根据主键key读取主键的值value  
    * @param filePath 属性文件名  
    * @param keyName 键名  
    */
	public static String readProperty(String proFileName,String keyName){
		String retStr = null;
		try {
			Properties prop = new Properties();
			Class clazz = PropertyUtil.class;
			// 开头的'/'表示classpath的根目录，这个是表示从classpath的根目录中开始查找资源，如果开头没有'/'，表示从当前这个class所在的包中开始查找
			InputStreamReader fileReader = new InputStreamReader(clazz.getResourceAsStream("/"+proFileName),"UTF-8");
			prop.load(fileReader);
			retStr = prop.getProperty(keyName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return retStr;
	}
	
	/**
	 * 更新properties文件的键值对  
	 * 如果该主键已经存在，更新该主键的值；  
	 * 如果该主键不存在，则插入一对键值。 
	 * @param proFileName 配置文件名称
	 * @param keyName 键名
	 * @param keyValue 键值
	 * @return
	 */
	public static boolean writeProperty(String proFileName,String keyName,String keyValue){
		boolean flag = false;
		try {
			Class clazz = PropertyUtil.class;
			String filePath  = clazz.getResource("/"+proFileName).getPath();
			System.out.println(filePath);
			Properties prop = new Properties();
			prop.load(new FileInputStream(filePath));   
			
			OutputStream oFile = new FileOutputStream(filePath);
			prop.setProperty(keyName, keyValue);
	        prop.store(oFile, "Update '" + keyName + "' value");
	        oFile.close();
	        flag = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return flag;
	}

	//D:\workspace\lihao\meetingserver\src\main\resources
	public static void main(String[] args) {
		String readProperty = readProperty("dingtalk.properties", "news_title");
		System.out.println(readProperty);
	}
}
