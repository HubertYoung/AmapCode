package com.amap.bundle.cloudconfig.appinit.request;

import android.content.SharedPreferences.Editor;
import android.os.Build.VERSION;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.Callback;

public abstract class BaseAppInitAndSwitchCallback implements Callback<String> {
    public static final boolean mLog = false;

    public static void Log(String str) {
    }

    public static void Log(String str, Throwable th) {
    }

    public static void LogFormat(String str, Throwable th, Object... objArr) {
    }

    public static void LogFormat(String str, Object... objArr) {
    }

    static void a() {
        try {
            Editor edit = AMapAppGlobal.getApplication().getSharedPreferences("appinit", 0).edit();
            edit.putLong("appinit", System.currentTimeMillis());
            if (VERSION.SDK_INT >= 9) {
                edit.apply();
            } else {
                edit.commit();
            }
        } catch (Exception e) {
            kf.a((Throwable) e);
        }
    }
}
