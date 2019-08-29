package com.alipay.mobile.common.utils;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;

public final class GtsUtils {
    private static long a = -1;

    public GtsUtils() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public static final long getCurrentTimeMillis() {
        long ltm = System.currentTimeMillis();
        if (ltm != a) {
            a = ltm;
        } else {
            synchronized (GtsUtils.class) {
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    LogCatUtil.printError("GtsUtils", e);
                }
                ltm = System.currentTimeMillis();
                a = ltm;
            }
        }
        return ltm;
    }

    public static final String get64HexCurrentTimeMillis() {
        return HexaDecimalConvUtil.c10to64(getCurrentTimeMillis());
    }
}
