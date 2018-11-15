package com.centit.server.webmgr.util;


public class SynchronizedException extends RuntimeException {
	
	private static final long serialVersionUID = 1028429824472617934L;
	private int status = 1;
	private String msg;
	
	public SynchronizedException(String msg) {
		super(msg);
		this.msg = msg;
	}
	public SynchronizedException(int status, String msg) {
		super(msg);
		this.status = status;
		this.msg = msg;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	

}
