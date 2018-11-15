package com.centit.server.dingtalk.manager.dao;/**
 * Created by li_hao on 2017/6/9.
 */

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import com.centit.server.dingtalk.manager.po.ServCorpInfo;

/**
 * <p>服务商企业信息实体类<p>
 * @version 1.0
 * @author li_hao
 * @date 2017年6月15日
 */
@Repository("servCorpInfoDao")
public interface ServCorpInfoDao {
	public static final Log log = LogFactory.getLog(ServCorpInfoDao.class);
	
	
	
	/**
	 * 查询详情
	 * @param servCorpInfo
	 * @return
	 */
	ServCorpInfo queryOne(ServCorpInfo servCorpInfo);
    
    /**
     * 更新
     * @param servCorpInfo
     * @return
     */
    int updateServCorpInfo(ServCorpInfo servCorpInfo);
}
