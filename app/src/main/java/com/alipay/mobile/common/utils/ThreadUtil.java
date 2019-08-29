package com.alipay.mobile.common.utils;

import android.os.Looper;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;

public class ThreadUtil {
    public ThreadUtil() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public static boolean checkMainThread() {
        return Looper.myLooper() != null && Looper.myLooper() == Looper.getMainLooper();
    }
}
