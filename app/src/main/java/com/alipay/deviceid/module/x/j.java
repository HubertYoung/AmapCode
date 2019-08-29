package com.alipay.deviceid.module.x;

import android.content.Context;

public final class j {
    private static j a = new j();

    private j() {
    }

    public static j a() {
        return a;
    }

    public static String a(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 16).versionName;
        } catch (Exception unused) {
            return "0.0.0";
        }
    }
}
