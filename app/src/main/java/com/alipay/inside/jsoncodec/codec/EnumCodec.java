package com.alipay.inside.jsoncodec.codec;

import java.lang.reflect.Type;

public class EnumCodec implements ObjectDeserializer, ObjectSerializer {
    public Object deserialize(Object obj, Type type) throws Exception {
        return Enum.valueOf((Class) type, obj.toString());
    }

    public Object serialize(Object obj) throws Exception {
        return ((Enum) obj).name();
    }

    public boolean match(Class<?> cls) {
        return Enum.class.isAssignableFrom(cls);
    }
}
