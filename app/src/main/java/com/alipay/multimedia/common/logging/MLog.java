package com.alipay.multimedia.common.logging;

import com.alipay.multimedia.adapter.AdapterFactory.L;
import com.alipay.multimedia.common.adapter.AdapterInitial;

public class MLog {
    public static final L sL = AdapterInitial.getAdapterFactory().Log();

    public static void v(String tag, String msg) {
        sL.v(tag, msg);
    }

    public static void d(String tag, String msg) {
        sL.d(tag, msg);
    }

    public static void i(String tag, String msg) {
        sL.i(tag, msg);
    }

    public static void w(String tag, String msg) {
        sL.w(tag, msg);
    }

    public static void e(String tag, String msg) {
        sL.e(tag, msg);
    }

    public static void e(String tag, String msg, Throwable t) {
        sL.e(tag, msg, t);
    }
}
