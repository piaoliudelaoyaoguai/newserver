package com.centit.server.webmgr.device.service.impl;/**
 * Created by li_hao on 2017/6/10.
 */

import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.fastjson.JSONObject;
import com.centit.server.dingtalk.manager.uitls.CommonUtil;
import com.centit.server.dingtalk.manager.uitls.DingTalkUtil;
import com.centit.server.webmgr.device.dao.DeviceDao;
import com.centit.server.webmgr.device.po.Device;
import com.centit.server.webmgr.device.service.DeviceManager;
import com.centit.server.webmgr.device.util.DeviceSync;


/**
 * <p>钉钉智能设备<p>
 * @version 1.0
 * @author li_hao
 * @date 2018年4月19日
 */
@Service
@Transactional
public class DeviceManagerImpl implements DeviceManager {
	
	public static final Log log = LogFactory.getLog(DeviceManager.class);
	
	@Resource
	private DeviceDao deviceDao;

	/**
	 * 查询设备列表
	 */
	@Override
	public JSONObject queryDeviceList(JSONObject reqJson) {
		JSONObject rsp_json = new JSONObject();
		String retCode = "1";
		String retMsg = "操作失败！";
		JSONObject bizData_json = new JSONObject();
		try {
			String corpid = reqJson.getString("corpid");
			if(!"".equals(corpid) && corpid!=null){
				
				//每次查询列表前都先进行设备同步
				String accessToken = CommonUtil.getAccessToken(corpid);
				new DeviceSync().deviceSync(corpid, accessToken);  //钉钉智能设备同步
				
				Device device = new Device();
				device.setCorpid(corpid);
				device.setDeviceserviceid("15");
				List<Device> deviceMeetingRoomList = deviceDao.queryDeviceMeetingRoomList(device);
				bizData_json.put("deviceList", deviceMeetingRoomList);
				
				retCode = "0";
				retMsg = "操作成功！";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		rsp_json.put("retCode", retCode);
		rsp_json.put("retMsg", retMsg);
		rsp_json.put("bizData", bizData_json);
		return rsp_json;
	}

	/**
	 * 修改设备名称
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject updateDeviceName(JSONObject reqJson) {
		JSONObject rsp_json = new JSONObject();
		String retCode = "1";
		String retMsg = "操作失败！";
		JSONObject bizData_json = new JSONObject();
		try {
			String corpid = reqJson.getString("corpid");
			if(!"".equals(corpid) && corpid!=null){
				String did = reqJson.getString("did");
				String deviceid = reqJson.getString("deviceid");
				String deviceserviceid = reqJson.getString("deviceserviceid");
				String nick = reqJson.getString("nick");
				
				//查询设备详情，判断是否有设备（因设备同步可能存在延迟的情况）
				String accessToken = CommonUtil.getAccessToken(corpid);
				JSONObject jsonDeviceDetail = DingTalkUtil.queryDeviceDetail(accessToken, Long.valueOf(deviceserviceid), deviceid);  //查询设备详情
				String deviceDetailRespCode = jsonDeviceDetail.getJSONObject("dingtalk_corp_device_manage_get_response").getJSONObject("result").getString("ding_open_errcode");
				if("0".equals(deviceDetailRespCode)){
					JSONObject jsonObject = DingTalkUtil.updateDeviceName(accessToken, Long.valueOf(deviceserviceid), deviceid, nick);  //更新设备名称
					String respCode = jsonObject.getJSONObject("dingtalk_corp_device_nick_update_response").getJSONObject("result").getString("ding_open_errcode");
					if("0".equals(respCode)){
						Device device = new Device();
						device.setId(did);
						device.setCorpid(corpid);
						device.setDeviceid(deviceid);
						device.setNick(nick);
						deviceDao.updateDevice(device);
						
						retCode = "0";
						retMsg = "操作成功！";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		rsp_json.put("retCode", retCode);
		rsp_json.put("retMsg", retMsg);
		rsp_json.put("bizData", bizData_json);
		return rsp_json;
	}

	/**
	 * 设备解绑（设备删除）
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject unbindDevice(JSONObject reqJson) {
		JSONObject rsp_json = new JSONObject();
		String retCode = "1";
		String retMsg = "操作失败！";
		JSONObject bizData_json = new JSONObject();
		try {
			String corpid = reqJson.getString("corpid");
			if(!"".equals(corpid) && corpid!=null){
				String did = reqJson.getString("did");
				String deviceid = reqJson.getString("deviceid");
				String deviceserviceid = reqJson.getString("deviceserviceid");
				
				//查询设备详情，判断是否有设备（因设备同步可能存在延迟的情况）
				String accessToken = CommonUtil.getAccessToken(corpid);
				JSONObject jsonDeviceDetail = DingTalkUtil.queryDeviceDetail(accessToken, Long.valueOf(deviceserviceid), deviceid);  //查询设备详情
				String deviceDetailRespCode = jsonDeviceDetail.getJSONObject("dingtalk_corp_device_manage_get_response").getJSONObject("result").getString("ding_open_errcode");
				if("0".equals(deviceDetailRespCode)){
					
					JSONObject jsonObject = DingTalkUtil.unbindDevice(accessToken, Long.valueOf(deviceserviceid), deviceid);  //设备解绑
					String respCode = jsonObject.getJSONObject("dingtalk_corp_device_manage_unbind_response").getJSONObject("result").getString("ding_open_errcode");
					if("0".equals(respCode)){
						Device device = new Device();
						device.setId(did);
						deviceDao.deleteDevice(device);  //删除设备表
						
						//删除设备-XXX关联表
						Device de = new Device();
						de.setCorpid(corpid);
						de.setDeviceid(deviceid);
						deviceDao.deleteMeetingRoomDevice(de);  //删除设备-XXX关联表
						
						retCode = "0";
						retMsg = "操作成功！";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		rsp_json.put("retCode", retCode);
		rsp_json.put("retMsg", retMsg);
		rsp_json.put("bizData", bizData_json);
		return rsp_json;
	}

	/**
	 * M2查询可绑定的XXX列表(分页)
	 */
	@Override
	public JSONObject queryCanBindMeetingRoomList(JSONObject reqJson) {
		JSONObject rsp_json = new JSONObject();
		String retCode = "1";
		String retMsg = "操作失败！";
		JSONObject bizData_json = new JSONObject();
		try {
			String corpid = reqJson.getString("corpid");
			if(!"".equals(corpid) && corpid!=null){
				String deptid = reqJson.getString("deptid");
				String queryTerm = reqJson.getString("queryTerm");
				String pageNum = reqJson.getString("pageNum");
				String pageSize = reqJson.getString("pageSize");
				
				HashMap<String, Object> reqMap = new HashMap<>();
				reqMap.put("corpid", corpid);
				reqMap.put("deptid", deptid);
				reqMap.put("queryTerm", queryTerm);
				reqMap.put("pageNum", (Integer.valueOf(pageNum)-1)*Integer.valueOf(pageSize));
				reqMap.put("pageSize", Integer.valueOf(pageSize));
				
				List<HashMap<String,Object>> meetingRoomList = deviceDao.queryCanBindMeetingRoomList(reqMap);
				int total = deviceDao.queryCanBindMeetingRoomListCount(reqMap);
				bizData_json.put("meetingRoomList", meetingRoomList);
				bizData_json.put("total", total);
				
				retCode = "0";
				retMsg = "操作成功！";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		rsp_json.put("retCode", retCode);
		rsp_json.put("retMsg", retMsg);
		rsp_json.put("bizData", bizData_json);
		return rsp_json;
	}

	/**
	 * XXX查询可绑定的M2列表
	 */
	@Override
	public JSONObject queryCanBindM2List(JSONObject reqJson) {
		JSONObject rsp_json = new JSONObject();
		String retCode = "1";
		String retMsg = "操作失败！";
		JSONObject bizData_json = new JSONObject();
		try {
			String corpid = reqJson.getString("corpid");
			if(!"".equals(corpid) && corpid!=null){
				String nick = reqJson.getString("nick");
				
				HashMap<String, Object> map = new HashMap<>();
				map.put("corpid", corpid);
				map.put("nick", nick);
				
				List<Device> m2List = deviceDao.queryCanBindM2List(map);
				bizData_json.put("m2List", m2List);
				
				retCode = "0";
				retMsg = "操作成功！";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		rsp_json.put("retCode", retCode);
		rsp_json.put("retMsg", retMsg);
		rsp_json.put("bizData", bizData_json);
		return rsp_json;
	}

	/**
	 * M2设备绑定XXX
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public synchronized JSONObject deviceBindingMeetingRoom(JSONObject reqJson) {
		JSONObject rsp_json = new JSONObject();
		String retCode = "1";
		String retMsg = "操作失败！";
		JSONObject bizData_json = new JSONObject();
		try {
			String corpid = reqJson.getString("corpid");
			if(!"".equals(corpid) && corpid!=null){
				String roomid = reqJson.getString("roomid");
				String deviceid = reqJson.getString("deviceid");
				String deviceserviceid = reqJson.getString("deviceserviceid");
				
				//查询设备详情，判断是否有设备（因设备同步可能存在延迟的情况）
				String accessToken = CommonUtil.getAccessToken(corpid);
				JSONObject jsonDeviceDetail = DingTalkUtil.queryDeviceDetail(accessToken, Long.valueOf(deviceserviceid), deviceid);  //查询设备详情
				String deviceDetailRespCode = jsonDeviceDetail.getJSONObject("dingtalk_corp_device_manage_get_response").getJSONObject("result").getString("ding_open_errcode");
				if("0".equals(deviceDetailRespCode)){
					HashMap<String,Object> map = new HashMap<>();
					map.put("corpid", corpid);
					map.put("deviceid", deviceid);
					map.put("roomid", roomid);
					
					//查询设备是否已经绑定XXX
					int num = deviceDao.queryDeviceRoomBind(map);
					if(num < 1){
						deviceDao.insertDeviceMeetingRoom(map); //插入M2设备-XXX关联表
						retCode = "0";
						retMsg = "操作成功！";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		rsp_json.put("retCode", retCode);
		rsp_json.put("retMsg", retMsg);
		rsp_json.put("bizData", bizData_json);
		return rsp_json;
	}

	/**
	 * M2设备解绑XXX
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject deviceUnbindingMeetingRoom(JSONObject reqJson) {
		JSONObject rsp_json = new JSONObject();
		String retCode = "1";
		String retMsg = "操作失败！";
		JSONObject bizData_json = new JSONObject();
		try {
			String corpid = reqJson.getString("corpid");
			if(!"".equals(corpid) && corpid!=null){
				String deviceid = reqJson.getString("deviceid");
				String roomid = reqJson.getString("roomid");
				
				Device device = new Device();
				device.setCorpid(corpid);
				device.setDeviceid(deviceid);
				device.setRoomid(roomid);
				deviceDao.deleteMeetingRoomDevice(device);
				
				retCode = "0";
				retMsg = "操作成功！";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		rsp_json.put("retCode", retCode);
		rsp_json.put("retMsg", retMsg);
		rsp_json.put("bizData", bizData_json);
		return rsp_json;
	}

	/**
	 * M2设备绑定虚拟节点
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public synchronized JSONObject deviceBindingM2Node(JSONObject reqJson) {
		JSONObject rsp_json = new JSONObject();
		String retCode = "1";
		String retMsg = "操作失败！";
		JSONObject bizData_json = new JSONObject();
		try {
			String corpid = reqJson.getString("corpid");
			if(!"".equals(corpid) && corpid!=null){
				String nodeid = reqJson.getString("nodeid");
				String deviceid = reqJson.getString("deviceid");
				String deviceserviceid = reqJson.getString("deviceserviceid");
				
				//查询设备详情，判断是否有设备（因设备同步可能存在延迟的情况）
				String accessToken = CommonUtil.getAccessToken(corpid);
				JSONObject jsonDeviceDetail = DingTalkUtil.queryDeviceDetail(accessToken, Long.valueOf(deviceserviceid), deviceid);  //查询设备详情
				String deviceDetailRespCode = jsonDeviceDetail.getJSONObject("dingtalk_corp_device_manage_get_response").getJSONObject("result").getString("ding_open_errcode");
				if("0".equals(deviceDetailRespCode)){
					HashMap<String,Object> map = new HashMap<>();
					map.put("corpid", corpid);
					map.put("deviceid", deviceid);
					map.put("nodeid", nodeid);
					
					//查询设备是否已经绑定虚拟节点
					int num = deviceDao.queryDeviceM2NodeBind(map);
					if(num < 1){
						deviceDao.insertDeviceM2Node(map); //插入M2设备-XX虚拟节点表
						retCode = "0";
						retMsg = "操作成功！";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		rsp_json.put("retCode", retCode);
		rsp_json.put("retMsg", retMsg);
		rsp_json.put("bizData", bizData_json);
		return rsp_json;
	}
	
	/**
	 * M2设备解绑虚拟节点
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject deviceUnbindingM2Node(JSONObject reqJson) {
		JSONObject rsp_json = new JSONObject();
		String retCode = "1";
		String retMsg = "操作失败！";
		JSONObject bizData_json = new JSONObject();
		try {
			String corpid = reqJson.getString("corpid");
			if(!"".equals(corpid) && corpid!=null){
				String deviceid = reqJson.getString("deviceid");
				String nodeid = reqJson.getString("nodeid");
				
				Device device = new Device();
				device.setCorpid(corpid);
				device.setDeviceid(deviceid);
				device.setNodeid(nodeid);
				deviceDao.deleteDeviceM2Node(device);
				
				retCode = "0";
				retMsg = "操作成功！";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		rsp_json.put("retCode", retCode);
		rsp_json.put("retMsg", retMsg);
		rsp_json.put("bizData", bizData_json);
		return rsp_json;
	}
	
	
	
	
	
	
	
}
