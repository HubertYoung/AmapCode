package com.alipay.inside.jsoncodec.codec;

import java.lang.reflect.Type;
import java.util.Date;

public class DateCodec implements ObjectDeserializer, ObjectSerializer {
    public Object deserialize(Object obj, Type type) throws Exception {
        return new Date(((Long) obj).longValue());
    }

    public Object serialize(Object obj) throws Exception {
        return Long.valueOf(((Date) obj).getTime());
    }

    public boolean match(Class<?> cls) {
        return Date.class.isAssignableFrom(cls);
    }
}
