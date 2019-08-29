package com.alipay.android.phone.inside.protobuf.wire;

import java.util.LinkedHashMap;
import java.util.Map;

final class ExtensionRegistry {
    private final Map<Class<? extends ExtendableMessage>, Map<Integer, Extension<?, ?>>> a = new LinkedHashMap();
    private final Map<Class<? extends ExtendableMessage>, Map<String, Extension<?, ?>>> b = new LinkedHashMap();

    ExtensionRegistry() {
    }

    public final <T extends ExtendableMessage<?>, E> void a(Extension<T, E> extension) {
        Class a2 = extension.a();
        Map map = this.a.get(a2);
        Map map2 = this.b.get(a2);
        if (map == null) {
            map = new LinkedHashMap();
            map2 = new LinkedHashMap();
            this.a.put(a2, map);
            this.b.put(a2, map2);
        }
        map.put(Integer.valueOf(extension.e()), extension);
        map2.put(extension.d(), extension);
    }

    public final <T extends ExtendableMessage<?>, E> Extension<T, E> a(Class<T> cls, int i) {
        Map map = this.a.get(cls);
        if (map == null) {
            return null;
        }
        return (Extension) map.get(Integer.valueOf(i));
    }
}
