package com.autonavi.minimap.offline.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.autonavi.minimap.offline.utils.OfflineUtil;
import java.util.HashMap;
import java.util.Set;

public class BaseOfflinePreference {
    private static final String NAME = "OfflinePreference";
    private static HashMap<String, BaseOfflinePreference> sPreferenceCache = new HashMap<>();
    private Editor mEditor;
    private SharedPreferences mSp;

    private BaseOfflinePreference(Context context, String str) {
        this.mSp = new MapSharePreference(str).sharedPrefs();
        this.mEditor = this.mSp.edit();
    }

    BaseOfflinePreference(String str) {
        this(OfflineUtil.getContext(), str);
    }

    BaseOfflinePreference() {
        this(OfflineUtil.getContext(), NAME);
    }

    static BaseOfflinePreference getInstance(String str) {
        BaseOfflinePreference baseOfflinePreference = sPreferenceCache.get(str);
        if (baseOfflinePreference == null) {
            synchronized (BaseOfflinePreference.class) {
                try {
                    baseOfflinePreference = sPreferenceCache.get(str);
                    if (baseOfflinePreference == null) {
                        baseOfflinePreference = new BaseOfflinePreference(str);
                        sPreferenceCache.put(str, baseOfflinePreference);
                    }
                }
            }
        }
        return baseOfflinePreference;
    }

    public BaseOfflinePreference removeValue(String str) {
        this.mEditor.remove(str);
        this.mEditor.apply();
        return this;
    }

    public int getIntValue(String str, int i) {
        return this.mSp.getInt(str, i);
    }

    public BaseOfflinePreference putIntValue(String str, int i) {
        this.mEditor.putInt(str, i);
        this.mEditor.apply();
        return this;
    }

    public boolean getBooleanValue(String str, boolean z) {
        return this.mSp.getBoolean(str, z);
    }

    public boolean contains(String str) {
        return this.mSp.contains(str);
    }

    public BaseOfflinePreference putBooleanValue(String str, boolean z) {
        this.mEditor.putBoolean(str, z);
        this.mEditor.apply();
        return this;
    }

    public float getFloatValue(String str, float f) {
        return this.mSp.getFloat(str, f);
    }

    public BaseOfflinePreference putFloatValue(String str, float f) {
        this.mEditor.putFloat(str, f);
        this.mEditor.apply();
        return this;
    }

    public long getLongValue(String str, long j) {
        return this.mSp.getLong(str, j);
    }

    public BaseOfflinePreference putLongValue(String str, long j) {
        this.mEditor.putLong(str, j);
        this.mEditor.apply();
        return this;
    }

    public String getStringValue(String str, String str2) {
        return this.mSp.getString(str, str2);
    }

    public BaseOfflinePreference putStringValue(String str, String str2) {
        this.mEditor.putString(str, str2);
        this.mEditor.apply();
        return this;
    }

    public Set<String> getStringSetValue(String str, Set<String> set) {
        return this.mSp.getStringSet(str, set);
    }

    public BaseOfflinePreference putStringSetValue(String str, Set<String> set) {
        this.mEditor.putStringSet(str, set);
        this.mEditor.apply();
        return this;
    }
}
