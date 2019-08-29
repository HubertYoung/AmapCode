package com.alipay.mobile.account.adapter;

import android.content.Context;
import com.alipay.android.phone.inside.framework.LauncherApplication;

public class CommonAdapter {
    private static CommonAdapter a;

    private CommonAdapter() {
    }

    public static CommonAdapter a() {
        if (a == null) {
            synchronized (CommonAdapter.class) {
                if (a == null) {
                    a = new CommonAdapter();
                }
            }
        }
        return a;
    }

    public static Context b() {
        return LauncherApplication.a();
    }
}
