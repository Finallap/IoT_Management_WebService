package com.njupt.service;

import java.sql.Date;
import com.njupt.dao.Dao;
import com.njupt.tools.Tools;

@javax.jws.WebService(targetNamespace = "http://service.njupt.com/", serviceName = "ServiceService", portName = "ServicePort")
public class ServiceDelegate {

	com.njupt.service.Service service = new com.njupt.service.Service();

	public String getPermission(String username, String passwords) {
		return service.getPermission(username, passwords);
	}

	public String userRegister(String username, String password, String email) {
		return service.userRegister(username, password, email);
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

	public String addControllingDevice(int ProjectID, String DeviceName,
			String Mac, String Protocol, String Description, String Localtion,
			String DeviceKey) {
		return service.addControllingDevice(ProjectID, DeviceName, Mac,
				Protocol, Description, Localtion, DeviceKey);
	}

	public String deleteControllingDevice(int ControllingDeviceID) {
		return service.deleteControllingDevice(ControllingDeviceID);
	}

	public String addSensingDevice(int ProjectID, String DeviceName,
			String Mac, String Protocol, String Description, String Localtion,
			String DeviceKey) {
		return service.addSensingDevice(ProjectID, DeviceName, Mac, Protocol,
				Description, Localtion, DeviceKey);
	}

	public String deleteSensingDevice(int SensingDeviceID) {
		return service.deleteSensingDevice(SensingDeviceID);
	}

	public String addDataType(int SensingDeviceID, String Type, String Mark,
			String Symbol) {
		return service.addDataType(SensingDeviceID, Type, Mark, Symbol);
	}

	public String addConfigType(int ControllingDeviceID, String ConfigTypeName,
			String Mark) {
		return service.addConfigType(ControllingDeviceID, ConfigTypeName, Mark);
	}

	public String countProject(int UserID) {
		return service.countProject(UserID);
	}

	public String countControllingDevice(int UserID) {
		return service.countControllingDevice(UserID);
	}

	public String countSensingDevice(int UserID) {
		return service.countSensingDevice(UserID);
	}

	public String countDataType(int SensingDeviceID) {
		return service.countDataType(SensingDeviceID);
	}

	public String countConfigType(int ControllingDeviceID) {
		return service.countConfigType(ControllingDeviceID);
	}

}