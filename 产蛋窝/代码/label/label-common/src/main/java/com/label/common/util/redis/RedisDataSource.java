package com.label.common.util.redis;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.label.common.util.UtilAES;
import com.label.common.util.UtilPropertiesFile;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class RedisDataSource {

//	protected static ReentrantLock lockPool = new ReentrantLock();
//	protected static ReentrantLock lockJedis = new ReentrantLock();

	private static Logger _log = LoggerFactory.getLogger(RedisDataSource.class);

	// Redis服务器IP
	private static String IP = UtilPropertiesFile.getInstance("redis").get("master.redis.ip");

	// Redis的端口号
	private static int PORT = UtilPropertiesFile.getInstance("redis").getInt("master.redis.port");

	// 访问密码
	private static String PASSWORD = UtilAES.AESDecode(UtilPropertiesFile.getInstance("redis").get("master.redis.password"));
	// 可用连接实例的最大数目，默认值为8；
	// 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
	private static int MAX_ACTIVE = UtilPropertiesFile.getInstance("redis").getInt("master.redis.max_active");

	// 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
	private static int MAX_IDLE = UtilPropertiesFile.getInstance("redis").getInt("master.redis.max_idle");

	// 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
	private static int MAX_WAIT = UtilPropertiesFile.getInstance("redis").getInt("master.redis.max_wait");

	// 超时时间
	private static int TIMEOUT = UtilPropertiesFile.getInstance("redis").getInt("master.redis.timeout");

	// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
	private static boolean TEST_ON_BORROW = false;

	private static ShardedJedisPool shardedJedisPool;
	
	/**
	 * 初始化Redis连接池
	 */
	private synchronized static void initialPool() {
		if(shardedJedisPool == null) {
			try {
				// 池基本配置 
		        JedisPoolConfig config = new JedisPoolConfig();
		        config.setMaxTotal(MAX_ACTIVE);
		        config.setMaxIdle(MAX_IDLE);
		        config.setMaxWaitMillis(MAX_WAIT);
		        config.setTestOnBorrow(TEST_ON_BORROW);
		        // slave链接
		        List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		        JedisShardInfo info = new JedisShardInfo(IP, PORT, TIMEOUT, "master");
		        info.setPassword(PASSWORD);
		        shards.add(info);
		        // 构造池 
		        shardedJedisPool = new ShardedJedisPool(config, shards);
			} catch (Exception e) {
				_log.error("jolley>> 创建ShardedJedisPool连接池失败: " + e);
			}
		}
	}
	
	/**
	 * 获取ShardedJedis资源
	 * @return
	 */
	public synchronized static ShardedJedis getResource() {
		ShardedJedis shardedJedis = null;
		try {
			initialPool();
			if(shardedJedisPool != null) {
				shardedJedis = shardedJedisPool.getResource();
			}
		} catch (Exception e) {
			_log.error("jolley>> 获取ShardedJedis连接池资源资源失败: " + e);
		}
		return shardedJedis;
	}
	
	/**
	 * 释放连接池资源
	 * @param shardedJedis
	 */
	public static void release(ShardedJedis shardedJedis) {
		try {
			if(shardedJedis != null) {
				shardedJedis.close();
			}
		} catch (Exception e) {
			_log.error("jolley>> 释放ShardedJedis连接池资源失败: " + e);
			e.printStackTrace();
		}
	}
	
}
