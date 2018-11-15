package com.centit.server.webmgr.device.dao;/**
 * Created by li_hao on 2017/6/9.
 */

import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import com.centit.server.webmgr.device.po.M2Node;


/**
 * <p>M2人脸识别虚拟节点<p>
 * @version 1.0
 * @author li_hao
 * @date 2018年7月9日
 */
@Repository
public interface M2NodeDao {
	public static final Log log = LogFactory.getLog(M2NodeDao.class);
	
	/**
	 * M2虚拟节点(单位)分页列表
	 */
	List<M2Node> queryM2NodeListPage(HashMap<String, Object> map);

	/**
	 * M2虚拟节点(单位)分页列表总数
	 */
	int queryM2NodeListPageCount(HashMap<String, Object> map);
	
	/**
	 * 新增M2虚拟节点(单位)（M2人脸识别）
	 */
	int addM2Node(M2Node node);
    
	/**
	 * 修改M2虚拟节点(单位)（M2人脸识别）
	 */
	int updateM2Node(M2Node node);
    
	/**
	 * 删除M2虚拟节点(单位)（M2人脸识别）
	 */
	int deleteM2Node(M2Node node);
    
}
