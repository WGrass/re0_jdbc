package java_jdbc_DAO;

import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import java_jdbc_exer.Student;

class DAOTest {

	DAO dao = new DAO();
	
	@Test
	void testUpdata() {
		String sql = "insert into customers(name, email, birth) values(?,?,?)";
		dao.updata(sql, "x", "123.com", new Date(new java.util.Date().getTime()));
	}

	@Test
	void testGet() {
		String sql = "select stu_id id,stu_name stuName,gender,seat,age,majorId from stuinfo where stu_id = ?";
		Student student = dao.get(Student.class, sql, 1);
		System.out.println(student);
	}

	@Test
	void testGetForList() {
		String sql = "select stu_id id,stu_name stuName,gender,seat,age,majorId from stuinfo";
		
		List<Student> stus = dao.getForList(Student.class, sql);
		System.out.println(stus);
	}

	@Test
	void testGetForValue() {
		String sql = "select stu_name from stuinfo where stu_id = ?";
		
		Object value = dao.getForValue(sql, 3);
		System.out.println(value);
	}

}
