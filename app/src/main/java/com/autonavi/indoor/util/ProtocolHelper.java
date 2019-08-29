package com.autonavi.indoor.util;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.support.annotation.RequiresPermission;
import android.text.TextUtils;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ProtocolHelper {
    static Method getSession;
    static Method getSpm;

    /* JADX WARNING: Can't wrap try/catch for region: R(2:22|23) */
    /* JADX WARNING: Code restructure failed: missing block: B:23:?, code lost:
        r0 = r9.getBytes();
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:22:0x0094 */
    @android.support.annotation.RequiresPermission("android.permission.ACCESS_WIFI_STATE")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void setLBSHead(java.net.HttpURLConnection r7, com.autonavi.indoor.constant.Configuration r8, java.lang.String r9) {
        /*
            com.autonavi.indoor.constant.Configuration$ServerType r0 = r8.mServerType
            com.autonavi.indoor.constant.Configuration$ServerType r1 = com.autonavi.indoor.constant.Configuration.ServerType.SERVER_LBS
            if (r0 == r1) goto L_0x0007
            return
        L_0x0007:
            java.lang.String r0 = "\\?"
            java.lang.String[] r9 = r9.split(r0)     // Catch:{ Throwable -> 0x00bc }
            r0 = 1
            r9 = r9[r0]     // Catch:{ Throwable -> 0x00bc }
            java.lang.String r1 = "&"
            java.lang.String[] r9 = r9.split(r1)     // Catch:{ Throwable -> 0x00bc }
            int r1 = r9.length     // Catch:{ Throwable -> 0x00bc }
            r2 = 0
            r3 = 0
        L_0x0019:
            if (r3 >= r1) goto L_0x0054
            r4 = r9[r3]     // Catch:{ Throwable -> 0x00bc }
            boolean r5 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x00bc }
            if (r5 == 0) goto L_0x0024
            com.autonavi.indoor.util.L.d(r4)     // Catch:{ Throwable -> 0x00bc }
        L_0x0024:
            java.lang.String r5 = "="
            java.lang.String[] r4 = r4.split(r5)     // Catch:{ Throwable -> 0x00bc }
            r5 = r4[r2]     // Catch:{ Throwable -> 0x00bc }
            java.lang.String r6 = "key"
            boolean r5 = r5.equals(r6)     // Catch:{ Throwable -> 0x00bc }
            if (r5 != 0) goto L_0x004a
            r5 = r4[r2]     // Catch:{ Throwable -> 0x00bc }
            java.lang.String r6 = "scode"
            boolean r5 = r5.equals(r6)     // Catch:{ Throwable -> 0x00bc }
            if (r5 != 0) goto L_0x004a
            r5 = r4[r2]     // Catch:{ Throwable -> 0x00bc }
            java.lang.String r6 = "ts"
            boolean r5 = r5.equals(r6)     // Catch:{ Throwable -> 0x00bc }
            if (r5 == 0) goto L_0x0051
        L_0x004a:
            r5 = r4[r2]     // Catch:{ Throwable -> 0x00bc }
            r4 = r4[r0]     // Catch:{ Throwable -> 0x00bc }
            r7.setRequestProperty(r5, r4)     // Catch:{ Throwable -> 0x00bc }
        L_0x0051:
            int r3 = r3 + 1
            goto L_0x0019
        L_0x0054:
            java.lang.String r9 = "logversion"
            java.lang.String r1 = "2.0"
            r7.setRequestProperty(r9, r1)     // Catch:{ Throwable -> 0x00bc }
            java.lang.String r9 = "platinfo"
            java.lang.String r1 = "platform=Android&sdkversion=%s&product=%s"
            r3 = 2
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x00bc }
            java.lang.String r4 = r8.mSDKVersion     // Catch:{ Throwable -> 0x00bc }
            r3[r2] = r4     // Catch:{ Throwable -> 0x00bc }
            java.lang.String r2 = r8.mSDKName     // Catch:{ Throwable -> 0x00bc }
            r3[r0] = r2     // Catch:{ Throwable -> 0x00bc }
            java.lang.String r0 = java.lang.String.format(r1, r3)     // Catch:{ Throwable -> 0x00bc }
            r7.setRequestProperty(r9, r0)     // Catch:{ Throwable -> 0x00bc }
            java.lang.String r9 = "User-Agent"
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00bc }
            java.lang.String r1 = "AMAP_SDK_Android_indoormap_"
            r0.<init>(r1)     // Catch:{ Throwable -> 0x00bc }
            java.lang.String r1 = r8.mSDKVersion     // Catch:{ Throwable -> 0x00bc }
            r0.append(r1)     // Catch:{ Throwable -> 0x00bc }
            java.lang.String r0 = r0.toString()     // Catch:{ Throwable -> 0x00bc }
            r7.setRequestProperty(r9, r0)     // Catch:{ Throwable -> 0x00bc }
            android.content.Context r9 = r8.context     // Catch:{ Throwable -> 0x00bc }
            java.lang.String r9 = getInitXInfo(r9)     // Catch:{ Throwable -> 0x00bc }
            java.lang.String r0 = "utf-8"
            byte[] r0 = r9.getBytes(r0)     // Catch:{ UnsupportedEncodingException -> 0x0094 }
            goto L_0x0098
        L_0x0094:
            byte[] r0 = r9.getBytes()     // Catch:{ Throwable -> 0x00bc }
        L_0x0098:
            r9 = 0
            android.content.Context r8 = r8.context     // Catch:{ Exception -> 0x00a0 }
            java.lang.String r8 = com.autonavi.indoor.util.EncryptHelper.privateRsaAesData(r8, r0)     // Catch:{ Exception -> 0x00a0 }
            goto L_0x00a9
        L_0x00a0:
            r8 = move-exception
            boolean r0 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x00bc }
            if (r0 == 0) goto L_0x00a8
            com.autonavi.indoor.util.L.d(r8)     // Catch:{ Throwable -> 0x00bc }
        L_0x00a8:
            r8 = r9
        L_0x00a9:
            java.lang.String r9 = "X-INFO"
            r7.setRequestProperty(r9, r8)     // Catch:{ Throwable -> 0x00bc }
            java.lang.String r8 = "csid"
            java.util.UUID r9 = java.util.UUID.randomUUID()     // Catch:{ Throwable -> 0x00bc }
            java.lang.String r9 = r9.toString()     // Catch:{ Throwable -> 0x00bc }
            r7.setRequestProperty(r8, r9)     // Catch:{ Throwable -> 0x00bc }
            return
        L_0x00bc:
            r7 = move-exception
            boolean r8 = com.autonavi.indoor.util.L.isLogging
            if (r8 == 0) goto L_0x00c4
            com.autonavi.indoor.util.L.d(r7)
        L_0x00c4:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.indoor.util.ProtocolHelper.setLBSHead(java.net.HttpURLConnection, com.autonavi.indoor.constant.Configuration, java.lang.String):void");
    }

    @RequiresPermission("android.permission.ACCESS_WIFI_STATE")
    private static String getInitXInfo(Context context) {
        StringBuilder sb = new StringBuilder();
        try {
            String deviceID = DeviceUtils.getDeviceID(context);
            sb.append("diu=");
            sb.append(deviceID);
            sb.append("&pkg=");
            sb.append(PackageHelper.getPackageName(context));
            sb.append("&model=");
            sb.append(Build.MODEL);
            sb.append("&manufacture=");
            sb.append(Build.MANUFACTURER);
            sb.append("&device=");
            sb.append(Build.DEVICE);
            sb.append("&appname=");
            sb.append(PackageHelper.getApplicationName(context));
            sb.append("&appversion=");
            sb.append(PackageHelper.getApplicationVersion(context));
            sb.append("&sysversion=");
            sb.append(VERSION.SDK_INT);
            String subscriberId = DeviceUtils.getSubscriberId(context);
            sb.append("&sim=");
            sb.append(subscriberId);
            StringBuilder sb2 = new StringBuilder("&resolution=");
            sb2.append(DeviceUtils.getReslution(context));
            sb.append(sb2.toString());
            sb.append("&mac=");
            sb.append(DeviceUtils.getDeviceMac(context));
            sb.append("&wifis=");
            sb.append(DeviceUtils.getWifiMacs(context));
            sb.append("&ant=");
            sb.append(DeviceUtils.getActiveNetWorkType(context));
            sb.append("&nt=");
            sb.append(DeviceUtils.getNetWorkType(context));
            String networkOperatorName = DeviceUtils.getNetworkOperatorName(context);
            sb.append("&np=");
            sb.append(networkOperatorName);
            sb.append("&mnc=");
            sb.append(DeviceUtils.getMNC(context));
            String connectWifi = DeviceUtils.getConnectWifi(context);
            if (!TextUtils.isEmpty(connectWifi)) {
                sb.append("&wifi=");
                sb.append(connectWifi);
            }
            String utdid = EncryptHelper.getUTDID(context);
            if (utdid == null) {
                utdid = "";
            }
            sb.append("&bts=");
            sb.append(DeviceUtils.getCellInfo(context));
            sb.append("&tid=");
            sb.append(utdid);
        } catch (Throwable th) {
            if (L.isLogging) {
                L.d(th);
            }
        }
        if (L.isLogging) {
            StringBuilder sb3 = new StringBuilder("xInfoStr:");
            sb3.append(sb.toString());
            L.d(sb3.toString());
        }
        return sb.toString();
    }

    public static String sortParams(String str) {
        if (str == null) {
            return null;
        }
        try {
            String[] split = str.split("&");
            Arrays.sort(split);
            StringBuffer stringBuffer = new StringBuffer();
            for (String append : split) {
                stringBuffer.append(append);
                stringBuffer.append("&");
            }
            String stringBuffer2 = stringBuffer.toString();
            if (stringBuffer2.length() > 1) {
                return (String) stringBuffer2.subSequence(0, stringBuffer2.length() - 1);
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return str;
    }

    public static String getSession() {
        Object obj = null;
        try {
            if (getSession == null) {
                getSession = Class.forName("com.autonavi.minimap.net.NetworkParam").getDeclaredMethod("getSession", new Class[0]);
            }
            obj = getSession.invoke(null, new Object[0]);
        } catch (ClassNotFoundException unused) {
        } catch (Exception e) {
            if (L.isLogging) {
                L.d((Throwable) e);
            }
        }
        if (obj == null) {
            return "";
        }
        return String.valueOf(obj);
    }

    public static String getSpm() {
        Object obj = null;
        try {
            if (getSpm == null) {
                getSpm = Class.forName("com.autonavi.minimap.net.NetworkParam").getDeclaredMethod("getSpm", new Class[0]);
            }
            obj = getSpm.invoke(null, new Object[0]);
        } catch (ClassNotFoundException unused) {
        } catch (Exception e) {
            if (L.isLogging) {
                L.d((Throwable) e);
            }
        }
        if (obj == null) {
            return "";
        }
        return String.valueOf(obj);
    }
}
