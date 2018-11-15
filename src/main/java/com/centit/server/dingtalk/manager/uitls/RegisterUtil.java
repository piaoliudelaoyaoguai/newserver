package com.centit.server.dingtalk.manager.uitls;

import java.util.ArrayList;
import java.util.List;
import com.alibaba.fastjson.JSONObject;
import com.centit.server.dingtalk.manager.common.DingTalkProperties;
import com.centit.server.dingtalk.manager.po.CallBackRegister;
import com.centit.server.dingtalk.manager.po.DingTalkSuite;
import com.taobao.api.ApiException;

/**
 * <p>注册回调事件、URL<p>
 * @version 1.0
 * @author li_hao
 * @date 2018年3月28日
 */
public class RegisterUtil {
	
	public static String token = "centit";
	public static String aesKey = "96idm7p5qnb6zs3usac0lkfm7qfkv23d40yd0va38s7";

	/**
	 * 通讯录事件、URL注册
	 * @param accessToken
	 * @param suiteid  企业内开发传空或null
	 */
	public static void registerCallBack(String accessToken,String suiteid){
		try {
			JSONObject callBack = DingTalkUtil.getCallBack(accessToken);
			System.out.println("查询注册事件："+callBack);
			String errcode = callBack.getString("errcode");
			
			if("isv".equals(DingTalkProperties.developtype)){  //企业内开发
				DingTalkSuite suite = new DingTalkSuite();
				suite.setSuiteid(suiteid);
				suite = CommonInit.dingTalkSuiteDao.queryOne(suite);
				aesKey = suite.getEncoding_aes_key();
			}
				
			if(!"0".equals(errcode)){
				//若没注册，则注册
				List<String> listStr = new ArrayList<String>();
				listStr.add(CallBackRegister.FACE_RECOGNIZE);  //人脸识别事件
				listStr.add("user_add_org");
				listStr.add("user_modify_org");
				listStr.add("user_leave_org");
				
				listStr.add("org_dept_create");
				listStr.add("org_dept_modify");
				listStr.add("org_dept_remove");
				System.out.println("===================================================================================================================");
				System.out.println(accessToken);
				System.out.println(listStr);
				System.out.println(token);
				System.out.println(aesKey);
				System.out.println(DingTalkProperties.call_back_url);
				System.out.println("===================================================================================================================");
				JSONObject registerCallBack = DingTalkUtil.registerCallBack(accessToken, listStr, token, aesKey, DingTalkProperties.call_back_url);  //注册
				System.out.println("注册事件返回："+registerCallBack);
			}else{
				//若已注册，则判断注册url是否正确
				String url = callBack.getString("url");
				if(!url.equals(DingTalkProperties.call_back_url)){
					List<String> listStr = new ArrayList<String>();
					listStr.add(CallBackRegister.FACE_RECOGNIZE);  //人脸识别事件
					listStr.add("user_add_org");
					listStr.add("user_modify_org");
					listStr.add("user_leave_org");
					
					listStr.add("org_dept_create");
					listStr.add("org_dept_modify");
					listStr.add("org_dept_remove");
					
					JSONObject registerCallBack = DingTalkUtil.updateCallBack(accessToken, listStr, token, aesKey, DingTalkProperties.call_back_url);  //更新注册
					System.out.println("注册事件返回："+registerCallBack);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws ApiException {
		String accessToken = "97bfb16db2413732a2b20d50985a68f6";
		
//		List<String> listStr = new ArrayList<String>();
//		listStr.add(CallBackRegister.FACE_RECOGNIZE);  //人脸识别事件
//		listStr.add("user_add_org");
//		listStr.add("user_modify_org");
//		listStr.add("user_leave_org");
//		
//		listStr.add("org_dept_create");
//		listStr.add("org_dept_modify");
//		listStr.add("org_dept_remove");
//		
//		JSONObject registerCallBack = DingTalkUtil.updateCallBack(accessToken, listStr, token, aesKey, "http://ding.qz.gov.cn/meetingserver/callbackreceive");  //更新注册
//		System.out.println("注册事件返回："+registerCallBack);

		JSONObject callBack = DingTalkUtil.getCallBack(accessToken);
		System.out.println("查询注册事件："+callBack);
	}
	
}
