package java_jdbc_MetaData;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.junit.Test;

import java.sql.Connection;

import java_jdbc_GetDatabaseLinked.JDBCTools;

public class BLOBTest {
	/**
	 * 1.ʹ��getBlob()��������ResultSet������еõ�Blob����
	 * 2.����Blob��getBinaryStream�õ�������
	 * 3.��ʹ��IO����
	 */
	@Test
	public void readBlob() {
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = JDBCTools.getConnection();
			String sql = "select * from customers where id = 8";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				int id = rs.getInt(1);
				String name = rs.getString(2);
				String email = rs.getString(3);
				
				System.out.println(id + "," + name + "," + email);
				
				Blob blob = rs.getBlob(5);
				InputStream in = blob.getBinaryStream();
				OutputStream out = new FileOutputStream("app.jpg");
				
				byte[] buffer = new byte[512];
				int len = 0;
				while((len = in.read(buffer)) != -1) {
					out.write(buffer, 0, len);
				}
				
				out.close();
				in.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCTools.release(rs, ps, conn);
		}
		
	}

	/**
	 * ����BLOB���͵����ݱ���ʹ��PreparedStatement����ΪBLOB����
	 * �������޷�ʹ���ַ���ƴд��
	 * 
	 * ����SetBlob(int index��InputStream inputStream)����
	 */
	@Test
	public void testInsertBlob() {
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = JDBCTools.getConnection();
			String sql = "insert into customers(name, email, birth, picture) values(?,?,?,?)";
//			ps = conn.prepareStatement(sql);
			//1.ʹ�����ص�PrepareStatement()����
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, "asdf");
			ps.setString(2, "asdf.com");
			ps.setDate(3, new Date(new java.util.Date().getTime()));
			
			InputStream inputStream = new FileInputStream("apple.jpg");
			ps.setBlob(4, inputStream);
			ps.executeUpdate();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			JDBCTools.release(null, ps, conn);
		}
	}
	
}
