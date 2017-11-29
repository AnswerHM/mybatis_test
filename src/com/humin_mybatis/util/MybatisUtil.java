/**
 * Nov 29, 2017
 */
package com.humin_mybatis.util;

import java.io.InputStream;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.humin_mybatis.test.Test;

/** 
 * @ClassName: MybatisUtil 
 * @Description: 
 * @author humin 
 * @date Nov 29, 2017 11:50:42 AM 
 *  
 */
public class MybatisUtil {
	public static SqlSessionFactory getFactory(){
		InputStream inputStream = MybatisUtil.class.getClassLoader().getResourceAsStream("mybatis-config.xml");
		SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
		return factory;
	}
}
