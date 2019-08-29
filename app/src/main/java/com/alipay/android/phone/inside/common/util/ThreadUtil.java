package com.alipay.android.phone.inside.common.util;

import android.os.Looper;

public class ThreadUtil {
    public static boolean a() {
        return Looper.myLooper() != null && Looper.myLooper() == Looper.getMainLooper();
    }
}
