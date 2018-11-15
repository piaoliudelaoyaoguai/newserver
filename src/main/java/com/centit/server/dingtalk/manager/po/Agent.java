package com.centit.server.dingtalk.manager.po;

import java.io.Serializable;

import javax.persistence.Entity;

/**
 * <p>套件企业--授权应用 类<p>
 * @version 1.0
 * @author li_hao
 * @date 2018年3月29日
 */
@Entity
public class Agent implements Serializable {

	private static final long serialVersionUID = -1130736006479006627L;
	
	
	private String id;  //主键
	private String corpid;  //企业corpid
	private String agentid;  //授权方应用id，agentId会在前端签名校验的时候使用到。
	private String agentname;  //授权方应用名字
	private String appid;  //套件中的应用id
	private String logourl;  //授权方应用头像
	private String adminlist;  //对此微应用有管理权限的管理员userid列表
	private String agenttype;  //应用类型：0应用；1服务窗应用
	private String enable;  //启用状态：0未启用；1启用
	private String createtime;  //授权企业corpid
	private String updatetime;  //授权企业corpid
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCorpid() {
		return corpid;
	}
	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}
	public String getAgentid() {
		return agentid;
	}
	public void setAgentid(String agentid) {
		this.agentid = agentid;
	}
	public String getAgentname() {
		return agentname;
	}
	public void setAgentname(String agentname) {
		this.agentname = agentname;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getLogourl() {
		return logourl;
	}
	public void setLogourl(String logourl) {
		this.logourl = logourl;
	}
	public String getAdminlist() {
		return adminlist;
	}
	public void setAdminlist(String adminlist) {
		this.adminlist = adminlist;
	}
	public String getAgenttype() {
		return agenttype;
	}
	public void setAgenttype(String agenttype) {
		this.agenttype = agenttype;
	}
	public String getEnable() {
		return enable;
	}
	public void setEnable(String enable) {
		this.enable = enable;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	@Override
	public String toString() {
		return "Agent [id=" + id + ", corpid=" + corpid + ", agentid=" + agentid + ", agentname=" + agentname
				+ ", appid=" + appid + ", logourl=" + logourl + ", adminlist=" + adminlist + ", agenttype=" + agenttype
				+ ", enable=" + enable + ", createtime=" + createtime + ", updatetime=" + updatetime + "]";
	}

	 
 
	
}
