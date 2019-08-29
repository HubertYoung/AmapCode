package com.xiaomi.mipush.sdk;

import android.content.ComponentName;
import android.content.Context;
import android.text.TextUtils;
import com.alipay.android.phone.inside.offlinecode.plugin.service.GenBusCodeService;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.channel.commonutils.reflect.a;

public class k {
    private static int a = -1;

    public static ac a(Context context) {
        try {
            return (context.getPackageManager().getServiceInfo(new ComponentName("com.huawei.hwid", "com.huawei.hms.core.service.HMSCoreService"), 128) == null || !a()) ? ac.OTHER : ac.HUAWEI;
        } catch (Exception unused) {
            return ac.OTHER;
        }
    }

    private static boolean a() {
        try {
            String str = (String) a.a((String) "android.os.SystemProperties", (String) "get", "ro.build.hw_emui_api_level", "");
            return !TextUtils.isEmpty(str) && Integer.parseInt(str) >= 9;
        } catch (Exception e) {
            b.a((Throwable) e);
        }
    }

    public static boolean b(Context context) {
        Object a2 = a.a(a.a((String) "com.google.android.gms.common.GoogleApiAvailability", (String) "getInstance", new Object[0]), (String) "isGooglePlayServicesAvailable", context);
        Object a3 = a.a((String) "com.google.android.gms.common.ConnectionResult", (String) GenBusCodeService.CODE_SUCESS);
        if (a3 == null || !(a3 instanceof Integer)) {
            b.c("google service is not avaliable");
            a = 0;
            return false;
        }
        int intValue = Integer.class.cast(a3).intValue();
        if (a2 != null) {
            if (a2 instanceof Integer) {
                a = Integer.class.cast(a2).intValue() == intValue ? 1 : 0;
            } else {
                a = 0;
                b.c("google service is not avaliable");
            }
        }
        StringBuilder sb = new StringBuilder("is google service can be used");
        sb.append(a > 0);
        b.c(sb.toString());
        return a > 0;
    }

    public static boolean c(Context context) {
        boolean z = false;
        Object a2 = a.a((String) "com.xiaomi.assemble.control.COSPushManager", (String) "isSupportPush", context);
        if (a2 != null && (a2 instanceof Boolean)) {
            z = Boolean.class.cast(a2).booleanValue();
        }
        b.c("color os push  is avaliable ? :".concat(String.valueOf(z)));
        return z;
    }
}
