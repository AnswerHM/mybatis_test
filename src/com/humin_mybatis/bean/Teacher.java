/**
 * Dec 4, 2017
 */
package com.humin_mybatis.bean;

/** 
 * @ClassName: Teacher 
 * @Description: 
 * @author humin 
 * @date Dec 4, 2017 2:11:20 PM 
 *  
 */
public class Teacher {
	
	private int id;
	private String name;
	
	
	/**
	 * 
	 */
	public Teacher() {
		super();
	}
	
	/**
	 * @param id
	 * @param name
	 */
	public Teacher(int id, String name) {
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
		return "Teacher [id=" + id + ", name=" + name + "]";
	}
	
	
	
	
}
