package com.njupt.test;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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
		String result = se.getUserByUserName("·½Ô«Èò");
		System.out.println(result);
	}
	
	@Ignore
	public void testAddProject(){
		String result = se.addProject(1,"ÄÏÓÊ2" ,false ,"dsfsdafsdafdsafasdfgdgfg");
		System.out.println(result);
	}
	
	@Ignore
	public void testDeleteProject(){
		String result = se.deleteProject(4);
		System.out.println(result);
	}
	
	@Ignore
	public void testUpdateProject(){
		String result = se.updateProject(1,"·½Ô«Èò",true);
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
		String result = se.addControllingDevice(3 ,"²âÊÔ¿ØÖÆÆ÷" ,"df-sd-sd-f-df-df-sd" ,"HTTP" ,"Ò»¶ÎÎÄ×ÖÃèÊö" ,"ÈıÅÆÂ¥","dfdsfsafdsafdsagfgdgfd");
		System.out.println(result);
	}
	
	@Ignore
	public void testAddSensingDevice(){
		String result = se.addSensingDevice(3 ,"²âÊÔ´«¸ĞÆ÷3" ,"df-sd-sd-f-df-df-sd" ,"HTTP" ,"Ò»¶ÎÎÄ×ÖÃèÊö" ,"ÈıÅÆÂ¥","dfdsfsafdsafdsagfgdgfd");
		System.out.println(result);
	}
	
	@Ignore
	public void testDeleteControllingDevice(){
		String result = se.deleteControllingDevice(4);
		System.out.println(result);
	}
	
	@Ignore
	public void testDeleteSensingDevice(){
		String result = se.deleteSensingDevice(4);
		System.out.println(result);
	}
	
	@Ignore
	public void testJsonAnalysis(){
		String result = se.getUserByUserName("·½Ô«Èò");
//		System.out.println(result);
		
		JSONObject jsonObject =JSONObject.fromObject(result);
//		JSONArray jsonArray = jsonObject.getJSONArray("user");
//		JSONObject jsonPersons=jsonObject.getJSONObject("status");
		
		if(jsonObject.getString("status").equals("success")){
			JSONObject userJsonObject = (JSONObject) jsonObject.get("User");
			int userId = userJsonObject.getInt("userId");
			System.out.println(userId);
		}	
	}
	
	@Ignore
	public void testQueryProject(){
		String result = se.queryProject(1,-1,0);
		System.out.println(result);
	}
	
	@Ignore
	public void testQueryUserControllingDevice(){
		String result = se.queryUserControllingDevice(1,-1,0);
		System.out.println(result);
	}
	
	@Ignore
	public void testQueryUserSensingDevice(){
		String result = se.queryUserSensingDevice(1,-1,0);
		System.out.println(result);
	}
	
	@Ignore
	public void testGetProjectByProjectID(){
		String result = se.getProjectByProjectID(3);
		System.out.println(result);
	}
	
	@Ignore
	public void testGETControllingDeviceByDeviceID(){
		String result = se.getControllingDeviceByDeviceID(2);
		System.out.println(result);
	}
	
	@Ignore
	public void testGeSensingDeviceByDeviceID(){
		String result = se.getSensingDeviceByDeviceID(3);
		System.out.println(result);
	}
	
	@Test
	public void testUpdateControllingDevice(){
		String result = se.updateControllingDevice(2 ,"¿ØÖÆÆ÷11111" ,"aa-bb-cc-dd-ee-ff-gg" ,"TCP" ,"ÃèÊöÃèÊöÃèÊöÃèÊöÃèÊö" ,"ÏÉÁÖ" ,"fdfdsfsdhthjkiuyewiyruhfnjkndsmf");
		System.out.println(result);
	}
	
	@Test
	public void testUpdateSensingDevice(){
		String result = se.updateSensingDevice(3 ,"´«¸ĞÆ÷222" ,"aa-bb-cc-dd-ee-ff-gg" ,"TCP" ,"ÃèÊöÃèÊöÃèÊöÃèÊöÃèÊö" ,"ÏÉÁÖ" ,"fdfdsfsdhthjkiuyewiyruhfnjkndsmf");
		System.out.println(result);
	}
}
