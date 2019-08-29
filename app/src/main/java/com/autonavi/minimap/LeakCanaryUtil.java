package com.autonavi.minimap;

import android.app.Application;

public class LeakCanaryUtil {
    public static final String SP_KEY_leakcanary_switch = "leakcanary_switch";
    private static a mInterface;

    public interface a {
    }

    public static void install(Application application) {
    }

    public static void onDestroy(Object obj) {
    }

    public static void setEnabled(Application application, boolean z) {
    }

    public static void setImpl(a aVar) {
        if (aVar == null) {
            throw new NullPointerException("ILeakCanaryUtil can not be null");
        }
        mInterface = aVar;
    }
}
