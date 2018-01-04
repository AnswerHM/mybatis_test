/**
 * Nov 30, 2017
 */
package com.humin_mybatis.test5;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.humin_mybatis.bean.ConditionUser;
import com.humin_mybatis.bean.Order;
import com.humin_mybatis.util.MybatisUtil;

/** 
 * @ClassName: Test5
 * @Description: 
 * @author humin 
 * @date Nov 30, 2017 3:33:42 PM 
 *  
 */
public class Test5 {
	public static void main(String[] args) {
		SqlSession openSession = MybatisUtil.getFactory().openSession(true);
		String string = "com.humin_mybatis.test5.userMapper.getUser";
		String name = "o";
		name = null;
		ConditionUser conditionUser = new ConditionUser(name ,13,18);
		List<Object> selectList = openSession.selectList(string,conditionUser);
		System.out.println(selectList);
		openSession.close();
	}
}
