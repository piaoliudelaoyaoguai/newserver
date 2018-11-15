package com.centit.server.webmgr.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 日期工具类
 * 
 * @version 3.0
 * @author hu_yang
 * @date 2015-11-10
 * 
 */
public class DateUtil {

	public static String formatTimeStr(String createTime) {
		long msgCreateTime = Long.parseLong(createTime) * 1000L;
		return sdf4.format(new Date(msgCreateTime));
	}

	public static String formatJSonTimeStr(Timestamp ts) {
		if (ts == null)
			return null;
		return sdf.format(ts);
	}
	
	public static String formatTimeStampToStr(Timestamp ts) {
		if (ts == null)
			return null;
		return sdf4.format(ts);
	}
	
	public static Date formatTimeStampToDate(Timestamp ts) {
		if (ts == null)
			return null;
		try {
			return sdf4.parse(sdf4.format(ts));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Timestamp formatTime(String createTime) {
		long msgCreateTime = Long.parseLong(createTime) * 1000L;
		return new Timestamp(msgCreateTime);
	}

	/**
	 * 获取今天早上8点
	 * 
	 * @param yyyyMM
	 * @return
	 */
	public static Timestamp getBeginTime(String yyyyMM) {
		try {
			String x = yyyyMM;
			x = yyyyMM + "080000";
			Date d = sdf.parse(x);
			java.util.Calendar cal = Calendar.getInstance();
			cal.setTime(d);
			return new Timestamp(d.getTime());
		} catch (ParseException e) {
			throw new SynchronizedException(String.format("时间参数[%s]不对", yyyyMM));
		}
	}

	/**
	 * 获取今天0点开始
	 * 
	 * @param yyyyMM
	 * @return
	 */
	public static Timestamp getTime(String yyyyMM) {
		try {
			String x = yyyyMM;
			x = yyyyMM + "000000";
			Date d = sdf.parse(x);
			java.util.Calendar cal = Calendar.getInstance();
			cal.setTime(d);
			return new Timestamp(d.getTime());
		} catch (ParseException e) {
			throw new SynchronizedException(String.format("时间参数[%s]不对", yyyyMM));
		}
	}

	/**
	 * 获得指定日期的前一天
	 * 
	 * @param specifiedDay
	 * @return
	 */
	public static String getSpecifiedDayAfter(Date specifiedDay) {
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = specifiedDay;
		} catch (Exception e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - 1);
		String dayBefore = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(c.getTime());
		return dayBefore;
	}

	/**
	 * 获得指定日期 7天后
	 * 
	 * @param specifiedDay
	 * @return
	 */
	public static String getSpecifiedfor7Days(Date specifiedDay) {
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = specifiedDay;
		} catch (Exception e) {
			e.printStackTrace();
		}
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + 7);
		String dayBefore = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(c.getTime());
		return dayBefore;
	}

	public static Timestamp parseTime(String publishDate) {
		try {
			Date d = sdf5.parse(publishDate);
			return new Timestamp(d.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
			throw new SynchronizedException(e.getMessage());
		}

	}

	public static Timestamp parseTimeForXinhua(String publishDate) {
		try {
			Date d = sdf11.parse(publishDate);
			return new Timestamp(d.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
			throw new SynchronizedException(e.getMessage());
		}

	}

	public static Timestamp parseYYYYMMDDTime(String publishDate) {
		try {
			Date d = sdf2.parse(publishDate);
			return new Timestamp(d.getTime());
		} catch (ParseException e) {
			return null;
		}
	}

	public static Timestamp getEndTime(String yyyyMMdd) {
		try {
			Date d = sdf.parse(yyyyMMdd + "235959");
			return new Timestamp(d.getTime());
		} catch (ParseException e) {
			throw new SynchronizedException("时间参数不对");
		}
	}

	/**
	 * 将String型格式化,比如想要将2011-11-11格式化成20111111,就StringPattern("2011-11-11",
	 * "yyyy-MM-dd","yyyyMMdd"). String日期转换成String日期
	 * 
	 * @param date
	 *            String 想要格式化的日期
	 * @param oldPattern
	 *            String 想要格式化的日期的现有格式
	 * @param newPattern
	 *            String 想要格式化成什么格式
	 * @return
	 */
	public final static String StringPattern(String date, String oldPattern, String newPattern) {
		if (date == null || oldPattern == null || newPattern == null)
			return "";
		SimpleDateFormat sdf1 = new SimpleDateFormat(oldPattern); // 实例化模板对象
		SimpleDateFormat sdf2 = new SimpleDateFormat(newPattern); // 实例化模板对象
		Date d = null;
		try {
			d = sdf1.parse(date); // 将给定的字符串中的日期提取出来
		} catch (Exception e) { // 如果提供的字符串格式有错误，则进行异常处理
			e.printStackTrace(); // 打印异常信息
		}
		return sdf2.format(d);
	}

	public static Timestamp parseChinaTime(String publishDate) {
		try {
			Date d = sdf6.parse(publishDate);
			return new Timestamp(d.getTime());
		} catch (ParseException e) {
			try {
				Date d = sdf7.parse(publishDate);
				return new Timestamp(d.getTime());
			} catch (Exception ee) {
				try {
					Date d = sdf8.parse(publishDate);
					return new Timestamp(d.getTime());
				} catch (Exception eee) {
					try {
						Date d = sdf9.parse(publishDate);
						return new Timestamp(d.getTime());
					} catch (Exception eee1) {
						throw new RuntimeException(eee1.getMessage());
					}
				}
			}
		}
	}

	static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
	static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
	static SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-01");
	static SimpleDateFormat sdf4 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	static SimpleDateFormat sdf5 = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
	static SimpleDateFormat sdf11 = new SimpleDateFormat("EEE,dd-MMM-yyyy HH:mm:ss Z", Locale.ENGLISH);
	static SimpleDateFormat sdf6 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss", Locale.CHINA);
	static SimpleDateFormat sdf7 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm", Locale.CHINA);
	static SimpleDateFormat sdf8 = new SimpleDateFormat("yyyy年MM月dd日HH:mm", Locale.CHINA);
	static SimpleDateFormat sdf9 = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);
	static SimpleDateFormat sdf10 = new SimpleDateFormat("HHmmss");

	public static java.sql.Date getSystemDate() {
		return new java.sql.Date(System.currentTimeMillis());
	}

	public static java.sql.Timestamp getSystemTimestamp() {
		return new java.sql.Timestamp(System.currentTimeMillis());
	}

	public static java.sql.Timestamp getSystemTimestampAfterHours(int minutes) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, minutes);
		return new java.sql.Timestamp(cal.getTimeInMillis());
	}

	public static String getMonthFirstDay() {
		return sdf3.format(getSystemDate());
	}

	public static String getSystemDateStr() {
		return sdf2.format(getSystemDate());
	}

	public static String getServerSystemDate() {
		return sdf.format(getSystemDate());
	}

	public static String getServerSystemDateSim() {
		return sdf1.format(getSystemDate());
	}

	public static String getServerSystemTime() {
		return sdf10.format(getSystemDate());
	}

	public static Timestamp parseYMdHmsTimestamp(String time) {
		try {
			Date d = sdf.parse(time);
			return new Timestamp(d.getTime());
		} catch (ParseException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public static Timestamp parseYMdTimestamp(String time) {
		try {
			Date d = sdf1.parse(time);
			return new Timestamp(d.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
			throw new SynchronizedException(e.getMessage());
		}

	}
	
	public static String getCurrentTime(Date date){
        String str = sdf.format(date);
        return str;
    }

	public static void main(String[] args) {
//		Date date = DateUtil.getSystemTimestamp();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//		String currentTime = sdf.format(date);
//		Timestamp beginTime = getTime(currentTime);
//		System.out.println("beginTime" + beginTime);
//		Timestamp specifiedDayAfter = Timestamp.valueOf(DateUtil.getSpecifiedfor7Days(beginTime));
//		System.out.println("specifiedDayAfter" + specifiedDayAfter);
		
		
		
		JSONObject json = new JSONObject();
		
		JSONArray ja = json.getJSONArray("1");
		
		if(ja!=null){
			System.out.println("11");
		}else {
			System.out.println("22");
		}
		
		

	}
}
