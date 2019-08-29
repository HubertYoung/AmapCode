package com.alipay.deviceid.module.rpc.mrpc.core;

import java.lang.reflect.Proxy;

public final class x {
    private g a;
    private z b = new z(this);

    public x(g gVar) {
        this.a = gVar;
    }

    public final g a() {
        return this.a;
    }

    public final <T> T a(Class<T> cls) {
        return Proxy.newProxyInstance(cls.getClassLoader(), new Class[]{cls}, new y(this.a, cls, this.b));
    }
}
