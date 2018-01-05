/**
 * Jan 4, 2018
 */
package com.humin_mybatis.testRedis;

import redis.clients.jedis.Jedis;

/** 
 * @ClassName: TestRedis 
 * @Description: 
 * @author humin 
 * @date Jan 4, 2018 3:19:21 PM 
 *  
 */
public class TestRedis {
	public static void main(String[] args) {
		Jedis jedis = new Jedis("127.0.0.1",6379);
		System.out.println(jedis.ping());
		if(jedis.ping().equals("PONG")){
			System.out.println(jedis.keys("*"));
			System.out.println(jedis.setnx("first", "one"));
			System.out.println(jedis.get("xx"));
			System.out.println(jedis.hset("third", "third", "third"));
		}else{
			System.out.println("Redis没有连接到！");
		}
		
	}
}
