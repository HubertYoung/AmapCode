package com.huawei.android.pushagent.a.a;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class e {
    protected SharedPreferences a;

    public e(Context context, String str) {
        if (context == null) {
            throw new NullPointerException("context is null!");
        }
        this.a = context.getSharedPreferences(str, 4);
    }

    public void a(String str, boolean z) {
        if (this.a != null) {
            Editor edit = this.a.edit();
            if (edit != null) {
                edit.putBoolean(str, z).commit();
            }
        }
    }

    public boolean a(String str) {
        if (this.a != null) {
            return this.a.getBoolean(str, false);
        }
        return false;
    }

    public boolean a(String str, String str2) {
        if (this.a == null) {
            return false;
        }
        Editor edit = this.a.edit();
        if (edit != null) {
            return edit.putString(str, str2).commit();
        }
        return false;
    }

    public String b(String str) {
        return this.a != null ? this.a.getString(str, "") : "";
    }

    public boolean c(String str) {
        return this.a != null && this.a.contains(str);
    }

    public boolean d(String str) {
        if (this.a == null || !this.a.contains(str)) {
            return false;
        }
        Editor remove = this.a.edit().remove(str);
        remove.commit();
        return remove.commit();
    }
}
