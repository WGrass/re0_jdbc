package java_jdbc_DAO;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.jupiter.api.Test;

import java_jdbc_exer.Student;

class BeanUtilsTest {

	@Test
	void testSetProperty() throws Exception {
		
		Object obj = new Student();
		System.out.println(obj);
		
		BeanUtils.setProperty(obj, "id", "1001");
		System.out.println(obj);
		
		Object val = BeanUtils.getProperty(obj, "id");
		System.out.println(val);
	}

}
