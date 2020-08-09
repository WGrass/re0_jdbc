package java_jdbc_DBUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java_jdbc_exer.ReflectionUtils;
/**
 * 使用 QueryRunner 提供其具体的实现
 * @param <T>
 * @author Lifeiyang
 * 
 * @Data 2020年7月14日
 */
public class jdbaDaoImpl<T> implements DAO<T> {

	private QueryRunner qr = null;
	private Class<T> type;
	
	public jdbaDaoImpl() {
		qr = new QueryRunner();
		//ReflectionUtils是自己写的一个工具类
		//返回值是class java_jdbc_DBUtils.Customer
		//调用getActualTypeArguments()方法，返回泛型的Class???
		type = ReflectionUtils.getSuperClassGenricType(getClass());
		System.out.println(getClass());//返回值为class java_jdbc_DBUtils.CustomerDao
		System.out.println(type);
	}

	@Override
	public void batch(Connection conn, String sql, Object[]... args) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <E> E getForValue(Connection conn, String sql, Object... args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> getForList(Connection conn, String sql, Object... args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T get(Connection conn, String sql, Object... args) throws SQLException {
		return qr.query(conn, sql, new BeanHandler<>(type), args);
	}

	@Override
	public void update(Connection conn, String sql, Object... args) {
		// TODO Auto-generated method stub
		
	}

}
