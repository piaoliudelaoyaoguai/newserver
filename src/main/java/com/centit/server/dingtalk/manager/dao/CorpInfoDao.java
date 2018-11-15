package com.centit.server.dingtalk.manager.dao;/**
 * Created by li_hao on 2017/6/9.
 */

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import com.centit.server.dingtalk.manager.po.CorpInfo;

/**
 * <p>企业授权信息dao<p>
 * @version 1.0
 * @author li_hao
 * @date 2017年6月15日
 */
@Repository("corpInfoDao")
public interface CorpInfoDao {
	public static final Log log = LogFactory.getLog(CorpInfoDao.class);
	
	
	/**
	 * 查询列表
	 * @param corpInfo
	 * @return
	 */
	List<CorpInfo> queryList(CorpInfo corpInfo);
	
	/**
	 * 查询详情
	 * @param corpInfo
	 * @return
	 */
	CorpInfo queryOne(CorpInfo corpInfo);
    
    /**
     * 插入企业授权信息
     * @param corpInfo
     * @return
     */
    int insertCorpInfo(CorpInfo corpInfo);
    
    
    /**
     * 更新企业授权信息
     * @param corpInfo
     * @return
     */
    int updateCorpInfo(CorpInfo corpInfo);
    
    
    /**
     * 删除企业授权信息
     * @param corpInfo
     * @return
     */
    int deleteCorpInfo(CorpInfo corpInfo);

    /**
     * REPLACE：corpid为唯一索引，有则更新，无则插入
     * @param corpInfo
     * @return
     */
    int replaceCorpInfo(CorpInfo corpInfo);



}
