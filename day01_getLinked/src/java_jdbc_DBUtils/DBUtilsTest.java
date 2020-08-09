package java_jdbc_DBUtils;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.jupiter.api.Test;

import java_jdbc_GetDatabaseLinked.JDBCTools;
/**
 * 测试DBUtils工具类
 * @author Lifeiyang
 * 
 * @Data 2020年7月13日
 */
class DBUtilsTest {
	QueryRunner qr = new QueryRunner();
	
	/**
	 * ScalarHandler
	 */
	@Test
	public void testScalarHandler() {
		Connection conn = null;
		try {
			
			conn = JDBCTools.getConnection();
			String sql = "select count(id) from customers";
			
			Object result = qr.query(conn, sql, new ScalarHandler<Customer>());
			
			System.out.println(result);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			JDBCTools.release(null, null, conn);
		}
	}
	
	/**
	 * MapListHandler:
	 */
	
	/**
	 * MapHandler:返回一条记录
	 * 键：sql 查询的列名（不是列的别名）
	 * 值：对应的值
	 */
	@Test
	public void testMapHandler() {
		Connection conn = null;
		try {
			
			conn = JDBCTools.getConnection();
			String sql = "select id, name, email, birth from customers";
			
			Map<String, Object>result = qr.query(conn, sql, new MapHandler());
			
			System.out.println(result);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			JDBCTools.release(null, null, conn);
		}
	}
	
	/**
	 * BeanListHandler:吧结果集转为一个List，该List不为null，但可能为空集合
	 */
	@Test
	public void testBeanListHandler() {
		Connection conn = null;
		
		try {
			
			conn = JDBCTools.getConnection();
			String sql = "select id, name, email, birth from customers";
			
			List<Customer> customers = new ArrayList<Customer>();
			customers = qr.query(conn, sql, new BeanListHandler<Customer>(Customer.class));
			
			System.out.println(customers);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			JDBCTools.release(null, null, conn);
		}
	}
	
	/**
	 * BeanHandler:把结果集的第一条记录转为BeanHandler传入的Class参数的类型
	 */
	@Test
	public void testBeanHandler() {
		Connection conn = null;
		
		try {
			
			conn = JDBCTools.getConnection();
			String sql = "select id, name, email, birth from customers where id = ?";
			
			Customer customer = qr.query(conn, sql, new BeanHandler<Customer>(Customer.class), 5);
			
			System.out.println(customer);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			JDBCTools.release(null, null, conn);
		}
		
	}
	
	class MyResultHandler implements ResultSetHandler<Object>{

		@Override
		public Object handle(ResultSet rs) throws SQLException {
			System.out.println("handle...");
			
			List<Customer> customers = new ArrayList<Customer>();
			while(rs.next()) {
				Integer id = rs.getInt(1);
				String name = rs.getString(2);
				String email = rs.getString(3);
				Date birth = rs.getDate(4);
				
				Customer customer = new Customer(id, name, email, birth);
				customers.add(customer);
			}
			
			return customers;
		}
		
	}
	/**
	 * QueryRunner的 query 方法的返回值取决于其参数ResultSetHandler 的 
	 * hander方法的返回值
	 */
	@Test
	public void testQuery() {
		Connection conn = null;
		
		try {
			
			conn = JDBCTools.getConnection();
			String sql = "select id, name, email, birth from customers";
			
			//第三个参数为结果集处理器
			//返回值为<T>类型，也就是说单条信息或多条信息都行
			Object obj = qr.query(conn, sql, new MyResultHandler());
			System.out.println(obj);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCTools.release(null, null, conn);
		}
		
	}
	
	/**
	 * 测试QueryRunner的update方法
	 * 当时的DAO是在类下造的，底下的每个测试方法都可以调用
	 */
	@Test
	void testQueryRunnerUpdate() {
		//1.创建QueryRunner的实现类
		//2.使用其update方法
		String sql = "delete from customers where name in (?,?)";
		
		Connection conn = null;
		try {
			conn = JDBCTools.getConnection();
			qr.update(conn, sql, 9, 10);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCTools.release(null, null, conn);
		}
		
		
	}

}
