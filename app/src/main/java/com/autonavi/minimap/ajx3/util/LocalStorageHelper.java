package com.autonavi.minimap.ajx3.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import java.util.Map;

public class LocalStorageHelper {
    private static final String SP_DEFAULT_ITEM = "SP_DEFAULT_ITEM";
    private Editor mEditor = this.mSP.edit();
    private SharedPreferences mSP;
    private SharedPreferences mSPDefault;

    public LocalStorageHelper(@NonNull Context context, @NonNull String str) {
        this.mSP = context.getSharedPreferences(str, 0);
        this.mSPDefault = context.getSharedPreferences(SP_DEFAULT_ITEM.concat(String.valueOf(str)), 0);
    }

    public boolean setDefaultItems(String[] strArr, Object[] objArr) {
        if (strArr == null || objArr == null || strArr.length != objArr.length || strArr.length <= 0) {
            return false;
        }
        String[] strArr2 = new String[objArr.length];
        int i = 0;
        for (String str : strArr) {
            Object obj = objArr[i];
            if (TextUtils.isEmpty(str) || obj == null) {
                return false;
            }
            strArr2[i] = valueToString(obj);
            i++;
        }
        Editor edit = this.mSPDefault.edit();
        int i2 = 0;
        for (String putString : strArr) {
            edit.putString(putString, strArr2[i2]);
            i2++;
        }
        edit.apply();
        return true;
    }

    public boolean setItem(String str, Object obj) {
        if (TextUtils.isEmpty(str) || obj == null) {
            return false;
        }
        this.mEditor.putString(str, valueToString(obj)).apply();
        return true;
    }

    public boolean contains(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return this.mSP.contains(str);
    }

    public String getItem(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String string = this.mSP.getString(str, null);
        if (string == null) {
            string = this.mSPDefault.getString(str, null);
        }
        return string == null ? str2 : string;
    }

    public Map<String, ?> getAllItems() {
        try {
            return this.mSP.getAll();
        } catch (NullPointerException unused) {
            return null;
        }
    }

    public boolean removeItem(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        this.mEditor.remove(str).apply();
        return true;
    }

    public boolean clear() {
        this.mEditor.clear().apply();
        return true;
    }

    public static String valueToString(Object obj) {
        if (obj instanceof Double) {
            Double d = (Double) obj;
            if (d.doubleValue() % 1.0d == 0.0d) {
                return String.valueOf(d.longValue());
            }
        }
        return String.valueOf(obj);
    }
}
