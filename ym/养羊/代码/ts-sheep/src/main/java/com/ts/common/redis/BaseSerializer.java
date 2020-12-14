package com.ts.common.redis;

/**
 *
 *
 * @author SoulBGM
 * @version 1.0
 * @date 2019/11/29 16:55
 */
public interface BaseSerializer { 

    /**
     * 反序列化
     *
     * @param val    数据
     * @param tClass 实体对象Class
     * @return 实体对象
     */
    <T> T deserialize(Object val, Class<T> tClass);

    /**
     * 序列化
     *
     * @param val 数据
     * @return 序列化之后数据
     */
    Object serialize(Object val);
}
