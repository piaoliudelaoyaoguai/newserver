package com.centit.server.webmgr.device.service;

import com.alibaba.fastjson.JSONObject;

/**
 * <p>人脸识别<p>
 * @version 1.0
 * @author li_hao
 * @date 2018年2月28日
 */
public interface FaceRecognitionManager {

	/**
	 * 上传人脸照片
	 * @param jsonObject
	 * @return
	 */
	public JSONObject uploadFaceRecPic(JSONObject reqJson);

	/**
	 * 查询人脸录入情况列表
	 * @param jsonObject
	 * @return
	 */
	public JSONObject queryM2FaceUserSetList(JSONObject reqJson);

	/**
	 * 查询人脸录入情况详情
	 * @param jsonObject
	 * @return
	 */
	public JSONObject queryM2FaceUserSetDetail(JSONObject reqJson);

	
}
