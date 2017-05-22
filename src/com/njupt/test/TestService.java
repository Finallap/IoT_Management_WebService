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
		String result = se.addControllingDevice(3 ,"²âÊÔ¿ØÖÆÆ÷" ,"df-sd-sd-f-df-df-sd" ,"HTTP" ,"Ò»¶ÎÎÄ×ÖÃèÊö" ,"ÈýÅÆÂ¥","dfdsfsafdsafdsagfgdgfd");
		System.out.println(result);
	}
	
	@Ignore
	public void testAddSensingDevice(){
		String result = se.addSensingDevice(3 ,"²âÊÔ´«¸ÐÆ÷3" ,"df-sd-sd-f-df-df-sd" ,"HTTP" ,"Ò»¶ÎÎÄ×ÖÃèÊö" ,"ÈýÅÆÂ¥","dfdsfsafdsafdsagfgdgfd");
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
	
	@Ignore
	public void testUpdateControllingDevice(){
		String result = se.updateControllingDevice(2 ,"¿ØÖÆÆ÷11111" ,"aa-bb-cc-dd-ee-ff-gg" ,"TCP" ,"ÃèÊöÃèÊöÃèÊöÃèÊöÃèÊö" ,"ÏÉÁÖ" ,"fdfdsfsdhthjkiuyewiyruhfnjkndsmf");
		System.out.println(result);
	}
	
	@Ignore
	public void testUpdateSensingDevice(){
		String result = se.updateSensingDevice(3 ,"´«¸ÐÆ÷222" ,"aa-bb-cc-dd-ee-ff-gg" ,"TCP" ,"ÃèÊöÃèÊöÃèÊöÃèÊöÃèÊö" ,"ÏÉÁÖ" ,"fdfdsfsdhthjkiuyewiyruhfnjkndsmf");
		System.out.println(result);
	}
	
	@Ignore
	public void testGetControllingDeviceListByProjectID(){
		String result = se.getControllingDeviceListByProjectID(2);
		System.out.println(result);
	}
	
	@Ignore
	public void testGetSensingDeviceListByProjectID(){
		String result = se.getSensingDeviceListByProjectID(2);
		System.out.println(result);
	}
	
	@Ignore
	public void testAddConfigType(){
		String result = se.addConfigType(2 ,"¹Ø±Õ" ,"shutdown");
		System.out.println(result);
	}
	
	@Ignore
	public void testAddDataType(){
		String result = se.addDataType(4 ,"ÆøÎÂ" ,"temperature","»ªÊÏ¶È");
		System.out.println(result);
	}
	
	@Ignore
	public void testUpdateConfigType(){
		String result = se.updateConfigType(3 ,"Ö¸Áî" ,"cmd");
		System.out.println(result);
	}
	
	@Ignore
	public void testUpdateDataType(){
		String result = se.updateDataType(2 ,"ÆøÎÂ" ,"temperature","»ªÊÏ¶È");
		System.out.println(result);
	}
	
	@Ignore
	public void testDeleteConfigType(){
		String result = se.deleteConfigType(3);
		System.out.println(result);
	}
	
	@Ignore
	public void testDeleteDataType(){
		String result = se.deleteDataType(1);
		System.out.println(result);
	}
	
	@Ignore
	public void testGetConfigTypeListByDeviceID(){
		String result = se.getConfigTypeListByDeviceID(6);
		System.out.println(result);
	}
	
	@Ignore
	public void testGetDataTypeListByDeviceID(){
		String result = se.getDataTypeListByDeviceID(5);
		System.out.println(result);
	}
	
	@Ignore
	public void testGetConfigLogByDeviceID(){
		String result = se.getConfigLogByDeviceID(2 ,"2017-05-02" ,"2017-05-30" ,1 ,1);
		System.out.println(result);
	}
	
	@Ignore
	public void testGetDataLogByDeviceID(){
		String result = se.getDataLogByDeviceID(4 ,"2017-05-02" ,"2017-05-30" ,2 ,1);
		System.out.println(result);
	}
	
	@Ignore
	public void testAddAlarmRule(){
		String result = se.addAlarmRule(1 ,4 ,2 ,">" ,25);
		System.out.println(result);
	}
	
	@Ignore
	public void testUpdateAlarmRule(){
		String result = se.updateAlarmRule(1 ,"<" ,10);
		System.out.println(result);
	}
	
	@Ignore
	public void testDeleteeAlarmRule(){
		String result = se.deleteAlarmRule(1);
		System.out.println(result);
	}
	
	@Ignore
	public void testGetAlarmRuleListByDeviceID(){
		String result = se.getAlarmRuleListByDeviceID(4);
		System.out.println(result);
	}
	
	@Test
	public void testSddConfigLog(){
		String result = se.addConfigLog(2,"ggg");
		System.out.println(result);
	}
}
