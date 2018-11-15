package com.centit.server.webmgr.device.service;

import com.alibaba.fastjson.JSONObject;

/**
 * <p>钉钉智能设备<p>
 * @version 1.0
 * @author li_hao
 * @date 2018年4月19日
 */
public interface DeviceManager {

	/**
	 * 查询设备列表
	 * @param jsonObject
	 * @return
	 */
	public JSONObject queryDeviceList(JSONObject reqJson);

	/**
	 * 修改设备名称
	 * @param jsonObject
	 * @return
	 */
	public JSONObject updateDeviceName(JSONObject reqJson);

	/**
	 * 设备解绑（设备删除）
	 * @param jsonObject
	 * @return
	 */
	public JSONObject unbindDevice(JSONObject reqJson);

	/**
	 * M2查询可绑定的XXX列表
	 * @param jsonObject
	 * @return
	 */
	public JSONObject queryCanBindMeetingRoomList(JSONObject reqJson);

	/**
	 * XXX查询可绑定的M2列表
	 * @param jsonObject
	 * @return
	 */
	public JSONObject queryCanBindM2List(JSONObject reqJson);

	/**
	 * M2设备绑定XXX
	 * @param jsonObject
	 * @return
	 */
	public JSONObject deviceBindingMeetingRoom(JSONObject reqJson);

	/**
	 * M2设备解绑XXX
	 * @param jsonObject
	 * @return
	 */
	public JSONObject deviceUnbindingMeetingRoom(JSONObject reqJson);

	/**
	 * M2设备绑定虚拟节点
	 * @param jsonObject
	 * @return
	 */
	public JSONObject deviceBindingM2Node(JSONObject reqJson);
	
	/**
	 * M2设备解绑虚拟节点
	 * @param jsonObject
	 * @return
	 */
	public JSONObject deviceUnbindingM2Node(JSONObject reqJson);

	
	
	
	
}
