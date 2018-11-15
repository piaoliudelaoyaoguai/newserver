package com.centit.server.dingtalk.manager.dao;/**
 * Created by li_hao on 2017/6/9.
 */

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import com.centit.server.dingtalk.manager.po.DingTalkSuite;

/**
 * <p>钉钉套件dao类<p>
 * @version 1.0
 * @author li_hao
 * @date 2017年6月13日
 */
@Repository("dingTalkSuiteDao")
public interface DingTalkSuiteDao {
	public static final Log log = LogFactory.getLog(DingTalkSuiteDao.class);

	/**
	 *查询列表
	 */
	List<DingTalkSuite> queryList(DingTalkSuite dingTalkSuite);
	
    /**
     *查询详情
     */
    DingTalkSuite queryOne(DingTalkSuite dingTalkSuite);
    
    /**
     * 更新数据
     */
    int update(DingTalkSuite dingTalkSuite);
    

}
