package com.alipay.mobile.beehive.util;

import android.os.SystemClock;

public class TimeCostCounter {
    private static long startTs = 0;

    public static long start() {
        long uptimeMillis = SystemClock.uptimeMillis();
        startTs = uptimeMillis;
        return uptimeMillis;
    }

    public static long end() {
        return SystemClock.uptimeMillis() - startTs;
    }
}
