package com.uc.webview.export.internal.utility;

import android.content.Context;
import android.os.Build.VERSION;
import android.util.Pair;
import com.uc.webview.export.Build;
import com.uc.webview.export.internal.SDKFactory;
import com.uc.webview.export.internal.interfaces.IGlobalSettings;
import com.uc.webview.export.internal.setup.UCMPackageInfo;
import com.uc.webview.export.internal.setup.UCMRunningInfo;
import com.uc.webview.export.internal.setup.UCSetupException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: ProGuard */
public final class j {
    public static int a = 5000;
    public static int b = 5000;
    public static boolean c = false;
    public static String d = "3032";
    private static final Long e = Long.valueOf(10000);
    private static final HashMap<String, Pair<Long, Object>> f = new HashMap<>();
    private static final Map<String, String> g = new k();
    private static final String[] h = {d};
    private static boolean i = false;
    private static boolean j = false;

    /* compiled from: ProGuard */
    public static class a {
        private static String a;

        public static String a(Context context) {
            long currentTimeMillis;
            String str;
            if (!j.a(a)) {
                return a;
            }
            if (SDKFactory.c == null || context == null) {
                return null;
            }
            try {
                currentTimeMillis = System.currentTimeMillis();
                str = (String) ReflectionUtil.invoke(Class.forName("com.ta.utdid2.device.UTDevice"), (String) "getUtdid", new Class[]{Context.class}, new Object[]{context});
            } catch (Throwable th) {
                Log.d("Utils", "getUtdidBySdk cd exception : ".concat(String.valueOf(th)));
                return null;
            }
            StringBuilder sb = new StringBuilder("getUtdidBySdk time: ");
            sb.append(System.currentTimeMillis() - currentTimeMillis);
            sb.append(" milliseconds");
            Log.d("Utils", sb.toString());
            Log.d("Utils", "getUtdidBySdk utdid: ".concat(String.valueOf(str)));
            if (!j.a(str)) {
                a = str;
            }
            return str;
        }

        public static void a(String str) {
            IGlobalSettings iGlobalSettings = (IGlobalSettings) SDKFactory.invoke(10022, new Object[0]);
            StringBuilder sb = new StringBuilder("setUtdid2Core utdid: ");
            sb.append(str);
            sb.append(", settings: ");
            sb.append(iGlobalSettings);
            Log.d("Utils", sb.toString());
            if (iGlobalSettings != null) {
                iGlobalSettings.setStringValue("UBIUtdId", str);
            }
        }
    }

    public static boolean a(Object obj) {
        return obj == null;
    }

    public static boolean a(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean b(String str) {
        return !a(str) && "true".equalsIgnoreCase(str);
    }

    public static boolean a(Boolean bool) {
        return bool != null && bool.booleanValue();
    }

    public static boolean b(Boolean bool) {
        return bool == null || !bool.booleanValue();
    }

    public static boolean c(Boolean bool) {
        return bool != null && !bool.booleanValue();
    }

    public static String a() {
        try {
            File[] listFiles = new File("/sys/devices/system/cpu/").listFiles(new l());
            StringBuilder sb = new StringBuilder("CPU Count: ");
            sb.append(listFiles.length);
            Log.d("Utils", sb.toString());
            return String.valueOf(listFiles.length);
        } catch (Throwable unused) {
            Log.d("Utils", "CPU Count: Failed.");
            return "1";
        }
    }

    public static String b() {
        return String.valueOf(c(e("/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq").trim()));
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x0034  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0039  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0040  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0045  */
    /* JADX WARNING: Removed duplicated region for block: B:34:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String e(java.lang.String r4) {
        /*
            java.lang.String r0 = ""
            r1 = 0
            java.io.FileReader r2 = new java.io.FileReader     // Catch:{ Throwable -> 0x003d, all -> 0x002f }
            r2.<init>(r4)     // Catch:{ Throwable -> 0x003d, all -> 0x002f }
            java.io.BufferedReader r4 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x003e, all -> 0x002c }
            r4.<init>(r2)     // Catch:{ Throwable -> 0x003e, all -> 0x002c }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x002a, all -> 0x0028 }
            r1.<init>()     // Catch:{ Throwable -> 0x002a, all -> 0x0028 }
        L_0x0012:
            java.lang.String r3 = r4.readLine()     // Catch:{ Throwable -> 0x002a, all -> 0x0028 }
            if (r3 == 0) goto L_0x001c
            r1.append(r3)     // Catch:{ Throwable -> 0x002a, all -> 0x0028 }
            goto L_0x0012
        L_0x001c:
            java.lang.String r1 = r1.toString()     // Catch:{ Throwable -> 0x002a, all -> 0x0028 }
            com.uc.webview.export.cyclone.UCCyclone.close(r2)
            com.uc.webview.export.cyclone.UCCyclone.close(r4)
            r0 = r1
            goto L_0x0048
        L_0x0028:
            r0 = move-exception
            goto L_0x0032
        L_0x002a:
            r1 = r4
            goto L_0x003e
        L_0x002c:
            r0 = move-exception
            r4 = r1
            goto L_0x0032
        L_0x002f:
            r0 = move-exception
            r4 = r1
            r2 = r4
        L_0x0032:
            if (r2 == 0) goto L_0x0037
            com.uc.webview.export.cyclone.UCCyclone.close(r2)
        L_0x0037:
            if (r4 == 0) goto L_0x003c
            com.uc.webview.export.cyclone.UCCyclone.close(r4)
        L_0x003c:
            throw r0
        L_0x003d:
            r2 = r1
        L_0x003e:
            if (r2 == 0) goto L_0x0043
            com.uc.webview.export.cyclone.UCCyclone.close(r2)
        L_0x0043:
            if (r1 == 0) goto L_0x0048
            com.uc.webview.export.cyclone.UCCyclone.close(r1)
        L_0x0048:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.webview.export.internal.utility.j.e(java.lang.String):java.lang.String");
    }

    /* JADX WARNING: Removed duplicated region for block: B:27:0x0049 A[SYNTHETIC, Splitter:B:27:0x0049] */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x004e A[SYNTHETIC, Splitter:B:31:0x004e] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void a(java.io.File r9, java.io.File r10) throws java.io.IOException {
        /*
            r0 = 0
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch:{ all -> 0x0044 }
            r1.<init>(r9)     // Catch:{ all -> 0x0044 }
            java.nio.channels.FileChannel r1 = r1.getChannel()     // Catch:{ all -> 0x0044 }
            java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch:{ all -> 0x0041 }
            r2.<init>(r10)     // Catch:{ all -> 0x0041 }
            java.nio.channels.FileChannel r8 = r2.getChannel()     // Catch:{ all -> 0x0041 }
            r4 = 0
            long r6 = r1.size()     // Catch:{ all -> 0x003f }
            r2 = r8
            r3 = r1
            long r2 = r2.transferFrom(r3, r4, r6)     // Catch:{ all -> 0x003f }
            long r4 = r9.length()     // Catch:{ all -> 0x003f }
            int r9 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r9 == 0) goto L_0x0032
            r10.delete()     // Catch:{ all -> 0x003f }
            java.lang.RuntimeException r9 = new java.lang.RuntimeException     // Catch:{ all -> 0x003f }
            java.lang.String r10 = "Size mismatch."
            r9.<init>(r10)     // Catch:{ all -> 0x003f }
            throw r9     // Catch:{ all -> 0x003f }
        L_0x0032:
            if (r1 == 0) goto L_0x0037
            r1.close()     // Catch:{ Throwable -> 0x0037 }
        L_0x0037:
            if (r8 == 0) goto L_0x003e
            r8.close()     // Catch:{ Throwable -> 0x003d }
            goto L_0x003e
        L_0x003d:
            return
        L_0x003e:
            return
        L_0x003f:
            r9 = move-exception
            goto L_0x0047
        L_0x0041:
            r9 = move-exception
            r8 = r0
            goto L_0x0047
        L_0x0044:
            r9 = move-exception
            r1 = r0
            r8 = r1
        L_0x0047:
            if (r1 == 0) goto L_0x004c
            r1.close()     // Catch:{ Throwable -> 0x004c }
        L_0x004c:
            if (r8 == 0) goto L_0x0051
            r8.close()     // Catch:{ Throwable -> 0x0051 }
        L_0x0051:
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.webview.export.internal.utility.j.a(java.io.File, java.io.File):void");
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(10:23|24|(3:26|27|28)|29|30|(3:31|33|(3:35|36|(1:38)(2:39|40)))|41|42|(1:44)|45) */
    /* JADX WARNING: Can't wrap try/catch for region: R(6:11|(3:13|(2:15|(1:17))|18)|(3:20|21|22)(10:23|24|(3:26|27|28)|29|30|(3:31|33|(3:35|36|(1:38)(2:39|40)))|41|42|(1:44)|45)|46|47|(2:49|50)(2:51|(2:53|54)(1:55))) */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x013e, code lost:
        r6 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x0146, code lost:
        throw new com.uc.webview.export.internal.setup.UCSetupException(1007, r6);
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:29:0x0086 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:46:0x00e3 */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x00bb A[SYNTHETIC, Splitter:B:35:0x00bb] */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x00d4 A[Catch:{ Throwable -> 0x00e3 }] */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x010c A[Catch:{ Throwable -> 0x013e }] */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x0126 A[Catch:{ Throwable -> 0x013e }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.io.File a(java.io.File r6, java.io.File r7, java.io.File r8, boolean r9) {
        /*
            boolean r0 = r7.exists()
            if (r0 == 0) goto L_0x0009
            if (r9 != 0) goto L_0x0009
            return r7
        L_0x0009:
            boolean r0 = r8.exists()
            if (r0 == 0) goto L_0x0028
            long r0 = r6.length()
            long r2 = r8.length()
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 != 0) goto L_0x0028
            long r0 = r6.lastModified()
            long r2 = r8.lastModified()
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 != 0) goto L_0x0028
            return r8
        L_0x0028:
            java.io.File r0 = new java.io.File
            java.lang.String r1 = r8.getParent()
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "bak_"
            r2.<init>(r3)
            java.lang.String r3 = r8.getName()
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r0.<init>(r1, r2)
            boolean r1 = r0.exists()
            if (r1 == 0) goto L_0x006c
            long r1 = r6.length()
            long r3 = r0.length()
            int r1 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r1 != 0) goto L_0x0069
            long r1 = r6.lastModified()
            r0.setLastModified(r1)
            long r1 = r0.lastModified()
            long r3 = r6.lastModified()
            int r1 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r1 == 0) goto L_0x0069
            return r0
        L_0x0069:
            r0.delete()
        L_0x006c:
            if (r9 == 0) goto L_0x0074
            java.lang.Throwable r7 = new java.lang.Throwable     // Catch:{ Throwable -> 0x00e3 }
            r7.<init>()     // Catch:{ Throwable -> 0x00e3 }
            throw r7     // Catch:{ Throwable -> 0x00e3 }
        L_0x0074:
            int r9 = android.os.Build.VERSION.SDK_INT     // Catch:{ Throwable -> 0x00e3 }
            r1 = 21
            if (r9 < r1) goto L_0x0086
            java.lang.String r9 = r6.getAbsolutePath()     // Catch:{ Exception -> 0x0086 }
            java.lang.String r1 = r7.getAbsolutePath()     // Catch:{ Exception -> 0x0086 }
            android.system.Os.symlink(r9, r1)     // Catch:{ Exception -> 0x0086 }
            return r7
        L_0x0086:
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00e3 }
            java.lang.String r1 = "ln -s "
            r9.<init>(r1)     // Catch:{ Throwable -> 0x00e3 }
            java.lang.String r1 = r6.getAbsolutePath()     // Catch:{ Throwable -> 0x00e3 }
            r9.append(r1)     // Catch:{ Throwable -> 0x00e3 }
            java.lang.String r1 = " "
            r9.append(r1)     // Catch:{ Throwable -> 0x00e3 }
            java.lang.String r1 = r7.getAbsolutePath()     // Catch:{ Throwable -> 0x00e3 }
            r9.append(r1)     // Catch:{ Throwable -> 0x00e3 }
            java.lang.String r9 = r9.toString()     // Catch:{ Throwable -> 0x00e3 }
            java.lang.Runtime r1 = java.lang.Runtime.getRuntime()     // Catch:{ Throwable -> 0x00e3 }
            java.lang.Process r9 = r1.exec(r9)     // Catch:{ Throwable -> 0x00e3 }
            com.uc.webview.export.cyclone.UCElapseTime r1 = new com.uc.webview.export.cyclone.UCElapseTime     // Catch:{ Throwable -> 0x00e3 }
            r1.<init>()     // Catch:{ Throwable -> 0x00e3 }
        L_0x00b1:
            long r2 = r1.getMilis()     // Catch:{ Throwable -> 0x00e3 }
            r4 = 500(0x1f4, double:2.47E-321)
            int r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r2 >= 0) goto L_0x00c8
            int r2 = r9.exitValue()     // Catch:{ IllegalThreadStateException -> 0x00b1 }
            if (r2 != 0) goto L_0x00c2
            goto L_0x00c8
        L_0x00c2:
            java.lang.Throwable r2 = new java.lang.Throwable     // Catch:{ IllegalThreadStateException -> 0x00b1 }
            r2.<init>()     // Catch:{ IllegalThreadStateException -> 0x00b1 }
            throw r2     // Catch:{ IllegalThreadStateException -> 0x00b1 }
        L_0x00c8:
            java.lang.String r9 = r6.getName()     // Catch:{ Throwable -> 0x00e3 }
            java.lang.String r2 = "libar_pak_kr_uc.so"
            boolean r9 = r9.equals(r2)     // Catch:{ Throwable -> 0x00e3 }
            if (r9 == 0) goto L_0x00e2
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00e3 }
            java.lang.String r2 = "linkOrCopyFile Time:"
            r9.<init>(r2)     // Catch:{ Throwable -> 0x00e3 }
            long r1 = r1.getMilis()     // Catch:{ Throwable -> 0x00e3 }
            r9.append(r1)     // Catch:{ Throwable -> 0x00e3 }
        L_0x00e2:
            return r7
        L_0x00e3:
            r8.delete()     // Catch:{ Throwable -> 0x013e }
            java.io.File r7 = new java.io.File     // Catch:{ Throwable -> 0x013e }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x013e }
            r9.<init>()     // Catch:{ Throwable -> 0x013e }
            java.lang.String r1 = r8.getAbsolutePath()     // Catch:{ Throwable -> 0x013e }
            r9.append(r1)     // Catch:{ Throwable -> 0x013e }
            java.lang.String r1 = ".tmp"
            r9.append(r1)     // Catch:{ Throwable -> 0x013e }
            java.lang.String r9 = r9.toString()     // Catch:{ Throwable -> 0x013e }
            r7.<init>(r9)     // Catch:{ Throwable -> 0x013e }
            r7.createNewFile()     // Catch:{ Throwable -> 0x013e }
            a(r6, r7)     // Catch:{ Throwable -> 0x013e }
            boolean r9 = r7.renameTo(r8)     // Catch:{ Throwable -> 0x013e }
            if (r9 != 0) goto L_0x0126
            r7.delete()     // Catch:{ Throwable -> 0x013e }
            com.uc.webview.export.internal.setup.UCSetupException r6 = new com.uc.webview.export.internal.setup.UCSetupException     // Catch:{ Throwable -> 0x013e }
            r9 = 1005(0x3ed, float:1.408E-42)
            java.lang.String r0 = "Rename [%s] to [%s] failed."
            r1 = 2
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ Throwable -> 0x013e }
            r2 = 0
            r1[r2] = r7     // Catch:{ Throwable -> 0x013e }
            r7 = 1
            r1[r7] = r8     // Catch:{ Throwable -> 0x013e }
            java.lang.String r7 = java.lang.String.format(r0, r1)     // Catch:{ Throwable -> 0x013e }
            r6.<init>(r9, r7)     // Catch:{ Throwable -> 0x013e }
            throw r6     // Catch:{ Throwable -> 0x013e }
        L_0x0126:
            long r1 = r6.lastModified()     // Catch:{ Throwable -> 0x013e }
            r8.setLastModified(r1)     // Catch:{ Throwable -> 0x013e }
            long r1 = r8.lastModified()     // Catch:{ Throwable -> 0x013e }
            long r6 = r6.lastModified()     // Catch:{ Throwable -> 0x013e }
            int r6 = (r1 > r6 ? 1 : (r1 == r6 ? 0 : -1))
            if (r6 == 0) goto L_0x013d
            r8.renameTo(r0)     // Catch:{ Throwable -> 0x013e }
            return r0
        L_0x013d:
            return r8
        L_0x013e:
            r6 = move-exception
            com.uc.webview.export.internal.setup.UCSetupException r7 = new com.uc.webview.export.internal.setup.UCSetupException
            r8 = 1007(0x3ef, float:1.411E-42)
            r7.<init>(r8, r6)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.webview.export.internal.utility.j.a(java.io.File, java.io.File, java.io.File, boolean):java.io.File");
    }

    /* JADX INFO: finally extract failed */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x00bd A[SYNTHETIC, Splitter:B:41:0x00bd] */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00c3 A[Catch:{ Throwable -> 0x00e7, Throwable -> 0x013c }] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00db A[Catch:{ Throwable -> 0x00e7, Throwable -> 0x013c }] */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x00e3 A[Catch:{ Throwable -> 0x00e7, Throwable -> 0x013c }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.io.File[] a(java.io.File[] r11, java.io.File[] r12, java.io.File[] r13) {
        /*
            int r0 = r11.length
            java.io.File[] r0 = new java.io.File[r0]
            r1 = 2
            r2 = 1
            r3 = 0
            int r4 = android.os.Build.VERSION.SDK_INT     // Catch:{ Throwable -> 0x00e7 }
            r5 = 21
            if (r4 < r5) goto L_0x0034
            r4 = 0
        L_0x000d:
            int r5 = r11.length     // Catch:{ Throwable -> 0x00e7 }
            if (r4 >= r5) goto L_0x0033
            r5 = r12[r4]     // Catch:{ Throwable -> 0x00e7 }
            boolean r5 = r5.exists()     // Catch:{ Throwable -> 0x00e7 }
            if (r5 == 0) goto L_0x001d
            r5 = r12[r4]     // Catch:{ Throwable -> 0x00e7 }
            r0[r4] = r5     // Catch:{ Throwable -> 0x00e7 }
            goto L_0x0030
        L_0x001d:
            r5 = r11[r4]     // Catch:{ Throwable -> 0x00e7 }
            java.lang.String r5 = r5.getAbsolutePath()     // Catch:{ Throwable -> 0x00e7 }
            r6 = r12[r4]     // Catch:{ Throwable -> 0x00e7 }
            java.lang.String r6 = r6.getAbsolutePath()     // Catch:{ Throwable -> 0x00e7 }
            android.system.Os.symlink(r5, r6)     // Catch:{ Throwable -> 0x00e7 }
            r5 = r12[r4]     // Catch:{ Throwable -> 0x00e7 }
            r0[r4] = r5     // Catch:{ Throwable -> 0x00e7 }
        L_0x0030:
            int r4 = r4 + 1
            goto L_0x000d
        L_0x0033:
            return r0
        L_0x0034:
            com.uc.webview.export.cyclone.UCElapseTime r4 = new com.uc.webview.export.cyclone.UCElapseTime     // Catch:{ Throwable -> 0x00e7 }
            r4.<init>()     // Catch:{ Throwable -> 0x00e7 }
            r5 = 0
            java.lang.Runtime r6 = java.lang.Runtime.getRuntime()     // Catch:{ Exception -> 0x00b3 }
            java.lang.String r7 = "sh"
            java.lang.Process r6 = r6.exec(r7)     // Catch:{ Exception -> 0x00b3 }
            java.io.DataOutputStream r5 = new java.io.DataOutputStream     // Catch:{ Exception -> 0x00ad, all -> 0x00ab }
            java.io.OutputStream r7 = r6.getOutputStream()     // Catch:{ Exception -> 0x00ad, all -> 0x00ab }
            r5.<init>(r7)     // Catch:{ Exception -> 0x00ad, all -> 0x00ab }
            java.io.BufferedReader r7 = new java.io.BufferedReader     // Catch:{ Exception -> 0x00ad, all -> 0x00ab }
            java.io.InputStreamReader r8 = new java.io.InputStreamReader     // Catch:{ Exception -> 0x00ad, all -> 0x00ab }
            java.io.InputStream r9 = r6.getInputStream()     // Catch:{ Exception -> 0x00ad, all -> 0x00ab }
            r8.<init>(r9)     // Catch:{ Exception -> 0x00ad, all -> 0x00ab }
            r7.<init>(r8)     // Catch:{ Exception -> 0x00ad, all -> 0x00ab }
            r7 = 0
        L_0x005c:
            int r8 = r11.length     // Catch:{ Exception -> 0x00ad, all -> 0x00ab }
            if (r7 >= r8) goto L_0x0096
            r8 = r12[r7]     // Catch:{ Exception -> 0x00ad, all -> 0x00ab }
            boolean r8 = r8.exists()     // Catch:{ Exception -> 0x00ad, all -> 0x00ab }
            if (r8 == 0) goto L_0x006c
            r8 = r12[r7]     // Catch:{ Exception -> 0x00ad, all -> 0x00ab }
            r0[r7] = r8     // Catch:{ Exception -> 0x00ad, all -> 0x00ab }
            goto L_0x0093
        L_0x006c:
            java.lang.String r8 = "ln -s %s %s"
            java.lang.Object[] r9 = new java.lang.Object[r1]     // Catch:{ Exception -> 0x00ad, all -> 0x00ab }
            r10 = r11[r7]     // Catch:{ Exception -> 0x00ad, all -> 0x00ab }
            java.lang.String r10 = r10.getAbsolutePath()     // Catch:{ Exception -> 0x00ad, all -> 0x00ab }
            r9[r3] = r10     // Catch:{ Exception -> 0x00ad, all -> 0x00ab }
            r10 = r12[r7]     // Catch:{ Exception -> 0x00ad, all -> 0x00ab }
            java.lang.String r10 = r10.getAbsolutePath()     // Catch:{ Exception -> 0x00ad, all -> 0x00ab }
            r9[r2] = r10     // Catch:{ Exception -> 0x00ad, all -> 0x00ab }
            java.lang.String r8 = java.lang.String.format(r8, r9)     // Catch:{ Exception -> 0x00ad, all -> 0x00ab }
            r5.writeBytes(r8)     // Catch:{ Exception -> 0x00ad, all -> 0x00ab }
            java.lang.String r8 = "\n"
            r5.writeBytes(r8)     // Catch:{ Exception -> 0x00ad, all -> 0x00ab }
            r5.flush()     // Catch:{ Exception -> 0x00ad, all -> 0x00ab }
            r8 = r12[r7]     // Catch:{ Exception -> 0x00ad, all -> 0x00ab }
            r0[r7] = r8     // Catch:{ Exception -> 0x00ad, all -> 0x00ab }
        L_0x0093:
            int r7 = r7 + 1
            goto L_0x005c
        L_0x0096:
            java.lang.String r12 = "exit\n"
            r5.writeBytes(r12)     // Catch:{ Exception -> 0x00ad, all -> 0x00ab }
            r5.flush()     // Catch:{ Exception -> 0x00ad, all -> 0x00ab }
            r6.waitFor()     // Catch:{ Exception -> 0x00ad, all -> 0x00ab }
            r5.close()     // Catch:{ Exception -> 0x00ad, all -> 0x00ab }
            if (r6 == 0) goto L_0x00a9
            r6.destroy()     // Catch:{ Throwable -> 0x00e7 }
        L_0x00a9:
            r12 = 1
            goto L_0x00c1
        L_0x00ab:
            r12 = move-exception
            goto L_0x00e1
        L_0x00ad:
            r12 = move-exception
            r5 = r6
            goto L_0x00b4
        L_0x00b0:
            r12 = move-exception
            r6 = r5
            goto L_0x00e1
        L_0x00b3:
            r12 = move-exception
        L_0x00b4:
            java.lang.String r6 = "Utils"
            java.lang.String r7 = "symlink exception."
            com.uc.webview.export.internal.utility.Log.e(r6, r7, r12)     // Catch:{ all -> 0x00b0 }
            if (r5 == 0) goto L_0x00c0
            r5.destroy()     // Catch:{ Throwable -> 0x00e7 }
        L_0x00c0:
            r12 = 0
        L_0x00c1:
            if (r12 == 0) goto L_0x00db
            java.lang.String r12 = "Utils"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00e7 }
            java.lang.String r6 = "link success! Time:"
            r5.<init>(r6)     // Catch:{ Throwable -> 0x00e7 }
            long r6 = r4.getMilis()     // Catch:{ Throwable -> 0x00e7 }
            r5.append(r6)     // Catch:{ Throwable -> 0x00e7 }
            java.lang.String r4 = r5.toString()     // Catch:{ Throwable -> 0x00e7 }
            com.uc.webview.export.internal.utility.Log.e(r12, r4)     // Catch:{ Throwable -> 0x00e7 }
            return r0
        L_0x00db:
            java.lang.Throwable r12 = new java.lang.Throwable     // Catch:{ Throwable -> 0x00e7 }
            r12.<init>()     // Catch:{ Throwable -> 0x00e7 }
            throw r12     // Catch:{ Throwable -> 0x00e7 }
        L_0x00e1:
            if (r6 == 0) goto L_0x00e6
            r6.destroy()     // Catch:{ Throwable -> 0x00e7 }
        L_0x00e6:
            throw r12     // Catch:{ Throwable -> 0x00e7 }
        L_0x00e7:
            r12 = 0
        L_0x00e8:
            int r4 = r11.length     // Catch:{ Throwable -> 0x013c }
            if (r12 >= r4) goto L_0x013b
            r4 = r13[r12]     // Catch:{ Throwable -> 0x013c }
            r5 = r11[r12]     // Catch:{ Throwable -> 0x013c }
            r4.delete()     // Catch:{ Throwable -> 0x013c }
            java.io.File r6 = new java.io.File     // Catch:{ Throwable -> 0x013c }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x013c }
            r7.<init>()     // Catch:{ Throwable -> 0x013c }
            java.lang.String r8 = r4.getAbsolutePath()     // Catch:{ Throwable -> 0x013c }
            r7.append(r8)     // Catch:{ Throwable -> 0x013c }
            java.lang.String r8 = ".tmp"
            r7.append(r8)     // Catch:{ Throwable -> 0x013c }
            java.lang.String r7 = r7.toString()     // Catch:{ Throwable -> 0x013c }
            r6.<init>(r7)     // Catch:{ Throwable -> 0x013c }
            r6.createNewFile()     // Catch:{ Throwable -> 0x013c }
            a(r5, r6)     // Catch:{ Throwable -> 0x013c }
            boolean r7 = r6.renameTo(r4)     // Catch:{ Throwable -> 0x013c }
            if (r7 != 0) goto L_0x012f
            r6.delete()     // Catch:{ Throwable -> 0x013c }
            com.uc.webview.export.internal.setup.UCSetupException r11 = new com.uc.webview.export.internal.setup.UCSetupException     // Catch:{ Throwable -> 0x013c }
            r12 = 1005(0x3ed, float:1.408E-42)
            java.lang.String r13 = "Rename [%s] to [%s] failed."
            java.lang.Object[] r0 = new java.lang.Object[r1]     // Catch:{ Throwable -> 0x013c }
            r0[r3] = r6     // Catch:{ Throwable -> 0x013c }
            r0[r2] = r4     // Catch:{ Throwable -> 0x013c }
            java.lang.String r13 = java.lang.String.format(r13, r0)     // Catch:{ Throwable -> 0x013c }
            r11.<init>(r12, r13)     // Catch:{ Throwable -> 0x013c }
            throw r11     // Catch:{ Throwable -> 0x013c }
        L_0x012f:
            long r5 = r5.lastModified()     // Catch:{ Throwable -> 0x013c }
            r4.setLastModified(r5)     // Catch:{ Throwable -> 0x013c }
            r0[r12] = r4     // Catch:{ Throwable -> 0x013c }
            int r12 = r12 + 1
            goto L_0x00e8
        L_0x013b:
            return r0
        L_0x013c:
            r11 = move-exception
            com.uc.webview.export.internal.setup.UCSetupException r12 = new com.uc.webview.export.internal.setup.UCSetupException
            r13 = 1007(0x3ef, float:1.411E-42)
            r12.<init>(r13, r11)
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.webview.export.internal.utility.j.a(java.io.File[], java.io.File[], java.io.File[]):java.io.File[]");
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(6:9|10|(1:16)|17|18|(6:19|20|(2:22|(1:24)(4:25|(1:(1:32)(4:33|(1:35)(1:36)|37|(2:39|40)(4:41|52|(2:54|55)|56)))|42|(2:44|45)(7:46|47|50|51|52|(0)|56)))|58|59|60)) */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00d8, code lost:
        r1 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x0110, code lost:
        r7 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x0115, code lost:
        r7 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x0116, code lost:
        r1 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x0118, code lost:
        r7 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x0119, code lost:
        r8 = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x011b, code lost:
        r7 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x0124, code lost:
        r7 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x0125, code lost:
        throw r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x0126, code lost:
        if (r8 != null) goto L_0x0128;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:?, code lost:
        r8.disconnect();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x012b, code lost:
        throw r7;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:17:0x0047 */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0070 A[Catch:{ UCKnownException -> 0x0115, Throwable -> 0x0112, all -> 0x0110 }] */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x00f1 A[SYNTHETIC, Splitter:B:54:0x00f1] */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x0110 A[ExcHandler: all (th java.lang.Throwable), Splitter:B:19:0x0052] */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x0115 A[ExcHandler: UCKnownException (e com.uc.webview.export.cyclone.UCKnownException), Splitter:B:19:0x0052] */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x0128 A[SYNTHETIC, Splitter:B:75:0x0128] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.util.Pair<java.lang.Long, java.lang.Long> a(java.lang.String r7, java.net.URL r8) throws com.uc.webview.export.internal.setup.UCSetupException {
        /*
            java.util.HashMap<java.lang.String, android.util.Pair<java.lang.Long, java.lang.Object>> r0 = f
            java.lang.Object r0 = r0.get(r7)
            android.util.Pair r0 = (android.util.Pair) r0
            r1 = 0
            if (r0 == 0) goto L_0x0025
            java.lang.Object r2 = r0.first
            java.lang.Long r2 = (java.lang.Long) r2
            long r3 = android.os.SystemClock.uptimeMillis()
            long r5 = r2.longValue()
            long r3 = r3 - r5
            java.lang.Long r2 = e
            long r5 = r2.longValue()
            int r2 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r2 >= 0) goto L_0x0025
            java.lang.Object r0 = r0.second
            goto L_0x0026
        L_0x0025:
            r0 = r1
        L_0x0026:
            android.util.Pair r0 = (android.util.Pair) r0
            if (r0 == 0) goto L_0x002b
            return r0
        L_0x002b:
            boolean r0 = com.uc.webview.export.internal.SDKFactory.g     // Catch:{ Throwable -> 0x0047 }
            if (r0 != 0) goto L_0x0047
            java.lang.String r0 = "traffic_stat"
            java.lang.String r0 = com.uc.webview.export.extension.UCCore.getParam(r0)     // Catch:{ Throwable -> 0x0047 }
            boolean r0 = java.lang.Boolean.parseBoolean(r0)     // Catch:{ Throwable -> 0x0047 }
            if (r0 == 0) goto L_0x0047
            int r0 = android.os.Build.VERSION.SDK_INT     // Catch:{ Throwable -> 0x0047 }
            r2 = 14
            if (r0 < r2) goto L_0x0047
            r0 = 40962(0xa002, float:5.74E-41)
            android.net.TrafficStats.setThreadStatsTag(r0)     // Catch:{ Throwable -> 0x0047 }
        L_0x0047:
            java.net.URL r0 = new java.net.URL     // Catch:{ UCKnownException -> 0x0124, Throwable -> 0x011b }
            r0.<init>(r8, r7)     // Catch:{ UCKnownException -> 0x0124, Throwable -> 0x011b }
            java.net.URLConnection r8 = r0.openConnection()     // Catch:{ UCKnownException -> 0x0124, Throwable -> 0x011b }
            java.net.HttpURLConnection r8 = (java.net.HttpURLConnection) r8     // Catch:{ UCKnownException -> 0x0124, Throwable -> 0x011b }
            int r2 = a     // Catch:{ UCKnownException -> 0x0115, Throwable -> 0x0112, all -> 0x0110 }
            r8.setConnectTimeout(r2)     // Catch:{ UCKnownException -> 0x0115, Throwable -> 0x0112, all -> 0x0110 }
            int r2 = b     // Catch:{ UCKnownException -> 0x0115, Throwable -> 0x0112, all -> 0x0110 }
            r8.setReadTimeout(r2)     // Catch:{ UCKnownException -> 0x0115, Throwable -> 0x0112, all -> 0x0110 }
            r2 = 0
            r8.setInstanceFollowRedirects(r2)     // Catch:{ UCKnownException -> 0x0115, Throwable -> 0x0112, all -> 0x0110 }
            java.lang.String r2 = "HEAD"
            r8.setRequestMethod(r2)     // Catch:{ UCKnownException -> 0x0115, Throwable -> 0x0112, all -> 0x0110 }
            r8.connect()     // Catch:{ UCKnownException -> 0x0115, Throwable -> 0x0112, all -> 0x0110 }
            int r2 = r8.getResponseCode()     // Catch:{ UCKnownException -> 0x0115, Throwable -> 0x0112, all -> 0x0110 }
            r3 = 200(0xc8, float:2.8E-43)
            if (r2 < r3) goto L_0x00f5
            r3 = 303(0x12f, float:4.25E-43)
            if (r2 <= r3) goto L_0x0076
            goto L_0x00f5
        L_0x0076:
            r4 = 300(0x12c, float:4.2E-43)
            if (r2 == r4) goto L_0x00be
            r4 = 301(0x12d, float:4.22E-43)
            if (r2 == r4) goto L_0x00be
            r4 = 302(0x12e, float:4.23E-43)
            if (r2 == r4) goto L_0x00be
            if (r2 != r3) goto L_0x0085
            goto L_0x00be
        L_0x0085:
            boolean r0 = c     // Catch:{ UCKnownException -> 0x0115, Throwable -> 0x0112, all -> 0x0110 }
            r1 = 0
            if (r0 == 0) goto L_0x0090
            long r3 = r8.getLastModified()     // Catch:{ UCKnownException -> 0x0115, Throwable -> 0x0112, all -> 0x0110 }
            goto L_0x0091
        L_0x0090:
            r3 = r1
        L_0x0091:
            int r0 = r8.getContentLength()     // Catch:{ UCKnownException -> 0x0115, Throwable -> 0x0112, all -> 0x0110 }
            long r5 = (long) r0     // Catch:{ UCKnownException -> 0x0115, Throwable -> 0x0112, all -> 0x0110 }
            int r0 = (r5 > r1 ? 1 : (r5 == r1 ? 0 : -1))
            if (r0 > 0) goto L_0x00ac
            com.uc.webview.export.cyclone.UCKnownException r7 = new com.uc.webview.export.cyclone.UCKnownException     // Catch:{ UCKnownException -> 0x0115, Throwable -> 0x0112, all -> 0x0110 }
            r0 = 4023(0xfb7, float:5.637E-42)
            java.lang.String r1 = "Total size is not correct:"
            java.lang.String r2 = java.lang.String.valueOf(r5)     // Catch:{ UCKnownException -> 0x0115, Throwable -> 0x0112, all -> 0x0110 }
            java.lang.String r1 = r1.concat(r2)     // Catch:{ UCKnownException -> 0x0115, Throwable -> 0x0112, all -> 0x0110 }
            r7.<init>(r0, r1)     // Catch:{ UCKnownException -> 0x0115, Throwable -> 0x0112, all -> 0x0110 }
            throw r7     // Catch:{ UCKnownException -> 0x0115, Throwable -> 0x0112, all -> 0x0110 }
        L_0x00ac:
            r8.disconnect()     // Catch:{ UCKnownException -> 0x0115, Throwable -> 0x0112, all -> 0x0110 }
            android.util.Pair r0 = new android.util.Pair     // Catch:{ UCKnownException -> 0x0115, Throwable -> 0x0112, all -> 0x0110 }
            java.lang.Long r1 = java.lang.Long.valueOf(r5)     // Catch:{ UCKnownException -> 0x0115, Throwable -> 0x0112, all -> 0x0110 }
            java.lang.Long r2 = java.lang.Long.valueOf(r3)     // Catch:{ UCKnownException -> 0x0115, Throwable -> 0x0112, all -> 0x0110 }
            r0.<init>(r1, r2)     // Catch:{ UCKnownException -> 0x0115, Throwable -> 0x0112, all -> 0x0110 }
            r1 = r8
            goto L_0x00dd
        L_0x00be:
            java.lang.String r2 = "Location"
            java.lang.String r2 = r8.getHeaderField(r2)     // Catch:{ UCKnownException -> 0x0115, Throwable -> 0x0112, all -> 0x0110 }
            boolean r3 = a(r2)     // Catch:{ UCKnownException -> 0x0115, Throwable -> 0x0112, all -> 0x0110 }
            if (r3 == 0) goto L_0x00d4
            com.uc.webview.export.cyclone.UCKnownException r7 = new com.uc.webview.export.cyclone.UCKnownException     // Catch:{ UCKnownException -> 0x0115, Throwable -> 0x0112, all -> 0x0110 }
            r0 = 4022(0xfb6, float:5.636E-42)
            java.lang.String r1 = "Redirect location is null."
            r7.<init>(r0, r1)     // Catch:{ UCKnownException -> 0x0115, Throwable -> 0x0112, all -> 0x0110 }
            throw r7     // Catch:{ UCKnownException -> 0x0115, Throwable -> 0x0112, all -> 0x0110 }
        L_0x00d4:
            r8.disconnect()     // Catch:{ Throwable -> 0x00d8, UCKnownException -> 0x0115, all -> 0x0110 }
            goto L_0x00d9
        L_0x00d8:
            r1 = r8
        L_0x00d9:
            android.util.Pair r0 = a(r2, r0)     // Catch:{ UCKnownException -> 0x0124, Throwable -> 0x011b }
        L_0x00dd:
            java.util.HashMap<java.lang.String, android.util.Pair<java.lang.Long, java.lang.Object>> r8 = f     // Catch:{ UCKnownException -> 0x0124, Throwable -> 0x011b }
            android.util.Pair r2 = new android.util.Pair     // Catch:{ UCKnownException -> 0x0124, Throwable -> 0x011b }
            long r3 = android.os.SystemClock.uptimeMillis()     // Catch:{ UCKnownException -> 0x0124, Throwable -> 0x011b }
            java.lang.Long r3 = java.lang.Long.valueOf(r3)     // Catch:{ UCKnownException -> 0x0124, Throwable -> 0x011b }
            r2.<init>(r3, r0)     // Catch:{ UCKnownException -> 0x0124, Throwable -> 0x011b }
            r8.put(r7, r2)     // Catch:{ UCKnownException -> 0x0124, Throwable -> 0x011b }
            if (r1 == 0) goto L_0x00f4
            r1.disconnect()     // Catch:{ Throwable -> 0x00f4 }
        L_0x00f4:
            return r0
        L_0x00f5:
            com.uc.webview.export.cyclone.UCKnownException r7 = new com.uc.webview.export.cyclone.UCKnownException     // Catch:{ UCKnownException -> 0x0115, Throwable -> 0x0112, all -> 0x0110 }
            r0 = 4021(0xfb5, float:5.635E-42)
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ UCKnownException -> 0x0115, Throwable -> 0x0112, all -> 0x0110 }
            java.lang.String r3 = "httpcode:"
            r1.<init>(r3)     // Catch:{ UCKnownException -> 0x0115, Throwable -> 0x0112, all -> 0x0110 }
            r1.append(r2)     // Catch:{ UCKnownException -> 0x0115, Throwable -> 0x0112, all -> 0x0110 }
            java.lang.String r2 = " not correct."
            r1.append(r2)     // Catch:{ UCKnownException -> 0x0115, Throwable -> 0x0112, all -> 0x0110 }
            java.lang.String r1 = r1.toString()     // Catch:{ UCKnownException -> 0x0115, Throwable -> 0x0112, all -> 0x0110 }
            r7.<init>(r0, r1)     // Catch:{ UCKnownException -> 0x0115, Throwable -> 0x0112, all -> 0x0110 }
            throw r7     // Catch:{ UCKnownException -> 0x0115, Throwable -> 0x0112, all -> 0x0110 }
        L_0x0110:
            r7 = move-exception
            goto L_0x0126
        L_0x0112:
            r7 = move-exception
            r1 = r8
            goto L_0x011c
        L_0x0115:
            r7 = move-exception
            r1 = r8
            goto L_0x0125
        L_0x0118:
            r7 = move-exception
            r8 = r1
            goto L_0x0126
        L_0x011b:
            r7 = move-exception
        L_0x011c:
            com.uc.webview.export.internal.setup.UCSetupException r8 = new com.uc.webview.export.internal.setup.UCSetupException     // Catch:{ all -> 0x0118 }
            r0 = 2009(0x7d9, float:2.815E-42)
            r8.<init>(r0, r7)     // Catch:{ all -> 0x0118 }
            throw r8     // Catch:{ all -> 0x0118 }
        L_0x0124:
            r7 = move-exception
        L_0x0125:
            throw r7     // Catch:{ all -> 0x0118 }
        L_0x0126:
            if (r8 == 0) goto L_0x012b
            r8.disconnect()     // Catch:{ Throwable -> 0x012b }
        L_0x012b:
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.webview.export.internal.utility.j.a(java.lang.String, java.net.URL):android.util.Pair");
    }

    public static Boolean a(ConcurrentHashMap<String, Object> concurrentHashMap, String str) {
        Object obj = concurrentHashMap.get(str);
        if (obj == null) {
            return null;
        }
        if (obj instanceof Boolean) {
            return (Boolean) obj;
        }
        if (obj instanceof String) {
            return Boolean.valueOf(Boolean.parseBoolean((String) obj));
        }
        throw new UCSetupException(3012, String.format("\"true\" or \"false\" or boolean expected with key:[%s], now is [%s]", new Object[]{str, obj}));
    }

    private static boolean c(File file) {
        try {
            if (file.getCanonicalPath().contains("/data/app/") || file.getCanonicalPath().contains("/system/")) {
                return true;
            }
            return false;
        } catch (IOException e2) {
            Log.e("Utils", "isSystemFile", e2);
            return false;
        }
    }

    public static boolean a(File file) {
        if (file != null) {
            try {
                if (!file.exists() || c(file)) {
                    return true;
                }
                if (VERSION.SDK_INT >= 9) {
                    return file.setReadable(true, false);
                }
                Runtime runtime = Runtime.getRuntime();
                StringBuilder sb = new StringBuilder("chmod 644 ");
                sb.append(file.getAbsolutePath());
                runtime.exec(sb.toString());
            } catch (Exception e2) {
                Log.e("Utils", "setReadable", e2);
                return false;
            }
        }
        return true;
    }

    public static boolean b(File file) {
        if (file != null) {
            try {
                if (!file.exists() || c(file)) {
                    return true;
                }
                if (VERSION.SDK_INT >= 9) {
                    return file.setExecutable(true, false);
                }
                Runtime runtime = Runtime.getRuntime();
                StringBuilder sb = new StringBuilder("chmod 711 ");
                sb.append(file.getAbsolutePath());
                runtime.exec(sb.toString());
            } catch (Exception e2) {
                Log.e("Utils", "setExecutable", e2);
                return false;
            }
        }
        return true;
    }

    public static int c(String str) {
        try {
            return Integer.parseInt(str);
        } catch (Exception unused) {
            return 0;
        }
    }

    public static long d(String str) {
        try {
            return Long.parseLong(str);
        } catch (Exception unused) {
            return 0;
        }
    }

    private static String f(String str) {
        try {
            Method declaredMethod = Class.forName("android.os.SystemProperties").getDeclaredMethod("get", new Class[]{String.class});
            declaredMethod.setAccessible(true);
            Object invoke = declaredMethod.invoke(null, new Object[]{str});
            if (invoke == null) {
                return null;
            }
            return invoke.toString();
        } catch (Throwable unused) {
            return null;
        }
    }

    public static String c() {
        for (Entry next : g.entrySet()) {
            String f2 = f((String) next.getKey());
            if (f2 != null && f2.length() > 0) {
                return (String) next.getValue();
            }
        }
        return "UNKNOWN";
    }

    public static boolean d() {
        if (j) {
            return i;
        }
        String[] strArr = h;
        int length = strArr.length;
        boolean z = false;
        int i2 = 0;
        while (true) {
            if (i2 >= length) {
                break;
            }
            if (Build.SDK_PROFILE_ID.equals(strArr[i2])) {
                z = true;
                break;
            }
            i2++;
        }
        i = z;
        j = true;
        return i;
    }

    public static String[] a(ClassLoader classLoader) {
        String[][] strArr;
        try {
            Class<?> cls = Class.forName("com.uc.webview.browser.shell.NativeLibraries", true, classLoader);
            if (cls != null) {
                Field declaredField = cls.getDeclaredField("LIBRARIES");
                declaredField.setAccessible(true);
                ArrayList arrayList = new ArrayList();
                for (String[] strArr2 : (String[][]) declaredField.get(null)) {
                    if (!(strArr2 == null || strArr2[0] == null)) {
                        arrayList.add(strArr2[0]);
                    }
                }
                return (String[]) arrayList.toArray(new String[arrayList.size()]);
            }
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchFieldException unused) {
        }
        return null;
    }

    public static String b(ClassLoader classLoader) {
        return a(classLoader, (String) "NAME");
    }

    public static String c(ClassLoader classLoader) {
        return a(classLoader, (String) "SUPPORT_SDK_MIN");
    }

    private static String a(ClassLoader classLoader, String str) {
        if (classLoader == null) {
            return null;
        }
        try {
            String str2 = (String) Class.forName("com.uc.webview.browser.shell.Build$Version", false, classLoader).getField(str).get(null);
            if (str2 == null || str2.length() <= 0) {
                return null;
            }
            return str2;
        } catch (Exception e2) {
            Log.d("Utils", ".getVersionFieldFromSdkShellDexLoader", e2);
        }
    }

    public static void a(Map<String, String> map) {
        try {
            Log.d("Utils", "addHeaderInfoToCrashSdk headerInfos: ".concat(String.valueOf(map)));
            Object invokeNoThrow = ReflectionUtil.invokeNoThrow((String) "com.uc.crashsdk.export.CrashApi", (String) "getInstance");
            if (invokeNoThrow != null && map.size() > 0) {
                for (Entry next : map.entrySet()) {
                    ReflectionUtil.invokeNoThrow(invokeNoThrow, (String) "addHeaderInfo", new Class[]{String.class, String.class}, new Object[]{next.getKey(), next.getValue()});
                }
            }
        } catch (Throwable unused) {
        }
    }

    public static int a(boolean z, boolean z2) {
        if (((Boolean) UCMPackageInfo.invoke(10011, new Object[0])).booleanValue()) {
            return 1;
        }
        if (z2) {
            return 4;
        }
        return z ? 2 : 3;
    }

    public static boolean a(UCMRunningInfo uCMRunningInfo) {
        return uCMRunningInfo == null || (uCMRunningInfo.coreType == 2 && SDKFactory.l);
    }

    public static String a(File file, String str) {
        return a(file, str, true);
    }

    public static String a(File file, String str, boolean z) {
        File[] listFiles = file.listFiles();
        if (listFiles == null || listFiles.length == 0) {
            return null;
        }
        for (File file2 : listFiles) {
            if (file2.isDirectory()) {
                if (z) {
                    String a2 = a(file2, str, true);
                    if (!a(a2)) {
                        return a2;
                    }
                } else {
                    continue;
                }
            }
            if (file2.getName().contains(str)) {
                return file2.getAbsolutePath();
            }
        }
        return null;
    }

    public static boolean b(File file, File file2) {
        try {
            if (!file.getCanonicalPath().startsWith(file2.getCanonicalPath())) {
                return false;
            }
            while (file.getCanonicalPath().contains(file2.getCanonicalPath()) && !file.getCanonicalPath().equals(file2.getCanonicalPath())) {
                if (!b(file)) {
                    return false;
                }
                file = file.getParentFile();
            }
            return true;
        } catch (Throwable unused) {
            return false;
        }
    }

    public static boolean e() {
        try {
            Class.forName("com.uc.webview.browser.BrowserCore");
            return true;
        } catch (Exception unused) {
            return false;
        }
    }
}
