package com.alipay.mobile.nebulacore.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.view.ViewConfiguration;
import com.alipay.mobile.common.share.widget.ResUtils;
import java.lang.reflect.Method;

public class NavigationBarUtil {
    private static String d;
    private final boolean a;
    private final int b;
    private final boolean c;

    static {
        if (VERSION.SDK_INT >= 19) {
            try {
                Method m = Class.forName("android.os.SystemProperties").getDeclaredMethod("get", new Class[]{String.class});
                m.setAccessible(true);
                d = (String) m.invoke(null, new Object[]{"qemu.hw.mainkeys"});
            } catch (Throwable th) {
                d = null;
            }
        }
    }

    public NavigationBarUtil(Activity activity) {
        boolean z;
        boolean z2 = true;
        if (activity.getResources().getConfiguration().orientation == 1) {
            z = true;
        } else {
            z = false;
        }
        this.c = z;
        this.b = a(activity);
        this.a = this.b <= 0 ? false : z2;
    }

    @TargetApi(14)
    private int a(Context context) {
        String key;
        Resources res = context.getResources();
        if (VERSION.SDK_INT < 14 || !b(context)) {
            return 0;
        }
        if (this.c) {
            key = "navigation_bar_height";
        } else {
            key = "navigation_bar_height_landscape";
        }
        return a(res, key);
    }

    @TargetApi(14)
    private static boolean b(Context context) {
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("config_showNavigationBar", "bool", "android");
        if (resourceId == 0) {
            return !ViewConfiguration.get(context).hasPermanentMenuKey();
        }
        boolean hasNav = res.getBoolean(resourceId);
        if ("1".equals(d)) {
            return false;
        }
        if ("0".equals(d)) {
            return true;
        }
        return hasNav;
    }

    private static int a(Resources res, String key) {
        int resourceId = res.getIdentifier(key, ResUtils.DIMEN, "android");
        if (resourceId > 0) {
            return res.getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    public boolean hasNavigationBar() {
        return this.a;
    }

    public int getNavigationBarHeight() {
        return this.b;
    }
}
