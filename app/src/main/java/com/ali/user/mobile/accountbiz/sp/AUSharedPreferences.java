package com.ali.user.mobile.accountbiz.sp;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

public class AUSharedPreferences {
    private Context a = null;
    private String b = "alipay_inside_lg_sp";
    private int c = 0;
    private Editor d = null;

    protected AUSharedPreferences(Context context, String str, int i) {
        if (context != null) {
            this.a = context.getApplicationContext();
        }
        this.b = str;
        this.c = i;
    }

    public final synchronized void a() {
        if (this.a != null && this.d == null) {
            this.d = this.a.getSharedPreferences(this.b, this.c).edit();
        }
    }

    public final boolean b() {
        if (this.d != null) {
            return this.d.commit();
        }
        return false;
    }

    public final void c() {
        if (this.d != null) {
            this.d.apply();
        }
    }

    public final boolean b(String str) {
        if (this.d == null || TextUtils.isEmpty(str)) {
            return false;
        }
        this.d.remove(str);
        return true;
    }

    public final String a(String str, String str2) {
        return this.a != null ? this.a.getSharedPreferences(this.b, this.c).getString(str, str2) : str2;
    }

    public final boolean b(String str, String str2) {
        if (this.d == null) {
            return false;
        }
        this.d.putString(str, str2);
        return true;
    }

    public final boolean a(String str) {
        String str2 = this.b;
        if (this.a != null) {
            return this.a.getSharedPreferences(str2, this.c).contains(str);
        }
        return false;
    }
}
