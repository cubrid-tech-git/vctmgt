package com.cubrid.dao;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * 
 * @author HUN
 *
 *	DB Connection Class - MySQL
 */
public class MyConnectionManager {
	final String CLASSNAME = "cubrid.jdbc.driver.CUBRIDDriver"; 
	final String URL = "jdbc:cubrid:192.168.0.233:33100:vctmgt:::?charset=UTF-8";
	final String db_user = "manager";
	final String db_pwd = "manager123";
	
	private Connection conn;
	
//	public Connection getConnection() throws Exception {
	private Connection getConnection() throws Exception {
		Class.forName(CLASSNAME);
		conn = DriverManager.getConnection(URL, db_user, db_pwd);
		conn.setAutoCommit(false);
		return conn;
	}
}
