package com.centit.server.dingtalk.manager.po;

import java.io.Serializable;

import javax.persistence.Entity;

/**
 * <p>钉钉部门实体类<p>
 * @version 1.0
 * @author li_hao
 * @date 2018年4月23日
 */
@Entity
public class DeptSync implements Serializable {

	private static final long serialVersionUID = -8713243015232936686L;

	
	private String id;
	private String corpid;  //授权企业corpid
	private String deptid;  //部门id
	private String deptnumber;  //数梦平台部门id
	private String deptname;  //部门名称
	private String parentid;  //父级部门id
	private String sort;  //部门排序号
	private String createuserid;  //创建人userid
	private String createtime;  //创建时间
	private String updateuserid;  //更新人userid
	private String updatetime;  //更新时间
	
	
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
	public String getDeptid() {
		return deptid;
	}
	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}
	public String getDeptnumber() {
		return deptnumber;
	}
	public void setDeptnumber(String deptnumber) {
		this.deptnumber = deptnumber;
	}
	public String getDeptname() {
		return deptname;
	}
	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}
	public String getParentid() {
		return parentid;
	}
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getCreateuserid() {
		return createuserid;
	}
	public void setCreateuserid(String createuserid) {
		this.createuserid = createuserid;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getUpdateuserid() {
		return updateuserid;
	}
	public void setUpdateuserid(String updateuserid) {
		this.updateuserid = updateuserid;
	}
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	@Override
	public String toString() {
		return "DeptSync [id=" + id + ", corpid=" + corpid + ", deptid=" + deptid + ", deptnumber=" + deptnumber
				+ ", deptname=" + deptname + ", parentid=" + parentid + ", sort=" + sort + ", createuserid="
				+ createuserid + ", createtime=" + createtime + ", updateuserid=" + updateuserid + ", updatetime="
				+ updatetime + "]";
	}
	 
	
}
