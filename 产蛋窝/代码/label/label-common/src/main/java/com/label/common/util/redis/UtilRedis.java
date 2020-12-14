package com.label.common.util.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.ShardedJedis;

/**
 * redis操作类
 * @author jolley
 */
public class UtilRedis {

	private static Logger _log = LoggerFactory.getLogger(UtilRedis.class);

	/**
	 * redis过期时间,以秒为单位
	 */
	public final static int EXRP_HOUR = 60 * 60;            //一小时
	public final static int EXRP_DAY = 60 * 60 * 24;        //一天
	public final static int EXRP_MONTH = 60 * 60 * 24 * 30;    //一个月
	
	// --------- String ---------
	/**
	 * 设置key对应的value
	 * @param key
	 * @param value
	 */
	public static void set(String key, String value) {
		ShardedJedis shardedJedis = RedisDataSource.getResource();
		if(shardedJedis != null) {
			try {
				shardedJedis.set(key, value);
			} catch (Exception e) {
				_log.error("jolley>> set失败：" + e);
			} finally {
				RedisDataSource.release(shardedJedis);
			}
		}
	}

	/**
	 * 获取key对应的value
	 * @param key
	 * @return
	 */
	public static String get(String key) {
		ShardedJedis shardedJedis = RedisDataSource.getResource();
		if(shardedJedis != null) {
			try {
				return shardedJedis.get(key);
			} catch (Exception e) {
				_log.error("jolley>> get失败：" + e);
			} finally {
				RedisDataSource.release(shardedJedis);
			}
		}
		return null;
	}
	
	/**
	 * 向库中添加string，设定过期时间time
	 * @param key
	 * @param seconds
	 * @param value
	 */
	public static void setex(String key, int seconds, String value) {
		ShardedJedis shardedJedis = RedisDataSource.getResource();
		if(shardedJedis != null) {
			try {
				shardedJedis.setex(key, seconds, value);
			} catch (Exception e) {
				_log.error("jolley>> setex失败：" + e);
			} finally {
				RedisDataSource.release(shardedJedis);
			}
		}
	}
	
	/**
	 * 根据key 删除redis缓存数据
	 * @param key
	 */
	public static void del(String key) {
		ShardedJedis shardedJedis = RedisDataSource.getResource();
		if(shardedJedis != null) {
			try {
				shardedJedis.del(key);
			} catch (Exception e) {
				_log.error("jolley>> del失败：" + e);
			} finally {
				RedisDataSource.release(shardedJedis);
			}
		}
	}
	
	/**
	 * 判断String字符串是否存在
	 * 判断key是否存在，true 表示存在，false表示不存在
	 * @param key
	 * @return
	 */
	public static boolean exists(String key) {
		boolean flag = false;
		ShardedJedis shardedJedis = RedisDataSource.getResource();
		if(shardedJedis != null) {
			try {
				flag = shardedJedis.exists(key);
			} catch (Exception e) {
				_log.error("jolley>> exists失败：" + e);
			} finally {
				RedisDataSource.release(shardedJedis);
			}
		}
		return flag;
	}
	
	// --------- List ---------
	/**
	 * 在名称为key的list左边添加一个值为value的 元素
	 * @param key
	 * @param values
	 */
	public synchronized static void lpush(String key, String... values) {
		ShardedJedis shardedJedis = RedisDataSource.getResource();
		if(shardedJedis != null) {
			try {
				shardedJedis.lpush(key, values);
			} catch (Exception e) {
				_log.error("jolley>> lpush失败：" + e);
			} finally {
				RedisDataSource.release(shardedJedis);
			}
		}
	}
	
//	/**
//	 * 在名称为key的list右边添加一个值为value的 元素
//	 * @param key
//	 * @param values
//	 */
//	public synchronized static void rpush(String key, String... values) {
//		ShardedJedis shardedJedis = RedisDataSource.getResource();
//		if(shardedJedis != null) {
//			try {
//				shardedJedis.rpush(key, values);
//			} catch (Exception e) {
//				_log.error("jolley>> rpush失败：" + e);
//			} finally {
//				RedisDataSource.release(shardedJedis);
//			}
//		}
//	}
	
	/**
	 * 返回名称为key的list的长度
	 * @param key
	 */
	public synchronized static long llen(String key) {
		ShardedJedis shardedJedis = RedisDataSource.getResource();
		if(shardedJedis != null) {
			try {
				return shardedJedis.llen(key);
			} catch (Exception e) {
				_log.error("jolley>> llen失败：" + e);
			} finally {
				RedisDataSource.release(shardedJedis);
			}
		}
		
		return 0;
	}
	
//	/**
//	 * 返回并删除名称为key的list中最左边的第一个元素
//	 * @param key
//	 * @return
//	 */
//	public synchronized static String lpop(String key) {
//		ShardedJedis shardedJedis = RedisDataSource.getResource();
//		if(shardedJedis != null) {
//			try {
//				return shardedJedis.lpop(key);
//			} catch (Exception e) {
//				_log.error("jolley>> lpop失败：" + e);
//			} finally {
//				RedisDataSource.release(shardedJedis);
//			}
//		}
//		
//		return null;
//	}
	
	/**
	 * 返回并删除名称为key的list中最右边的第一个元素
	 * @param key
	 * @return
	 */
	public synchronized static String rpop(String key) {
		ShardedJedis shardedJedis = RedisDataSource.getResource();
		if(shardedJedis != null) {
			try {
				return shardedJedis.rpop(key);
			} catch (Exception e) {
				_log.error("jolley>> rpop失败：" + e);
			} finally {
				RedisDataSource.release(shardedJedis);
			}
		}
		
		return null;
	}
	
	/**
	 * 返回并删除名称为key的list中最右边的第一个元素
	 * @param key
	 * @return
	 */
	public static List<String> lrange(String key, long start, long end) {
		ShardedJedis shardedJedis = RedisDataSource.getResource();
		if(shardedJedis != null) {
			try {
				return shardedJedis.lrange(key, start, end);
			} catch (Exception e) {
				_log.error("jolley>> lrange失败：" + e);
			} finally {
				RedisDataSource.release(shardedJedis);
			}
		}
		
		return null;
	}
	
	/**
	 * 移除列表元素
	 * @param key
	 * @return
	 */
	public static long lrem(String key, int count, String value) {
		ShardedJedis shardedJedis = RedisDataSource.getResource();
		if(shardedJedis != null) {
			try {
				return shardedJedis.lrem(key, count, value);
			} catch (Exception e) {
				_log.error("jolley>> lrem失败：" + e);
			} finally {
				RedisDataSource.release(shardedJedis);
			}
		}
		
		return -1;
	}
	
	// --------- Hash ---------
	/**
	 * 向名称为key的hash中添加元素field
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	public static long hset(String key, String field, String value) {
		ShardedJedis shardedJedis = RedisDataSource.getResource();
		if(shardedJedis != null) {
			try {
				return shardedJedis.hset(key, field, value);
			} catch (Exception e) {
				_log.error("jolley>> hset失败：" + e);
			} finally {
				RedisDataSource.release(shardedJedis);
			}
		}
		
		return -1;
	}
	
	/**
	 * 返回名称为key的hash中field对应的value
	 * @param key
	 * @param field
	 * @return
	 */
	public static String hget(String key, String field) {
		ShardedJedis shardedJedis = RedisDataSource.getResource();
		if(shardedJedis != null) {
			try {
				return shardedJedis.hget(key, field);
			} catch (Exception e) {
				_log.error("jolley>> hget失败：" + e);
			} finally {
				RedisDataSource.release(shardedJedis);
			}
		}
		
		return null;
	}
	
	/**
	 * 返回名称为key的hash中field i对应的value
	 * @param key
	 * @param fields
	 * @return
	 */
	public static List<String> hmget(String key, String... fields) {
		ShardedJedis shardedJedis = RedisDataSource.getResource();
		if(shardedJedis != null) {
			try {
				return shardedJedis.hmget(key, fields);
			} catch (Exception e) {
				_log.error("jolley>> hmget失败：" + e);
			} finally {
				RedisDataSource.release(shardedJedis);
			}
		}
		
		return null;
	}
	
	/**
	 * 向名称为key的hash中添加元素field
	 * @param key
	 * @param map
	 */
	public static void hmset(String key, Map<String, String> map) {
		ShardedJedis shardedJedis = RedisDataSource.getResource();
		if(shardedJedis != null) {
			try {
				shardedJedis.hmset(key, map);
			} catch (Exception e) {
				_log.error("jolley>> hmset失败：" + e);
			} finally {
				RedisDataSource.release(shardedJedis);
			}
		}
	}
	
	/**
	 * 名称为key的hash中是否存在键为field的域
	 * @param key
	 * @param field
	 * @return
	 */
	public static boolean hexists(String key, String field) {
		boolean flag = false;
		ShardedJedis shardedJedis = RedisDataSource.getResource();
		if(shardedJedis != null) {
			try {
				flag = shardedJedis.hexists(key, field);
			} catch (Exception e) {
				_log.error("jolley>> hexists失败：" + e);
			} finally {
				RedisDataSource.release(shardedJedis);
			}
		}
		return flag;
	}
	
	/**
	 * 删除名称为key的hash中键为field的域
	 * @param key
	 * @param fields
	 */
	public static void hdel(String key, String... fields) {
		ShardedJedis shardedJedis = RedisDataSource.getResource();
		if(shardedJedis != null) {
			try {
				shardedJedis.hdel(key, fields);
			} catch (Exception e) {
				_log.error("jolley>> hdel失败：" + e);
			} finally {
				RedisDataSource.release(shardedJedis);
			}
		}
	}
	
	/**
	 * 返回名称为key的hash中元素个数
	 * @param key
	 * @return
	 */
	public static long hlen(String key) {
		ShardedJedis shardedJedis = RedisDataSource.getResource();
		if(shardedJedis != null) {
			try {
				return shardedJedis.hlen(key);
			} catch (Exception e) {
				_log.error("jolley>> hlen失败：" + e);
			} finally {
				RedisDataSource.release(shardedJedis);
			}
		}
		
		return 0;
	}
	
	/**
	 * 返回名称为key的hash中所有键
	 * @param key
	 * @return
	 */
	public static Set<String> hkeys(String key) {
		ShardedJedis shardedJedis = RedisDataSource.getResource();
		if(shardedJedis != null) {
			try {
				return shardedJedis.hkeys(key);
			} catch (Exception e) {
				_log.error("jolley>> hkeys失败：" + e);
			} finally {
				RedisDataSource.release(shardedJedis);
			}
		}
		
		return null;
	}
	
	/**
	 * 返回名称为key的hash中所有键对应的value
	 * @param key
	 * @return
	 */
	public static List<String> hvals(String key) {
		ShardedJedis shardedJedis = RedisDataSource.getResource();
		if(shardedJedis != null) {
			try {
				return shardedJedis.hvals(key);
			} catch (Exception e) {
				_log.error("jolley>> hvals失败：" + e);
			} finally {
				RedisDataSource.release(shardedJedis);
			}
		}
		
		return null;
	}
	
	/**
	 * 返回名称为key的hash中所有的键（field）及其对应的value
	 * @param key
	 * @return
	 */
	public static Map<String, String> hgetAll(String key) {
		ShardedJedis shardedJedis = RedisDataSource.getResource();
		if(shardedJedis != null) {
			try {
				return shardedJedis.hgetAll(key);
			} catch (Exception e) {
				_log.error("jolley>> hgetAll失败：" + e);
			} finally {
				RedisDataSource.release(shardedJedis);
			}
		}
		return null;
	}
	
	// --------- Set ---------
	/**
	 * 将一个或多个成员元素加入到集合中
	 * @param key
	 * @param members
	 * @return
	 */
	public static long sadd(String key, String... members) {
		ShardedJedis shardedJedis = RedisDataSource.getResource();
		if(shardedJedis != null) {
			try {
				return shardedJedis.sadd(key, members);
			} catch (Exception e) {
				_log.error("jolley>> sadd失败：" + e);
			} finally {
				RedisDataSource.release(shardedJedis);
			}
		}
		return -1;
	}
	
	/**
	 * 判断 member 元素是否是集合 key 的成员
	 * @param key
	 * @param member
	 * @return
	 */
	public static boolean sismember(String key, String member) {
		ShardedJedis shardedJedis = RedisDataSource.getResource();
		if(shardedJedis != null) {
			try {
				return shardedJedis.sismember(key, member);
			} catch (Exception e) {
				_log.error("jolley>> sismember失败：" + e);
			} finally {
				RedisDataSource.release(shardedJedis);
			}
		}
		return false;
	}
	
	/**
	 * 返回集合中的所有成员
	 * @param key
	 * @param member
	 * @return
	 */
	public static Set<String> smembers(String key) {
		ShardedJedis shardedJedis = RedisDataSource.getResource();
		if(shardedJedis != null) {
			try {
				return shardedJedis.smembers(key);
			} catch (Exception e) {
				_log.error("jolley>> smembers失败：" + e);
			} finally {
				RedisDataSource.release(shardedJedis);
			}
		}
		return null;
	}
	
	/**
	 * 移除集合中一个或多个成员
	 * @param key
	 * @param members
	 * @return
	 */
	public static long srem(String key, String... members) {
		ShardedJedis shardedJedis = RedisDataSource.getResource();
		if(shardedJedis != null) {
			try {
				return shardedJedis.srem(key, members);
			} catch (Exception e) {
				_log.error("jolley>> srem失败：" + e);
			} finally {
				RedisDataSource.release(shardedJedis);
			}
		}
		return -1;
	}
	

}
