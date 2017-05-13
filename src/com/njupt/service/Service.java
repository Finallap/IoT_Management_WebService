package com.njupt.service;

import java.util.List;

import net.sf.json.JSONObject;

import com.njupt.bean.AlarmRule;
import com.njupt.bean.Configlog;
import com.njupt.bean.Configtype;
import com.njupt.bean.Controllingdevice;
import com.njupt.bean.Datatype;
import com.njupt.bean.Devicedata;
import com.njupt.bean.Project;
import com.njupt.bean.Sensingdevice;
import com.njupt.bean.User;
import com.njupt.dao.Dao;



public class Service {
	Dao db;

	public Service(){
		db = new Dao();
	}
	
	public String getPermission(String username, String passwords){
		int a = db.islegalUser(username, passwords);
		System.out.println("用户id:"+a);
		if (a > 0) {
			System.out.println("用户身份验证通过");
			return "{\"status\":\"success\"}";
		} else{
			return "{\"status\":\"用户验证失败\"}";			
		}
	}
	
	public String userRegister(String username, String password,String email) {
		String value = db.userRegister(username, password,email);
		return value;
	}
	
	public String getUserByUserName(String username) {
		boolean exist= db.findByUsername(username);
		if(exist){
			User user = db.getUserByUserName(username);
			JSONObject userJsonObject = JSONObject.fromObject(user);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("status", "success");
			jsonObject.put("User", userJsonObject);
	        System.out.println("getUserByUserName: "+jsonObject.toString());
			return jsonObject.toString();
		}else{
			System.out.println("getUserByUserName: 用户不存在");
			return "{\"status\":\"User not exist\"}";
		}
	}
	
	public String addProject(int UserID ,String ProjectName ,Boolean isPublic ,String ProjectKey){
		boolean exist= db.findByUserID(UserID);
		if(exist){
			boolean flag = db.addProject(UserID ,ProjectName,isPublic ,ProjectKey);
			if(flag==true){
				System.out.println("addProject: success");
				return "{\"status\":\"success\"}";
			}else{
				System.out.println("addProject: failed");
				return "{\"status\":\"failed\"}";
			}
		}else{
			System.out.println("addProject: 用户不存在");
			return "{\"status\":\"User not exist\"}";
		}
	}
	
	public String updateProject(int ProjectID ,String ProjectName ,Boolean isPublic){
		String value = db.updateProject(ProjectID ,ProjectName,isPublic);
		return value;
	}
	
	public String deleteProject(int ProjectID){
		boolean exist= db.existProjectByProjectID(ProjectID);
		if(exist){
			boolean flag = db.deleteProjecByProjectID(ProjectID);
			if(flag==true){
				System.out.println("deleteProjecByProjectID: success");
				return "{\"status\":\"success\"}";
			}else{
				System.out.println("deleteProjecByProjectID: failed");
				return "{\"status\":\"failed\"}";
			}
		}else{
			System.out.println("deleteProjecByProjectID: 项目不存在");
			return "{\"status\":\"Project not exist\"}";
		}
	}
	
	public String countProject(int UserID){
		boolean exist= db.findByUserID(UserID);
		if(exist){
			int value = db.countProject(UserID);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("status", "success");
			jsonObject.put("ProjectNum", value);
			
			return jsonObject.toString();
		}else{
			System.out.println("countProject: 用户不存在");
			return "{\"status\":\"User not exist\"}";
		}
	}
	
	public String queryProject(int UserID, int count , int offset){
		boolean exist= db.findByUserID(UserID);
		if(exist){
			List<Project> value = db.queryProject(UserID,count,offset);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("status", "success");
			jsonObject.put("count",  db.countProject(UserID));
			jsonObject.put("ProjectList", value);
			
			return jsonObject.toString();
		}else{
			System.out.println("countProject: 用户不存在");
			return "{\"status\":\"User not exist\"}";
		}
	}
	
	public String getProjectByProjectID(int ProjectID) {
		boolean exist= db.existProjectByProjectID(ProjectID);
		if(exist){
			Project project = db.getProjectByProjectID(ProjectID);
			JSONObject projectJsonObject = JSONObject.fromObject(project);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("status", "success");
			jsonObject.put("Project", projectJsonObject);
	        System.out.println("getProjectByProjectID: "+jsonObject.toString());
			return jsonObject.toString();
		}else{
			System.out.println("getProjectByProjectID: 项目不存在");
			return "{\"status\":\"Project not exist\"}";
		}
	}
	
	public String addControllingDevice(int ProjectID ,String DeviceName ,String Mac ,String Protocol ,String Description ,String Localtion,String DeviceKey){
		boolean exist= db.existProjectByProjectID(ProjectID);
		if(exist){
			boolean flag = db.addControllingDevice(ProjectID ,DeviceName ,Mac ,Protocol ,Description ,Localtion,DeviceKey);
			
			if(flag==true){
				System.out.println("addControllingDevice: success");
				return "{\"status\":\"success\"}";
			}else{
				System.out.println("addControllingDevice: failed");
				return "{\"status\":\"failed\"}";
			}
		}else{
			System.out.println("addControllingDevice: 项目不存在");
			return "{\"status\":\"Project not exist\"}";
		}
	}
	
	public String updateControllingDevice(int DeviceID ,String DeviceName ,String Mac ,String Protocol ,String Description ,String Localtion ,String DeviceKey){
		boolean exist= db.existControllingDeviceByDeviceID(DeviceID);
		if(exist){
			boolean flag = db.updateControllingDevice(DeviceID ,DeviceName ,Mac ,Protocol ,Description ,Localtion ,DeviceKey);
			if(flag==true){
				System.out.println("updateControllingDevice: success");
				return "{\"status\":\"success\"}";
			}else{
				System.out.println("updateControllingDevice: failed");
				return "{\"status\":\"failed\"}";
			}
		}else{
			System.out.println("updateControllingDevice: 控制设备不存在");
			return "{\"status\":\"ControllingDevice not exist\"}";
		}
	}
	
	public String deleteControllingDevice(int ControllingDeviceID){
		boolean exist= db.existControllingDeviceByDeviceID(ControllingDeviceID);
		if(exist){
			boolean flag = db.deleteControllingDeviceByDeviceID(ControllingDeviceID);
			if(flag==true){
				System.out.println("deleteControllingDevice: success");
				return "{\"status\":\"success\"}";
			}else{
				System.out.println("deleteControllingDevice: failed");
				return "{\"status\":\"failed\"}";
			}
		}else{
			System.out.println("deleteControllingDevice: 控制设备不存在");
			return "{\"status\":\"ControllingDevice not exist\"}";
		}
	}
	
	public String countUserControllingDevice(int UserID){
		boolean exist= db.findByUserID(UserID);
		if(exist){
			int value = db.countUserControllingDevice(UserID);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("status", "success");
			jsonObject.put("ControllingDeviceNum", value);
			
			System.out.println("countUserControllingDevice: "+jsonObject.toString());
			return jsonObject.toString();
		}else{
			System.out.println("updateProject: 用户不存在");
			return "{\"status\":\"Project not exist\"}";
		}
	}
	
	public String queryUserControllingDevice(int UserID, int count , int offset){
		boolean exist= db.findByUserID(UserID);
		if(exist){
			List<Controllingdevice> value = db.queryUserControllingDevice(UserID,count,offset);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("status", "success");
			jsonObject.put("count",  db.countUserControllingDevice(UserID));
			jsonObject.put("ControllingDeviceList", value);
			
			return jsonObject.toString();
		}else{
			System.out.println("countProject: 用户不存在");
			return "{\"status\":\"User not exist\"}";
		}
	}
	
	public String getControllingDeviceByDeviceID(int DeviceID) {
		boolean exist= db.existControllingDeviceByDeviceID(DeviceID);
		if(exist){
			Controllingdevice controllingdevice = db.getControllingDeviceByDeviceID(DeviceID);
			JSONObject deviceJsonObject = JSONObject.fromObject(controllingdevice);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("status", "success");
			jsonObject.put("ControllingDevice", deviceJsonObject);
	        System.out.println("getControllingDeviceByDeviceID: "+jsonObject.toString());
			return jsonObject.toString();
		}else{
			System.out.println("getControllingDeviceByDeviceID: 控制设备不存在");
			return "{\"status\":\"ControllingDevice not exist\"}";
		}
	}
	
	public String getControllingDeviceListByProjectID(int ProjectID){
		boolean exist= db.existProjectByProjectID(ProjectID);
		if(exist){
			List<Controllingdevice> value = db.getControllingDeviceListByProjectID(ProjectID);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("status", "success");
			jsonObject.put("ControllingDeviceList", value);
			
			return jsonObject.toString();
		}else{
			System.out.println("getControllingDeviceListByProjectID: 项目不存在");
			return "{\"status\":\"Project not exist\"}";
		}
	}
	
	public String addSensingDevice(int ProjectID ,String DeviceName ,String Mac ,String Protocol ,String Description ,String Localtion,String DeviceKey){
		boolean exist= db.existProjectByProjectID(ProjectID);
		if(exist){
			boolean flag = db.addSensingDevice(ProjectID ,DeviceName ,Mac ,Protocol ,Description ,Localtion,DeviceKey);
			
			if(flag==true){
				System.out.println("addSensingDevice: success");
				return "{\"status\":\"success\"}";
			}else{
				System.out.println("addSensingDevice: failed");
				return "{\"status\":\"failed\"}";
			}
		}else{
			System.out.println("addSensingDevice: 项目不存在");
			return "{\"status\":\"Project not exist\"}";
		}
	}
	
	public String updateSensingDevice(int DeviceID ,String DeviceName ,String Mac ,String Protocol ,String Description ,String Localtion ,String DeviceKey){
		boolean exist= db.existSensingDeviceByDeviceID(DeviceID);
		if(exist){
			boolean flag = db.updateSensingDevice(DeviceID ,DeviceName ,Mac ,Protocol ,Description ,Localtion ,DeviceKey);
			if(flag==true){
				System.out.println("updateSensingDevice: success");
				return "{\"status\":\"success\"}";
			}else{
				System.out.println("updateSensingDevice: failed");
				return "{\"status\":\"failed\"}";
			}
		}else{
			System.out.println("updateSensingDevice: 控制设备不存在");
			return "{\"status\":\"SensingDevice not exist\"}";
		}
	}
	
	public String deleteSensingDevice(int SensingDeviceID){
		boolean exist= db.existSensingDeviceByDeviceID(SensingDeviceID);
		if(exist){
			boolean flag = db.deleteSensingDeviceByDeviceID(SensingDeviceID);
			if(flag==true){
				System.out.println("deleteSensingDevice: success");
				return "{\"status\":\"success\"}";
			}else{
				System.out.println("deleteSensingDevice: failed");
				return "{\"status\":\"failed\"}";
			}
		}else{
			System.out.println("deleteSensingDevice: 传感设备不存在");
			return "{\"status\":\"SensingDevice not exist\"}";
		}
	}
	
	public String countUserSensingDevice(int UserID){
		boolean exist= db.findByUserID(UserID);
		if(exist){
			int value = db.countUserSensingDevice(UserID);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("status", "success");
			jsonObject.put("SensingDeviceNum", value);
			
			return jsonObject.toString();
		}else{
			System.out.println("countUserSensingDevice: 用户不存在");
			return "{\"status\":\"User not exist\"}";
		}
	}
	
	public String queryUserSensingDevice(int UserID, int count , int offset){
		boolean exist= db.findByUserID(UserID);
		if(exist){
			List<Sensingdevice> value = db.queryUserSensingDevice(UserID,count,offset);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("status", "success");
			jsonObject.put("count",  db.countUserSensingDevice(UserID));
			jsonObject.put("SensingDeviceList", value);
			
			return jsonObject.toString();
		}else{
			System.out.println("countProject: 用户不存在");
			return "{\"status\":\"User not exist\"}";
		}
	}
	
	public String getSensingDeviceByDeviceID(int DeviceID) {
		boolean exist= db.existSensingDeviceByDeviceID(DeviceID);
		if(exist){
			Sensingdevice sensingdevice = db.getSensingDeviceByDeviceID(DeviceID);
			JSONObject deviceJsonObject = JSONObject.fromObject(sensingdevice);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("status", "success");
			jsonObject.put("SensingDevice", deviceJsonObject);
	        System.out.println("getSensingDeviceByDeviceID: "+jsonObject.toString());
			return jsonObject.toString();
		}else{
			System.out.println("getSensingDeviceByDeviceID: 传感设备不存在");
			return "{\"status\":\"SensingDevice not exist\"}";
		}
	}
	
	public String getSensingDeviceListByProjectID(int ProjectID){
		boolean exist= db.existProjectByProjectID(ProjectID);
		if(exist){
			List<Sensingdevice> value = db.getSensingDeviceListByProjectID(ProjectID);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("status", "success");
			jsonObject.put("SensingDeviceList", value);
			
			return jsonObject.toString();
		}else{
			System.out.println("getSensingDeviceListByProjectID: 项目不存在");
			return "{\"status\":\"Project not exist\"}";
		}
	}
	
	public String addConfigType(int ControllingDeviceID ,String ConfigTypeName ,String Mark){
		boolean exist= db.existControllingDeviceByDeviceID(ControllingDeviceID);
		if(exist){
			boolean flag = db.addConfigType(ControllingDeviceID ,ConfigTypeName ,Mark);
			
			if(flag==true){
				System.out.println("addConfigType: success");
				return "{\"status\":\"success\"}";
			}else{
				System.out.println("addConfigType: failed");
				return "{\"status\":\"failed\"}";
			}
		}else{
			System.out.println("addConfigType: 控制设备不存在");
			return "{\"status\":\"ControllingDevice not exist\"}";
		}
	}
	
	public String updateConfigType(int ConfigTypeID ,String ConfigTypeName ,String Mark){
		boolean exist= db.existConfigTypeByTypeID(ConfigTypeID);
		if(exist){
			boolean flag = db.updateConfigType(ConfigTypeID ,ConfigTypeName ,Mark );
			
			if(flag==true){
				System.out.println("updateConfigType: success");
				return "{\"status\":\"success\"}";
			}else{
				System.out.println("updateConfigType: failed");
				return "{\"status\":\"failed\"}";
			}
		}else{
			System.out.println("updateConfigType: 控制类型不存在");
			return "{\"status\":\"ConfigType not exist\"}";
		}
	}
	
	public String deleteConfigType(int ConfigTypeID){
		boolean exist= db.existConfigTypeByTypeID(ConfigTypeID);
		if(exist){
			boolean flag = db.deleteConfigType(ConfigTypeID);
			
			if(flag==true){
				System.out.println("deleteConfigType: success");
				return "{\"status\":\"success\"}";
			}else{
				System.out.println("deleteConfigType: failed");
				return "{\"status\":\"failed\"}";
			}
		}else{
			System.out.println("deleteConfigType: 控制类型不存在");
			return "{\"status\":\"ConfigType not exist\"}";
		}
	}
	
	public String  getConfigTypeListByDeviceID(int DeviceID){
		boolean exist= db.existControllingDeviceByDeviceID(DeviceID);
		if(exist){
			List<Configtype> value = db. getConfigTypeListByDeviceID(DeviceID);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("status", "success");
			jsonObject.put("ConfigTypeList", value);
			
			return jsonObject.toString();
		}else{
			System.out.println("getConfigTypeListByDeviceID: 控制设备不存在");
			return "{\"status\":\"ControllingDevice not exist\"}";
		}
	}
	
	public String addDataType(int SensingDeviceID ,String Type ,String Mark ,String Symbol){
		boolean exist= db.existSensingDeviceByDeviceID(SensingDeviceID);
		if(exist){
			boolean flag = db.addDataType(SensingDeviceID ,Type ,Mark ,Symbol);
			
			if(flag==true){
				System.out.println("addDataType: success");
				return "{\"status\":\"success\"}";
			}else{
				System.out.println("addDataType: failed");
				return "{\"status\":\"failed\"}";
			}
		}else{
			System.out.println("addDataType: 传感设备不存在");
			return "{\"status\":\"SensingDevice not exist\"}";
		}
	}
	
	public String updateDataType(int DataTypeID ,String Type ,String Mark ,String Symbol){
		boolean exist= db.existDataTypeByTypeID(DataTypeID);
		if(exist){
			boolean flag = db.updateDataType(DataTypeID ,Type ,Mark ,Symbol);
			
			if(flag==true){
				System.out.println("updateDataType: success");
				return "{\"status\":\"success\"}";
			}else{
				System.out.println("updateDataType: failed");
				return "{\"status\":\"failed\"}";
			}
		}else{
			System.out.println("updateDataType: 数据类型不存在");
			return "{\"status\":\"DataType not exist\"}";
		}
	}
	
	public String deleteDataType(int DataTypeID){
		boolean exist= db.existDataTypeByTypeID(DataTypeID);
		if(exist){
			boolean flag = db.deleteDataType(DataTypeID);
			
			if(flag==true){
				System.out.println("deleteDataType: success");
				return "{\"status\":\"success\"}";
			}else{
				System.out.println("deleteDataType: failed");
				return "{\"status\":\"failed\"}";
			}
		}else{
			System.out.println("deleteDataType: 数据类型不存在");
			return "{\"status\":\"DataType not exist\"}";
		}
	}
	
	public String  getDataTypeListByDeviceID(int DeviceID){
		boolean exist= db.existSensingDeviceByDeviceID(DeviceID);
		if(exist){
			List<Datatype> value = db. getDataTypeListByDeviceID(DeviceID);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("status", "success");
			jsonObject.put("DataTypeList", value);
			
			return jsonObject.toString();
		}else{
			System.out.println("getDataTypeListByDeviceID: 传感设备不存在");
			return "{\"status\":\"SensingDevice not exist\"}";
		}
	}

	public String getConfigLogByDeviceID(int DeviceID ,String start_date ,String end_date ,int limite ,int offset){
		boolean exist= db.existControllingDeviceByDeviceID(DeviceID);
		if(exist){
			List<Configlog> value = db.getConfigLogByDeviceID(DeviceID ,start_date ,end_date ,limite ,offset);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("status", "success");
			jsonObject.put("count", db.countConfigLogByDeviceID(DeviceID, start_date, end_date));
			jsonObject.put("ConfigLogList", value);
			
			return jsonObject.toString();
		}else{
			System.out.println("getConfigLogByDeviceID: 控制设备不存在");
			return "{\"status\":\"Device not exist\"}";
		}
	}
	
	public String getDataLogByDeviceID(int DeviceID ,String start_date ,String end_date ,int limite ,int offset){
		boolean exist= db.existSensingDeviceByDeviceID(DeviceID);
		if(exist){
			List<Devicedata> value = db.getDataLogByDeviceID(DeviceID ,start_date ,end_date ,limite ,offset);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("status", "success");
			jsonObject.put("count", db.countDataLogByDeviceID(DeviceID, start_date, end_date));
			jsonObject.put("DataLogList", value);
			
			return jsonObject.toString();
		}else{
			System.out.println("getDataLogByDeviceID: 传感设备不存在");
			return "{\"status\":\"Device not exist\"}";
		}
	}
	
	public String addAlarmRule(int UserID ,int SensingDeviceID ,int DataTypeID ,String Rule ,float Threshold){
		boolean exist= db.existSensingDeviceByDeviceID(SensingDeviceID);
		if(exist){
			boolean flag = db.addAlarmRule(UserID ,SensingDeviceID ,DataTypeID ,Rule ,Threshold);
			
			if(flag==true){
				System.out.println("addAlarmRule: success");
				return "{\"status\":\"success\"}";
			}else{
				System.out.println("addAlarmRule: failed");
				return "{\"status\":\"failed\"}";
			}
		}else{
			System.out.println("addAlarmRule: 传感设备不存在");
			return "{\"status\":\"SensingDevice not exist\"}";
		}
	}
	
	public String updateAlarmRule(int AlarmRuleID ,String Rule ,float Threshold){
		boolean exist= db.existeAlarmRuleByAlarmRuleID(AlarmRuleID);
		if(exist){
			boolean flag = db.updateAlarmRule(AlarmRuleID ,Rule ,Threshold);
			
			if(flag==true){
				System.out.println("updateAlarmRule: success");
				return "{\"status\":\"success\"}";
			}else{
				System.out.println("updateAlarmRule: failed");
				return "{\"status\":\"failed\"}";
			}
		}else{
			System.out.println("updateAlarmRule: 数据类型不存在");
			return "{\"status\":\"AlarmRule not exist\"}";
		}
	}
	
	public String deleteAlarmRule(int AlarmRuleID){
		boolean exist= db.existeAlarmRuleByAlarmRuleID(AlarmRuleID);
		if(exist){
			boolean flag = db.deleteAlarmRule(AlarmRuleID);
			
			if(flag==true){
				System.out.println("deleteAlarmRule: success");
				return "{\"status\":\"success\"}";
			}else{
				System.out.println("deleteAlarmRule: failed");
				return "{\"status\":\"failed\"}";
			}
		}else{
			System.out.println("deleteAlarmRule: 数据类型不存在");
			return "{\"status\":\"AlarmRule not exist\"}";
		}
	}
	
	public String  getAlarmRuleListByDeviceID(int DeviceID){
		boolean exist= db.existSensingDeviceByDeviceID(DeviceID);
		if(exist){
			List<AlarmRule> value = db. getAlarmRuleListByDeviceID(DeviceID);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("status", "success");
			jsonObject.put("AlarmRuleList", value);
			
			return jsonObject.toString();
		}else{
			System.out.println("getAlarmRuleListByDeviceID: 传感设备不存在");
			return "{\"status\":\"SensingDevice not exist\"}";
		}
	}
}
