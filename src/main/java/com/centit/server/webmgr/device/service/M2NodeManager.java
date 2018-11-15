package com.centit.server.webmgr.device.service;

import com.alibaba.fastjson.JSONObject;

/**
 * <p>M2人脸识别虚拟节点<p>
 * @version 1.0
 * @author li_hao
 * @date 2018年7月9日
 */
public interface M2NodeManager {
	
	/**
	 * M2虚拟节点(单位)分页列表
	 * @param jsonObject
	 * @return
	 */
	public JSONObject queryM2NodePageList(JSONObject reqJson);

	/**
	 * 新增M2虚拟节点(单位)（M2人脸识别）
	 * @param jsonObject
	 * @return
	 */
	public JSONObject addM2Node(JSONObject reqJson);

	/**
	 * 修改M2虚拟节点(单位)（M2人脸识别）
	 * @param jsonObject
	 * @return
	 */
	public JSONObject updateM2Node(JSONObject reqJson);

	/**
	 * 删除M2虚拟节点(单位)（M2人脸识别）
	 * @param jsonObject
	 * @return
	 */
	public JSONObject deleteM2Node(JSONObject reqJson);

	
	
	
	
}
