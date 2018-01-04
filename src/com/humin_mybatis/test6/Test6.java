/**
 * Dec 5, 2017
 */
package com.humin_mybatis.test6;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.humin_mybatis.util.MybatisUtil;

/** 
 * @ClassName: Test6 
 * @Description: 
 * @author humin 
 * @date Dec 5, 2017 2:30:36 PM 
 *  
 */
public class Test6 {

	/**
	 * 
	 * @author: humin
	 * @since: Dec 5, 2017 2:30:37 PM
	 * @param: 
	 * @return
	 */
	public static void main(String[] args) {
		SqlSession openSession = MybatisUtil.getFactory().openSession(true);
		String statement = "com.humin_mybatis.test6.PUserMapper.getCount";
		Map<String,Integer> map = new HashMap<>();
		map.put("sex_id", 1);
		map.put("user_count",-1);
		openSession.selectOne(statement, map);
		Integer userCount = map.get("user_count");
		openSession.close();
		System.out.println(userCount);
	}

}
