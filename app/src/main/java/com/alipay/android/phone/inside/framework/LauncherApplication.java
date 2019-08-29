package com.alipay.android.phone.inside.framework;

import android.app.Application;
import android.content.Context;

public class LauncherApplication {
    private static Application a;

    public static Application a() {
        return a;
    }

    public static void a(Context context) {
        if (context != null) {
            a = (Application) context.getApplicationContext();
        }
    }
}
