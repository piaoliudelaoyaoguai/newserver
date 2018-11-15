package com.centit.server.dingtalk.manager.po;

/**
 * <p>钉钉消息通用类<p>
 * @version 1.0
 * @author li_hao
 * @date 2017年7月6日
 */
public class NewsCommon {

	private String msgType;  //消息类型
	private String agentid;  //微应用agentid
	private String userids;  //推送用户id，多个用逗号分隔 (userid可以存在逗号间为空的值，例如： ,userid1,userid2,,userid4)
	private String deptids;  //推送部门id，多个用逗号分隔
	private String corpAccessToken;  //授权企业corpAccessToken
	private boolean toAllFlag;  //是否推送给全体成员
	
	
	
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getAgentid() {
		return agentid;
	}
	public void setAgentid(String agentid) {
		this.agentid = agentid;
	}
	public String getUserids() {
		return userids;
	}
	public void setUserids(String userids) {
		this.userids = userids;
	}
	public String getDeptids() {
		return deptids;
	}
	public void setDeptids(String deptids) {
		this.deptids = deptids;
	}
	public String getCorpAccessToken() {
		return corpAccessToken;
	}
	public void setCorpAccessToken(String corpAccessToken) {
		this.corpAccessToken = corpAccessToken;
	}
	public boolean isToAllFlag() {
		return toAllFlag;
	}
	public void setToAllFlag(boolean toAllFlag) {
		this.toAllFlag = toAllFlag;
	}
	@Override
	public String toString() {
		return "NewsCommon [msgType=" + msgType + ", agentid=" + agentid + ", userids=" + userids + ", deptids="
				+ deptids + ", corpAccessToken=" + corpAccessToken + ", toAllFlag=" + toAllFlag + "]";
	}
	
	
}
