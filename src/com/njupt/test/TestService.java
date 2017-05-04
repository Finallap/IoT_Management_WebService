package com.njupt.test;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.njupt.service.Service;

public class TestService {
	Service se= new Service(); 
	
	@Test
    public void testUserRegister(){
		String result = se.userRegister("qqq", "123456","547805712@qq.com");
		System.out.println(result);
    }
	
	@Test
	public void test(){
		String result = se.getPermission("dffdsf","123456");
		System.out.println(result);
	}
}
