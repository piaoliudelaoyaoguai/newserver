package com.centit.server.dingtalk.manager.dao;/**
 * Created by li_hao on 2017/6/9.
 */

import java.util.HashMap;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import com.centit.server.dingtalk.manager.po.DeptSync;
/**
 * <p>钉钉部门dao<p>
 * @version 1.0
 * @author li_hao
 * @date 2018年4月23日
 */
@Repository("deptSyncDao")
public interface DeptSyncDao {
	public static final Log log = LogFactory.getLog(DeptSyncDao.class);
	
	/**
	 * 查询列表
	 * @param deptSync
	 * @return
	 */
	List<DeptSync> queryList(DeptSync deptSync);
	
	/**
	 * 查询子部门列表
	 * @param deptSync
	 * @return
	 */
	List<DeptSync> queryChildList(DeptSync deptSync);
	
	/**
	 * 插入
	 * @param deptSync
	 * @return
	 */
	int insertDingTalkDept(DeptSync deptSync);

	/**
	 * 更新
	 * @param deptSync
	 * @return
	 */
	int updateDingTalkDept(DeptSync deptSync);

	/**
	 * 更新:数梦平台
	 * @param deptSync
	 * @return
	 */
	int updateDingTalkDeptDtDream(DeptSync deptSync);

	/**
	 * REPLACE：corpid和deptid为唯一索引，有则更新，无则插入
	 * @param deptSync
	 * @return
	 */
	int replaceDingTalkDept(DeptSync deptSync);
	
	/**
	 * 删除
	 * @param deptSync
	 * @return
	 */
	int deleteDingTalkDept(DeptSync deptSync);

	/**
	 * 删除:数梦平台
	 * @param deptSync
	 * @return
	 */
	int deleteDingTalkDeptDtDream(DeptSync deptSync);

	/**
	 * 查询用户的部门列表
	 * @param deptSync
	 * @return
	 */
	List<DeptSync> queryDeptListByUser(HashMap<String, Object> reqMap);

	/**
	 * 查询上级部门
	 * @param deptSync
	 * @return
	 */
	DeptSync queryParentDept(DeptSync deptSync);

	/**
	 * 根绝deptNumber查询deptid
	 * @param deptSync
	 * @return
	 */
	DeptSync queryDeptidByDeptnumber(DeptSync deptSync);
	
}
