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
	
	@Ignore
	public void testGetUserByUserName(){
		String result = se.getUserByUserName("��ԫ��");
		System.out.println(result);
	}
	
	@Ignore
	public void testAddProject(){
		String result = se.addProject(1,"����2" ,false ,"dsfsdafsdafdsafasdfgdgfg");
		System.out.println(result);
	}
	
	@Ignore
	public void testDeleteProject(){
		String result = se.deleteProject(4);
		System.out.println(result);
	}
	
	@Ignore
	public void testUpdateProject(){
		String result = se.updateProject(1,"��ԫ��",true);
		System.out.println(result);
	}
	
	@Ignore
	public void testCountProject(){
		String result = se.countProject(3);
		System.out.println(result);
	}
	
	@Ignore
	public void testCountUserControllingDevice(){
		String result = se.countUserControllingDevice(2);
		System.out.println(result);
	}
	
	@Ignore
	public void testCountUserSensingDevice(){
		String result = se.countUserSensingDevice(1);
		System.out.println(result);
	}
	
	@Ignore
	public void testAddControllingDevice(){
		String result = se.addControllingDevice(3 ,"���Կ�����" ,"df-sd-sd-f-df-df-sd" ,"HTTP" ,"һ����������" ,"����¥","dfdsfsafdsafdsagfgdgfd");
		System.out.println(result);
	}
	
	@Ignore
	public void testAddSensingDevice(){
		String result = se.addSensingDevice(3 ,"���Դ�����" ,"df-sd-sd-f-df-df-sd" ,"HTTP" ,"һ����������" ,"����¥","dfdsfsafdsafdsagfgdgfd");
		System.out.println(result);
	}
	
	@Test
	public void testDeleteControllingDevice(){
		String result = se.deleteControllingDevice(4);
		System.out.println(result);
	}
	
	@Test
	public void testDeleteSensingDevice(){
		String result = se.deleteSensingDevice(4);
		System.out.println(result);
	}
}
