package com.centit.server.dingtalk.manager.uitls;

import com.centit.server.dingtalk.manager.dao.AgentDao;
import com.centit.server.dingtalk.manager.dao.CorpInfoDao;
import com.centit.server.dingtalk.manager.dao.DeptSyncDao;
import com.centit.server.dingtalk.manager.dao.DingTalkSuiteDao;
import com.centit.server.dingtalk.manager.dao.ServCorpInfoDao;
import com.centit.server.dingtalk.manager.dao.UserDeptSyncDao;
import com.centit.server.dingtalk.manager.dao.UserSyncDao;
import com.centit.server.webmgr.device.dao.DeviceDao;

/**
 * 
 * @Version : 1.0
 * @Author : li_hao
 * @Description : 通用装载类
 * @Date : 2017年11月14日 下午4:33:20
 */
public class CommonInit {
	
	public static String agentid = "";  //微应用id(incorp:企业内开发时需要)
	
	public static ServCorpInfoDao servCorpInfoDao ;
	public static CorpInfoDao corpInfoDao ;
	public static AgentDao agentDao ;
	public static DingTalkSuiteDao dingTalkSuiteDao ;
	public static DeviceDao deviceDao;
	public static UserSyncDao userSyncDao;
	public static DeptSyncDao deptSyncDao;
	public static UserDeptSyncDao userDeptSyncDao;
	
//	/** 钉钉通讯录同步 */
//	public static AddressListSync addressListSync;
	
}
