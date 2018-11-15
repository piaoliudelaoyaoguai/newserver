package com.centit.server.webmgr.device.controller;/**
 * Created by li_hao on 2017/6/10.
 */

import com.alibaba.fastjson.JSONObject;
import com.centit.server.webmgr.device.service.FaceRecognitionManager;
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
 * <p>人脸识别<p>
 * @version 1.0
 * @author li_hao
 * @date 2018年4月18日
 */
@Controller
@RequestMapping("/faceRec")
public class FaceRecognitionController {
	private static final Log log = LogFactory.getLog(FaceRecognitionController.class);
	
	 @Resource
	 private FaceRecognitionManager faceRecognitionManager;
	
	 /**
 	 * 上传人脸照片
 	 * @param request
 	 * @param response
 	 * @param reqJson
 	 */
    @RequestMapping(value = "/uploadFaceRecPic", method = RequestMethod.POST)
    @ResponseBody
	public JSONObject uploadFaceRecPic(HttpServletRequest request, HttpServletResponse response,@RequestBody JSONObject reqJson){
    	JSONObject respObject = faceRecognitionManager.uploadFaceRecPic(reqJson);
		return respObject;
    }

    /**
     * 查询人脸录入情况列表
     * @param request
     * @param response
     * @param reqJson
     */
    @RequestMapping(value = "/queryM2FaceUserSetList", method = RequestMethod.POST)
    @ResponseBody
	public JSONObject queryM2FaceUserSetList(HttpServletRequest request, HttpServletResponse response,@RequestBody JSONObject reqJson){
    	JSONObject respObject = faceRecognitionManager.queryM2FaceUserSetList(reqJson);
		return respObject;
    }
    
    /**
     * 查询人脸录入情况详情
     * @param request
     * @param response
     * @param reqJson
     */
    @RequestMapping(value = "/queryM2FaceUserSetDetail", method = RequestMethod.POST)
    @ResponseBody
	public JSONObject queryM2FaceUserSetDetail(HttpServletRequest request, HttpServletResponse response,@RequestBody JSONObject reqJson){
    	JSONObject respObject = faceRecognitionManager.queryM2FaceUserSetDetail(reqJson);
		return respObject;
    }
    
    

    
}
