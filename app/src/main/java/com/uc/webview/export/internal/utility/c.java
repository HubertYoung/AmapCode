package com.uc.webview.export.internal.utility;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.os.Looper;

@SuppressLint({"DefaultLocale"})
/* compiled from: ProGuard */
public class c {
    /* access modifiers changed from: private */
    public static volatile NetworkInfo a;
    private static volatile BroadcastReceiver b;

    /* compiled from: ProGuard */
    public static class a {
        public static Context a;
    }

    public static boolean a() {
        String str;
        if (b == null) {
            synchronized (c.class) {
                if (b == null) {
                    a = b();
                    b = new d();
                    a.a.registerReceiver(b, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
                }
            }
        }
        if (Looper.myLooper() != Looper.getMainLooper()) {
            a = b();
        }
        NetworkInfo networkInfo = a;
        if (networkInfo != null) {
            switch (networkInfo.getSubtype()) {
                case 1:
                    str = "2.5G";
                    break;
                case 2:
                case 7:
                    str = "2.75G";
                    break;
                case 3:
                case 5:
                case 6:
                case 8:
                case 9:
                case 10:
                case 12:
                case 14:
                case 15:
                    str = "3G";
                    break;
                case 4:
                case 11:
                    str = "2G";
                    break;
                case 13:
                    str = "4G";
                    break;
                default:
                    str = "0";
                    break;
            }
        } else {
            str = "-1";
        }
        return "2G".equals(str) || "2.5G".equals(str) || "2.75G".equals(str);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x001e, code lost:
        if (r2.isConnected() == false) goto L_0x0020;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.net.NetworkInfo b() {
        /*
            r0 = 0
            java.lang.String r1 = "connectivity"
            android.content.Context r2 = com.uc.webview.export.internal.utility.c.a.a     // Catch:{ Exception -> 0x0038 }
            if (r2 != 0) goto L_0x0009
            r1 = r0
            goto L_0x000f
        L_0x0009:
            android.content.Context r2 = com.uc.webview.export.internal.utility.c.a.a     // Catch:{ Exception -> 0x0038 }
            java.lang.Object r1 = r2.getSystemService(r1)     // Catch:{ Exception -> 0x0038 }
        L_0x000f:
            android.net.ConnectivityManager r1 = (android.net.ConnectivityManager) r1     // Catch:{ Exception -> 0x0038 }
            if (r1 != 0) goto L_0x0014
            return r0
        L_0x0014:
            android.net.NetworkInfo r2 = r1.getActiveNetworkInfo()     // Catch:{ Exception -> 0x0038 }
            if (r2 == 0) goto L_0x0020
            boolean r0 = r2.isConnected()     // Catch:{ Exception -> 0x003d }
            if (r0 != 0) goto L_0x003d
        L_0x0020:
            android.net.NetworkInfo[] r0 = r1.getAllNetworkInfo()     // Catch:{ Exception -> 0x003d }
            if (r0 == 0) goto L_0x003d
            r1 = 0
        L_0x0027:
            int r3 = r0.length     // Catch:{ Exception -> 0x003d }
            if (r1 >= r3) goto L_0x003d
            r3 = r0[r1]     // Catch:{ Exception -> 0x003d }
            if (r3 == 0) goto L_0x003a
            r3 = r0[r1]     // Catch:{ Exception -> 0x003d }
            boolean r3 = r3.isConnected()     // Catch:{ Exception -> 0x003d }
            if (r3 == 0) goto L_0x003a
            r0 = r0[r1]     // Catch:{ Exception -> 0x003d }
        L_0x0038:
            r2 = r0
            goto L_0x003d
        L_0x003a:
            int r1 = r1 + 1
            goto L_0x0027
        L_0x003d:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.webview.export.internal.utility.c.b():android.net.NetworkInfo");
    }
}
