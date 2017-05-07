package com.njupt.service;

import java.sql.Date;
import java.util.List;

import net.sf.json.JSONObject;

import com.njupt.bean.Controllingdevice;
import com.njupt.bean.Project;
import com.njupt.bean.Sensingdevice;
import com.njupt.bean.User;
import com.njupt.dao.Dao;
import com.njupt.tools.Tools;



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
			jsonObject.put("ProjectList", value);
			
			return jsonObject.toString();
		}else{
			System.out.println("countProject: 用户不存在");
			return "{\"status\":\"User not exist\"}";
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
			
			System.out.println("updateProject: "+jsonObject.toString());
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
			jsonObject.put("ControllingDeviceList", value);
			
			return jsonObject.toString();
		}else{
			System.out.println("countProject: 用户不存在");
			return "{\"status\":\"User not exist\"}";
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
			System.out.println("deleteSensingDevice: 控制设备不存在");
			return "{\"status\":\"SensingDevice not exist\"}";
		}
	}
	
	public String countUserSensingDevice(int UserID){
		boolean exist= db.findByUserID(UserID);
		if(exist){
			int value = db.countUserSensingDevice(UserID);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("status", "success");
			jsonObject.put("ControllingDeviceNum", value);
			
			return jsonObject.toString();
		}else{
			System.out.println("updateProject: 用户不存在");
			return "{\"status\":\"Project not exist\"}";
		}
	}
	
	public String queryUserSensingDevice(int UserID, int count , int offset){
		boolean exist= db.findByUserID(UserID);
		if(exist){
			List<Sensingdevice> value = db.queryUserSensingDevice(UserID,count,offset);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("status", "success");
			jsonObject.put("SensingDeviceList", value);
			
			return jsonObject.toString();
		}else{
			System.out.println("countProject: 用户不存在");
			return "{\"status\":\"User not exist\"}";
		}
	}
	
	public String addDataType(int SensingDeviceID ,String Type ,String Mark ,String Symbol){
		return "";
	}
	
	public String deleteDataType(int DataTypeID){
		return "";
	}
	
	public String countDataType(int SensingDeviceID){
		return "";
	}
	
	public String addConfigType(int ControllingDeviceID ,String ConfigTypeName ,String Mark){
		return "";
	}
	
	public String deleteConfigType(int ConfigTypeID){
		return "";
	}

	public String countConfigType(int ControllingDeviceID){
		return "";
	}
	
//	public String getDataLog(int DataTypeID ,Date start_date ,Date end_date ,int limite ,int offset){
//		return "";
//	}
//	
//	public String getConfigLog(int ControllingDeviceID ,Date start_date ,Date end_date ,int limite ,int offset){
//		return "";
//	}
}
