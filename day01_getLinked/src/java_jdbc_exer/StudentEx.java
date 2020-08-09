package java_jdbc_exer;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import java.sql.Connection;
import java_jdbc_GetDatabaseLinked.JDBCTools;

public class StudentEx {

	public static void addStudent2(Student stu) {
		String sql = "insert into stuinfo(id,stuName,gender,seat,age,majorId)" + 
				"values(?,?,?,?,?,?)";
		
		JDBCTools.update(sql, stu.getId(),stu.getStuName(),stu.getGender(),stu.getSeat(),stu.getAge(),stu.getMajorId());
		
	}
	
	public static void addStudent(Student stu) {
		//1.ԭʼ����
		String sql = "insert into stuinfo values(" + stu.getId() + ",'" + stu.getStuName() + "','" + stu.getGender()
				+ "'," + stu.getSeat() + "," + stu.getAge() + "," + stu.getMajorId() + ")";

		System.out.println(sql);
        //����JDBCTools��update����
		JDBCTools.update(sql);
	
	}

	
	public static Student searchStudent(int searchType) {
		String sql = "select * from stuinfo where ";

		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		// 1.���������searchType����ʾ�û���Ϣ
		// 2.ȷ��sql���
		if (searchType == 1) {
			System.out.println("������id��");
			int id = scanner.nextInt();
			sql = sql + "id = " + id;
		} else {
			System.out.println("��������λ�ţ�");
			int seat = scanner.nextInt();
			sql = sql + "seat = " + seat;
		}

		// 3.ִ�в�ѯ
		Student student = getStudent(sql);

		// 4.�����ڲ�ѯ�������װλStudent���󲢷���

		return student;
	}

	/**
	 * ���ݴ����SQL��䣬����Student����
	 * 
	 * @return
	 */
	private static Student getStudent(String sql) {

		Student student = null;

		Connection conn = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try {
			conn = JDBCTools.getConnection();
			statement = conn.createStatement();
			resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {
				student = new Student(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
						resultSet.getInt(4), resultSet.getInt(5), resultSet.getInt(6));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			JDBCTools.release(resultSet, statement, conn);
		}
		return student;
	}
	private static Student getStudent(String sql,Object ... args) {
		
		Student student = null;
		
		Connection conn = null;
		java.sql.PreparedStatement ps = null;
		ResultSet resultSet = null;
		
		try {
			conn = JDBCTools.getConnection();
			ps = conn.prepareStatement(sql);
			for(int i = 0;i < args.length;i++) {
				ps.setObject(i + 1, args[i]);
			}
			
			resultSet = ps.executeQuery();
			if (resultSet.next()) {
				student = new Student(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
						resultSet.getInt(4), resultSet.getInt(5), resultSet.getInt(6));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			JDBCTools.release(resultSet, ps, conn);
		}
		return student;
	}

	/**
	 * ��ӡѧ����Ϣ���������ڣ���ӡ���޴���
	 * 
	 * @param stu
	 */
	public static void printStudent(Student stu) {
		if (stu != null) {
			System.out.println(stu);
		} else {
			System.out.println("���޴��ˣ���");
		}
	}

}
