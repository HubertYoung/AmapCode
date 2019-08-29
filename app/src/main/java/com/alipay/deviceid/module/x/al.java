package com.alipay.deviceid.module.x;

import com.alipay.deviceid.module.rpc.json.a;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

public final class al implements aj {
    public final Object a(Object obj, Type type) {
        if (!obj.getClass().equals(a.class)) {
            return null;
        }
        a aVar = (a) obj;
        HashSet hashSet = new HashSet();
        Type type2 = type instanceof ParameterizedType ? ((ParameterizedType) type).getActualTypeArguments()[0] : Object.class;
        for (int i = 0; i < aVar.a(); i++) {
            hashSet.add(af.a(aVar.a(i), type2));
        }
        return hashSet;
    }

    public final boolean a(Class<?> cls) {
        return Set.class.isAssignableFrom(cls);
    }
}
