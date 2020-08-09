package java_jdbc_GetDatabaseLinked;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.sql.Connection;

import java_jdbc_exer.ReflectionUtils;

public class JDBCTools {

	/**
	 * �P�]Statement��Connection
	 * 
	 * @param statement
	 * @param conn
	 */
	public static void release(ResultSet rs, Statement statement, Connection conn) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		}
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				/*
				 * 进行close操作，并不是真的关闭，而是把链接换个俩劫持
				 */
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * DataConnectionPool
	 * is initialed by only once
	 */
	private static DataSource dataSource = null;
	static {
		dataSource = new ComboPooledDataSource("helloc3p0");
	}
	
	public static Connection getConnection() throws Exception {
		return dataSource.getConnection();
	}
//	public static Connection getConnection() throws Exception {
//		String driverClass = null;
//		String jbdcUrl = null;
//		String user = null;
//		String password = null;
//		
//		InputStream in = JDBCTools.class.getClassLoader().getResourceAsStream("jdbc.properties");
//		Properties properties = new Properties();
//		properties.load(in);
//		driverClass = properties.getProperty("driver");
//		jbdcUrl = properties.getProperty("jdbcUrl");
//		user = properties.getProperty("user");
//		password = properties.getProperty("password");
//	
//		Driver driver = (Driver) Class.forName(driverClass).newInstance();
//		Properties info = new Properties();
//		info.put("user", user);
//		info.put("password", password);
//		Connection conn = (Connection) driver.connect(jbdcUrl, info);
//
//		return conn;
//	}

	/**
	 * ͨ�õĸ��·���������INSERT,UPDATE,DELETE �汾1
	 */
	public static void update(String sql) {
		Connection conn = null;
		Statement statement = null;

		try {
			conn = JDBCTools.getConnection();
			statement = conn.createStatement();
			statement.executeUpdate(sql);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			JDBCTools.release(null, statement, conn);
		}
	}

	/**
	 * ͨ�ø��£�ʹ��PreparedStatement
	 * 
	 * @param sql
	 * @param args
	 */
	public static void update(String sql, Object... args) {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = JDBCTools.getConnection();
			ps = conn.prepareStatement(sql);

			for (int i = 1; i < args.length; i++) {
				ps.setObject(i + 1, args[i]);
			}

			ps.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			JDBCTools.release(null, ps, conn);
		}
	}

	public static <T> T get(Class<T> clazz, String sql, Object... args) {
		T entity = null;

		Connection conn = null;
		java.sql.PreparedStatement ps = null;
		ResultSet resultSet = null;

		try {
			conn = JDBCTools.getConnection();
			ps = conn.prepareStatement(sql);
			for (int i = 0; i < args.length; i++) {
				ps.setObject(i + 1, args[i]);
			}

			resultSet = ps.executeQuery();
			Map<String, Object> values = new HashMap<String, Object>();
			// �õ�ResultSetMetaData ����
			ResultSetMetaData rsmd = resultSet.getMetaData();
			// ͨ������sql������жϵ���ѡ������Щ��
			// 3.��ȡ������еı���
			while (resultSet.next()) {
				for (int i = 0; i < rsmd.getColumnCount(); i++) {
					String columnLable = rsmd.getColumnLabel(i + 1);
					Object columnValue = resultSet.getObject(columnLable);
					// 4.�ٻ�ȡ�����ÿһ�е�ֵ�����3�õ�һ��Map��ֵ��
					values.put(columnLable, columnValue);
				}
			}
			if (values.size() > 0) {
				entity = clazz.newInstance();
				// 5.�����÷���Ϊ2�Ķ�Ӧ�����Ը�ֵ
				for (Map.Entry<String, Object> entry : values.entrySet()) {
					String fieldName = entry.getKey();
					Object fieldValue = entry.getValue();

					ReflectionUtils.setFieldValue(entity, fieldName, fieldValue);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			JDBCTools.release(resultSet, ps, conn);
		}

		return entity;
	}

}
