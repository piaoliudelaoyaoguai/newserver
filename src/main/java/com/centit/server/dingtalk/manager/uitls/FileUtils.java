package com.centit.server.dingtalk.manager.uitls;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>文件操作工具类<p>
 * @version 1.0
 * @author li_hao
 * @date 2017年1月18日
 */
@SuppressWarnings({"resource","unused"})
public class FileUtils {
	
	/**
	 * 字符串转数组
	 * @param str 字符串
	 * @param splitStr 分隔符
	 * @return
	 */
	public static String[] StringToArray(String str,String splitStr){
		String[] arrayStr = null;
    	if(!"".equals(str) && str !=null){
			if(str.indexOf(splitStr)!=-1){
				arrayStr = str.split(splitStr);
			}else{
				arrayStr = new String[1];
				arrayStr[0] = str;
			}
		}
		return arrayStr;
	}
	
	/**
	 * 获取文件夹下所有文件的名称 + 模糊查询（当不需要模糊查询时，queryStr传空或null即可）
	 * 1.当路径不存在时，map返回retType值为1
	 * 2.当路径为文件路径时，map返回retType值为2，文件名fileName值为文件名
	 * 3.当路径下有文件夹时，map返回retType值为3，文件名列表fileNameList，文件夹名列表folderNameList
	 * @param folderPath 路径
	 * @param queryStr 模糊查询字符串
	 * @return
	 */
	public static HashMap<String, Object> getFilesName(String folderPath , String queryStr) {
		HashMap<String, Object> map = new HashMap<>();
		List<String> fileNameList = new ArrayList<>();//文件名列表
		List<String> folderNameList = new ArrayList<>();//文件夹名列表
        File f = new File(folderPath);
        if (!f.exists()) { //路径不存在
        	map.put("retType", "1");
        }else{
        	boolean flag = f.isDirectory();
            if(flag==false){ //路径为文件
            	map.put("retType", "2");
            	map.put("fileName", f.getName());
            }else{ //路径为文件夹
            	map.put("retType", "3");
            	File fa[] = f.listFiles();
            	queryStr = queryStr==null ? "" : queryStr;//若queryStr传入为null,则替换为空（indexOf匹配值不能为null）
                for (int i = 0; i < fa.length; i++) {
                    File fs = fa[i];
                    if(fs.getName().indexOf(queryStr)!=-1){
                    	 if (fs.isDirectory()) {
                         	folderNameList.add(fs.getName());
                         } else {
                         	fileNameList.add(fs.getName());
                         }
             		}
                }
                map.put("fileNameList", fileNameList);
                map.put("folderNameList", folderNameList);
            }
        }
		return map;
    }
	
	/**
     * 以行为单位读取文件，读取到最后一行
     * @param filePath
     * @return
     */
    public static List<String> readFileContent(String filePath) {  
        BufferedReader reader = null;
        List<String> listContent = new ArrayList<>();
        try {  
            reader = new BufferedReader(new FileReader(filePath));  
            String tempString = null;  
            int line = 1;  
            // 一次读入一行，直到读入null为文件结束 
            while ((tempString = reader.readLine()) != null) {
            	listContent.add(tempString);
                line++;
            }  
            reader.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            if (reader != null) {  
                try {  
                    reader.close();  
                } catch (IOException e1) {  
                }  
            }  
        }
		return listContent;
    }  
    
    /**
     * 读取指定行数据 ，注意：0为开始行
     * @param filePath
     * @param lineNumber
     * @return
     */
    public static String readLineContent(String filePath,int lineNumber){
        BufferedReader reader = null;
        String lineContent="";
    	try {
    		reader = new BufferedReader(new FileReader(filePath));
	        int line=0;
	        while(line<=lineNumber){
	        	lineContent=reader.readLine();
	        	line++;
	        }
	        reader.close();
		} catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            if (reader != null) {  
                try {  
                    reader.close();  
                } catch (IOException e1) {  
                }  
            }  
        }  
        return lineContent;
    }
    
    /**
     * 读取从beginLine到endLine数据（包含beginLine和endLine），注意：0为开始行
     * @param filePath
     * @param beginLineNumber 开始行
     * @param endLineNumber 结束行
     * @return
     */
    public static List<String> readLinesContent(String filePath,int beginLineNumber,int endLineNumber){
    	List<String> listContent = new ArrayList<>();
    	try{
           int count = 0;
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
           String content = reader.readLine();
           while(content !=null){
        	   if(count >= beginLineNumber && count <=endLineNumber){
        		   listContent.add(content);
        	   }	
        	   content = reader.readLine();
        	   count++;   
           }
    	} catch(Exception e){
    	}
		return listContent;
    }
    
    
    /**
     * 读取若干文件中所有数据
     * @param listFilePath
     * @return
     */
    public static List<String> readFileContent_list(List<String> listFilePath) {  
    	List<String> listContent = new ArrayList<>();
    	for(String filePath : listFilePath){
    		 File file = new File(filePath);
    		 BufferedReader reader = null;
	        try {  
	            reader = new BufferedReader(new FileReader(file));  
	            String tempString = null;  
				int line = 1;  
	            // 一次读入一行，直到读入null为文件结束 
	            while ((tempString = reader.readLine()) != null) {
	            	listContent.add(tempString);
	                line++;
	            }  
	            reader.close();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        } finally {  
	            if (reader != null) {  
	                try {  
	                    reader.close();  
	                } catch (IOException e1) {  
	                }  
	            }  
	        }
    	}
		return listContent;
    }
    
    /**
     * 文件数据写入（如果文件夹和文件不存在，则先创建，再写入）
     * @param filePath
     * @param content
     */
    public static String fileLinesWrite(String filePath,String content){
    	String filedo = "write";
    	FileWriter fw = null;
    	try {
	    	File file=new File(filePath);
	    	//如果文件夹不存在，则创建文件夹
	    	if (!file.getParentFile().exists()){
				file.getParentFile().mkdirs();
			}
	    	if(!file.exists()){//如果文件不存在，则创建文件,写入第一行内容
	    		file.createNewFile();
	    		fw = new FileWriter(file);
	    		filedo = "create";
	    	}else{//如果文件存在，则追加内容；
	    		fw = new FileWriter(file, true);
	    	}
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    		PrintWriter pw = new PrintWriter(fw);
    		pw.println(content);
    		pw.flush();
    	try {
	    	fw.flush();
	    	pw.close();
	    	fw.close();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
		return filedo;
    }
    
    /**
     * 写文件
     * @param ins
     * @param out
     */
    public static void writeIntoOut(InputStream ins, OutputStream out) {
		byte[] bb = new byte[10 * 1024];
		try {
			int cnt = ins.read(bb);
			while (cnt > 0) {
				out.write(bb, 0, cnt);
				cnt = ins.read(bb);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.flush();
				ins.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
    
    /**
     * 判断list中元素是否完全相同（完全相同返回true,否则返回false）
     * @param list
     * @return
     */
    private static boolean hasSame(List<? extends Object> list){  
        if(null == list)  
        	return false;  
        return 1 == new HashSet<Object>(list).size();  
    } 
    
    /**
     * 判断list中是否有重复元素（无重复返回true,否则返回false）
     * @param list
     * @return
     */
    private static boolean hasSame2(List<? extends Object> list){  
        if(null == list)  
            return false;  
        return list.size() == new HashSet<Object>(list).size();  
    }  
    
    /**
     * 增加/减少天数
     * @param date
     * @param num
     * @return
     */
    public static Date DateAddOrSub(Date date, int num) {  
        Calendar startDT = Calendar.getInstance();  
        startDT.setTime(date);  
        startDT.add(Calendar.DAY_OF_MONTH, num);  
        return startDT.getTime();  
    }
    
    public static boolean Move(String srcFile, String destPath){
       // File (or directory) to be moved
       File file = new File(srcFile);
      
       // Destination directory
       File dir = new File(destPath);
       if(!dir.exists()){
    	   dir.mkdirs();
       }
       // Move file to new directory
       boolean success = file.renameTo(new File(dir, file.getName()));
      
       return success;
   }
    
    
    public static void main(String[] args) {
		String convertMD5 = CoderUtil.convertMD5("");
		System.out.println(convertMD5);
		
	}
}
