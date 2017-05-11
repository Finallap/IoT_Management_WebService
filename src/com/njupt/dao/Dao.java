package com.njupt.dao;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.njupt.bean.Configlog;
import com.njupt.bean.Configtype;
import com.njupt.bean.Controllingdevice;
import com.njupt.bean.Datatype;
import com.njupt.bean.Devicedata;
import com.njupt.bean.Project;
import com.njupt.bean.Sensingdevice;
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
				//将创建时间转换为String
				DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String createTime = "";
				try{
						createTime = sdf.format(rs.getTimestamp(5));   
			       } catch (Exception e) {  
			            e.printStackTrace();  
			    }  
				
				user = new User(rs.getInt(1),rs.getString(2), rs.getString(3), rs.getString(4),
						createTime,rs.getBoolean(6));
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
	
	
	public int countProject(int userID){
		int result = 0;
		String sql = "select count(*) from project where UserID=?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userID);
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
	
	public List<Project> queryProject(int userID , int count , int offset) {
		// TODO Auto-generated method stub
		List<Project> projectList = new ArrayList<Project>(); 
		Project project;
		
		String sql = "select * from project where UserID=?";
		if(count>0)
			sql = sql + " limit ?,?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userID);
			if(count>0)
			{
				pstmt.setInt(2, offset);
				pstmt.setInt(3, count);
			}
			rs = pstmt.executeQuery();
			while (rs.next()) {
				project = new Project();
				project.setProjectId(rs.getInt(1));
				project.setUserID(userID);
				project.setProjectName(rs.getString(3));
				project.setIsPublic(rs.getBoolean(4));
				project.setProjectKey(rs.getString(5));
				project.setControllingDeviceNum(countProjectControllingDeviceID(rs.getInt(1)));
				project.setSensingDeviceNum(countProjectSensingDevices(rs.getInt(1)));
				
				//将创建时间转换为String
				DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String createTime = "";
				try{
						createTime = sdf.format(rs.getTimestamp(6));   
			       } catch (Exception e) {  
			            e.printStackTrace();  
			    }  
				project.setCreateTime(createTime);
				
				projectList.add(project);
			}
			System.out.println("queryProject: "+projectList.toString());
			return projectList;
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
		return projectList;
	}
	
	public Project getProjectByProjectID(int ProjectID){
		Project project = null;
		String sql = "SELECT * FROM `project` WHERE `ProjectID` = ?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ProjectID);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				project = new Project();
				project.setProjectId(rs.getInt(1));
				project.setUserID(rs.getInt(2));
				project.setProjectName(rs.getString(3));
				project.setIsPublic(rs.getBoolean(4));
				project.setProjectKey(rs.getString(5));
				project.setControllingDeviceNum(countProjectControllingDeviceID(rs.getInt(1)));
				project.setSensingDeviceNum(countProjectSensingDevices(rs.getInt(1)));
				
				//将创建时间转换为String
				DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String createTime = "";
				try{
						createTime = sdf.format(rs.getTimestamp(6));   
			       } catch (Exception e) {  
			            e.printStackTrace();  
			    }  
				project.setCreateTime(createTime);
			}
			
			return project;
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
	
	public int countProjectSensingDevices(int ProjectID){
		ResultSet rs = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = "select count(*) from sensingdevice where ProjectID=?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ProjectID);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				result=rs.getInt(1);
			}
			System.out.println("countProjectSensingDevices: "+result);
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
	
	public int countProjectControllingDeviceID(int ProjectID){
		ResultSet rs = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = "select count(*) from controllingdevice where ProjectID=?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ProjectID);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				result=rs.getInt(1);
			}
			System.out.println("countProjectControllingDeviceID: "+result);
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
	
	public Boolean updateControllingDevice(int DeviceID ,String DeviceName ,String Mac ,String Protocol ,String Description ,String Localtion ,String DeviceKey){
		String sql = "UPDATE `controllingdevice` SET `DeviceName` = ?, `Mac` = ?, `Protocol` = ?, `Description` = ?, `Localtion` = ?, `DeviceKey` = ? WHERE `ControllingDeviceID` = ?;";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, DeviceName);
			pstmt.setString(2, Mac);
			pstmt.setString(3, Protocol);
			pstmt.setString(4, Description);
			pstmt.setString(5, Localtion);
			pstmt.setString(6, DeviceKey);
			pstmt.setInt(7, DeviceID);
				
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
	
	public List<Controllingdevice> queryUserControllingDevice(int userID , int count , int offset) {
		// TODO Auto-generated method stub
		List<Controllingdevice> controllingDeviceList = new ArrayList<Controllingdevice>(); 
		Controllingdevice controllingdevice;
		
		String sql = "select controllingdevice.*,project.`ProjectName` from controllingdevice,project where (controllingdevice.ProjectID in (select project.ProjectID from project where project.UserID = ?)) AND controllingdevice.`ProjectID` = project.`ProjectID` ";
		if(count>0)
			sql = sql + " limit ?,?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userID);
			if(count>0)
			{
				pstmt.setInt(2, offset);
				pstmt.setInt(3, count);
			}
			rs = pstmt.executeQuery();
			while (rs.next()) {
				controllingdevice = new Controllingdevice();
				controllingdevice.setControllingDeviceId(rs.getInt(1));
				controllingdevice.setProjectID(rs.getInt(2));
				controllingdevice.setDeviceName(rs.getString(3));
				controllingdevice.setMac(rs.getString(4));
				controllingdevice.setProtocol(rs.getString(5));
				controllingdevice.setDescription(rs.getString(6));
				controllingdevice.setLocaltion(rs.getString(7));
				controllingdevice.setDeviceKey(rs.getString(8));
				controllingdevice.setProjectName(rs.getString(10));
				controllingdevice.setTypeCount(countDeviceConfigType(rs.getInt(1)));
				
				//将创建时间转换为String
				DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String createTime = "";
				try{
						createTime = sdf.format(rs.getTimestamp(9));   
			       } catch (Exception e) {  
			            e.printStackTrace();  
			    }  
				controllingdevice.setCreateTime(createTime);
				
				controllingDeviceList.add(controllingdevice);
			}
			System.out.println("queryUserControllingDevice: "+controllingDeviceList.toString());
			return controllingDeviceList;
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
		return controllingDeviceList;
	}
	
	public Controllingdevice getControllingDeviceByDeviceID(int DeviceID){
		Controllingdevice controllingdevice = null;
		String sql = "select controllingdevice.*,project.`ProjectName` from controllingdevice,project WHERE controllingdevice.`ControllingDeviceID` = ? AND controllingdevice.`ProjectID` = project.`ProjectID`";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, DeviceID);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				controllingdevice = new Controllingdevice();
				controllingdevice.setControllingDeviceId(rs.getInt(1));
				controllingdevice.setProjectID(rs.getInt(2));
				controllingdevice.setDeviceName(rs.getString(3));
				controllingdevice.setMac(rs.getString(4));
				controllingdevice.setProtocol(rs.getString(5));
				controllingdevice.setDescription(rs.getString(6));
				controllingdevice.setLocaltion(rs.getString(7));
				controllingdevice.setDeviceKey(rs.getString(8));
				controllingdevice.setProjectName(rs.getString(10));
				controllingdevice.setTypeCount(countDeviceConfigType(rs.getInt(1)));
				
				//将创建时间转换为String
				DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String createTime = "";
				try{
						createTime = sdf.format(rs.getTimestamp(9));   
			       } catch (Exception e) {  
			            e.printStackTrace();  
			    }  
				controllingdevice.setCreateTime(createTime);
			}
			
			return controllingdevice;
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
	
	public List<Controllingdevice> getControllingDeviceListByProjectID(int ProjectID) {
		// TODO Auto-generated method stub
		List<Controllingdevice> controllingDeviceList = new ArrayList<Controllingdevice>(); 
		Controllingdevice controllingdevice;
		
		String sql = "select controllingdevice.*,project.`ProjectName` from controllingdevice,project where controllingdevice.ProjectID = ? AND controllingdevice.`ProjectID` = project.`ProjectID` ";

		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ProjectID);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				controllingdevice = new Controllingdevice();
				controllingdevice.setControllingDeviceId(rs.getInt(1));
				controllingdevice.setProjectID(rs.getInt(2));
				controllingdevice.setDeviceName(rs.getString(3));
				controllingdevice.setMac(rs.getString(4));
				controllingdevice.setProtocol(rs.getString(5));
				controllingdevice.setDescription(rs.getString(6));
				controllingdevice.setLocaltion(rs.getString(7));
				controllingdevice.setDeviceKey(rs.getString(8));
				controllingdevice.setProjectName(rs.getString(10));
				controllingdevice.setTypeCount(countDeviceConfigType(rs.getInt(1)));
				
				//将创建时间转换为String
				DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String createTime = "";
				try{
						createTime = sdf.format(rs.getTimestamp(9));   
			       } catch (Exception e) {  
			            e.printStackTrace();  
			    }  
				controllingdevice.setCreateTime(createTime);
				
				controllingDeviceList.add(controllingdevice);
			}

			return controllingDeviceList;
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
		return controllingDeviceList;
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
	
	public Boolean updateSensingDevice(int DeviceID ,String DeviceName ,String Mac ,String Protocol ,String Description ,String Localtion ,String DeviceKey){
		String sql = "UPDATE `sensingdevice` SET `DeviceName` = ?, `Mac` = ?, `Protocol` = ?, `Description` = ?, `Localtion` = ?, `DeviceKey` = ? WHERE `SensingDeviceID` = ?;";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, DeviceName);
			pstmt.setString(2, Mac);
			pstmt.setString(3, Protocol);
			pstmt.setString(4, Description);
			pstmt.setString(5, Localtion);
			pstmt.setString(6, DeviceKey);
			pstmt.setInt(7, DeviceID);
				
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
	
	public List<Sensingdevice> queryUserSensingDevice(int userID , int count , int offset) {
		// TODO Auto-generated method stub
		List<Sensingdevice> sensingDeviceList = new ArrayList<Sensingdevice>(); 
		Sensingdevice sensingdevice;
		
		String sql = "select sensingdevice.*,project.`ProjectName` from sensingdevice,project where (sensingdevice.ProjectID in (select project.ProjectID from project where project.UserID = ?)) AND sensingdevice.`ProjectID` = project.`ProjectID`";
		if(count>0)
			sql = sql + " limit ?,?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userID);
			if(count>0)
			{
				pstmt.setInt(2, offset);
				pstmt.setInt(3, count);
			}
			rs = pstmt.executeQuery();
			while (rs.next()) {
				sensingdevice = new Sensingdevice();
				sensingdevice.setSensingDeviceId(rs.getInt(1));
				sensingdevice.setProjectID(rs.getInt(2));
				sensingdevice.setDeviceName(rs.getString(3));
				sensingdevice.setMac(rs.getString(4));
				sensingdevice.setProtocol(rs.getString(5));
				sensingdevice.setDescription(rs.getString(6));
				sensingdevice.setLocaltion(rs.getString(7));
				sensingdevice.setDeviceKey(rs.getString(8));
				sensingdevice.setProjectName(rs.getString(10));
				sensingdevice.setTypeCount(countDeviceDataType(rs.getInt(1)));
				
				//将创建时间转换为String
				DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String createTime = "";
				try{
						createTime = sdf.format(rs.getTimestamp(9));   
			       } catch (Exception e) {  
			            e.printStackTrace();  
			    }  
				sensingdevice.setCreateTime(createTime);
				
				sensingDeviceList.add(sensingdevice);
			}
			System.out.println("queryUserSensingDevice: "+sensingDeviceList.toString());
			return sensingDeviceList;
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
		return sensingDeviceList;
	}
	
	public Sensingdevice getSensingDeviceByDeviceID(int DeviceID){
		Sensingdevice sensingdevice = null;
		String sql = "select sensingdevice.*,project.`ProjectName` from sensingdevice,project WHERE sensingdevice.`SensingDeviceID` = ? AND sensingdevice.`ProjectID` = project.`ProjectID`";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, DeviceID);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				sensingdevice = new Sensingdevice();
				sensingdevice.setSensingDeviceId(rs.getInt(1));
				sensingdevice.setProjectID(rs.getInt(2));
				sensingdevice.setDeviceName(rs.getString(3));
				sensingdevice.setMac(rs.getString(4));
				sensingdevice.setProtocol(rs.getString(5));
				sensingdevice.setDescription(rs.getString(6));
				sensingdevice.setLocaltion(rs.getString(7));
				sensingdevice.setDeviceKey(rs.getString(8));
				sensingdevice.setProjectName(rs.getString(10));
				sensingdevice.setTypeCount(countDeviceDataType(rs.getInt(1)));
				
				//将创建时间转换为String
				DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String createTime = "";
				try{
						createTime = sdf.format(rs.getTimestamp(9));   
			       } catch (Exception e) {  
			            e.printStackTrace();  
			    }  
				sensingdevice.setCreateTime(createTime);
			}
			
			return sensingdevice;
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
	
	public List<Sensingdevice> getSensingDeviceListByProjectID(int ProjectID){
		// TODO Auto-generated method stub
		List<Sensingdevice> sensingDeviceList = new ArrayList<Sensingdevice>(); 
		Sensingdevice sensingdevice;
		
		String sql = "select sensingdevice.*,project.`ProjectName` from sensingdevice,project where sensingdevice.ProjectID = ? AND sensingdevice.`ProjectID` = project.`ProjectID`";

		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ProjectID);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				sensingdevice = new Sensingdevice();
				sensingdevice.setSensingDeviceId(rs.getInt(1));
				sensingdevice.setProjectID(rs.getInt(2));
				sensingdevice.setDeviceName(rs.getString(3));
				sensingdevice.setMac(rs.getString(4));
				sensingdevice.setProtocol(rs.getString(5));
				sensingdevice.setDescription(rs.getString(6));
				sensingdevice.setLocaltion(rs.getString(7));
				sensingdevice.setDeviceKey(rs.getString(8));
				sensingdevice.setProjectName(rs.getString(10));
				sensingdevice.setTypeCount(countDeviceDataType(rs.getInt(1)));
				
				//将创建时间转换为String
				DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String createTime = "";
				try{
						createTime = sdf.format(rs.getTimestamp(9));   
			       } catch (Exception e) {  
			            e.printStackTrace();  
			    }  
				sensingdevice.setCreateTime(createTime);
				
				sensingDeviceList.add(sensingdevice);
			}
			System.out.println("getSensingDeviceListByProjectID: "+sensingDeviceList.toString());
			return sensingDeviceList;
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
		return sensingDeviceList;
	}
	
	public Boolean addConfigType(int ControllingDeviceID ,String ConfigTypeName ,String Mark){
		String sql = "INSERT INTO `configtype` (`ConfigTypeID`, `ControllingDeviceID`, `ConfigTypeName`, `Mark`, `CreateTime`) VALUES (NULL, ?, ?, ?, ?)";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ControllingDeviceID);
			pstmt.setString(2, ConfigTypeName);
			pstmt.setString(3, Mark);
			pstmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
				
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
	
	public Boolean updateConfigType(int ConfigTypeID ,String ConfigTypeName ,String Mark){
		String sql = "UPDATE`configtype` SET `ConfigTypeName` = ?, `Mark` = ? WHERE `ConfigTypeID` = ?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ConfigTypeName);
			pstmt.setString(2, Mark);
			pstmt.setInt(3, ConfigTypeID);
				
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
	
	public boolean existConfigTypeByTypeID(int TypeID){
		boolean value=false;
		String sql = "select count(*) from configtype where ConfigTypeID=?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, TypeID);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int res=rs.getInt(1);
				if(res>0){
					value=true;
				}
			}
			System.out.println("existConfigTypeByTypeID: "+value);
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
	
	public Boolean deleteConfigType(int ConfigTypeID){
		String sql = "DELETE FROM `configtype` WHERE `ConfigTypeID` = ?";
		int flag=0;
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ConfigTypeID);
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
	
	public int countDeviceConfigType(int deviceID){
		ResultSet rs = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		int result = 0;
		String sql = "select count(*) from configtype where ControllingDeviceID=?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, deviceID);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				result=rs.getInt(1);
			}
			System.out.println("countDeviceConfigType: "+result);
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
	
	public List<Configtype> getConfigTypeListByDeviceID(int DeviceID){
		// TODO Auto-generated method stub
		List<Configtype> ConfigTypeList = new ArrayList<Configtype>(); 
		Configtype configtype;
		
		String sql = "SELECT * FROM `configtype` WHERE `ControllingDeviceID` = ?";

		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, DeviceID);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				configtype = new Configtype();
				configtype.setConfigTypeId(rs.getInt(1));
				configtype.setControllingDeviceID(rs.getInt(2));
				configtype.setConfigTypeName(rs.getString(3));
				configtype.setMark(rs.getString(4));
				
				//将创建时间转换为String
				DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String createTime = "";
				try{
						createTime = sdf.format(rs.getTimestamp(5));   
			       } catch (Exception e) {  
			            e.printStackTrace();  
			    }  
				configtype.setCreateTime(createTime);
				
				ConfigTypeList.add(configtype);
			}
			System.out.println("getConfigTypeListByDeviceID: "+ConfigTypeList.toString());
			return ConfigTypeList;
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
		return ConfigTypeList;
	}
	
	public Boolean addDataType(int SensingDeviceID ,String Type ,String Mark ,String Symbol){
		String sql = "INSERT INTO `datatype` (`DataTypeID`, `SensingDeviceID`, `Type`, `Mark`, `Symbol`, `CreateTime`) VALUES (NULL, ?, ?, ?, ?, ?)";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, SensingDeviceID);
			pstmt.setString(2, Type);
			pstmt.setString(3, Mark);
			pstmt.setString(4, Symbol);
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
	
	public Boolean updateDataType(int DataTypeID ,String Type ,String Mark ,String Symbol){
		String sql = "UPDATE `datatype` SET `Type` = ?, `Mark` = ?, `Symbol` = ? WHERE `DataTypeID` = ?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, Type);
			pstmt.setString(2, Mark);
			pstmt.setString(3, Symbol);
			pstmt.setInt(4, DataTypeID);
				
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
	
	public boolean existDataTypeByTypeID(int TypeID){
		boolean value=false;
		String sql = "select count(*) from datatype where DataTypeID=?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, TypeID);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int res=rs.getInt(1);
				if(res>0){
					value=true;
				}
			}
			System.out.println("existDataTypeByTypeID: "+value);
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
	
	public Boolean deleteDataType(int DataTypeID){
		String sql = "DELETE FROM `datatype` WHERE `DataTypeID` = ?";
		int flag=0;
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, DataTypeID);
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
	
	public int countDeviceDataType(int deviceID){
		ResultSet rs = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		int result = 0;
		String sql = "select count(*) from datatype where SensingDeviceID=?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, deviceID);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				result=rs.getInt(1);
			}
			System.out.println("countDeviceDataType: "+result);
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
	
	public List<Datatype> getDataTypeListByDeviceID(int DeviceID){
		// TODO Auto-generated method stub
		List<Datatype> DataTypeList = new ArrayList<Datatype>(); 
		Datatype datetype;
		
		String sql = "SELECT * FROM `datatype` WHERE `SensingDeviceID` = ?";

		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, DeviceID);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				datetype = new Datatype();
				datetype.setDataTypeId(rs.getInt(1));
				datetype.setSensingDeviceID(rs.getInt(2));
				datetype.setType(rs.getString(3));
				datetype.setMark(rs.getString(4));
				datetype.setSymbol(rs.getString(5));
				
				//将创建时间转换为String
				DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String createTime = "";
				try{
						createTime = sdf.format(rs.getTimestamp(6));   
			       } catch (Exception e) {  
			            e.printStackTrace();  
			    }  
				datetype.setCreateTime(createTime);
				
				DataTypeList.add(datetype);
			}
			System.out.println("getDataTypeListByDeviceID: "+DataTypeList.toString());
			return DataTypeList;
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
		return DataTypeList;
	}
	
	public int countConfigLogByDeviceID(int DeviceID ,String start_date ,String end_date){
		ResultSet rs = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		int result = 0;
		String sql = "SELECT *"
				+ " FROM `configlog`"
				+ " WHERE `ConfigTypeID` in (select ConfigTypeID from configtype where ControllingDeviceID = ?)"
				+ " AND `Savetime` >= ?"
				+ " AND `Savetime` <= ?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, DeviceID);
			pstmt.setString(2, start_date);
			pstmt.setString(3, end_date);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				result=rs.getInt(1);
			}
			System.out.println("countConfigLogByDeviceID: "+result);
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
	
	public List<Configlog> getConfigLogByDeviceID(int DeviceID ,String start_date ,String end_date ,int limite ,int offset){
		List<Configlog> ConfigLogList = new ArrayList<Configlog>(); 
		Configlog configlog;
		
		String sql = "SELECT `configlog`.*,`configtype`.*"
				+ " FROM `configlog`,`configtype`"
				+ " WHERE `configlog`.`ConfigTypeID` in (select ConfigTypeID from configtype where ControllingDeviceID = ?)"
				+ " AND `configlog`.`Savetime` >= ?"
				+ " AND `configlog`.`Savetime` <= ?"
				+ " AND `configlog`.`ConfigTypeID` =`configtype`.`ConfigTypeID`"
				+ " ORDER BY `configlog`.`Savetime` DESC";
		if(limite>0)
			sql = sql + " limit ?,?";
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, DeviceID);
			pstmt.setString(2, start_date);
			pstmt.setString(3, end_date);
			if(limite>0)
			{
				pstmt.setInt(4, offset);
				pstmt.setInt(5, limite);
			}

			rs = pstmt.executeQuery();
			while (rs.next()) {
				configlog = new Configlog();
				configlog.setConfigLogID(rs.getInt(1));
				configlog.setConfigTypeID(rs.getInt(2));
				configlog.setConfigContent(rs.getString(3));
				configlog.setConfigTypeName(rs.getString(7));
				configlog.setMark(rs.getString(8));
				
				//将创建时间转换为String
				DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String createTime = "";
				try{
						createTime = sdf.format(rs.getTimestamp(4));   
			       } catch (Exception e) {  
			            e.printStackTrace();  
			    }  
				configlog.setSaveTime(createTime);
				
				ConfigLogList.add(configlog);
			}
			System.out.println("getConfigLogByDeviceID: "+ConfigLogList.toString());
			return ConfigLogList;
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
		return ConfigLogList;
	}
	
	public int countDataLogByDeviceID(int DeviceID ,String start_date ,String end_date){
		ResultSet rs = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		int result = 0;
		String sql = "SELECT * FROM `devicedata`"
				+ " WHERE `DataTypeID` in (select DataTypeID from datatype where SensingDeviceID = ?)"
				+ " AND `Savetime` >= ?"
				+ " AND `Savetime` <= ?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, DeviceID);
			pstmt.setString(2, start_date);
			pstmt.setString(3, end_date);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				result=rs.getInt(1);
			}
			System.out.println("countDataLogByDeviceID: "+result);
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
	
	public List<Devicedata> getDataLogByDeviceID(int DeviceID ,String start_date ,String end_date ,int limite ,int offset){
		List<Devicedata> DataLogList = new ArrayList<Devicedata>(); 
		Devicedata datalog;
		
		String sql = "SELECT `devicedata`.*,`datatype`.* FROM `devicedata`,`datatype`"
				+ " WHERE `devicedata`.`DataTypeID` in (select DataTypeID from datatype where SensingDeviceID = ?)"
				+ " AND `devicedata`.`Savetime` >= ?"
				+ " AND `devicedata`.`Savetime` <= ?"
				+ " AND `devicedata`.`DataTypeID`=`datatype`.`DataTypeID`"
				+ " ORDER BY `devicedata`.`Savetime` DESC";
		if(limite>0)
			sql = sql + " limit ?,?";
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, DeviceID);
			pstmt.setString(2, start_date);
			pstmt.setString(3, end_date);
			if(limite>0)
			{
				pstmt.setInt(4, offset);
				pstmt.setInt(5, limite);
			}

			rs = pstmt.executeQuery();
			while (rs.next()) {
				datalog = new Devicedata();
				datalog.setDeviceDataID(rs.getInt(1));
				datalog.setDataTypeID(rs.getInt(2));
				datalog.setValue(rs.getFloat(3));
				datalog.setType(rs.getString(7));
				datalog.setMark(rs.getString(8));
				datalog.setSymbol(rs.getString(9));
				
				//将创建时间转换为String
				DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String createTime = "";
				try{
						createTime = sdf.format(rs.getTimestamp(4));   
			       } catch (Exception e) {  
			            e.printStackTrace();  
			    }  
				datalog.setSaveTime(createTime);
				
				DataLogList.add(datalog);
			}
			System.out.println("getDataLogByDeviceID: "+DataLogList.toString());
			return DataLogList;
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
		return DataLogList;
	}
}