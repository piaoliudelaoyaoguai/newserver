package com.centit.server.dingtalk.manager.servlet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.alibaba.fastjson.JSONObject;
import com.centit.server.dingtalk.demo.utils.aes.DingTalkEncryptException;
import com.centit.server.dingtalk.demo.utils.aes.DingTalkEncryptor;
import com.centit.server.dingtalk.manager.common.DingTalkProperties;
import com.centit.server.dingtalk.manager.po.CallBackRegister;
import com.centit.server.dingtalk.manager.po.DingTalkSuite;
import com.centit.server.dingtalk.manager.po.ServCorpInfo;
import com.centit.server.dingtalk.manager.sync.AddressListSync;
import com.centit.server.dingtalk.manager.uitls.CommonInit;
import com.centit.server.dingtalk.manager.uitls.RegisterUtil;

/**
 * <p>此servlet用来接收钉钉服务器回调接口的推送<p>
 * @version 1.0
 * @author li_hao
 * @date 2017年6月20日
 */
@WebServlet("/callbackreceive")
public class CallBackServlet extends HttpServlet {
	
	private static final long serialVersionUID = -8084665633920416157L;

	public CallBackServlet() {
		super();
	}
	
	
	/**
	 * 创建加密/解密 类
	 * @return
	 */
	public DingTalkEncryptor createDingTalkEncryptor(String suitetbId){
		DingTalkEncryptor dingTalkEncryptor = null;  //加密方法类
		try {
			if("incorp".equals(DingTalkProperties.developtype)){
				ServCorpInfo servCorpInfo = CommonInit.servCorpInfoDao.queryOne(null); //查询钉钉信息
				dingTalkEncryptor = new DingTalkEncryptor(RegisterUtil.token, RegisterUtil.aesKey,servCorpInfo.getCorpid());  //创建加解密类
				
			}else if("isv".equals(DingTalkProperties.developtype)){
				DingTalkSuite suite = new DingTalkSuite();
				suite.setId(suitetbId);
				suite = CommonInit.dingTalkSuiteDao.queryOne(suite); //查询钉钉套件suite信息
				String suite_key = suite.getSuite_key(); //套件注册成功后生成的suite_key
				dingTalkEncryptor = new DingTalkEncryptor(suite.getToken(), suite.getEncoding_aes_key(),suite_key);  //创建加解密类
			}
		} catch (DingTalkEncryptException e) {
			e.printStackTrace();
		}
		return dingTalkEncryptor;
	}
	
	/**
	 * encrypt解密
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
//			JSONObject jsonEncrypt = JSONObject.parseObject(sb.toString());
//			encrypt = jsonEncrypt.getString("encrypt");
//			
//			String decodeEncrypt = decodeEncrypt(suitetbId, msgSignature, timeStamp, nonce, encrypt); //密文解密
//			JSONObject decodeEncryptJson = JSONObject.parseObject(decodeEncrypt);
			
			//判断是否从政务样板间转发过来
			JSONObject decodeEncryptJson = new JSONObject();
			if("off".equals(DingTalkProperties.register_call_back_url_switch)){
				//政务样板间转：发过来的数据是解密后的数据
				decodeEncryptJson = JSONObject.parseObject(sb.toString());
				System.out.println("接收转发的数据：===========================" + decodeEncryptJson);
			}else{
				//钉钉原始回调
				JSONObject jsonEncrypt = JSONObject.parseObject(sb.toString());
				encrypt = jsonEncrypt.getString("encrypt");
				
				String decodeEncrypt = decodeEncrypt(suitetbId, msgSignature, timeStamp, nonce, encrypt); //密文解密
				decodeEncryptJson = JSONObject.parseObject(decodeEncrypt);
			}
			
			String eventType = decodeEncryptJson.getString("EventType");  //回调类型
			
			String CorpId = "";  //发生通讯录变更的企业
			String UserIds = "";  //用户发生变更的userid列表
			String DeptIds = "";  //部门发生变更的deptId列表
			String res = "success";  //res是需要返回给钉钉服务器的字符串，一般为success;"check_create_suite_url"和"check_update_suite_url"事件为random字段;(具体请查看文档或者对应eventType的处理步骤)
			
			JSONObject jsonObjectData = new JSONObject();
			//根据不同的回调类型，进行相应的操作
			switch (eventType) {
			case CallBackRegister.USER_ADD_ORG :
				//通讯录用户增加
				CorpId = decodeEncryptJson.getString("CorpId");
				UserIds = decodeEncryptJson.getString("UserId");
				
				jsonObjectData.put("CorpId", CorpId);
				jsonObjectData.put("UserIds", UserIds);
//				CommonInit.addressListSync.createUser(jsonObjectData);
				new AddressListSync().createUser(jsonObjectData);
				break;
			case CallBackRegister.USER_MODIFY_ORG :
				//通讯录用户更改
				CorpId = decodeEncryptJson.getString("CorpId");
				UserIds = decodeEncryptJson.getString("UserId");
				
				jsonObjectData.put("CorpId", CorpId);
				jsonObjectData.put("UserIds", UserIds);
//				CommonInit.addressListSync.updateUsers(jsonObjectData);
				new AddressListSync().updateUsers(jsonObjectData);
				break;
			case CallBackRegister.USER_LEAVE_ORG :
				//通讯录用户离职deleteUsers
				CorpId = decodeEncryptJson.getString("CorpId");
				UserIds = decodeEncryptJson.getString("UserId");
				
				jsonObjectData.put("CorpId", CorpId);
				jsonObjectData.put("UserIds", UserIds);
//				CommonInit.addressListSync.deleteUsers(jsonObjectData);
				new AddressListSync().deleteUsers(jsonObjectData);
				break;
			case CallBackRegister.ORG_ADMIN_ADD :
				//通讯录用户被设为管理员
				break;
			case CallBackRegister.ORG_ADMIN_REMOVE :
				//通讯录用户被取消设置管理员
				break;
			case CallBackRegister.ORG_DEPT_CREATE :
				//通讯录企业部门创建
				CorpId = decodeEncryptJson.getString("CorpId");
				DeptIds = decodeEncryptJson.getString("DeptId");
				
				jsonObjectData.put("CorpId", CorpId);
				jsonObjectData.put("DeptIds", DeptIds);
//				CommonInit.addressListSync.deptCreate(jsonObjectData);
				new AddressListSync().deptCreate(jsonObjectData);
				break;
			case CallBackRegister.ORG_DEPT_MODIFY :
				//通讯录企业部门修改
				CorpId = decodeEncryptJson.getString("CorpId");
				DeptIds = decodeEncryptJson.getString("DeptId");
				
				jsonObjectData.put("CorpId", CorpId);
				jsonObjectData.put("DeptIds", DeptIds);
//				CommonInit.addressListSync.deptUpdate(jsonObjectData);
				new AddressListSync().deptUpdate(jsonObjectData);
				break;
			case CallBackRegister.ORG_DEPT_REMOVE :
				//通讯录企业部门删除
				CorpId = decodeEncryptJson.getString("CorpId");
				DeptIds = decodeEncryptJson.getString("DeptId");
				
				jsonObjectData.put("CorpId", CorpId);
				jsonObjectData.put("DeptIds", DeptIds);
//				CommonInit.addressListSync.deptDelete(jsonObjectData);
				new AddressListSync().deptDelete(jsonObjectData);
				break;
			case CallBackRegister.ORG_REMOVE :
				//企业被解散
				break;
			case CallBackRegister.ORG_CHANGE :
				//企业信息发生变更
				break;
			case CallBackRegister.LABEL_USER_CHANGE :
				//员工角色信息发生变更
				break;
			case CallBackRegister.LABEL_CONF_ADD :
				//增加角色或者角色组
				break;
			case CallBackRegister.LABEL_CONF_DEL :
				//删除角色或者角色组
				break;
			case CallBackRegister.LABEL_CONF_MODIFY :
				//修改角色或者角色组
				break;
			case CallBackRegister.FACE_RECOGNIZE :
				//M2回调接口事件类型
				System.out.println("进入M2回调事件类型处理：===========================" + decodeEncryptJson);
				break;
			case CallBackRegister.CHECK_URL :
				//测试回调接口事件类型
				System.out.println("测试回调接口！");
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
