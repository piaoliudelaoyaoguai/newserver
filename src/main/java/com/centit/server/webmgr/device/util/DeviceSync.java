package com.centit.server.webmgr.device.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.centit.server.dingtalk.manager.uitls.CommonInit;
import com.centit.server.dingtalk.manager.uitls.DingTalkUtil;
import com.centit.server.webmgr.device.po.Device;

/**
 * <p>钉钉智能设备同步<p>
 * @version 1.0
 * @author li_hao
 * @date 2018年3月27日
 */
@Transactional
public class DeviceSync {

	/**
	 * 钉钉智能设备同步
	 * @param corpid
	 * @param accessToken
	 */
	@Transactional(rollbackFor = Exception.class)
	public void deviceSync(String corpid, String accessToken){
		try {
//        	Long deviceServiceIds[] = {9L, 14L, 15L, 24L};  // 设备产品类型 产品编码：M1：9 C1：14 M2：15 D1：24
        	Long deviceServiceIds[] = {15L};  // 设备产品类型 产品编码：M1：9 C1：14 M2：15 D1：24
        	
        	for(Long deviceServiceId : deviceServiceIds){  //遍历所有设备类型
     			JSONArray jsonArrayOneTypeAll = new JSONArray();  //单一类型所有设备
     			Long cursor = 0L;
	    		while(cursor != null) {  //递归查询单一类型设备全部列表
	    			JSONObject jsonDeviceList = DingTalkUtil.queryDeviceList(accessToken, deviceServiceId, cursor, 20L);//循环内容
	    			JSONObject respJson = jsonDeviceList.getJSONObject("dingtalk_corp_device_manage_querylist_response"); //返回
	    			if(respJson != null){
	    				JSONObject result = respJson.getJSONObject("result").getJSONObject("result");  //返回的Page对象
		    			JSONArray deviceList = result.getJSONObject("list").getJSONArray("open_device_vo");  //此次查询设备列表
		    			cursor = result.getLong("next_cursor");  //下次拉取列表的游标，如果为Null，代表没有数据了
		    			if(deviceList != null){
		    				jsonArrayOneTypeAll.addAll(deviceList);
		    			}
	    			}
	    		}
	    		
	    		Device de = new Device();
	    		de.setCorpid(corpid);
	    		de.setDeviceserviceid(String.valueOf(deviceServiceId));
	    		List<Device> deviceListOld = CommonInit.deviceDao.queryList(de);  //数据库中数据（旧）
	    		List<String> deviceIdListOld = new ArrayList<>(); //数据库中数据（旧）的deviceId列表
	    		for(Device d : deviceListOld){
	    			deviceIdListOld.add(d.getDeviceid());
	    		}
	    		
	    		List<String> sameDeviceIdList = new ArrayList<>(); //新旧列表相同的交集deviceId
	    		//遍历jsonArrayOneTypeAll
	    		for(int i=0; i<jsonArrayOneTypeAll.size(); i++){
	    			JSONObject deviceJsonObject = jsonArrayOneTypeAll.getJSONObject(i);
	    			String deviceId = deviceJsonObject.getString("device_id");  //设备id
	    			if(deviceIdListOld.contains(deviceId)){  //如果数据库中数据（旧）的deviceId列表中含有新查出的设备id
	    				sameDeviceIdList.add(deviceId);
	    			}
	    		}
	    		//删除
	    		for(Device deviceOld : deviceListOld){
	    			if(!sameDeviceIdList.contains(deviceOld.getDeviceid())){
	    				CommonInit.deviceDao.deleteDevice(deviceOld);  //删除设备表
	    				CommonInit.deviceDao.deleteMeetingRoomDevice(deviceOld);  //删除设备-xxx关联表
	    				CommonInit.deviceDao.deleteDeviceM2Node(deviceOld);  //删除设备-虚拟节点关联表
	    			}
	    		}
	    		//新增
	    		for(int i=0; i<jsonArrayOneTypeAll.size(); i++){
	    			JSONObject deviceJsonObject = jsonArrayOneTypeAll.getJSONObject(i);
    				Device device = new Device();
	     			device.setCorpid(corpid);
	     			device.setDeviceid(deviceJsonObject.getString("device_id"));
	     			device.setNick(deviceJsonObject.getString("nick"));
	     			device.setAvatar(deviceJsonObject.getString("avatar"));
	     			device.setDevicetypename(deviceJsonObject.getString("device_type_name"));
	     			device.setDeviceserviceid(String.valueOf(deviceServiceId));
	     			CommonInit.deviceDao.replaceDevice(device);
	    		}
     		}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	
	
	
	
	
}
