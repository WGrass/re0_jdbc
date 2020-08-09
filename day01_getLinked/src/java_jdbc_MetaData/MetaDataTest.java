package java_jdbc_MetaData;


import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import org.junit.Test;

import java.sql.Connection;

import java_jdbc_GetDatabaseLinked.JDBCTools;

public class MetaDataTest {
	/**
	 * ResultSetMetaData �����������Ԫ����
	 * ���Եõ�������еĻ�����Ϣ �������������Щ�У��������еı���
	 */
	@Test
	public void testResultSetMetaData() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = JDBCTools.getConnection();
			String sql = "select * from customers";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			//1.�õ�ResultSetMetaData����
			ResultSetMetaData rsmd = rs.getMetaData();
			
			int columnCount = rsmd.getColumnCount();
			
			for(int i = 0; i < columnCount; i++) {
				String columnName = rsmd.getColumnName(i + 1);
				
				String columnLable = rsmd.getColumnLabel(i + 1);
				System.out.println(columnName + "," + columnLable);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCTools.release(rs, ps, conn);
		}
		
	}
	
	
	/**
	 * DatabaseMetaData ʱ���� ���ݿ� ��Ԫ���ݶ���
	 * ������Connection�õ�
	 * �˽�
	 */
	@Test
	public void testDataBaseMetaData() {
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = JDBCTools.getConnection();
			DatabaseMetaData data = conn.getMetaData();
			
			//���Եõ����ݿⱾ���һЩ������Ϣ
			int version = data.getDatabaseMajorVersion();
			System.out.println(version);
			
			//���ӵ����ݿ���û���
			String userName = data.getUserName();
			System.out.println(userName);
			
			//�õ�mysql������Щ���ݿ�
			rs = data.getCatalogs();
			while(rs.next()) {
				System.out.println(rs.getString(1));
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTools.release(rs, null, conn);
		}
		
		
	}

}
