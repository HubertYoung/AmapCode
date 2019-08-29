package com.alipay.mobile.nebulacore.data;

import android.content.SharedPreferences;
import android.text.TextUtils;
import com.alipay.mobile.h5container.api.H5Data;
import com.alipay.mobile.nebulacore.env.H5Environment;

public class H5PrefData implements H5Data {
    private SharedPreferences a;

    public H5PrefData(String name) {
        this.a = H5Environment.getContext().getSharedPreferences(!TextUtils.isEmpty(name) ? "h5_data_" + name : "h5_data_", 0);
    }

    public void set(String name, String value) {
        this.a.edit().putString(name, value).apply();
    }

    public String get(String name) {
        return this.a.getString(name, null);
    }

    public String remove(String name) {
        String value = this.a.getString(name, null);
        if (!TextUtils.isEmpty(value)) {
            this.a.edit().remove(name).apply();
        }
        return value;
    }

    public boolean has(String name) {
        return this.a.contains(name);
    }
}
