package com.alipay.mobile.h5container.api;

public class H5PageLoader {
    public static String h5SessionToken;
    public static String h5Token;
    public static boolean isPageClose;
    public static String mainUrl;
    public static long sServiceStart;
    public static long start;

    public static void release() {
        sServiceStart = 0;
        start = 0;
        mainUrl = null;
        isPageClose = false;
    }
}
