package com.alipay.android.phone.inside.commonbiz.ids.model;

import android.text.TextUtils;
import java.util.List;

public class TelephoneInfo {
    private String a;
    private String b;
    private List<GsmModel> c;
    private CdmaModel d;
    private String e;

    public TelephoneInfo() {
    }

    public TelephoneInfo(String str, String str2, List<GsmModel> list, CdmaModel cdmaModel, String str3) {
        this.a = str;
        this.b = str2;
        if (list != null && !list.isEmpty()) {
            this.c = list;
        }
        if (cdmaModel != null && !cdmaModel.e()) {
            this.d = cdmaModel;
        }
        this.e = str3;
    }

    public final List<GsmModel> a() {
        return this.c;
    }

    public final CdmaModel b() {
        return this.d;
    }

    public final String c() {
        return this.e;
    }

    public final boolean d() {
        return !TextUtils.isEmpty(this.e) || !TextUtils.isEmpty(this.a) || !TextUtils.isEmpty(this.b) || this.c != null || this.d != null;
    }
}
