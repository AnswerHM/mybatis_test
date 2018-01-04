/**
 * Dec 5, 2017
 */
package com.humin_mybatis.test7;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;

import com.humin_mybatis.bean.CUser;
import com.humin_mybatis.util.MybatisUtil;

/** 
 * @ClassName: Test7
 * @Description: 
 * @author humin 
 * @date Dec 5, 2017 2:30:36 PM 
 *  
 */
public class Test7 {
	/**
	 * 测试缓存
	 * 	一、一级缓存
	 * 	1.执行了clearCache();方法
	 *  2.执行了CRUD
	 *  3.不是同一个Session对象
	 *  二、二级缓存
	 *  存在于一个statement对象上的缓存
	 */
	@Test
	public void testCache1(){
		SqlSession openSession = MybatisUtil.getFactory().openSession(true);
		String statement = "com.humin_mybatis.test7.CUserMapper.getCuser";
		CUser cUser = openSession.selectOne(statement, 1);
		System.out.println(cUser);
		
		/*
		 * 一级缓存默认就会被使用
		 */
		cUser = openSession.selectOne(statement, 1);
		System.out.println(cUser);
		
		System.out.println("------------------------");
		/*
		 * 第一种情况：执行openSession.clearCache();清除缓存
			openSession.clearCache();
		 */
		/*
		 * 第二种情况：
		openSession.update("com.humin_mybatis.test7.CUserMapper.updateCuser",new CUser(1,"Tom",13));
		 */
		/*
		 * 第三种情况：
		 * 重新获取另一个Session
		 */
		cUser = openSession.selectOne(statement, 1);
		System.out.println(cUser);
	}
	
	/**
	 * 测试二级缓存
	 * 
	 */
	@Test
	public void testCache2(){
		SqlSessionFactory factory = MybatisUtil.getFactory();
		SqlSession openSession = factory.openSession();
		String statement = "com.humin_mybatis.test7.CUserMapper.getCuser";
		CUser cUser = openSession.selectOne(statement, 1);
		openSession.commit();
		System.out.println(cUser);
		System.out.println("------------------------");
		SqlSession openSession2 = factory.openSession();
		cUser = openSession2.selectOne(statement, 1);
		openSession2.commit();
		System.out.println(cUser);
		
		
	}

}
