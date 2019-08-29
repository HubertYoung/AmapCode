package com.alipay.ma.common.a;

import com.alipay.mobile.bqcscanservice.Logger;

/* compiled from: MaLogger */
public final class a {
    public static void a(String tag, String msg) {
        Logger.d(tag, msg);
    }

    public static void b(String tag, String msg) {
        Logger.w(tag, msg);
    }

    public static void c(String tag, String msg) {
        Logger.e(tag, msg);
    }
}
