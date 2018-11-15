package com.centit.server.webmgr.device.po;

import java.io.Serializable;

import javax.persistence.Entity;

/**
 * <p>文件po类<p>
 * @version 1.0
 * @author li_hao
 * @date 2017年6月24日
 */
@Entity
public class Asset implements Serializable {
	
	private static final long serialVersionUID = 768167780065754438L;
	
	
	private String id;  //标识
	private String corpid;  //标识
	private String mid;  //xx标识
    private String media_id;  //文件上传后的标识
    private String filename;  //文件名称
    private String file_type;  //xx文件类型  0:xx资料；1：xx纪要
    private String createtime;  //上传时间
    private String spaceid;  //钉盘空间id
    
    private String storepath;  //存储路径
    private String filesize;  //文件大小
    private String fileformat;  //文件格式
    private String createuserid;  //创建人

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
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getMedia_id() {
		return media_id;
	}
	public void setMedia_id(String media_id) {
		this.media_id = media_id;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getFile_type() {
		return file_type;
	}
	public void setFile_type(String file_type) {
		this.file_type = file_type;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getSpaceid() {
		return spaceid;
	}
	public void setSpaceid(String spaceid) {
		this.spaceid = spaceid;
	}
	public String getStorepath() {
		return storepath;
	}
	public void setStorepath(String storepath) {
		this.storepath = storepath;
	}
	public String getFilesize() {
		return filesize;
	}
	public void setFilesize(String filesize) {
		this.filesize = filesize;
	}
	public String getFileformat() {
		return fileformat;
	}
	public void setFileformat(String fileformat) {
		this.fileformat = fileformat;
	}
	public String getCreateuserid() {
		return createuserid;
	}
	public void setCreateuserid(String createuserid) {
		this.createuserid = createuserid;
	}
	@Override
	public String toString() {
		return "Asset [id=" + id + ", corpid=" + corpid + ", mid=" + mid + ", media_id=" + media_id + ", filename="
				+ filename + ", file_type=" + file_type + ", createtime=" + createtime + ", spaceid=" + spaceid
				+ ", storepath=" + storepath + ", filesize=" + filesize + ", fileformat=" + fileformat
				+ ", createuserid=" + createuserid + "]";
	}




}
