package com.alipay.mobile.nebulauc.impl;

import android.util.Log;
import com.alipay.mobile.nebula.util.H5Log;
import com.uc.webview.export.extension.UCCore;
import com.uc.webview.export.internal.interfaces.INetLogger;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class UcDebugLogger implements INetLogger {
    private static final int LEVEL_DEBUG = 2;
    private static final int LEVEL_ERROR = 0;
    private static final int LEVEL_INFO = 3;
    private static final int LEVEL_NONE = 10;
    private static final int LEVEL_WARNING = 1;
    private static final String TAG = "UcDebugLogger";
    private boolean mEnable = false;
    private int mLogLevel = 10;

    public static void init() {
        try {
            UcDebugLogger logger = new UcDebugLogger();
            logger.setLogLevel(0);
            logger.setEnable(true);
            UCCore.setNetLogger(logger);
            enableH5TraceLog();
            Method method = Class.forName("com.alipay.ipcperf.IpcPerfManager").getDeclaredMethod("hackConfigServiceLite", new Class[0]);
            method.setAccessible(true);
            method.invoke(null, new Object[0]);
        } catch (Throwable e) {
            Log.e(TAG, "init ucdebug logger error!", e);
        }
    }

    private static void enableH5TraceLog() {
        try {
            Field sEnabledField = Class.forName("com.alipay.mobile.nebula.data.H5Trace").getDeclaredField("sEnabled");
            sEnabledField.setAccessible(true);
            sEnabledField.setBoolean(null, true);
        } catch (Exception e) {
            Log.e(TAG, "enableH5TraceLog error!", e);
        }
    }

    public void setLogLevel(int level) {
        this.mLogLevel = level;
    }

    public int getLogLevel() {
        return this.mLogLevel;
    }

    public void setEnable(boolean enable) {
        this.mEnable = enable;
    }

    public boolean getEnable() {
        return this.mEnable;
    }

    public void e(String TAG2, String msg) {
        if (this.mLogLevel <= 0) {
            H5Log.e(TAG2, msg);
        }
    }

    public void w(String TAG2, String msg) {
        if (this.mLogLevel <= 1) {
            H5Log.w(TAG2, msg);
        }
    }

    public void d(String TAG2, String msg) {
        if (this.mLogLevel <= 2) {
            H5Log.d(TAG2, msg);
        }
    }

    public void i(String TAG2, String msg) {
        if (this.mLogLevel <= 3) {
            H5Log.d(TAG2, msg);
        }
    }
}
