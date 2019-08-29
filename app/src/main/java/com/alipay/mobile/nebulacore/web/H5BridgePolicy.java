package com.alipay.mobile.nebulacore.web;

import android.os.Build;
import android.os.Build.VERSION;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebulacore.Nebula;

public class H5BridgePolicy {
    public static final String TAG = "H5BridgePolicy";
    private static int a = 0;

    public static int get() {
        return a;
    }

    public static void negotiate() {
        if ("HuaweiMediaPad".equals(Build.ID) && "4.4.2".equals(VERSION.RELEASE) && "hw7d501l".equals(Build.DEVICE) && "C208B011".equals(VERSION.INCREMENTAL)) {
            a = 1;
            if (Nebula.DEBUG) {
                H5Log.d(TAG, "HUAWEI MediaPad C208B011 matched!");
            }
        }
    }
}
