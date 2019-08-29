package org.xidea.el.impl;

import java.lang.reflect.Type;
import java.util.Map;

/* compiled from: ValueStackImpl */
class RefrenceStackImpl extends ValueStackImpl {
    public Object get(Object obj) {
        int length = this.a.length;
        while (true) {
            int i = length - 1;
            if (length <= 0) {
                return null;
            }
            Object obj2 = this.a[i];
            if (obj2 instanceof Map) {
                return new ReferenceImpl(obj2, obj);
            }
            if (ReflectUtil.a((Type) obj2.getClass(), obj) != null) {
                return new ReferenceImpl(obj2, obj);
            }
            length = i;
        }
    }
}
