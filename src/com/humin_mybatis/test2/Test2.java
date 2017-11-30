/**
 * Nov 30, 2017
 */
package com.humin_mybatis.test2;

import org.apache.ibatis.session.SqlSession;

import com.humin_mybatis.bean.Order;
import com.humin_mybatis.util.MybatisUtil;

/** 
 * @ClassName: Test2 
 * @Description: 
 * @author humin 
 * @date Nov 30, 2017 3:33:42 PM 
 *  
 */
public class Test2 {
	public static void main(String[] args) {
		SqlSession openSession = MybatisUtil.getFactory().openSession(true);
		Object selectOne = openSession.selectOne("com.humin_mybatis.test2.orderMapper.getOrder2", 1);
		System.out.println(selectOne);
	}
}
