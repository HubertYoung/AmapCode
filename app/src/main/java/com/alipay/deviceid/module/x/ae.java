package com.alipay.deviceid.module.x;

import java.lang.reflect.Type;

public final class ae implements aj, ak {
    public final Object a(Object obj) {
        return ((Enum) obj).name();
    }

    public final Object a(Object obj, Type type) {
        return Enum.valueOf((Class) type, obj.toString());
    }

    public final boolean a(Class<?> cls) {
        return Enum.class.isAssignableFrom(cls);
    }
}
