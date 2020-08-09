package java_jdbc_function;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

import org.junit.Test;

import java_jdbc_GetDatabaseLinked.JDBCTools;

public class FunctionTest {

	@Test
	public void testCallableStatement() {
		
		Connection conn = null;
		CallableStatement cs = null;
		try {
			conn = JDBCTools.getConnection();
			String sql = "{? = call function_name(?,?...)}";
//			String sql = "{call function_name(?,?...)}";//存储过程
			cs = conn.prepareCall(sql);
			
			//2.执行前注册参数，调用registerOutParameter()方法
			cs.registerOutParameter(1, Types.NUMERIC);
			cs.registerOutParameter(3, Types.NUMERIC);
			//3.
			cs.setInt(2, 80);
			//4.执行
			cs.execute();
			//5.如果所调用的是 带返回参数的存储过程
			//还需要调用getXxx()方法获取其返回值
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCTools.release(null, cs, conn);
		}
	}
	
}
