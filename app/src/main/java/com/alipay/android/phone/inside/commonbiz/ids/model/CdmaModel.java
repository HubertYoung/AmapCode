package com.alipay.android.phone.inside.commonbiz.ids.model;

import android.text.TextUtils;

public class CdmaModel {
    private String a;
    private String b;
    private String c;
    private String d;

    public CdmaModel(String str, String str2, String str3, String str4) {
        this.a = str;
        this.b = str2;
        this.c = str3;
        this.d = str4;
    }

    public final String a() {
        return this.a;
    }

    public final String b() {
        return this.b;
    }

    public final String c() {
        return this.c;
    }

    public final String d() {
        return this.d;
    }

    public final boolean e() {
        return !TextUtils.isEmpty(this.a) || !TextUtils.isEmpty(this.b) || !TextUtils.isEmpty(this.c) || !TextUtils.isEmpty(this.d);
    }
}
