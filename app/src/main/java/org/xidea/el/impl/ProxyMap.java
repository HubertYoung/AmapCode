package org.xidea.el.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

class ProxyMap extends HashMap<String, Object> {
    private static final long serialVersionUID = 1;

    public /* synthetic */ Object put(Object obj, Object obj2) {
        Object put = super.put((String) obj, obj2);
        if (!(put instanceof ReferenceImpl)) {
            return put;
        }
        ReferenceImpl referenceImpl = (ReferenceImpl) put;
        ReflectUtil.a(referenceImpl.a, referenceImpl.b, obj2);
        return null;
    }

    public Object get(Object obj) {
        Object obj2 = super.get(obj);
        return obj2 instanceof ReferenceImpl ? ((ReferenceImpl) obj2).a() : obj2;
    }

    public Set<Entry<String, Object>> entrySet() {
        Set<Entry<String, Object>> entrySet = super.entrySet();
        for (Entry next : entrySet) {
            Object value = next.getValue();
            if (value instanceof ReferenceImpl) {
                next.setValue(((ReferenceImpl) value).a());
            }
        }
        return entrySet;
    }

    public Collection<Object> values() {
        entrySet();
        return super.values();
    }
}
