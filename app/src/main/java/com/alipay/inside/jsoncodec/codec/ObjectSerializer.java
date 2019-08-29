package com.alipay.inside.jsoncodec.codec;

public interface ObjectSerializer {
    boolean match(Class<?> cls);

    Object serialize(Object obj) throws Exception;
}
