package com.centit.server.webmgr.device.controller;/**
 * Created by li_hao on 2017/6/10.
 */

import com.alibaba.fastjson.JSONObject;
import com.centit.server.webmgr.device.service.M2NodeManager;
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
 * <p>M2人脸识别虚拟节点<p>
 * @version 1.0
 * @author li_hao
 * @date 2018年7月9日
 */
@Controller
@RequestMapping("/node")
public class M2NodeController {
	private static final Log log = LogFactory.getLog(M2NodeController.class);

	 @Resource
	 private M2NodeManager m2NodeManager;
	 
	 /**
	  * M2虚拟节点(单位)分页列表
	  * @param request
	  * @param response
	  * @param reqJson
	  */
	 @RequestMapping(value = "/queryM2NodePageList", method = RequestMethod.POST)
	 @ResponseBody
	 public JSONObject queryM2NodePageList(HttpServletRequest request, HttpServletResponse response,@RequestBody JSONObject reqJson){
		 JSONObject respObject = m2NodeManager.queryM2NodePageList(reqJson);
		return respObject;
	 }
    
	 /**
	  * 新增M2虚拟节点(单位)（M2人脸识别）
	  * @param request
	  * @param response
	  * @param reqJson
	  */
	 @RequestMapping(value = "/addM2Node", method = RequestMethod.POST)
	 @ResponseBody
	 public JSONObject addM2Node(HttpServletRequest request, HttpServletResponse response,@RequestBody JSONObject reqJson){
		 JSONObject respObject = m2NodeManager.addM2Node(reqJson);
		return respObject;
	 }

	 /**
	  * 修改M2虚拟节点(单位)（M2人脸识别）
	  * @param request
	  * @param response
	  * @param reqJson
	  */
	 @RequestMapping(value = "/updateM2Node", method = RequestMethod.POST)
	 @ResponseBody
	 public JSONObject updateM2Node(HttpServletRequest request, HttpServletResponse response,@RequestBody JSONObject reqJson){
		 JSONObject respObject = m2NodeManager.updateM2Node(reqJson);
		return respObject;
	 }
	
	 /**
	  * 删除M2虚拟节点(单位)（M2人脸识别）
	  * @param request
	  * @param response
	  * @param reqJson
	  */
	 @RequestMapping(value = "/deleteM2Node", method = RequestMethod.POST)
	 @ResponseBody
	 public JSONObject deleteM2Node(HttpServletRequest request, HttpServletResponse response,@RequestBody JSONObject reqJson){
		 JSONObject respObject = m2NodeManager.deleteM2Node(reqJson);
		return respObject;
	 }

    
    

    
}
