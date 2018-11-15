package com.centit.server.dingtalk.manager.controller;/**
 * Created by li_hao on 2017/6/10.
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.centit.server.dingtalk.demo.OApiException;
import com.centit.server.dingtalk.demo.auth.AuthHelper;
import com.centit.server.dingtalk.manager.common.DingTalkProperties;
import com.centit.server.dingtalk.manager.dao.DeptSyncDao;
import com.centit.server.dingtalk.manager.po.CorpInfo;
import com.centit.server.dingtalk.manager.po.DeptSync;
import com.centit.server.dingtalk.manager.po.ServCorpInfo;
import com.centit.server.dingtalk.manager.service.CorpInfoManager;
import com.centit.server.dingtalk.manager.service.DingTalkSuiteManager;
import com.centit.server.dingtalk.manager.service.ServCorpInfoManager;
import com.centit.server.dingtalk.manager.uitls.CommonInit;
import com.centit.server.dingtalk.manager.uitls.CommonUtil;
import com.centit.server.dingtalk.manager.uitls.DingTalkUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;

/**
 * @version : 1.0
 * @Author : li_hao
 * @Description : dingtalkCommom
 * @Date : 2017-06-10 10:44
 **/
@SuppressWarnings("unused")
@Controller
@RequestMapping("/dingtalkCommom")
public class DingtalkCommonController {
//	private static final Log log = LogFactory.getLog(DingtalkCommonController.class);
	private static Logger logger = Logger.getLogger(DingtalkCommonController.class); 
	
	@Resource
	private CorpInfoManager corpInfoManager;
	@Resource
	private ServCorpInfoManager servCorpInfoManager;
	@Resource
	private DingTalkSuiteManager dingTalkSuiteManager;
	@Resource
	private DeptSyncDao deptSyncDao;
	
 	@RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject queryList(HttpServletRequest request, HttpServletResponse response) {
    	System.out.println("111111111111111");
    	JSONObject jsonObject = new JSONObject();
    	jsonObject.put("1", "1111");
    	jsonObject.put("2", "2222");
    	jsonObject.put("3", "3333");
    	return jsonObject;
	}
	
	

	/**
	 * 前端初始页面校验
	 * @param request
	 * @param response
	 */
    @RequestMapping(value = "/feCheck", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject queryList(HttpServletRequest request, HttpServletResponse response,@RequestBody JSONObject reqJson) {
    	String developtype = DingTalkProperties.developtype;
    	String signedUrl = reqJson.getString("url");
     	HashMap<String,String> mapUrl = getParameter(signedUrl);
    	
    	long timeStamp = System.currentTimeMillis()/1000;
     	String nonceStr = "centit";
     	String corpid = "";
     	String ticket = "";
     	String agentid = "";
     	if("incorp".equals(developtype)){
     		agentid = mapUrl.get("agentid");
     		ServCorpInfo servCorpInfo = new ServCorpInfo();
     		servCorpInfo = servCorpInfoManager.queryOne(servCorpInfo);
     		corpid = servCorpInfo.getCorpid();
     		ticket = servCorpInfo.getJsapi_ticket();
     	}else if("isv".equals(developtype)){
     		corpid = mapUrl.get("corpid");
     		agentid = mapUrl.get("agentid");
     		CorpInfo corpInfo = new CorpInfo();
        	corpInfo.setCorpid(corpid);
        	corpInfo = corpInfoManager.queryOne(corpInfo);
        	ticket = corpInfo.getJsapi_ticket();
     	}
     	
    	String signature = null;
    	try {
			signature = AuthHelper.sign(ticket, nonceStr, timeStamp, signedUrl);
		} catch (OApiException e) {
			e.printStackTrace();
		}
    	HashMap<String, Object> map = new HashMap<>();
    	map.put("jsticket", ticket);
    	map.put("signature", signature);
    	map.put("nonceStr", nonceStr);
    	map.put("timeStamp", timeStamp);
    	map.put("corpId", corpid);
    	map.put("agentid", agentid);
    	
    	return JSONObject.parseObject(JSON.toJSONString(map));
    }
    
    public static HashMap<String, String> getParameter(String url) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			final String charset = "utf-8";
			url = URLDecoder.decode(url, charset);
			if (url.indexOf('?') != -1) {
				final String contents = url.substring(url.indexOf('?') + 1);
				String[] keyValues = contents.split("&");
				for (int i = 0; i < keyValues.length; i++) {
					String key = keyValues[i].substring(0,
							keyValues[i].indexOf("="));
					String value = keyValues[i].substring(keyValues[i]
							.indexOf("=") + 1);
//					if (key.equals(parameter)) {
//						if (value == null || "".equals(value.trim())) {
//							return defaultValue;
//						}
//						return value;
//					}
					map.put(key, value);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
    
    /**
	 * 获取企业下的自定义空间
	 * @param request
	 * @param response
	 */
    @RequestMapping(value = "/getCustomSpace", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getCustomSpace(HttpServletRequest request, HttpServletResponse response,@RequestBody JSONObject reqJson) {
    	String agentid = reqJson.getString("agentid");
    	String corpid = reqJson.getString("corpid");
    	
    	System.out.println("agentid:"+agentid+"---------corpid:"+corpid);
    	String corp_accesstoken = "";
    	String developtype = DingTalkProperties.developtype;
    	boolean isIsv = false;
    	if("incorp".equals(developtype)){
    		ServCorpInfo servCorpInfo = new ServCorpInfo();
     		servCorpInfo = servCorpInfoManager.queryOne(servCorpInfo);
     		corp_accesstoken = servCorpInfo.getAccesstoken();
     	}else if("isv".equals(developtype)){
     		CorpInfo corpInfo = new CorpInfo();
        	corpInfo.setCorpid(corpid);
        	corpInfo = corpInfoManager.queryOne(corpInfo);
        	corp_accesstoken = corpInfo.getAccess_token();
        	isIsv = true;
     	}
    	
        JSONObject jsonObject = DingTalkUtil.getCustomSpace(isIsv, corp_accesstoken, agentid);
		return jsonObject;
    }
    
    /**
     * 授权用户访问企业下的自定义空间
     * @param request
     * @param response
     */
    @RequestMapping(value = "/grantCustomSpace", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject grantCustomSpace(HttpServletRequest request, HttpServletResponse response,@RequestBody JSONObject reqJson) {
    	String agentid = reqJson.getString("agentid");
    	String corpid = reqJson.getString("corpid");
    	String type = reqJson.getString("type");
    	String userid = reqJson.getString("userid");
    	String fileids = reqJson.getString("fileids");

    	String developtype = DingTalkProperties.developtype;
    	String corp_accesstoken = "";
    	boolean isIsv = false;
    	if("incorp".equals(developtype)){
    		ServCorpInfo servCorpInfo = new ServCorpInfo();
     		servCorpInfo = servCorpInfoManager.queryOne(servCorpInfo);
     		corp_accesstoken = servCorpInfo.getAccesstoken();
     	}else if("isv".equals(developtype)){
     		CorpInfo corpInfo = new CorpInfo();
        	corpInfo.setCorpid(corpid);
        	corpInfo = corpInfoManager.queryOne(corpInfo);
        	corp_accesstoken = corpInfo.getAccess_token();
        	isIsv = true;
     	}
    	
    	JSONObject jsonObject = DingTalkUtil.grantCustomSpace(isIsv, corp_accesstoken, agentid, type, userid, null, fileids);
		return jsonObject;
    }
    
    /**
     * ISV获取授权企业授权的部门列表 
     * @param request
     * @param response
     */
    @RequestMapping(value = "/getDeptInfoList", method = RequestMethod.POST) 
    @ResponseBody
    public JSONObject getDeptInfoList(HttpServletRequest request, HttpServletResponse response,@RequestBody JSONObject reqJson) {
    	JSONObject rsp_json = new JSONObject();
		String retCode = "0";
		String retMsg = "";
		JSONObject jsonObject = new JSONObject();
		try {	
			String corpid = reqJson.getString("corpid");

			String developtype = DingTalkProperties.developtype;
	    	String corp_accesstoken = "";
	    	if("incorp".equals(developtype)){
	    		ServCorpInfo servCorpInfo = new ServCorpInfo();
	     		servCorpInfo = servCorpInfoManager.queryOne(servCorpInfo);
	     		corp_accesstoken = servCorpInfo.getAccesstoken();
	     	}else if("isv".equals(developtype)){
	     		CorpInfo corpInfo = new CorpInfo();
	        	corpInfo.setCorpid(corpid);
	        	corpInfo = corpInfoManager.queryOne(corpInfo);
	        	corp_accesstoken = corpInfo.getAccess_token();
	     	}
	    	jsonObject = DingTalkUtil.getCorpDepartmentList(corp_accesstoken);
			
			retMsg = "操作成功！";
		} catch (Exception e) {
			e.printStackTrace();
			retCode = "1";
			retMsg = "操作失败！";
		}
		rsp_json.put("retCode", retCode);
		rsp_json.put("retMsg", retMsg);
		rsp_json.put("bizData", jsonObject);
		
		return rsp_json;
    }

    /**
     * 获取下级部门列表
     * @param request
     * @param response
     */
    @RequestMapping(value = "/getChildDeptList", method = RequestMethod.POST) 
    @ResponseBody
    public JSONObject getChildDeptList(HttpServletRequest request, HttpServletResponse response,@RequestBody JSONObject reqJson) {
    	JSONObject rsp_json = new JSONObject();
    	String retCode = "1";
    	String retMsg = "操作失败";
    	JSONObject jsonObject = new JSONObject();
    	try {	
    		String corpid = reqJson.getString("corpid");
    		if(StringUtils.isNotBlank(corpid)){
    			JSONArray jsonArray = new JSONArray();
    			
    			String parentid = reqJson.getString("parentid");  //父级部门id
    			if(StringUtils.isBlank(parentid)){  //第一次查询顶层
    				String accessToken = CommonUtil.getAccessToken(corpid);
    	    		
    	    		JSONArray jsonArrayAuthScopes = new JSONArray();
    	    		JSONObject jsonAuthScopes = DingTalkUtil.getAuthScopes(accessToken); //获取通讯录权限
    	    		if(jsonAuthScopes != null){
    	    			jsonArrayAuthScopes = jsonAuthScopes.getJSONObject("auth_org_scopes").getJSONArray("authed_dept"); //授权部门id列表
    	    		}
    	    		if(jsonArrayAuthScopes.size()==1){ //授权类型为全部，或者为某一个部门（corpsecret勾选某一级部门）
    					String deptid = jsonArrayAuthScopes.get(0).toString();
    					JSONObject departmentDetail = DingTalkUtil.getDepartmentDetail(accessToken, deptid); //获取所勾选的部门（虚拟顶级部门）详情
    					JSONObject deptJson = new JSONObject();
    					deptJson.put("deptid", departmentDetail.getString("id"));
    					deptJson.put("deptname", departmentDetail.getString("name"));
    					deptJson.put("parentid", departmentDetail.getString("parentid"));
    					jsonArray.add(deptJson);
    	    		}
    			}else{  //查询子部门
    				DeptSync deptSync = new DeptSync();
    				deptSync.setCorpid(corpid);
    				deptSync.setParentid(parentid);
    				List<DeptSync> childList = deptSyncDao.queryChildList(deptSync);
    				jsonArray = JSONArray.parseArray(JSON.toJSONString(childList));
    			}
    			jsonObject.put("deptinfolist", jsonArray);
    		}
    		retCode = "0";
    		retMsg = "操作成功！";
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	rsp_json.put("retCode", retCode);
    	rsp_json.put("retMsg", retMsg);
    	rsp_json.put("bizData", jsonObject);
    	
		return rsp_json;
    }
    
    /**
     * 获取部门成员（详情）列表
     * @param request
     * @param response
     */
    @RequestMapping(value = "/getDeptDetailUsers", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getDeptDetailUsers(HttpServletRequest request, HttpServletResponse response,@RequestBody JSONObject reqJson) {
    	String corpid = reqJson.getString("corpid");
    	String deptid = reqJson.getString("deptid");
    	
    	String developtype = DingTalkProperties.developtype;
    	String corp_accesstoken = "";
    	if("incorp".equals(developtype)){
    		ServCorpInfo servCorpInfo = new ServCorpInfo();
     		servCorpInfo = servCorpInfoManager.queryOne(servCorpInfo);
     		corp_accesstoken = servCorpInfo.getAccesstoken();
     	}else if("isv".equals(developtype)){
     		CorpInfo corpInfo = new CorpInfo();
        	corpInfo.setCorpid(corpid);
        	corpInfo = corpInfoManager.queryOne(corpInfo);
        	corp_accesstoken = corpInfo.getAccess_token();
     	}
    	JSONObject jsonObject = DingTalkUtil.getDepartmentDetailUsers(corp_accesstoken, deptid);
		return jsonObject;
    }
    
    /**
     * 获取成员详情
     * @param request
     * @param response
     */
    @RequestMapping(value = "/getUserDetail", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getUserDetail(HttpServletRequest request, HttpServletResponse response,@RequestBody JSONObject reqJson) {
    	String corpid = reqJson.getString("corpid");
    	String userid = reqJson.getString("userid");
    	
    	String developtype = DingTalkProperties.developtype;
    	String corp_accesstoken = "";
    	if("incorp".equals(developtype)){
    		ServCorpInfo servCorpInfo = new ServCorpInfo();
     		servCorpInfo = servCorpInfoManager.queryOne(servCorpInfo);
     		corp_accesstoken = servCorpInfo.getAccesstoken();
     	}else if("isv".equals(developtype)){
     		CorpInfo corpInfo = new CorpInfo();
        	corpInfo.setCorpid(corpid);
        	corpInfo = corpInfoManager.queryOne(corpInfo);
        	corp_accesstoken = corpInfo.getAccess_token();
     	}
    	JSONObject jsonObject = DingTalkUtil.getUserDetail(corp_accesstoken, userid);
    	JSONArray deptIds = jsonObject.getJSONArray("department");
    	JSONArray deptNames = new JSONArray();
    	if(deptIds.size()>0 && deptIds!=null){
    		for(int i=0;i<deptIds.size();i++){
    			String deptId = deptIds.getString(i);
    			JSONObject departmentDetail = DingTalkUtil.getDepartmentDetail(corp_accesstoken, deptId);  //获取部门名称
    			String deptName = departmentDetail.getString("name");
    			deptNames.add(deptName);
    		}
    	}
    	jsonObject.put("departmentname", deptNames);
		return jsonObject;
    }
    
    
    
    
}
