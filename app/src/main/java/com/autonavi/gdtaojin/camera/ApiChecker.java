package com.autonavi.gdtaojin.camera;

import android.os.Build.VERSION;

public class ApiChecker {
    public static final boolean AT_LEAST_10 = (VERSION.SDK_INT >= 10);
    public static final boolean AT_LEAST_11 = (VERSION.SDK_INT >= 11);
    public static final boolean AT_LEAST_14 = (VERSION.SDK_INT >= 14);
    public static final boolean AT_LEAST_16 = (VERSION.SDK_INT >= 16);
    public static final boolean AT_LEAST_5 = (VERSION.SDK_INT >= 5);
    public static final boolean AT_LEAST_8 = (VERSION.SDK_INT >= 8);
    public static final boolean HAS_AUTO_FOCUS_MOVE_CALLBACK = (VERSION.SDK_INT >= 16);
    public static final boolean HAS_DISPLAY_LISTENER = (VERSION.SDK_INT >= 17);
    public static final boolean HAS_GET_CAMERA_NUMBER = (VERSION.SDK_INT < 9);
    public static final boolean HAS_HIDEYBARS;

    static {
        boolean z = false;
        if (VERSION.SDK_INT >= 19 || "KeyLimePie".equals(VERSION.CODENAME)) {
            z = true;
        }
        HAS_HIDEYBARS = z;
    }
}
