package java_jdbc_batch;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

import java.sql.Connection;

import java_jdbc_GetDatabaseLinked.JDBCTools;

public class BatchTest {
	
	@Test
	public void testBatch() {
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = null;
		try {
			conn = JDBCTools.getConnection();
			conn.setAutoCommit(false);
			sql = "insert into customers values(?,?,?,?)";
			ps = conn.prepareStatement(sql);
			
			long begin = System.currentTimeMillis();
			for(int i = 0; i < 10; i++) {
				ps.setString(1, "no_" + (i + 1));
				ps.setString(2, "email_" + i);
				ps.setDate(3, new Date(new java.util.Date().getTime()));
				ps.setInt(4, (i + 1));
				//积攒 sql
				ps.addBatch();
				//当积攒到一定量就统一执行一次，并清空先前积攒的sql
				if((i + 1) % 300 == 0) {
					ps.executeBatch();
					ps.clearBatch();
				}
			}
			//若总条数不是批量数值的整数倍，则还需要再额外执行一次
			if(10 % 300 != 0) {
				ps.executeBatch();
				ps.clearBatch();
			}
			
			conn.commit();
			
			long end = System.currentTimeMillis();
			System.out.println(end - begin);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			JDBCTools.release(null, ps, conn);
		}
	}
	
	@Test
	public void testBatchWithPrepareStatement() {
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = null;
		try {
			conn = JDBCTools.getConnection();
			conn.setAutoCommit(false);
			sql = "insert into customers values(?,?,?)";
			ps = conn.prepareStatement(sql);
			
			long begin = System.currentTimeMillis();
			for(int i = 0; i < 10000; i++) {
				ps.setString(1, "no_" + i + 1);
				ps.setString(2, "email_" + i);
				ps.setDate(3, new Date(new java.util.Date().getTime()));
				
				ps.executeUpdate();
			}
			conn.commit();
			
			long end = System.currentTimeMillis();
			System.out.println(end - begin);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			JDBCTools.release(null, ps, conn);
		}
	}
	
	/**
	 * 像数据表中插入10000条数据
	 * 测试如何插入用时最短
	 * 1.使用Statement
	 * 2.使用PrepareStatement
	 */
	@Test
	public void testBatchWithStatement() {
		Connection conn = null;
		Statement statement = null;
		String sql = null;
		try {
			conn = JDBCTools.getConnection();
			conn.setAutoCommit(false);
			
			statement = conn.createStatement();
			long begin = System.currentTimeMillis();
			for(int i = 0; i < 10000; i++) {
				sql = "insert into customers values('" + (i + 1) + "','name_" + i + "','2020-7-12')";
				statement.executeUpdate(sql);
			}
			conn.commit();
			
			long end = System.currentTimeMillis();
			System.out.println(end - begin);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			JDBCTools.release(null, statement, conn);
		}
		
	}

}
