package com.centit.server.dingtalk.manager.dao;/**
 * Created by li_hao on 2017/6/9.
 */

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import com.centit.server.dingtalk.manager.po.Agent;

/**
 * <p>套件企业--授权应用 dao<p>
 * @version 1.0
 * @author li_hao
 * @date 2018年3月29日
 */
@Repository("agentDao")
public interface AgentDao {
	public static final Log log = LogFactory.getLog(AgentDao.class);
	
	
	/**
	 * 查询列表
	 * @param agent
	 * @return
	 */
	List<Agent> queryList(Agent agent);
	
	/**
	 * 查询详情
	 * @param agent
	 * @return
	 */
	Agent queryOne(Agent agent);
    
    /**
     * 插入
     * @param agent
     * @return
     */
    int insertAgent(Agent agent);
    
    
    /**
     * 更新
     * @param agent
     * @return
     */
    int updateAgent(Agent agent);
    
    
    /**
     * 删除
     * @param agent
     * @return
     */
    int deleteAgent(Agent agent);

    /**
     * REPLACE：corpid和agentid为唯一索引，有则更新，无则插入
     * @param agent
     * @return
     */
    int replaceAgent(Agent agent);



}
