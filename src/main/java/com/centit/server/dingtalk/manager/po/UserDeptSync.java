package com.centit.server.dingtalk.manager.po;

import java.io.Serializable;

import javax.persistence.Entity;

/**
 * <p>钉钉用户-部门关联 实体类<p>
 * @version 1.0
 * @author li_hao
 * @date 2018年4月23日
 */
@Entity
public class UserDeptSync implements Serializable {

	private static final long serialVersionUID = 4117618375112033028L;
	
	
	private String id;
	private String corpid;  //企业id
	private String userid;  //用户表id
	private String deptid;  //部门表id
	private String sort;  //用户在部门中的排序号
	
	
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
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getDeptid() {
		return deptid;
	}
	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	@Override
	public String toString() {
		return "UserDeptSync [id=" + id + ", corpid=" + corpid + ", userid=" + userid + ", deptid=" + deptid + ", sort="
				+ sort + "]";
	}
	
	
}
