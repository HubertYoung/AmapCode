package com.amap.location.sdk.b;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import com.amap.location.g.a;

/* compiled from: ConfigInitializer */
public class c {
    public static void a(Context context) {
        if (VERSION.SDK_INT >= 28 && Build.MANUFACTURER.toUpperCase().trim().equals("HUAWEI")) {
            a.a = 12000;
        }
    }
}
