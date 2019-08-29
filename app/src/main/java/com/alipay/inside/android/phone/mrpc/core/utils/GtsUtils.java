package com.alipay.inside.android.phone.mrpc.core.utils;

import com.alipay.android.phone.inside.log.api.LoggerFactory;

public final class GtsUtils {
    private static long currentTimeMillis = -1;

    public static final long getCurrentTimeMillis() {
        long currentTimeMillis2;
        long currentTimeMillis3 = System.currentTimeMillis();
        if (currentTimeMillis3 != currentTimeMillis) {
            currentTimeMillis = currentTimeMillis3;
            return currentTimeMillis3;
        }
        synchronized (GtsUtils.class) {
            try {
                Thread.sleep(5);
            } catch (Exception e) {
                LoggerFactory.f().d("GtsUtils", e.toString());
            }
            currentTimeMillis2 = System.currentTimeMillis();
            currentTimeMillis = currentTimeMillis2;
        }
        return currentTimeMillis2;
    }

    public static final String get64HexCurrentTimeMillis() {
        return HexaDecimalConvUtil.c10to64(getCurrentTimeMillis());
    }
}
