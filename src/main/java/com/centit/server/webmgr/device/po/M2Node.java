package com.centit.server.webmgr.device.po;

import java.io.Serializable;

import javax.persistence.Entity;

/**
 * <p>M2人脸识别虚拟节点<p>
 * @version 1.0
 * @author li_hao
 * @date 2018年7月9日
 */
@Entity
public class M2Node implements Serializable {
	
	private static final long serialVersionUID = -7987244503591718999L;
	
	private String id;  //标识
	private String corpid;  //企业标识
	private String deptid;  //节点（部门）id
	private String deptname;  //节点（部门）名称
    private String parentid;  //父节点（父部门）id
    private String createuserid;
    private String createtime;
    private String updateuserid;
    private String updatetime;
    private String sn;  //排序号
    private String mark;  //备用字段
    
    
    
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
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	@Override
	public String toString() {
		return "Node [id=" + id + ", corpid=" + corpid + ", deptid=" + deptid + ", deptname=" + deptname + ", parentid="
				+ parentid + ", createuserid=" + createuserid + ", createtime=" + createtime + ", updateuserid="
				+ updateuserid + ", updatetime=" + updatetime + ", sn=" + sn + ", mark=" + mark + "]";
	}
	

}
