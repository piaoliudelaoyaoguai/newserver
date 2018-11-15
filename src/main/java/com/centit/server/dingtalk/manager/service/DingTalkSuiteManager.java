package com.centit.server.dingtalk.manager.service;

import com.centit.server.dingtalk.manager.po.DingTalkSuite;
/**
 * @version : 1.0
 * @Author : li_hao
 * @Description : 套件信息
 * @Date : 2017-06-09 17:28
 **/
public interface DingTalkSuiteManager {

	/**
     *查询详情
     */
    public DingTalkSuite queryOne(DingTalkSuite dingTalkSuite);
    
    /**
     * 更新数据
     */
    public int update(DingTalkSuite dingTalkSuite);
}
