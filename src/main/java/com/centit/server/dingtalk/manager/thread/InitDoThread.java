package com.centit.server.dingtalk.manager.thread;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.centit.server.dingtalk.manager.common.DingTalkProperties;
import com.centit.server.dingtalk.manager.po.CorpInfo;
import com.centit.server.dingtalk.manager.po.DingTalkSuite;
import com.centit.server.dingtalk.manager.po.ServCorpInfo;
import com.centit.server.dingtalk.manager.sync.AddressListSync;
import com.centit.server.dingtalk.manager.uitls.CommonInit;
import com.centit.server.dingtalk.manager.uitls.RegisterUtil;

/**
 * <p>同步通讯录和注册回调的线程<p>
 * @version 1.0
 * @author li_hao
 * @date 2018年4月23日
 */
public class InitDoThread implements Runnable {   

	public void run() {
    	try {
    		Thread.sleep(5 * 1000); //等待5秒后执行
    		
    		if("incorp".equals(DingTalkProperties.developtype)){  //企业内开发
    			ServCorpInfo servCorpInfo = CommonInit.servCorpInfoDao.queryOne(null);
    			String corpid = servCorpInfo.getCorpid();
    			String accessToken = servCorpInfo.getAccesstoken();
    			
    			JSONObject jsonObject = new JSONObject();
    			jsonObject.put("CorpId", corpid);
    			
    			if("on".equals(DingTalkProperties.address_sync_switch)){
    				new AddressListSync().addressListSync(jsonObject);  //初始化同步通讯录
    			}
    			
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
                			String corpid = corpInfo.getCorpid();
                			String accessToken = corpInfo.getAccess_token();
                			
                			JSONObject jsonObject = new JSONObject();
                			jsonObject.put("CorpId", corpid);
                			
                			if("on".equals(DingTalkProperties.address_sync_switch)){
                				new AddressListSync().addressListSync(jsonObject);  //初始化同步通讯录
                			}
                			
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
}    