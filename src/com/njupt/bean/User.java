package com.njupt.bean;
// default package

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * User entity. @author MyEclipse Persistence Tools
 */

public class User {

	// Fields

	private Integer userId;
	private String userName;
	private String email;
	private String password;
	private Timestamp registerTime;
	private Boolean isAdmin;

	// Constructors

	/** default constructor */
	public User() {
	}

	/** full constructor */
	public User(int userId,String userName, String email, String password,
			Timestamp registerTime, Boolean isAdmin) {
		this.userId = userId;
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.registerTime = registerTime;
		this.isAdmin = isAdmin;
	}

	// Property accessors

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Timestamp getRegisterTime() {
		return this.registerTime;
	}

	public void setRegisterTime(Timestamp registerTime) {
		this.registerTime = registerTime;
	}

	public Boolean getIsAdmin() {
		return this.isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

}