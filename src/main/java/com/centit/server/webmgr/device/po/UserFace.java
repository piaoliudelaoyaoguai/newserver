package com.centit.server.webmgr.device.po;

import java.io.Serializable;

import javax.persistence.Entity;

/**
 * <p>人员-人脸照片<p>
 * @version 1.0
 * @author li_hao
 * @date 2018年3月1日
 */
@Entity
public class UserFace implements Serializable {
	
	private static final long serialVersionUID = -5624391613921971027L;

	
	private String id;  //标识
	private String corpid;  //企业id
	private String userid;  //用户id
    private String faceassetid;  //人脸照片文件id
    private String createtime;  //上传时间
    private String updatetime;  //更新时间
    private String deleteflag;  //删除状态：0未删除；1删除
    
    private String storepath;  //照片存储路径
    
    
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
	public String getFaceassetid() {
		return faceassetid;
	}
	public void setFaceassetid(String faceassetid) {
		this.faceassetid = faceassetid;
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
	public String getDeleteflag() {
		return deleteflag;
	}
	public void setDeleteflag(String deleteflag) {
		this.deleteflag = deleteflag;
	}
	public String getStorepath() {
		return storepath;
	}
	public void setStorepath(String storepath) {
		this.storepath = storepath;
	}
	@Override
	public String toString() {
		return "UserFace [id=" + id + ", corpid=" + corpid + ", userid=" + userid + ", faceassetid=" + faceassetid
				+ ", createtime=" + createtime + ", updatetime=" + updatetime + ", deleteflag=" + deleteflag
				+ ", storepath=" + storepath + "]";
	}
	
    
}
