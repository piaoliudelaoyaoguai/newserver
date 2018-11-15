package com.centit.server.dingtalk.manager.uitls;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.centit.server.dingtalk.demo.OApiException;
import com.centit.server.dingtalk.manager.po.AccessToken;
import com.centit.server.dingtalk.manager.po.NewsCommon;
import com.centit.server.dingtalk.manager.po.OANews;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.CorpDeviceManageGetRequest;
import com.dingtalk.api.request.CorpDeviceManageQuerylistRequest;
import com.dingtalk.api.request.CorpDeviceManageUnbindRequest;
import com.dingtalk.api.request.CorpDeviceNickUpdateRequest;
import com.dingtalk.api.request.CorpMessageCorpconversationAsyncsendRequest;
import com.dingtalk.api.request.CorpSmartdeviceAddfaceRequest;
import com.dingtalk.api.request.CorpSmartdeviceAddfaceRequest.DidoFaceVO;
import com.dingtalk.api.request.CorpSmartdeviceAddrecognizenotifyRequest;
import com.dingtalk.api.request.CorpSmartdeviceAddrecognizenotifyRequest.RecognizeNotifyVO;
import com.dingtalk.api.request.CorpSmartdeviceHasfaceRequest;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.response.CorpDeviceManageGetResponse;
import com.dingtalk.api.response.CorpDeviceManageQuerylistResponse;
import com.dingtalk.api.response.CorpDeviceManageUnbindResponse;
import com.dingtalk.api.response.CorpDeviceNickUpdateResponse;
import com.dingtalk.api.response.CorpMessageCorpconversationAsyncsendResponse;
import com.dingtalk.api.response.CorpSmartdeviceAddfaceResponse;
import com.dingtalk.api.response.CorpSmartdeviceAddrecognizenotifyResponse;
import com.dingtalk.api.response.CorpSmartdeviceHasfaceResponse;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.taobao.api.ApiException;

/**
 * <p>钉钉接口调用方法类<p>
 * @version 1.0
 * @author li_hao
 * @date 2017年6月12日
 */
public class DingTalkUtil {
	private static Logger log = LoggerFactory.getLogger(DingTalkUtil.class);
	
	/**
	 * get请求
	 * @param url为接口地址参数
	 * @return
	 */
	public static JSONObject doGetStr(String url){
		 CloseableHttpClient httpClient = HttpClients.createDefault();
	     HttpGet httpGet = new HttpGet(url);
	     CloseableHttpResponse response = null;
		 JSONObject jsonObject = null;//接收结果
		 try {
			response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();//从消息体里获取结果
			if(entity!=null){
				String result = EntityUtils.toString(entity,"UTF-8");
				jsonObject = JSONObject.parseObject(result);
			}
			EntityUtils.consume(entity);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
	        try {
	        	if(response != null){
	        		response.close();
	        	}
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
		 return jsonObject;
	}
	
	/**
	 * post请求
	 * @param url为接口地址参数
	 * @param outStr
	 * @return
	 */
	public static JSONObject doPostStr(String url,String outStr){
	    CloseableHttpClient httpClient = HttpClients.createDefault();
		//DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		JSONObject jsonObject = null;
		try {
			httpPost.setEntity(new StringEntity(outStr, "UTF-8"));
			HttpResponse response = httpClient.execute(httpPost);
			String result = EntityUtils.toString(response.getEntity(), "UTF-8");
			jsonObject = JSONObject.parseObject(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
	
	/**
	 * 获取AccessToken
	 * @param corpid
	 * @param corpsecret
	 * @return
	 */
	public static AccessToken getAccessToken(String corpid,String corpsecret){
		AccessToken token = new AccessToken();
		String url = CommomUrl.ACCESS_TOKEN_URL.replace("CORPID", corpid).replace("CORPSECRET", corpsecret);//替换原url中的CORPID和CORPSECRET
		//System.out.println(url);
		JSONObject jsonObject = doGetStr(url);//通过get方法获取结果
		if(jsonObject != null){
			try {
				token.setToken(jsonObject.getString("access_token"));//放入access_token
				token.setExpiresIn(7200);//放入有效时间
			} catch (JSONException e) {
				token = null;
				// 获取token失败
				log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInteger("errcode"), jsonObject.getString("errmsg"));
			}
		}
		return token;
	}
	
	
	/**
	 * 获取SsoToken
	 * @param corpid
	 * @param ssosecret
	 * @return
	 */
	public static String getSsoToken(String corpid,String ssosecret){
		String ssoToken = null;
		String url = CommomUrl.SSO_TOKEN_URL.replace("CORPID", corpid).replace("SSOSECRET", ssosecret);//替换原url中的CORPID和SSOSECRET
		JSONObject jsonObject = doGetStr(url);//通过get方法获取结果
		if(jsonObject != null){
			try {
				ssoToken = jsonObject.getString("access_token");//放入access_token
			} catch (JSONException e) {
				// 获取token失败
				log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInteger("errcode"), jsonObject.getString("errmsg"));
			}
		}
		return ssoToken;
	}
	
	/**
	 * 获取通讯录权限
	 * @param accesstoken
	 * @return
	 */
	public static JSONObject getAuthScopes(String accesstoken){
		String url = CommomUrl.AUTH_SCOPES_URL.replace("ACCESS_TOKEN", accesstoken);
		JSONObject jsonObject = doGetStr(url);//通过get方法获取结果
		return jsonObject;
	}
	
	/**
	 * 获取部门列表
	 * @param accesstoken
	 * @param deptid  父部门id(如果不传，默认部门为根部门，根部门ID为1)
	 * @param fetch_child  是否递归部门的全部子部门，ISV微应用固定传递false。
	 * @return
	 */
	public static JSONObject getDepartments(String accesstoken,String parentDeptId,String fetch_child){
		String url = CommomUrl.DEPARTMENTS_URL.replace("ACCESS_TOKEN", accesstoken).replace("PARENTDEPTID", parentDeptId).replace("FETCH_CHILD", fetch_child);
		JSONObject jsonObject = doGetStr(url);//通过get方法获取结果
		return jsonObject;
	}
	
	/**
	 * 获取部门详情
	 * @param accesstoken
	 * @param deptid
	 * @return
	 */
	public static JSONObject getDepartmentDetail(String accesstoken,String deptid){
		String url = CommomUrl.DEPARTMENT_DETAIL_URL.replace("ACCESS_TOKEN", accesstoken).replace("DEPTID", deptid);
		JSONObject jsonObject = doGetStr(url);
		return jsonObject;
	}
	
	
	/**
	 * 获取部门成员列表
	 * @param accesstoken
	 * @param deptid
	 * @return
	 */
	public static JSONObject getDepartmentUsers(String accesstoken,String deptid){
		String url = CommomUrl.DEPARTMENT_USERS_URL.replace("ACCESS_TOKEN", accesstoken).replace("DEPTID", deptid);
		JSONObject jsonObject = doGetStr(url);
		return jsonObject;
	}
	
	/**
	 * 获取部门成员(详情)列表
	 * @param accesstoken
	 * @param deptid
	 * @return
	 */
	public static JSONObject getDepartmentDetailUsers(String accesstoken,String deptid){
		String url = CommomUrl.DEPARTMENT_DETAIL_USERS_URL.replace("ACCESS_TOKEN", accesstoken).replace("DEPTID", deptid);
		JSONObject jsonObject = doGetStr(url);
		return jsonObject;
	}
	
	
	
	/**
	 * 获取管理员列表
	 * @param accesstoken
	 * @return
	 */
	public static JSONObject getAdmins(String accesstoken){
		String url = CommomUrl.GRT_ADMINS_URL.replace("ACCESS_TOKEN", accesstoken);
		JSONObject jsonObject = doGetStr(url);//通过get方法获取结果
		return jsonObject;
	}
	
	/**
	 * 获取成员详情
	 * @param accesstoken
	 * @param userid
	 * @return
	 */
	public static JSONObject getUserDetail(String accesstoken,String userid){
		String url = CommomUrl.USER_DETAIL_URL.replace("ACCESS_TOKEN", accesstoken).replace("USERID", userid);
		JSONObject jsonObject = doGetStr(url);//通过get方法获取结果
		return jsonObject;
	}
	
	/**
	 * 创建部门
	 * @param accessToken
	 * @param deptJson  部门信息
	 * @return
	 */
	public static JSONObject departmentCreate(String accessToken,JSONObject deptJson){
		String url = CommomUrl.DEPARTMENT_CREATE.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = doPostStr(url, deptJson.toString());
		return jsonObject;
	}

	/**
	 * 更新部门
	 * @param accessToken
	 * @param deptJson  部门信息
	 * @return
	 */
	public static JSONObject departmentUpdate(String accessToken,JSONObject deptJson){
		String url = CommomUrl.DEPARTMENT_UPDATE.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = doPostStr(url, deptJson.toString());
		return jsonObject;
	}
	
	/**
	 * 删除部门
	 * @param accesstoken
	 * @param deptid
	 * @return
	 */
	public static JSONObject departmentDelete(String accessToken,String deptid){
		String url = CommomUrl.DEPARTMENT_DELETE.replace("ACCESS_TOKEN", accessToken).replace("ID", deptid);
		JSONObject jsonObject = doGetStr(url);//通过get方法获取结果
		return jsonObject;
	}

	/**
	 * 创建人员
	 * @param accessToken
	 * @param deptJson  部门信息
	 * @return
	 */
	public static JSONObject userCreate(String accessToken,JSONObject userJson){
		String url = CommomUrl.USER_CREATE.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = doPostStr(url, userJson.toString());
		return jsonObject;
	}
	
	/**
	 * 更新人员
	 * @param accessToken
	 * @param deptJson  部门信息
	 * @return
	 */
	public static JSONObject userUpdate(String accessToken,JSONObject userJson){
		String url = CommomUrl.USER_UPDATE.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = doPostStr(url, userJson.toString());
		return jsonObject;
	}
	
	/**
	 * 删除人员
	 * @param accesstoken
	 * @param deptid
	 * @return
	 */
	public static JSONObject userDelete(String accessToken,String userid){
		String url = CommomUrl.USER_DELETE.replace("ACCESS_TOKEN", accessToken).replace("ID", userid);
		JSONObject jsonObject = doGetStr(url);//通过get方法获取结果
		return jsonObject;
	}
	
	
	/**
	 * 获取套件访问Token（suite_access_token）
	 * @param suite_key 应用套件key
	 * @param suite_secret  应用套件suite_secret
	 * @param suite_ticket  应用套件suite_ticket
	 * @return
	 */
	public static JSONObject getSuiteAccessToken(String suite_key, String suite_secret,String suite_ticket){
		JSONObject jsonReq = new JSONObject();
		jsonReq.put("suite_key", suite_key);
		jsonReq.put("suite_secret", suite_secret);
		jsonReq.put("suite_ticket", suite_ticket);
		JSONObject jsonObject = doPostStr(CommomUrl.SUITE_ACCESS_TOKEN_URL, jsonReq.toString()); 
		return jsonObject;
	}
	
	/**
	 * 获取企业的永久授权（包含永久授权码和企业corpid等信息）
	 * @param suite_access_token 套件suite_access_token
	 * @param tmp_auth_code  临时授权码
	 * @return
	 */
	public static JSONObject getPermanentCode(String suite_access_token,String tmp_auth_code){
		String url = CommomUrl.GET_PERMANENT_CODE_URL.replace("SUITE_ACCESS_TOKEN", suite_access_token);
		JSONObject jsonReq = new JSONObject();
		jsonReq.put("tmp_auth_code", tmp_auth_code);
		JSONObject jsonObject = doPostStr(url, jsonReq.toString());
		return jsonObject;
	}
	
	/**
	 * 激活授权套件
	 * @param suite_access_token  套件suite_access_token
	 * @param suite_key  应用套件key
	 * @param auth_corpid  	授权方corpid
	 * @param permanent_code  永久授权码
	 * @return
	 */
	public static JSONObject activateSuite(String suite_access_token,String suite_key,String auth_corpid,String permanent_code){
		String url = CommomUrl.ACTIVATE_SUITE_URL.replace("SUITE_ACCESS_TOKEN", suite_access_token);
		JSONObject jsonReq = new JSONObject();
		jsonReq.put("suite_key", suite_key);
		jsonReq.put("auth_corpid", auth_corpid);
		jsonReq.put("permanent_code", permanent_code);
		JSONObject jsonObject = doPostStr(url, jsonReq.toString());
		return jsonObject;
	}
	
	/**
	 * 获取企业授权的access_token
	 * @param suite_access_token
	 * @param auth_corpid  授权方corpid
	 * @param permanent_code  永久授权码
	 * @return
	 */
	public static JSONObject getCorpToken(String suite_access_token,String auth_corpid,String permanent_code){
		String url = CommomUrl.GET_CORP_TOKEN_URL.replace("SUITE_ACCESS_TOKEN", suite_access_token);
		JSONObject jsonReq = new JSONObject();
		jsonReq.put("auth_corpid", auth_corpid);
		jsonReq.put("permanent_code", permanent_code);
		JSONObject jsonObject = doPostStr(url, jsonReq.toString());
		return jsonObject;
	}
	
	/**
	 * 获取jsapi_ticket
	 * type 固定值为 jsapi
	 * @param accesstoken 企业的access_token
	 * @return
	 */
	public static JSONObject getJsapiTicket(String accesstoken){
		String url = CommomUrl.GET_JSAPI_TICKET_URL.replace("ACCESS_TOKE", accesstoken)+"&type=jsapi";
		JSONObject jsonObject = doGetStr(url);
		return jsonObject;
	}
	
	/**
	 * ISV为授权方的企业单独设置IP白名单
	 * @param suite_access_token  套件suite_access_token
	 * @param auth_corpid  授权方corpid
	 * @param ip_whitelist ip白名单列表；要为其设置的IP白名单,格式支持IP段,用星号表示,如【5.6.*.*】,代表从【5.6.0.*】到【5.6.255.*】的任意IP,在第三段设为星号时,将忽略第四段的值,注意:仅支持后两段设置为星号
	 * @return
	 */
	public static JSONObject setCorpIpWhiteList(String suite_access_token,String auth_corpid,List<String> ip_whitelist){
		String url = CommomUrl.SET_CORP_IPWHITELIST.replace("SUITE_ACCESS_TOKEN", suite_access_token);
		JSONObject jsonReq = new JSONObject();
		jsonReq.put("auth_corpid", auth_corpid);
		jsonReq.put("ip_whitelist", ip_whitelist);
		JSONObject jsonObject = doPostStr(url, jsonReq.toString());
		return jsonObject;
	}
	
	/**
	 * 获取企业授权的授权数据
	 * @param suite_access_token  套件suite_access_token
	 * @param auth_corpid  授权方corpid
	 * @param suite_key  套件key
	 * @return
	 */
	public static JSONObject getAuthInfo(String suite_access_token,String auth_corpid,String suite_key){
		String url = CommomUrl.GET_AUTH_INFO.replace("SUITE_ACCESS_TOKEN", suite_access_token);
		JSONObject jsonReq = new JSONObject();
		jsonReq.put("auth_corpid", auth_corpid);
		jsonReq.put("suite_key", suite_key);
		JSONObject jsonObject = doPostStr(url, jsonReq.toString());
		return jsonObject;
	}
	
	
	/**
	 * 获取企业的应用信息
	 * @param suite_access_token  套件suite_access_token
	 * @param suite_key  套件key
	 * @param auth_corpid  授权方corpid
	 * @param permanent_code  永久授权码
	 * @param agentid  	授权方应用id
	 * @return
	 */
	public static JSONObject getAgent(String suite_access_token,String suite_key,String auth_corpid,String permanent_code,String agentid){
		String url = CommomUrl.GET_AGENT.replace("SUITE_ACCESS_TOKEN", suite_access_token);
		JSONObject jsonReq = new JSONObject();
		jsonReq.put("suite_key", suite_key);
		jsonReq.put("auth_corpid", auth_corpid);
		jsonReq.put("permanent_code", permanent_code);
		jsonReq.put("agentid", agentid);
		JSONObject jsonObject = doPostStr(url, jsonReq.toString());
		return jsonObject;
	}
		
	/**
	 * 上传媒体文件
	 * @param accessToken
	 * @param type  媒体文件类型：分别有图片（image）、语音（voice）、普通文件(file)
	 * @param filePath  文件地址
	 * @return mediaId  文件标识
	 * @throws IOException 
	 * @throws OApiException 
	 */
	public static JSONObject mediaUpload(String accessToken,String type,String filePath) throws IOException, OApiException{
		String url = null;
		if(type==null){
			url = CommomUrl.MEDIA_UPLOAD.replace("ACCESS_TOKEN", accessToken);
		}else{
			url = CommomUrl.MEDIA_UPLOAD.replace("ACCESS_TOKEN", accessToken).replace("TYPE", type);
		}
		File file = new File(filePath);
		if(!file.exists() || !file.isFile()){
			throw new IOException("文件不存在");
		}
		JSONObject uploadMedia = HttpHelper.uploadMedia(url, file);
		return uploadMedia;
	}
	
//	/**
//	 * 下载媒体文件
//	 * @param accessToken
//	 * @param mediaId
//	 * @param fileDir
//	 * @throws Exception
//	 */
//	public static void download(String accessToken, String mediaId, String fileDir) throws Exception {
//		
//		String url = Env.OAPI_HOST + "/media/get?" + "access_token=" + accessToken + "&media_id="  + mediaId;
//		JSONObject response = HttpHelper.downloadMedia(url, fileDir);
//		System.out.println(response);
//	}
	
	/**
	 * 获取媒体文件
	 * @param accessToken
	 * @param media_id 文件标识
	 * @param fileDir 文件本地存储路径
	 * @return
	 * @throws OApiException 
	 */
	public static void mediaDownload(String accessToken,String media_id,String fileDir) throws OApiException{
		String url = CommomUrl.MEDIA_DOWNLOAD.replace("ACCESS_TOKEN", accessToken).replace("MEDIA_ID", media_id);
		System.out.println(url);
		com.alibaba.fastjson.JSONObject downloadMedia = HttpHelper.downloadMedia(url, fileDir);
		System.out.println(downloadMedia);
	}
	
	
	/**
	 * 新增文件到用户钉盘
	 * @param accessToken 
	 * @param agent_id  微应用的agentId
	 * @param code  如果是微应用，code值为微应用免登授权码,如果是服务窗应用，code值为服务窗免登授权码。code为临时授权码，只能消费一次，下次请求需要重新获取新的code
	 * @param media_id  媒体文件id
	 * @param space_id  钉盘空间id
	 * @param folder_id  钉盘文件夹id
	 * @param name  文件名称
	 * @param overwrite  遇到同名文件是否覆盖，若不覆盖，则会自动重命名本次新增的文件，默认为false
	 * @throws OApiException
	 */
	public static JSONObject dingSpaceFileAdd(String accessToken,String agent_id,String code,String media_id,String space_id,String folder_id,String name,String overwrite) throws OApiException{
		String url = CommomUrl.DINGSPACE_FILE_ADD.replace("ACCESS_TOKEN", accessToken).replace("AGENT_ID", agent_id).replace("CODE", code).replace("MEDIA_ID", media_id)
				.replace("SPACE_ID", space_id).replace("FOLDER_ID", folder_id).replace("NAME", name).replace("OVERWRITE", overwrite);
		JSONObject jsonObject = doGetStr(url);
		return jsonObject;
	}
	
	
	/**
	 * 获取企业下的自定义空间
	 * @param isIsv 是否为isv：true为isv；false为非isv
	 * @param accesstoken 企业的access_token
	 * @param agentid 企业应用agentid
	 * @return
	 */
	public static JSONObject getCustomSpace(boolean isIsv, String accesstoken, String agentid){
		String url = null;
		if(isIsv){
			url = CommomUrl.GET_CUSTOM_SPACE.replace("ACCESS_TOKEN", accesstoken).replace("&domain=DOMAIN", "").replace("AGENT_ID", agentid);
		}else{
			url = CommomUrl.GET_CUSTOM_SPACE.replace("ACCESS_TOKEN", accesstoken).replace("AGENT_ID", agentid);
		}
		System.out.println(url);
		JSONObject jsonObject = doGetStr(url);
		return jsonObject;
	}
	
	/**
	 * 获取企业下的自定义空间
	 * @param isIsv 是否为isv：true为isv；false为非isv
	 * @param accesstoken 企业的access_token
	 * @param agentid 企业应用agentid
	 * @param type 上传请传add，下载请传download
	 * @param userid 用户id
	 * @param path 授权访问的路径，如授权访问所有文件传“/”，授权访问/doc文件夹传“/doc/
	 * @param fileids 授权访问的文件id列表，id之间用英文逗号隔开，如“fileId1,fileId2”  (type为add时传空，type为download时传)
	 * @return
	 */
	public static JSONObject grantCustomSpace(boolean isIsv, String accesstoken, String agentid, String type, String userid, String path, String fileids){
		String url = null;
		if(isIsv){
			url = CommomUrl.GRANT_CUSTOM_SPACE.replace("ACCESS_TOKEN", accesstoken).replace("&domain=DOMAIN", "").replace("AGENT_ID", agentid).replace("TYPE", type).replace("USERID", userid).replace("&duration=DURATION", "");
		}else{
			url = CommomUrl.GRANT_CUSTOM_SPACE.replace("ACCESS_TOKEN", accesstoken).replace("AGENT_ID", agentid).replace("TYPE", type).replace("USERID", userid).replace("&duration=DURATION", "");
		}
		if("add".equals(type)){
			url = url.replace("PATH", "/").replace("&fileids=FILEIDS", "");
		}else if("download".equals(type)){
			url = url.replace("FILEIDS", fileids).replace("&path=PATH", "");
		}
		System.out.println(url);
		JSONObject jsonObject = doGetStr(url);
		return jsonObject;
	}
	
	
//	/**
//	 * ISV获取授权企业授权的部门列表 
//	 * （PS注：返回部门列表中，跟部门（id为1）无parentid，子部门有parentid）
//	 * @param accesstoken 企业的access_token
//	 * @return 
//	 * {deptinfolist":[{"id":1,"createDeptGroup":true,"name":"测试企业","autoAddUser":true},{"id":41788307,"createDeptGroup":false,"name":"test_OM","parentid":1,"autoAddUser":false},{"id":41788308,"createDeptGroup":false,"name":"移动","parentid":1,"autoAddUser":false},{"id":41788309,"createDeptGroup":false,"name":"事业","parentid":1,"autoAddUser":false},{"id":42453233,"createDeptGroup":true,"name":"测试","parentid":1,"autoAddUser":true},{"id":42599178,"createDeptGroup":true,"name":"移动子部门","parentid":41788308,"autoAddUser":true}]}
//	 */
//	public static JSONObject getCorpDepartmentList(String accesstoken){
//		JSONObject json_resp = new JSONObject();//返回部门列表 返回参数示例：{deptinfolist":[{"id":1,"createDeptGroup":true,"name":"测试企业","autoAddUser":true},{"id":41788307,"createDeptGroup":false,"name":"test_OM","parentid":1,"autoAddUser":false},{"id":41788308,"createDeptGroup":false,"name":"移动","parentid":1,"autoAddUser":false},{"id":41788309,"createDeptGroup":false,"name":"事业","parentid":1,"autoAddUser":false},{"id":42453233,"createDeptGroup":true,"name":"测试","parentid":1,"autoAddUser":true},{"id":42599178,"createDeptGroup":true,"name":"移动子部门","parentid":41788308,"autoAddUser":true}]}
//		JSONArray jsonArray_depts = new JSONArray();//返回部门列表 返回参数示例：[{"id":1,"createDeptGroup":true,"name":"测试企业","autoAddUser":true},{"id":41788307,"createDeptGroup":false,"name":"test_OM","parentid":1,"autoAddUser":false},{"id":41788308,"createDeptGroup":false,"name":"移动","parentid":1,"autoAddUser":false},{"id":41788309,"createDeptGroup":false,"name":"事业","parentid":1,"autoAddUser":false},{"id":42453233,"createDeptGroup":true,"name":"测试","parentid":1,"autoAddUser":true},{"id":42599178,"createDeptGroup":true,"name":"移动子部门","parentid":41788308,"autoAddUser":true}]
//		
//		JSONArray jsonArrayAuthScopes = new JSONArray();
//		JSONObject jsonAuthScopes = getAuthScopes(accesstoken); //获取通讯录权限
//		if(!jsonAuthScopes.isEmpty()){
//			jsonArrayAuthScopes = jsonAuthScopes.getJSONObject("auth_org_scopes").getJSONArray("authed_dept"); //授权部门id列表
//		}
//		if(jsonArrayAuthScopes.size()>0 && !jsonArrayAuthScopes.isEmpty()){  //授权类型为：全部员工 或者 部分员工  authed_dept":[1] 或者 authed_dept":[xxx,xxx,xxx...]
//			if(jsonArrayAuthScopes.size()==1 && "1".equals(jsonArrayAuthScopes.get(0).toString())){ //授权类型为：全部员工， authed_dept":[1]，部门id只有一个（也就是企业部门(根部门)id），值为1
//				JSONObject jsondepts = getDepartments(accesstoken);  //获取部门列表
//				if(!jsondepts.isEmpty()){
//					jsonArray_depts = jsondepts.getJSONArray("department");
//				}
//			}else{  //授权类型为：部分员工， authed_dept":[xxx,xxx,xxx...]，多个部门id
//				for(int i=0;i<jsonArrayAuthScopes.size();i++){
//					String deptid = jsonArrayAuthScopes.getString(i);
//					JSONObject departmentDetail = getDepartmentDetail(accesstoken, deptid); //获取部门详情
//					JSONObject jsonDeptInfo = new JSONObject();
//					if(!departmentDetail.isEmpty()){
//						jsonDeptInfo.put("id", departmentDetail.getString("id"));  //部门id
//						jsonDeptInfo.put("createDeptGroup", departmentDetail.getString("createDeptGroup")); //是否同步创建一个关联此部门的企业群, true表示是, false表示不是
//						jsonDeptInfo.put("name", departmentDetail.getString("name"));  //部门名称
//						jsonDeptInfo.put("autoAddUser", departmentDetail.getString("autoAddUser"));  //当群已经创建后，是否有新人加入部门会自动加入该群, true表示是, false表示不是
//						jsonDeptInfo.put("parentid", departmentDetail.getString("parentid"));  //父部门id，根部门为1
//					}
//					jsonArray_depts.add(jsonDeptInfo);
//				}
//			}
//		}else{  //授权类型为：管理员（包括子管理员） authed_dept":[]
//			
//		}
//		json_resp.put("deptinfolist", jsonArray_depts);
//		return json_resp;
//	}
	
	/**
	 * ISV获取授权企业授权的部门列表 
	 * （PS注：返回部门列表中，跟部门（id为1）无parentid，子部门有parentid）
	 * @param accesstoken 企业的access_token
	 * @return 
	 * {deptinfolist":[{"id":1,"createDeptGroup":true,"name":"测试企业","autoAddUser":true},{"id":41788307,"createDeptGroup":false,"name":"test_OM","parentid":1,"autoAddUser":false},{"id":41788308,"createDeptGroup":false,"name":"移动","parentid":1,"autoAddUser":false},{"id":41788309,"createDeptGroup":false,"name":"事业","parentid":1,"autoAddUser":false},{"id":42453233,"createDeptGroup":true,"name":"测试","parentid":1,"autoAddUser":true},{"id":42599178,"createDeptGroup":true,"name":"移动子部门","parentid":41788308,"autoAddUser":true}]}
	 */
	public static JSONObject getCorpDepartmentList(String accesstoken){
		JSONObject json_resp = new JSONObject();//返回部门列表 返回参数示例：{deptinfolist":[{"id":1,"createDeptGroup":true,"name":"测试企业","autoAddUser":true},{"id":41788307,"createDeptGroup":false,"name":"test_OM","parentid":1,"autoAddUser":false},{"id":41788308,"createDeptGroup":false,"name":"移动","parentid":1,"autoAddUser":false},{"id":41788309,"createDeptGroup":false,"name":"事业","parentid":1,"autoAddUser":false},{"id":42453233,"createDeptGroup":true,"name":"测试","parentid":1,"autoAddUser":true},{"id":42599178,"createDeptGroup":true,"name":"移动子部门","parentid":41788308,"autoAddUser":true}]}
		JSONArray jsonArray_depts = new JSONArray();//返回部门列表 返回参数示例：[{"id":1,"createDeptGroup":true,"name":"测试企业","autoAddUser":true},{"id":41788307,"createDeptGroup":false,"name":"test_OM","parentid":1,"autoAddUser":false},{"id":41788308,"createDeptGroup":false,"name":"移动","parentid":1,"autoAddUser":false},{"id":41788309,"createDeptGroup":false,"name":"事业","parentid":1,"autoAddUser":false},{"id":42453233,"createDeptGroup":true,"name":"测试","parentid":1,"autoAddUser":true},{"id":42599178,"createDeptGroup":true,"name":"移动子部门","parentid":41788308,"autoAddUser":true}]
		
		JSONArray jsonArrayAuthScopes = new JSONArray();
		JSONObject jsonAuthScopes = getAuthScopes(accesstoken); //获取通讯录权限
		if(jsonAuthScopes != null){
			jsonArrayAuthScopes = jsonAuthScopes.getJSONObject("auth_org_scopes").getJSONArray("authed_dept"); //授权部门id列表
		}
		if(jsonArrayAuthScopes.size()>0 && !jsonArrayAuthScopes.isEmpty()){  //授权类型为：全部员工 或者 部分员工  authed_dept":[1] 或者 authed_dept":[xxx,xxx,xxx...]
			if(jsonArrayAuthScopes.size()==1){ //授权类型为全部，或者为某一个部门（corpsecret勾选某一级部门）
				String deptid = jsonArrayAuthScopes.get(0).toString();
				if("1".equals(deptid)){ //授权类型为：全部员工， authed_dept":[1]，部门id只有一个（也就是企业部门(根部门)id），值为1
					JSONObject jsondepts = getDepartments(accesstoken,"","");  //获取部门列表
					if(jsondepts != null ){
						jsonArray_depts = jsondepts.getJSONArray("department");
					}
				}else{  //或者corpsecret勾选为某一级部门
					JSONObject departmentDetail = getDepartmentDetail(accesstoken, deptid); //获取所勾选的部门（虚拟顶级部门）详情
					JSONObject jsonDeptInfo = new JSONObject();
					if(departmentDetail != null){
						jsonDeptInfo.put("id", departmentDetail.getString("id"));  //部门id
						jsonDeptInfo.put("createDeptGroup", departmentDetail.getString("createDeptGroup")); //是否同步创建一个关联此部门的企业群, true表示是, false表示不是
						jsonDeptInfo.put("name", departmentDetail.getString("name"));  //部门名称
						jsonDeptInfo.put("autoAddUser", departmentDetail.getString("autoAddUser"));  //当群已经创建后，是否有新人加入部门会自动加入该群, true表示是, false表示不是
						jsonDeptInfo.put("parentid", departmentDetail.getString("parentid"));  //父部门id，根部门为1
					}
					jsonArray_depts.add(jsonDeptInfo);
					
					JSONObject jsondepts = getDepartments(accesstoken,deptid,"true");  //获取所有下级部门列表
					if(jsondepts != null ){
						jsonArray_depts.addAll(jsondepts.getJSONArray("department"));
					}
				}
			}else{  //授权类型为：部分员工， authed_dept":[xxx,xxx,xxx...]，多个部门id
				for(int i=0;i<jsonArrayAuthScopes.size();i++){
					String deptid = jsonArrayAuthScopes.getString(i);
					JSONObject departmentDetail = getDepartmentDetail(accesstoken, deptid); //获取部门详情
					JSONObject jsonDeptInfo = new JSONObject();
					if(departmentDetail != null){
						jsonDeptInfo.put("id", departmentDetail.getString("id"));  //部门id
						jsonDeptInfo.put("createDeptGroup", departmentDetail.getString("createDeptGroup")); //是否同步创建一个关联此部门的企业群, true表示是, false表示不是
						jsonDeptInfo.put("name", departmentDetail.getString("name"));  //部门名称
						jsonDeptInfo.put("autoAddUser", departmentDetail.getString("autoAddUser"));  //当群已经创建后，是否有新人加入部门会自动加入该群, true表示是, false表示不是
						jsonDeptInfo.put("parentid", departmentDetail.getString("parentid"));  //父部门id，根部门为1
					}
					jsonArray_depts.add(jsonDeptInfo);
				}
			}
		}else{  //授权类型为：管理员（包括子管理员） authed_dept":[]
			
		}
		json_resp.put("deptinfolist", jsonArray_depts);
		return json_resp;
	}
	
	/**
	 * 企业会话消息接口 （企业发消息）（V1.0）
	 * @param newsCommon
	 * @param oaNews
	 * @param formType  是否显示form: true显示、false不显示
	 * @return
	 * @throws ApiException
	 */
	public static JSONObject sendNews(NewsCommon newsCommon,OANews oaNews,Boolean formType) throws ApiException{
		String url = CommomUrl.ECO_ROUTE_REST;
		DingTalkClient client = new DefaultDingTalkClient(url);
		CorpMessageCorpconversationAsyncsendRequest req = new CorpMessageCorpconversationAsyncsendRequest();
		String userids = newsCommon.getUserids();  //每次推送不能超过20人
		int strNums = getStrNums(userids, ",");//id集合中逗号出现的次数
		
		List<String> ListUserids = new ArrayList<>(); //推送的userids集合：[1,2,3...20],[21,22,23...40]...
		if(strNums > 19){  //大于20人
			String[] stringToArray = StringToArray(userids, ",");  //字符串转数组
			Object[] splitAry = splitAry(stringToArray, 20);  //拆分数组
			for(Object ary : splitAry){
//				JSONArray jsonArray = JSONArray.parseArray(StringBaseOpt.objectToString(ary));
				JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(ary));
				String useridsSend="";
				for(int i = 0; i < jsonArray.size(); i++){
					if("".equals(useridsSend) || useridsSend==null){
						useridsSend = jsonArray.getString(i);
					}else{
						useridsSend = useridsSend+","+jsonArray.getString(i);
					}
					
				}
				ListUserids.add(useridsSend);
			}
		}else{
			ListUserids.add(userids);
		}
		
		String msgType = newsCommon.getMsgType();
    	req.setMsgtype(msgType);
    	req.setAgentId(Long.valueOf(newsCommon.getAgentid()));
//    	req.setUseridList(newsCommon.getUserids());
    	req.setDeptIdList(newsCommon.getDeptids());
    	req.setToAllUser(newsCommon.isToAllFlag());
    	
    	JSONObject msgContent = new JSONObject();
		if("oa".equals(msgType)){
			JSONObject json_head = new JSONObject();
			json_head.put("bgcolor", oaNews.getHead_bgcolor());
			json_head.put("text", oaNews.getHead_text());
			
			JSONObject json_body = new JSONObject();
			json_body.put("title", oaNews.getBody_title());
			json_body.put("content", oaNews.getBody_content());
			if(formType){
				json_body.put("form", oaNews.getBody_form());
			}

			msgContent.put("message_url", oaNews.getMessage_url());
			msgContent.put("head", json_head);
			msgContent.put("body", json_body);
		}else if("text".equals(msgType)){
			msgContent.put("content", oaNews.getBody_content());
		}
		
    	req.setMsgcontentString(msgContent.toString());
    	
    	CorpMessageCorpconversationAsyncsendResponse rsp = null;
    	for(String ids : ListUserids){
    		req.setUseridList(ids);
    		rsp = client.execute(req, newsCommon.getCorpAccessToken());
    	}
		return JSONObject.parseObject(rsp.getBody());
	}

	/**
	 * 企业会话消息接口 （企业发消息）（V2.0）
	 * @param newsCommon
	 * @param oaNews
	 * @param formType  是否显示form: true显示、false不显示
	 * @return
	 * @throws ApiException
	 */
	public static JSONObject sendNewsV2(NewsCommon newsCommon,OANews oaNews,Boolean formType) throws ApiException{
		String url = CommomUrl.MESSAGE_ASYNCSEND_V2;
		DingTalkClient client = new DefaultDingTalkClient(url);
		OapiMessageCorpconversationAsyncsendV2Request req = new OapiMessageCorpconversationAsyncsendV2Request();
		
		//用户
		String userids = newsCommon.getUserids();
		List<String> listUserids = StringToPartList(userids, ",", 5000);  //把splitStr分隔的字符串str切分成partSize大小的一个个list集合，如果不够拆分，则直接把str放入list中
		
		//部门
		String deptids = newsCommon.getDeptids();
		List<String> listDeptids = StringToPartList(deptids, ",", 500);  //把splitStr分隔的字符串str切分成partSize大小的一个个list集合，如果不够拆分，则直接把str放入list中
		
		String msgType = newsCommon.getMsgType();
		req.setAgentId(Long.valueOf(newsCommon.getAgentid()));
//		req.setDeptIdList(newsCommon.getDeptids());
		req.setToAllUser(newsCommon.isToAllFlag());
		
		JSONObject msgContent = new JSONObject();
		if("oa".equals(msgType)){
			msgContent.put("msgtype", msgType);
			
			JSONObject json_head = new JSONObject();
			json_head.put("bgcolor", oaNews.getHead_bgcolor());
			json_head.put("text", oaNews.getHead_text());
			
			JSONObject json_body = new JSONObject();
			json_body.put("title", oaNews.getBody_title());
			json_body.put("content", oaNews.getBody_content());
			if(formType){
				json_body.put("form", oaNews.getBody_form());
			}
			JSONObject oa = new JSONObject();
			oa.put("message_url", oaNews.getMessage_url());
			oa.put("head", json_head);
			oa.put("body", json_body);
			
			msgContent.put("oa", oa);
		}else if("text".equals(msgType)){
			msgContent.put("msgtype", msgType);
			
			JSONObject text = new JSONObject();
			text.put("content", oaNews.getBody_content());
			msgContent.put("text", text);
		}
		
		req.setMsg(msgContent.toString());
		
		OapiMessageCorpconversationAsyncsendV2Response rsp = null;
		
		for(String ids : listUserids){
			req.setUseridList(ids);
			rsp = client.execute(req, newsCommon.getCorpAccessToken());
		}
		
		req.setUseridList(null);
		for(String deptIds : listDeptids){
			req.setDeptIdList(deptIds);
			rsp = client.execute(req, newsCommon.getCorpAccessToken());
		}
		return JSONObject.parseObject(rsp.getBody());
	}
	
	/**
	 * 企业会话消息接口 （企业发消息）
	 * @param newsCommon
	 * @param oaNews
	 * @param formType  是否显示form: true显示、false不显示
	 * @return
	 * @throws ApiException
	 */
//	public static JSONObject sendNews2(NewsCommon newsCommon,JSONObject jsonOA) throws ApiException{
//		String url = CommomUrl.ECO_ROUTE_REST;
//		DingTalkClient client = new DefaultDingTalkClient(url);
//		CorpMessageCorpconversationAsyncsendRequest req = new CorpMessageCorpconversationAsyncsendRequest();
//		String userids = newsCommon.getUserids();  //每次推送不能超过20人
//		int strNums = getStrNums(userids, ",");//id集合中逗号出现的次数
//		
//		List<String> ListUserids = new ArrayList<>(); //推送的userids集合：[1,2,3...20],[21,22,23...40]...
//		if(strNums > 19){  //大于20人
//			String[] stringToArray = StringToArray(userids, ",");  //字符串转数组
//			Object[] splitAry = splitAry(stringToArray, 20);  //拆分数组
//			for(Object ary : splitAry){
//				JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(ary));
//				String useridsSend="";
//				for(int i = 0; i < jsonArray.size(); i++){
//					if("".equals(useridsSend) || useridsSend==null){
//						useridsSend = jsonArray.getString(i);
//					}else{
//						useridsSend = useridsSend+","+jsonArray.getString(i);
//					}
//					
//				}
//				ListUserids.add(useridsSend);
//			}
//		}else{
//			ListUserids.add(userids);
//		}
//		
//		String msgType = newsCommon.getMsgType();
//		req.setMsgtype(msgType);
//		req.setAgentId(Long.valueOf(newsCommon.getAgentid()));
////    	req.setUseridList(newsCommon.getUserids());
//		req.setDeptIdList(newsCommon.getDeptids());
//		req.setToAllUser(newsCommon.isToAllFlag());
//		
//		req.setMsgcontentString(jsonOA.toString());
//		
//		String accesstoken = newsCommon.getCorpAccessToken();
//		if("".equals(accesstoken) || accesstoken==null){
//			ServCorpInfo servCorpInfo = CommonInit.servCorpInfoDao.queryOne(null);
//			accesstoken = servCorpInfo.getAccesstoken();
//		}
//		
//		CorpMessageCorpconversationAsyncsendResponse rsp = null;
//		for(String ids : ListUserids){
//			req.setUseridList(ids);
//			rsp = client.execute(req, accesstoken);
//		}
//		return JSONObject.parseObject(rsp.getBody());
//	}
	
	/**
	 * 通讯录：注册事件回调接口
	 * @param accesstoken 企业的accesstoken
	 * @param callBackTag  需要监听的事件类型，共有20种（Array[String]）
	 * @param token  	加解密需要用到的token，ISV(服务提供商)推荐使用注册套件时填写的token，普通企业可以随机填写
	 * @param aesKey  	数据加密密钥。用于回调数据的加密，长度固定为43个字符，从a-z, A-Z, 0-9共62个字符中选取,您可以随机生成，ISV(服务提供商)推荐使用注册套件时填写的EncodingAESKey
	 * @param callBackUrl  	接收事件回调的url
	 * @return
	 * @throws ApiException
	 */
	public static JSONObject registerCallBack(String accesstoken,List<String> callBackTag,String token,String aesKey,String callBackUrl) throws ApiException{
		String url = CommomUrl.REGISTER_CALL_BACK.replace("ACCESS_TOKEN", accesstoken);
		JSONObject jsonReq = new JSONObject();
		jsonReq.put("call_back_tag", callBackTag);
		jsonReq.put("token", token);
		jsonReq.put("aes_key", aesKey);
		jsonReq.put("url", callBackUrl);
		
		System.out.println(jsonReq.toString());
		JSONObject jsonObject = doPostStr(url, jsonReq.toString());
		return jsonObject;
	}
	
	/**
	 * 通讯录：查询事件回调接口
	 * @param accesstoken  企业的accesstoken
	 * @return
	 */
	public static JSONObject getCallBack(String accesstoken){
		String url = CommomUrl.GET_CALL_BACK.replace("ACCESS_TOKEN", accesstoken);
		JSONObject jsonObject = doGetStr(url);
		return jsonObject;
	}
	
	/**
	 * 通讯录：更新事件回调接口
	 * @param accesstoken 企业的accesstoken
	 * @param callBackTag  需要监听的事件类型，共有20种（Array[String]）
	 * @param token  	加解密需要用到的token，ISV(服务提供商)推荐使用注册套件时填写的token，普通企业可以随机填写
	 * @param aesKey  	数据加密密钥。用于回调数据的加密，长度固定为43个字符，从a-z, A-Z, 0-9共62个字符中选取,您可以随机生成，ISV(服务提供商)推荐使用注册套件时填写的EncodingAESKey
	 * @param callBackUrl  	接收事件回调的url
	 * @return
	 * @throws ApiException
	 */
	public static JSONObject updateCallBack(String accesstoken,List<String> callBackTag,String token,String aesKey,String callBackUrl) throws ApiException{
		String url = CommomUrl.UPDATE_CALL_BACK.replace("ACCESS_TOKEN", accesstoken);
		JSONObject jsonReq = new JSONObject();
		jsonReq.put("call_back_tag", callBackTag);
		jsonReq.put("token", token);
		jsonReq.put("aes_key", aesKey);
		jsonReq.put("url", callBackUrl);
		
		JSONObject jsonObject = doPostStr(url, jsonReq.toString());
		return jsonObject;
	}
	
	/**
	 * 通讯录：删除事件回调接口
	 * @param accesstoken  企业的accesstoken
	 * @return
	 */
	public static JSONObject deleteCallBack(String accesstoken){
		String url = CommomUrl.DELETE_CALL_BACK.replace("ACCESS_TOKEN", accesstoken);
		JSONObject jsonObject = doGetStr(url);
		return jsonObject;
	}

	/**
	 * 通讯录：获取回调失败的结果
	 * @param accesstoken  企业的accesstoken
	 * @return
	 */
	public static JSONObject getCallBackFailedResult(String accesstoken){
		String url = CommomUrl.GET_CALL_BACK_FAILED_RESULT.replace("ACCESS_TOKEN", accesstoken);
		JSONObject jsonObject = doGetStr(url);
		return jsonObject;
	}
	
	/** 
	  * 将emoji表情替换成空串 
	  * @param source 
	  * @return 过滤后的字符串 
	  */  
	 public static String filterEmoji(String source) {  
		 if (source != null && source.length() > 0) {  
			 return source.replaceAll("[\ud800\udc00-\udbff\udfff\ud800-\udfff]", "");  
		 } else {  
			 return source;  
		 }  
	 }
	
	 /**
	  * 把splitStr分隔的字符串str切分成partSize大小的一个个list集合，如果不够拆分，则直接把str放入list中
	  * @param str splitStr分隔的字符串
	  * @param splitStr 分隔字符串的符号，如：逗号，
	  * @param partSize 切分大小，例如：20
	  * @return
	  */
	 public static List<String> StringToPartList(String str, String splitStr, int partSize){
		 int strNums = getStrNums(str, splitStr);  //str中分隔符splitStr出现的次数
		 List<String> listStrParts = new ArrayList<>(); //分隔后listStr集合，例如：[1,2,3...20],[21,22,23...40]...
		 if(strNums > (partSize-1)){ //大于 partSize
			 String[] stringToArray = StringToArray(str, splitStr);  //字符串转数组
			 Object[] splitAry = splitAry(stringToArray, partSize);  //拆分数组
			 for(Object ary : splitAry){
				 JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(ary));
				 
				 String strPart="";
				 for(int i = 0; i < jsonArray.size(); i++){
					 if("".equals(strPart) || strPart==null){
						 strPart = jsonArray.getString(i);
					 }else{
						 strPart = strPart + splitStr + jsonArray.getString(i);
					 }
				 }
				 listStrParts.add(strPart);
			 }
		 }else {
			 if(StringUtils.isNotBlank(str)){
				 listStrParts.add(str);
			 }
		 }
		return listStrParts;
	 }
	 
	 
	/**
	 * 查询字符串中(str)某个字符串(key)出现的次数
	 * @param str
	 * @param key
	 * @return
	 */
	public static int getStrNums(String str , String key) {  
        int count = 0;  
        int index = 0;  
        if(!"".equals(str) && str!=null){
        	while((index = str.indexOf(key,index))!=-1) {  
                index = index+key.length();  
                count++;  
            }  
        }
        return count;  
    }  
	
	/**
	 * 字符串转数组
	 * @param str 字符串
	 * @param splitStr 分隔符
	 * @return
	 */
	public static String[] StringToArray(String str,String splitStr){
		String[] arrayStr = null;
    	if(!"".equals(str) && str != null){
			if(str.indexOf(splitStr)!=-1){
				arrayStr = str.split(splitStr);
			}else{
				arrayStr = new String[1];
				arrayStr[0] = str;
			}
		}
		return arrayStr;
	}
	
	/**
	 * 拆分数组
	 * @param ary数组
	 * @param subSize拆分大小
	 * @return
	 */
	private static Object[] splitAry(String[] ary, int subSize) {
		int count = ary.length % subSize == 0 ? ary.length / subSize : ary.length / subSize + 1;
		List<List<Object>> subAryList = new ArrayList<List<Object>>();
		for (int i = 0; i < count; i++) {
			int index = i * subSize;
			List<Object> list = new ArrayList<Object>();
			int j = 0;
			while (j < subSize && index < ary.length) {
				list.add(ary[index++]);
				j++;
			}
			subAryList.add(list);
		}
		Object[] subAry = new Object[subAryList.size()];
		for (int i = 0; i < subAryList.size(); i++) {
			List<Object> subList = subAryList.get(i);
			Object[] subAryItem = new Object[subList.size()];
			for (int j = 0; j < subList.size(); j++) {
				subAryItem[j] = subList.get(j);
			}
			subAry[i] = subAryItem;
		}
		return subAry;
	}
	
	/**
	 * 网络文件下载保存
	 * @param url 文件url
	 * @param dir 存储目录
	 * @param fileName 存储文件名
	 */
	public static boolean downloadHttpUrl(String url, String dir, String fileName) {  
		boolean flag = false;
        try {  
            URL httpurl = new URL(url);  
            File dirfile = new File(dir);    
            if (!dirfile.exists()) {    
                dirfile.mkdirs();  
            }  
            FileUtils.copyURLToFile(httpurl, new File(dir+fileName));
            flag = true;
        } catch (Exception e) {  
            e.printStackTrace();  
        }
		return flag;  
    } 
	
	/**
	 * 向企业员工添加人脸识别照片(dingtalk.corp.smartdevice.addface)
	 * @param accessToken
	 * @param MediaId  识别用照片id，安全考虑，获取成功后立即删除
	 * @param Userid  用户id
	 * @param UserType  用户类型，用于区别不同的识别问候语 如interview,friends,business,communication,training,inspection,other
	 * @param startDateTime  终端识别有效期开始时间 timestamp(毫秒)
	 * @param endDatetime  终端识别有效期截止时间 timestamp(毫秒)
	 * @return
	 * @throws ApiException
	 */
	public static JSONObject m2DeviceAddface(String accessToken, String mediaId, String userid, String userType, Long startDateTime, Long endDatetime){
		CorpSmartdeviceAddfaceResponse rsp = null;
		try {
			String url = CommomUrl.ECO_ROUTE_REST;
			DingTalkClient client = new DefaultDingTalkClient(url);
			CorpSmartdeviceAddfaceRequest req = new CorpSmartdeviceAddfaceRequest();
			DidoFaceVO obj1 = new DidoFaceVO();
			obj1.setMediaId(mediaId);
			obj1.setUserid(userid);
			obj1.setUserType(userType);
//			obj1.setStartDate(startDateTime);
//			obj1.setEndDate(endDatetime);
			req.setFaceVo(obj1);
			rsp = client.execute(req, accessToken);
		} catch (ApiException e) {
			e.printStackTrace();
		}
		return JSONObject.parseObject(rsp.getBody());
	}

	/**
	 * 查询企业员工是否已录入人脸 (dingtalk.corp.smartdevice.hasface)
	 * @param accessToken
	 * @param userIdList  用户id列表,最大列表长度：20
	 * @return
	 * @throws ApiException
	 */
	public static JSONObject queryM2FaceUserSetList(String accessToken, String userIdList){
		CorpSmartdeviceHasfaceResponse rsp = null;
		try {
			String url = CommomUrl.ECO_ROUTE_REST;
			DingTalkClient client = new DefaultDingTalkClient(url);
			CorpSmartdeviceHasfaceRequest req = new CorpSmartdeviceHasfaceRequest();
			req.setUseridList(userIdList);
			rsp = client.execute(req, accessToken);
		} catch (ApiException e) {
			e.printStackTrace();
		}
		return JSONObject.parseObject(rsp.getBody());
	}

	/**
	 * 拉取企业下某类设备列表(dingtalk.corp.device.manage.querylist)
	 * @param accessToken
	 * @param device_service_id  设备产品类型 产品编码：M1：9 C1：14 M2：15 D1：24
	 * @param cursor  分页拉取设备列表的游标，首次拉取可传Null或者0
	 * @param size  最大值：20;单次请求的大小，最大不超过20
	 * @return
	 * @throws ApiException
	 */
	public static JSONObject queryDeviceList(String accessToken,Long device_service_id, Long cursor, Long size){
			CorpDeviceManageQuerylistResponse rsp = null;
			try {
				String url = CommomUrl.ECO_ROUTE_REST;
				DingTalkClient client = new DefaultDingTalkClient(url);
				CorpDeviceManageQuerylistRequest req = new CorpDeviceManageQuerylistRequest();
				req.setDeviceServiceId(device_service_id);
				req.setCursor(cursor);
				req.setSize(size);
				rsp = client.execute(req, accessToken);
			} catch (ApiException e) {
				e.printStackTrace();
			}
		return JSONObject.parseObject(rsp.getBody());
	}

	/**
	 * 获取单设备详情 (dingtalk.corp.device.manage.get)
	 * @param accessToken
	 * @param device_service_id  设备产品类型 产品编码：M1：9 C1：14 M2：15 D1：24
	 * @param deviceId  设备id
	 * @return
	 * @throws ApiException
	 */
	public static JSONObject queryDeviceDetail(String accessToken,Long device_service_id, String deviceId){
		CorpDeviceManageGetResponse rsp = null;
		try {
			String url = CommomUrl.ECO_ROUTE_REST;
			DingTalkClient client = new DefaultDingTalkClient(url);
			CorpDeviceManageGetRequest req = new CorpDeviceManageGetRequest();
			req.setDeviceServiceId(device_service_id);
			req.setDeviceId(deviceId);
			rsp = client.execute(req, accessToken);
		} catch (ApiException e) {
			e.printStackTrace();
		}
		return JSONObject.parseObject(rsp.getBody());
	}

	/**
	 * 更改设备昵称 (dingtalk.corp.device.nick.update)
	 * @param accessToken
	 * @param device_service_id  设备产品类型 产品编码：M1：9 C1：14 M2：15 D1：24
	 * @param deviceId  设备id
	 * @param newNick  新名称
	 * @return
	 * @throws ApiException
	 */
	public static JSONObject updateDeviceName(String accessToken,Long device_service_id, String deviceId, String newNick){
		CorpDeviceNickUpdateResponse rsp = null;
		try {
			String url = CommomUrl.ECO_ROUTE_REST;
			DingTalkClient client = new DefaultDingTalkClient(url);
			CorpDeviceNickUpdateRequest req = new CorpDeviceNickUpdateRequest();
			req.setDeviceServiceId(device_service_id);
			req.setDeviceId(deviceId);
			req.setNewNick(newNick);
			rsp = client.execute(req, accessToken);
		} catch (ApiException e) {
			e.printStackTrace();
		}
		return JSONObject.parseObject(rsp.getBody());
	}

	/**
	 * 设备解绑 (dingtalk.corp.device.manage.unbind)
	 * @param accessToken
	 * @param device_service_id  设备产品类型 产品编码：M1：9 C1：14 M2：15 D1：24
	 * @param deviceId  设备id
	 * @return
	 * @throws ApiException
	 */
	public static JSONObject unbindDevice(String accessToken,Long device_service_id, String deviceId){
		CorpDeviceManageUnbindResponse rsp = null;
		try {
			String url = CommomUrl.ECO_ROUTE_REST;
			DingTalkClient client = new DefaultDingTalkClient(url);
			CorpDeviceManageUnbindRequest req = new CorpDeviceManageUnbindRequest();
			req.setDeviceServiceId(device_service_id);
			req.setDeviceId(deviceId);
			rsp = client.execute(req, accessToken);
		} catch (ApiException e) {
			e.printStackTrace();
		}
		return JSONObject.parseObject(rsp.getBody());
	}
	
	/**
	 * 添加用户识别成功后的通知(dingtalk.corp.smartdevice.addrecognizenotify)
	 * @param accessToken
	 * @param userList
	 * @return
	 */
	public static JSONObject addrecognizenotify(String accessToken,List<String> userList){
		CorpSmartdeviceAddrecognizenotifyResponse rsp = new CorpSmartdeviceAddrecognizenotifyResponse();
		try {
			DingTalkClient client = new DefaultDingTalkClient(CommomUrl.ECO_ROUTE_REST);
			CorpSmartdeviceAddrecognizenotifyRequest req = new CorpSmartdeviceAddrecognizenotifyRequest();
			RecognizeNotifyVO obj1 = new RecognizeNotifyVO();
			obj1.setNotifyUserList(userList);
			obj1.setNotifyType(1L);
			obj1.setNotifyTemplate("notify.template.key");
			obj1.setRecognizeStarttime(1515600565000L);
			obj1.setRecognizeEndtime(1515600565000L);
			obj1.setAppointedEndtime(1515600565000L);
			obj1.setAppointedStarttime(1515600565000L);
			obj1.setUserid("12345");
			req.setNotifyVo(obj1);
			rsp = client.execute(req, accessToken);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSONObject.parseObject(rsp.getBody());
	}
	
	
	

	public static void main(String[] args) throws IOException, OApiException, ApiException {
//		String accesstoken = "2184f2d038703f2492895e0e2da43d47";
//		Long next_cursor = 0L;
		
//		JSONObject jsapiTicket = getJsapiTicket("c74f9efaeb053414b53eacce7f6a3e51");
//		System.out.println(jsapiTicket);
		
//		JSONObject jsonDeviceList = queryDeviceList(accesstoken, 15L, next_cursor, 20L);
//		System.out.println(jsonDeviceList);
//		
//		JSONObject result = jsonDeviceList.getJSONObject("dingtalk_corp_device_manage_querylist_response").getJSONObject("result").getJSONObject("result");  //返回的Page对象
//		System.out.println(result);
//		
//		JSONArray deviceList = result.getJSONObject("list").getJSONArray("open_device_vo");  //设备列表
//		System.out.println(deviceList);
//		
//		
//		next_cursor = result.getLong("next_cursor");  //下次拉取列表的游标，如果为Null，代表没有数据了
//		System.out.println(next_cursor);
//		
//		if(next_cursor == null){
//			System.out.println(deviceList);
//		}else {
//			
//		}
		
//		String accesstoken = "9974c2b740a43ef3af0980429bbfaccf";

//		String mtype = "image";
//		String filePath = "D:/ts/44.JPG";
//		JSONObject mediaUpload = mediaUpload(accesstoken, mtype, filePath);
//		System.out.println(mediaUpload);
		
		
		
//		String mediaId = "@lADPBY0V4zG3E6XNBm3NBDg";
//		String userid = "0140271200847835";
//		String usertype = "inspection";
//		Long starttime = 1521715680000L;
//		Long endtime = 4101595200000L;
//		JSONObject m2DeviceAddface = m2DeviceAddface(accesstoken, mediaId, userid, usertype, starttime, endtime);
//		System.out.println(m2DeviceAddface);
//		
//		String ding_open_errcode = m2DeviceAddface.getJSONObject("dingtalk_corp_smartdevice_addface_response").getJSONObject("result").getString("ding_open_errcode");
//		
//		System.out.println(ding_open_errcode);
		
//		JSONObject updateDeviceName = updateDeviceName("2c3a5d46875c3e69af32853ff2bfbc84", 15L, "788512015", "移动应用事业部M2");
//		System.out.println(updateDeviceName);
		
	}

	
	
	
}
