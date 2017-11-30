/**
 * Nov 28, 2017
 */
package com.humin_mybatis.test;

import java.io.InputStream;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.humin_mybatis.bean.User;

/** 
 * @ClassName: Test 
 * @Description: 
 * @author humin 
 * @date Nov 28, 2017 5:42:11 PM 
 *  
 */
public class Test {
	public static void main(String[] args) throws ClassNotFoundException {
		InputStream inputStream = Test.class.getClassLoader().getResourceAsStream("mybatis-config.xml");
		SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession openSession = factory.openSession();
		User user = openSession.selectOne("com.humin_mybatis.test.userMapper.getUser",2);
		if(user != null){
			System.out.println(user);
		}
	}
}
