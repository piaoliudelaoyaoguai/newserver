package com.centit.server.webmgr.common;

import java.io.Serializable;

/**
 * <p>分页实体类<p>
 * @version 1.0
 * @author li_hao
 * @date 2018年6月4日
 */
public class PageEntity  implements Serializable {
	
	private static final long serialVersionUID = 8277663177812829201L;

	/** 查询页码：从1开始，以后每页加1 */
	private int pageNum;
	
	/** 每页数据数(最大为20) */
	private int pageSize;

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	@Override
	public String toString() {
		return "PageEntity [pageNum=" + pageNum + ", pageSize=" + pageSize + "]";
	}
	
	
}
