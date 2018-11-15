package com.centit.server.webmgr.device.dao;/**
 * Created by li_hao on 2017/6/9.
 */

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.centit.server.webmgr.device.po.UserFace;



/**
 * <p>人员-人脸照片<p>
 * @version 1.0
 * @author li_hao
 * @date 2018年3月1日
 */
@Repository
public interface UserFaceDao {
	public static final Log log = LogFactory.getLog(UserFaceDao.class);

	/**
	 * 查询详情
	 */
	UserFace getUserFaceDetail(UserFace userFace);

	/**
	 * 查询列表
	 */
	List<UserFace> getUserFaceList(UserFace userFace);
	
	/**
	 * 插入
	 */
    int insertUserFace(UserFace userFace);
    
    /**
     * 更新
     */
    int updateUserFace(UserFace userFace);
    
    /**
     * 删除
     */
    int deleteUserFace(UserFace userFace);
    
    /**
     * REPLACE：corpid和userid为唯一索引，有则更新，无则插入
     */
    int replaceUserFace(UserFace userFace);
    
    
}
