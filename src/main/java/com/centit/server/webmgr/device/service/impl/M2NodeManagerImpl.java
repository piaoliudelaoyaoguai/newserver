package com.centit.server.webmgr.device.service.impl;/**
 * Created by li_hao on 2017/6/10.
 */

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.fastjson.JSONObject;
import com.centit.server.dingtalk.manager.uitls.CommonUtil;
import com.centit.server.dingtalk.manager.uitls.DingTalkUtil;
import com.centit.server.webmgr.common.CommonFlag;
import com.centit.server.webmgr.device.dao.DeviceDao;
import com.centit.server.webmgr.device.dao.M2NodeDao;
import com.centit.server.webmgr.device.po.Device;
import com.centit.server.webmgr.device.po.M2Node;
import com.centit.server.webmgr.device.service.M2NodeManager;


/**
 * <p>M2人脸识别虚拟节点<p>
 * @version 1.0
 * @author li_hao
 * @date 2018年7月9日
 */
@Service
@Transactional
public class M2NodeManagerImpl implements M2NodeManager {
	
	public static final Log log = LogFactory.getLog(M2NodeManager.class);
	
	@Resource
	private M2NodeDao m2NodeDao;
	@Resource
	private DeviceDao deviceDao;
	
	/**
	 * M2虚拟节点(单位)分页列表
	 */
	@Override
	public JSONObject queryM2NodePageList(JSONObject reqJson) {
		JSONObject rsp_json = new JSONObject();
		String retCode = "1";
		String retMsg = "操作失败！";
		JSONObject bizData_json = new JSONObject();
		try {
			String corpid = reqJson.getString("corpid");
			if(!"".equals(corpid) && corpid!=null){
				String deptname = reqJson.getString("deptname");
				String pageNum = reqJson.getString("pageNum");
				String pageSize = reqJson.getString("pageSize");
				
				HashMap<String, Object> reqMap = new HashMap<String, Object>();
				reqMap.put("corpid", corpid);
				reqMap.put("deptname", deptname);
				reqMap.put("pageNum", (Integer.valueOf(pageNum)-1)*Integer.valueOf(pageSize));
				reqMap.put("pageSize", Integer.valueOf(pageSize));
				
				List<M2Node> m2NodeList = m2NodeDao.queryM2NodeListPage(reqMap);
				int total = m2NodeDao.queryM2NodeListPageCount(reqMap);
				
				bizData_json.put("M2NodeList", m2NodeList);
				bizData_json.put("total", total);
				
				retCode = "0";
				retMsg = "操作成功！";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		rsp_json.put("retCode", retCode);
		rsp_json.put("retMsg", retMsg);
		rsp_json.put("bizData", bizData_json);
		return rsp_json;
	}

	/**
	 * 新增M2虚拟节点(单位)（M2人脸识别）
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject addM2Node(JSONObject reqJson) {
		JSONObject rsp_json = new JSONObject();
		String retCode = "1";
		String retMsg = "操作失败！";
		JSONObject bizData_json = new JSONObject();
		try {
			String corpid = reqJson.getString("corpid");
			if(!"".equals(corpid) && corpid!=null){
				String deptname = reqJson.getString("deptname");
				String parentid = reqJson.getString("parentid");
				String createuserid = reqJson.getString("createuserid");
				
				//钉钉虚拟节点创建
				String accessToken = CommonUtil.getAccessToken(corpid);
				JSONObject deptJson = new JSONObject();
				deptJson.put("name", deptname);
				deptJson.put("parentid", parentid);
				JSONObject departmentCreate = DingTalkUtil.departmentCreate(accessToken, deptJson);
				String errcode = departmentCreate.getString("errcode");
				if(CommonFlag.TYPE_Z.equals(errcode)){
					//本地入库
					M2Node node = new M2Node();
					node.setCorpid(corpid);
					node.setDeptid(departmentCreate.getString("id"));
					node.setDeptname(deptname);
					node.setParentid(parentid);
					node.setCreateuserid(createuserid);
					
					m2NodeDao.addM2Node(node);
				}
				
				retCode = "0";
				retMsg = "操作成功！";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		rsp_json.put("retCode", retCode);
		rsp_json.put("retMsg", retMsg);
		rsp_json.put("bizData", bizData_json);
		return rsp_json;
	}

	/**
	 * 修改M2虚拟节点(单位)（M2人脸识别）
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject updateM2Node(JSONObject reqJson) {
		JSONObject rsp_json = new JSONObject();
		String retCode = "1";
		String retMsg = "操作失败！";
		JSONObject bizData_json = new JSONObject();
		try {
			String corpid = reqJson.getString("corpid");
			if(!"".equals(corpid) && corpid!=null){
				String id = reqJson.getString("id");
				String deptid = reqJson.getString("deptid");
				String deptname = reqJson.getString("deptname");
				String updateuserid = reqJson.getString("updateuserid");
				
				//钉钉虚拟节点修改
				String accessToken = CommonUtil.getAccessToken(corpid);
				JSONObject deptJson = new JSONObject();
				deptJson.put("id", deptid);
				deptJson.put("name", deptname);
				JSONObject departmentCreate = DingTalkUtil.departmentUpdate(accessToken, deptJson);
				String errcode = departmentCreate.getString("errcode");
				if(CommonFlag.TYPE_Z.equals(errcode)){
					//本地数据库修改
					M2Node node = new M2Node();
					node.setCorpid(corpid);
					node.setId(id);
					node.setDeptid(deptid);
					node.setDeptname(deptname);
					node.setUpdateuserid(updateuserid);
					
					m2NodeDao.updateM2Node(node);
				}
				
				retCode = "0";
				retMsg = "操作成功！";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		rsp_json.put("retCode", retCode);
		rsp_json.put("retMsg", retMsg);
		rsp_json.put("bizData", bizData_json);
		return rsp_json;
	}
	
	/**
	 * 删除M2虚拟节点(单位)（M2人脸识别）
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject deleteM2Node(JSONObject reqJson) {
		JSONObject rsp_json = new JSONObject();
		String retCode = "1";
		String retMsg = "操作失败！";
		JSONObject bizData_json = new JSONObject();
		try {
			String corpid = reqJson.getString("corpid");
			if(!"".equals(corpid) && corpid!=null){
				String id = reqJson.getString("id");
				String deptid = reqJson.getString("deptid");
				
				//钉钉虚拟节点删除
				String accessToken = CommonUtil.getAccessToken(corpid);
				JSONObject departmentCreate = DingTalkUtil.departmentDelete(accessToken, deptid);
				String errcode = departmentCreate.getString("errcode");
				if(CommonFlag.TYPE_Z.equals(errcode)){
					//本地数据库删除
					M2Node node = new M2Node();
					node.setCorpid(corpid);
					node.setId(id);
					node.setDeptid(deptid);
					
					m2NodeDao.deleteM2Node(node);  //删除虚拟节点
					
					Device device = new Device();
					device.setCorpid(corpid);
					device.setNodeid(id);
					deviceDao.deleteDeviceM2Node(device);  //删除关联表
					
				}
				
				retCode = "0";
				retMsg = "操作成功！";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		rsp_json.put("retCode", retCode);
		rsp_json.put("retMsg", retMsg);
		rsp_json.put("bizData", bizData_json);
		return rsp_json;
	}

	
	
	
	
	
	
	
}
