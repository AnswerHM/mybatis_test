/**
 * Nov 28, 2017
 */
package com.humin_mybatis.bean;

/** 
 * @ClassName: User 
 * @Description: 
 * @author humin 
 * @date Nov 28, 2017 4:27:25 PM 
 *  
 */
public class User {
	private int id;
	private String name;
	private int age;
	
	public User() {
		super();
	}
	
	/**
	 * @param id
	 * @param name
	 * @param age
	 */
	public User(int id, String name, int age) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
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
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", age=" + age + "]";
	}
	
	

}
