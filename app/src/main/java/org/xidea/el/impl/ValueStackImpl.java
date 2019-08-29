package org.xidea.el.impl;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class ValueStackImpl implements Map<String, Object> {
    protected Object[] a;

    /* access modifiers changed from: protected */
    public Object a() {
        return null;
    }

    public void clear() {
    }

    public boolean containsKey(Object obj) {
        return false;
    }

    public boolean containsValue(Object obj) {
        return false;
    }

    public Set<Entry<String, Object>> entrySet() {
        return null;
    }

    public boolean isEmpty() {
        return false;
    }

    public Set<String> keySet() {
        return null;
    }

    public void putAll(Map<? extends String, ? extends Object> map) {
    }

    public Object remove(Object obj) {
        return null;
    }

    public int size() {
        return 0;
    }

    public Collection<Object> values() {
        return null;
    }

    public /* synthetic */ Object put(Object obj, Object obj2) {
        ReflectUtil.a(this.a[this.a.length - 1], (String) obj, obj2);
        return null;
    }

    public ValueStackImpl(Object... objArr) {
        this.a = objArr;
    }

    public Object get(Object obj) {
        Object obj2;
        int length = this.a.length;
        while (true) {
            int i = length - 1;
            if (length <= 0) {
                return a();
            }
            Object obj3 = this.a[i];
            if (obj3 instanceof Map) {
                Map map = (Map) obj3;
                obj2 = map.get(obj);
                if (obj2 != null || map.containsKey(obj)) {
                    return obj2;
                }
            } else if (obj3 != null) {
                Object a2 = ReflectUtil.a(obj3, obj);
                Class<?> cls = obj3.getClass();
                if (a2 != null || ReflectUtil.a((Type) cls, obj) != null) {
                    return a2;
                }
                if (obj instanceof String) {
                    return ExpressionFactoryImpl.a(cls, (String) obj);
                }
            } else {
                continue;
            }
            length = i;
        }
        return obj2;
    }
}
