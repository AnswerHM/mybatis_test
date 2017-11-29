/**
 * Nov 29, 2017
 */
package com.humin_mybatis.test1;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;

import com.humin_mybatis.test.User;
import com.humin_mybatis.util.MybatisUtil;

/** 
 * @ClassName: Test 
 * @Description: 
 * @author humin 
 * @date Nov 29, 2017 11:46:55 AM 
 *  
 */
public class Test2 {
	
	@Test
	public void testAdd(){
		SqlSessionFactory factory = MybatisUtil.getFactory();
		SqlSession openSession = factory.openSession();
		User user = new User();
		user.setAge(1);
		user.setName("zs");
		int insert = openSession.insert("com.humin_mybatis.test1.userMapper2.insertUser",user);
		openSession.commit();
		openSession.close();
		System.out.println(insert);
	}
	
	@Test
	public void testUpdate(){
		SqlSessionFactory factory = MybatisUtil.getFactory();
		SqlSession openSession = factory.openSession();
		int update = openSession.update("com.humin_mybatis.test1.userMapper2.updateUser",new User(5,"123",13));
		openSession.commit();
		openSession.close();
		System.out.println(update);
	}
	
	@Test
	public void deleteUpdate(){
		SqlSessionFactory factory = MybatisUtil.getFactory();
		SqlSession openSession = factory.openSession();
		int delete = openSession.update("com.humin_mybatis.test1.userMapper2.deleteUser",5);
		openSession.commit();
		openSession.close();
		System.out.println(delete);
	}
	
	@Test
	public void getAllUser(){
		SqlSessionFactory factory = MybatisUtil.getFactory();
		SqlSession openSession = factory.openSession();
		List<User> selectList = openSession.selectList("com.humin_mybatis.test1.userMapper2.getAllUser");
		openSession.commit();
		openSession.close();
		if(selectList !=null && selectList.size()>0){
			for(User user : selectList){
				System.out.println(user);
			}
		}
	}
	
	
	@Test
	public void testAdd2(){
		SqlSessionFactory factory = MybatisUtil.getFactory();
		SqlSession openSession = factory.openSession(true);
		UserMapper mapper = openSession.getMapper(UserMapper.class);
		int addUser = mapper.addUser(new User(-1,"Mapper",17));
		openSession.close();
		System.out.println(addUser);
	}
	
	
	@Test
	public void getAllUser2(){
		SqlSessionFactory factory = MybatisUtil.getFactory();
		SqlSession openSession = factory.openSession(true);
		UserMapper mapper = openSession.getMapper(UserMapper.class);
		List<User> allUser = mapper.getAllUser();
		openSession.close();
		if(allUser !=null && allUser.size()>0){
			for(User user : allUser){
				System.out.println(user);
			}
		}
	}
}
