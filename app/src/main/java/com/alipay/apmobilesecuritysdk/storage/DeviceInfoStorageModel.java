package com.alipay.apmobilesecuritysdk.storage;

import com.alipay.security.mobile.module.commonutils.CommonUtils;

public class DeviceInfoStorageModel {
    private String a = "";
    private String b = "";
    private String c = "";
    private String d = "";
    private String e = "";

    public DeviceInfoStorageModel() {
    }

    public DeviceInfoStorageModel(String str, String str2, String str3, String str4, String str5) {
        this.a = str;
        this.b = str2;
        this.c = str3;
        this.d = str4;
        this.e = str5;
    }

    public final String a() {
        return CommonUtils.getNonNullString(this.a);
    }

    public final void a(String str) {
        this.a = str;
    }

    public final String b() {
        return CommonUtils.getNonNullString(this.b);
    }

    public final void b(String str) {
        this.b = str;
    }

    public final String c() {
        return CommonUtils.getNonNullString(this.c);
    }

    public final void c(String str) {
        this.c = str;
    }

    public final String d() {
        return CommonUtils.getNonNullString(this.d);
    }

    public final void d(String str) {
        this.d = str;
    }

    public final String e() {
        return CommonUtils.getNonNullString(this.e);
    }

    public final void e(String str) {
        this.e = str;
    }
}
