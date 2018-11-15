package com.centit.server.dingtalk.manager.thread;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServlet;

import com.centit.server.dingtalk.manager.common.DingTalkProperties;
import com.centit.server.dingtalk.manager.po.CorpInfo;
import com.centit.server.dingtalk.manager.po.DingTalkSuite;
import com.centit.server.dingtalk.manager.po.ServCorpInfo;
import com.centit.server.dingtalk.manager.uitls.CommonInit;
import com.centit.server.dingtalk.manager.uitls.RegisterUtil;

/**
 * <p>定时执行 查询注册更新回调 的线程<p>
 * @version 1.0
 * @author li_hao
 * @date 2018年7月20日
 */
public class TimerManagerRegisterUrl extends HttpServlet {
	
	private static final long serialVersionUID = 3110743178752250105L;

	public TimerManagerRegisterUrl() {  
    	Runnable runnable = new Runnable() {  
            public synchronized void run() { 
            	try {
            		if("incorp".equals(DingTalkProperties.developtype)){  //企业内开发
            			ServCorpInfo servCorpInfo = CommonInit.servCorpInfoDao.queryOne(null);
            			String accessToken = servCorpInfo.getAccesstoken();
            			
            			if("on".equals(DingTalkProperties.register_call_back_url_switch)){
            				RegisterUtil.registerCallBack(accessToken,null);  //初始化注册回调事件
            			}
            			
            		}else if("isv".equals(DingTalkProperties.developtype)){  //应用服务商开发
            			List<DingTalkSuite> listSuite = CommonInit.dingTalkSuiteDao.queryList(null);
                		for(DingTalkSuite suite : listSuite){
                			String suiteid = suite.getSuiteid();  //套件id
                        	
                			CorpInfo corp = new CorpInfo();
                			corp.setSuiteid(suiteid);
                    		List<CorpInfo> listcorpInfo = CommonInit.corpInfoDao.queryList(corp);
                    		if(listcorpInfo.size()>0 && listcorpInfo!=null){
                    			for(CorpInfo corpInfo : listcorpInfo){
                        			String accessToken = corpInfo.getAccess_token();
                        			
                        			if("on".equals(DingTalkProperties.register_call_back_url_switch)){
                        				RegisterUtil.registerCallBack(accessToken,null);  //初始化注册回调事件
                        			}
                        		}
                    		}
                		}
            		}
				} catch (Exception e) {
					e.printStackTrace();
				}
            }  
        };
 
        ScheduledExecutorService service = Executors .newSingleThreadScheduledExecutor();  
        // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间 (定时执行：60秒)
        service.scheduleAtFixedRate(runnable, 0, 60, TimeUnit.SECONDS);
        
    }  
    
	
	
}
