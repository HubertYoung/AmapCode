package com.alibaba.wireless.security.framework.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Build.VERSION;
import android.os.Process;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import java.lang.reflect.Method;

public class f {
    private static String[] a = {"armeabi", "armeabi-v7a", "x86", "arm64-v8a", "x86_64"};
    private static boolean b = true;
    private static boolean c = false;
    private static boolean d = true;
    private static boolean e = false;
    private static boolean f = true;
    private static boolean g = false;

    public static String a(ClassLoader classLoader, String str) {
        String str2;
        if (classLoader == null || str == null || "".equals(str)) {
            str2 = null;
        } else {
            str2 = a(classLoader, str, true);
            if (str2 == null) {
                return a(classLoader, str, false);
            }
        }
        return str2;
    }

    private static String a(ClassLoader classLoader, String str, boolean z) {
        Method method;
        if (classLoader == null) {
            return null;
        }
        if (z) {
            try {
                method = classLoader.getClass().getMethod("findLibrary", new Class[]{String.class});
            } catch (Exception unused) {
                return null;
            }
        } else {
            method = classLoader.getClass().getDeclaredMethod("findLibrary", new Class[]{String.class});
        }
        if (method == null) {
            return null;
        }
        if (!method.isAccessible()) {
            method.setAccessible(true);
        }
        Object invoke = method.invoke(classLoader, new Object[]{str});
        if (invoke == null || !(invoke instanceof String)) {
            return null;
        }
        return (String) invoke;
    }

    public static boolean a() {
        return VERSION.SDK_INT >= 24;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0021, code lost:
        if ((r3.applicationInfo.flags & 128) == 0) goto L_0x0025;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean a(android.content.Context r3) {
        /*
            boolean r0 = b
            if (r0 == 0) goto L_0x0029
            android.content.pm.PackageManager r0 = r3.getPackageManager()
            r1 = 0
            java.lang.String r3 = r3.getPackageName()     // Catch:{ Throwable -> 0x0024 }
            android.content.pm.PackageInfo r3 = r0.getPackageInfo(r3, r1)     // Catch:{ Throwable -> 0x0024 }
            r0 = 1
            if (r3 == 0) goto L_0x0024
            android.content.pm.ApplicationInfo r2 = r3.applicationInfo     // Catch:{ Throwable -> 0x0024 }
            int r2 = r2.flags     // Catch:{ Throwable -> 0x0024 }
            r2 = r2 & r0
            if (r2 == 0) goto L_0x0024
            android.content.pm.ApplicationInfo r3 = r3.applicationInfo     // Catch:{ Throwable -> 0x0024 }
            int r3 = r3.flags     // Catch:{ Throwable -> 0x0024 }
            r3 = r3 & 128(0x80, float:1.794E-43)
            if (r3 != 0) goto L_0x0024
            goto L_0x0025
        L_0x0024:
            r0 = 0
        L_0x0025:
            c = r0
            b = r1
        L_0x0029:
            boolean r3 = c
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.wireless.security.framework.utils.f.a(android.content.Context):boolean");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0042, code lost:
        r9 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0043, code lost:
        r1 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0049, code lost:
        r9 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:?, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:?, code lost:
        r2.close();
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0049 A[ExcHandler: all (th java.lang.Throwable), Splitter:B:3:0x0007] */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x005a A[SYNTHETIC, Splitter:B:31:0x005a] */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x0060 A[SYNTHETIC, Splitter:B:36:0x0060] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean a(java.lang.String r9, java.lang.String r10, java.io.File r11) {
        /*
            r0 = 0
            r1 = 0
            java.util.zip.ZipFile r2 = new java.util.zip.ZipFile     // Catch:{ IOException -> 0x0051 }
            r2.<init>(r9)     // Catch:{ IOException -> 0x0051 }
            java.lang.String[] r9 = a     // Catch:{ IOException -> 0x004b, all -> 0x0049 }
            int r1 = r9.length     // Catch:{ IOException -> 0x004b, all -> 0x0049 }
            r3 = 0
        L_0x000b:
            if (r0 >= r1) goto L_0x0045
            r4 = r9[r0]     // Catch:{ IOException -> 0x0042, all -> 0x0049 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0042, all -> 0x0049 }
            java.lang.String r6 = "lib"
            r5.<init>(r6)     // Catch:{ IOException -> 0x0042, all -> 0x0049 }
            java.lang.String r6 = java.io.File.separator     // Catch:{ IOException -> 0x0042, all -> 0x0049 }
            r5.append(r6)     // Catch:{ IOException -> 0x0042, all -> 0x0049 }
            r5.append(r4)     // Catch:{ IOException -> 0x0042, all -> 0x0049 }
            java.lang.String r4 = java.io.File.separator     // Catch:{ IOException -> 0x0042, all -> 0x0049 }
            r5.append(r4)     // Catch:{ IOException -> 0x0042, all -> 0x0049 }
            r5.append(r10)     // Catch:{ IOException -> 0x0042, all -> 0x0049 }
            java.lang.String r4 = r5.toString()     // Catch:{ IOException -> 0x0042, all -> 0x0049 }
            java.util.zip.ZipEntry r4 = r2.getEntry(r4)     // Catch:{ IOException -> 0x0042, all -> 0x0049 }
            if (r4 == 0) goto L_0x003f
            long r5 = r4.getSize()     // Catch:{ IOException -> 0x0042, all -> 0x0049 }
            r7 = 0
            int r5 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r5 == 0) goto L_0x003f
            boolean r4 = a(r2, r4, r11)     // Catch:{ IOException -> 0x0042, all -> 0x0049 }
            r3 = r4
        L_0x003f:
            int r0 = r0 + 1
            goto L_0x000b
        L_0x0042:
            r9 = move-exception
            r1 = r2
            goto L_0x0053
        L_0x0045:
            r2.close()     // Catch:{ IOException -> 0x005d }
            return r3
        L_0x0049:
            r9 = move-exception
            goto L_0x005e
        L_0x004b:
            r9 = move-exception
            r1 = r2
            goto L_0x0052
        L_0x004e:
            r9 = move-exception
            r2 = r1
            goto L_0x005e
        L_0x0051:
            r9 = move-exception
        L_0x0052:
            r3 = 0
        L_0x0053:
            java.lang.String r10 = ""
            com.alibaba.wireless.security.framework.utils.a.a(r10, r9)     // Catch:{ all -> 0x004e }
            if (r1 == 0) goto L_0x005d
            r1.close()     // Catch:{ IOException -> 0x005d }
        L_0x005d:
            return r3
        L_0x005e:
            if (r2 == 0) goto L_0x0063
            r2.close()     // Catch:{ IOException -> 0x0063 }
        L_0x0063:
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.wireless.security.framework.utils.f.a(java.lang.String, java.lang.String, java.io.File):boolean");
    }

    /* JADX WARNING: Removed duplicated region for block: B:52:0x009a A[SYNTHETIC, Splitter:B:52:0x009a] */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x009f A[SYNTHETIC, Splitter:B:56:0x009f] */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x00aa A[SYNTHETIC, Splitter:B:65:0x00aa] */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x00af A[SYNTHETIC, Splitter:B:69:0x00af] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean a(java.util.zip.ZipFile r8, java.util.zip.ZipEntry r9, java.io.File r10) {
        /*
            r0 = 0
            if (r8 == 0) goto L_0x00d4
            if (r9 == 0) goto L_0x00d4
            if (r10 != 0) goto L_0x0008
            return r0
        L_0x0008:
            r1 = 1
            r2 = 0
            java.io.File r3 = new java.io.File     // Catch:{ Exception -> 0x00a6, all -> 0x0095 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00a6, all -> 0x0095 }
            r4.<init>()     // Catch:{ Exception -> 0x00a6, all -> 0x0095 }
            java.lang.String r5 = r10.getAbsolutePath()     // Catch:{ Exception -> 0x00a6, all -> 0x0095 }
            r4.append(r5)     // Catch:{ Exception -> 0x00a6, all -> 0x0095 }
            java.lang.String r5 = ".tmp."
            r4.append(r5)     // Catch:{ Exception -> 0x00a6, all -> 0x0095 }
            int r5 = android.os.Process.myPid()     // Catch:{ Exception -> 0x00a6, all -> 0x0095 }
            r4.append(r5)     // Catch:{ Exception -> 0x00a6, all -> 0x0095 }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x00a6, all -> 0x0095 }
            r3.<init>(r4)     // Catch:{ Exception -> 0x00a6, all -> 0x0095 }
            boolean r4 = r3.exists()     // Catch:{ Exception -> 0x0093, all -> 0x0090 }
            if (r4 == 0) goto L_0x0034
            r3.delete()     // Catch:{ Exception -> 0x0093, all -> 0x0090 }
        L_0x0034:
            java.io.InputStream r8 = r8.getInputStream(r9)     // Catch:{ Exception -> 0x0093, all -> 0x0090 }
            java.io.FileOutputStream r4 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x008d, all -> 0x0088 }
            r4.<init>(r3)     // Catch:{ Exception -> 0x008d, all -> 0x0088 }
            r5 = 8192(0x2000, float:1.14794E-41)
            byte[] r5 = new byte[r5]     // Catch:{ Exception -> 0x008e, all -> 0x0086 }
        L_0x0041:
            int r6 = r8.read(r5)     // Catch:{ Exception -> 0x008e, all -> 0x0086 }
            r7 = -1
            if (r6 == r7) goto L_0x004c
            r4.write(r5, r0, r6)     // Catch:{ Exception -> 0x008e, all -> 0x0086 }
            goto L_0x0041
        L_0x004c:
            r8.close()     // Catch:{ Exception -> 0x008e, all -> 0x0086 }
            r4.flush()     // Catch:{ Exception -> 0x00a8, all -> 0x0084 }
            r4.close()     // Catch:{ Exception -> 0x00a8, all -> 0x0084 }
            boolean r8 = r10.exists()     // Catch:{ Exception -> 0x0093, all -> 0x0090 }
            if (r8 == 0) goto L_0x0069
            long r4 = r10.length()     // Catch:{ Exception -> 0x0093, all -> 0x0090 }
            long r6 = r9.getSize()     // Catch:{ Exception -> 0x0093, all -> 0x0090 }
            int r8 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r8 != 0) goto L_0x0069
            r0 = 1
            goto L_0x00b2
        L_0x0069:
            boolean r8 = r3.exists()     // Catch:{ Exception -> 0x0093, all -> 0x0090 }
            if (r8 == 0) goto L_0x00b2
            long r4 = r3.length()     // Catch:{ Exception -> 0x0093, all -> 0x0090 }
            long r6 = r9.getSize()     // Catch:{ Exception -> 0x0093, all -> 0x0090 }
            int r8 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r8 != 0) goto L_0x00b2
            r10.delete()     // Catch:{ Exception -> 0x0093, all -> 0x0090 }
            boolean r8 = r3.renameTo(r10)     // Catch:{ Exception -> 0x0093, all -> 0x0090 }
            r0 = r8
            goto L_0x00b2
        L_0x0084:
            r8 = move-exception
            goto L_0x0098
        L_0x0086:
            r9 = move-exception
            goto L_0x008a
        L_0x0088:
            r9 = move-exception
            r4 = r2
        L_0x008a:
            r2 = r8
            r8 = r9
            goto L_0x0098
        L_0x008d:
            r4 = r2
        L_0x008e:
            r2 = r8
            goto L_0x00a8
        L_0x0090:
            r8 = move-exception
            r4 = r2
            goto L_0x0098
        L_0x0093:
            r4 = r2
            goto L_0x00a8
        L_0x0095:
            r8 = move-exception
            r3 = r2
            r4 = r3
        L_0x0098:
            if (r2 == 0) goto L_0x009d
            r2.close()     // Catch:{ Exception -> 0x009d }
        L_0x009d:
            if (r4 == 0) goto L_0x00a2
            r4.close()     // Catch:{ Exception -> 0x00a2 }
        L_0x00a2:
            r3.delete()
            throw r8
        L_0x00a6:
            r3 = r2
            r4 = r3
        L_0x00a8:
            if (r2 == 0) goto L_0x00ad
            r2.close()     // Catch:{ Exception -> 0x00ad }
        L_0x00ad:
            if (r4 == 0) goto L_0x00b2
            r4.close()     // Catch:{ Exception -> 0x00b2 }
        L_0x00b2:
            r3.delete()
            if (r0 != 0) goto L_0x00d4
            boolean r8 = r10.exists()
            if (r8 == 0) goto L_0x00d4
            long r2 = r10.length()
            r4 = 0
            int r8 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r8 <= 0) goto L_0x00d4
            long r2 = r10.length()
            long r8 = r9.getSize()
            int r8 = (r2 > r8 ? 1 : (r2 == r8 ? 0 : -1))
            if (r8 != 0) goto L_0x00d4
            r0 = 1
        L_0x00d4:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.wireless.security.framework.utils.f.a(java.util.zip.ZipFile, java.util.zip.ZipEntry, java.io.File):boolean");
    }

    public static boolean b(Context context) {
        boolean z;
        if (d) {
            try {
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                if (!(packageInfo == null || (packageInfo.applicationInfo.flags & 128) == 0)) {
                    z = true;
                    e = z;
                    d = false;
                }
            } catch (Throwable unused) {
            }
            z = false;
            e = z;
            d = false;
        }
        return e;
    }

    public static boolean c(Context context) {
        if (f) {
            try {
                String d2 = d(context);
                String packageName = context.getPackageName();
                if ("com.ali.money.shield".equals(packageName)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(packageName);
                    sb.append(":fore");
                    packageName = sb.toString();
                }
                g = d2.equals(packageName);
                f = false;
            } catch (Exception unused) {
            }
        }
        return g;
    }

    public static String d(Context context) {
        try {
            int myPid = Process.myPid();
            if (context == null) {
                return "";
            }
            for (RunningAppProcessInfo next : ((ActivityManager) context.getSystemService(WidgetType.ACTIVITY)).getRunningAppProcesses()) {
                if (next.pid == myPid) {
                    return next.processName != null ? next.processName : "";
                }
            }
            return "";
        } catch (Throwable unused) {
            return "";
        }
    }
}
