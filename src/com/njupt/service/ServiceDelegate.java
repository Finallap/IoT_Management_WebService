package com.njupt.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javassist.bytecode.Descriptor.Iterator;
import net.sf.json.JSONObject;
import com.njupt.bean.AlarmData;
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

@javax.jws.WebService(targetNamespace = "http://service.njupt.com/", serviceName = "ServiceService", portName = "ServicePort")
public class ServiceDelegate {

	com.njupt.service.Service service = new com.njupt.service.Service();

	public String getPermission(String username, String passwords) {
		return service.getPermission(username, passwords);
	}

	public String userRegister(String username, String password, String email) {
		return service.userRegister(username, password, email);
	}

	public String getUserByUserName(String username) {
		return service.getUserByUserName(username);
	}

	public String addProject(int UserID, String ProjectName, Boolean isPublic,
			String ProjectKey) {
		return service.addProject(UserID, ProjectName, isPublic, ProjectKey);
	}

	public String updateProject(int ProjectID, String ProjectName,
			Boolean isPublic) {
		return service.updateProject(ProjectID, ProjectName, isPublic);
	}

	public String deleteProject(int ProjectID) {
		return service.deleteProject(ProjectID);
	}

	public String countProject(int UserID) {
		return service.countProject(UserID);
	}

	public String queryProject(int UserID, int count, int offset) {
		return service.queryProject(UserID, count, offset);
	}

	public String getProjectByProjectID(int ProjectID) {
		return service.getProjectByProjectID(ProjectID);
	}

	public String addControllingDevice(int ProjectID, String DeviceName,
			String Mac, String Protocol, String Description, String Localtion,
			String DeviceKey) {
		return service.addControllingDevice(ProjectID, DeviceName, Mac,
				Protocol, Description, Localtion, DeviceKey);
	}

	public String updateControllingDevice(int DeviceID, String DeviceName,
			String Mac, String Protocol, String Description, String Localtion,
			String DeviceKey) {
		return service.updateControllingDevice(DeviceID, DeviceName, Mac,
				Protocol, Description, Localtion, DeviceKey);
	}

	public String deleteControllingDevice(int ControllingDeviceID) {
		return service.deleteControllingDevice(ControllingDeviceID);
	}

	public String countUserControllingDevice(int UserID) {
		return service.countUserControllingDevice(UserID);
	}

	public String queryUserControllingDevice(int UserID, int count, int offset) {
		return service.queryUserControllingDevice(UserID, count, offset);
	}

	public String getControllingDeviceByDeviceID(int DeviceID) {
		return service.getControllingDeviceByDeviceID(DeviceID);
	}

	public String getControllingDeviceListByProjectID(int ProjectID) {
		return service.getControllingDeviceListByProjectID(ProjectID);
	}

	public String addSensingDevice(int ProjectID, String DeviceName,
			String Mac, String Protocol, String Description, String Localtion,
			String DeviceKey) {
		return service.addSensingDevice(ProjectID, DeviceName, Mac, Protocol,
				Description, Localtion, DeviceKey);
	}

	public String updateSensingDevice(int DeviceID, String DeviceName,
			String Mac, String Protocol, String Description, String Localtion,
			String DeviceKey) {
		return service.updateSensingDevice(DeviceID, DeviceName, Mac, Protocol,
				Description, Localtion, DeviceKey);
	}

	public String deleteSensingDevice(int SensingDeviceID) {
		return service.deleteSensingDevice(SensingDeviceID);
	}

	public String countUserSensingDevice(int UserID) {
		return service.countUserSensingDevice(UserID);
	}

	public String queryUserSensingDevice(int UserID, int count, int offset) {
		return service.queryUserSensingDevice(UserID, count, offset);
	}

	public String getSensingDeviceByDeviceID(int DeviceID) {
		return service.getSensingDeviceByDeviceID(DeviceID);
	}

	public String getSensingDeviceListByProjectID(int ProjectID) {
		return service.getSensingDeviceListByProjectID(ProjectID);
	}

	public String addConfigType(int ControllingDeviceID, String ConfigTypeName,
			String Mark) {
		return service.addConfigType(ControllingDeviceID, ConfigTypeName, Mark);
	}

	public String updateConfigType(int ConfigTypeID, String ConfigTypeName,
			String Mark) {
		return service.updateConfigType(ConfigTypeID, ConfigTypeName, Mark);
	}

	public String deleteConfigType(int ConfigTypeID) {
		return service.deleteConfigType(ConfigTypeID);
	}

	public String getConfigTypeListByDeviceID(int DeviceID) {
		return service.getConfigTypeListByDeviceID(DeviceID);
	}

	public String addDataType(int SensingDeviceID, String Type, String Mark,
			String Symbol) {
		return service.addDataType(SensingDeviceID, Type, Mark, Symbol);
	}

	public String updateDataType(int DataTypeID, String Type, String Mark,
			String Symbol) {
		return service.updateDataType(DataTypeID, Type, Mark, Symbol);
	}

	public String deleteDataType(int DataTypeID) {
		return service.deleteDataType(DataTypeID);
	}

	public String getDataTypeListByDeviceID(int DeviceID) {
		return service.getDataTypeListByDeviceID(DeviceID);
	}

	public String getConfigLogByDeviceID(int DeviceID, String start_date,
			String end_date, int limite, int offset) {
		return service.getConfigLogByDeviceID(DeviceID, start_date, end_date,
				limite, offset);
	}

	public String getDataLogByDeviceID(int DeviceID, String start_date,
			String end_date, int limite, int offset) {
		return service.getDataLogByDeviceID(DeviceID, start_date, end_date,
				limite, offset);
	}

	public String addAlarmRule(int UserID, int SensingDeviceID, int DataTypeID,
			String Rule, float Threshold) {
		return service.addAlarmRule(UserID, SensingDeviceID, DataTypeID, Rule,
				Threshold);
	}

	public String updateAlarmRule(int AlarmRuleID, String Rule, float Threshold) {
		return service.updateAlarmRule(AlarmRuleID, Rule, Threshold);
	}

	public String deleteAlarmRule(int AlarmRuleID) {
		return service.deleteAlarmRule(AlarmRuleID);
	}

	public String getAlarmRuleListByDeviceID(int DeviceID) {
		return service.getAlarmRuleListByDeviceID(DeviceID);
	}

	public String addConfigLog(int ConfigTypeID, String ConfigContent) {
		return service.addConfigLog(ConfigTypeID, ConfigContent);
	}

	public String getAlarmDataByUserID(int UserID, String start_date,
			String end_date, int limite, int offset) {
		return service.getAlarmDataByUserID(UserID, start_date, end_date,
				limite, offset);
	}

	public String addDataLog(String content) {
		return service.addDataLog(content);
	}

}