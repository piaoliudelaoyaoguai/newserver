package com.centit.server.webmgr.device.po;

import java.io.Serializable;

import javax.persistence.Entity;

/**
 * <p>钉钉智能硬件<p>
 * @version 1.0
 * @author li_hao
 * @date 2018年3月22日
 */
@Entity
public class Device implements Serializable {
	
	private static final long serialVersionUID = -4680367194700238172L;
	
	
	private String id;  //标识
	private String corpid;  //企业标识
	private String deviceid;  //设备id
	private String nick;  //设备昵称
    private String avatar;  //设备头像
    private String devicetypename;  //设备类型名称
    private String devicetype;  //设备类型
    private String deviceserviceid;  //设备产品类型 产品编码：M1：9 C1：14 M2：15 D1：24
    private String mark;  //备注
    private String createtime;  //创建时间
    private String updatetime;  //更新时间
    
    private String roomid;  //xxxid
    private String roomname;  //xxx名称
    
    private String nodeid;  //虚拟节点主键id
    private String deptid;  //虚拟节点钉钉id
    private String deptname;  //虚拟节点名称
    
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
	public String getDeviceid() {
		return deviceid;
	}
	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getDevicetypename() {
		return devicetypename;
	}
	public void setDevicetypename(String devicetypename) {
		this.devicetypename = devicetypename;
	}
	public String getDevicetype() {
		return devicetype;
	}
	public void setDevicetype(String devicetype) {
		this.devicetype = devicetype;
	}
	public String getDeviceserviceid() {
		return deviceserviceid;
	}
	public void setDeviceserviceid(String deviceserviceid) {
		this.deviceserviceid = deviceserviceid;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
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
	public String getRoomid() {
		return roomid;
	}
	public void setRoomid(String roomid) {
		this.roomid = roomid;
	}
	public String getRoomname() {
		return roomname;
	}
	public void setRoomname(String roomname) {
		this.roomname = roomname;
	}
	public String getNodeid() {
		return nodeid;
	}
	public void setNodeid(String nodeid) {
		this.nodeid = nodeid;
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
	@Override
	public String toString() {
		return "Device [id=" + id + ", corpid=" + corpid + ", deviceid=" + deviceid + ", nick=" + nick + ", avatar="
				+ avatar + ", devicetypename=" + devicetypename + ", devicetype=" + devicetype + ", deviceserviceid="
				+ deviceserviceid + ", mark=" + mark + ", createtime=" + createtime + ", updatetime=" + updatetime
				+ ", roomid=" + roomid + ", roomname=" + roomname + ", nodeid=" + nodeid + ", deptid=" + deptid
				+ ", deptname=" + deptname + "]";
	}


    

}
