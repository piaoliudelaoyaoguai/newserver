package com.centit.server.dingtalk.manager.servlet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.centit.server.dingtalk.demo.utils.aes.DingTalkEncryptException;
import com.centit.server.dingtalk.demo.utils.aes.DingTalkEncryptor;
import com.centit.server.dingtalk.manager.po.Agent;
import com.centit.server.dingtalk.manager.po.CorpInfo;
import com.centit.server.dingtalk.manager.po.DingTalkSuite;
import com.centit.server.dingtalk.manager.uitls.CommonInit;
import com.centit.server.dingtalk.manager.uitls.DataEnv;
import com.centit.server.dingtalk.manager.uitls.DingTalkUtil;
import com.centit.server.dingtalk.manager.uitls.PropertyUtil;

/**
 * <p>此servlet用来接收钉钉服务器回调接口的推送<p>
 * @version 1.0
 * @author li_hao
 * @date 2017年6月20日
 */
@WebServlet("/isvreceive")
public class IsvServlet extends HttpServlet {
	private static final long serialVersionUID = -4721184700710135123L;
	

	public IsvServlet() {
		super();
	}
	
	
	/**
	 * 创建加密/解密 类
	 * @return
	 */
	public DingTalkEncryptor createDingTalkEncryptor(String suitetbId){
		DingTalkEncryptor dingTalkEncryptor = null;  //加密方法类
		try {
			DingTalkSuite suite = new DingTalkSuite();
			suite.setId(suitetbId);
			suite = CommonInit.dingTalkSuiteDao.queryOne(suite); //查询钉钉套件suite信息
			String suite_key = suite.getSuite_key(); //套件注册成功后生成的suite_key
			
			if(!"".equals(suite_key) && suite_key!=null){ //当suite_key存在时，即套件创建成功
				dingTalkEncryptor = new DingTalkEncryptor(suite.getToken(), suite.getEncoding_aes_key(),suite_key);  //创建加解密类
			}else{  //当suite_key不存在时，第一次创建套件
				dingTalkEncryptor = new DingTalkEncryptor(suite.getToken(), suite.getEncoding_aes_key(),PropertyUtil.readProperty(DataEnv.DINGTALK_PROPERTIES, "create_suite_key"));  //创建加解密类
			}
		} catch (DingTalkEncryptException e) {
			e.printStackTrace();
		}
		return dingTalkEncryptor;
	}
	
	/**
	 * encrypt解密
	 * @param suitetbId 套件表id
	 * @param msgSignature
	 * @param timeStamp
	 * @param nonce
	 * @param encrypt  密文
	 * @return decodeEncrypt 解密后的明文
	 */
	public String decodeEncrypt(String suitetbId,String msgSignature,String timeStamp,String nonce,String encrypt){
			String decodeEncrypt = null;
			try {
				decodeEncrypt = createDingTalkEncryptor(suitetbId).getDecryptMsg(msgSignature, timeStamp, nonce, encrypt); //encrypt解密
			} catch (DingTalkEncryptException e) {
				e.printStackTrace();
			}
		return decodeEncrypt;
	}
	
	
	/**
	 *  对返回信息进行加密
	 * @param res
	 * @param timeStamp
	 * @param nonce
	 * @return
	 */
	public JSONObject codeEncrypt(String suitetbId,String res,String timeStamp,String nonce){
		long timeStampLong = Long.parseLong(timeStamp);
		Map<String, String> jsonMap = null;
		try {
			jsonMap = createDingTalkEncryptor(suitetbId).getEncryptedMap(res, timeStampLong, nonce); //jsonMap是需要返回给钉钉服务器的加密数据包
		} catch (DingTalkEncryptException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		JSONObject json = new JSONObject();
		json.putAll(jsonMap);
		return json;
	}
	
	
	/**
	 * 创建套件
	 * check_create_suite_url"事件将在创建套件的时候推送
	 * @param decodeEncrypt_json 解密后的encrypt数据
	 * @return
	 * {
		"EventType":"check_create_suite_url",
		"Random":"brdkKLMW",
		"TestSuiteKey":"suite4xxxxxxxxxxxxxxx"
	   }
	 */
	public String createSuiteCheck(JSONObject decodeEncrypt_json){
		//此事件需要返回的"Random"字段，
		String res = decodeEncrypt_json.getString("Random");
		String testSuiteKey = decodeEncrypt_json.getString("TestSuiteKey");
		return res;
	}
	
	/**
	 * 更新套件
	 * check_update_suite_url"事件将在更新套件的时候推送
	 * @param decodeEncrypt_json 解密后的encrypt数据
	 * @return
	 * {
		"EventType":"check_update_suite_url",
		"Random":"Aedr5LMW",
		"TestSuiteKey":"suited6db0pze8yao1b1y"
	   }
	 */
	public String updateSuiteCheck(JSONObject decodeEncrypt_json){
		//此事件需要返回的"Random"字段，
		String res = decodeEncrypt_json.getString("Random");
		return res;
	}
	
	
	/**
	 * 钉钉后台推送套件ticket处理
	 * suite_ticket"事件每二十分钟推送一次,数据格式如下
	 * @param suitetbId  //套件表主键id
	 * @param decodeEncrypt_json
	 * {
		"SuiteKey": "suitexxxxxx",
		"EventType": "suite_ticket ",
		"TimeStamp": 1234456,
		"SuiteTicket": "adsadsad"
	   }
	 */
	public void suiteTicket(JSONObject decodeEncrypt_json, String suitetbId){
		try {
			DingTalkSuite suite = new DingTalkSuite();
			suite.setId(suitetbId);
			suite = CommonInit.dingTalkSuiteDao.queryOne(suite); //查询钉钉套件suite信息
			
			String suiteTicket = decodeEncrypt_json.getString("SuiteTicket");
			JSONObject suiteToken = DingTalkUtil.getSuiteAccessToken(suite.getSuite_key(), suite.getSuite_secret(), suiteTicket);  //通过suiteTicket获取之后需要换取suiteToken
			
			DingTalkSuite dingTalkSuite = new DingTalkSuite();
			dingTalkSuite.setId(suitetbId);
			dingTalkSuite.setSuit_ticket(suiteTicket);
			dingTalkSuite.setSuite_access_token(suiteToken.getString("suite_access_token")); //有效期7200秒，每20分钟（1200秒）推送一次就会更新一次（更新时存储数据库），所以不需要额外定时获取
			CommonInit.dingTalkSuiteDao.update(dingTalkSuite);  //保存数据库
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * "tmp_auth_code"事件将企业对套件发起授权的时候推送(企业通过临时授权码tmp_auth_code授权)
	 * @param suitetbId  //套件表主键id
	 * @param decodeEncrypt_json
	 * {
		"SuiteKey": "suitexxxxxx",
		"EventType": " tmp_auth_code",
		"TimeStamp": 1234456,
		"AuthCode": "adads"
		}
	 */
	public void tempAuthCode(JSONObject decodeEncrypt_json, String suitetbId){
		String authCode = decodeEncrypt_json.getString("AuthCode");  //临时授权码
		
		DingTalkSuite suite = new DingTalkSuite();
		suite.setId(suitetbId);
		suite = CommonInit.dingTalkSuiteDao.queryOne(suite);
		String suiteAccessToken = suite.getSuite_access_token();  //查询套件suiteAccessToken
		if(!"".equals(suiteAccessToken) && suiteAccessToken!=null){
			try {
				JSONObject permanentCode = DingTalkUtil.getPermanentCode(suiteAccessToken, authCode);  //用临时授权码获取永久授权（包括永久授权码和企业corpid等信息）
				JSONObject json_auth_corp_info = permanentCode.getJSONObject("auth_corp_info");  //这里只有企业的corpid和corpname
				String corpid = json_auth_corp_info.getString("corpid");
				String corpname = json_auth_corp_info.getString("corp_name");
				String  permanent_code = permanentCode.getString("permanent_code");
				
				//数据库持久化存储corpid和permanent_code,之后在获取企业的access_token时需要使用
				CorpInfo corpInfo = new CorpInfo();
				corpInfo.setCorpid(corpid);
				corpInfo.setCorpname(corpname);
				corpInfo.setPermanent_code(permanent_code);
				corpInfo.setSuiteid(suite.getSuiteid());
				
				//获取corpAccessToken和jsapi_ticket
				//至于7200的时效性，在项目启动时，定时器会扫描T_DT_CORPINFO表中的所有corpid下的access_token和jsapi_ticket，定时更新
				//（因为此时的操作是在项目启动后执行的，T_DT_CORPINFO的中启动时获取有效期的剩余时间会小于此时获取的7200秒），所以在此时获取的数据失效前就会被定时器更新，更新后有效期达到一致
				JSONObject corpTokenJson = DingTalkUtil.getCorpToken(suiteAccessToken, corpid, permanent_code);
				String corp_accesstoken = corpTokenJson.getString("access_token");  //授权方（企业）access_token,每一个企业都会有一个对应的access_token，访问对应企业的数据都将需要带上这个access_token
				JSONObject jsapiTicketJson = DingTalkUtil.getJsapiTicket(corp_accesstoken);  //获取授权企业的jsapi_ticket
				String jsapi_ticket = jsapiTicketJson.getString("ticket");
				corpInfo.setAccess_token(corp_accesstoken);
				corpInfo.setJsapi_ticket(jsapi_ticket);
				
				//获取授权企业信息、授权应用信息
				String suite_key = suite.getSuite_key();  //查询套件suite_key
				JSONObject jsonAuthInfo = DingTalkUtil.getAuthInfo(suiteAccessToken, corpid, suite_key); //获取企业授权的授权数据
				JSONObject json_corp_info = jsonAuthInfo.getJSONObject("auth_corp_info");  //获取企业基本信息
				JSONArray jsonArray_agent = jsonAuthInfo.getJSONObject("auth_info").getJSONArray("agent"); //授权的应用信息列表
				JSONArray jsonArray_channelAgent = jsonAuthInfo.getJSONObject("channel_auth_info").getJSONArray("channelAgent"); //授权的服务窗应用信息列表
				
				corpInfo.setCorplogourl(json_corp_info.getString("corp_logo_url"));
				corpInfo.setIndustry(json_corp_info.getString("industry"));
				corpInfo.setInvitecode(json_corp_info.getString("invite_code"));
				corpInfo.setLicensecode(json_corp_info.getString("license_code"));
				corpInfo.setAuthchannel(json_corp_info.getString("auth_channel"));
				corpInfo.setAuthchanneltype(json_corp_info.getString("auth_channel_type"));
				corpInfo.setIsauthenticated(json_corp_info.getString("is_authenticated"));
				corpInfo.setAuthlevel(json_corp_info.getString("auth_level"));
				corpInfo.setInviteurl(json_corp_info.getString("invite_url"));
				corpInfo.setAuthuserinfo(jsonAuthInfo.getJSONObject("auth_user_info").getString("userId"));
				
				//有则更新无则插入
				CommonInit.corpInfoDao.replaceCorpInfo(corpInfo);
				
				//遍历授权的应用信息列表
				if(jsonArray_agent!=null){
					for(int i=0;i<jsonArray_agent.size();i++){
						JSONObject job = jsonArray_agent.getJSONObject(i); // 遍历 jsonarray 数组，把每一个对象转成 json 对象
						
						Agent agent = new Agent();
						agent.setCorpid(corpid);
						agent.setAgentid(job.getString("agentid"));
						agent.setAgentname(job.getString("agent_name"));
						agent.setAppid(job.getString("appid"));
						agent.setLogourl(job.getString("logo_url"));
						agent.setAdminlist(job.getJSONArray("admin_list").toJSONString());
						agent.setAgenttype("0");
						CommonInit.agentDao.replaceAgent(agent);
					}
				}
				
				//遍历授权的服务窗应用信息列表
				if(jsonArray_channelAgent!=null){
					for(int j=0;j<jsonArray_channelAgent.size();j++){
						JSONObject job = jsonArray_channelAgent.getJSONObject(j); // 遍历 jsonarray 数组，把每一个对象转成 json 对象
						
						Agent agent = new Agent();
						agent.setCorpid(corpid);
						agent.setAgentid(job.getString("agentid"));
						agent.setAgentname(job.getString("agent_name"));
						agent.setAppid(job.getString("appid"));
						agent.setLogourl(job.getString("logo_url"));
						agent.setAgenttype("1");
						CommonInit.agentDao.replaceAgent(agent);
					}
				}

				DingTalkUtil.activateSuite(suiteAccessToken, suite.getSuite_key(), corpid, permanent_code); //对企业授权的套件发起激活
			} catch (Exception e) {
				System.out.println("临时授权码已经使用过！已失效！");
			}
		}
	}
	
	/**
	 * change_auth"事件将在企业授权变更消息发生时推送 处理
	 * 
	 * @param suitetbId  //套件表主键id
	 * @param decodeEncrypt_json
	 * {
		"SuiteKey": "suitexxxxxx",
		"EventType": " change_auth",
		"TimeStamp": 1234456,
		"AuthCorpId": "xxxxx"
	   }
	 */
	public void changeAuth(JSONObject decodeEncrypt_json, String suitetbId){
		// 由于以下操作需要从持久化存储中获得数据，而本demo并没有做持久化存储（因为部署环境千差万别），所以没有具体代码，只有操作指导。
		// 1.根据corpid查询对应的permanent_code(永久授权码)
		// 2.调用『企业授权的授权数据』接口（ServiceHelper.getAuthInfo方法），此接口返回数据具体详情请查看文档。
		// 3.遍历从『企业授权的授权数据』接口中获取所有的微应用信息
		// 4.对每一个微应用都调用『获取企业的应用信息接口』（ServiceHelper.getAgent方法）
		/* 
		 * 5.获取『获取企业的应用信息接口』接口返回值其中的"close"参数，才能得知微应用在企业用户做了授权变更之后的状态，有三种状态码
		 * 	分别为0，1，2.含义如下：
		 *  0:禁用（例如企业用户在OA后台禁用了微应用） 
		 *  1:正常 (例如企业用户在禁用之后又启用了微应用)
		 *  2:待激活 (企业已经进行了授权，但是ISV还未为企业激活应用) 
		 *  再根据具体状态做具体操作。 
		 */
		
		DingTalkSuite suite = new DingTalkSuite();
		suite.setId(suitetbId);
		suite = CommonInit.dingTalkSuiteDao.queryOne(suite);
		
		//1.从T_DT_CORPINFO表中获取永久授权码
		String corpid = decodeEncrypt_json.getString("AuthCorpId");
		CorpInfo corpInfo = new CorpInfo();
		corpInfo.setCorpid(corpid);
		corpInfo.setSuiteid(suite.getSuiteid());
		corpInfo = CommonInit.corpInfoDao.queryOne(corpInfo);
		String permanent_code = corpInfo.getPermanent_code(); //查询永久授权码
		
		//2.获取企业授权的授权数据
		JSONObject jsonAuthInfo = new JSONObject();
		String suiteAccessToken = suite.getSuite_access_token();  //查询套件suiteAccessToken
		String suite_key = suite.getSuite_key();  //查询套件suite_key
		if(!"".equals(suiteAccessToken) && suiteAccessToken!=null){
			jsonAuthInfo = DingTalkUtil.getAuthInfo(suiteAccessToken, corpid, suite_key); //获取企业授权的授权数据
		}
		
		// 3.遍历从『企业授权的授权数据』接口中获取所有的微应用信息
		JSONObject json_auth_info = jsonAuthInfo.getJSONObject("auth_info");
//		JSONArray jsonArray_agent = json_auth_info.getJSONObject("auth_info").getJSONArray("agent"); //授权的应用信息列表
//		JSONArray jsonArray_channel_auth_info = json_auth_info.getJSONObject("channel_auth_info").getJSONArray("channelAgent"); //授权的服务窗应用信息列表
		JSONArray jsonArray_agent = json_auth_info.getJSONArray("agent"); //授权的应用信息列表
		
		//遍历授权的应用信息列表
		if(jsonArray_agent!=null){
			for(int i=0;i<jsonArray_agent.size();i++){
				JSONObject job = jsonArray_agent.getJSONObject(i); // 遍历 jsonarray 数组，把每一个对象转成 json 对象
				String agentid = job.getString("agentid");  	//授权方应用id
				
				//4.对每一个微应用都调用『获取企业的应用信息接口』
				JSONObject jsonAgent = DingTalkUtil.getAgent(suiteAccessToken, suite_key, corpid, permanent_code, agentid);  //获取企业的应用信息
				String close = jsonAgent.getString("jsonAgent");  //获取close参数
				
				//5.获取『获取企业的应用信息接口』接口返回值其中的"close"参数，才能得知微应用在企业用户做了授权变更之后的状态，有三种状态码；根据具体状态做具体操作。 
				if("0".equals(close)){  //0:禁用（例如企业用户在OA后台禁用了微应用）
					
				}else if("1".equals(close)){  //1:正常 (例如企业用户在禁用之后又启用了微应用)
					
				}else if("2".equals(close)){  //2:待激活 (企业已经进行了授权，但是ISV还未为企业激活应用) 
					
				}
			}
		}
		
//		//遍历授权的服务窗应用信息列表
//		if(jsonArray_channel_auth_info.size()>0 && jsonArray_channel_auth_info!=null){
//			for(int i=0;i<jsonArray_channel_auth_info.size();i++){
//				JSONObject job = jsonArray_channel_auth_info.getJSONObject(i); // 遍历 jsonarray 数组，把每一个对象转成 json 对象
//				String agentid = job.getString("agentid");  	//授权方应用id
//				
//				//4.对每一个微应用都调用『获取企业的应用信息接口』
//				JSONObject jsonAgent = DingTalkUtil.getAgent(suiteAccessToken, suite_key, corpid, permanent_code, agentid);  //获取企业的应用信息
//				String close = jsonAgent.getString("jsonAgent");  //获取close参数
//				
//				//5.获取『获取企业的应用信息接口』接口返回值其中的"close"参数，才能得知微应用在企业用户做了授权变更之后的状态，有三种状态码；根据具体状态做具体操作。 
//				if("0".equals(close)){  //0:禁用（例如企业用户在OA后台禁用了微应用）
//					
//				}else if("1".equals(close)){  //1:正常 (例如企业用户在禁用之后又启用了微应用)
//					
//				}else if("2".equals(close)){  //2:待激活 (企业已经进行了授权，但是ISV还未为企业激活应用) 
//					
//				}
//			}
//		}
		
		
	}
	
//	/**
//	 * 获取agentid
//	 * @param corpid
//	 * @return
//	 */
//	public String getAgentid(String corpid){
//		String agentid = null;
//		JSONObject jsonAuthInfo = new JSONObject();
//		DingTalkSuite suite = CommonInit.dingTalkSuiteDao.queryOne(null);
//		String suiteAccessToken = suite.getSuite_access_token();  //查询套件suiteAccessToken
//		String suite_key = suite.getSuite_key();  //查询套件suite_key
//		if(!"".equals(suiteAccessToken) && suiteAccessToken!=null){
//			jsonAuthInfo = DingTalkUtil.getAuthInfo(suiteAccessToken, corpid, suite_key); //获取企业授权的授权数据
//		}
//		// 3.遍历从『企业授权的授权数据』接口中获取所有的微应用信息
//		JSONObject json_auth_info = jsonAuthInfo.getJSONObject("auth_info");
//		JSONArray jsonArray_agent = json_auth_info.getJSONObject("auth_info").getJSONArray("agent"); //授权的应用信息列表
//		//遍历授权的应用信息列表
//		if(jsonArray_agent!=null){
//			for(int i=0;i<jsonArray_agent.size();i++){
//				JSONObject job = jsonArray_agent.getJSONObject(i); // 遍历 jsonarray 数组，把每一个对象转成 json 对象
//				agentid = job.getString("agentid");  	//授权方应用id
//			}
//		}
//		return agentid;
//	}
	
	
	/**
	 * 企业主动解除授权，或者ISV主动解除企业授权（删除数据库中t_dt_corpinfo中的企业信息，否则定时任务获取企业corp_accesstoken获取不到，会一直60秒就重新获取）
	 * @param decodeEncrypt_json
	 */
	public void suiteRElieve(JSONObject decodeEncrypt_json,String suitetbId){
		DingTalkSuite suite = new DingTalkSuite();
		suite.setId(suitetbId);
		suite = CommonInit.dingTalkSuiteDao.queryOne(suite);
		
		String corpid = decodeEncrypt_json.getString("AuthCorpId");
		CorpInfo corpInfo = new CorpInfo();
		corpInfo.setCorpid(corpid);
		corpInfo.setSuiteid(suite.getSuiteid());
		CommonInit.corpInfoDao.deleteCorpInfo(corpInfo);
		
		Agent agent = new Agent();
		agent.setCorpid(corpid);
		CommonInit.agentDao.deleteAgent(agent);
	}
	
	/**
	 * 企业物理删除微应用事件
	 * @param decodeEncrypt_json
	 */
	public void orgMicroAppStop(JSONObject decodeEncrypt_json){
		Agent agent = new Agent();
		agent.setCorpid(decodeEncrypt_json.getString("AuthCorpId"));
		agent.setAgentid(decodeEncrypt_json.getString("AgentId"));
		agent.setEnable("0");
		CommonInit.agentDao.updateAgent(agent);
	}

	/**
	 * 企业逻辑停用微应用事件
	 * @param decodeEncrypt_json
	 */
	public void orgMicroAppRemove(JSONObject decodeEncrypt_json){
		Agent agent = new Agent();
		agent.setCorpid(decodeEncrypt_json.getString("AuthCorpId"));
		agent.setAgentid(decodeEncrypt_json.getString("AgentId"));
		CommonInit.agentDao.deleteAgent(agent);
	}
	
	/**
	 * 企业逻辑启用微应用事件
	 * @param decodeEncrypt_json
	 */
	public void orgMicroAppRestore(JSONObject decodeEncrypt_json){
		Agent agent = new Agent();
		agent.setCorpid(decodeEncrypt_json.getString("AuthCorpId"));
		agent.setAgentid(decodeEncrypt_json.getString("AgentId"));
		agent.setEnable("1");
		CommonInit.agentDao.updateAgent(agent);
	}
	
	
	
	
	/*
	 * 接收钉钉服务器的回调数据
	 * 
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response){
		try {
			String suitetbId = request.getParameter("suitetbId");  //套件表主键id
			
			/** url中的签名 **/
			String msgSignature = request.getParameter("signature");
			/** url中的时间戳 **/
			String timeStamp = request.getParameter("timestamp");
			/** url中的随机字符串 **/
			String nonce = request.getParameter("nonce");
			/** 取得JSON对象中的encrypt字段	 **/
			String encrypt = "";
			
			/** 获取post数据包数据中的加密数据 **/
			ServletInputStream sis = request.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(sis));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			JSONObject jsonEncrypt = JSONObject.parseObject(sb.toString());
			encrypt = jsonEncrypt.getString("encrypt");
			
			String decodeEncrypt = decodeEncrypt(suitetbId,msgSignature, timeStamp, nonce, encrypt); //密文解密
			JSONObject decodeEncrypt_json = JSONObject.parseObject(decodeEncrypt);
			
			String eventType = decodeEncrypt_json.getString("EventType");  //回调类型
			String res = "success";  //res是需要返回给钉钉服务器的字符串，一般为success;"check_create_suite_url"和"check_update_suite_url"事件为random字段;(具体请查看文档或者对应eventType的处理步骤)
		
			//根据不同的回调类型，进行相应的操作
			switch (eventType) {
			case "suite_ticket":
				suiteTicket(decodeEncrypt_json, suitetbId);  //钉钉后台推送套件ticket处理
				break;
			case "tmp_auth_code":
				tempAuthCode(decodeEncrypt_json, suitetbId);  //tmp_auth_code"事件将企业对套件发起授权的时候推送处理
				break;
			case "change_auth":
				changeAuth(decodeEncrypt_json, suitetbId);  //change_auth"事件将在企业授权变更消息发生时推送处理
				break;
			case "check_create_suite_url":
				res = createSuiteCheck(decodeEncrypt_json);  //创建套件
				break;
			case "check_update_suite_url":
				res = updateSuiteCheck(decodeEncrypt_json);  //更新套件
				break;
			case "suite_relieve":
				//企业主动解除授权，或者ISV主动解除企业授权（删除数据库中t_dt_corpinfo中的企业信息，否则定时任务获取企业corp_accesstoken获取不到，会一直60秒就重新获取）
				suiteRElieve(decodeEncrypt_json,suitetbId);
				break;
			case "org_micro_app_remove":
				//企业物理删除微应用事件
				orgMicroAppRemove(decodeEncrypt_json);
				break;
			case "org_micro_app_stop":
				  //企业逻辑停用微应用事件(企业主动停用应用)
				orgMicroAppStop(decodeEncrypt_json);
				break;
			case "org_micro_app_restore":
				//企业逻辑启用微应用事件
				orgMicroAppRestore(decodeEncrypt_json);
				break;
			case "face_recognize":
				//人脸识别回调事件
				System.out.println(decodeEncrypt_json);
				break;
			default: // do something
				break;
			}
			response.getWriter().append(codeEncrypt(suitetbId, res, timeStamp, nonce).toString()); //返回加密后的数据
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		doGet(request, response);
	}
}
