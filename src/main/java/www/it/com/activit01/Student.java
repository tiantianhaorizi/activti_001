package www.it.com.activit01;

import java.io.Serializable;

public class Student implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer age;
	
	private String  stuName;

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getStuName() {
		return stuName;
	}

	public void setStuName(String stuName) {
		this.stuName = stuName;
	}

	
	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Student(Integer age, String stuName) {
		super();
		this.age = age;
		this.stuName = stuName;
	}
	

}
