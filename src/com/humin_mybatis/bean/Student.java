/**
 * Dec 4, 2017
 */
package com.humin_mybatis.bean;

/** 
 * @ClassName: Student 
 * @Description: 
 * @author humin 
 * @date Dec 4, 2017 3:51:38 PM 
 *  
 */
public class Student {
	private int id;
	private String name;
	/**
	 * 
	 */
	public Student() {
		super();
	}
	/**
	 * @param id
	 * @param name
	 */
	public Student(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + "]";
	}
	
}
