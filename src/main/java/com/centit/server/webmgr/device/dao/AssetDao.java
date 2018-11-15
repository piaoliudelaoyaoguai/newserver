package com.centit.server.webmgr.device.dao;/**
 * Created by li_hao on 2017/6/9.
 */

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.centit.server.webmgr.device.po.Asset;


/**
 * @version : 1.0
 * @Author : li_hao
 * @Description : 资料Dao类
 * @Date : 2017-06-09 18:52
 **/
@Repository
public interface AssetDao {
	public static final Log log = LogFactory.getLog(AssetDao.class);

    
    /**
     * 新增
     */
    int insertAsset(Asset asset);
    
    /**
     * 查询列表
     */
    List<Asset> queryAssetList(Asset asset);
    
    /**
     * 删除
     */
    int deleteAsset(Asset asset);
    
    
    
}
