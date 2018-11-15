package com.centit.server.dingtalk.manager.service.impl;/**
 * Created by li_hao on 2017/6/10.
 */

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.centit.server.dingtalk.manager.dao.ServCorpInfoDao;
import com.centit.server.dingtalk.manager.po.ServCorpInfo;
import com.centit.server.dingtalk.manager.service.ServCorpInfoManager;

/**
 * @version : 1.0
 * @Author : li_hao
 * @Description : 企业授权信息
 * @Date : 2017-06-10 10:38
 **/
@Service
public class ServCorpInfoManagerImpl implements ServCorpInfoManager {
	
	public static final Log log = LogFactory.getLog(ServCorpInfoManager.class);
	
	@Resource
	private ServCorpInfoDao servCorpInfoDao;

	@Override
	public ServCorpInfo queryOne(ServCorpInfo servCorpInfo) {
		return servCorpInfoDao.queryOne(servCorpInfo);
	}

	@Override
	public int updateServCorpInfo(ServCorpInfo servCorpInfo) {
		return servCorpInfoDao.updateServCorpInfo(servCorpInfo);
	}



}
