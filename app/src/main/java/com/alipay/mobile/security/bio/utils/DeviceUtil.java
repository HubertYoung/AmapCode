package com.alipay.mobile.security.bio.utils;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.StatFs;
import java.io.File;

public class DeviceUtil {
    public static int getProcessorNum() {
        try {
            return new File("/sys/devices/system/cpu/").listFiles(new a()).length;
        } catch (Throwable th) {
            return 0;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x0032 A[SYNTHETIC, Splitter:B:18:0x0032] */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0037 A[SYNTHETIC, Splitter:B:21:0x0037] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0042 A[SYNTHETIC, Splitter:B:27:0x0042] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0047 A[SYNTHETIC, Splitter:B:30:0x0047] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static long getMemorySize() {
        /*
            r3 = 0
            java.lang.String r4 = "/proc/meminfo"
            r0 = 0
            java.io.FileReader r2 = new java.io.FileReader     // Catch:{ Throwable -> 0x002e, all -> 0x003d }
            r2.<init>(r4)     // Catch:{ Throwable -> 0x002e, all -> 0x003d }
            java.io.BufferedReader r4 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x005a, all -> 0x0055 }
            r5 = 8192(0x2000, float:1.14794E-41)
            r4.<init>(r2, r5)     // Catch:{ Throwable -> 0x005a, all -> 0x0055 }
            java.lang.String r3 = r4.readLine()     // Catch:{ Throwable -> 0x005c, all -> 0x0058 }
            if (r3 == 0) goto L_0x0027
            java.lang.String r5 = "\\s+"
            java.lang.String[] r3 = r3.split(r5)     // Catch:{ Throwable -> 0x005c, all -> 0x0058 }
            r5 = 1
            r3 = r3[r5]     // Catch:{ Throwable -> 0x005c, all -> 0x0058 }
            long r0 = java.lang.Long.parseLong(r3)     // Catch:{ Throwable -> 0x005c, all -> 0x0058 }
            r5 = 1024(0x400, double:5.06E-321)
            long r0 = r0 * r5
        L_0x0027:
            r2.close()     // Catch:{ Throwable -> 0x004b }
        L_0x002a:
            r4.close()     // Catch:{ Throwable -> 0x004d }
        L_0x002d:
            return r0
        L_0x002e:
            r2 = move-exception
            r2 = r3
        L_0x0030:
            if (r2 == 0) goto L_0x0035
            r2.close()     // Catch:{ Throwable -> 0x004f }
        L_0x0035:
            if (r3 == 0) goto L_0x002d
            r3.close()     // Catch:{ Throwable -> 0x003b }
            goto L_0x002d
        L_0x003b:
            r2 = move-exception
            goto L_0x002d
        L_0x003d:
            r0 = move-exception
            r2 = r3
            r4 = r3
        L_0x0040:
            if (r2 == 0) goto L_0x0045
            r2.close()     // Catch:{ Throwable -> 0x0051 }
        L_0x0045:
            if (r4 == 0) goto L_0x004a
            r4.close()     // Catch:{ Throwable -> 0x0053 }
        L_0x004a:
            throw r0
        L_0x004b:
            r2 = move-exception
            goto L_0x002a
        L_0x004d:
            r2 = move-exception
            goto L_0x002d
        L_0x004f:
            r2 = move-exception
            goto L_0x0035
        L_0x0051:
            r1 = move-exception
            goto L_0x0045
        L_0x0053:
            r1 = move-exception
            goto L_0x004a
        L_0x0055:
            r0 = move-exception
            r4 = r3
            goto L_0x0040
        L_0x0058:
            r0 = move-exception
            goto L_0x0040
        L_0x005a:
            r4 = move-exception
            goto L_0x0030
        L_0x005c:
            r3 = move-exception
            r3 = r4
            goto L_0x0030
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.security.bio.utils.DeviceUtil.getMemorySize():long");
    }

    public static long getTotalStorageSize(File file) {
        long j = 0;
        try {
            StatFs statFs = new StatFs(file.getPath());
            return ((long) statFs.getBlockCount()) * ((long) statFs.getBlockSize());
        } catch (Throwable th) {
            return j;
        }
    }

    public static long getAvailableStorageSize(File file) {
        long j = 0;
        try {
            StatFs statFs = new StatFs(file.getPath());
            return ((long) statFs.getAvailableBlocks()) * ((long) statFs.getBlockSize());
        } catch (Throwable th) {
            return j;
        }
    }

    public static boolean isDebug(Context context) {
        try {
            if ((context.getPackageManager().getApplicationInfo(context.getPackageName(), 16384).flags & 2) != 0) {
                return true;
            }
        } catch (Throwable th) {
        }
        return false;
    }

    public static String getVersionName(Context context) {
        if (context == null) {
            return "";
        }
        String str = "";
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            return str;
        }
    }

    public static String getUtdid(Context context) {
        try {
            return (String) Class.forName("com.ut.device.UTDevice").getMethod("getUtdid", new Class[]{Context.class}).invoke(null, new Object[]{context});
        } catch (Exception e) {
            r0 = "";
            return "";
        }
    }
}
