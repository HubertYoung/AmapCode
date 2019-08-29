package com.autonavi.minimap.onekeycheck.netease.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import proguard.annotation.KeepName;
import proguard.annotation.KeepPublicClassMembers;

@KeepPublicClassMembers
@SuppressLint({"DefaultLocale"})
@KeepName
public class LDNetUtil {
    public static final String NETWORKTYPE_INVALID = "UNKNOWN";
    public static final String NETWORKTYPE_WAP = "WAP";
    public static final String NETWORKTYPE_WIFI = "WIFI";
    public static final String OPEN_IP = "";
    public static final String OPERATOR_URL = "";

    public static String getNetWorkType(Context context) {
        String str;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager == null) {
            return "ConnectivityManager not found";
        }
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            str = "UNKNOWN";
        } else {
            String typeName = activeNetworkInfo.getTypeName();
            if (typeName.equalsIgnoreCase("WIFI")) {
                str = "WIFI";
            } else if (!typeName.equalsIgnoreCase("MOBILE")) {
                str = null;
            } else if (TextUtils.isEmpty(Proxy.getDefaultHost())) {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
                if (telephonyManager != null) {
                    switch (telephonyManager.getNetworkType()) {
                        case 0:
                            str = "UNKNOWN";
                            break;
                        case 1:
                            str = "2G";
                            break;
                        case 2:
                            str = "2G";
                            break;
                        case 3:
                            str = "3G";
                            break;
                        case 4:
                            str = "2G";
                            break;
                        case 5:
                            str = "3G";
                            break;
                        case 6:
                            str = "3G";
                            break;
                        case 7:
                            str = "2G";
                            break;
                        case 8:
                            str = "3G";
                            break;
                        case 9:
                            str = "3G";
                            break;
                        case 10:
                            str = "3G";
                            break;
                        case 11:
                            str = "2G";
                            break;
                        case 12:
                            str = "3G";
                            break;
                        case 13:
                            str = "4G";
                            break;
                        case 14:
                            str = "3G";
                            break;
                        case 15:
                            str = "3G";
                            break;
                        default:
                            str = "4G";
                            break;
                    }
                } else {
                    str = "TM==null";
                }
            } else {
                str = NETWORKTYPE_WAP;
            }
        }
        return str;
    }

    public static Boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService("connectivity");
        if (connectivityManager == null) {
            return Boolean.FALSE;
        }
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo == null || !activeNetworkInfo.isAvailable()) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public static String getMobileOperator(Context context) {
        String str;
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        if (telephonyManager == null) {
            str = null;
        } else {
            str = telephonyManager.getNetworkOperatorName();
        }
        return !TextUtils.isEmpty(str) ? str : "未知运营商";
    }

    public static String getLocalIpByWifi(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
        if (wifiManager == null) {
            return "wifiManager not found";
        }
        WifiInfo connectionInfo = wifiManager.getConnectionInfo();
        if (connectionInfo == null) {
            return "wifiInfo not found";
        }
        int ipAddress = connectionInfo.getIpAddress();
        return String.format("%d.%d.%d.%d", new Object[]{Integer.valueOf(ipAddress & 255), Integer.valueOf((ipAddress >> 8) & 255), Integer.valueOf((ipAddress >> 16) & 255), Integer.valueOf((ipAddress >> 24) & 255)});
    }

    public static String getLocalIpBy3G() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                Enumeration<InetAddress> inetAddresses = networkInterfaces.nextElement().getInetAddresses();
                while (true) {
                    if (inetAddresses.hasMoreElements()) {
                        InetAddress nextElement = inetAddresses.nextElement();
                        if (!nextElement.isLoopbackAddress() && (nextElement instanceof Inet4Address)) {
                            return nextElement.getHostAddress().toString();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String pingGateWayInWifi(Context context) {
        String str;
        WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
        if (wifiManager == null) {
            return "wifiManager not found";
        }
        DhcpInfo dhcpInfo = wifiManager.getDhcpInfo();
        if (dhcpInfo != null) {
            int i = dhcpInfo.gateway;
            str = String.format("%d.%d.%d.%d", new Object[]{Integer.valueOf(i & 255), Integer.valueOf((i >> 8) & 255), Integer.valueOf((i >> 16) & 255), Integer.valueOf((i >> 24) & 255)});
        } else {
            str = null;
        }
        return str;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0062, code lost:
        if (r1 == null) goto L_0x0067;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:?, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0067, code lost:
        r2.destroy();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0070, code lost:
        if (r1 == null) goto L_0x0067;
     */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x0080 A[SYNTHETIC, Splitter:B:44:0x0080] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:35:0x006d=Splitter:B:35:0x006d, B:27:0x005f=Splitter:B:27:0x005f} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getLocalDns(java.lang.String r6) {
        /*
            java.lang.StringBuffer r0 = new java.lang.StringBuffer
            java.lang.String r1 = ""
            r0.<init>(r1)
            r1 = 0
            java.lang.Runtime r2 = java.lang.Runtime.getRuntime()     // Catch:{ IOException -> 0x006b, InterruptedException -> 0x005d, all -> 0x005a }
            java.lang.String r3 = "getprop net."
            java.lang.String r6 = java.lang.String.valueOf(r6)     // Catch:{ IOException -> 0x006b, InterruptedException -> 0x005d, all -> 0x005a }
            java.lang.String r6 = r3.concat(r6)     // Catch:{ IOException -> 0x006b, InterruptedException -> 0x005d, all -> 0x005a }
            java.lang.Process r6 = r2.exec(r6)     // Catch:{ IOException -> 0x006b, InterruptedException -> 0x005d, all -> 0x005a }
            java.io.BufferedReader r2 = new java.io.BufferedReader     // Catch:{ IOException -> 0x0055, InterruptedException -> 0x0050, all -> 0x004e }
            java.io.InputStreamReader r3 = new java.io.InputStreamReader     // Catch:{ IOException -> 0x0055, InterruptedException -> 0x0050, all -> 0x004e }
            java.io.InputStream r4 = r6.getInputStream()     // Catch:{ IOException -> 0x0055, InterruptedException -> 0x0050, all -> 0x004e }
            r3.<init>(r4)     // Catch:{ IOException -> 0x0055, InterruptedException -> 0x0050, all -> 0x004e }
            r2.<init>(r3)     // Catch:{ IOException -> 0x0055, InterruptedException -> 0x0050, all -> 0x004e }
        L_0x0028:
            java.lang.String r1 = r2.readLine()     // Catch:{ IOException -> 0x0048, InterruptedException -> 0x0042, all -> 0x003f }
            if (r1 == 0) goto L_0x0032
            r0.append(r1)     // Catch:{ IOException -> 0x0048, InterruptedException -> 0x0042, all -> 0x003f }
            goto L_0x0028
        L_0x0032:
            r2.close()     // Catch:{ IOException -> 0x0048, InterruptedException -> 0x0042, all -> 0x003f }
            r6.waitFor()     // Catch:{ IOException -> 0x0048, InterruptedException -> 0x0042, all -> 0x003f }
            r2.close()     // Catch:{ Exception -> 0x0073 }
            r6.destroy()     // Catch:{ Exception -> 0x0073 }
            goto L_0x0073
        L_0x003f:
            r0 = move-exception
            r1 = r2
            goto L_0x007e
        L_0x0042:
            r1 = move-exception
            r5 = r2
            r2 = r6
            r6 = r1
            r1 = r5
            goto L_0x005f
        L_0x0048:
            r1 = move-exception
            r5 = r2
            r2 = r6
            r6 = r1
            r1 = r5
            goto L_0x006d
        L_0x004e:
            r0 = move-exception
            goto L_0x007e
        L_0x0050:
            r2 = move-exception
            r5 = r2
            r2 = r6
            r6 = r5
            goto L_0x005f
        L_0x0055:
            r2 = move-exception
            r5 = r2
            r2 = r6
            r6 = r5
            goto L_0x006d
        L_0x005a:
            r0 = move-exception
            r6 = r1
            goto L_0x007e
        L_0x005d:
            r6 = move-exception
            r2 = r1
        L_0x005f:
            r6.printStackTrace()     // Catch:{ all -> 0x007c }
            if (r1 == 0) goto L_0x0067
        L_0x0064:
            r1.close()     // Catch:{ Exception -> 0x0073 }
        L_0x0067:
            r2.destroy()     // Catch:{ Exception -> 0x0073 }
            goto L_0x0073
        L_0x006b:
            r6 = move-exception
            r2 = r1
        L_0x006d:
            r6.printStackTrace()     // Catch:{ all -> 0x007c }
            if (r1 == 0) goto L_0x0067
            goto L_0x0064
        L_0x0073:
            java.lang.String r6 = r0.toString()
            java.lang.String r6 = r6.trim()
            return r6
        L_0x007c:
            r0 = move-exception
            r6 = r2
        L_0x007e:
            if (r1 == 0) goto L_0x0083
            r1.close()     // Catch:{ Exception -> 0x0086 }
        L_0x0083:
            r6.destroy()     // Catch:{ Exception -> 0x0086 }
        L_0x0086:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.onekeycheck.netease.utils.LDNetUtil.getLocalDns(java.lang.String):java.lang.String");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0035, code lost:
        r2 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0036, code lost:
        r10 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0038, code lost:
        r4 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0039, code lost:
        r2 = 0;
        r10 = null;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0035 A[ExcHandler: all (th java.lang.Throwable), Splitter:B:1:0x0006] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.util.Map<java.lang.String, java.lang.Object> getDomainIp(java.lang.String r10) {
        /*
            java.util.HashMap r0 = new java.util.HashMap
            r0.<init>()
            r1 = 0
            long r2 = java.lang.System.currentTimeMillis()     // Catch:{ UnknownHostException -> 0x0038, all -> 0x0035 }
            java.net.InetAddress[] r10 = java.net.InetAddress.getAllByName(r10)     // Catch:{ UnknownHostException -> 0x0032, all -> 0x0035 }
            if (r10 == 0) goto L_0x0026
            long r4 = java.lang.System.currentTimeMillis()     // Catch:{ UnknownHostException -> 0x0024 }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ UnknownHostException -> 0x0024 }
            r6.<init>()     // Catch:{ UnknownHostException -> 0x0024 }
            r7 = 0
            long r4 = r4 - r2
            r6.append(r4)     // Catch:{ UnknownHostException -> 0x0024 }
            java.lang.String r4 = r6.toString()     // Catch:{ UnknownHostException -> 0x0024 }
            r1 = r4
            goto L_0x0026
        L_0x0024:
            r4 = move-exception
            goto L_0x003c
        L_0x0026:
            java.lang.String r2 = "remoteInet"
            r0.put(r2, r10)
            java.lang.String r10 = "useTime"
            r0.put(r10, r1)
            goto L_0x005c
        L_0x0032:
            r4 = move-exception
            r10 = r1
            goto L_0x003c
        L_0x0035:
            r2 = move-exception
            r10 = r1
            goto L_0x0064
        L_0x0038:
            r4 = move-exception
            r2 = 0
            r10 = r1
        L_0x003c:
            long r5 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x0063 }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x0063 }
            r7.<init>()     // Catch:{ all -> 0x0063 }
            r8 = 0
            long r5 = r5 - r2
            r7.append(r5)     // Catch:{ all -> 0x0063 }
            java.lang.String r2 = r7.toString()     // Catch:{ all -> 0x0063 }
            r4.printStackTrace()     // Catch:{ all -> 0x005d }
            java.lang.String r10 = "remoteInet"
            r0.put(r10, r1)
            java.lang.String r10 = "useTime"
            r0.put(r10, r2)
        L_0x005c:
            return r0
        L_0x005d:
            r10 = move-exception
            r9 = r2
            r2 = r10
            r10 = r1
            r1 = r9
            goto L_0x0064
        L_0x0063:
            r2 = move-exception
        L_0x0064:
            java.lang.String r3 = "remoteInet"
            r0.put(r3, r10)
            java.lang.String r10 = "useTime"
            r0.put(r10, r1)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.onekeycheck.netease.utils.LDNetUtil.getDomainIp(java.lang.String):java.util.Map");
    }
}
