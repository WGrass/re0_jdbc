package java_jdbc_GetDatabaseLinked;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Properties;

import org.junit.jupiter.api.Test;

import java.sql.Connection;

/**
 * 
 * @author Lifeiyang
 * 
 * @Data 2020��5��30��
 */
public class java_JDBCTest {
	/**
	 * ResultSet�����������װ��ʹ��JDBC���в�ѯ�Ľ�� 1.����Statement�����executeQuery(sql)�������Եõ������
	 * 2.ResultSet ���ص�ʵ���Ͼ���һ�����ݱ���һ��ָ��ָ�����ݱ�ĵ�һ�С�
	 * ���Ե���next()�����������һ���Ƿ���Ч������Ч������true����ָ�����ơ� �൱��Iterater����hasnext()��next()�����Ľ���塣
	 * 3.��ָ�붨λ��һ��ʱ������ͨ������getXxx(index)��getXxx(columnName)
	 * ��ȡÿһ�е�ֵ�����磺getInt(1),getString("name") 4.��ȻҲ��Ҫ�رա�
	 */
	@Test
	public void testResultSet() {
		// ��ȡid����4��customers ���ݱ�ļ�¼������ӡ
		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;

		try {
			// 1.��ȡ���ݿ�����
			conn = JDBCTools.getConnection();
			
			// 2.��ȡStatement
			statement = conn.createStatement();
			
			// 3.׼��SQL
			String sql = "select *" + "from customers";
			// 4.ִ�в�ѯ���õ�ResultSet
			rs = statement.executeQuery(sql);
			
			// 5.����RsultSet
			while(rs.next()) {
				int id = rs.getInt(1);
				String name = rs.getString("name");
				String email = rs.getString(3);
				Date birth = rs.getDate(4);
				
				System.out.print(id);
				System.out.print(name);
				System.out.print(email);
				System.out.println(birth);
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			// 6.�ر���Դ
			JDBCTools.release(rs, statement, conn);
		}
	}

	/**
	 * ͨ�õĸ��·���������INSERT,UPDATE,DELETE �汾1
	 * 
	 */
	public void update(String sql) {
		Connection conn = null;
		Statement statement = null;

		try {
			conn = JDBCTools.getConnection();
			statement = conn.createStatement();
			statement.executeUpdate(sql);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			JDBCTools.release(null,statement, conn);
		}
	}

	/**
	 * ��ָ�������ݱ��в���һ����¼
	 * 
	 * 1.Statement����ִ��SQL���Ķ��� ͨ��Connection��createStatement()��������ȡ
	 * ͨ��executeUpdate(sql)����ִ��SQL���
	 * 
	 * 2.Connection��Statement����Ӧ�ó�������ݿ��������������Դ��ʹ�ú�һ��Ҫ�ر�
	 * 
	 * 3.�رյ�˳���ȹرպ��ȡ�ġ� ���ȹر�Statement ��ر�Connection
	 * 
	 * @throws Exception
	 * 
	 */
	@Test
	public void testStatement() throws Exception {
		// 1.��ȡ���ݿ�����
		Connection conn = null;
		Statement statement = null;

		try {
			conn = JDBCTools.getConnection();

			// 3.׼�������SQL���
//			String sql0 = "use atguigu";
//			String sql = "insert into customers(name,email,birth)" + "values('Joh','zxcvbnm.com','2000-8-8')";

		
			String sql = "insert into stuinfo values(4,'Jerry','B',0010,12,1000)";
			
			// 4.ִ�в���
			// 4.1��ȡSQL��������Statement����
			statement = conn.createStatement();
			// 4.2����Statement�����executeUpdate(sql)ִ��SQL�����в���
			//statement.executeUpdate(sql0);
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 5.�ر�Statement����
			try {
				if (statement != null)
					statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				// 2.�ر�����
				if (conn != null)
					conn.close();
			}

		}

	}

	/**
	 * DriverManger�������Ĺ�����
	 * 
	 * @throws SQLException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * 
	 *                                �ô��� ���Թ��������ݿ�������ע�������ݿ�����
	 *                                ����ͨ�����ص�getConnection()������ȡ���ݿ����ӣ���Ϊ����
	 */
	@Test
	public void testDriverManger() throws Exception {
		// ������ȫ����
		String driverClass = null;
		// url
		String jbdcUrl = null;
		// user
		String user = null;
		String password = null;

		// ��ȡ��·���µ�jdbc.properties�ļ�
		InputStream in = getClass().getClassLoader().getResourceAsStream("jdbc.properties");
		Properties properties = new Properties();
		properties.load(in);
		driverClass = properties.getProperty("driver");
		jbdcUrl = properties.getProperty("jdbcUrl");
		user = properties.getProperty("user");
		password = properties.getProperty("password");

		// 2.�������ݿ��������򣨶�Ӧ��Driverʵ��������ע�������ľ�̬����飩
//		DriverManager.registerDriver((Driver) Class.forName(driverClass).newInstance());
		Class.forName(driverClass);

		// 3.ͨ��DriverManeger�ӿڵ�getConnection()������ȡ���ݿ�������
		Connection conn = (Connection) DriverManager.getConnection(jbdcUrl, user, password);
		System.out.println(conn);

	}

	/*
	 * Driver��һ���ӿڣ����ݿ⳧�̱����ṩʵ�ֵĽӿڣ��ܴ����л�ȡ���ݿ����� ����ͨ��Driver��ʵ��������ȡ���ݿ����ӡ�
	 * 
	 * 1.����mysql���� 2.����jar��
	 * 
	 */
	@Test
	public void testDriver() throws SQLException {
		// 1.����һ��Driverʵ����Ķ���
		Driver driver = new com.mysql.jdbc.Driver();

		String url = "jdbc:mysql://localhost:3306/MySQL?useSSL=false";
		Properties info = new Properties();
		info.put("user", "root");
		info.put("password", "090031");

		// 2.����Driver�ӿڵ�connect()������ȡ���ݿ�����
		Connection conn = (Connection) driver.connect(url, info);
		System.out.println(conn);
	}

}
