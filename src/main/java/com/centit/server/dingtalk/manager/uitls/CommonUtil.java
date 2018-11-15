package com.centit.server.dingtalk.manager.uitls;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.centit.server.dingtalk.manager.common.DingTalkProperties;
import com.centit.server.dingtalk.manager.po.CorpInfo;
import com.centit.server.dingtalk.manager.po.ServCorpInfo;

/**
 * <p>通用工具类<p>
 * @version 1.0
 * @author li_hao
 * @date 2018年3月22日
 */
public class CommonUtil {


    /**
     * 将时间转换为时间戳
     * @param datetime格式 yyyy-MM-dd HH:mm:ss
     * @return 时间戳
     */
    public static String dateToStamp(String datetime){
        String res = null;
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        Date date = simpleDateFormat.parse(datetime);
			long ts = date.getTime();
	        res = String.valueOf(ts);
		} catch (ParseException e) {
			e.printStackTrace();
		}
        return res;
    }

    /**
     * 将时间戳转换为时间
     * @param timestamp 时间戳
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String stampToDate(String timestamp){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(timestamp);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 获取accessToken，若为incorp则不用传corpid
     * @param corpid
     * @return
     */
    public static String getAccessToken(String corpid){
    	String corpAcesstoken = "";
    	String developtype = DingTalkProperties.developtype;
    	if("incorp".equals(developtype)){
    		ServCorpInfo servCorpInfo = new ServCorpInfo();
     		servCorpInfo = CommonInit.servCorpInfoDao.queryOne(servCorpInfo);
     		corpAcesstoken = servCorpInfo.getAccesstoken();
     	}else if("isv".equals(developtype)){
     		CorpInfo corpInfo = new CorpInfo();
        	corpInfo.setCorpid(corpid);
        	corpInfo = CommonInit.corpInfoDao.queryOne(corpInfo);
        	corpAcesstoken = corpInfo.getAccess_token();
     	}
    	return corpAcesstoken;
    }
    
    
	/**
	 * Date格式的时间增加、减少 N/天/小时/分钟/秒/毫秒
	 * @pattern 传入日期格式的字符串格式，例如：yyyy-MM-dd HH:mm:ss；yyyy-MM-dd HH:mm，yyyy-MM-dd
	 * @param dateStr Date格式的字符串，例如：2018-07-11 23:50:00
	 * @param time  需要增加/减少的时间转换为毫秒，例如：增加30分钟为30*60*1000L；减少30分钟为-30*60*1000L；
	 * @return
	 */
	public static String dateStrAddSub(String pattern, String dateStr, Long time){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = sdf.parse(dateStr, pos);
		
		Date dateRet = new Date(strtodate .getTime() + time);  //增加/减少N毫秒后的时间
		return sdf.format(dateRet);
	}
	
	/**
	 * 对比两个list,返回两个list中：合并后集合、合并去重后的集合、相同的集合、不同的集合、list1中不在list2中的集合、list2不在list2中的集合
	 * @param list1  集合1
	 * @param list2  集合2
	 * @param cmpType 比较类型返回：a：合并后集合；b：合并去重后的集合；c：相同的集合；d：不同的集合；e：list1中不在list2中的集合；f：list2不在list1中的集合；
	 * @return List 返回处理后的集合
	 * 例如：
	 * list1  ：[1, 2, 3, 3, 4, 5, 6]
	 * list2  ：[3, 4, 4, 7, 8]
	 * a：合并后集合，listAll：[1, 2, 3, 3, 4, 5, 6, 3, 4, 4, 7, 8]
	 * b：合并去重后的集合；[1, 2, 3, 4, 5, 6, 7, 8]
	 * c：相同的集合；[3, 4]
	 * d：不同的集合；[1, 2, 5, 6, 7, 8]
	 * e：list1中不在list2中的集合；[1, 2, 5, 6]
	 * f：list2不在list1中的集合；[7, 8]
	 */
	public static List<String> compareList(List<String> list1, List<String> list2, String cmpType){
		List<String> retList = new ArrayList<String>();
		List<String> listAll = new ArrayList<String>();
		
		listAll.addAll(list1);
		listAll.addAll(list2);
		
		if("a".equals(cmpType)){
			//合并后的集合
			retList = listAll;
		}
		if("b".equals(cmpType)){
			//合并去重后的集合
			retList = listAll.stream().distinct().collect(Collectors.toList());
		}
		if("c".equals(cmpType) || "d".equals(cmpType) || "e".equals(cmpType) || "f".equals(cmpType)){
			//相同的集合
			List<String> listSameTemp = new ArrayList<String>();
			list1.stream().forEach(a -> {
				if(list2.contains(a))
					listSameTemp.add(a);
			});
			retList = listSameTemp.stream().distinct().collect(Collectors.toList());
			
			//不同的集合
			if("d".equals(cmpType)){
				List<String> listTemp = new ArrayList<>(listAll);
				listTemp.removeAll(retList);
				retList = listTemp;
			}
			//list1中不在list2中的集合
			if("e".equals(cmpType)){
				List<String> listTemp = new ArrayList<>(list1);
				listTemp.removeAll(retList);
				retList = listTemp;
			}
			//list2中不在list1中的集合
			if("f".equals(cmpType)){
				List<String> listTemp = new ArrayList<>(list2);
				listTemp.removeAll(retList);
				retList = listTemp;
			}
		}
		return retList;
	}
    
	/**
	 * 生成6位随机数
	 */
    public static int randomSexNum(){
    	int num = (int)((Math.random()*9+1)*100000);
		return num;
    }
    
    
    
    public static void main(String[] args) {
		
//    	String dateStrAddSub = dateStrAddSub("yyyy-MM-dd HH:mm", "2018-07-11 23:50", -30*60*1000L);
//    	System.out.println(dateStrAddSub);
    	
	}
    

}
