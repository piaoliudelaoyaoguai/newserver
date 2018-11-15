package com.centit.server.webmgr.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class DocUtil {
	
	
	/**
	 * 项目文件操作地址
	 */
	public static final String getfileUrl(){
		String fileUrl = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		if("acs".equals(fileUrl.substring(1,4))){
			fileUrl = (fileUrl.substring(0,fileUrl.length()-16)) + "WEB-INF/classes/file/";//阿里聚石塔
		}else if("usr".equals(fileUrl.substring(1,4))){
			fileUrl = (fileUrl.substring(0,fileUrl.length()-16)) + "WEB-INF/classes/file/";//linux
    	}else if("data".equals(fileUrl.substring(1,5))){
    		fileUrl = (fileUrl.substring(0,fileUrl.length()-16)) + "WEB-INF/classes/file/";//linux
    	}else{
    		fileUrl = (fileUrl.substring(1,fileUrl.length()-16)) + "WEB-INF/classes/file/";//windows
    	}
		return fileUrl;
	}
	
	

	/**
	 * 创建word文档
	 * @param dataMap 写入文档的内容
	 * @param path  ftl文件所在文件夹位置（如：D:/testfile/）
	 * @param fileName  ftl文件名
	 * @param outPath  word文件输出文件夹位置（如：D:/testfile/）
	 * @return outFilePath word文件输出地址（如：D:/testfile/xxx.doc）
	 */
	public static String createWord(HashMap<String, Object> dataMap, String path, String fileName,String outPath){
		String outFileName = null;
		Template t=null;
		try {
			Configuration configuration = new Configuration();
			configuration.setDefaultEncoding("UTF-8");
			configuration.setDirectoryForTemplateLoading(new File(path));
			t = configuration.getTemplate(fileName); //文件名
		} catch (IOException e) {
			e.printStackTrace();
		}
		outFileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()).toString() +".doc";
		String outFilePath = outPath + outFileName;
		
		//如果文件夹不存在则创建 
		File outPathFolder = new File(outPath);
		if(!outPathFolder.exists() && !outPathFolder.isDirectory()){       
			outPathFolder.mkdir();    
		}
		
		File outFile = new File(outFilePath);
		Writer out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile)));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
        try {
			t.process(dataMap, out);
			if(out != null){
				out.close();
			}
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return outFileName;
	}
	
	
	public static void main(String[] args) {
//		HashMap<String, Object> dataMap = new HashMap<>();
//		
//		dataMap.put("title", "参会人员名单");
//		dataMap.put("year", "2017");
//		dataMap.put("month", "08");
//		dataMap.put("day", "04");
////		dataMap.put("number", 1);
////		dataMap.put("content", "内容"+2);
//		
//		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
//		for (int i = 0; i < 10; i++) {
//			System.out.println(i);
//			Map<String,Object> map = new HashMap<String,Object>();
//			map.put("no", i);
//			map.put("name", "张三");
//			map.put("position", "江苏省南京市发改委机要局局长");
//			map.put("mark", "发改委");
//			list.add(map);
//		}
//		
//		
//		dataMap.put("list", list);
//		System.out.println(dataMap);
//		createWord(dataMap, "D:/testfile/", "personModel.ftl", "D:/testfile/");
		System.out.println();
	}
	
	
}
