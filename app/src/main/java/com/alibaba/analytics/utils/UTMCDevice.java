package com.alibaba.analytics.utils;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import java.lang.reflect.Field;
import java.util.Map;

public class UTMCDevice {
    private static Map<String, String> s_deviceInfoMap;

    /* JADX WARNING: Can't wrap try/catch for region: R(2:11|12) */
    /* JADX WARNING: Code restructure failed: missing block: B:12:?, code lost:
        s_deviceInfoMap.put(com.alibaba.analytics.core.model.LogField.ACCESS.toString(), "Unknown");
        s_deviceInfoMap.put(com.alibaba.analytics.core.model.LogField.ACCESS_SUBTYPE.toString(), "Unknown");
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x004c */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized void updateUTMCDeviceNetworkStatus(android.content.Context r6) {
        /*
            java.lang.Class<com.alibaba.analytics.utils.UTMCDevice> r0 = com.alibaba.analytics.utils.UTMCDevice.class
            monitor-enter(r0)
            java.util.Map<java.lang.String, java.lang.String> r1 = s_deviceInfoMap     // Catch:{ all -> 0x0077 }
            if (r1 == 0) goto L_0x0075
            java.lang.String r1 = "UTMCDevice"
            r2 = 1
            java.lang.Object[] r3 = new java.lang.Object[r2]     // Catch:{ all -> 0x0077 }
            java.lang.String r4 = "updateUTMCDeviceNetworkStatus"
            r5 = 0
            r3[r5] = r4     // Catch:{ all -> 0x0077 }
            com.alibaba.analytics.utils.Logger.d(r1, r3)     // Catch:{ all -> 0x0077 }
            java.lang.String[] r6 = com.alibaba.analytics.core.network.NetworkUtil.getNetworkState(r6)     // Catch:{ Exception -> 0x004c }
            java.util.Map<java.lang.String, java.lang.String> r1 = s_deviceInfoMap     // Catch:{ Exception -> 0x004c }
            com.alibaba.analytics.core.model.LogField r3 = com.alibaba.analytics.core.model.LogField.ACCESS     // Catch:{ Exception -> 0x004c }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x004c }
            r4 = r6[r5]     // Catch:{ Exception -> 0x004c }
            r1.put(r3, r4)     // Catch:{ Exception -> 0x004c }
            r1 = r6[r5]     // Catch:{ Exception -> 0x004c }
            java.lang.String r3 = "2G/3G"
            boolean r1 = r1.equals(r3)     // Catch:{ Exception -> 0x004c }
            if (r1 == 0) goto L_0x003e
            java.util.Map<java.lang.String, java.lang.String> r1 = s_deviceInfoMap     // Catch:{ Exception -> 0x004c }
            com.alibaba.analytics.core.model.LogField r3 = com.alibaba.analytics.core.model.LogField.ACCESS_SUBTYPE     // Catch:{ Exception -> 0x004c }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x004c }
            r6 = r6[r2]     // Catch:{ Exception -> 0x004c }
            r1.put(r3, r6)     // Catch:{ Exception -> 0x004c }
            goto L_0x0066
        L_0x003e:
            java.util.Map<java.lang.String, java.lang.String> r6 = s_deviceInfoMap     // Catch:{ Exception -> 0x004c }
            com.alibaba.analytics.core.model.LogField r1 = com.alibaba.analytics.core.model.LogField.ACCESS_SUBTYPE     // Catch:{ Exception -> 0x004c }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x004c }
            java.lang.String r2 = "Unknown"
            r6.put(r1, r2)     // Catch:{ Exception -> 0x004c }
            goto L_0x0066
        L_0x004c:
            java.util.Map<java.lang.String, java.lang.String> r6 = s_deviceInfoMap     // Catch:{ all -> 0x0077 }
            com.alibaba.analytics.core.model.LogField r1 = com.alibaba.analytics.core.model.LogField.ACCESS     // Catch:{ all -> 0x0077 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x0077 }
            java.lang.String r2 = "Unknown"
            r6.put(r1, r2)     // Catch:{ all -> 0x0077 }
            java.util.Map<java.lang.String, java.lang.String> r6 = s_deviceInfoMap     // Catch:{ all -> 0x0077 }
            com.alibaba.analytics.core.model.LogField r1 = com.alibaba.analytics.core.model.LogField.ACCESS_SUBTYPE     // Catch:{ all -> 0x0077 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x0077 }
            java.lang.String r2 = "Unknown"
            r6.put(r1, r2)     // Catch:{ all -> 0x0077 }
        L_0x0066:
            java.util.Map<java.lang.String, java.lang.String> r6 = s_deviceInfoMap     // Catch:{ all -> 0x0077 }
            com.alibaba.analytics.core.model.LogField r1 = com.alibaba.analytics.core.model.LogField.CARRIER     // Catch:{ all -> 0x0077 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x0077 }
            java.lang.String r2 = com.alibaba.analytics.core.network.NetworkOperatorUtil.getCurrentNetworkOperatorName()     // Catch:{ all -> 0x0077 }
            r6.put(r1, r2)     // Catch:{ all -> 0x0077 }
        L_0x0075:
            monitor-exit(r0)
            return
        L_0x0077:
            r6 = move-exception
            monitor-exit(r0)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.analytics.utils.UTMCDevice.updateUTMCDeviceNetworkStatus(android.content.Context):void");
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(28:10|11|12|13|14|15|16|17|18|19|20|21|(6:23|(1:25)|26|(1:28)|29|(1:31))|32|(1:34)|35|36|(1:38)(1:39)|40|(1:42)(1:43)|46|47|(1:49)(1:50)|53|54|55|56|57) */
    /* JADX WARNING: Code restructure failed: missing block: B:45:?, code lost:
        r2.put(com.alibaba.analytics.core.model.LogField.RESOLUTION.toString(), "Unknown");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:?, code lost:
        r2.put(com.alibaba.analytics.core.model.LogField.ACCESS.toString(), "Unknown");
        r2.put(com.alibaba.analytics.core.model.LogField.ACCESS_SUBTYPE.toString(), "Unknown");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x01c3, code lost:
        return null;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:14:0x0020 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:19:0x007f */
    /* JADX WARNING: Missing exception handler attribute for start block: B:44:0x015e */
    /* JADX WARNING: Missing exception handler attribute for start block: B:51:0x019b */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0090 A[Catch:{ Exception -> 0x01c2 }] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x00de A[Catch:{ Exception -> 0x01c2 }] */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x00f9 A[Catch:{ Exception -> 0x015e }] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0109 A[Catch:{ Exception -> 0x015e }] */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x0122 A[Catch:{ Exception -> 0x015e }] */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x0140 A[Catch:{ Exception -> 0x015e }] */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x0182 A[Catch:{ Exception -> 0x019b }] */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x018f A[Catch:{ Exception -> 0x019b }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized java.util.Map<java.lang.String, java.lang.String> getDeviceInfo(android.content.Context r8) {
        /*
            java.lang.Class<com.alibaba.analytics.utils.UTMCDevice> r0 = com.alibaba.analytics.utils.UTMCDevice.class
            monitor-enter(r0)
            java.util.Map<java.lang.String, java.lang.String> r1 = s_deviceInfoMap     // Catch:{ all -> 0x01c6 }
            if (r1 == 0) goto L_0x000b
            java.util.Map<java.lang.String, java.lang.String> r8 = s_deviceInfoMap     // Catch:{ all -> 0x01c6 }
            monitor-exit(r0)
            return r8
        L_0x000b:
            r1 = 0
            if (r8 == 0) goto L_0x01c4
            java.util.HashMap r2 = new java.util.HashMap     // Catch:{ all -> 0x01c6 }
            r2.<init>()     // Catch:{ all -> 0x01c6 }
            com.alibaba.analytics.core.model.LogField r3 = com.alibaba.analytics.core.model.LogField.UTDID     // Catch:{ Exception -> 0x0020 }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x0020 }
            java.lang.String r4 = com.ut.device.UTDevice.getUtdid(r8)     // Catch:{ Exception -> 0x0020 }
            r2.put(r3, r4)     // Catch:{ Exception -> 0x0020 }
        L_0x0020:
            com.alibaba.analytics.core.model.LogField r3 = com.alibaba.analytics.core.model.LogField.IMEI     // Catch:{ Exception -> 0x01c2 }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x01c2 }
            java.lang.String r4 = com.alibaba.analytics.utils.PhoneInfoUtils.getImei(r8)     // Catch:{ Exception -> 0x01c2 }
            r2.put(r3, r4)     // Catch:{ Exception -> 0x01c2 }
            com.alibaba.analytics.core.model.LogField r3 = com.alibaba.analytics.core.model.LogField.IMSI     // Catch:{ Exception -> 0x01c2 }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x01c2 }
            java.lang.String r4 = com.alibaba.analytics.utils.PhoneInfoUtils.getImsi(r8)     // Catch:{ Exception -> 0x01c2 }
            r2.put(r3, r4)     // Catch:{ Exception -> 0x01c2 }
            com.alibaba.analytics.core.model.LogField r3 = com.alibaba.analytics.core.model.LogField.DEVICE_MODEL     // Catch:{ Exception -> 0x01c2 }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x01c2 }
            java.lang.String r4 = android.os.Build.MODEL     // Catch:{ Exception -> 0x01c2 }
            r2.put(r3, r4)     // Catch:{ Exception -> 0x01c2 }
            com.alibaba.analytics.core.model.LogField r3 = com.alibaba.analytics.core.model.LogField.BRAND     // Catch:{ Exception -> 0x01c2 }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x01c2 }
            java.lang.String r4 = android.os.Build.BRAND     // Catch:{ Exception -> 0x01c2 }
            r2.put(r3, r4)     // Catch:{ Exception -> 0x01c2 }
            com.alibaba.analytics.core.model.LogField r3 = com.alibaba.analytics.core.model.LogField.OSVERSION     // Catch:{ Exception -> 0x01c2 }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x01c2 }
            java.lang.String r4 = android.os.Build.VERSION.RELEASE     // Catch:{ Exception -> 0x01c2 }
            r2.put(r3, r4)     // Catch:{ Exception -> 0x01c2 }
            com.alibaba.analytics.core.model.LogField r3 = com.alibaba.analytics.core.model.LogField.OS     // Catch:{ Exception -> 0x01c2 }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x01c2 }
            java.lang.String r4 = "a"
            r2.put(r3, r4)     // Catch:{ Exception -> 0x01c2 }
            r3 = 0
            android.content.pm.PackageManager r4 = r8.getPackageManager()     // Catch:{ NameNotFoundException -> 0x007f }
            java.lang.String r5 = r8.getPackageName()     // Catch:{ NameNotFoundException -> 0x007f }
            android.content.pm.PackageInfo r4 = r4.getPackageInfo(r5, r3)     // Catch:{ NameNotFoundException -> 0x007f }
            com.alibaba.analytics.core.model.LogField r5 = com.alibaba.analytics.core.model.LogField.APPVERSION     // Catch:{ NameNotFoundException -> 0x007f }
            java.lang.String r5 = r5.toString()     // Catch:{ NameNotFoundException -> 0x007f }
            java.lang.String r4 = r4.versionName     // Catch:{ NameNotFoundException -> 0x007f }
            r2.put(r5, r4)     // Catch:{ NameNotFoundException -> 0x007f }
            goto L_0x008a
        L_0x007f:
            com.alibaba.analytics.core.model.LogField r4 = com.alibaba.analytics.core.model.LogField.APPVERSION     // Catch:{ Exception -> 0x01c2 }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x01c2 }
            java.lang.String r5 = "Unknown"
            r2.put(r4, r5)     // Catch:{ Exception -> 0x01c2 }
        L_0x008a:
            boolean r4 = isYunOSSystem()     // Catch:{ Exception -> 0x01c2 }
            if (r4 == 0) goto L_0x00d8
            com.alibaba.analytics.core.model.LogField r4 = com.alibaba.analytics.core.model.LogField.OS     // Catch:{ Exception -> 0x01c2 }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x01c2 }
            java.lang.String r5 = "y"
            r2.put(r4, r5)     // Catch:{ Exception -> 0x01c2 }
            java.lang.String r4 = getUUID()     // Catch:{ Exception -> 0x01c2 }
            boolean r5 = com.alibaba.analytics.utils.StringUtils.isEmpty(r4)     // Catch:{ Exception -> 0x01c2 }
            if (r5 != 0) goto L_0x00af
            com.alibaba.analytics.core.model.UTMCLogFields r5 = com.alibaba.analytics.core.model.UTMCLogFields.DEVICE_ID     // Catch:{ Exception -> 0x01c2 }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x01c2 }
            r2.put(r5, r4)     // Catch:{ Exception -> 0x01c2 }
        L_0x00af:
            java.lang.String r4 = "ro.yunos.version"
            java.lang.String r4 = java.lang.System.getProperty(r4)     // Catch:{ Exception -> 0x01c2 }
            boolean r5 = com.alibaba.analytics.utils.StringUtils.isEmpty(r4)     // Catch:{ Exception -> 0x01c2 }
            if (r5 != 0) goto L_0x00c5
            com.alibaba.analytics.core.model.LogField r5 = com.alibaba.analytics.core.model.LogField.OSVERSION     // Catch:{ Exception -> 0x01c2 }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x01c2 }
            r2.put(r5, r4)     // Catch:{ Exception -> 0x01c2 }
        L_0x00c5:
            java.lang.String r4 = getBuildVersion()     // Catch:{ Exception -> 0x01c2 }
            boolean r5 = com.alibaba.analytics.utils.StringUtils.isEmpty(r4)     // Catch:{ Exception -> 0x01c2 }
            if (r5 != 0) goto L_0x00d8
            com.alibaba.analytics.core.model.LogField r5 = com.alibaba.analytics.core.model.LogField.OSVERSION     // Catch:{ Exception -> 0x01c2 }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x01c2 }
            r2.put(r5, r4)     // Catch:{ Exception -> 0x01c2 }
        L_0x00d8:
            boolean r4 = isYunOSTvSystem()     // Catch:{ Exception -> 0x01c2 }
            if (r4 == 0) goto L_0x00e9
            com.alibaba.analytics.core.model.LogField r4 = com.alibaba.analytics.core.model.LogField.OS     // Catch:{ Exception -> 0x01c2 }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x01c2 }
            java.lang.String r5 = "a"
            r2.put(r4, r5)     // Catch:{ Exception -> 0x01c2 }
        L_0x00e9:
            android.content.res.Configuration r4 = new android.content.res.Configuration     // Catch:{ Exception -> 0x015e }
            r4.<init>()     // Catch:{ Exception -> 0x015e }
            android.content.ContentResolver r5 = r8.getContentResolver()     // Catch:{ Exception -> 0x015e }
            android.provider.Settings.System.getConfiguration(r5, r4)     // Catch:{ Exception -> 0x015e }
            java.util.Locale r5 = r4.locale     // Catch:{ Exception -> 0x015e }
            if (r5 == 0) goto L_0x0109
            com.alibaba.analytics.core.model.LogField r5 = com.alibaba.analytics.core.model.LogField.LANGUAGE     // Catch:{ Exception -> 0x015e }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x015e }
            java.util.Locale r4 = r4.locale     // Catch:{ Exception -> 0x015e }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x015e }
            r2.put(r5, r4)     // Catch:{ Exception -> 0x015e }
            goto L_0x0114
        L_0x0109:
            com.alibaba.analytics.core.model.LogField r4 = com.alibaba.analytics.core.model.LogField.LANGUAGE     // Catch:{ Exception -> 0x015e }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x015e }
            java.lang.String r5 = "Unknown"
            r2.put(r4, r5)     // Catch:{ Exception -> 0x015e }
        L_0x0114:
            android.content.res.Resources r4 = r8.getResources()     // Catch:{ Exception -> 0x015e }
            android.util.DisplayMetrics r4 = r4.getDisplayMetrics()     // Catch:{ Exception -> 0x015e }
            int r5 = r4.widthPixels     // Catch:{ Exception -> 0x015e }
            int r4 = r4.heightPixels     // Catch:{ Exception -> 0x015e }
            if (r4 <= r5) goto L_0x0140
            com.alibaba.analytics.core.model.LogField r6 = com.alibaba.analytics.core.model.LogField.RESOLUTION     // Catch:{ Exception -> 0x015e }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x015e }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x015e }
            r7.<init>()     // Catch:{ Exception -> 0x015e }
            r7.append(r4)     // Catch:{ Exception -> 0x015e }
            java.lang.String r4 = "*"
            r7.append(r4)     // Catch:{ Exception -> 0x015e }
            r7.append(r5)     // Catch:{ Exception -> 0x015e }
            java.lang.String r4 = r7.toString()     // Catch:{ Exception -> 0x015e }
            r2.put(r6, r4)     // Catch:{ Exception -> 0x015e }
            goto L_0x0169
        L_0x0140:
            com.alibaba.analytics.core.model.LogField r6 = com.alibaba.analytics.core.model.LogField.RESOLUTION     // Catch:{ Exception -> 0x015e }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x015e }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x015e }
            r7.<init>()     // Catch:{ Exception -> 0x015e }
            r7.append(r5)     // Catch:{ Exception -> 0x015e }
            java.lang.String r5 = "*"
            r7.append(r5)     // Catch:{ Exception -> 0x015e }
            r7.append(r4)     // Catch:{ Exception -> 0x015e }
            java.lang.String r4 = r7.toString()     // Catch:{ Exception -> 0x015e }
            r2.put(r6, r4)     // Catch:{ Exception -> 0x015e }
            goto L_0x0169
        L_0x015e:
            com.alibaba.analytics.core.model.LogField r4 = com.alibaba.analytics.core.model.LogField.RESOLUTION     // Catch:{ Exception -> 0x01c2 }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x01c2 }
            java.lang.String r5 = "Unknown"
            r2.put(r4, r5)     // Catch:{ Exception -> 0x01c2 }
        L_0x0169:
            java.lang.String[] r8 = com.alibaba.analytics.core.network.NetworkUtil.getNetworkState(r8)     // Catch:{ Exception -> 0x019b }
            com.alibaba.analytics.core.model.LogField r4 = com.alibaba.analytics.core.model.LogField.ACCESS     // Catch:{ Exception -> 0x019b }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x019b }
            r5 = r8[r3]     // Catch:{ Exception -> 0x019b }
            r2.put(r4, r5)     // Catch:{ Exception -> 0x019b }
            r3 = r8[r3]     // Catch:{ Exception -> 0x019b }
            java.lang.String r4 = "2G/3G"
            boolean r3 = r3.equals(r4)     // Catch:{ Exception -> 0x019b }
            if (r3 == 0) goto L_0x018f
            com.alibaba.analytics.core.model.LogField r3 = com.alibaba.analytics.core.model.LogField.ACCESS_SUBTYPE     // Catch:{ Exception -> 0x019b }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x019b }
            r4 = 1
            r8 = r8[r4]     // Catch:{ Exception -> 0x019b }
            r2.put(r3, r8)     // Catch:{ Exception -> 0x019b }
            goto L_0x01b1
        L_0x018f:
            com.alibaba.analytics.core.model.LogField r8 = com.alibaba.analytics.core.model.LogField.ACCESS_SUBTYPE     // Catch:{ Exception -> 0x019b }
            java.lang.String r8 = r8.toString()     // Catch:{ Exception -> 0x019b }
            java.lang.String r3 = "Unknown"
            r2.put(r8, r3)     // Catch:{ Exception -> 0x019b }
            goto L_0x01b1
        L_0x019b:
            com.alibaba.analytics.core.model.LogField r8 = com.alibaba.analytics.core.model.LogField.ACCESS     // Catch:{ Exception -> 0x01c2 }
            java.lang.String r8 = r8.toString()     // Catch:{ Exception -> 0x01c2 }
            java.lang.String r3 = "Unknown"
            r2.put(r8, r3)     // Catch:{ Exception -> 0x01c2 }
            com.alibaba.analytics.core.model.LogField r8 = com.alibaba.analytics.core.model.LogField.ACCESS_SUBTYPE     // Catch:{ Exception -> 0x01c2 }
            java.lang.String r8 = r8.toString()     // Catch:{ Exception -> 0x01c2 }
            java.lang.String r3 = "Unknown"
            r2.put(r8, r3)     // Catch:{ Exception -> 0x01c2 }
        L_0x01b1:
            com.alibaba.analytics.core.model.LogField r8 = com.alibaba.analytics.core.model.LogField.CARRIER     // Catch:{ Exception -> 0x01c2 }
            java.lang.String r8 = r8.toString()     // Catch:{ Exception -> 0x01c2 }
            java.lang.String r3 = com.alibaba.analytics.core.network.NetworkOperatorUtil.getCurrentNetworkOperatorName()     // Catch:{ Exception -> 0x01c2 }
            r2.put(r8, r3)     // Catch:{ Exception -> 0x01c2 }
            s_deviceInfoMap = r2     // Catch:{ all -> 0x01c6 }
            monitor-exit(r0)
            return r2
        L_0x01c2:
            monitor-exit(r0)
            return r1
        L_0x01c4:
            monitor-exit(r0)
            return r1
        L_0x01c6:
            r8 = move-exception
            monitor-exit(r0)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.analytics.utils.UTMCDevice.getDeviceInfo(android.content.Context):java.util.Map");
    }

    private static boolean isYunOSSystem() {
        if ((System.getProperty("java.vm.name") == null || !System.getProperty("java.vm.name").toLowerCase().contains("lemur")) && TextUtils.isEmpty(System.getProperty("ro.yunos.version")) && TextUtils.isEmpty(SystemProperties.get("ro.yunos.build.version"))) {
            return isYunOSTvSystem();
        }
        return true;
    }

    private static boolean isYunOSTvSystem() {
        return !TextUtils.isEmpty(SystemProperties.get("ro.yunos.product.chip")) || !TextUtils.isEmpty(SystemProperties.get("ro.yunos.hardware"));
    }

    private static String getUUID() {
        String str = SystemProperties.get("ro.aliyun.clouduuid");
        if (StringUtils.isEmpty(str)) {
            str = SystemProperties.get("ro.sys.aliyun.clouduuid");
        }
        return StringUtils.isEmpty(str) ? getYunOSTVUuid() : str;
    }

    private static String getYunOSTVUuid() {
        try {
            return (String) Class.forName("com.yunos.baseservice.clouduuid.CloudUUID").getMethod("getCloudUUID", new Class[0]).invoke(null, new Object[0]);
        } catch (Exception unused) {
            return null;
        }
    }

    private static String getBuildVersion() {
        try {
            Field declaredField = Build.class.getDeclaredField("YUNOS_BUILD_VERSION");
            if (declaredField != null) {
                declaredField.setAccessible(true);
                return (String) declaredField.get(new String());
            }
        } catch (Throwable unused) {
        }
        return null;
    }

    public static boolean isPad(Context context) {
        if (context == null) {
            return false;
        }
        return isPadByPhoneType(context) || isPadByScrren(context);
    }

    private static boolean isPadByScrren(Context context) {
        try {
            return (context.getResources().getConfiguration().screenLayout & 15) >= 3;
        } catch (Throwable unused) {
            return false;
        }
    }

    private static boolean isPadByPhoneType(Context context) {
        try {
            if (((TelephonyManager) context.getSystemService("phone")).getPhoneType() == 0) {
                return true;
            }
            return false;
        } catch (Throwable unused) {
            return false;
        }
    }
}
