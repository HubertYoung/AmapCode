package com.alipay.deviceid.module.rpc.mrpc.core;

import java.lang.reflect.Method;

public abstract class a implements v {
    protected Method a;
    protected byte[] b;
    protected String c;
    protected int d;
    protected String e;
    protected boolean f;

    public a(Method method, int i, String str, byte[] bArr, String str2, boolean z) {
        this.a = method;
        this.d = i;
        this.c = str;
        this.b = bArr;
        this.e = str2;
        this.f = z;
    }
}
