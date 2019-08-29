package com.alipay.deviceid.module.x;

import android.os.Build;
import android.os.Build.VERSION;
import java.io.File;

public final class m {
    private static m a = new m();

    private m() {
    }

    public static m a() {
        return a;
    }

    private static String a(String str, String str2) {
        try {
            return (String) Class.forName("android.os.SystemProperties").getMethod("get", new Class[]{String.class, String.class}).invoke(null, new Object[]{str, str2});
        } catch (Exception unused) {
            return str2;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x0056 A[Catch:{ Exception -> 0x0067 }, RETURN] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean a(android.content.Context r7) {
        /*
            r0 = 0
            java.lang.String r1 = android.os.Build.HARDWARE     // Catch:{ Exception -> 0x0067 }
            java.lang.String r2 = "goldfish"
            boolean r1 = r1.contains(r2)     // Catch:{ Exception -> 0x0067 }
            r2 = 1
            if (r1 != 0) goto L_0x0066
            java.lang.String r1 = android.os.Build.PRODUCT     // Catch:{ Exception -> 0x0067 }
            java.lang.String r3 = "sdk"
            boolean r1 = r1.contains(r3)     // Catch:{ Exception -> 0x0067 }
            if (r1 != 0) goto L_0x0066
            java.lang.String r1 = android.os.Build.FINGERPRINT     // Catch:{ Exception -> 0x0067 }
            java.lang.String r3 = "generic"
            boolean r1 = r1.contains(r3)     // Catch:{ Exception -> 0x0067 }
            if (r1 == 0) goto L_0x0022
            return r2
        L_0x0022:
            java.lang.String r1 = "phone"
            java.lang.Object r1 = r7.getSystemService(r1)     // Catch:{ Exception -> 0x0067 }
            android.telephony.TelephonyManager r1 = (android.telephony.TelephonyManager) r1     // Catch:{ Exception -> 0x0067 }
            if (r1 == 0) goto L_0x0057
            java.lang.String r1 = r1.getDeviceId()     // Catch:{ Exception -> 0x0067 }
            if (r1 == 0) goto L_0x0053
            int r3 = r1.length()     // Catch:{ Exception -> 0x0067 }
            if (r3 != 0) goto L_0x0039
            goto L_0x0053
        L_0x0039:
            r4 = 0
        L_0x003a:
            if (r4 >= r3) goto L_0x0053
            char r5 = r1.charAt(r4)     // Catch:{ Exception -> 0x0067 }
            boolean r5 = java.lang.Character.isWhitespace(r5)     // Catch:{ Exception -> 0x0067 }
            if (r5 != 0) goto L_0x0050
            char r5 = r1.charAt(r4)     // Catch:{ Exception -> 0x0067 }
            r6 = 48
            if (r5 == r6) goto L_0x0050
            r1 = 0
            goto L_0x0054
        L_0x0050:
            int r4 = r4 + 1
            goto L_0x003a
        L_0x0053:
            r1 = 1
        L_0x0054:
            if (r1 == 0) goto L_0x0057
            return r2
        L_0x0057:
            android.content.ContentResolver r7 = r7.getContentResolver()     // Catch:{ Exception -> 0x0067 }
            java.lang.String r1 = "android_id"
            java.lang.String r7 = android.provider.Settings.Secure.getString(r7, r1)     // Catch:{ Exception -> 0x0067 }
            boolean r7 = com.alipay.deviceid.module.x.e.a(r7)     // Catch:{ Exception -> 0x0067 }
            return r7
        L_0x0066:
            return r2
        L_0x0067:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.deviceid.module.x.m.a(android.content.Context):boolean");
    }

    public static String b() {
        return "android";
    }

    public static boolean c() {
        String[] strArr = {"/system/bin/", "/system/xbin/", "/system/sbin/", "/sbin/", "/vendor/bin/"};
        int i = 0;
        while (i < 5) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(strArr[i]);
                sb.append("su");
                if (new File(sb.toString()).exists()) {
                    return true;
                }
                i++;
            } catch (Exception unused) {
            }
        }
        return false;
    }

    public static String d() {
        return Build.BOARD;
    }

    public static String e() {
        return Build.BRAND;
    }

    public static String f() {
        return Build.DEVICE;
    }

    public static String g() {
        return Build.DISPLAY;
    }

    public static String h() {
        return VERSION.INCREMENTAL;
    }

    public static String i() {
        return Build.MANUFACTURER;
    }

    public static String j() {
        return Build.MODEL;
    }

    public static String k() {
        return Build.PRODUCT;
    }

    public static String l() {
        return VERSION.RELEASE;
    }

    public static String m() {
        return VERSION.SDK;
    }

    public static String n() {
        return Build.TAGS;
    }

    public static String o() {
        return a("ro.kernel.qemu", "0");
    }
}
