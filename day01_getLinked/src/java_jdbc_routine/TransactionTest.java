package java_jdbc_routine;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import java.sql.Connection;

import java_jdbc_GetDatabaseLinked.JDBCTools;

public class TransactionTest {

	@Test
	public void testTransactionIsolution() {
		Connection conn = null;
		
		try {
			conn = JDBCTools.getConnection();
			conn.setAutoCommit(false);
			
			String sql = "update customers set email = '123456' where id = 1";
			update(conn,sql);
			
			conn.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			JDBCTools.release(null, null, conn);
		}
	}
	@Test
	public void test2() {
		String sql = "select email from customers where id = 1";
		Object obj = getForValue(sql);
		System.out.println(obj);
	}
	
	
	@SuppressWarnings("unchecked")
	public <E> E getForValue(String sql, Object... args) {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet resultSet = null;

		try {
			conn = JDBCTools.getConnection();
			conn.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
			
			ps = conn.prepareStatement(sql);

			for (int i = 0; i < args.length; i++) {
				ps.setObject(i + 1, args[i]);
			}
			resultSet = ps.executeQuery();
			
			if(resultSet.next()) {
				return (E) resultSet.getObject(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCTools.release(resultSet, ps, conn);
		}
		return null;
	}
	
	
	@Test
	public void testTransaction() {
		
//		DAO dao = new DAO();
//		
//		String sql = "update customers set id = id + 10 where id = 1";
//		dao.updata(sql);
//		
//		int i = 10 / 0;
//		System.out.println(i);
//		
//		String sql1 = "update customers set id = id - 1 where id = 2";
//		dao.updata(sql1);
		
		Connection conn = null;
		
		try {
			conn = JDBCTools.getConnection();
			conn.setAutoCommit(false);
			
			String sql = "update customers set id = id + 10 where id = 1";

			update(conn,sql);
			int i = 10 / 0;
			System.out.println(i);
			
			sql = "update customers set id = id - 1 where id = 2";
		    update(conn,sql);
		    
		    conn.commit();
		    
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			JDBCTools.release(null, null, conn);
		}
		
	}
	

	public void update(Connection conn, String sql, Object... args) {
		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(sql);

			for (int i = 0; i < args.length; i++) {
				ps.setObject(i + 1, args[i]);
			}

			ps.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			JDBCTools.release(null, ps, null);
		}
	}
	
}
