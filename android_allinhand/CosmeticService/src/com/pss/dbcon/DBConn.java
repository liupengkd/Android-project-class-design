package com.pss.dbcon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConn {
	
	public final static String DRIVER = "com.mysql.jdbc.Driver"; // ���ݿ�����
	
	public final static String URL = "jdbc:mysql://localhost:3306/cosmeticdb"; // url

	public final static String DBNAME = "root"; // ���ݿ��û���

	public final static String DBPASS = "root"; // ���ݿ�����

	protected Connection conn = null; // �������ݿ�����

	protected PreparedStatement pstmt = null; // ����ִ��SQL���

	protected ResultSet rs = null; // �û������ѯ�����

	/**
	 * �õ����ݿ�����
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Connection getConn() throws ClassNotFoundException, SQLException {
		Class.forName(DRIVER); // ע������
		conn = DriverManager.getConnection(URL, DBNAME, DBPASS); // ������ݿ�����
		return conn;
	}

	/**
	 * �ͷ���Դ
	 * 
	 */
	public void closeAll() {
		/* ���rs���գ��ر�rs */
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		/* ���pstmt���գ��ر�pstmt */
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		/* ���conn���գ��ر�conn */
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
	 * ִ��SQL��䣬���Խ�������ɾ���ĵĲ���������ִ�в�ѯ
	 * 
	 * @param sql
	 *            Ԥ����� SQL ���
	 * @param param
	 *            Ԥ����� SQL ����еġ������������ַ�������
	 * @return Ӱ�������
	 */
	public int executeSQL(String preparedSql, String[] param) {
		int num = 0;

		/* ����SQL,ִ��SQL */
		try {
			this.getConn(); // �õ����ݿ�����
			pstmt = conn.prepareStatement(preparedSql); // �õ�PreparedStatement����
			if (param != null) {
				for (int i = 0; i < param.length; i++) {
					pstmt.setString(i + 1, param[i]); // ΪԤ����sql���ò���
				}
			}
			num = pstmt.executeUpdate(); // ִ��SQL���
		} catch (ClassNotFoundException e) {
			e.printStackTrace(); // ����ClassNotFoundException�쳣
		} catch (SQLException e) {
			e.printStackTrace(); // ����SQLException�쳣
		} finally {
			this.closeAll(); // �ͷ���Դ
		}
		return num;
	}

}
