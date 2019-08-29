package com.xiaomi.metoknlp.devicediscover;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import java.net.InetAddress;
import java.net.UnknownHostException;

class j {
    public static DhcpInfo a(Context context) {
        DhcpInfo dhcpInfo = null;
        if (context == null) {
            return null;
        }
        WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
        if (wifiManager != null) {
            if (!wifiManager.isWifiEnabled()) {
                return null;
            }
            try {
                if (context.getPackageManager().checkPermission("android.permission.ACCESS_WIFI_STATE", context.getPackageName()) == 0) {
                    dhcpInfo = wifiManager.getDhcpInfo();
                }
            } catch (Exception unused) {
            }
        }
        return dhcpInfo;
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x002b A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x002c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String a(android.content.Context r4, int r5) {
        /*
            r0 = 0
            java.lang.String r1 = "wifi"
            java.lang.Object r1 = r4.getSystemService(r1)     // Catch:{ Exception -> 0x0047 }
            android.net.wifi.WifiManager r1 = (android.net.wifi.WifiManager) r1     // Catch:{ Exception -> 0x0047 }
            if (r1 == 0) goto L_0x0047
            boolean r2 = r1.isWifiEnabled()     // Catch:{ Exception -> 0x0047 }
            if (r2 != 0) goto L_0x0013
            return r0
        L_0x0013:
            android.content.pm.PackageManager r2 = r4.getPackageManager()     // Catch:{ Exception -> 0x0028 }
            java.lang.String r3 = "android.permission.ACCESS_WIFI_STATE"
            java.lang.String r4 = r4.getPackageName()     // Catch:{ Exception -> 0x0028 }
            int r4 = r2.checkPermission(r3, r4)     // Catch:{ Exception -> 0x0028 }
            if (r4 != 0) goto L_0x0028
            android.net.wifi.WifiInfo r4 = r1.getConnectionInfo()     // Catch:{ Exception -> 0x0028 }
            goto L_0x0029
        L_0x0028:
            r4 = r0
        L_0x0029:
            if (r4 != 0) goto L_0x002c
            return r0
        L_0x002c:
            if (r5 != 0) goto L_0x0033
            java.lang.String r4 = r4.getMacAddress()     // Catch:{ Exception -> 0x0047 }
            return r4
        L_0x0033:
            r1 = 1
            if (r5 != r1) goto L_0x003b
            java.lang.String r4 = r4.getBSSID()     // Catch:{ Exception -> 0x0047 }
            return r4
        L_0x003b:
            r1 = 2
            if (r5 != r1) goto L_0x0047
            java.lang.String r4 = r4.getSSID()     // Catch:{ Exception -> 0x0047 }
            java.lang.String r4 = a(r4)     // Catch:{ Exception -> 0x0047 }
            return r4
        L_0x0047:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.metoknlp.devicediscover.j.a(android.content.Context, int):java.lang.String");
    }

    private static String a(String str) {
        return (!str.startsWith("\"") || !str.endsWith("\"")) ? str : str.substring(1, str.length() - 1);
    }

    public static InetAddress a(int i) {
        try {
            return InetAddress.getByAddress(new byte[]{(byte) (i & 255), (byte) ((i >> 8) & 255), (byte) ((i >> 16) & 255), (byte) ((i >> 24) & 255)});
        } catch (UnknownHostException unused) {
            throw new AssertionError();
        }
    }

    public static void a(Context context, String str, String str2) {
        Editor edit = context.getSharedPreferences("devicediscover", 0).edit();
        edit.putString(str, str2);
        edit.commit();
    }

    public static String b(Context context) {
        try {
            DhcpInfo a = a(context);
            if (a == null) {
                return null;
            }
            return a(a.gateway).getHostAddress();
        } catch (Exception unused) {
            return null;
        }
    }

    public static String b(Context context, String str, String str2) {
        return context.getSharedPreferences("devicediscover", 0).getString(str, str2);
    }

    public static String c(Context context) {
        try {
            DhcpInfo a = a(context);
            if (a == null) {
                return null;
            }
            return a(a.serverAddress).getHostAddress();
        } catch (Exception unused) {
            return null;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0041, code lost:
        if (r1 != null) goto L_0x0043;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:?, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0046, code lost:
        return r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0047, code lost:
        if (r1 != null) goto L_0x0043;
     */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x003d A[SYNTHETIC, Splitter:B:24:0x003d] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String d(android.content.Context r6) {
        /*
            r0 = 0
            java.lang.String r6 = a(r6, r0)
            if (r6 == 0) goto L_0x0015
            boolean r1 = r6.isEmpty()
            if (r1 != 0) goto L_0x0015
            java.lang.String r1 = "02:00:00:00:00:00"
            boolean r1 = r1.equals(r6)
            if (r1 == 0) goto L_0x004a
        L_0x0015:
            r1 = 0
            r2 = 1024(0x400, float:1.435E-42)
            char[] r3 = new char[r2]     // Catch:{ FileNotFoundException -> 0x0047, Exception -> 0x0041, all -> 0x003a }
            java.io.FileReader r4 = new java.io.FileReader     // Catch:{ FileNotFoundException -> 0x0047, Exception -> 0x0041, all -> 0x003a }
            java.lang.String r5 = "/sys/class/net/wlan0/address"
            r4.<init>(r5)     // Catch:{ FileNotFoundException -> 0x0047, Exception -> 0x0041, all -> 0x003a }
            int r1 = r4.read(r3, r0, r2)     // Catch:{ FileNotFoundException -> 0x0038, Exception -> 0x0036, all -> 0x0033 }
            java.lang.String r2 = new java.lang.String     // Catch:{ FileNotFoundException -> 0x0038, Exception -> 0x0036, all -> 0x0033 }
            r2.<init>(r3, r0, r1)     // Catch:{ FileNotFoundException -> 0x0038, Exception -> 0x0036, all -> 0x0033 }
            java.lang.String r0 = r2.trim()     // Catch:{ FileNotFoundException -> 0x0038, Exception -> 0x0036, all -> 0x0033 }
            r4.close()     // Catch:{ Exception -> 0x0031 }
        L_0x0031:
            r6 = r0
            return r6
        L_0x0033:
            r6 = move-exception
            r1 = r4
            goto L_0x003b
        L_0x0036:
            r1 = r4
            goto L_0x0041
        L_0x0038:
            r1 = r4
            goto L_0x0047
        L_0x003a:
            r6 = move-exception
        L_0x003b:
            if (r1 == 0) goto L_0x0040
            r1.close()     // Catch:{ Exception -> 0x0040 }
        L_0x0040:
            throw r6
        L_0x0041:
            if (r1 == 0) goto L_0x004a
        L_0x0043:
            r1.close()     // Catch:{ Exception -> 0x004a }
            return r6
        L_0x0047:
            if (r1 == 0) goto L_0x004a
            goto L_0x0043
        L_0x004a:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.metoknlp.devicediscover.j.d(android.content.Context):java.lang.String");
    }
}
