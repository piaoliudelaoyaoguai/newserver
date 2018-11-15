package com.centit.server.webmgr.device.controller;/**
 * Created by li_hao on 2017/6/10.
 */

import com.alibaba.fastjson.JSONObject;
import com.centit.server.webmgr.device.service.DeviceManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>钉钉智能设备<p>
 * @version 1.0
 * @author li_hao
 * @date 2018年4月19日
 */
@Controller
@RequestMapping("/device")
public class DeviceController {
	private static final Log log = LogFactory.getLog(DeviceController.class);

	 @Resource
	 private DeviceManager deviceManager;
	
    
	 /**
	  * 查询设备列表
	  * @param request
	  * @param response
	  * @param reqJson
	  */
	 @RequestMapping(value = "/queryDeviceList", method = RequestMethod.POST)
	 @ResponseBody
	 public JSONObject queryDeviceList(HttpServletRequest request, HttpServletResponse response,@RequestBody JSONObject reqJson){
		 JSONObject respObject = deviceManager.queryDeviceList(reqJson);
		return respObject;
	 }

	 /**
	  * 修改设备名称
	  * @param request
	  * @param response
	  * @param reqJson
	  */
	 @RequestMapping(value = "/updateDeviceName", method = RequestMethod.POST)
	 @ResponseBody
	 public JSONObject updateDeviceName(HttpServletRequest request, HttpServletResponse response,@RequestBody JSONObject reqJson){
		 JSONObject respObject = deviceManager.updateDeviceName(reqJson);
		return respObject;
	 }

	 /**
	  * 设备解绑（设备删除）
	  * @param request
	  * @param response
	  * @param reqJson
	  */
	 @RequestMapping(value = "/unbindDevice", method = RequestMethod.POST)
	 @ResponseBody
	 public JSONObject unbindDevice(HttpServletRequest request, HttpServletResponse response,@RequestBody JSONObject reqJson){
		 JSONObject respObject = deviceManager.unbindDevice(reqJson);
		return respObject;
	 }

	 /**
	  * M2查询可绑定的XXX列表（分页）
	  * @param request
	  * @param response
	  * @param reqJson
	  */
	 @RequestMapping(value = "/queryCanBindMeetingRoomList", method = RequestMethod.POST)
	 @ResponseBody
	 public JSONObject queryCanBindMeetingRoomList(HttpServletRequest request, HttpServletResponse response,@RequestBody JSONObject reqJson){
		 JSONObject respObject = deviceManager.queryCanBindMeetingRoomList(reqJson);
		return respObject;
	 }

	 /**
	  * XXX查询可绑定的M2列表
	  * @param request
	  * @param response
	  * @param reqJson
	  */
	 @RequestMapping(value = "/queryCanBindM2List", method = RequestMethod.POST)
	 @ResponseBody
	 public JSONObject queryCanBindM2List(HttpServletRequest request, HttpServletResponse response,@RequestBody JSONObject reqJson){
		 JSONObject respObject = deviceManager.queryCanBindM2List(reqJson);
		return respObject;
	 }

	 /**
	  * M2设备绑定XXX
	  * @param request
	  * @param response
	  * @param reqJson
	  */
	 @RequestMapping(value = "/deviceBindingMeetingRoom", method = RequestMethod.POST)
	 @ResponseBody
	 public JSONObject deviceBindingMeetingRoom(HttpServletRequest request, HttpServletResponse response,@RequestBody JSONObject reqJson){
		 JSONObject respObject = deviceManager.deviceBindingMeetingRoom(reqJson);
		return respObject;
	 }

	 /**
	  * M2设备解绑XXX
	  * @param request
	  * @param response
	  * @param reqJson
	  */
	 @RequestMapping(value = "/deviceUnbindingMeetingRoom", method = RequestMethod.POST)
	 @ResponseBody
	 public JSONObject deviceUnbindingMeetingRoom(HttpServletRequest request, HttpServletResponse response,@RequestBody JSONObject reqJson){
		 JSONObject respObject = deviceManager.deviceUnbindingMeetingRoom(reqJson);
		return respObject;
	 }

	 /**
	  * M2设备绑定虚拟节点
	  * @param request
	  * @param response
	  * @param reqJson
	  */
	 @RequestMapping(value = "/deviceBindingM2Node", method = RequestMethod.POST)
	 @ResponseBody
	 public JSONObject deviceBindingM2Node(HttpServletRequest request, HttpServletResponse response,@RequestBody JSONObject reqJson){
		 JSONObject respObject = deviceManager.deviceBindingM2Node(reqJson);
		return respObject;
	 }
	 
	 /**
	  * M2设备解绑虚拟节点
	  * @param request
	  * @param response
	  * @param reqJson
	  */
	 @RequestMapping(value = "/deviceUnbindingM2Node", method = RequestMethod.POST)
	 @ResponseBody
	 public JSONObject deviceUnbindingM2Node(HttpServletRequest request, HttpServletResponse response,@RequestBody JSONObject reqJson){
		 JSONObject respObject = deviceManager.deviceUnbindingM2Node(reqJson);
		return respObject;
	 }
    
    
    

    
}
