package com.njupt.service;

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
		System.out.println("********************************begin*******************************");
		int a = db.islegalUser(username, passwords);
		System.out.println("用户id:"+a);
		if (a > 0) {
			System.out.println("用户身份验证通过");
			System.out.println("*********************************end*******************************");
			return "{\"res\":\"success\"}";
		} else{
			System.out.println("*********************************end*******************************");
			return "{\"res\":\"用户验证失败\"}";			
		}
	}
	
	public String userRegister(String username, String password,String email) {
		System.out.println("********************************begin*******************************");
		String value = db.userRegister(username, password,email);
		System.out.println("*********************************end*******************************");
		return value;

	}
}
