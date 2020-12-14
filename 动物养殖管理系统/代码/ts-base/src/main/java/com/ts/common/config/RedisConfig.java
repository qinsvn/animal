package com.ts.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import com.ts.common.redis.JdkSerializer;
import com.ts.common.redis.RedisUtil;

/**
 * @author SoulBGM
 * @version 1.0
 * @date 2019/11/28 17:09
 */
@Component
public class RedisConfig {

    private RedisConnectionFactory redisConnectionFactory;

    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    public RedisConfig(RedisConnectionFactory redisConnectionFactory, StringRedisTemplate stringRedisTemplate) {
        this.redisConnectionFactory = redisConnectionFactory;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }

    @Bean
    public RedisUtil redisUtil(RedisTemplate<String, Object> redisTemplate) {
//        return new CommonRedisTool(redisTemplate, stringRedisTemplate, new JacksonSerializer());
//        return new CommonRedisTool(redisTemplate, stringRedisTemplate, new FastJsonSerializer());
    	return new RedisUtil(redisTemplate, stringRedisTemplate, new JdkSerializer());
    }

}
