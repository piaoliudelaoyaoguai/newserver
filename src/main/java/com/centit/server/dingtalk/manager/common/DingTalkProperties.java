package com.centit.server.dingtalk.manager.common;

import com.centit.server.dingtalk.manager.uitls.PropertyUtil;

/**
 * 
 * @Version : 1.0
 * @Author : li_hao
 * @Description : 配置参数类
 * @Date : 2017年11月8日 上午10:14:21
 */
public class DingTalkProperties {
	
	/** 通讯录回调URL */
	public static String call_back_url = PropertyUtil.readProperty("dingtalk.properties", "call_back_url");

	/** 钉钉能力开关:on/off */
	public static String dingtalk_switch = PropertyUtil.readProperty("dingtalk.properties", "dingtalk_switch");
	/** 通讯录能力源：dingtalk/dtdream */
	public static String address_source = PropertyUtil.readProperty("dingtalk.properties", "address_source");
	/** 初始化同步通讯录开关:on/off */
	public static String address_sync_switch = PropertyUtil.readProperty("dingtalk.properties", "address_sync_switch");
	/** 注册回调：注册开关:on/off */
	public static String register_call_back_url_switch = PropertyUtil.readProperty("dingtalk.properties", "register_call_back_url_switch");
	/** 定时执行 查询注册更新回调（强制防顶）开关：on/off */
	public static String callBackUpdatePowerSwitch = PropertyUtil.readProperty("dingtalk.properties", "callBackUpdatePowerSwitch");
	/** 开发接入部署模式(incorp:企业内开发；isv:应用服务商) */
	public static String developtype = PropertyUtil.readProperty("dingtalk.properties", "developtype");
	/** M2能力开关：on/off */
	public static String m2_switch = PropertyUtil.readProperty("dingtalk.properties", "m2_switch");

	public static String timeCell = PropertyUtil.readProperty("dingtalk.properties", "timeCell");
	public static String M2NodeAddUsertimeCell = PropertyUtil.readProperty("dingtalk.properties", "M2NodeAddUsertimeCell");

	public static String upload_filedir = PropertyUtil.readProperty("dingtalk.properties", "upload_filedir");
	public static String file_prefix = PropertyUtil.readProperty("dingtalk.properties", "file_prefix");
	public static String publish_url = PropertyUtil.readProperty("dingtalk.properties", "publish_url");
	public static String application_setup_url = PropertyUtil.readProperty("dingtalk.properties", "application_setup_url");


	public static String plist_template_url = PropertyUtil.readProperty("dingtalk.properties", "plist_template_url");
	public static String android_publish_url = PropertyUtil.readProperty("dingtalk.properties", "android_publish_url");
	public static String bundle_identifier = PropertyUtil.readProperty("dingtalk.properties", "bundle_identifier");
	

	//推送通知信息
	public static String mobile_url = PropertyUtil.readProperty("dingtalk.properties", "mobile_url");
	public static String pc_url = PropertyUtil.readProperty("dingtalk.properties", "pc_url");
	public static String news_headcolor = PropertyUtil.readProperty("dingtalk.properties", "news_headcolor");
	public static String meeting_exam = PropertyUtil.readProperty("dingtalk.properties", "meeting_exam");
	public static String news_audit_text = PropertyUtil.readProperty("dingtalk.properties", "news_audit_text");
	public static String news_update_text = PropertyUtil.readProperty("dingtalk.properties", "news_update_text");
	public static String news_creater_text = PropertyUtil.readProperty("dingtalk.properties", "news_creater_text");
	public static String news_joinner_text = PropertyUtil.readProperty("dingtalk.properties", "news_joinner_text");
	public static String news_joinnerdel_text = PropertyUtil.readProperty("dingtalk.properties", "news_joinnerdel_text");
	public static String news_dept_text = PropertyUtil.readProperty("dingtalk.properties", "news_dept_text");
	public static String news_deptdel_text = PropertyUtil.readProperty("dingtalk.properties", "news_deptdel_text");
	public static String news_ready_text = PropertyUtil.readProperty("dingtalk.properties", "news_ready_text");
	public static String news_usercancel_text = PropertyUtil.readProperty("dingtalk.properties", "news_usercancel_text");
	public static String news_setdeptuser_text = PropertyUtil.readProperty("dingtalk.properties", "news_setdeptuser_text");
	public static String news_deldeptuser_text = PropertyUtil.readProperty("dingtalk.properties", "news_deldeptuser_text");
	public static String news_deldeptuser_once_text = PropertyUtil.readProperty("dingtalk.properties", "news_deldeptuser_once_text");
	public static String news_setdeptuser_once_text = PropertyUtil.readProperty("dingtalk.properties", "news_setdeptuser_once_text");
	public static String news_updatedeptuser_text = PropertyUtil.readProperty("dingtalk.properties", "news_updatedeptuser_text");
	public static String news_groupdeptcancel_text = PropertyUtil.readProperty("dingtalk.properties", "news_groupdeptcancel_text");
	public static String news_meetingcancel_text = PropertyUtil.readProperty("dingtalk.properties", "news_meetingcancel_text");
	public static String news_deptleader_text1 = PropertyUtil.readProperty("dingtalk.properties", "news_deptleader_text1");
	public static String news_deptleader_text2 = PropertyUtil.readProperty("dingtalk.properties", "news_deptleader_text2");
	public static String news_deptresp_text = PropertyUtil.readProperty("dingtalk.properties", "news_deptresp_text");	
	
	
	
	
}
