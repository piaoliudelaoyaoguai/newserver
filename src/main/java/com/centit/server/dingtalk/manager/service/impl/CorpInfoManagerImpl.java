package com.centit.server.dingtalk.manager.service.impl;/**
 * Created by li_hao on 2017/6/10.
 */

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import com.centit.server.dingtalk.manager.dao.CorpInfoDao;
import com.centit.server.dingtalk.manager.po.CorpInfo;
import com.centit.server.dingtalk.manager.service.CorpInfoManager;
import java.util.List;

import javax.annotation.Resource;

/**
 * @version : 1.0
 * @Author : li_hao
 * @Description : 企业授权信息
 * @Date : 2017-06-10 10:38
 **/
@Service
public class CorpInfoManagerImpl implements CorpInfoManager {
	
	public static final Log log = LogFactory.getLog(CorpInfoManager.class);

	@Resource
	private CorpInfoDao corpInfoDao;

	@Override
	public List<CorpInfo> queryList(CorpInfo corpInfo) {
		return corpInfoDao.queryList(corpInfo);
	}

	@Override
	public CorpInfo queryOne(CorpInfo corpInfo) {
		return corpInfoDao.queryOne(corpInfo);
	}

	@Override
	public int insertCorpInfo(CorpInfo corpInfo) {
		return corpInfoDao.insertCorpInfo(corpInfo);
	}

	@Override
	public int updateCorpInfo(CorpInfo corpInfo) {
		return corpInfoDao.updateCorpInfo(corpInfo);
	}

}
