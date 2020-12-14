package com.ts.common.redis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * 通用Redis工具类
 *
 * @author SoulBGM
 * @version 1.0
 * @date 2019/11/28 16:59
 */
public class RedisUtil {

    private static RedisTemplate<String, Object> redisTemplate;

    private static StringRedisTemplate stringRedisTemplate;

    private static BaseSerializer serializer;

    public RedisUtil(RedisTemplate<String, Object> redisTemplate, StringRedisTemplate stringRedisTemplate) {
        this(redisTemplate, stringRedisTemplate, new FastJsonSerializer());
    }

    public RedisUtil(RedisTemplate<String, Object> redisTemplate, StringRedisTemplate stringRedisTemplate, BaseSerializer serializer) {
        RedisUtil.redisTemplate = redisTemplate;
        RedisUtil.stringRedisTemplate = stringRedisTemplate;
        RedisUtil.serializer = serializer; 
    }

    public static void setSerializer(BaseSerializer serializer) {
    	RedisUtil.serializer = serializer;
    }

    /**
     * 检查是否为null或者空字符的@{code key}
     *
     * @param key Key
     */
    private static void checkNullKey(String key) {
        if (key == null || "".equals(key)) {
            throw new RuntimeException("key不允许为空");
        }
    }

    /**
     * 序列化
     *
     * @param value 多个数据
     * @return 序列化之后数据
     */
    private static Object[] serialize(Object[] value) {
        Object[] newVal = new Object[value.length];
        for (int i = 0; i < value.length; i++) {
            newVal[i] = serialize(value[i]);
        }
        return newVal;
    }

    /**
     * 序列化
     *
     * @param value 数据
     * @return 序列化之后数据
     */
    private static  Object serialize(Object value) {
        return serializer.serialize(value);
    }

    /**
     * 反序列化
     *
     * @param value  数据
     * @param tClass 实体对象Class
     * @return 实体对象
     */
    private static  <T> T deserialize(Object value, Class<T> tClass) {
        return serializer.deserialize(value, tClass);
    }

    public static Long ttl(String key) {
        return ttl(key, TimeUnit.SECONDS);
    }

    
    public static  Long ttl(String key, TimeUnit timeUnit) {
        checkNullKey(key);
        return redisTemplate.getExpire(key, timeUnit);
    }

    
    public static  Boolean expire(String key, long timeout) {
        return expire(key, timeout, TimeUnit.SECONDS);
    }

    
    public static  Boolean expire(String key, long timeout, TimeUnit timeUnit) {
        checkNullKey(key);
        return redisTemplate.expire(key, timeout, timeUnit);
    }

    
    public static  long incr(String key, long increaseValue) {
        redisTemplate.opsForValue().increment(key, increaseValue);
        return 0;
    }

    /**
     * 删除给定的{@code key}
     *
     * @param key 多个key
     * @return 删除的Key数
     */
    
    public static  Long del(Object... key) {
        if (key == null || key.length == 0) {
            return 0L;
        }
        List<String> keyList = new ArrayList<>(key.length);
        for (Object k : key) {
            keyList.add(String.valueOf(k));
        }
        return del(keyList);
    }

    /**
     * 删除给定的{@code key}
     *
     * @param keyList 多个key
     * @return 删除的Key数
     */
    
    public static  Long del(Collection<String> keyList) {
        return redisTemplate.delete(keyList);
    }

    
    public static  Set<String> keys(String pattern) {
        checkNullKey(pattern);
        return redisTemplate.keys(pattern);
    }

    
    public static  Boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 为{@code value}设置{@code key}
     *
     * @param key   Key
     * @param value Value
     */
    
    public static  <T> void set(String key, T value) {
        checkNullKey(key);
        if (value.getClass() == String.class) {
            stringRedisTemplate.opsForValue().set(key, (String) value);
        } else {
            redisTemplate.opsForValue().set(key, serialize(value));
        }
    }

    /**
     * 设置{@code key}的{@code value}和过期{@code timeout}。
     * 单位秒
     *
     * @param key     Key
     * @param value   Value
     * @param timeout 超时时间
     */
    
    public static  <T> void set(String key, T value, long timeout) {
        set(key, value, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置{@code key}的{@code value}和过期{@code timeout}。
     *
     * @param key      Key
     * @param value    Value
     * @param timeout  超时时间
     * @param timeUnit 时间单位
     */
    
    public static  <T> void set(String key, T value, long timeout, TimeUnit timeUnit) {
        checkNullKey(key);
        if (value.getClass() == String.class) {
            stringRedisTemplate.opsForValue().set(key, (String) value, timeout, timeUnit);
        } else {
            redisTemplate.opsForValue().set(key, serialize(value), timeout, timeUnit);
        }
    }

    
    public static  Boolean setNx(String key, Object value) {
        checkNullKey(key);
        if (value.getClass() == String.class) {
            return stringRedisTemplate.opsForValue().setIfAbsent(key, (String) value);
        } else {
            return redisTemplate.opsForValue().setIfAbsent(key, serialize(value));
        }
    }

    
    public static  void setEx(String key, Object value, long timeout) {
        set(key, value, timeout);
    }

    
    public static  Boolean setNxEx(String key, Object value, long timeout) {
        checkNullKey(key);
        if (value.getClass() == String.class) {
            return stringRedisTemplate.opsForValue().setIfAbsent(key, (String) value, timeout, TimeUnit.SECONDS);
        } else {
            return redisTemplate.opsForValue().setIfAbsent(key, serialize(value), timeout, TimeUnit.SECONDS);
        }
    }

    
    public static  String get(String key) {
        checkNullKey(key);
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 获取{@code key}的值
     *
     * @param key Key
     * @return 值
     */
    
    public static  <T> T get(String key, Class<T> tClass) {
        checkNullKey(key);
        return deserialize(redisTemplate.opsForValue().get(key), tClass);
    }

    
    public static  String getAndSet(String key, String value) {
        checkNullKey(key);
        return stringRedisTemplate.opsForValue().getAndSet(key, value);
    }

    
    public static  <T> T getAndSet(String key, Object value, Class<T> tClass) {
        checkNullKey(key);
        return deserialize(redisTemplate.opsForValue().getAndSet(key, serialize(value)), tClass);
    }

    
    public static  void hset(String key, String field, Object value) {
        checkNullKey(key);
        redisTemplate.opsForHash().put(key, field, serialize(value));
    }

    
    public static  void hsetAll(String key, Map<String, ?> map) {
        checkNullKey(key);
        Map<String, Object> newMap = new HashMap<>(map.size());
        Set<String> keySet = map.keySet();
        for (String k : keySet) {
            newMap.put(k, serialize(map.get(k)));
        }
        redisTemplate.opsForHash().putAll(key, newMap);
    }

    
    public static  <T> Map<String, T> hgetAll(String key, Class<T> tClass) {
        checkNullKey(key);
        Map<Object, Object> map = redisTemplate.opsForHash().entries(key);
        Map<String, T> newMap = new HashMap<>(map.size());
        Set<Object> keySet = map.keySet();
        for (Object o : keySet) {
            String k = String.valueOf(o);
            newMap.put(k, deserialize(map.get(o), tClass));
        }
        return newMap;
    }

    
    public static  <T> T hget(String key, String field, Class<T> tClass) {
        checkNullKey(key);
        checkNullKey(field);
        return deserialize(redisTemplate.opsForHash().get(key, field), tClass);
    }

    
    public static  Map<Object, Object> hgetAll(String key) {
        checkNullKey(key);
        return redisTemplate.opsForHash().entries(key);
    }

    
    public static  Long hdel(String key, Object... field) {
        checkNullKey(key);
        return redisTemplate.opsForHash().delete(key, field);
    }

    
    public static  <T> Long lpush(String key, T value) {
        checkNullKey(key);
        return redisTemplate.opsForList().leftPush(key, serialize(value));
    }

    
    public static  <T> Long rpush(String key, T data) {
        checkNullKey(key);
        return redisTemplate.opsForList().rightPush(key, serialize(data));
    }

    
    public static  <T> List<T> lget(String key, long start, long end, Class<T> tClass) {
        checkNullKey(key);
        List<Object> list = redisTemplate.opsForList().range(key, start, end);
        if (list != null && list.size() > 0) {
            List<T> newList = new ArrayList<>(list.size());
            for (Object o : list) {
                newList.add(deserialize(o, tClass));
            }
            return newList;
        }
        return new ArrayList<>(0);
    }

    
    public static  <T> List<T> lget(String key, Class<T> tClass) {
        return lget(key, 0, listSize(key), tClass);
    }

    
    public static  Long listSize(String key) {
        checkNullKey(key);
        return redisTemplate.opsForList().size(key);
    }

    
    public static  Long setAdd(String key, Object... value) {
        checkNullKey(key);
        return redisTemplate.opsForSet().add(key, serialize(value));
    }

    
    public static  Long setRemove(String key, Object... value) {
        checkNullKey(key);
        return redisTemplate.opsForSet().remove(key, serialize(value));
    }

    
    public static  <T> Set<T> getSetAll(String key, Class<T> tClass) {
        checkNullKey(key);
        Set<Object> members = redisTemplate.opsForSet().members(key);
        if (members == null || members.size() == 0) {
            return new HashSet<>(0);
        }
        Set<T> newSet = new HashSet<>(members.size());
        for (Object v : members) {
            newSet.add(deserialize(v, tClass));
        }
        return newSet;
    }

    
    public static  Boolean setExist(String key, Object value) {
        checkNullKey(key);
        return redisTemplate.opsForSet().isMember(key, serialize(value));
    }

    
    public static  Long setSize(String key) {
        checkNullKey(key);
        return redisTemplate.opsForSet().size(key);
    }

    
    public static  <T> T getSetRandom(String key, Class<T> tClass) {
        checkNullKey(key);
        return deserialize(redisTemplate.opsForSet().randomMember(key), tClass);
    }

    
    public static  Boolean zsetAdd(String key, Object value, double score) {
        checkNullKey(key);
        return redisTemplate.opsForZSet().add(key, serialize(value), score);
    }

    
    public static  Boolean zsetStartAdd(String key, Object value) {
        checkNullKey(key);
        return redisTemplate.opsForZSet().add(key, serialize(value), -zsetSize(key));
    }

    
    public static  Boolean zsetEndAdd(String key, Object value) {
        checkNullKey(key);
        return redisTemplate.opsForZSet().add(key, serialize(value), zsetSize(key));
    }

    
    public static  Long zsetRemove(String key, Object... value) {
        checkNullKey(key);
        return redisTemplate.opsForZSet().remove(key, serialize(value));
    }

    
    public static  Long zsetRemoveRange(String key, long start, long end) {
        checkNullKey(key);
        return redisTemplate.opsForZSet().removeRange(key, start, end);
    }

    
    public static  <T> Set<T> zsetRange(String key, long start, long end, Class<T> tClass) {
        checkNullKey(key);
        Set<Object> set = redisTemplate.opsForZSet().range(key, start, end);
        if (set != null && set.size() > 0) {
            Set<T> newSet = new HashSet<>(set.size());
            for (Object v : set) {
                newSet.add(deserialize(v, tClass));
            }
            return newSet;
        }
        return new HashSet<>(0);
    }

    
    public static  <T> Set<T> zsetAll(String key, Class<T> tClass) {
        return zsetRange(key, 0, zsetSize(key), tClass);
    }

    
    public static  Long zsetSize(String key) {
        checkNullKey(key);
        return redisTemplate.opsForZSet().size(key);
    }

    
    public <T> T eval(String script, Class<T> tClass, List<String> keys, String... args) {
        return null;
    }

//    public static RedisUtil obj(){
//    	return (RedisUtil)SpringUtils.getBean("RedisUtil");
//    }
}
