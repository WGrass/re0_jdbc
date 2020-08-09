package java_jdbc_ConnectionPool;

import java.sql.Connection;

import javax.sql.DataSource;

import org.junit.Test;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java_jdbc_GetDatabaseLinked.JDBCTools;


public class C3P0Test {

	@Test
	public void testJDBCTools() throws Exception {
		Connection conn = JDBCTools.getConnection();
		System.out.println(conn);
		conn.close();
	}
	
	/**
	 * 1.创建c3p0-config.xml文件，参考index附录B
	 * 2.创建 ComboPooledDataSource 实例
	 * 3.从实例中获取数据库连接
	 * @throws Exception
	 */
	@Test
	public void testc3p0WithConfigFile() throws Exception {
		DataSource ds = new ComboPooledDataSource("helloc3p0");
		
		System.out.println(ds.getConnection());
		
		ComboPooledDataSource cpds = (ComboPooledDataSource) ds;
		System.out.println(cpds.getMaxPoolSize());
		
		cpds.close();
	}
	
	/**
	 * 也要导入两个包
	 * 		c3p0.jar
	 * 		mchange-commons-java.jar
	 * @throws Exception
	 */
	@Test
	public void test() throws Exception {
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		cpds.setDriverClass( "com.mysql.jdbc.Driver" ); //loads the jdbc driver            
		cpds.setJdbcUrl( "jdbc:mysql://127.0.0.1:3306/atguigu?useSSL=false" );
		cpds.setUser("root");                                  
		cpds.setPassword("090031"); 
		
		System.out.println(cpds.getConnection());
		cpds.close();
	}
	
}
