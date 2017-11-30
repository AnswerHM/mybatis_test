/**
 * Nov 29, 2017
 */
package com.humin_mybatis.test1;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.humin_mybatis.bean.User;

/** 
 * @ClassName: UserMapper 
 * @Description: 
 * @author humin 
 * @date Nov 29, 2017 2:07:35 PM 
 *  
 */
public interface UserMapper {
	
	@Insert("insert into users (age,name) values (#{age},#{name});")
	public int addUser(User user);
	@Delete("delete from users where id=#{id};")
	public int deleteById(int id);
	@Update("update users set age=#{age},name=#{name} where id=#{id};")
	public int update(User user);
	@Select("select * from users where id=#{id}")
	public User getUserById(int id);
	@Select("select * from users;")
	public List<User> getAllUser();
}
