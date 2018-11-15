package com.centit.server.dingtalk.manager.po;

import java.io.Serializable;

import javax.persistence.Entity;

/**
 * <p>钉钉人员实体类<p>
 * @version 1.0
 * @author li_hao
 * @date 2018年4月23日
 */
@Entity
public class UserSync implements Serializable {

	private static final long serialVersionUID = -5874796420221364067L;

	
	private String id;
	private String corpid;  //授权企业corpid
	private String userid;  //用户id
	private String usernumber;  //数梦平台用户id
	private String password;  //密码
	private String username;  //用户名
	private String mobile;  //手机号
	private String avatar;  //头像URL
	private String workplace;  //工作地点
	private String tel;  //办公电话
	private String remark;  //备注
	private String position;  //职位
	private String jobnumber;  //员工工号
	private String createuserid;  //创建人userid
	private String createtime;  //创建时间
	private String updateuserid;  //更新人userid
	private String updatetime;  //更新时间
	
	private String m2faceurl;  //M2照片Url
	private String m2faceflag;  //是否上传M2照片：0上传，1未上传
	
	
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
	public String getUsernumber() {
		return usernumber;
	}
	public void setUsernumber(String usernumber) {
		this.usernumber = usernumber;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getWorkplace() {
		return workplace;
	}
	public void setWorkplace(String workplace) {
		this.workplace = workplace;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getJobnumber() {
		return jobnumber;
	}
	public void setJobnumber(String jobnumber) {
		this.jobnumber = jobnumber;
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
	public String getM2faceurl() {
		return m2faceurl;
	}
	public void setM2faceurl(String m2faceurl) {
		this.m2faceurl = m2faceurl;
	}
	public String getM2faceflag() {
		return m2faceflag;
	}
	public void setM2faceflag(String m2faceflag) {
		this.m2faceflag = m2faceflag;
	}
	@Override
	public String toString() {
		return "UserSync [id=" + id + ", corpid=" + corpid + ", userid=" + userid + ", usernumber=" + usernumber
				+ ", password=" + password + ", username=" + username + ", mobile=" + mobile + ", avatar=" + avatar
				+ ", workplace=" + workplace + ", tel=" + tel + ", remark=" + remark + ", position=" + position
				+ ", jobnumber=" + jobnumber + ", createuserid=" + createuserid + ", createtime=" + createtime
				+ ", updateuserid=" + updateuserid + ", updatetime=" + updatetime + ", m2faceurl=" + m2faceurl
				+ ", m2faceflag=" + m2faceflag + "]";
	}
	
	 
	
}
