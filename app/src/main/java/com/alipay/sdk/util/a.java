package com.alipay.sdk.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;

public final class a {
    private static final String b = "00:00:00:00:00:00";
    private static a e;
    public String a;
    private String c;
    private String d;

    public static a a(Context context) {
        if (e == null) {
            e = new a(context);
        }
        return e;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0066, code lost:
        if (android.text.TextUtils.isEmpty(r3.a) == false) goto L_0x0069;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0069, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0053, code lost:
        if (android.text.TextUtils.isEmpty(r3.a) != false) goto L_0x0055;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0055, code lost:
        r3.a = b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0059, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private a(android.content.Context r4) {
        /*
            r3 = this;
            r3.<init>()
            android.content.Context r0 = r4.getApplicationContext()     // Catch:{ Exception -> 0x005c }
            java.lang.String r1 = "phone"
            java.lang.Object r0 = r0.getSystemService(r1)     // Catch:{ Exception -> 0x005c }
            android.telephony.TelephonyManager r0 = (android.telephony.TelephonyManager) r0     // Catch:{ Exception -> 0x005c }
            java.lang.String r1 = r0.getDeviceId()     // Catch:{ Exception -> 0x005c }
            r3.b(r1)     // Catch:{ Exception -> 0x005c }
            java.lang.String r0 = r0.getSubscriberId()     // Catch:{ Exception -> 0x005c }
            if (r0 == 0) goto L_0x0034
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x005c }
            r1.<init>()     // Catch:{ Exception -> 0x005c }
            r1.append(r0)     // Catch:{ Exception -> 0x005c }
            java.lang.String r0 = "000000000000000"
            r1.append(r0)     // Catch:{ Exception -> 0x005c }
            java.lang.String r0 = r1.toString()     // Catch:{ Exception -> 0x005c }
            r1 = 0
            r2 = 15
            java.lang.String r0 = r0.substring(r1, r2)     // Catch:{ Exception -> 0x005c }
        L_0x0034:
            r3.c = r0     // Catch:{ Exception -> 0x005c }
            android.content.Context r4 = r4.getApplicationContext()     // Catch:{ Exception -> 0x005c }
            java.lang.String r0 = "wifi"
            java.lang.Object r4 = r4.getSystemService(r0)     // Catch:{ Exception -> 0x005c }
            android.net.wifi.WifiManager r4 = (android.net.wifi.WifiManager) r4     // Catch:{ Exception -> 0x005c }
            android.net.wifi.WifiInfo r4 = r4.getConnectionInfo()     // Catch:{ Exception -> 0x005c }
            java.lang.String r4 = r4.getMacAddress()     // Catch:{ Exception -> 0x005c }
            r3.a = r4     // Catch:{ Exception -> 0x005c }
            java.lang.String r4 = r3.a
            boolean r4 = android.text.TextUtils.isEmpty(r4)
            if (r4 == 0) goto L_0x0069
        L_0x0055:
            java.lang.String r4 = "00:00:00:00:00:00"
            r3.a = r4
            return
        L_0x005a:
            r4 = move-exception
            goto L_0x006a
        L_0x005c:
            r4 = move-exception
            r4.printStackTrace()     // Catch:{ all -> 0x005a }
            java.lang.String r4 = r3.a
            boolean r4 = android.text.TextUtils.isEmpty(r4)
            if (r4 == 0) goto L_0x0069
            goto L_0x0055
        L_0x0069:
            return
        L_0x006a:
            java.lang.String r0 = r3.a
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 == 0) goto L_0x0076
            java.lang.String r0 = "00:00:00:00:00:00"
            r3.a = r0
        L_0x0076:
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.util.a.<init>(android.content.Context):void");
    }

    public final String a() {
        if (TextUtils.isEmpty(this.c)) {
            this.c = "000000000000000";
        }
        return this.c;
    }

    public final String b() {
        if (TextUtils.isEmpty(this.d)) {
            this.d = "000000000000000";
        }
        return this.d;
    }

    private void a(String str) {
        if (str != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append("000000000000000");
            str = sb.toString().substring(0, 15);
        }
        this.c = str;
    }

    private void b(String str) {
        if (str != null) {
            byte[] bytes = str.getBytes();
            for (int i = 0; i < bytes.length; i++) {
                if (bytes[i] < 48 || bytes[i] > 57) {
                    bytes[i] = 48;
                }
            }
            String str2 = new String(bytes);
            StringBuilder sb = new StringBuilder();
            sb.append(str2);
            sb.append("000000000000000");
            str = sb.toString().substring(0, 15);
        }
        this.d = str;
    }

    private String c() {
        String b2 = b();
        StringBuilder sb = new StringBuilder();
        sb.append(b2);
        sb.append(MergeUtil.SEPARATOR_KV);
        String sb2 = sb.toString();
        String a2 = a();
        if (TextUtils.isEmpty(a2)) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(sb2);
            sb3.append("000000000000000");
            return sb3.toString();
        }
        StringBuilder sb4 = new StringBuilder();
        sb4.append(sb2);
        sb4.append(a2);
        return sb4.toString();
    }

    private String d() {
        return this.a;
    }

    public static d b(Context context) {
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getApplicationContext().getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo != null && activeNetworkInfo.getType() == 0) {
                return d.a(activeNetworkInfo.getSubtype());
            }
            if (activeNetworkInfo == null || activeNetworkInfo.getType() != 1) {
                return d.NONE;
            }
            return d.WIFI;
        } catch (Exception unused) {
            return d.NONE;
        }
    }

    public static String c(Context context) {
        String str;
        a a2 = a(context);
        String b2 = a2.b();
        StringBuilder sb = new StringBuilder();
        sb.append(b2);
        sb.append(MergeUtil.SEPARATOR_KV);
        String sb2 = sb.toString();
        String a3 = a2.a();
        if (TextUtils.isEmpty(a3)) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(sb2);
            sb3.append("000000000000000");
            str = sb3.toString();
        } else {
            StringBuilder sb4 = new StringBuilder();
            sb4.append(sb2);
            sb4.append(a3);
            str = sb4.toString();
        }
        return str.substring(0, 8);
    }

    public static String d(Context context) {
        if (context == null) {
            return "";
        }
        try {
            return context.getResources().getConfiguration().locale.toString();
        } catch (Throwable unused) {
            return "";
        }
    }
}
