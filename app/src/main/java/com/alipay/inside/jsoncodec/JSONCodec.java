package com.alipay.inside.jsoncodec;

import com.alipay.inside.jsoncodec.codec.JSONDeserializer;
import com.alipay.inside.jsoncodec.codec.JSONSerializer;
import com.alipay.inside.jsoncodec.util.ClassUtil;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public abstract class JSONCodec {
    public static final Object parseObject(String str, Type type) throws Exception {
        return JSONDeserializer.deserialize(str, type);
    }

    public static final <T> List<T> parseArray(String str, Type type) throws Exception {
        return (List) JSONDeserializer.deserialize(str, ClassUtil.makeParameterizedType(List.class, type));
    }

    public static final <K, V> Map<K, V> parseMap(String str, Type type, Type type2) throws Exception {
        return (Map) JSONDeserializer.deserialize(str, ClassUtil.makeParameterizedType(Map.class, type, type2));
    }

    public static String toJSONString(Object obj) throws Exception {
        return JSONSerializer.serialize(obj);
    }
}
