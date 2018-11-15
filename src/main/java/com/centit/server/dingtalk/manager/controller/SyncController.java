package com.centit.server.dingtalk.manager.controller;/**
 * Created by li_hao on 2017/6/10.
 */

import com.alibaba.fastjson.JSONObject;
import com.centit.server.dingtalk.manager.service.SyncManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>通讯录同步<p>
 * @version 1.0
 * @author li_hao
 * @date 2018年8月17日
 */
@Controller
@RequestMapping("/sync")
public class SyncController {
	private static final Log log = LogFactory.getLog(SyncController.class);

	 @Resource
	 private SyncManager syncManager;
	 
	/**
     * 系统超级管理员分页列表
     * @param request
     * @param response
     */
    @RequestMapping(value = "/syncAllDepts", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject syncAllDepts(HttpServletRequest request, HttpServletResponse response){
    	JSONObject reqJson = new JSONObject();
    	String corpid = request.getParameter("corpid");
    	reqJson.put("corpid", corpid);
    	
    	JSONObject respObject = syncManager.syncAllDepts(reqJson);
		return respObject;
    }

    /**
     * 同步本单位下所有人员
     * @param request
     * @param response
     */
    @RequestMapping(value = "/syncThisDeptUsers", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject syncThisDeptUsers(HttpServletRequest request, HttpServletResponse response){
    	JSONObject reqJson = new JSONObject();
    	String corpid = request.getParameter("corpid");
    	String deptid = request.getParameter("deptid");
    	reqJson.put("corpid", corpid);
    	reqJson.put("deptid", deptid);
    	
    	JSONObject respObject = syncManager.syncThisDeptUsers(reqJson);
		return respObject;
    }

    /**
     * 同步本单位及所有下级子单位的所有人员
     * @param request
     * @param response
     */
    @RequestMapping(value = "/syncDeptAndChildsUsers", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject syncDeptAndChildsUsers(HttpServletRequest request, HttpServletResponse response){
    	JSONObject reqJson = new JSONObject();
    	String corpid = request.getParameter("corpid");
    	String deptid = request.getParameter("deptid");
    	reqJson.put("corpid", corpid);
    	reqJson.put("deptid", deptid);
    	
    	JSONObject respObject = syncManager.syncDeptAndChildsUsers(reqJson);
		return respObject;
    }
    
    
}
