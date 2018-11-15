package com.centit.server.dingtalk.manager.dao;/**
 * Created by li_hao on 2017/6/9.
 */

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.centit.server.dingtalk.manager.po.UserDeptSync;

/**
 * <p>钉钉用户-部门 关联dao<p>
 * @version 1.0
 * @author li_hao
 * @date 2018年4月23日
 */
@Repository("userDeptSyncDao")
public interface UserDeptSyncDao {
	public static final Log log = LogFactory.getLog(UserDeptSyncDao.class);
	
	/**
	 * 删除 用户-部门关联
	 * @param userDeptSync
	 * @return
	 */
	int insertDingTalkUserDept(UserDeptSync userDeptSync);
	
	/**
	 * 删除 用户-部门关联
	 * @param userDeptSync
	 * @return
	 */
	int deleteDingTalkUserDept(UserDeptSync userDeptSync);
	


}
