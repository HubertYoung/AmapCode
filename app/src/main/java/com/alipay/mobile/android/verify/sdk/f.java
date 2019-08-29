package com.alipay.mobile.android.verify.sdk;

import android.os.Handler;
import android.os.Looper;

/* compiled from: Utils */
public class f {
    public static void a(Runnable runnable) {
        new Handler(Looper.getMainLooper()).post(runnable);
    }
}
