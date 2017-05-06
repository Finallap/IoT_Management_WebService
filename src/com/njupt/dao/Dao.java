package com.njupt.dao;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.net.URI;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.njupt.bean.User;
import com.njupt.tools.Tools;


public class Dao {
	
	private static final String url = "jdbc:mysql://localhost:3306/iot_management?useUnicode=true&characterEncoding=UTF8";
	private static final String user = "root";
	private static final String userpass = "";
	private static ComboPooledDataSource ds;
	private ResultSet rs;
	private Connection conn;
	private PreparedStatement pstmt;
	private static final String driver = "com.mysql.jdbc.Driver";
	
	/**
     * 初始化连接池代码块,全局静态代码块
     */
	static{
		initDB();
	}
	
	/**
	 * 初始化数据库操作，建立连接池,使用的是C3PO数据源
	 * 
	 */
	private static final void initDB() {
		ds = new ComboPooledDataSource();
		try {
			ds.setDriverClass(driver);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		ds.setJdbcUrl(url);
		ds.setUser(user);
		ds.setPassword(userpass);
		ds.setMaxPoolSize(100);
		ds.setInitialPoolSize(10);
		ds.setMinPoolSize(5);
		ds.setMaxStatements(300);
		ds.setMaxIdleTime(10);
		ds.setMaxConnectionAge(100);
		//连接的最大空闲时间，如果超过这个时间，某个数据库连接还没有被使用，则会断开掉这个连接,单位秒  
		//ds.setMaxIdleTime(10);
		//配置连接的生存时间，超过这个时间的连接将由连接池自动断开丢弃掉。当然正在使用的连接不会马上断开，而是等待它close再断开。
		//ds.setMaxConnectionAge(50);
	}
	
	/**
	 * 判断用户是否合法
	 * 
	 * @param username
	 * @param password
	 * @return
	 * @return
	 */
	public int islegalUser(String username, String password) {
		String sql = "select * from user where UserName = ?";
		int c = 0;
		String b = Tools.getMD5Str(password);
		System.out.println("用户输入的密码："+b);
		String a = "";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			rs = pstmt.executeQuery();
			if (rs.next()) {
//				a = Tools.getMD5Str(rs.getString(4));
				a = rs.getString(4);
				System.out.println("用户注册在数据库中的密码："+a);
				if (a.equals(b)) {
					c = rs.getInt(1);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return c;
	}
	
	/**
	 * 用户注册
	 * @param username
	 * @param password
	 * @param telnum
	 * @return{res:}
	 * @throws Exception
	 */
	public String userRegister(String username, String password,String email) {
		boolean exist=findByUsername(username);
		if(!exist){
			String sql = "insert into user values(NULL, ?, ?, ?, ?, '0')";
			
			String encrypted_password = Tools.getMD5Str(password);
			System.out.println("加密后的密码："+encrypted_password);
			
			try {
				conn = ds.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, username);
				pstmt.setString(2, email);
				pstmt.setString(3, encrypted_password);
				pstmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
				int flag = pstmt.executeUpdate();
				if(flag==1){
					System.out.println("userRegister: success");
					return "{\"status\":\"success\"}";			
				}
			}catch(Exception e){			
				e.printStackTrace();
			} finally {
				try {
					if (pstmt != null)
						pstmt.close();
					if (conn != null)
						conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			System.out.println("userRegister: failed");
			return "{\"status\":\"failed\"}";
		}else{
			System.out.println("userRegister: 用户名已存在");
			return "{\"status\":\"User already exist\"}";
		}
	}
	
	/**
	 * 注册时用来判断用户名是否存在
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public boolean findByUsername(String username){
		boolean value=false;
		String sql = "select count(*) from user where UserName=?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int res=rs.getInt(1);
				if(res>0){
					value=true;
				}
			}
			System.out.println("findByUsername: "+value);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return value;
	}
	
	/**
	 * 注册时用来判断用户名是否存在
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public boolean findByUserID(int UserID){
		boolean value=false;
		String sql = "select count(*) from user where UserID=?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, UserID);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int res=rs.getInt(1);
				if(res>0){
					value=true;
				}
			}
			System.out.println("findByUserID: "+value);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return value;
	}

	public User getUserByUserName(String username){
		User user = null;
		String sql = "SELECT * FROM `user` WHERE `UserName` = ?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				user = new User(rs.getInt(1),rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getTimestamp(5),rs.getBoolean(6));
			}
			
			return user;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public Boolean addProject(int userid ,String projectname, Boolean ispublic ,String projectkey) {
		String sql = "INSERT INTO `project` (`ProjectID`, `UserID`, `ProjectName`, `isPublic`, `ProjectKey`, `CreateTime`) VALUES (NULL, ?, ?, ?, ?, ?)";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userid);
			pstmt.setString(2, projectname);
			pstmt.setBoolean(3, ispublic);
			pstmt.setString(4, projectkey);
			pstmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
				
			int flag = pstmt.executeUpdate();
			if(flag==1){
				return true;
			}
		}catch(Exception e){			
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public String updateProject(int ProjectID ,String ProjectName ,Boolean isPublic){
		boolean exist= existProjectByProjectID(ProjectID);
		if(exist){
			String sql = "UPDATE `project` SET `ProjectName` = ? , `isPublic` = ? WHERE `ProjectID` = ?;";
			int flag=0;
			try {
				conn = ds.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, ProjectName);
				pstmt.setBoolean(2, isPublic);
				pstmt.setInt(3, ProjectID);
				flag = pstmt.executeUpdate();
				if(flag==1){
					System.out.println("updateProject:"+flag);
					return "{\"status\":\"success\"}";			
				}
			} catch(Exception e){
				e.printStackTrace();
			}finally {
				try{
					if (pstmt != null)
						pstmt.close();
					if (conn != null)
						conn.close();				
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			System.out.println("updateProject: failed");
			return "{\"status\":\"failed\"}";
		}else{
			System.out.println("updateProject: 项目不存在");
			return "{\"status\":\"Project not exist\"}";
		}
	}
	
	
	public boolean existProjectByProjectID(int ProjectID){
		boolean value=false;
		String sql = "select count(*) from project where ProjectID=?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ProjectID);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int res=rs.getInt(1);
				if(res>0){
					value=true;
				}
			}
			System.out.println("existProjectByProjectID: "+value);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return value;
	}
	
	
	public Boolean deleteProjecByProjectID(int ProjectID) {
		String sql = "DELETE FROM `project` WHERE `ProjectID` = ?";
		int flag=0;
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ProjectID);
			flag = pstmt.executeUpdate();
			if(flag==1){
				return true;			
			}
		} catch(Exception e){
			e.printStackTrace();
		}finally {
			try{
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return false;
	}
	
	
	public int countProject(int userid){
		int result = 0;
		String sql = "select count(*) from project where UserID=?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userid);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				result=rs.getInt(1);
			}
			System.out.println("countProject: "+result);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	
	public Boolean addControllingDevice(int ProjectID ,String DeviceName ,String Mac ,String Protocol ,String Description ,String Localtion,String DeviceKey){
		String sql = "INSERT INTO `controllingdevice` (`ProjectID`, `DeviceName`, `Mac`, `Protocol`, `Description`, `Localtion`, `DeviceKey`, `CreateTime`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ProjectID);
			pstmt.setString(2, DeviceName);
			pstmt.setString(3, Mac);
			pstmt.setString(4, Protocol);
			pstmt.setString(5, Description);
			pstmt.setString(6, Localtion);
			pstmt.setString(7, DeviceKey);
			pstmt.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
				
			int flag = pstmt.executeUpdate();
			if(flag==1){
				return true;
			}
		}catch(Exception e){			
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public boolean existControllingDeviceByDeviceID(int ControllingDeviceID){
		boolean value=false;
		String sql = "select count(*) from controllingdevice where ControllingDeviceID=?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ControllingDeviceID);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int res=rs.getInt(1);
				if(res>0){
					value=true;
				}
			}
			System.out.println("existControllingDeviceByDeviceID: "+value);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return value;
	}
	
	public Boolean deleteControllingDeviceByDeviceID(int ControllingDeviceID) {
		String sql = "DELETE FROM `controllingdevice` WHERE `ControllingDeviceID` = ?";
		int flag=0;
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ControllingDeviceID);
			flag = pstmt.executeUpdate();
			if(flag==1){
				return true;			
			}
		} catch(Exception e){
			e.printStackTrace();
		}finally {
			try{
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public int countUserControllingDevice(int userid){
		int result = 0;
		String sql = "select count(*) from controllingdevice where ProjectID in (select ProjectID from project where UserID = ?)";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userid);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				result=rs.getInt(1);
			}
			System.out.println("countUserControllingDevice: "+result);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	

	public Boolean addSensingDevice(int ProjectID ,String DeviceName ,String Mac ,String Protocol ,String Description ,String Localtion,String DeviceKey){
		String sql = "INSERT INTO `sensingdevice` (`ProjectID`, `DeviceName`, `Mac`, `Protocol`, `Description`, `Localtion`, `DeviceKey`, `CreateTime`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ProjectID);
			pstmt.setString(2, DeviceName);
			pstmt.setString(3, Mac);
			pstmt.setString(4, Protocol);
			pstmt.setString(5, Description);
			pstmt.setString(6, Localtion);
			pstmt.setString(7, DeviceKey);
			pstmt.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
				
			int flag = pstmt.executeUpdate();
			if(flag==1){
				return true;
			}
		}catch(Exception e){			
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public boolean existSensingDeviceByDeviceID(int SensingDeviceID){
		boolean value=false;
		String sql = "select count(*) from sensingdevice where SensingDeviceID=?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, SensingDeviceID);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int res=rs.getInt(1);
				if(res>0){
					value=true;
				}
			}
			System.out.println("existSensingDeviceByDeviceID: "+value);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return value;
	}
	
	public Boolean deleteSensingDeviceByDeviceID(int SensingDeviceID) {
		String sql = "DELETE FROM `sensingdevice` WHERE `SensingDeviceID` = ?";
		int flag=0;
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, SensingDeviceID);
			flag = pstmt.executeUpdate();
			if(flag==1){
				return true;			
			}
		} catch(Exception e){
			e.printStackTrace();
		}finally {
			try{
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public int countUserSensingDevice(int userid){
		int result = 0;
		String sql = "select count(*) from sensingdevice where ProjectID in (select ProjectID from project where UserID = ?)";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userid);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				result=rs.getInt(1);
			}
			System.out.println("countProject: "+result);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}