package com.alipay.deviceid.module.x;

import java.lang.reflect.Type;
import java.util.Date;

public final class ad implements aj, ak {
    public final Object a(Object obj) {
        return Long.valueOf(((Date) obj).getTime());
    }

    public final Object a(Object obj, Type type) {
        return new Date(((Long) obj).longValue());
    }

    public final boolean a(Class<?> cls) {
        return Date.class.isAssignableFrom(cls);
    }
}
