package com.alipay.mobile.nebula.util;

import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.alipay.mobile.nebula.wallet.H5WalletLog;

public class H5Log {
    public static final String CURRENT_DEVICE_SPEC = (Build.MANUFACTURER + "-" + Build.MODEL + "-" + Build.CPU_ABI + "-api" + VERSION.SDK_INT);
    public static final String TAG = "H5Log";

    public static void d(String log) {
        d(TAG, log);
    }

    public static void d(String tag, String log) {
        if (!TextUtils.isEmpty(log)) {
            H5WalletLog.d(tag, log);
        }
    }

    public static void w(String log) {
        w(TAG, log);
    }

    public static void w(String tag, String log) {
        if (!TextUtils.isEmpty(log)) {
            H5WalletLog.w(tag, log, null);
        }
    }

    public static void w(String tag, String log, Throwable t) {
        H5WalletLog.w(tag, log, t);
    }

    public static void e(String log) {
        e(TAG, log, null);
    }

    public static void e(String tag, String log) {
        e(tag, log, null);
    }

    public static void e(String log, Throwable t) {
        e(TAG, log, t);
    }

    public static void e(String tag, String log, Throwable t) {
        H5WalletLog.e(tag, log, t);
    }

    public static void debug(String tag, String log) {
        if (!TextUtils.isEmpty(log)) {
            H5WalletLog.debug(tag, log);
        }
    }
}
