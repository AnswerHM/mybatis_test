/**
 * Jan 4, 2018
 */
package com.humin_mybatis.testRedis;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/** 
 * @ClassName: ConfigProperties 
 * @Description: 
 * @author humin 
 * @date Jan 4, 2018 3:44:52 PM 
 *  
 */
public class ConfigProperties {
	private static Properties properties = null;
	private ConfigProperties(){
		
	}
	private static Resource resource = new ClassPathResource("META-INF/app.properties");
	public static synchronized Properties newInstance() throws IOException{
		if(!resource.exists()){
			resource = new ClassPathResource("app.properties");
		}
		if(!resource.exists()){
			resource = new ClassPathResource("redis.properties");
		}
		if(!resource.exists())
			return null;
		properties = PropertiesLoaderUtils.loadProperties(resource);
		return properties;
	}
}
