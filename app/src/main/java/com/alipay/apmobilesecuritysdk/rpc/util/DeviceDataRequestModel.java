package com.alipay.apmobilesecuritysdk.rpc.util;

import com.alipay.apmobilesecuritysdk.type.DevType;
import com.alipay.security.mobile.module.commonutils.CommonUtils;
import java.util.HashMap;
import java.util.Map;

public class DeviceDataRequestModel {
    private String a;
    private String b;
    private String c;
    private String d;
    private String e;
    private String f;
    private String g;
    private Map<String, DevType<?>> h;

    public final void a(String str) {
        this.a = str;
    }

    public final String a() {
        return this.g;
    }

    public final void b(String str) {
        this.g = str;
    }

    public final String b() {
        return CommonUtils.getNonNullString(this.b);
    }

    public final void c(String str) {
        this.b = str;
    }

    public final String c() {
        return CommonUtils.getNonNullString(this.c);
    }

    public final void d(String str) {
        this.c = str;
    }

    public final String d() {
        return CommonUtils.getNonNullString(this.d);
    }

    public final void e(String str) {
        this.d = str;
    }

    public final void f(String str) {
        this.e = str;
    }

    public final String e() {
        return CommonUtils.getNonNullString(this.f);
    }

    public final void g(String str) {
        this.f = str;
    }

    public final Map<String, DevType<?>> f() {
        return this.h == null ? new HashMap() : this.h;
    }

    public final void a(Map<String, DevType<?>> map) {
        this.h = map;
    }
}
