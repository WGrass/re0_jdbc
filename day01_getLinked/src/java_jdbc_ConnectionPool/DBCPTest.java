package java_jdbc_ConnectionPool;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.junit.Test;

public class DBCPTest {
	/**
	 * 1.加载properties配置文件
	 * 		键值需要来自B阿思翠DATa Source的属性
	 * 2.调用BasicDataSourceFactory的静态方法创建实例
	 * 3.获取连接
	 * @throws Exception
	 */
	@Test
	public void testDBCPWithDataSourceFactory() throws Exception {
		Properties properties = new Properties();
		InputStream inStream = DBCPTest.class.getClassLoader().getResourceAsStream("dbcp.properties");
		properties.load(inStream);
		
		DataSource ds = BasicDataSourceFactory.createDataSource(properties);
		
		System.out.println(ds.getConnection());
		
		BasicDataSource basicDataSource = (BasicDataSource) ds;
		System.out.println(basicDataSource.getMaxWaitMillis());
	}
	
	/**
	 * 使用DBCP数据库连接池
	 * 1.加入jar包(2)
	 * 		依赖于 commons pool
	 * 2.创建数据库连接池
	 * 3.为数据源实例指定必须的属性
	 * 4.从数据源中获取数据库连接
	 * @throws SQLException 
	 */
	BasicDataSource ds = null;
	@SuppressWarnings("resource")
	@Test
	public void testDBCP() throws SQLException {
		
		//创建DBPC数据源实例
		ds = new BasicDataSource();
		
		//2.为数据源实例指定必须的属性
		ds.setUsername("root");
		ds.setPassword("090031");
		ds.setUrl("jdbc:mysql:///atguigu");
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		
		//2.5指定数据源可选的属性
//		指定数据库连接池中初始化连接数
		ds.setInitialSize(5);
		
		//指定最大连接数:空闲状态下最多的连接
		//setMaxActive()方法没了
		ds.setMaxIdle(5);
		
		//等待数据库连接池分配链接的最长时间，单位为毫秒
		ds.setMaxWaitMillis(1000 * 5);
	
		//3.从数据源中获取数据库连接
		Connection conn1 = ds.getConnection();
		System.out.println(conn1.getClass());
		Connection conn2 = ds.getConnection();
		System.out.println(conn2.getClass());
		Connection conn3 = ds.getConnection();
		System.out.println(conn3.getClass());
		Connection conn4 = ds.getConnection();
		System.out.println(conn4.getClass());
		Connection conn5 = ds.getConnection();
		System.out.println("5555" + conn5.getClass());
		
		new Thread() {
			public void run() {
				Connection conn;
				try {
					conn = ds.getConnection();
					System.out.println(conn.getClass());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		conn5.close();
	}

}
