package com.alipay.mobile.base.config.impl;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import java.util.List;
import java.util.Map;

public abstract class SPAdapter {
    public static final String TAG = "SPAdapter";
    private SharedPreferences mNewSp;
    private SharedPreferences mOldSp;

    public abstract String beforePutInNewSp(String str, String str2);

    public abstract String getStringInNewSp(String str, String str2, String str3);

    public abstract String getStringInOldSp(String str, String str2, String str3);

    public abstract void onClear(SharedPreferences sharedPreferences, SharedPreferences sharedPreferences2);

    public SPAdapter(SharedPreferences oldSp, SharedPreferences newSp) {
        this.mOldSp = oldSp;
        this.mNewSp = newSp;
    }

    public String getString(String key, String def) {
        if (this.mNewSp != null && this.mNewSp.contains(key)) {
            String value = this.mNewSp.getString(key, def);
            if (TextUtils.isEmpty(value) || value.equals(def)) {
                return def;
            }
            return getStringInNewSp(key, value, def);
        } else if (this.mOldSp == null || !this.mOldSp.contains(key)) {
            return def;
        } else {
            return getStringInOldSp(key, this.mOldSp.getString(key, def), def);
        }
    }

    public void putMap(Map<String, String> maps) {
        if (maps == null || maps.isEmpty()) {
            LoggerFactory.getTraceLogger().info(TAG, "putMap maps is null");
            return;
        }
        Editor edit = this.mNewSp.edit();
        StringBuffer sb = new StringBuffer();
        sb.append("putMap_");
        for (String key : maps.keySet()) {
            String value = maps.get(key);
            sb.append(key + "=" + value + "#r#");
            if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
                edit.putString(key, beforePutInNewSp(key, value));
            }
        }
        edit.apply();
        LoggerFactory.getTraceLogger().info(TAG, sb.toString());
    }

    public void putString(String key, String value) {
        if (this.mNewSp != null) {
            Editor edit = this.mNewSp.edit();
            edit.putString(key, beforePutInNewSp(key, value));
            edit.apply();
        }
        if (this.mOldSp != null && this.mOldSp.contains(key)) {
            Editor edit2 = this.mOldSp.edit();
            edit2.remove(key);
            edit2.apply();
        }
    }

    public boolean contains(String key) {
        return this.mNewSp.contains(key);
    }

    public void clear() {
        if (this.mOldSp != null) {
            Editor edit = this.mOldSp.edit();
            edit.clear();
            edit.apply();
        }
        if (this.mNewSp != null) {
            Editor edit2 = this.mNewSp.edit();
            edit2.clear();
            edit2.apply();
        }
        onClear(this.mOldSp, this.mNewSp);
    }

    public void removeKeys(List<String> deleteKeys) {
        StringBuffer sbOld = new StringBuffer();
        if (this.mOldSp != null) {
            sbOld.append("remove mOldSp=");
            Editor edit = this.mOldSp.edit();
            for (String deleteKey : deleteKeys) {
                edit.remove(deleteKey);
                sbOld.append(deleteKey).append(".");
            }
            edit.apply();
            LoggerFactory.getTraceLogger().info(TAG, sbOld.toString());
        }
        StringBuffer sbNew = new StringBuffer();
        if (this.mNewSp != null) {
            sbNew.append("remove mNewSp=");
            Editor edit2 = this.mNewSp.edit();
            for (String deleteKey2 : deleteKeys) {
                edit2.remove(deleteKey2);
                sbNew.append(deleteKey2).append(".");
            }
            edit2.apply();
            LoggerFactory.getTraceLogger().info(TAG, sbNew.toString());
        }
    }

    public void removeKey(String key) {
        removeKeyInSp(this.mOldSp, key);
        removeKeyInSp(this.mNewSp, key);
    }

    private void removeKeyInSp(SharedPreferences sharedPreferences, String key) {
        if (sharedPreferences != null) {
            Editor edit = sharedPreferences.edit();
            edit.remove(key);
            edit.apply();
        }
    }
}
