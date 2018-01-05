/**
 * Jan 4, 2018
 */
package com.humin_mybatis.testRedis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.SerializationUtils;
import org.apache.commons.lang.StringUtils;

import com.pfb.redis.util.Primitives;

import redis.clients.jedis.ShardedJedis;

/** 
 * @ClassName: RedisUtilsService 
 * @Description: 
 * @author humin 
 * @date Jan 4, 2018 3:51:55 PM 
 *  
 */
public class RedisUtilsService extends AbstractRedisBaseDao<String, Serializable> {

	private final String SUCCESS = "OK";

	/**
	 * key-value方式设值
	 * 
	 * @param key
	 * @param val
	 * @return
	 * @since:2015年7月8日 下午4:43:09
	 * @author:yuzengjia
	 */
	public boolean setByKey(String key, Serializable val) {
		if (StringUtils.isBlank(key))
			return false;
		ShardedJedis jedis = null;
		try {
			jedis = getJedis();
			byte[] keyVal = SerializationUtils.serialize(key);
			byte[] value = SerializationUtils.serialize(val);
			String res = jedis.set(keyVal, value);
			return SUCCESS.equals(res);
		} finally {
			release(jedis);
		}

	}

	/**
	 * 设置值并加超时时间
	 * 
	 * @param key
	 * @param val
	 * @param seconds
	 * @return
	 * @since:2015年7月8日 下午6:13:51
	 * @author:yuzengjia
	 */
	public boolean setByKey(String key, Serializable val, int seconds) {
		if (StringUtils.isBlank(key))
			return false;
		ShardedJedis jedis = null;
		try {
			jedis = getJedis();
			byte[] keyVal = SerializationUtils.serialize(key);
			byte[] value = SerializationUtils.serialize(val);
			String res = jedis.setex(keyVal, seconds, value);
			return SUCCESS.equals(res);
		} finally {
			release(jedis);
		}
	}

	/**
	 * 设置值，没有则设置，有则不操作
	 * 
	 * @param key
	 * @param val
	 * @return
	 */
	public boolean setNx(String key, Serializable val) {
		if (StringUtils.isBlank(key))
			return false;
		ShardedJedis jedis = null;
		try {
			jedis = getJedis();
			byte[] keyVal = SerializationUtils.serialize(key);
			byte[] value = SerializationUtils.serialize(val);
			Long res = jedis.setnx(keyVal, value);
			return res == 1;
		} finally {
			release(jedis);
		}
	}

	/**
	 * 获取设置值
	 * 
	 * @param key
	 * @param val
	 * @return
	 */
	public String getSet(String key, Serializable val) {
		if (StringUtils.isBlank(key))
			return null;
		ShardedJedis jedis = null;
		try {
			jedis = getJedis();
			byte[] keyVal = SerializationUtils.serialize(key);
			byte[] value = SerializationUtils.serialize(val);
			String res =String.valueOf(SerializationUtils.deserialize(jedis.getSet(keyVal, value)));
			return res;
		} finally {
			release(jedis);
		}
	}

	public boolean setXxByKey(String key, Serializable val, long timeout, TimeUnit timeunit) {
		if (StringUtils.isBlank(key))
			return false;
		ShardedJedis jedis = null;
		try {
			jedis = getJedis();
			byte[] keyVal = SerializationUtils.serialize(key);
			byte[] value = SerializationUtils.serialize(val);
			byte[] px = null;
			if (TimeUnit.SECONDS.equals(timeunit)) {
				px = "EX".getBytes();
			} else {
				px = "PX".getBytes();
			}
			String res = jedis.set(keyVal, value, "XX".getBytes(), px, timeout);
			return SUCCESS.equals(res);
		} finally {
			release(jedis);
		}
	}

	public boolean setByKey(String key, Serializable val, long timeout, TimeUnit timeunit) {
		if (StringUtils.isBlank(key))
			return false;
		ShardedJedis jedis = null;
		try {
			jedis = getJedis();
			byte[] keyVal = SerializationUtils.serialize(key);
			byte[] value = SerializationUtils.serialize(val);
			byte[] px = null;
			if (TimeUnit.SECONDS.equals(timeunit)) {
				px = "EX".getBytes();
			} else {
				px = "PX".getBytes();
			}
			if (jedis.exists(keyVal)) {
				jedis.del(keyVal);
			}
			String res = jedis.set(keyVal, value, "NX".getBytes(), px, timeout);
			return SUCCESS.equals(res);
		} finally {
			release(jedis);
		}
	}

	/**
	 * 添加数组元素
	 * 
	 * @param key
	 * @param val
	 * @return boolean
	 * @author:yu.zengjia@pufubao.net
	 * @date:2016年10月23日 下午6:49:18
	 */
	public boolean addListByKey(String key, String val) {
		if (StringUtils.isBlank(key))
			return false;
		ShardedJedis jedis = null;
		try {
			jedis = getJedis();
			byte[] keyVal = SerializationUtils.serialize(key);
			byte[] value = SerializationUtils.serialize(val);
			jedis.rpush(keyVal, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			release(jedis);
		}
	}

	/**
	 * 弹出数组元素
	 * 
	 * @param key
	 * @return boolean
	 * @author:yu.zengjia@pufubao.net
	 * @date:2016年10月23日 下午6:49:18
	 */
	public Object popListByKey(String key) {
		ShardedJedis jedis = null;
		try {
			jedis = getJedis();
			byte[] keyVal = SerializationUtils.serialize(key);
			byte[] val = jedis.lpop(keyVal);
			return SerializationUtils.deserialize(val);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			release(jedis);
		}
	}

	/**
	 * 移除数组元素
	 * 
	 * @param key
	 * @return boolean
	 * @author:yu.zengjia@pufubao.net
	 * @date:2016年10月23日 下午6:49:18
	 */
	public Long remListByKey(String key, String val) {
		ShardedJedis jedis = null;
		try {
			jedis = getJedis();
			byte[] keyVal = SerializationUtils.serialize(key);
			byte[] value = SerializationUtils.serialize(val);
			return jedis.lrem(keyVal, 0, value);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			release(jedis);
		}
	}

	/**
	 * 获取数组
	 * 
	 * @param key
	 * @return List<Object>
	 * @author:yu.zengjia@pufubao.net
	 * @date:2016年10月23日 下午7:05:27
	 */
	public List<Object> getListByKey(String key) {
		if (StringUtils.isBlank(key))
			return null;
		ShardedJedis jedis = null;
		try {
			jedis = getJedis();
			byte[] keyVal = SerializationUtils.serialize(key);
			List<byte[]> list = jedis.lrange(keyVal, 0, -1);
			if (list == null || list.isEmpty()) {
				return null;
			}
			List<Object> l = new ArrayList<Object>(list.size());
			for (byte[] b : list) {
				l.add(SerializationUtils.deserialize(b));
			}
			return l;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			release(jedis);
		}
	}

	public boolean setNxByKey(String key, Serializable val, long timeout, TimeUnit timeunit) {
		if (StringUtils.isBlank(key))
			return false;
		ShardedJedis jedis = null;
		try {
			jedis = getJedis();
			byte[] keyVal = SerializationUtils.serialize(key);
			byte[] value = SerializationUtils.serialize(val);
			byte[] px = null;
			if (TimeUnit.SECONDS.equals(timeunit)) {
				px = "EX".getBytes();
			} else {
				px = "PX".getBytes();
			}
			String res = jedis.set(keyVal, value, "NX".getBytes(), px, timeout);
			return SUCCESS.equals(res);
		} finally {
			release(jedis);
		}
	}

	/**
	 * 设置超时时间
	 * 
	 * @param key
	 * @param seconds
	 * @return
	 * @since:2015年7月8日 下午6:17:05
	 * @author:yuzengjia
	 */
	public boolean expired(String key, int seconds) {
		if (StringUtils.isBlank(key))
			return false;
		ShardedJedis jedis = null;
		try {
			jedis = getJedis();
			byte[] keyVal = SerializationUtils.serialize(key);
			Long res = jedis.expire(keyVal, seconds);
			return 1 == res.intValue();
		} finally {
			release(jedis);
		}

	}

	/**
	 * 根据key取出对象
	 * 
	 * @param key
	 * @return
	 * @since:2015年7月8日 下午4:43:45
	 * @author:yuzengjia
	 */
	public Object getByKey(String key) {
		ShardedJedis jedis = null;
		try {
			jedis = getJedis();
			byte[] keyVal = SerializationUtils.serialize(key);
			byte[] res = jedis.get(keyVal);
			if (res == null || res.length == 0)
				return null;
			return SerializationUtils.deserialize(res);
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			release(jedis);
		}
		return null;
	}

	/**
	 * 设置Map值
	 * 
	 * @param key
	 * @param filed
	 * @param val
	 * @return
	 * @since:2015年7月8日 下午6:24:58
	 * @author:yuzengjia
	 */
	public boolean hset(String key, String filed, Serializable val) {
		if (StringUtils.isBlank(key) || StringUtils.isBlank(filed))
			return false;
		ShardedJedis jedis = null;
		try {
			jedis = getJedis();
			byte[] keyVal = SerializationUtils.serialize(key);
			byte[] filedVal = SerializationUtils.serialize(filed);
			byte[] value = SerializationUtils.serialize(val);
			Long res = jedis.hset(keyVal, filedVal, value);
			return 1 == res.intValue();
		} finally {
			release(jedis);
		}

	}

	/**
	 * 删除Ｈash key val
	 * 
	 * @param key
	 * @param filed
	 * @return boolean
	 * @author:yu.zengjia@pufubao.net
	 * @date:2016年6月21日 下午5:17:00
	 */
	public boolean hdel(String key, String filed) {
		if (StringUtils.isBlank(key) || StringUtils.isBlank(filed))
			return false;
		ShardedJedis jedis = null;
		try {
			jedis = getJedis();
			byte[] keyVal = SerializationUtils.serialize(key);
			byte[] filedVal = SerializationUtils.serialize(filed);
			Long res = jedis.hdel(keyVal, filedVal);
			return 1 == res.intValue();
		} finally {
			release(jedis);
		}

	}

	/**
	 * 查Ｈash keys
	 * 
	 * @param key
	 * @return
	 * @since:2015年7月8日 下午6:24:58
	 * @author:yuzengjia
	 */
	public List<String> hKeys(String key) {
		if (StringUtils.isBlank(key))
			return null;
		ShardedJedis jedis = null;
		try {
			jedis = getJedis();
			byte[] keyVal = SerializationUtils.serialize(key);
			Set<byte[]> keys = jedis.hkeys(keyVal);
			List<String> vals = new ArrayList<String>(keys.size());
			for (byte[] k : keys) {
				byte[] res = jedis.get(k);
				if (res == null)
					continue;
				vals.add(String.valueOf(SerializationUtils.deserialize(res)));
				return vals;
			}
		} finally {
			release(jedis);
		}
		return null;
	}

	/**
	 * 查询Hash值
	 * 
	 * @param key
	 * @param filed
	 * @return
	 * @since:2015年7月8日 下午6:25:49
	 * @author:yuzengjia
	 */
	public Object getByHashKey(String key, String filed) {
		ShardedJedis jedis = null;
		try {
			jedis = getJedis();
			byte[] keyVal = SerializationUtils.serialize(key);
			byte[] filedVal = SerializationUtils.serialize(filed);
			byte[] res = jedis.hget(keyVal, filedVal);
			if (res == null || res.length == 0)
				return null;
			return SerializationUtils.deserialize(res);
		} finally {
			release(jedis);
		}

	}

	/**
	 * 查询Map集合
	 * 
	 * @param key
	 * @return
	 * @since:2015年7月8日 下午7:46:13
	 * @author:yuzengjia
	 */
	public Map<String, Object> getMap(String key) {
		if (StringUtils.isBlank(key))
			return null;
		ShardedJedis jedis = null;
		try {
			jedis = getJedis();
			byte[] keyVal = SerializationUtils.serialize(key);
			Map<byte[], byte[]> byteMap = jedis.hgetAll(keyVal);
			if (byteMap != null) {
				Set<Entry<byte[], byte[]>> entries = byteMap.entrySet();
				Map<String, Object> resMap = new HashMap<String, Object>();
				for (Entry<byte[], byte[]> e : entries) {
					String k = String.valueOf(SerializationUtils.deserialize(e.getKey()));
					Object v = SerializationUtils.deserialize(e.getValue());
					resMap.put(k, v);
				}
				return resMap;
			}
		} finally {
			release(jedis);
		}
		return null;
	}

	/**
	 * 在集合中添加元素 TODO
	 * 
	 * @author yu.zengjia@pufubao.net
	 * @since 2017 2017年1月4日 下午4:10:32
	 */
	public boolean sAdd(String key, List<Serializable> list) {
		if (StringUtils.isBlank(key))
			return false;
		if (list == null || list.isEmpty())
			return false;
		ShardedJedis jedis = null;
		try {
			jedis = getJedis();
			byte[] keyVal = SerializationUtils.serialize(key);
			byte[][] membersVal = new byte[list.size()][];
			for (int i = 0; i < list.size(); i++) {
				membersVal[i] = SerializationUtils.serialize(list.get(i));
			}
			Long res = jedis.sadd(keyVal, membersVal);
			return res.intValue() > 0;
		} finally {
			release(jedis);
		}
	}

	/**
	 * 在集合中移除元素 TODO
	 * 
	 * @author yu.zengjia@pufubao.net
	 * @since 2017 2017年1月4日 下午4:10:32
	 */
	public boolean sRem(String key, List<Serializable> list) {
		if (StringUtils.isBlank(key))
			return false;
		if (list == null || list.isEmpty())
			return false;
		ShardedJedis jedis = null;
		try {
			jedis = getJedis();
			byte[] keyVal = SerializationUtils.serialize(key);
			byte[][] membersVal = new byte[list.size()][];
			for (int i = 0; i < list.size(); i++) {
				membersVal[i] = SerializationUtils.serialize(list.get(i));
			}
			Long res = jedis.srem(keyVal, membersVal);
			return res.intValue() > 0;
		} finally {
			release(jedis);
		}
	}

	/**
	 * 判断集合中有无该元素 TODO
	 * 
	 * @author yu.zengjia@pufubao.net
	 * @since 2017 2017年1月4日 下午4:10:32
	 */
	public boolean sIsmember(String key, Serializable memeber) {
		if (StringUtils.isBlank(key))
			return false;
		ShardedJedis jedis = null;
		try {
			jedis = getJedis();
			byte[] keyVal = SerializationUtils.serialize(key);
			byte[] memberVal = SerializationUtils.serialize(memeber);
			return jedis.sismember(keyVal, memberVal);
		} finally {
			release(jedis);
		}
	}

	/**
	 * 按score分数排序 TODO
	 * 
	 * @author yu.zengjia@pufubao.net
	 * @since 2017 2017年1月4日 下午4:10:32
	 */
	public boolean zAdd(String key, double score, Serializable memeber) {
		if (StringUtils.isBlank(key))
			return false;
		ShardedJedis jedis = null;
		try {
			jedis = getJedis();
			byte[] keyVal = SerializationUtils.serialize(key);
			byte[] memberVal = SerializationUtils.serialize(memeber);
			Long res = jedis.zadd(keyVal, score, memberVal);
			return 1 == res.intValue();
		} finally {
			release(jedis);
		}
	}

	/**
	 * 从有序集合中删除指定元素
	 * 
	 * @param key
	 * @param list
	 * @return
	 */
	public boolean zRem(String key, List<Serializable> list) {
		if (StringUtils.isBlank(key))
			return false;
		if (list == null || list.isEmpty())
			return false;
		ShardedJedis jedis = null;
		try {
			jedis = getJedis();
			byte[] keyVal = SerializationUtils.serialize(key);
			byte[][] membersVal = new byte[list.size()][];
			for (int i = 0; i < list.size(); i++) {
				membersVal[i] = SerializationUtils.serialize(list.get(i));
			}
			Long res = jedis.zrem(keyVal, membersVal);
			return res.intValue() > 0;
		} finally {
			release(jedis);
		}
	}

	/**
	 * 按照Score范围从大到小返回值集合
	 *  FIXME 注意：此处返回值为无序的集合
	 * @author yu.zengjia@pufubao.net
	 * @since 2017 2017年1月4日 下午4:29:43
	 */
	public Set<Object> zRangeDesc(String key, long start, long end) {
		if (StringUtils.isBlank(key))
			return null;
		ShardedJedis jedis = null;
		try {
			jedis = getJedis();
			byte[] keyVal = SerializationUtils.serialize(key);
			Set<byte[]> byteMap = jedis.zrevrange(keyVal, start, end);
			if (byteMap != null) {
				Set<Object> resMap = new HashSet<Object>();
				for (byte[] e : byteMap) {
					resMap.add(SerializationUtils.deserialize(e));
				}
				return resMap;
			}
		} finally {
			release(jedis);
		}
		return null;
	}

	/**
	 * 返回zRangeSet的有序集合List
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public List<Object> zRangeSetDesc(String key, long start, long end) {
		if (StringUtils.isBlank(key))
			return null;
		ShardedJedis jedis = null;
		try {
			jedis = getJedis();
			byte[] keyVal = SerializationUtils.serialize(key);
			Set<byte[]> byteMap = jedis.zrevrange(keyVal, start, end);
			if (byteMap != null) {
				List<Object> resMap = new ArrayList<Object>();
				for (byte[] e : byteMap) {
					resMap.add(SerializationUtils.deserialize(e));
				}
				return resMap;
			}
		} finally {
			release(jedis);
		}
		return null;
	}

	/**
	 * 查询并转换为实体
	 * 
	 * @param key
	 * @param clazz
	 * @return
	 * @since:2015年7月21日 下午3:13:53
	 * @author:yuzengjia
	 */
	public <T> T get(String key, Class<T> clazz) {
		ShardedJedis jedis = null;
		try {
			jedis = getJedis();
			byte[] keyVal = SerializationUtils.serialize(key);
			byte[] res = jedis.get(keyVal);
			if (res == null || res.length == 0)
				return null;
			return Primitives.wrap(clazz).cast(SerializationUtils.deserialize(res));
		} finally {
			release(jedis);
		}

	}

	/**
	 * 判断Key是否存在
	 * 
	 * @param key
	 * @return
	 * @since:2015年7月21日 下午3:13:44
	 * @author:yuzengjia
	 */
	public boolean isExists(String key) {
		if (StringUtils.isBlank(key))
			return false;
		ShardedJedis jedis = null;
		try {
			jedis = getJedis();
			byte[] keyVal = SerializationUtils.serialize(key);
			return jedis.exists(keyVal);
		} finally {
			release(jedis);
		}
	}

	public Long delete(String key) {
		ShardedJedis jedis = null;
		try {
			jedis = getJedis();
			byte[] keyVal = SerializationUtils.serialize(key);
			return jedis.del(keyVal);
		} finally {
			release(jedis);
		}
	}

	/**
	 * 按照指定数目增加
	 * 
	 * @param key
	 * @param num
	 * @return
	 */
	public Long incrby(String key, Integer num) {
		ShardedJedis jedis = null;
		try {
			jedis = getJedis();
			byte[] keyVal = SerializationUtils.serialize(key);
			return jedis.incrBy(keyVal, num);
		} finally {
			release(jedis);
		}
	}

	/**
	 * 按照指定数目减少
	 * 
	 * @param key
	 * @param num
	 * @return
	 */
	public Long decrby(String key, Integer num) {
		ShardedJedis jedis = null;
		try {
			jedis = getJedis();
			byte[] keyVal = SerializationUtils.serialize(key);
			return jedis.decrBy(keyVal, num);
		} finally {
			release(jedis);
		}
	}

	/**
	 * 数目加一
	 * 
	 * @param key
	 * @return
	 */
	public Long inrc(String key) {
		ShardedJedis jedis = null;
		try {
			jedis = getJedis();
			byte[] keyVal = SerializationUtils.serialize(key);
			return jedis.incr(keyVal);
		} finally {
			release(jedis);
		}
	}

	/**
	 * 数目减一
	 * 
	 * @param key
	 * @return
	 */
	public Long decr(String key) {
		ShardedJedis jedis = null;
		try {
			jedis = getJedis();
			byte[] keyVal = SerializationUtils.serialize(key);
			return jedis.decr(keyVal);
		} finally {
			release(jedis);
		}
	}
}
