/**
 * Jan 4, 2018
 */
package com.humin_mybatis.testRedis;

import javax.annotation.Resource;


import redis.clients.jedis.ShardedJedis;

/** 
 * @ClassName: AbstractRedisBaseDao 
 * @Description: 
 * @author humin 
 * @date Jan 4, 2018 3:51:07 PM 
 *  
 */
public class AbstractRedisBaseDao<K, V> {
	 public AbstractRedisBaseDao(){}
		@Resource  
	    protected JedisPool jedisPool; ;
	    
	    protected ShardedJedis  getJedis(){
	    	return jedisPool.getPool().getResource();
	    }
	    
	    protected void release(ShardedJedis resource){
	    	 jedisPool.getPool().returnResourceObject(resource);
	    }
}
