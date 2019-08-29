package com.xiaomi.metoknlp.a;

import android.content.Context;
import android.telephony.TelephonyManager;

public class d {
    private static TelephonyManager a;
    private static Context b;

    public static String a() {
        if (a != null) {
            return a.getNetworkOperator();
        }
        return null;
    }

    public static void a(Context context) {
        b = context;
        a = (TelephonyManager) context.getSystemService("phone");
    }

    public static String b() {
        String str = null;
        try {
            if (!(b == null || b.getPackageManager().checkPermission("android.permission.READ_PHONE_STATE", b.getPackageName()) != 0 || a == null)) {
                str = a.getDeviceId();
            }
        } catch (Exception unused) {
        }
        return str != null ? str : "UNKNOWN";
    }
}
