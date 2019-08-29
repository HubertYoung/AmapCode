package com.alipay.inside.android.phone.mrpc.core.utils;

import android.os.Looper;

public class ThreadUtil {
    public static boolean checkMainThread() {
        return Looper.myLooper() != null && Looper.myLooper() == Looper.getMainLooper();
    }
}
