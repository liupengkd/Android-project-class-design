package com.pss.dbcon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConn {
	
	public final static String DRIVER = "com.mysql.jdbc.Driver"; // 数据库驱动
	
	public final static String URL = "jdbc:mysql://localhost:3306/cosmeticdb"; // url

	public final static String DBNAME = "root"; // 数据库用户名

	public final static String DBPASS = "root"; // 数据库密码

	protected Connection conn = null; // 保存数据库连接

	protected PreparedStatement pstmt = null; // 用于执行SQL语句

	protected ResultSet rs = null; // 用户保存查询结果集

	/**
	 * 得到数据库连接
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Connection getConn() throws ClassNotFoundException, SQLException {
		Class.forName(DRIVER); // 注册驱动
		conn = DriverManager.getConnection(URL, DBNAME, DBPASS); // 获得数据库连接
		return conn;
	}

	/**
	 * 释放资源
	 * 
	 */
	public void closeAll() {
		/* 如果rs不空，关闭rs */
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		/* 如果pstmt不空，关闭pstmt */
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		/* 如果conn不空，关闭conn */
		try {
			if (conn != null && conn.isClosed() == false) {
				conn.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 执行SQL语句，可以进行增、删、改的操作，不能执行查询
	 * 
	 * @param sql
	 *            预编译的 SQL 语句
	 * @param param
	 *            预编译的 SQL 语句中的‘？’参数的字符串数组
	 * @return 影响的条数
	 */
	public int executeSQL(String preparedSql, String[] param) {
		int num = 0;

		/* 处理SQL,执行SQL */
		try {
			this.getConn(); // 得到数据库连接
			pstmt = conn.prepareStatement(preparedSql); // 得到PreparedStatement对象
			if (param != null) {
				for (int i = 0; i < param.length; i++) {
					pstmt.setString(i + 1, param[i]); // 为预编译sql设置参数
				}
			}
			num = pstmt.executeUpdate(); // 执行SQL语句
		} catch (ClassNotFoundException e) {
			e.printStackTrace(); // 处理ClassNotFoundException异常
		} catch (SQLException e) {
			e.printStackTrace(); // 处理SQLException异常
		} finally {
			this.closeAll(); // 释放资源
		}
		return num;
	}

}
