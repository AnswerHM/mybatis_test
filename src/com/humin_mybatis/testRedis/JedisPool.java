/**
 * Jan 4, 2018
 */
package com.humin_mybatis.testRedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;


import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

/** 
 * @ClassName: JedisPool 
 * @Description: 
 * @author humin 
 * @date Jan 4, 2018 3:48:44 PM 
 *  
 */
public class JedisPool implements InitializingBean {

	@Resource private PoolConfig poolConfig;
	
	private ShardedJedisPool shardedJedisPool;
	private List<JedisShardInfo> shareList = new ArrayList<JedisShardInfo>();

	@Override
	public void afterPropertiesSet() throws Exception {
		Properties properties = ConfigProperties.newInstance();
		if(properties != null &&StringUtils.isNotBlank(properties.getProperty("redis.host"))){
			String hosts = properties.getProperty("redis.host").trim();
			int port = Integer.parseInt(properties.getProperty("redis.port").trim());
			String[] hostArr = hosts.split(",");
			for(String host : hostArr){
				JedisShardInfo info = new JedisShardInfo(host.trim(), port);
				String password = properties.getProperty("redis.password");
				info.setPassword(StringUtils.isNotBlank(password)?password:"2D5D637DB3DDCF26507FF0EF424B4BF7");
				shareList.add(info);
			}
		}else{
			JedisShardInfo info = new JedisShardInfo("127.0.0.1", 6379);
			shareList.add(info);
		}
		shardedJedisPool = new ShardedJedisPool(poolConfig, shareList);
	}
	public ShardedJedisPool getPool(){
		return shardedJedisPool;
	}
	
}
