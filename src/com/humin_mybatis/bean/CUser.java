/**
 * Dec 5, 2017
 */
package com.humin_mybatis.bean;

import java.io.Serializable;

/** 
 * @ClassName: CUser 
 * @Description: 
 * @author humin 
 * @date Dec 5, 2017 3:16:00 PM 
 *  
 */
public class CUser implements Serializable{
	private int id;
	private String name;
	private int age;
	
	public CUser() {
		super();
	}
	
	/**
	 * @param id
	 * @param name
	 * @param age
	 */
	public CUser(int id, String name, int age) {
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
		return "CUser [id=" + id + ", name=" + name + ", age=" + age + "]";
	}
	
	

}
