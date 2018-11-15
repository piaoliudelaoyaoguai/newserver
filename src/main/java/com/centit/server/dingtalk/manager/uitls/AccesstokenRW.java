package com.centit.server.dingtalk.manager.uitls;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


/**
 * 读写json文件
 * 
 * @author li_hao
 *
 */
public class AccesstokenRW {

	/**
	 * 本地保存的accesstoken.json文件的地址
	 */
	public static final String getACCESS_TOKEN_AREA(){
		String accessTokenurl = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		System.out.println("systemUrl："+accessTokenurl);
		if("acs".equals(accessTokenurl.substring(1,4))){
			accessTokenurl = (accessTokenurl.substring(0,accessTokenurl.length()-16)) + "WEB-INF/classes/accesstoken.json";//阿里聚石塔
		}else if("usr".equals(accessTokenurl.substring(1,4))){
    		accessTokenurl = (accessTokenurl.substring(0,accessTokenurl.length()-16)) + "WEB-INF/classes/accesstoken.json";//linux
    	}else{
    		accessTokenurl = (accessTokenurl.substring(1,accessTokenurl.length()-16)) + "WEB-INF/classes/accesstoken.json";//windows
    	}
		System.out.println("accesstokenUrl："+accessTokenurl);
		return accessTokenurl;
	}
	
	
	
	/**
	 * 从json文件中获取Access_Token
	 * @param path
	 * @return
	 */
	public static JSONObject getAccess_Token(String path){
		String JsonContext = ReadFile(path);
		JSONArray jsonArray = JSONArray.parseArray(JsonContext);
		JSONObject jsonObject = new JSONObject();
		int size = jsonArray.size();
		for (int i = 0; i < size; i++) {
			JSONObject jsonObject2 = jsonArray.getJSONObject(i);
			jsonObject.put("token", jsonObject2.get("token"));
			jsonObject.put("expiresIn", jsonObject2.get("expiresIn"));
		}
		return jsonObject;
	}

	/**
	 * 读取json文件
	 * 
	 * @param Path
	 * @return
	 */
	public static String ReadFile(String Path) {
		BufferedReader reader = null;
		String laststr = "";
		try {
			FileInputStream fileInputStream = new FileInputStream(Path);
			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
			reader = new BufferedReader(inputStreamReader);
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				laststr += tempString;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return laststr;
	}

	/**
	 * 写入json文件
	 * @param filePath
	 * @param sets
	 * @throws IOException
	 */
	public static void writeFile(String filePath, String str) throws IOException {
		FileWriter fw = new FileWriter(filePath);
		PrintWriter out = new PrintWriter(fw);
		out.write("[" + str + "]");
		out.println();
		fw.close();
		out.close();
	}

}