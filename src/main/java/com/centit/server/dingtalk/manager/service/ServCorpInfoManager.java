package com.centit.server.dingtalk.manager.service;

import com.centit.server.dingtalk.manager.po.ServCorpInfo;
/**
 * @version : 1.0
 * @Author : li_hao
 * @Description : 企业信息
 * @Date : 2017-06-09 17:28
 **/
public interface ServCorpInfoManager {

	/**
	 * 查询详情
	 * @param servCorpInfo
	 * @return
	 */
	public ServCorpInfo queryOne(ServCorpInfo servCorpInfo);
    
    /**
     * 更新
     * @param servCorpInfo
     * @return
     */
    public int updateServCorpInfo(ServCorpInfo servCorpInfo);
    
    
}
