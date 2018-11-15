package com.centit.server.dingtalk.manager.dao;/**
 * Created by li_hao on 2017/6/9.
 */

import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.centit.server.dingtalk.manager.po.UserSync;

/**
 * <p>钉钉用户dao<p>
 * @version 1.0
 * @author li_hao
 * @date 2018年4月23日
 */
@Repository("userSyncDao")
public interface UserSyncDao {
	public static final Log log = LogFactory.getLog(UserSyncDao.class);
	
	
	/**
	 * 查询详情
	 */
	UserSync queryDetail(UserSync userSync);

	/**
	 * 查询列表
	 */
	List<UserSync> queryList(UserSync userSync);

	/**
     * 查询列表(分页)
     */
    List<UserSync> queryListPage(HashMap<String, Object> map);
    
    /**
     * 查询分页总数
     */
    String queryPageCounts(HashMap<String, Object> map);
    
	/**
	 * REPLACE：corpid和userid为唯一索引，有则更新，无则插入
	 * @param userSync
	 * @return
	 */
	int replaceDingTalkUser(UserSync userSync);
	
	/**
	 * 删除
	 * @param userSync
	 * @return
	 */
	int deleteDingTalkUser(UserSync userSync);

	/**
	 * 根据usernumber查询userid
	 */
	UserSync queryUseridByUsernumber(UserSync userSync);
	
	
}
