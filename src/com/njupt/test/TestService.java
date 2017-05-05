package com.njupt.test;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.njupt.service.Service;

public class TestService {
	Service se= new Service(); 
	
	@Ignore
    public void testUserRegister(){
		String result = se.userRegister("qqq", "123456","547805712@qq.com");
		System.out.println(result);
    }
	
	@Ignore
	public void testLogin(){
		String result = se.getPermission("dffdsf","123456");
		System.out.println(result);
	}
	
	@Test
	public void testAddProject(){
		String result = se.addProject(5,"ssssss" ,false ,"dsfsdafsdafdsafasdfgdgfg");
		System.out.println(result);
	}
}
