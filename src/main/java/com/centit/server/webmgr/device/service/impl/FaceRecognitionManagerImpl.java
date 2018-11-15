package com.centit.server.webmgr.device.service.impl;/**
 * Created by li_hao on 2017/6/10.
 */

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.fastjson.JSONObject;
import com.centit.server.dingtalk.manager.common.DingTalkProperties;
import com.centit.server.dingtalk.manager.dao.CorpInfoDao;
import com.centit.server.dingtalk.manager.dao.ServCorpInfoDao;
import com.centit.server.dingtalk.manager.dao.UserSyncDao;
import com.centit.server.dingtalk.manager.po.UserSync;
import com.centit.server.dingtalk.manager.uitls.CommonUtil;
import com.centit.server.dingtalk.manager.uitls.DingTalkUtil;
import com.centit.server.webmgr.device.dao.AssetDao;
import com.centit.server.webmgr.device.dao.UserFaceDao;
import com.centit.server.webmgr.device.po.Asset;
import com.centit.server.webmgr.device.po.UserFace;
import com.centit.server.webmgr.device.service.FaceRecognitionManager;
import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicMatch;


/**
 * <p>人脸识别<p>
 * @version 1.0
 * @author li_hao
 * @date 2018年2月28日
 */
@SuppressWarnings("static-access")
@Service
@Transactional
public class FaceRecognitionManagerImpl implements FaceRecognitionManager {
	
	public static final Log log = LogFactory.getLog(FaceRecognitionManager.class);
	
	@Resource
	private ServCorpInfoDao servCorpInfoDao;
	@Resource
	private CorpInfoDao corpInfoDao;
	@Resource
	private UserFaceDao userFaceDao;
	@Resource
	private UserSyncDao userSyncDao;
	@Resource
	private AssetDao assetDao;


	/**
     * 上传人脸照片
     * @param request
     * @param response
     * @param reqJson
     */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject uploadFaceRecPic(JSONObject reqJson) {
		JSONObject rsp_json = new JSONObject();
		String retCode = "1";
		String retMsg = "操作失败！";
		JSONObject bizDataJson = new JSONObject();
		try {
			String corpid = reqJson.getString("corpid");
			if(!"".equals(corpid) && corpid!=null){
				String corp_accesstoken = CommonUtil.getAccessToken(corpid);
				
				String userid = reqJson.getString("userid");
				String picurl = reqJson.getString("picurl");
				
				String fileName = picurl.substring(picurl.lastIndexOf("/")+1);
				fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + fileName;
				
				String storePath = "/faceRec/upload/" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) +"/" +fileName;
				String realFile = DingTalkProperties.upload_filedir + storePath;
				//文件存储目录
				String dir = DingTalkProperties.upload_filedir + "/faceRec/upload/" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) +"/";
				
				//下载网络url文件
				boolean flag = DingTalkUtil.downloadHttpUrl(picurl, dir, fileName);
				if(flag){
					File file = new File(realFile);
					//获取文件大小
					long fileSize = file.length();
					//获取文件类型
					Magic parser = new Magic();    
					MagicMatch match = parser.getMagicMatch(file, false);    
			        String fileType = match.getMimeType();
			        
					//上传媒体文件获取mediaID
					JSONObject jsonObject = DingTalkUtil.mediaUpload(corp_accesstoken, "image", realFile);
					String media_id = jsonObject.getString("media_id");
					bizDataJson.put("mediaID", media_id);
					
					//向企业员工添加人脸识别照片
					String usertype = "inspection";
					Long starttime = 1521715680000L;  //2018-03-22 18:48:00
					Long endtime = 4101595200000L;  //2099-12-22 12:00:00
					JSONObject m2DeviceAddface = DingTalkUtil.m2DeviceAddface(corp_accesstoken, media_id, userid, usertype, starttime, endtime);
					JSONObject respJson = m2DeviceAddface.getJSONObject("dingtalk_corp_smartdevice_addface_response");
					if(respJson != null){
						String respCode = respJson.getJSONObject("result").getString("ding_open_errcode");
						//若上传成功，则入库
						if("0".equals(respCode)){
							Asset asset = new Asset();
							asset.setCorpid(corpid);
							asset.setFilename(fileName);
							asset.setFilesize(String.valueOf(fileSize));
							asset.setFileformat(fileType);
							asset.setStorepath(storePath);
							asset.setCreateuserid(userid);
							assetDao.insertAsset(asset);
							
							//插入人员-人脸照片关联表
							UserFace userFace = new UserFace();
							userFace.setCorpid(corpid);
							userFace.setUserid(userid);
							userFace.setFaceassetid(asset.getId());
							userFaceDao.replaceUserFace(userFace);
							
							retCode = "0";
							retMsg = "操作成功！";
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		rsp_json.put("retCode", retCode);
		rsp_json.put("retMsg", retMsg);
		rsp_json.put("bizData", bizDataJson);
		return rsp_json;
	}
	
	/**
	 * 查询人脸录入情况列表
	 */
	@Override
	public JSONObject queryM2FaceUserSetList(JSONObject reqJson) {
		JSONObject rsp_json = new JSONObject();
 		String retCode = "1";
		String retMsg = "操作失败！";
		JSONObject bizDataJson = new JSONObject();
		try {
			String corpid = reqJson.getString("corpid");
			if(!"".equals(corpid) && corpid!=null){
				
				String deptid = reqJson.getString("deptid");
				String username = reqJson.getString("username");
				String pageNum = reqJson.getString("pageNum");
				String pageSize = reqJson.getString("pageSize");
				
				if(Integer.valueOf(pageSize) <= 20){
					HashMap<String, Object> reqMap = new HashMap<String, Object>();
					reqMap.put("corpid", corpid);
					reqMap.put("deptid", deptid);
					reqMap.put("username", username);
					reqMap.put("pageNum", (Integer.valueOf(pageNum)-1)*Integer.valueOf(pageSize));
					reqMap.put("pageSize", Integer.valueOf(pageSize));
					
					List<UserSync> userList = userSyncDao.queryListPage(reqMap);
					String total = userSyncDao.queryPageCounts(reqMap);
					
					userList.stream().forEach(u -> u.setM2faceurl(findFacePicUrl(corpid, u.getUserid())));
					
					bizDataJson.put("userList", userList);
					bizDataJson.put("total", total);
					retCode = "0";
					retMsg = "操作成功！";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		rsp_json.put("retCode", retCode);
		rsp_json.put("retMsg", retMsg);
		rsp_json.put("bizData", bizDataJson);
		return rsp_json;
	}

	/**
	 * 查询人脸录入情况详情
	 */
	@Override
	public JSONObject queryM2FaceUserSetDetail(JSONObject reqJson) {
		JSONObject rsp_json = new JSONObject();
		String retCode = "1";
		String retMsg = "操作失败！";
		JSONObject bizDataJson = new JSONObject();
		try {
			String corpid = reqJson.getString("corpid");
			if(!"".equals(corpid) && corpid!=null){
				String userid = reqJson.getString("userid");
				
				UserSync userSync = new UserSync();
				userSync.setCorpid(corpid);
				userSync.setUserid(userid);
				userSync = userSyncDao.queryDetail(userSync);
				if(userSync != null){
					userSync.setM2faceurl(findFacePicUrl(corpid, userid));
					bizDataJson.put("userdetail", userSync);
					
					retCode = "0";
					retMsg = "操作成功！";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		rsp_json.put("retCode", retCode);
		rsp_json.put("retMsg", retMsg);
		rsp_json.put("bizData", bizDataJson);
		return rsp_json;
	}
	
	/**
	 * 通过userid查询M2头像URL
	 * @param corpid
	 * @param userid
	 * @return
	 */
	private String findFacePicUrl(String corpid, String userid){
		String fecePicUrl = "";
		
		UserFace userFace = new UserFace();
		userFace.setCorpid(corpid);
		userFace.setUserid(userid);
		userFace = userFaceDao.getUserFaceDetail(userFace);
		if(userFace != null){
			fecePicUrl = DingTalkProperties.file_prefix + userFace.getStorepath();
		}
		return fecePicUrl;
	}

	

}
