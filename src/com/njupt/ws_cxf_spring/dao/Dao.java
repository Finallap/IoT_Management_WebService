package com.njupt.ws_cxf_spring.dao;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.net.URI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mchange.v2.c3p0.ComboPooledDataSource;

//import net.sf.json.JSONArray;

public class Dao {
	
	private static final String url = "jdbc:mysql://localhost:3306/test";
	private static final String user = "root";
	private static final String userpass = "";
	private static ComboPooledDataSource ds;
	private ResultSet rs;
	private Connection conn;
	private PreparedStatement pstmt;
	private static final String driver = "com.mysql.jdbc.Driver";
	
	public String sayHello(){
		return "Hello World!";
	}
	
	/**
     * ��ʼ�����ӳش����,ȫ�־�̬�����
     */
	static{
		initDB();
	}
	
	/**
	 * ��ʼ�����ݿ�������������ӳ�,ʹ�õ���C3PO����Դ
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
		//���ӵ�������ʱ�䣬����������ʱ�䣬ĳ�����ݿ����ӻ�û�б�ʹ�ã����Ͽ����������,��λ��  
		//ds.setMaxIdleTime(10);
		//�������ӵ�����ʱ�䣬�������ʱ������ӽ������ӳ��Զ��Ͽ�����������Ȼ����ʹ�õ����Ӳ������϶Ͽ������ǵȴ���close�ٶϿ���
		//ds.setMaxConnectionAge(50);
	}
	
	public int insertstudent(String name) {
		
		String sql = "INSERT INTO `test`.`student` (`name`) VALUES (?);";
		int flag=0;
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			flag = pstmt.executeUpdate();
			return flag;
		}catch(Exception e){
			e.printStackTrace();
		} finally {
			try{
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return flag;
	}
	
	public String getStudentnamebyID(int ID){
		String name = "";
		String sql = "SELECT * FROM `student` WHERE `ID` = ?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ID);
			rs = pstmt.executeQuery();
			System.out.println("getUserIDByUsername(String username)");
			while (rs.next()) {
				name=rs.getString(1);
			}
			return name;
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
		return name;
	}
}