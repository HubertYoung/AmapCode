package com.alipay.inside.jsoncodec.codec;

import com.alipay.inside.jsoncodec.util.ClassUtil;
import java.lang.reflect.Type;

public class SimpleClassCodec implements ObjectDeserializer, ObjectSerializer {
    public Object serialize(Object obj) throws Exception {
        return obj;
    }

    public Object deserialize(Object obj, Type type) throws Exception {
        return (!String.class.equals(type) || (obj instanceof String)) ? obj : obj.toString();
    }

    public boolean match(Class<?> cls) {
        return ClassUtil.isSimpleType(cls);
    }
}
