package com.autonavi.minimap.acanvas;

import com.alipay.mobile.beehive.rpc.action.ActionConstant;

public class ACanvasLog {
    public static final int LEVEL_ASSERT = 5;
    public static final int LEVEL_DEBUG = 1;
    public static final int LEVEL_ERROR = 4;
    public static final int LEVEL_INFO = 2;
    public static final int LEVEL_WARN = 3;
    public static final String TAG = "ACanvas.java";
    static int sLogLevel = 1;

    static void d(String str, String str2) {
    }

    static void d(String str, String str2, Throwable th) {
    }

    static void e(String str, String str2) {
    }

    static void e(String str, String str2, Throwable th) {
    }

    static void i(String str, String str2) {
    }

    static void i(String str, String str2, Throwable th) {
    }

    static void w(String str, String str2) {
    }

    static void w(String str, String str2, Throwable th) {
    }

    public static void setLevel(String str) {
        if (str != null) {
            char c = 65535;
            switch (str.hashCode()) {
                case 3237038:
                    if (str.equals("info")) {
                        c = 1;
                        break;
                    }
                    break;
                case 3641990:
                    if (str.equals(ActionConstant.EXCEPTION_VIEW_TYPE_WARN)) {
                        c = 2;
                        break;
                    }
                    break;
                case 95458899:
                    if (str.equals("debug")) {
                        c = 0;
                        break;
                    }
                    break;
                case 96784904:
                    if (str.equals("error")) {
                        c = 3;
                        break;
                    }
                    break;
                case 97203460:
                    if (str.equals("fatal")) {
                        c = 4;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    sLogLevel = 1;
                    break;
                case 1:
                    sLogLevel = 2;
                    break;
                case 2:
                    sLogLevel = 3;
                    break;
                case 3:
                    sLogLevel = 4;
                    break;
                case 4:
                    sLogLevel = 5;
                    break;
            }
            ACanvasJNI.setLogLevel(sLogLevel);
        }
    }

    static int getLevel() {
        return sLogLevel;
    }

    static void i(String str) {
        i(TAG, str);
    }

    static void d(String str) {
        d(TAG, str);
    }

    static void e(String str) {
        e(TAG, str);
    }

    static void w(String str) {
        w(TAG, str);
    }
}
