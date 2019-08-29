package com.alipay.mobile.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;

public class CacheSet {
    public static final String FILE_NAME = "AppHall.cache";
    static final String TAG = "AlixSet";
    private static CacheSet c = null;
    private Context a;
    private SharedPreferences b;

    public static CacheSet getInstance(Context context) {
        if (c == null) {
            synchronized (CacheSet.class) {
                if (c == null) {
                    c = new CacheSet(context);
                }
            }
        }
        return c;
    }

    private CacheSet(Context context) {
        this.a = context.getApplicationContext();
        if (this.a == null) {
            this.a = context;
        }
        this.b = this.a.getSharedPreferences(FILE_NAME, 0);
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public String getString(String key) {
        return this.b == null ? "" : this.b.getString(key, "");
    }

    public void putString(String key, String value) {
        this.b.edit().putString(key, value).apply();
    }

    public boolean getBoolean(String key, boolean defValue) {
        return this.b == null ? defValue : this.b.getBoolean(key, defValue);
    }

    public void putBoolean(String key, boolean value) {
        this.b.edit().putBoolean(key, value).apply();
    }

    public long getLong(String key, long defValue) {
        return this.b == null ? defValue : this.b.getLong(key, defValue);
    }

    public void putLong(String key, long value) {
        this.b.edit().putLong(key, value).apply();
    }

    public float getFloat(String key, float defValue) {
        return this.b == null ? defValue : this.b.getFloat(key, defValue);
    }

    public void putFloat(String key, float value) {
        this.b.edit().putFloat(key, value).apply();
    }

    public int getInt(String key, int defValue) {
        return this.b == null ? defValue : this.b.getInt(key, defValue);
    }

    public void putInt(String key, int value) {
        this.b.edit().putInt(key, value).apply();
    }

    public void remove(String key) {
        this.b.edit().remove(key).apply();
    }
}
