package com.alipay.mobile.account.adapter;

import com.alipay.android.phone.inside.common.info.AppInfo;
import com.alipay.android.phone.inside.common.util.DebugUtil;

public class AppInfoAdapter {
    private static AppInfoAdapter a;

    private AppInfoAdapter() {
    }

    public static AppInfoAdapter a() {
        if (a == null) {
            synchronized (AppInfoAdapter.class) {
                if (a == null) {
                    a = new AppInfoAdapter();
                }
            }
        }
        return a;
    }

    public static String b() {
        return AppInfo.a().g();
    }

    public static String c() {
        return AppInfo.a().e();
    }

    public static boolean d() {
        return DebugUtil.a();
    }
}
