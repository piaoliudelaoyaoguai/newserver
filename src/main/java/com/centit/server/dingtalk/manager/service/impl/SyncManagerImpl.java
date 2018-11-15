package com.centit.server.dingtalk.manager.service.impl;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.centit.server.dingtalk.manager.dao.DeptSyncDao;
import com.centit.server.dingtalk.manager.dao.UserDeptSyncDao;
import com.centit.server.dingtalk.manager.dao.UserSyncDao;
import com.centit.server.dingtalk.manager.po.DeptSync;
import com.centit.server.dingtalk.manager.po.UserDeptSync;
import com.centit.server.dingtalk.manager.po.UserSync;
import com.centit.server.dingtalk.manager.service.SyncManager;
import com.centit.server.dingtalk.manager.uitls.CommonInit;
import com.centit.server.dingtalk.manager.uitls.CommonUtil;
import com.centit.server.dingtalk.manager.uitls.DingTalkUtil;

/**
 * <p>通讯录同步<p>
 * @version 1.0
 * @author li_hao
 * @date 2018年8月17日
 */
@Service
@Transactional
public class SyncManagerImpl implements SyncManager {
	public static final Log log = LogFactory.getLog(SyncManager.class);
	
	@Resource
	private UserSyncDao userSyncDao;
	@Resource
	private DeptSyncDao deptSyncDao;
	@Resource
	private UserDeptSyncDao userDeptSyncDao;

	/**
	 * 同步所有单位
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject syncAllDepts(JSONObject reqJson) {
		JSONObject rsp_json = new JSONObject();
		String retCode = "1";
		String retMsg = "操作失败！";
		JSONObject bizData_json = new JSONObject();
		try {
			String corpid = reqJson.getString("corpid");
			if(StringUtils.isNotBlank(corpid)){
				String accessToken = CommonUtil.getAccessToken(corpid);
				
				DeptSync deptSyncQ = new DeptSync();
				deptSyncQ.setCorpid(corpid);
				List<DeptSync> deptListOld = deptSyncDao.queryList(deptSyncQ);  //部门列表（旧）
				List<String> deptIdListOld = new ArrayList<>(); //数据库中数据（旧）的deptId列表
				for(DeptSync dept : deptListOld){
					deptIdListOld.add(dept.getDeptid());
				}
				
				List<String> samedeptIdList = new ArrayList<>(); //新旧列表相同的交集deptId
				
				//部门同步
	    		JSONArray jsonArrayDepts = DingTalkUtil.getCorpDepartmentList(accessToken).getJSONArray("deptinfolist");
	    		if(jsonArrayDepts!=null){
	    			for(int i=0;i<jsonArrayDepts.size();i++){
	    				JSONObject jobDept = jsonArrayDepts.getJSONObject(i);
	    				String deptid = jobDept.getString("id");
	    				
	    				if(deptIdListOld.contains(deptid)){  //如果数据库中数据（旧）的deptId列表中含有新查出的id
	    					samedeptIdList.add(deptid);
		    			}
	    			}
	    		}
	    		//删除
	    		for(DeptSync deptOld : deptListOld){
	    			String deptidOld = deptOld.getDeptid();
	    			if(!samedeptIdList.contains(deptidOld)){
	    				deptSyncDao.deleteDingTalkDept(deptOld);  //删除部门
	    				
	    				//删除用户部门关联表
	    				UserDeptSync userDeptSyncDel = new UserDeptSync();
	    				userDeptSyncDel.setCorpid(corpid);
	    				userDeptSyncDel.setDeptid(deptidOld);
	    				userDeptSyncDao.deleteDingTalkUserDept(userDeptSyncDel);
	    			}
	    		}
	    		
	    		//新增、修改
	    		if(jsonArrayDepts!=null){
	    			for(int i=0;i<jsonArrayDepts.size();i++){
	    				JSONObject jobDept = jsonArrayDepts.getJSONObject(i);
	    				String deptid = jobDept.getString("id");
	    				
	    				String deptname = DingTalkUtil.filterEmoji(jobDept.getString("name"));
						
						DeptSync deptSync = new DeptSync();
						deptSync.setCorpid(corpid);
						deptSync.setDeptid(deptid);
						deptSync.setDeptname(deptname);
						deptSync.setParentid(jobDept.getString("parentid"));
						deptSync.setSort(jobDept.getString("order"));
	    				
	    				//新增
	    				if(!samedeptIdList.contains(deptid)){
	    					deptSyncDao.insertDingTalkDept(deptSync);
	    				}
	    				//修改
	    				if(samedeptIdList.contains(deptid)){
	    					deptSyncDao.updateDingTalkDept(deptSync);
	    				}
	    			}
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
	 * 同步本单位下所有人员
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject syncThisDeptUsers(JSONObject reqJson) {
		JSONObject rsp_json = new JSONObject();
		String retCode = "1";
		String retMsg = "操作失败！";
		JSONObject bizData_json = new JSONObject();
		try {
			String corpid = reqJson.getString("corpid");
			if(StringUtils.isNotBlank(corpid)){
				String deptid = reqJson.getString("deptid");
				
				boolean flag = syncOneDeptUsers(corpid, deptid);
				if(flag){
					retCode = "0";
					retMsg = "操作成功！";
				}
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
	 * 同步本单位及所有下级子单位的所有人员
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject syncDeptAndChildsUsers(JSONObject reqJson) {
		JSONObject rsp_json = new JSONObject();
		String retCode = "1";
		String retMsg = "操作失败！";
		JSONObject bizData_json = new JSONObject();
		try {
			String corpid = reqJson.getString("corpid");
			if(StringUtils.isNotBlank(corpid)){
				String deptid = reqJson.getString("deptid");
				
				//先同步本单位人员
				boolean flag = syncOneDeptUsers(corpid, deptid);
				
				if(flag){
					//再同步所有下级单位人员
					//查询子单位
					String accessToken = CommonUtil.getAccessToken(corpid);
					JSONObject jsondepts = DingTalkUtil.getDepartments(accessToken,deptid,"true");  //获取所有下级部门列表
					if(jsondepts != null ){
						JSONArray deptJsonArray = jsondepts.getJSONArray("department");
						if(deptJsonArray != null){
							for(int i=0;i<deptJsonArray.size();i++){
								JSONObject deptjson = deptJsonArray.getJSONObject(i);
								String id = deptjson.getString("id");
								
								boolean flag2 = syncOneDeptUsers(corpid, id);
								
								if(!flag2){
									flag = false;
								}
							}
						}
					}
					
				}
				if(flag){
					retCode = "0";
					retMsg = "操作成功！";
				}
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
	 * 同步单个指定部门的人员
	 * @param corpid
	 * @param deptid
	 */
	private boolean syncOneDeptUsers(String corpid, String deptid){
		boolean flag = false;
		try {
			if(StringUtils.isNotBlank(corpid) && StringUtils.isNotBlank(deptid)){
				//删除用户部门关联表
				UserDeptSync userDeptSyncDel = new UserDeptSync();
				userDeptSyncDel.setCorpid(corpid);
				userDeptSyncDel.setDeptid(deptid);
				userDeptSyncDao.deleteDingTalkUserDept(userDeptSyncDel);
				
				String accessToken = CommonUtil.getAccessToken(corpid);
				
				//同步部门下的用户
				JSONArray jsonArrayUsers = DingTalkUtil.getDepartmentDetailUsers(accessToken, deptid).getJSONArray("userlist");

				if(jsonArrayUsers!=null){
	    			for(int j=0;j<jsonArrayUsers.size();j++){
	    				JSONObject jobUser = jsonArrayUsers.getJSONObject(j);
						String userid = DingTalkUtil.filterEmoji(jobUser.getString("userid"));
						String mobile = DingTalkUtil.filterEmoji(jobUser.getString("mobile"));
						String username = DingTalkUtil.filterEmoji(jobUser.getString("name"));
						String position = DingTalkUtil.filterEmoji(jobUser.getString("position"));
						String workPlace = DingTalkUtil.filterEmoji(jobUser.getString("workPlace"));
						String tel = DingTalkUtil.filterEmoji(jobUser.getString("tel"));
//						String email = DingTalkUtil.filterEmoji(jobUser.getString("email"));
						String remark = DingTalkUtil.filterEmoji(jobUser.getString("remark"));
						String jobnumber = DingTalkUtil.filterEmoji(jobUser.getString("jobnumber"));
						
						UserSync userSync = new UserSync();
						userSync.setCorpid(corpid);
						userSync.setUserid(userid);
						userSync.setUsername(username);
//						userSync.setPassword(new Md5PasswordEncoder().encodePassword(CommonFlag.TYPE_123456, mobile));
						userSync.setMobile(mobile);
						userSync.setAvatar(jobUser.getString("avatar"));
						userSync.setWorkplace(workPlace);
						userSync.setTel(tel);
						userSync.setRemark(remark);
						userSync.setPosition(position);
						userSync.setJobnumber(jobnumber);
						userSyncDao.replaceDingTalkUser(userSync);  //更新人员表
						
	        			UserDeptSync userDeptSync = new UserDeptSync();
	        			userDeptSync.setCorpid(corpid);
						userDeptSync.setUserid(userid);
						userDeptSync.setDeptid(deptid);
						userDeptSyncDao.insertDingTalkUserDept(userDeptSync);  //插入人员-部门关联表
	    			}
				}
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	
	
	
	

}
