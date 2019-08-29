package org.xidea.el.impl;

import org.xidea.el.Reference;

class ReferenceImpl implements Reference {
    Object a;
    Object b;

    public ReferenceImpl(Object obj, Object obj2) {
        this.a = obj;
        this.b = obj2;
    }

    public String toString() {
        return String.valueOf(a());
    }

    public final Object a() {
        return ReflectUtil.a(this.a, this.b);
    }
}
