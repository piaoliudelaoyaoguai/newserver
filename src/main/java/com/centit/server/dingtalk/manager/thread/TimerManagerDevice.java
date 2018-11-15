package com.centit.server.dingtalk.manager.thread;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServlet;

import com.centit.server.dingtalk.manager.common.DingTalkProperties;
import com.centit.server.dingtalk.manager.po.CorpInfo;
import com.centit.server.dingtalk.manager.po.ServCorpInfo;
import com.centit.server.dingtalk.manager.uitls.CommonInit;
import com.centit.server.webmgr.device.util.DeviceSync;

/**
 * <p>定时获取企业最新设备列表<p>
 * @version 1.0
 * @author li_hao
 * @date 2018年3月27日
 */
public class TimerManagerDevice extends HttpServlet {
	
	private static final long serialVersionUID = 8998366927705391799L;

	public TimerManagerDevice() {
    	Runnable runnable = new Runnable() {
            public synchronized void run() {
            	
            	//---------------------------------定时获取企业最新设备列表--------------------------------
            	try {
            		String corp_accesstoken = "";
    		    	String developtype = DingTalkProperties.developtype;
    		    	
    		    	if("incorp".equals(developtype)){
    		    		ServCorpInfo servCorpInfo = new ServCorpInfo();
    		     		servCorpInfo = CommonInit.servCorpInfoDao.queryOne(servCorpInfo);
    		     		corp_accesstoken = servCorpInfo.getAccesstoken();
    		     		
    		     		new DeviceSync().deviceSync(servCorpInfo.getCorpid(), corp_accesstoken);  //钉钉智能设备同步
    		     	}else if("isv".equals(developtype)){
    		     		CorpInfo corp = new CorpInfo();
                		List<CorpInfo> listcorpInfo = CommonInit.corpInfoDao.queryList(corp);
                		for(CorpInfo corpInfo : listcorpInfo){
                			new DeviceSync().deviceSync(corpInfo.getCorpid(), corpInfo.getAccess_token());  //钉钉智能设备同步
                		}
    		     	}
				} catch (Exception e) {
					e.printStackTrace();
				}
            }
        };
 
        ScheduledExecutorService service = Executors .newSingleThreadScheduledExecutor();  
        // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间 (定时执行：6500秒)
        service.scheduleAtFixedRate(runnable, 0, 6500, TimeUnit.SECONDS);
    }
	
	
}
