package com.alipay.android.phone.mobilesdk.permission.guide;

import android.content.res.Resources;
import com.alipay.android.phone.mobilesdk.permission.utils.b;
import com.alipay.mobile.framework.LauncherApplicationAgent;

/* compiled from: ResUtil */
public class g {
    private static Resources a;

    private g() {
    }

    public static String a(int strId) {
        return a().getString(strId);
    }

    private static Resources a() {
        if (a == null) {
            synchronized (g.class) {
                if (a == null) {
                    a = LauncherApplicationAgent.getInstance().getBundleContext().getResourcesByBundle(b.a());
                }
            }
        }
        return a;
    }
}
