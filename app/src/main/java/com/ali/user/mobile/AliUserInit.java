package com.ali.user.mobile;

import android.content.Context;
import com.ali.user.mobile.dataprovider.AppDataProvider;
import com.ali.user.mobile.info.AppInfo;
import com.ali.user.mobile.info.DeviceInfo;
import com.alipay.android.phone.inside.framework.LauncherApplication;
import com.alipay.android.phone.inside.security.util.SsoLoginUtils;
import java.util.concurrent.atomic.AtomicBoolean;

public class AliUserInit {
    private static Context a;
    private static boolean b;
    private static String c;
    private static AtomicBoolean d = new AtomicBoolean(false);

    public static String a() {
        return c;
    }

    public static Context b() {
        synchronized (AliUserInit.class) {
            if (a == null) {
                a = LauncherApplication.a();
            }
        }
        return a;
    }

    public static void a(Context context) {
        a = context;
        if (!d.get()) {
            try {
                b = (a.getPackageManager().getApplicationInfo(a.getPackageName(), 16384).flags & 2) != 0;
            } catch (Exception unused) {
                b = false;
            }
            SsoLoginUtils.a(a);
            DeviceInfo.b();
            DeviceInfo.a();
            AppInfo.getInstance().init(a);
            d.set(true);
        }
    }

    public static void a(AppDataProvider appDataProvider) {
        AppInfo.getInstance().setDataProvider(appDataProvider);
    }

    public static boolean c() {
        return b;
    }
}
