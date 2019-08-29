package com.autonavi.ae.search;

import com.autonavi.ae.bl.search.BLNativeSearchMapl;

public class NativeSearchMapl {
    public static int nativeInit() {
        return BLNativeSearchMapl.init();
    }

    public static int nativeUnInit() {
        return BLNativeSearchMapl.uninit();
    }
}
