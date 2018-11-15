package com.centit.server.webmgr.device.dao;/**
 * Created by li_hao on 2017/6/9.
 */

import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import com.centit.server.webmgr.device.po.Device;


/**
 * <p>钉钉智能硬件<p>
 * @version 1.0
 * @author li_hao
 * @date 2018年3月22日
 */
@Repository
public interface DeviceDao {
	public static final Log log = LogFactory.getLog(DeviceDao.class);
	
	/**
	 * 查询详情
	 */
	Device queryDetail(Device device);

	/**
     * 查询列表
     */
    List<Device> queryList(Device device);

    /**
     * 新增
     */
    int insertDevice(Device device);
    
    /**
     * 更新
     */
    int updateDevice(Device device);

    /**
     * 删除
     */
    int deleteDevice(Device device);
    
    /**
     * REPLACE：deviceid为唯一索引，有则更新，无则插入
     */
    int replaceDevice(Device device);
    
    /**
     * 查询设备-XXX关联列表
     */
    List<Device> queryDeviceMeetingRoomList(Device device);

    /**
     * M2查询可绑定的XXX列表（分页）
     */
    List<HashMap<String, Object>> queryCanBindMeetingRoomList(HashMap<String, Object> map);

    /**
     * M2查询可绑定的XXX列表总数（分页）
     */
    int queryCanBindMeetingRoomListCount(HashMap<String, Object> map);

    /**
     * XXX查询可绑定的M2列表
     */
    List<Device> queryCanBindM2List(HashMap<String, Object> map);
    
    /**
     * 查询设备是否已绑定XXX
     */
    int queryDeviceRoomBind(HashMap<String, Object> map);

    /**
     * 插入XXX-设备关联表
     */
    int insertDeviceMeetingRoom(HashMap<String, Object> map);

    /**
     * 删除XXX-设备关联表
     */
    int deleteMeetingRoomDevice(Device device);
    
    /**
     * 查询设备是否已绑定虚拟节点
     */
    int queryDeviceM2NodeBind(HashMap<String, Object> map);
    
    /**
     * 插入设备-xx虚拟节点表
     */
    int insertDeviceM2Node(HashMap<String, Object> map);
    
    /**
     * 删除XXX-虚拟节点关联表
     */
    int deleteDeviceM2Node(Device device);
    
    
    
    
    
    
}
