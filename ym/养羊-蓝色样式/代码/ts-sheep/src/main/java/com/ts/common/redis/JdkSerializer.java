package com.ts.common.redis;

/**
 * @author SoulBGM
 * @version 1.0
 * @date 2019/12/1 23:12
 */
public class JdkSerializer implements BaseSerializer {

    @Override
    public <T> T deserialize(Object val, Class<T> tClass) {
        return (T) val;
    }

    @Override
    public Object serialize(Object val) {
        return val;
    }
}