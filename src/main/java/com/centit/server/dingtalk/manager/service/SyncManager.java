package com.centit.server.dingtalk.manager.service;

import com.alibaba.fastjson.JSONObject;

/**
 * <p>通讯录同步<p>
 * @version 1.0
 * @author li_hao
 * @date 2018年8月17日
 */
public interface SyncManager {

	/**
     * 同步所有单位
     */
    public JSONObject syncAllDepts(JSONObject reqJson);
	
    /**
     * 同步本单位下所有人员
     */
    public JSONObject syncThisDeptUsers(JSONObject reqJson);

    /**
     * 同步本单位及所有下级子单位的所有人员
     */
    public JSONObject syncDeptAndChildsUsers(JSONObject reqJson);
}
