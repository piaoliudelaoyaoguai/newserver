package com.centit.server.dingtalk.manager.po;

import com.alibaba.fastjson.JSONArray;

/**
 * <p>钉钉OA类型消息实体类<p>
 * @version 1.0
 * @author li_hao
 * @date 2017年7月6日
 */
public class OANews {
	
	private String message_url;  //客户端点击消息时跳转到的H5地址
	private String pc_message_url;  //	PC端点击消息时跳转到的URL地址
	private String head_bgcolor;  //  消息头部的背景颜色。长度限制为8个英文字符，其中前2为表示透明度，后6位表示颜色值。不要添加0x
	private String head_text;  //  消息头部标题
	private String body_title;  //  正文标题
	private JSONArray body_form;  //  消息体的表单，最多显示6个，超过会被隐藏
	private String body_rich_num;  //  单行富文本信息的数目
	private String body_rich_unit;  //  单行富文本信息的单位
	private String body_content;  //  消息体的内容，最多显示3行
	private String body_image;  //  消息体中的图片media_id
	private String body_file_count;  //  自定义的附件数目。此数字仅供显示，钉钉不作验证
	private String body_author;  //  自定义的作者名字
	
	
	public String getMessage_url() {
		return message_url;
	}
	public void setMessage_url(String message_url) {
		this.message_url = message_url;
	}
	public String getPc_message_url() {
		return pc_message_url;
	}
	public void setPc_message_url(String pc_message_url) {
		this.pc_message_url = pc_message_url;
	}
	public String getHead_bgcolor() {
		return head_bgcolor;
	}
	public void setHead_bgcolor(String head_bgcolor) {
		this.head_bgcolor = head_bgcolor;
	}
	public String getHead_text() {
		return head_text;
	}
	public void setHead_text(String head_text) {
		this.head_text = head_text;
	}
	public String getBody_title() {
		return body_title;
	}
	public void setBody_title(String body_title) {
		this.body_title = body_title;
	}
	public JSONArray getBody_form() {
		return body_form;
	}
	public void setBody_form(JSONArray body_form) {
		this.body_form = body_form;
	}
	public String getBody_rich_num() {
		return body_rich_num;
	}
	public void setBody_rich_num(String body_rich_num) {
		this.body_rich_num = body_rich_num;
	}
	public String getBody_rich_unit() {
		return body_rich_unit;
	}
	public void setBody_rich_unit(String body_rich_unit) {
		this.body_rich_unit = body_rich_unit;
	}
	public String getBody_content() {
		return body_content;
	}
	public void setBody_content(String body_content) {
		this.body_content = body_content;
	}
	public String getBody_image() {
		return body_image;
	}
	public void setBody_image(String body_image) {
		this.body_image = body_image;
	}
	public String getBody_file_count() {
		return body_file_count;
	}
	public void setBody_file_count(String body_file_count) {
		this.body_file_count = body_file_count;
	}
	public String getBody_author() {
		return body_author;
	}
	public void setBody_author(String body_author) {
		this.body_author = body_author;
	}
	@Override
	public String toString() {
		return "OANews [message_url=" + message_url + ", pc_message_url=" + pc_message_url + ", head_bgcolor="
				+ head_bgcolor + ", head_text=" + head_text + ", body_title=" + body_title + ", body_form=" + body_form
				+ ", body_rich_num=" + body_rich_num + ", body_rich_unit=" + body_rich_unit + ", body_content="
				+ body_content + ", body_image=" + body_image + ", body_file_count=" + body_file_count
				+ ", body_author=" + body_author + "]";
	}

	
	
}
