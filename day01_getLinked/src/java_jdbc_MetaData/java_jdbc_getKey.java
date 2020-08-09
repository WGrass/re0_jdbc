package java_jdbc_MetaData;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.Test;

import java.sql.Connection;

import java_jdbc_GetDatabaseLinked.JDBCTools;

public class java_jdbc_getKey {
	
	/**
	 * ȡ�����ݿ��Զ����ɵ�����ֵ
	 * Ϊʲô�������Ҫ��
	 * 		
	 */
	@Test
	public void testGetKey() {
		
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = JDBCTools.getConnection();
			String sql = "insert into customers(name, email, birth) values(?,?,?)";
//			ps = conn.prepareStatement(sql);
			//1.ʹ�����ص�PrepareStatement()����
			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			ps.setString(1, "asdf");
			ps.setString(2, "asdf.com");
			ps.setDate(3, new Date(new java.util.Date().getTime()));
			ps.executeUpdate();
			
			//2.ͨ��getGeneratedKeys();������ȡ�����ɵ�����ResultSet
			ResultSet rs = ps.getGeneratedKeys();
			if(rs.next()) {
				System.out.println(rs.getObject(1));
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			JDBCTools.release(null, ps, conn);
		}
		
	}

}
