package com.centit.server.dingtalk.manager.sync;

import java.util.ArrayList;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.centit.server.dingtalk.manager.po.DeptSync;
import com.centit.server.dingtalk.manager.po.UserDeptSync;
import com.centit.server.dingtalk.manager.po.UserSync;
import com.centit.server.dingtalk.manager.uitls.CommonInit;
import com.centit.server.dingtalk.manager.uitls.CommonUtil;
import com.centit.server.dingtalk.manager.uitls.DingTalkUtil;

/**
 * <p>钉钉通讯录同步<p>
 * @version 1.0
 * @author li_hao
 * @date 2017年12月21日
 */
@Transactional
public class AddressListSync {
	
	/**
	 * 通讯录用户新增
	 * @param jsonObject
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public void createUser(JSONObject jsonObject){
		try {
			String corpid = jsonObject.getString("CorpId");
			if(!"".equals(corpid) && corpid!=null){
				String accessToken = CommonUtil.getAccessToken(corpid);
				
				JSONArray userIds = jsonObject.getJSONArray("UserIds");
				for(int i=0;i<userIds.size();i++){
					String userId = userIds.getString(i);
					
					JSONObject bizDataJson = DingTalkUtil.getUserDetail(accessToken, userId);
					String userid = DingTalkUtil.filterEmoji(bizDataJson.getString("userid"));
					String mobile = DingTalkUtil.filterEmoji(bizDataJson.getString("mobile"));
					String username = DingTalkUtil.filterEmoji(bizDataJson.getString("name"));
					String position = DingTalkUtil.filterEmoji(bizDataJson.getString("position"));
					String workPlace = DingTalkUtil.filterEmoji(bizDataJson.getString("workPlace"));
					String tel = DingTalkUtil.filterEmoji(bizDataJson.getString("tel"));
//					String email = DingTalkUtil.filterEmoji(bizDataJson.getString("email"));
					String remark = DingTalkUtil.filterEmoji(bizDataJson.getString("remark"));
					String jobnumber = DingTalkUtil.filterEmoji(bizDataJson.getString("jobnumber"));
					
					UserSync userSync = new UserSync();
					userSync.setCorpid(corpid);
					userSync.setUserid(userid);
					userSync.setUsername(username);
//					userSync.setPassword(new Md5PasswordEncoder().encodePassword(CommonFlag.TYPE_123456, mobile));
					userSync.setMobile(mobile);
					userSync.setAvatar(bizDataJson.getString("avatar"));
					userSync.setWorkplace(workPlace);
					userSync.setTel(tel);
					userSync.setRemark(remark);
					userSync.setPosition(position);
					userSync.setJobnumber(jobnumber);
					CommonInit.userSyncDao.replaceDingTalkUser(userSync);  //插入人员表
					
					JSONArray deptIds = bizDataJson.getJSONArray("department");
					if(deptIds.size()>0 && deptIds!=null){
						for(int j=0;j<deptIds.size();j++){
		        			String deptId = deptIds.getString(j);
		        			UserDeptSync userDeptSync = new UserDeptSync();
		        			userDeptSync.setCorpid(corpid);
		        			userDeptSync.setUserid(userid);
							userDeptSync.setDeptid(deptId);
							CommonInit.userDeptSyncDao.insertDingTalkUserDept(userDeptSync);  //插入人员-部门关联表
		        		}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	/**
	 * 通讯录用户更改
	 * @param jsonObject
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public void updateUsers(JSONObject jsonObject) {
		try {
			String corpid = jsonObject.getString("CorpId");
			if(!"".equals(corpid) && corpid!=null){
				String accessToken = CommonUtil.getAccessToken(corpid);
				
				JSONArray userIds = jsonObject.getJSONArray("UserIds");
				for(int i=0;i<userIds.size();i++){
					String userId = userIds.getString(i);
					
					JSONObject bizDataJson = DingTalkUtil.getUserDetail(accessToken, userId);
					String userid = DingTalkUtil.filterEmoji(bizDataJson.getString("userid"));
					String mobile = DingTalkUtil.filterEmoji(bizDataJson.getString("mobile"));
					String username = DingTalkUtil.filterEmoji(bizDataJson.getString("name"));
					String position = DingTalkUtil.filterEmoji(bizDataJson.getString("position"));
					String workPlace = DingTalkUtil.filterEmoji(bizDataJson.getString("workPlace"));
					String tel = DingTalkUtil.filterEmoji(bizDataJson.getString("tel"));
//					String email = DingTalkUtil.filterEmoji(bizDataJson.getString("email"));
					String remark = DingTalkUtil.filterEmoji(bizDataJson.getString("remark"));
					String jobnumber = DingTalkUtil.filterEmoji(bizDataJson.getString("jobnumber"));
					
					UserSync userSync = new UserSync();
					userSync.setCorpid(corpid);
					userSync.setUserid(userid);
					userSync.setUsername(username);
//					userSync.setPassword(new Md5PasswordEncoder().encodePassword(CommonFlag.TYPE_123456, mobile));
					userSync.setMobile(mobile);
					userSync.setAvatar(bizDataJson.getString("avatar"));
					userSync.setWorkplace(workPlace);
					userSync.setTel(tel);
					userSync.setRemark(remark);
					userSync.setPosition(position);
					userSync.setJobnumber(jobnumber);
					CommonInit.userSyncDao.replaceDingTalkUser(userSync);  //更新人员表
					
					UserDeptSync userDeptSyncOld = new UserDeptSync();
					userDeptSyncOld.setCorpid(corpid);
					userDeptSyncOld.setUserid(userid);
					CommonInit.userDeptSyncDao.deleteDingTalkUserDept(userDeptSyncOld);
					
					JSONArray deptIds = bizDataJson.getJSONArray("department");
					if(deptIds.size()>0 && deptIds!=null){
						for(int j=0;j<deptIds.size();j++){
		        			String deptId = deptIds.getString(j);
		        			UserDeptSync userDeptSync = new UserDeptSync();
		        			userDeptSync.setCorpid(corpid);
		        			userDeptSync.setUserid(userid);
							userDeptSync.setDeptid(deptId);
							CommonInit.userDeptSyncDao.insertDingTalkUserDept(userDeptSync);  //插入人员-部门关联表
		        		}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	/**
	 * 通讯录用户删除
	 * @param jsonObject
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public void deleteUsers(JSONObject jsonObject) {
		try {
			String corpid = jsonObject.getString("CorpId");
			if(!"".equals(corpid) && corpid!=null){
				JSONArray userIds = jsonObject.getJSONArray("UserIds");
				for(int i=0;i<userIds.size();i++){
					String userId = userIds.getString(i);
					
					UserSync userSync = new UserSync();
					userSync.setCorpid(corpid);
					userSync.setUserid(userId);
					CommonInit.userSyncDao.deleteDingTalkUser(userSync);
					
					UserDeptSync userDeptSyncOld = new UserDeptSync();
					userDeptSyncOld.setCorpid(corpid);
					userDeptSyncOld.setUserid(userId);
					CommonInit.userDeptSyncDao.deleteDingTalkUserDept(userDeptSyncOld);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	/**
	 * 通讯录部门创建
	 * @param jsonObject
	 * @return
	 */
	public void deptCreate(JSONObject jsonObject) {
		try {
			String corpid = jsonObject.getString("CorpId");
			if(!"".equals(corpid) && corpid!=null){
				String accessToken = CommonUtil.getAccessToken(corpid);
				
				JSONArray DeptIds = jsonObject.getJSONArray("DeptIds");
				for(int i=0;i<DeptIds.size();i++){
					String deptId = DeptIds.getString(i);
					
					JSONObject departmentDetail = DingTalkUtil.getDepartmentDetail(accessToken, deptId);  //获取部门详情
					String deptname = DingTalkUtil.filterEmoji(departmentDetail.getString("name"));
					String parentid = departmentDetail.getString("parentid");
					String order = departmentDetail.getString("order");
					
					DeptSync deptSync = new DeptSync();
					deptSync.setCorpid(corpid);
					deptSync.setDeptid(deptId);
					deptSync.setDeptname(deptname);
					deptSync.setParentid(parentid);
					deptSync.setSort(order);
					CommonInit.deptSyncDao.replaceDingTalkDept(deptSync);  //部门插入
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 通讯录部门更新
	 * @param jsonObject
	 * @return
	 */
	public void deptUpdate(JSONObject jsonObject) {
		try {
			String corpid = jsonObject.getString("CorpId");
			if(!"".equals(corpid) && corpid!=null){
				String accessToken = CommonUtil.getAccessToken(corpid);
				
				JSONArray DeptIds = jsonObject.getJSONArray("DeptIds");
				for(int i=0;i<DeptIds.size();i++){
					String deptId = DeptIds.getString(i);
					
					JSONObject departmentDetail = DingTalkUtil.getDepartmentDetail(accessToken, deptId);  //获取部门详情
					String deptname = DingTalkUtil.filterEmoji(departmentDetail.getString("name"));
					String parentid = departmentDetail.getString("parentid");
					String order = departmentDetail.getString("order");
					
					DeptSync deptSync = new DeptSync();
					deptSync.setCorpid(corpid);
					deptSync.setDeptid(deptId);
					deptSync.setDeptname(deptname);
					deptSync.setParentid(parentid);
					deptSync.setSort(order);
					CommonInit.deptSyncDao.replaceDingTalkDept(deptSync);  //部门更新
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 通讯录部门删除
	 * @param jsonObject
	 * @return
	 */
	public void deptDelete(JSONObject jsonObject) {
		try {
			String corpid = jsonObject.getString("CorpId");
			if(!"".equals(corpid) && corpid!=null){
				
				JSONArray DeptIds = jsonObject.getJSONArray("DeptIds");
				for(int i=0;i<DeptIds.size();i++){
					String deptId = DeptIds.getString(i);
					
					DeptSync deptSync = new DeptSync();
					deptSync.setCorpid(corpid);
					deptSync.setDeptid(deptId);
					CommonInit.deptSyncDao.deleteDingTalkDept(deptSync);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 通讯录同步
	 * @param jsonObject
	 * @return
	 */
	public JSONObject addressListSync(JSONObject jsonObject) {
		try {
			String corpid = jsonObject.getString("CorpId");
			if(!"".equals(corpid) && corpid!=null){
				String accessToken = CommonUtil.getAccessToken(corpid);
				
				//删除用户
				UserSync userSyncDel = new UserSync();
				userSyncDel.setCorpid(corpid);
				CommonInit.userSyncDao.deleteDingTalkUser(userSyncDel);
				//删除用户部门关联表
				UserDeptSync userDeptSyncDel = new UserDeptSync();
				userDeptSyncDel.setCorpid(corpid);
				CommonInit.userDeptSyncDao.deleteDingTalkUserDept(userDeptSyncDel);
				
				DeptSync deptSyncQ = new DeptSync();
				deptSyncQ.setCorpid(corpid);
				List<DeptSync> deptListOld = CommonInit.deptSyncDao.queryList(deptSyncQ);  //部门列表（旧）
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
	    				CommonInit.deptSyncDao.deleteDingTalkDept(deptOld);  //删除部门
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
	    					CommonInit.deptSyncDao.insertDingTalkDept(deptSync);
	    				}
	    				//修改
	    				if(samedeptIdList.contains(deptid)){
	    					CommonInit.deptSyncDao.updateDingTalkDept(deptSync);
	    				}
	    				
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
//								String email = DingTalkUtil.filterEmoji(jobUser.getString("email"));
								String remark = DingTalkUtil.filterEmoji(jobUser.getString("remark"));
								String jobnumber = DingTalkUtil.filterEmoji(jobUser.getString("jobnumber"));
								
								UserSync userSync = new UserSync();
								userSync.setCorpid(corpid);
								userSync.setUserid(userid);
								userSync.setUsername(username);
//								userSync.setPassword(new Md5PasswordEncoder().encodePassword(CommonFlag.TYPE_123456, mobile));
								userSync.setMobile(mobile);
								userSync.setAvatar(jobUser.getString("avatar"));
								userSync.setWorkplace(workPlace);
								userSync.setTel(tel);
								userSync.setRemark(remark);
								userSync.setPosition(position);
								userSync.setJobnumber(jobnumber);
								CommonInit.userSyncDao.replaceDingTalkUser(userSync);  //更新人员表
								
			        			UserDeptSync userDeptSync = new UserDeptSync();
			        			userDeptSync.setCorpid(corpid);
								userDeptSync.setUserid(userid);
								userDeptSync.setDeptid(deptid);
								CommonInit.userDeptSyncDao.insertDingTalkUserDept(userDeptSync);  //插入人员-部门关联表
			    			}
						}
	    			}
	    		}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
}
