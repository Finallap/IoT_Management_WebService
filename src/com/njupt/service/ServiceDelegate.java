package com.njupt.service;

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

}