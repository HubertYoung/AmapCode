package com.alibaba.analytics.utils;

import android.os.Build.VERSION;
import android.os.Environment;
import android.os.StatFs;

public class SystemUtils {
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0044  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0055  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getCpuInfo() {
        /*
            r0 = 0
            java.io.FileReader r1 = new java.io.FileReader     // Catch:{ Exception -> 0x0032 }
            java.lang.String r2 = "/proc/cpuinfo"
            r1.<init>(r2)     // Catch:{ Exception -> 0x0032 }
            java.io.BufferedReader r2 = new java.io.BufferedReader     // Catch:{ IOException -> 0x0021 }
            r3 = 1024(0x400, float:1.435E-42)
            r2.<init>(r1, r3)     // Catch:{ IOException -> 0x0021 }
            java.lang.String r3 = r2.readLine()     // Catch:{ IOException -> 0x0021 }
            r2.close()     // Catch:{ IOException -> 0x001d, Exception -> 0x001a }
            r1.close()     // Catch:{ IOException -> 0x001d, Exception -> 0x001a }
            goto L_0x0042
        L_0x001a:
            r0 = move-exception
            r1 = r0
            goto L_0x0034
        L_0x001d:
            r0 = move-exception
            r1 = r0
            r0 = r3
            goto L_0x0022
        L_0x0021:
            r1 = move-exception
        L_0x0022:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0032 }
            java.lang.String r3 = "Could not read from file /proc/cpuinfo :"
            r2.<init>(r3)     // Catch:{ Exception -> 0x0032 }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x0032 }
            r2.append(r1)     // Catch:{ Exception -> 0x0032 }
            r3 = r0
            goto L_0x0042
        L_0x0032:
            r1 = move-exception
            r3 = r0
        L_0x0034:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r2 = "BaseParameter-Could not open file /proc/cpuinfo :"
            r0.<init>(r2)
            java.lang.String r1 = r1.toString()
            r0.append(r1)
        L_0x0042:
            if (r3 == 0) goto L_0x0055
            r0 = 58
            int r0 = r3.indexOf(r0)
            int r0 = r0 + 1
            java.lang.String r0 = r3.substring(r0)
            java.lang.String r0 = r0.trim()
            return r0
        L_0x0055:
            java.lang.String r0 = ""
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.analytics.utils.SystemUtils.getCpuInfo():java.lang.String");
    }

    public static double getSystemFreeSize() {
        StatFs statFs = new StatFs(Environment.getRootDirectory().getPath());
        if (VERSION.SDK_INT >= 18) {
            return (((double) statFs.getAvailableBytes()) / 1024.0d) / 1024.0d;
        }
        return (((double) statFs.getFreeBytes()) / 1024.0d) / 1024.0d;
    }
}
