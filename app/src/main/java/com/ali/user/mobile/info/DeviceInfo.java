package com.ali.user.mobile.info;

import com.ali.user.mobile.log.AliUserLog;

public final class DeviceInfo {
    private static DeviceInfo a;

    private DeviceInfo() {
    }

    public static void a() {
        AliUserLog.c("DeviceInfo", "fake init");
        com.alipay.android.phone.inside.common.info.DeviceInfo.b();
    }

    public static DeviceInfo b() {
        synchronized (DeviceInfo.class) {
            if (a == null) {
                a = new DeviceInfo();
            }
        }
        return a;
    }

    public static String c() {
        return com.alipay.android.phone.inside.common.info.DeviceInfo.a().p();
    }

    public static String d() {
        return com.alipay.android.phone.inside.common.info.DeviceInfo.a().q();
    }

    public static int e() {
        return com.alipay.android.phone.inside.common.info.DeviceInfo.a().l();
    }

    public static int f() {
        return com.alipay.android.phone.inside.common.info.DeviceInfo.a().m();
    }

    public static String g() {
        return com.alipay.android.phone.inside.common.info.DeviceInfo.a().d();
    }

    public static String h() {
        return com.alipay.android.phone.inside.common.info.DeviceInfo.a().f();
    }
}
