package com.centit.server.dingtalk.manager.po;

import java.io.Serializable;

import javax.persistence.Entity;

/**
 * <p>钉钉套件实体类<p>
 * @version 1.0
 * @author li_hao
 * @date 2017年6月13日
 */
@Entity
public class DingTalkSuite implements Serializable {
	private static final long serialVersionUID = -1934032518104204548L;

	 private String id;  //主键
	 private String suiteid;  //套件id
	 private String name;  //套件名称
	 private String token;  //注册套件的之时填写的token
	 private String encoding_aes_key;  //注册套件的之时生成的数据加密密钥
	 private String suite_key;  //套件注册成功后生成的suite_key
	 private String suite_secret;  //套件注册成功后生成的suite_secret
	 private String suit_ticket;  //钉钉后台推送的套件ticket
	 private String suite_access_token;  //获取套件访问Token（suite_access_token）
	 private String createtime;  //创建时间
	 private String updatetime;  //更新时间
	 
	 
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getSuiteid() {
		return suiteid;
	}
	public void setSuiteid(String suiteid) {
		this.suiteid = suiteid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getEncoding_aes_key() {
		return encoding_aes_key;
	}
	public void setEncoding_aes_key(String encoding_aes_key) {
		this.encoding_aes_key = encoding_aes_key;
	}
	public String getSuite_key() {
		return suite_key;
	}
	public void setSuite_key(String suite_key) {
		this.suite_key = suite_key;
	}
	public String getSuite_secret() {
		return suite_secret;
	}
	public void setSuite_secret(String suite_secret) {
		this.suite_secret = suite_secret;
	}
	public String getSuit_ticket() {
		return suit_ticket;
	}
	public void setSuit_ticket(String suit_ticket) {
		this.suit_ticket = suit_ticket;
	}
	public String getSuite_access_token() {
		return suite_access_token;
	}
	public void setSuite_access_token(String suite_access_token) {
		this.suite_access_token = suite_access_token;
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
		return "DingTalkSuite [id=" + id + ", suiteid=" + suiteid + ", name=" + name + ", token=" + token
				+ ", encoding_aes_key=" + encoding_aes_key + ", suite_key=" + suite_key + ", suite_secret="
				+ suite_secret + ", suit_ticket=" + suit_ticket + ", suite_access_token=" + suite_access_token
				+ ", createtime=" + createtime + ", updatetime=" + updatetime + "]";
	}

	
}
