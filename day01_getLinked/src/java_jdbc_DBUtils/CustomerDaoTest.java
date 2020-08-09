package java_jdbc_DBUtils;

import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Connection;

import org.junit.jupiter.api.Test;

import java_jdbc_GetDatabaseLinked.JDBCTools;
import java_jdbc_exer.ReflectionUtils;

class CustomerDaoTest {

	CustomerDao customerDao = new CustomerDao();
	
	@Test
	void testBatch() {
		fail("Not yet implemented");
	}

	@Test
	void testGetForValue() {
		fail("Not yet implemented");
	}

	@Test
	void testGetForList() {
		fail("Not yet implemented");
	}

	@Test
	void testGet() {
		Connection conn = null;
		try {
			conn = JDBCTools.getConnection();
			String sql = "select id, name, email, birth from customers where id = ?";
			Customer customer = customerDao.get(conn, sql, 6);
			System.out.println(customer);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCTools.release(null, null, conn);
		}
		
	}

	@Test
	void testUpdate() {
		fail("Not yet implemented");
	}
	
	
	@Test
	public void test() {
		Class<Customer> type = ReflectionUtils.getSuperClassGenricType(getClass());
		System.out.println(type.getName());
	}

}
