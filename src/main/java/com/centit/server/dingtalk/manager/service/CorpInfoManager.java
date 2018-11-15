package com.centit.server.dingtalk.manager.service;

import java.util.List;
import com.centit.server.dingtalk.manager.po.CorpInfo;
/**
 * @version : 1.0
 * @Author : li_hao
 * @Description : 企业授权信息
 * @Date : 2017-06-09 17:28
 **/
public interface CorpInfoManager {

	/**
	 * 查询列表
	 * @param corpInfo
	 * @return
	 */
	public List<CorpInfo> queryList(CorpInfo corpInfo);
	
	/**
	 * 查询详情
	 * @param corpInfo
	 * @return
	 */
	public CorpInfo queryOne(CorpInfo corpInfo);
    
    /**
     * 插入企业授权信息
     * @param corpInfo
     * @return
     */
	public int insertCorpInfo(CorpInfo corpInfo);
    
    
    /**
     * 更新企业授权信息
     * @param corpInfo
     * @return
     */
	public int updateCorpInfo(CorpInfo corpInfo);
}
