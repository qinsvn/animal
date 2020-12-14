package com.ts.common.redis;
import com.alibaba.fastjson.JSONObject;

/**
 * @author SoulBGM
 * @version 1.0
 * @date 2019/11/29 16:54
 */
public class FastJsonSerializer implements BaseSerializer {

    @Override
    public <T> T deserialize(Object val, Class<T> tClass) {
        return JSONObject.parseObject(String.valueOf(val), tClass);
    }

    @Override
    public Object serialize(Object val) {
        return JSONObject.toJSONString(val);
    }
}
