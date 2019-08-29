package com.amap.bundle.mapstorage;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.autonavi.amap.app.AMapAppGlobal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import proguard.annotation.KeepName;
import proguard.annotation.KeepPublicClassMemberNames;

@KeepPublicClassMemberNames
@KeepName
public class MapSharePreference {
    private static Map<String, ya> b = new ConcurrentHashMap();
    public ya a;

    public enum SharePreferenceName {
        SharedPreferences,
        user_route_method_info
    }

    public MapSharePreference(SharePreferenceName sharePreferenceName) {
        this(AMapAppGlobal.getApplication(), sharePreferenceName);
    }

    public MapSharePreference(Context context, SharePreferenceName sharePreferenceName) {
        a(this, context, sharePreferenceName.toString());
    }

    public MapSharePreference(String str) {
        a(this, AMapAppGlobal.getApplication(), str);
    }

    public int getIntValue(String str, int i) {
        return this.a.getInt(str, i);
    }

    public void putIntValue(String str, int i) {
        this.a.putInt(str, i);
        this.a.apply();
    }

    public boolean getBooleanValue(String str, boolean z) {
        return this.a.getBoolean(str, z);
    }

    public void putBooleanValue(String str, boolean z) {
        this.a.putBoolean(str, z);
        this.a.apply();
    }

    public boolean contains(String str) {
        return this.a.contains(str);
    }

    public float getFloatValue(String str, float f) {
        return this.a.getFloat(str, f);
    }

    public void putFloatValue(String str, float f) {
        this.a.putFloat(str, f);
        this.a.apply();
    }

    public long getLongValue(String str, long j) {
        return this.a.getLong(str, j);
    }

    public void putLongValue(String str, long j) {
        this.a.putLong(str, j);
        this.a.apply();
    }

    public String getStringValue(String str, String str2) {
        return this.a.getString(str, str2);
    }

    public void putStringValue(String str, String str2) {
        synchronized (this.a.c) {
            this.a.putString(str, str2);
            this.a.apply();
        }
    }

    public void remove(String str) {
        synchronized (this.a.c) {
            this.a.remove(str);
            this.a.apply();
        }
    }

    public Editor edit() {
        return this.a;
    }

    public SharedPreferences sharedPrefs() {
        return this.a;
    }

    private static void a(MapSharePreference mapSharePreference, Context context, String str) {
        ya yaVar = b.get(str);
        if (yaVar != null) {
            mapSharePreference.a = yaVar;
            return;
        }
        synchronized (MapSharePreference.class) {
            ya yaVar2 = b.get(str);
            if (yaVar2 == null) {
                yaVar2 = new ya(context, str);
                b.put(str, yaVar2);
            }
            mapSharePreference.a = yaVar2;
        }
    }
}
