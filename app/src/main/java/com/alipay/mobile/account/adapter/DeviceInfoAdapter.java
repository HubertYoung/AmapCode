package com.alipay.mobile.account.adapter;

import com.alipay.android.phone.inside.common.info.DeviceInfo;

public class DeviceInfoAdapter {
    private static DeviceInfoAdapter a;

    private DeviceInfoAdapter() {
    }

    public static DeviceInfoAdapter a() {
        if (a == null) {
            synchronized (DeviceInfoAdapter.class) {
                if (a == null) {
                    a = new DeviceInfoAdapter();
                }
            }
        }
        return a;
    }

    public static String b() {
        return DeviceInfo.a().j();
    }

    public static String c() {
        return DeviceInfo.a().s();
    }

    public static String d() {
        return DeviceInfo.a().u();
    }

    public static String e() {
        return DeviceInfo.a().p();
    }

    public static String f() {
        return DeviceInfo.a().q();
    }
}
