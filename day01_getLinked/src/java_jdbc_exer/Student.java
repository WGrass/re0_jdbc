package java_jdbc_exer;

public class Student {

	private int id;          //ѧ��
	private String stuName;  //ѧ������
	private String gender;     //�Ա�
	private int seat;        //��λ��
	private int age;         //����
	private int majorId;     //רҵ��
	
	public Student() {
		super();
	}
	public Student(int id, String stuName, String gender, int seat, int age, int majorId) {
		super();
		this.id = id;
		this.stuName = stuName;
		this.gender = gender;
		this.seat = seat;
		this.age = age;
		this.majorId = majorId;
	}
	@Override
	public String toString() {
		return "Student [id=" + id + ", stuName=" + stuName + ", gender=" + gender + ", seat=" + seat + ", age=" + age
				+ ", majorId=" + majorId + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStuName() {
		return stuName;
	}
	public void setStuName(String stuName) {
		this.stuName = stuName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String string) {
		this.gender = string;
	}
	public int getSeat() {
		return seat;
	}
	public void setSeat(int seat) {
		this.seat = seat;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public int getMajorId() {
		return majorId;
	}
	public void setMajorId(int majorId) {
		this.majorId = majorId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + age;
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + id;
		result = prime * result + majorId;
		result = prime * result + seat;
		result = prime * result + ((stuName == null) ? 0 : stuName.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		if (age != other.age)
			return false;
		if (gender == null) {
			if (other.gender != null)
				return false;
		} else if (!gender.equals(other.gender))
			return false;
		if (id != other.id)
			return false;
		if (majorId != other.majorId)
			return false;
		if (seat != other.seat)
			return false;
		if (stuName == null) {
			if (other.stuName != null)
				return false;
		} else if (!stuName.equals(other.stuName))
			return false;
		return true;
	}

	
	
	
	
}
