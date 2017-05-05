package com.njupt.service;

import java.sql.Date;

import com.njupt.dao.Dao;
import com.njupt.tools.Tools;



public class Service {
	Dao db;

	public Service(){
		db = new Dao();
	}
	
	/*
	 * 判断用户是否合法
	 * 
	 */
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
	
	public String addProject(int UserID ,String ProjectName ,Boolean isPublic ,String ProjectKey){
		String value = db.addProject(UserID ,ProjectName,isPublic ,ProjectKey);
		return value;
	}
	
	public String deleteProject(int ProjectID){
		String value = db.deleteProjecByProjectID(ProjectID);
		return value;
	}
	
	public String addControllingDevice(int ProjectID ,String DeviceName ,String Mac ,String Protocol ,String Description ,String Localtion,String DeviceKey){
		return "";
	}
	
	public String deleteControllingDevice(int ControllingDeviceID){
		return "";
	}
	
	public String addSensingDevice(int ProjectID ,String DeviceName ,String Mac ,String Protocol ,String Description ,String Localtion,String DeviceKey){
		return "";
	}
	
	public String deleteSensingDevice(int SensingDeviceID){
		return "";
	}
	
	public String addDataType(int SensingDeviceID ,String Type ,String Mark ,String Symbol){
		return "";
	}
	
	public String addConfigType(int ControllingDeviceID ,String ConfigTypeName ,String Mark){
		return "";
	}
	
	public String countProject(int UserID){
		return "";
	}
	
	public String countControllingDevice(int UserID){
		return "";
	}
	
	public String countSensingDevice(int UserID){
		return "";
	}
	
	public String countDataType(int SensingDeviceID){
		return "";
	}
	
	public String countConfigType(int ControllingDeviceID){
		return "";
	}
	
	public String getDataLog(int DataTypeID ,Date start_date ,Date end_date ,int limite ,int offset){
		return "";
	}
	
	public String getConfigLog(int ControllingDeviceID ,Date start_date ,Date end_date ,int limite ,int offset){
		return "";
	}
}
