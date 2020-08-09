package java_jdbc_DBUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 访问数据的DAO接口
 * 定义增删改查方法
 * @param T:DAO 处理的实体类类型
 * @author Lifeiyang
 * 
 * @Data 2020年7月14日
 */
public interface DAO<T> {

	/**
	 * 批量处理的方法
	 * @param conn
	 * @param sql
	 * @param args：填充占位符的Object[]数组类型的可变参数
	 */
	void batch(Connection conn, String sql, Object[] ...args);
	
	/**
	 * 返回一个值，例如总人数，平均工资等
	 * @param <E>
	 * @param conn
	 * @param sql
	 * @param args
	 * @return
	 */
	<E> E getForValue(Connection conn, String sql, Object ...args);
	
	/**
	 * 返回T的一个集合
	 * @param conn
	 * @param sql
	 * @param args
	 * @return
	 */
	List<T> getForList(Connection conn, String sql, Object ...args);
	
	/**
	 * 返回一个T对象
	 * @param conn
	 * @param sql
	 * @param args
	 * @return
	 * @throws SQLException 
	 */
	T get(Connection conn, String sql, Object ... args) throws SQLException;
	
	/**
	 * INSERT UPDATE DELETE
	 * @param conn：数据库连接
	 * @param sql：SQL语句
	 * @param args：填充占位符的可变参数
	 */
	 void update(Connection conn, String sql, Object ... args);
	
	
}
