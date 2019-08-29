package com.sina.weibo.sdk.statistic;

import android.content.Context;

class StatisticConfig {
    public static boolean ACTIVITY_DURATION_OPEN = true;
    private static final long DEFAULT_UPLOAD_INTERVAL = 90000;
    private static final long MAX_UPLOAD_INTERVAL = 28800000;
    public static final long MIN_UPLOAD_INTERVAL = 30000;
    public static long kContinueSessionMillis = 30000;
    private static long kForceUploadInterval = 30000;
    private static long kUploadInterval = 90000;
    private static String mAppkey = null;
    private static String mChannel = null;
    private static boolean mNeedGizp = true;

    StatisticConfig() {
    }

    public static void setAppkey(String str) {
        mAppkey = str;
    }

    public static void setChannel(String str) {
        mChannel = str;
    }

    public static String getAppkey(Context context) {
        if (mAppkey == null) {
            mAppkey = LogBuilder.getAppKey(context);
        }
        return mAppkey;
    }

    public static String getChannel(Context context) {
        if (mChannel == null) {
            mChannel = LogBuilder.getChannel(context);
        }
        return mChannel;
    }

    public static long getUploadInterval() {
        return kUploadInterval;
    }

    public static void setUploadInterval(long j) throws Exception {
        if (j < MIN_UPLOAD_INTERVAL || j > MAX_UPLOAD_INTERVAL) {
            throw new Exception("The interval must be between 30 seconds and 8 hours");
        }
        kUploadInterval = j;
    }

    public static boolean isNeedGizp() {
        return mNeedGizp;
    }

    public static void setNeedGizp(boolean z) {
        mNeedGizp = z;
    }

    public static long getForceUploadInterval() {
        return kForceUploadInterval;
    }

    public static void setForceUploadInterval(long j) {
        kForceUploadInterval = j;
    }
}
