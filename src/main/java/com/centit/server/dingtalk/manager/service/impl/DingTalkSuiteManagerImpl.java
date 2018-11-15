package com.centit.server.dingtalk.manager.service.impl;/**
 * Created by li_hao on 2017/6/10.
 */

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import com.centit.server.dingtalk.manager.dao.DingTalkSuiteDao;
import com.centit.server.dingtalk.manager.po.DingTalkSuite;
import com.centit.server.dingtalk.manager.service.DingTalkSuiteManager;
import javax.annotation.Resource;

/**
 * @version : 1.0
 * @Author : li_hao
 * @Description : 套件信息
 * @Date : 2017-06-10 10:38
 **/
@Service
public class DingTalkSuiteManagerImpl implements DingTalkSuiteManager {
	
	public static final Log log = LogFactory.getLog(DingTalkSuiteManager.class);

	@Resource
	private DingTalkSuiteDao dingTalkSuiteDao;

	@Override
	public DingTalkSuite queryOne(DingTalkSuite dingTalkSuite) {
		return dingTalkSuiteDao.queryOne(dingTalkSuite);
	}

	@Override
	public int update(DingTalkSuite dingTalkSuite) {
		return dingTalkSuiteDao.update(dingTalkSuite);
	}

	
}
