package com.alipay.apmobilesecuritysdk.type;

public abstract class DevType<T> {
    protected T a;
    protected int b;

    public abstract byte[] a();

    public DevType(T t, int i) {
        this.a = t;
        this.b = i;
    }

    public final T b() {
        return this.a;
    }

    public final int c() {
        return this.b;
    }
}
