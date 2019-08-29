package com.autonavi.minimap.ajx3.modules;

import android.content.SharedPreferences.Editor;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.autonavi.minimap.ajx3.util.LocalStorageHelper;
import java.util.Map;

public class AmapLocalStorageHelper {
    private static final String SP_DEFAULT_ITEM = "SP_DEFAULT_ITEM_ENCRYPTED";
    private MapSharePreference mSP;
    private MapSharePreference mSPDefault;

    public AmapLocalStorageHelper(@NonNull String str) {
        this.mSP = new MapSharePreference(str);
        this.mSPDefault = new MapSharePreference(SP_DEFAULT_ITEM.concat(String.valueOf(str)));
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
            strArr2[i] = LocalStorageHelper.valueToString(obj);
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
        this.mSP.putStringValue(str, LocalStorageHelper.valueToString(obj));
        return true;
    }

    public boolean contains(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return this.mSP.contains(str);
    }

    public String getItem(String str, String str2) {
        String str3;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            str3 = this.mSP.getStringValue(str, null);
        } catch (Exception unused) {
            str3 = null;
        }
        if (str3 == null) {
            str3 = this.mSPDefault.getStringValue(str, null);
        }
        return str3 == null ? str2 : str3;
    }

    public Map<String, ?> getAllItems() {
        try {
            return this.mSP.sharedPrefs().getAll();
        } catch (NullPointerException unused) {
            return null;
        }
    }

    public boolean removeItem(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        this.mSP.remove(str);
        return true;
    }

    public boolean clear() {
        this.mSP.edit().clear().apply();
        return true;
    }
}
