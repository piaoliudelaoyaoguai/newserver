package com.centit.server.dingtalk.manager.servlet;  

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import com.centit.server.common.utils.SpringContextUtil;
import com.centit.server.dingtalk.manager.common.DingTalkProperties;
import com.centit.server.dingtalk.manager.thread.InitDoThread;
import com.centit.server.dingtalk.manager.thread.TimerManagerDevice;
import com.centit.server.dingtalk.manager.thread.TimerManagerRegisterUrl;
import com.centit.server.dingtalk.manager.thread.TokenThread;
import com.centit.server.dingtalk.manager.uitls.CommonInit;
import com.centit.server.dingtalk.manager.uitls.PropertyUtil;
    
/**
 * <p>初始化装载servlet<p>
 * @version 1.0
 * @author li_hao
 * @date 2017年6月13日
 */
public class InitServlet extends HttpServlet {    

	private static final long serialVersionUID = -3219526358972473612L;

	public void init() throws ServletException {
		//----------------------------------------------钉钉平台能力--------------------------------------------------------------
		new DingTalkProperties();
		if("incorp".equals(DingTalkProperties.developtype)){
        	CommonInit.agentid = PropertyUtil.readProperty("dingtalk.properties", "agentid");  //微应用id(incorp:企业内开发时需要)
        }
		CommonInit.servCorpInfoDao = SpringContextUtil.getBean("servCorpInfoDao");
		CommonInit.dingTalkSuiteDao = SpringContextUtil.getBean("dingTalkSuiteDao");
		CommonInit.corpInfoDao = SpringContextUtil.getBean("corpInfoDao");
		CommonInit.agentDao = SpringContextUtil.getBean("agentDao");
        CommonInit.deviceDao = SpringContextUtil.getBean("deviceDao"); 
        CommonInit.userSyncDao = SpringContextUtil.getBean("userSyncDao"); 
        CommonInit.deptSyncDao = SpringContextUtil.getBean("deptSyncDao"); 
        CommonInit.userDeptSyncDao = SpringContextUtil.getBean("userDeptSyncDao"); 
        
        
        //钉钉能力开关
        if("on".equals(DingTalkProperties.dingtalk_switch)){
        	new Thread(new TokenThread()).start();// 启动定时获取access_token的线程
        	//M2能力开关
        	if("on".equals(DingTalkProperties.m2_switch)){
        		new TimerManagerDevice();
        	}
        }
        
        //通讯录能力源：dingtalk/dtxxxx
        if("dingtalk".equals(DingTalkProperties.address_source)){
        	 new Thread(new InitDoThread()).start();// 启动同步通讯录和注册回调的线程
        	 //定时执行 查询注册更新回调（强制防顶）开关：on/off
             if("on".equals(DingTalkProperties.callBackUpdatePowerSwitch)){
            	 new TimerManagerRegisterUrl(); //定时执行 查询注册更新回调 的线程
             }
        }
        
        
        
        
        
        
        
        
        
        
        
    }    
}    
