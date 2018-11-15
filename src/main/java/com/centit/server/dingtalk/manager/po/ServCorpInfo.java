package com.centit.server.dingtalk.manager.po;

import java.io.Serializable;

import javax.persistence.Entity;

/**
 * <p>服务商企业信息实体类<p>
 * @version 1.0
 * @author li_hao
 * @date 2017年6月15日
 */
@Entity
public class ServCorpInfo implements Serializable {
	
	private static final long serialVersionUID = 2380483034874313868L;
	
	private String corpid;  //企业corpid
	private String corpsecret;  //企业corpsecret
	private String accesstoken;  //企业accesstoken
	private String jsapi_ticket;  //企业的jsapi_ticket
	private String updatetime;  //修改时间
	
	
	public String getCorpid() {
		return corpid;
	}
	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}
	public String getCorpsecret() {
		return corpsecret;
	}
	public void setCorpsecret(String corpsecret) {
		this.corpsecret = corpsecret;
	}
	public String getAccesstoken() {
		return accesstoken;
	}
	public void setAccesstoken(String accesstoken) {
		this.accesstoken = accesstoken;
	}
	public String getJsapi_ticket() {
		return jsapi_ticket;
	}
	public void setJsapi_ticket(String jsapi_ticket) {
		this.jsapi_ticket = jsapi_ticket;
	}
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	@Override
	public String toString() {
		return "ServCorpInfo [corpid=" + corpid + ", corpsecret=" + corpsecret + ", accesstoken=" + accesstoken
				+ ", jsapi_ticket=" + jsapi_ticket + ", updatetime=" + updatetime + "]";
	}


	 
	
}
