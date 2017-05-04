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

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.njupt.tools.Tools;

//import net.sf.json.JSONArray;

public class Dao {
	
	private static final String url = "jdbc:mysql://localhost:3306/iot_management";
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
	
	/**
	 * �ж��û��Ƿ�Ϸ�
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
		System.out.println("�û���������룺"+b);
		String a = "";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			rs = pstmt.executeQuery();
			if (rs.next()) {
//				a = Tools.getMD5Str(rs.getString(4));
				a = rs.getString(4);
				System.out.println("�û�ע�������ݿ��е����룺"+a);
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
	 * �û�ע��
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
			System.out.println("���ܺ�����룺"+encrypted_password);
			
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
					return "{\"res\":\"success\"}";			
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
			return "{\"res\":\"failed\"}";
		}else{
			System.out.println("userRegister: �û����Ѵ���");
			return "{\"res\":\"�û����Ѵ���\"}";
		}
	}
	
	/**
	 * ע��ʱ�����ж��û����Ƿ����
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
	
}