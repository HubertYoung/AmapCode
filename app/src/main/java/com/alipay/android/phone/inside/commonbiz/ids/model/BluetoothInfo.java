package com.alipay.android.phone.inside.commonbiz.ids.model;

import android.text.TextUtils;

public class BluetoothInfo {
    private String a;
    private String b;

    public BluetoothInfo() {
    }

    public BluetoothInfo(String str, String str2) {
        this.a = str;
        this.b = str2;
    }

    public final String a() {
        return this.b;
    }

    public final String b() {
        return this.a;
    }

    public final boolean c() {
        return !TextUtils.isEmpty(this.b) || !TextUtils.isEmpty(this.a);
    }
}
