package com.centit.server.dingtalk.manager.thread;
 
import java.util.List;
import com.alibaba.fastjson.JSONObject;
import com.centit.server.dingtalk.manager.common.DingTalkProperties;
import com.centit.server.dingtalk.manager.po.AccessToken;
import com.centit.server.dingtalk.manager.po.CorpInfo;
import com.centit.server.dingtalk.manager.po.DingTalkSuite;
import com.centit.server.dingtalk.manager.po.ServCorpInfo;
import com.centit.server.dingtalk.manager.uitls.CommonInit;
import com.centit.server.dingtalk.manager.uitls.DingTalkUtil;

    
/**
 * <p>定时获取钉钉access_token的线程 <p>
 * <p>定时获取套件的suite_access_token、授权企业的access_token和jsapi_ticket <p>
 * @version 1.0
 * @author li_hao
 * @date 2017年6月13日
 */
public class TokenThread implements Runnable {   
	public static AccessToken accessToken = null;
 	
    public void run() {
        while (true) {    
            try {    
            	boolean flag = false;  //获取标志：若获取有异常或失败，则60秒后再次获取；获取成功，则6500秒后再次获取
            	
            	if("incorp".equals(DingTalkProperties.developtype)){  //企业内开发
                	//---------------------------------定时获取服务商企业的access_token--------------------------------
                	try {
                		ServCorpInfo servCorpInfo = new ServCorpInfo();
                		servCorpInfo = CommonInit.servCorpInfoDao.queryOne(servCorpInfo);
                		System.out.println("accesstoken_old:"+servCorpInfo.getAccesstoken());
                		accessToken = DingTalkUtil.getAccessToken(servCorpInfo.getCorpid(),servCorpInfo.getCorpsecret());
                		String servAccessToken = accessToken.getToken();
                		servCorpInfo.setAccesstoken(servAccessToken);
                		
                		JSONObject jsapiTicketJson = DingTalkUtil.getJsapiTicket(servAccessToken);
                		String jsapi_ticket = jsapiTicketJson.getString("ticket");
                		servCorpInfo.setJsapi_ticket(jsapi_ticket);
                		CommonInit.servCorpInfoDao.updateServCorpInfo(servCorpInfo);
                		System.out.println("开发类型：" + DingTalkProperties.developtype + "---accesstoken_new:"+servAccessToken + "---jsapi_ticket:" + jsapi_ticket);
                		
                		if("".equals(servAccessToken) || servAccessToken==null){
            				flag = true;
            			}
    				} catch (Exception e) {
    					e.printStackTrace();
    					flag = true;
    				}
            	}else if("isv".equals(DingTalkProperties.developtype)){  //应用服务商开发
            		//---------------------------------定时获取授权企业的access_token和jsapi_ticket--------------------------------
                	try {
                		//System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                		//查询套件列表
                		List<DingTalkSuite> listSuite = CommonInit.dingTalkSuiteDao.queryList(null);
                		for(DingTalkSuite suite : listSuite){
                			String suite_access_token = suite.getSuite_access_token();
                			String suiteid = suite.getSuiteid();  //套件id
                        	
                        	//定时重新获取授权企业表(T_DT_CORPINFO)中所有授权企业的access_token和jsapi_ticket
                			CorpInfo corp = new CorpInfo();
                			corp.setSuiteid(suiteid);
                    		List<CorpInfo> listcorpInfo = CommonInit.corpInfoDao.queryList(corp);
                    		if(listcorpInfo.size()>0 && listcorpInfo!=null){
                    			for(CorpInfo corpInfo : listcorpInfo){
                        			JSONObject corpTokenJson = DingTalkUtil.getCorpToken(suite_access_token, corpInfo.getCorpid(), corpInfo.getPermanent_code());
                        			
                        			String corp_accesstoken = corpTokenJson.getString("access_token");  //授权方（企业）access_token,每一个企业都会有一个对应的access_token，访问对应企业的数据都将需要带上这个access_token
                        			JSONObject jsapiTicketJson = DingTalkUtil.getJsapiTicket(corp_accesstoken);  //获取授权企业的jsapi_ticket
                        			String jsapi_ticket = jsapiTicketJson.getString("ticket");
                        			
                        			CorpInfo corp_accesstoken_jsapiticket = new CorpInfo();
                        			corp_accesstoken_jsapiticket.setCorpid(corpInfo.getCorpid());
                        			corp_accesstoken_jsapiticket.setAccess_token(corp_accesstoken);
                        			corp_accesstoken_jsapiticket.setJsapi_ticket(jsapi_ticket);
                        			corp_accesstoken_jsapiticket.setSuiteid(suiteid);
                        			CommonInit.corpInfoDao.updateCorpInfo(corp_accesstoken_jsapiticket);  //定时更新T_DT_CORPINFO表中的access_token和jsapi_ticket
                        			
                        			System.out.println("开发类型：" + DingTalkProperties.developtype + "---corpInfo---:"+corpInfo.getCorpid()+"||||||"+"corp_accesstoken----:"+corp_accesstoken);
                        			if("".equals(corp_accesstoken) || corp_accesstoken==null){
                        				flag = true;
                        			}
                        		}
                    		}
                		}
    				} catch (Exception e) {
    					e.printStackTrace();
    					flag = true;
    				}
            	}
            	if(flag){
            		// 如果access_token为null，60秒后再获取    
                    Thread.sleep(60 * 1000);
            	}else{
            		 // 休眠6500秒    
                    Thread.sleep(6500 * 1000);
            	}
            } catch (InterruptedException e) {    
                try {    
                    Thread.sleep(60 * 1000);    
                } catch (InterruptedException e1) {    
                	 System.out.println("error:" + e1);
                }
                System.out.println("error:" + e);
            }    
        }    
    }    
}    