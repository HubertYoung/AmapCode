package com.autonavi.ae.bl.search;

public class BLNativeSearchMapl {
    private static native synchronized int nativeInit();

    private static native synchronized int nativeUnInit();

    public static int init() {
        return nativeInit();
    }

    public static int uninit() {
        return nativeUnInit();
    }
}
