package com.xiaomi.channel.commonutils.android;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.channel.commonutils.reflect.a;
import java.util.ArrayList;

public class d {
    private static String a = null;
    private static String b = "";
    private static String c;
    private static String d;

    public static String a() {
        String str = null;
        if (VERSION.SDK_INT > 8 && VERSION.SDK_INT < 26) {
            return Build.SERIAL;
        }
        if (VERSION.SDK_INT >= 26) {
            str = (String) a.a((String) "android.os.Build", (String) "getSerial", (Object[]) null);
        }
        return str;
    }

    public static String a(Context context) {
        try {
            return Secure.getString(context.getContentResolver(), "android_id");
        } catch (Throwable th) {
            b.a(th);
            return null;
        }
    }

    public static String a(Context context, boolean z) {
        if (c == null) {
            String str = "";
            if (f.e()) {
                str = z ? b(context) : k(context);
            }
            String a2 = a(context);
            String a3 = a();
            StringBuilder sb = new StringBuilder("a-");
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str);
            sb2.append(a2);
            sb2.append(a3);
            sb.append(com.xiaomi.channel.commonutils.string.d.b(sb2.toString()));
            c = sb.toString();
        }
        return c;
    }

    @TargetApi(17)
    public static int b() {
        if (VERSION.SDK_INT < 17) {
            return -1;
        }
        Object a2 = a.a((String) "android.os.UserHandle", (String) "myUserId", new Object[0]);
        if (a2 == null) {
            return -1;
        }
        return Integer.class.cast(a2).intValue();
    }

    public static String b(Context context) {
        String c2 = c(context);
        int i = 10;
        while (c2 == null) {
            int i2 = i - 1;
            if (i <= 0) {
                break;
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException unused) {
            }
            c2 = c(context);
            i = i2;
        }
        return c2;
    }

    /* JADX WARNING: Removed duplicated region for block: B:25:0x0052 A[Catch:{ Throwable -> 0x007b }] */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0057 A[Catch:{ Throwable -> 0x007b }] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0078 A[Catch:{ Throwable -> 0x007b }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String c(android.content.Context r5) {
        /*
            boolean r0 = com.xiaomi.channel.commonutils.android.f.e()
            if (r0 != 0) goto L_0x0009
            java.lang.String r5 = ""
            return r5
        L_0x0009:
            java.lang.String r0 = a
            if (r0 == 0) goto L_0x0010
            java.lang.String r5 = a
            return r5
        L_0x0010:
            r0 = 0
            boolean r1 = com.xiaomi.channel.commonutils.android.f.a()     // Catch:{ Throwable -> 0x007b }
            if (r1 == 0) goto L_0x003b
            java.lang.String r1 = "miui.telephony.TelephonyManager"
            java.lang.String r2 = "getDefault"
            r3 = 0
            java.lang.Object[] r4 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x007b }
            java.lang.Object r1 = com.xiaomi.channel.commonutils.reflect.a.a(r1, r2, r4)     // Catch:{ Throwable -> 0x007b }
            if (r1 == 0) goto L_0x003b
            java.lang.String r2 = "getMiuiDeviceId"
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x007b }
            java.lang.Object r1 = com.xiaomi.channel.commonutils.reflect.a.a(r1, r2, r3)     // Catch:{ Throwable -> 0x007b }
            if (r1 == 0) goto L_0x003b
            boolean r2 = r1 instanceof java.lang.String     // Catch:{ Throwable -> 0x007b }
            if (r2 == 0) goto L_0x003b
            java.lang.Class<java.lang.String> r2 = java.lang.String.class
            java.lang.Object r1 = r2.cast(r1)     // Catch:{ Throwable -> 0x007b }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ Throwable -> 0x007b }
            goto L_0x003c
        L_0x003b:
            r1 = r0
        L_0x003c:
            if (r1 != 0) goto L_0x0076
            boolean r2 = l(r5)     // Catch:{ Throwable -> 0x007b }
            if (r2 == 0) goto L_0x0076
            java.lang.String r2 = "phone"
            java.lang.Object r5 = r5.getSystemService(r2)     // Catch:{ Throwable -> 0x007b }
            android.telephony.TelephonyManager r5 = (android.telephony.TelephonyManager) r5     // Catch:{ Throwable -> 0x007b }
            int r2 = android.os.Build.VERSION.SDK_INT     // Catch:{ Throwable -> 0x007b }
            r3 = 26
            if (r2 >= r3) goto L_0x0057
            java.lang.String r1 = r5.getDeviceId()     // Catch:{ Throwable -> 0x007b }
            goto L_0x0076
        L_0x0057:
            r2 = 1
            int r3 = r5.getPhoneType()     // Catch:{ Throwable -> 0x007b }
            if (r2 != r3) goto L_0x0068
            java.lang.String r1 = "getImei"
            java.lang.Object r5 = com.xiaomi.channel.commonutils.reflect.a.a(r5, r1, r0)     // Catch:{ Throwable -> 0x007b }
        L_0x0064:
            r1 = r5
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ Throwable -> 0x007b }
            goto L_0x0076
        L_0x0068:
            r2 = 2
            int r3 = r5.getPhoneType()     // Catch:{ Throwable -> 0x007b }
            if (r2 != r3) goto L_0x0076
            java.lang.String r1 = "getMeid"
            java.lang.Object r5 = com.xiaomi.channel.commonutils.reflect.a.a(r5, r1, r0)     // Catch:{ Throwable -> 0x007b }
            goto L_0x0064
        L_0x0076:
            if (r1 == 0) goto L_0x007a
            a = r1     // Catch:{ Throwable -> 0x007b }
        L_0x007a:
            return r1
        L_0x007b:
            r5 = move-exception
            com.xiaomi.channel.commonutils.logger.b.a(r5)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.channel.commonutils.android.d.c(android.content.Context):java.lang.String");
    }

    public static String d(Context context) {
        String f = f(context);
        int i = 10;
        while (f == null) {
            int i2 = i - 1;
            if (i <= 0) {
                break;
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException unused) {
            }
            f = f(context);
            i = i2;
        }
        return f;
    }

    public static String e(Context context) {
        Object a2;
        if (!f.e()) {
            return "";
        }
        if (VERSION.SDK_INT < 22) {
            return "";
        }
        if (!TextUtils.isEmpty(b)) {
            return b;
        }
        if (!l(context)) {
            return "";
        }
        String c2 = c(context);
        a = c2;
        if (TextUtils.isEmpty(c2)) {
            return "";
        }
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            Integer num = (Integer) a.a((Object) telephonyManager, (String) "getPhoneCount", new Object[0]);
            if (num == null || num.intValue() <= 1) {
                return "";
            }
            String str = null;
            for (int i = 0; i < num.intValue(); i++) {
                if (VERSION.SDK_INT < 26) {
                    a2 = a.a((Object) telephonyManager, (String) "getDeviceId", Integer.valueOf(i));
                } else if (1 == telephonyManager.getPhoneType()) {
                    a2 = a.a((Object) telephonyManager, (String) "getImei", Integer.valueOf(i));
                } else {
                    if (2 == telephonyManager.getPhoneType()) {
                        a2 = a.a((Object) telephonyManager, (String) "getMeid", Integer.valueOf(i));
                    }
                    if (!TextUtils.isEmpty(str) && !TextUtils.equals(a, str)) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(b);
                        sb.append(str);
                        sb.append(",");
                        b = sb.toString();
                    }
                }
                str = (String) a2;
                StringBuilder sb2 = new StringBuilder();
                sb2.append(b);
                sb2.append(str);
                sb2.append(",");
                b = sb2.toString();
            }
            String substring = b.substring(0, b.length() - 1);
            b = substring;
            return substring;
        } catch (Exception e) {
            b.d(e.toString());
            return "";
        }
    }

    public static String f(Context context) {
        e(context);
        if (TextUtils.isEmpty(b)) {
            return "";
        }
        String str = "";
        for (String a2 : b.split(",")) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(com.xiaomi.channel.commonutils.string.d.a(a2));
            sb.append(",");
            str = sb.toString();
        }
        int length = str.length();
        if (length > 0) {
            str = str.substring(0, length - 1);
        }
        return str;
    }

    public static ArrayList<String> g(Context context) {
        a = c(context);
        b = e(context);
        if (TextUtils.isEmpty(a)) {
            return null;
        }
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(a);
        if (TextUtils.isEmpty(b)) {
            return arrayList;
        }
        for (String add : b.split(",")) {
            arrayList.add(add);
        }
        return arrayList;
    }

    public static synchronized String h(Context context) {
        synchronized (d.class) {
            if (d != null) {
                String str = d;
                return str;
            }
            String a2 = a(context);
            String a3 = a();
            StringBuilder sb = new StringBuilder();
            sb.append(a2);
            sb.append(a3);
            String b2 = com.xiaomi.channel.commonutils.string.d.b(sb.toString());
            d = b2;
            return b2;
        }
    }

    public static String i(Context context) {
        return ((TelephonyManager) context.getSystemService("phone")).getSimOperatorName();
    }

    public static String j(Context context) {
        if (!f.e()) {
            return "";
        }
        try {
            return ((WifiManager) context.getSystemService("wifi")).getConnectionInfo().getMacAddress();
        } catch (Exception e) {
            b.a((Throwable) e);
            return null;
        }
    }

    private static String k(Context context) {
        String c2 = c(context);
        int i = 10;
        while (TextUtils.isEmpty(c2)) {
            int i2 = i - 1;
            if (i <= 0) {
                break;
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException unused) {
            }
            c2 = c(context);
            i = i2;
        }
        return c2;
    }

    private static boolean l(Context context) {
        return context.getPackageManager().checkPermission("android.permission.READ_PHONE_STATE", context.getPackageName()) == 0;
    }
}
