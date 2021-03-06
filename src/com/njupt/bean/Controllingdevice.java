package com.njupt.bean;
// default package

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * Controllingdevice entity. @author MyEclipse Persistence Tools
 */

public class Controllingdevice implements java.io.Serializable {

	// Fields

	private Integer controllingDeviceId;
	private Integer projectID;
	private String deviceName;
	private String mac;
	private String protocol;
	private String description;
	private String localtion;
	private String deviceKey;
	private String createTime;
	private String projectName;
	private Integer typeCount;
	
	// Constructors

	/** default constructor */
	public Controllingdevice() {
	}

	/** full constructor */
	public Controllingdevice(int projectID, String deviceName, String mac,
			String protocol, String description, String localtion,
			String deviceKey, String createTime , String projectName,int typeCount) {
		this.projectID = projectID;
		this.deviceName = deviceName;
		this.mac = mac;
		this.protocol = protocol;
		this.description = description;
		this.localtion = localtion;
		this.deviceKey = deviceKey;
		this.createTime = createTime;
		this.projectName = projectName;
		this.typeCount = typeCount;
	}

	// Property accessors

	public Integer getControllingDeviceId() {
		return this.controllingDeviceId;
	}

	public void setControllingDeviceId(Integer controllingDeviceId) {
		this.controllingDeviceId = controllingDeviceId;
	}

	public Integer getProjectID() {
		return projectID;
	}

	public void setProjectID(Integer projectID) {
		this.projectID = projectID;
	}

	public String getDeviceName() {
		return this.deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getMac() {
		return this.mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getProtocol() {
		return this.protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocaltion() {
		return this.localtion;
	}

	public void setLocaltion(String localtion) {
		this.localtion = localtion;
	}

	public String getDeviceKey() {
		return this.deviceKey;
	}

	public void setDeviceKey(String deviceKey) {
		this.deviceKey = deviceKey;
	}

	public String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Integer getTypeCount() {
		return typeCount;
	}

	public void setTypeCount(Integer typeCount) {
		this.typeCount = typeCount;
	}

}