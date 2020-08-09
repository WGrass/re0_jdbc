package java_jdbc_exer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.junit.Test;

import java.sql.Connection;

import java_jdbc_GetDatabaseLinked.JDBCTools;

public class JDBCTest {

	@Test
	public void testResultSetMetaData() {
		Connection conn = null;
		java.sql.PreparedStatement ps = null;
		ResultSet resultSet = null;

		try {
			String sql = "select stu_id id,stu_name stuName,gender,seat,age,majorId from stuinfo where stu_id = ?";
			conn = JDBCTools.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, 3);

			resultSet = ps.executeQuery();
			
			Map<String, Object> values = new HashMap<String, Object>();
			//�õ�ResultSetMetaData ����
			ResultSetMetaData rsmd = resultSet.getMetaData();
			// ͨ������sql������жϵ���ѡ������Щ��
			// 3.��ȡ������еı���
			while(resultSet.next()) {
				for(int i = 0;i < rsmd.getColumnCount();i++) {
					String columnLable = rsmd.getColumnLabel(i + 1);
					Object columnValue = resultSet.getObject(columnLable);
					// 4.�ٻ�ȡ�����ÿһ�е�ֵ�����3�õ�һ��Map��ֵ��
					values.put(columnLable,columnValue);
				}
			}
			System.out.println(values);
			// 5.�����÷���Ϊ2�Ķ�Ӧ�����Ը�ֵ
			Class clazz = Student.class;
			Object obj = clazz.newInstance();
			for(Map.Entry<String, Object> entry : values.entrySet()) {
				String fieldName = entry.getKey();
				Object fieldValue = entry.getValue();
				
				ReflectionUtils.setFieldValue(obj, fieldName, fieldValue);
			}
			
			System.out.println(obj);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			JDBCTools.release(resultSet, ps, conn);
		}

	}

	@Test
	public void testPreparedStatement() {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = JDBCTools.getConnection();
			String sql = "insert into customers(name,email,birth)" + "values(?,?,?)";

			ps = conn.prepareStatement(sql);
			ps.setString(1, "asd");
			ps.setString(2, "123456.com");
			ps.setDate(3, (java.sql.Date) new Date(new Date().getTime()));

			ps.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			JDBCTools.release(null, ps, conn);
		}
	}

	/**
	 * �㶨�ˣ�ע������ 1.sql��select��䣬�����ֶ���ʱ�����Ҳ��Ҫ�� + �Ÿ���
	 * 
	 */
	@Test
	public void testAddNewStudent() {
		// 1.��ȡ�ӿ���̨�����Student����
		Student stu = getStudentFromConsole();

		// 2.����addStudent(Student stu)����ִ�в������
//		StudentEx.addStudent(stu);
		StudentEx.addStudent2(stu);
	}

	@Test
	public void testGetStudent() {

		// 1.�õ���ѯ����
		int searchType = getSearchTypeFromConsole();
		// 2.
		Student stu = StudentEx.searchStudent(searchType);
		// 3.��ӡѧ����Ϣ
		StudentEx.printStudent(stu);
	}

	/**
	 * �ӿ���̨����һ��������ȷ��Ҫ��ѯ������
	 * 
	 * @return 1.��id��ѯ 2.����λ�Ų�ѯ ������Ч������ʾ��������
	 */
	@SuppressWarnings("resource")
	private int getSearchTypeFromConsole() {

		System.out.println("�������ѯ���ͣ���1.ѧ�Ų�ѯ or 2.��λ�Ų�ѯ��");
		Scanner scanner = new Scanner(System.in);
		int type = scanner.nextInt();

		if (type != 1 && type != 2) {
			System.out.println("���������룺");
			throw new RuntimeException();
		}

		return type;
	}

	/**
	 * �ӿ���̨����ѧ������Ϣ
	 * 
	 * @return
	 */
	private static Student getStudentFromConsole() {

		Scanner scanner = new Scanner(System.in);
		Student student = new Student();

		System.out.println("ID:");
		student.setId(scanner.nextInt());
		System.out.println("stuNmae:");
		student.setStuName(scanner.next());
		System.out.println("gender:");
		student.setGender(scanner.next());
		System.out.println("seat:");
		student.setSeat(scanner.nextInt());
		System.out.println("age:");
		student.setAge(scanner.nextInt());
		System.out.println("majorId:");
		student.setMajorId(scanner.nextInt());

		return student;
	}

	@Test
	public void test2() {
		String sql = "insert into stuinfo values(8,'Jerry','B',007,12,1000)";
		JDBCTools.update(sql);
	}

}
