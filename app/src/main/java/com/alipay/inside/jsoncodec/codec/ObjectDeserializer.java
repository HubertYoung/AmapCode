package com.alipay.inside.jsoncodec.codec;

import java.lang.reflect.Type;

public interface ObjectDeserializer {
    Object deserialize(Object obj, Type type) throws Exception;

    boolean match(Class<?> cls);
}
