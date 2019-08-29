package com.alipay.mobile.quinox.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Process;
import android.support.v4.content.PermissionChecker;
import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.DeviceProperty;
import com.alipay.mobile.common.logging.api.ProcessInfo;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class SystemUtil {
    public static boolean IS_ALIPAY = true;
    public static final int PER_USER_RANGE = 100000;
    private static final String TAG = "SystemUtil";
    private static int canHookArtFlag = 0;
    private static int canHookFlag = 0;
    private static int isUILaunch = 0;
    static int sFrequency = -1;
    private static Boolean sHuaweiPreloadLaunch;
    private static String sPreloadBy;
    private static Boolean sPreloadLaunch;

    public static boolean hasActivityOccur(Context context) {
        return false;
    }

    private SystemUtil() {
    }

    public static boolean isBackground1(Context context) {
        if (context == null || PermissionChecker.checkSelfPermission(context, "android.permission.GET_TASKS") == -1) {
            return false;
        }
        try {
            List<RunningTaskInfo> runningTasks = ((ActivityManager) context.getSystemService(WidgetType.ACTIVITY)).getRunningTasks(1);
            if (runningTasks == null || runningTasks.isEmpty() || runningTasks.get(0).topActivity.getPackageName().equals(context.getPackageName())) {
                return false;
            }
            return true;
        } catch (SecurityException e) {
            TraceLogger.e((String) TAG, (Throwable) e);
        }
    }

    @TargetApi(21)
    private static boolean isBackground2(Context context) {
        List list;
        try {
            list = ((ActivityManager) context.getSystemService(WidgetType.ACTIVITY)).getRunningAppProcesses();
        } catch (Throwable th) {
            TraceLogger.w((String) TAG, th);
            list = null;
        }
        if (list != null) {
            String packageName = context.getPackageName();
            Iterator it = list.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                RunningAppProcessInfo runningAppProcessInfo = (RunningAppProcessInfo) it.next();
                if (runningAppProcessInfo.processName.equals(packageName)) {
                    if (runningAppProcessInfo.importance >= 125) {
                        return true;
                    }
                    try {
                        Field declaredField = runningAppProcessInfo.getClass().getDeclaredField("processState");
                        if (((Integer) declaredField.get(runningAppProcessInfo)).intValue() > ((Integer) ActivityManager.class.getDeclaredField("PROCESS_STATE_TOP").get(null)).intValue()) {
                            return true;
                        }
                    } catch (Throwable th2) {
                        TraceLogger.e((String) TAG, th2);
                    }
                }
            }
        }
        return false;
    }

    public static boolean isAppOnBackground(Context context) {
        if (VERSION.SDK_INT < 21) {
            return isBackground1(context);
        }
        return isBackground2(context);
    }

    public static void clearApplicationUserData(Context context, Set<String> set, Set<String> set2) {
        File parentFile = context.getFilesDir().getParentFile();
        if (parentFile.exists()) {
            StringBuilder sb = new StringBuilder("clear user date root: [");
            sb.append(parentFile.getAbsolutePath());
            sb.append("]");
            LogUtil.i(TAG, sb.toString());
            try {
                FileUtil.deleteFiles(parentFile, set, set2);
            } catch (Exception e) {
                LogUtil.w((String) TAG, (Throwable) e);
            }
        }
    }

    public static void clearSharePreference(Context context, Set<String> set) {
        File file = new File(context.getFilesDir().getParent(), "shared_prefs");
        if (file.exists()) {
            StringBuilder sb = new StringBuilder("clear shared_prefs root: [");
            sb.append(file.getAbsolutePath());
            sb.append("]");
            LogUtil.i(TAG, sb.toString());
            try {
                FileUtil.deleteFilesWithExcludes(file, set);
            } catch (Exception e) {
                LogUtil.w((String) TAG, (Throwable) e);
            }
        }
    }

    public static void clearApplicationDatabase(Context context, Set<String> set) {
        File parentFile = context.getDatabasePath("db").getParentFile();
        if (parentFile.exists()) {
            StringBuilder sb = new StringBuilder("clear database root: [");
            sb.append(parentFile.getAbsolutePath());
            sb.append("]");
            LogUtil.i(TAG, sb.toString());
            try {
                FileUtil.deleteFilesWithExcludePrefixes(parentFile, set);
            } catch (Exception e) {
                LogUtil.w((String) TAG, (Throwable) e);
            }
        }
    }

    public static synchronized int getCPUFrequencyMax() {
        int i;
        synchronized (SystemUtil.class) {
            if (sFrequency == -1) {
                sFrequency = readSystemFileAsInt("/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq");
                if (-1 != sFrequency) {
                    sFrequency /= 1000;
                }
            }
            i = sFrequency;
        }
        return i;
    }

    public static int readSystemFileAsInt(String str) {
        FileInputStream fileInputStream = null;
        try {
            FileInputStream fileInputStream2 = new FileInputStream(str);
            try {
                StringBuilder sb = new StringBuilder();
                Scanner scanner = new Scanner(fileInputStream2);
                while (scanner.hasNextLine()) {
                    sb.append(scanner.nextLine());
                }
                int parseInt = Integer.parseInt(sb.toString());
                StreamUtil.closeSafely(fileInputStream2);
                return parseInt;
            } catch (Exception unused) {
                fileInputStream = fileInputStream2;
                try {
                    StringBuilder sb2 = new StringBuilder("Exception to readSystemFileAsInt(");
                    sb2.append(str);
                    sb2.append("), return -1");
                    TraceLogger.w((String) TAG, sb2.toString());
                    StreamUtil.closeSafely(fileInputStream);
                    return -1;
                } catch (Throwable th) {
                    th = th;
                    StreamUtil.closeSafely(fileInputStream);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                fileInputStream = fileInputStream2;
                StreamUtil.closeSafely(fileInputStream);
                throw th;
            }
        } catch (Exception unused2) {
            StringBuilder sb22 = new StringBuilder("Exception to readSystemFileAsInt(");
            sb22.append(str);
            sb22.append("), return -1");
            TraceLogger.w((String) TAG, sb22.toString());
            StreamUtil.closeSafely(fileInputStream);
            return -1;
        }
    }

    public static boolean isArt() {
        String property = System.getProperty("java.vm.version");
        if (property != null) {
            Matcher matcher = Pattern.compile("(\\d+)\\.(\\d+)(\\.\\d+)?").matcher(property);
            if (matcher.matches()) {
                try {
                    if (Integer.parseInt(matcher.group(1)) >= 2) {
                        return true;
                    }
                } catch (NumberFormatException unused) {
                }
            }
        }
        return false;
    }

    private static boolean supportARM() {
        String[] strArr = {Build.CPU_ABI.toLowerCase(), Build.CPU_ABI2.toLowerCase()};
        for (int i = 0; i < 2; i++) {
            new StringBuilder("armList: ").append(strArr[i]);
            if (strArr[i].startsWith("armeabi")) {
                return true;
            }
        }
        return false;
    }

    public static boolean isX86() {
        if (VERSION.SDK_INT >= 21) {
            return isX86Lollipop();
        }
        return isX86Kitkat();
    }

    /* JADX WARNING: Removed duplicated region for block: B:39:0x0078 A[SYNTHETIC, Splitter:B:39:0x0078] */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x0086 A[SYNTHETIC, Splitter:B:44:0x0086] */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x0094 A[SYNTHETIC, Splitter:B:49:0x0094] */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x00a5 A[SYNTHETIC, Splitter:B:56:0x00a5] */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x00b3 A[SYNTHETIC, Splitter:B:61:0x00b3] */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x00c1 A[SYNTHETIC, Splitter:B:66:0x00c1] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean isX86Kitkat() {
        /*
            r0 = 0
            java.lang.Runtime r1 = java.lang.Runtime.getRuntime()     // Catch:{ Throwable -> 0x006a, all -> 0x0064 }
            java.lang.String r2 = "getprop ro.product.cpu.abi"
            java.lang.Process r1 = r1.exec(r2)     // Catch:{ Throwable -> 0x006a, all -> 0x0064 }
            java.io.InputStreamReader r2 = new java.io.InputStreamReader     // Catch:{ Throwable -> 0x005f, all -> 0x005a }
            java.io.InputStream r3 = r1.getInputStream()     // Catch:{ Throwable -> 0x005f, all -> 0x005a }
            r2.<init>(r3)     // Catch:{ Throwable -> 0x005f, all -> 0x005a }
            java.io.BufferedReader r3 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x0055, all -> 0x004f }
            r3.<init>(r2)     // Catch:{ Throwable -> 0x0055, all -> 0x004f }
            java.lang.String r0 = r3.readLine()     // Catch:{ Throwable -> 0x004d }
            java.lang.String r4 = "x86"
            boolean r0 = r0.contains(r4)     // Catch:{ Throwable -> 0x004d }
            r3.close()     // Catch:{ Throwable -> 0x0028 }
            goto L_0x0030
        L_0x0028:
            r3 = move-exception
            java.lang.String r4 = "SystemUtil"
            java.lang.String r5 = "isX86CPU error"
            com.alipay.mobile.quinox.utils.TraceLogger.w(r4, r5, r3)
        L_0x0030:
            r2.close()     // Catch:{ Throwable -> 0x0034 }
            goto L_0x003c
        L_0x0034:
            r2 = move-exception
            java.lang.String r3 = "SystemUtil"
            java.lang.String r4 = "isX86CPU error"
            com.alipay.mobile.quinox.utils.TraceLogger.w(r3, r4, r2)
        L_0x003c:
            if (r1 == 0) goto L_0x00a1
            r1.destroy()     // Catch:{ Throwable -> 0x0043 }
            goto L_0x00a1
        L_0x0043:
            r1 = move-exception
            java.lang.String r2 = "SystemUtil"
            java.lang.String r3 = "isX86CPU error"
            com.alipay.mobile.quinox.utils.TraceLogger.w(r2, r3, r1)
            goto L_0x00a1
        L_0x004d:
            r0 = move-exception
            goto L_0x006f
        L_0x004f:
            r3 = move-exception
            r6 = r3
            r3 = r0
            r0 = r6
            goto L_0x00a3
        L_0x0055:
            r3 = move-exception
            r6 = r3
            r3 = r0
            r0 = r6
            goto L_0x006f
        L_0x005a:
            r2 = move-exception
            r3 = r0
            r0 = r2
            r2 = r3
            goto L_0x00a3
        L_0x005f:
            r2 = move-exception
            r3 = r0
            r0 = r2
            r2 = r3
            goto L_0x006f
        L_0x0064:
            r1 = move-exception
            r2 = r0
            r3 = r2
            r0 = r1
            r1 = r3
            goto L_0x00a3
        L_0x006a:
            r1 = move-exception
            r2 = r0
            r3 = r2
            r0 = r1
            r1 = r3
        L_0x006f:
            java.lang.String r4 = "SystemUtil"
            java.lang.String r5 = "isX86CPU error"
            com.alipay.mobile.quinox.utils.TraceLogger.w(r4, r5, r0)     // Catch:{ all -> 0x00a2 }
            if (r3 == 0) goto L_0x0084
            r3.close()     // Catch:{ Throwable -> 0x007c }
            goto L_0x0084
        L_0x007c:
            r0 = move-exception
            java.lang.String r3 = "SystemUtil"
            java.lang.String r4 = "isX86CPU error"
            com.alipay.mobile.quinox.utils.TraceLogger.w(r3, r4, r0)
        L_0x0084:
            if (r2 == 0) goto L_0x0092
            r2.close()     // Catch:{ Throwable -> 0x008a }
            goto L_0x0092
        L_0x008a:
            r0 = move-exception
            java.lang.String r2 = "SystemUtil"
            java.lang.String r3 = "isX86CPU error"
            com.alipay.mobile.quinox.utils.TraceLogger.w(r2, r3, r0)
        L_0x0092:
            if (r1 == 0) goto L_0x00a0
            r1.destroy()     // Catch:{ Throwable -> 0x0098 }
            goto L_0x00a0
        L_0x0098:
            r0 = move-exception
            java.lang.String r1 = "SystemUtil"
            java.lang.String r2 = "isX86CPU error"
            com.alipay.mobile.quinox.utils.TraceLogger.w(r1, r2, r0)
        L_0x00a0:
            r0 = 0
        L_0x00a1:
            return r0
        L_0x00a2:
            r0 = move-exception
        L_0x00a3:
            if (r3 == 0) goto L_0x00b1
            r3.close()     // Catch:{ Throwable -> 0x00a9 }
            goto L_0x00b1
        L_0x00a9:
            r3 = move-exception
            java.lang.String r4 = "SystemUtil"
            java.lang.String r5 = "isX86CPU error"
            com.alipay.mobile.quinox.utils.TraceLogger.w(r4, r5, r3)
        L_0x00b1:
            if (r2 == 0) goto L_0x00bf
            r2.close()     // Catch:{ Throwable -> 0x00b7 }
            goto L_0x00bf
        L_0x00b7:
            r2 = move-exception
            java.lang.String r3 = "SystemUtil"
            java.lang.String r4 = "isX86CPU error"
            com.alipay.mobile.quinox.utils.TraceLogger.w(r3, r4, r2)
        L_0x00bf:
            if (r1 == 0) goto L_0x00cd
            r1.destroy()     // Catch:{ Throwable -> 0x00c5 }
            goto L_0x00cd
        L_0x00c5:
            r1 = move-exception
            java.lang.String r2 = "SystemUtil"
            java.lang.String r3 = "isX86CPU error"
            com.alipay.mobile.quinox.utils.TraceLogger.w(r2, r3, r1)
        L_0x00cd:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.quinox.utils.SystemUtil.isX86Kitkat():boolean");
    }

    public static boolean isX86Lollipop() {
        boolean z = false;
        try {
            ArrayList arrayList = new ArrayList();
            arrayList.add(Build.CPU_ABI);
            arrayList.add(Build.CPU_ABI2);
            if (VERSION.SDK_INT >= 21) {
                String[] strArr = Build.SUPPORTED_ABIS;
                if (strArr != null) {
                    for (String add : strArr) {
                        arrayList.add(add);
                    }
                }
            }
            int i = 0;
            while (true) {
                if (i >= arrayList.size()) {
                    break;
                }
                String str = (String) arrayList.get(i);
                if (str != null && str.contains("x86")) {
                    z = true;
                    break;
                }
                i++;
            }
        } catch (Exception e) {
            TraceLogger.w(TAG, "isX86CPU error", e);
        }
        TraceLogger.i((String) TAG, "isX86=".concat(String.valueOf(z)));
        return z;
    }

    public static boolean isYunOs() {
        try {
            Method method = Class.forName("android.os.SystemProperties").getMethod("get", new Class[]{String.class});
            String str = (String) method.invoke(null, new Object[]{"ro.yunos.version"});
            String str2 = (String) method.invoke(null, new Object[]{"java.vm.name"});
            if ((str2 == null || !str2.toLowerCase().contains("lemur")) && (str == null || str.trim().length() <= 0)) {
                return false;
            }
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0056, code lost:
        return false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized boolean canHookArt() {
        /*
            java.lang.Class<com.alipay.mobile.quinox.utils.SystemUtil> r0 = com.alipay.mobile.quinox.utils.SystemUtil.class
            monitor-enter(r0)
            int r1 = canHookArtFlag     // Catch:{ all -> 0x0057 }
            r2 = 1
            if (r1 != r2) goto L_0x000a
            monitor-exit(r0)
            return r2
        L_0x000a:
            int r1 = canHookArtFlag     // Catch:{ all -> 0x0057 }
            r3 = 0
            r4 = 2
            if (r1 != r4) goto L_0x0012
            monitor-exit(r0)
            return r3
        L_0x0012:
            canHookArtFlag = r4     // Catch:{ all -> 0x0057 }
            int r1 = android.os.Build.VERSION.SDK_INT     // Catch:{ all -> 0x0057 }
            r5 = 21
            if (r1 < r5) goto L_0x0055
            int r1 = android.os.Build.VERSION.SDK_INT     // Catch:{ all -> 0x0057 }
            r5 = 22
            if (r1 <= r5) goto L_0x0021
            goto L_0x0055
        L_0x0021:
            boolean r1 = isArt()     // Catch:{ all -> 0x0057 }
            boolean r5 = isX86()     // Catch:{ all -> 0x0057 }
            boolean r6 = isYunOs()     // Catch:{ all -> 0x0057 }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x0057 }
            java.lang.String r8 = "isArt="
            r7.<init>(r8)     // Catch:{ all -> 0x0057 }
            r7.append(r1)     // Catch:{ all -> 0x0057 }
            java.lang.String r8 = ", isX86="
            r7.append(r8)     // Catch:{ all -> 0x0057 }
            r7.append(r5)     // Catch:{ all -> 0x0057 }
            java.lang.String r8 = ", isYunOs="
            r7.append(r8)     // Catch:{ all -> 0x0057 }
            r7.append(r6)     // Catch:{ all -> 0x0057 }
            if (r1 == 0) goto L_0x0051
            if (r5 != 0) goto L_0x0051
            if (r6 != 0) goto L_0x0051
            canHookArtFlag = r2     // Catch:{ all -> 0x0057 }
            monitor-exit(r0)
            return r2
        L_0x0051:
            canHookArtFlag = r4     // Catch:{ all -> 0x0057 }
            monitor-exit(r0)
            return r3
        L_0x0055:
            monitor-exit(r0)
            return r3
        L_0x0057:
            r1 = move-exception
            monitor-exit(r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.quinox.utils.SystemUtil.canHookArt():boolean");
    }

    public static synchronized boolean canHookLite() {
        synchronized (SystemUtil.class) {
            if (VERSION.SDK_INT >= 19 && VERSION.SDK_INT <= 27) {
                boolean supportARM = supportARM();
                boolean isX86 = isX86();
                boolean isYunOs = isYunOs();
                if (supportARM && !isX86 && !isYunOs) {
                    return true;
                }
            }
            return false;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0064, code lost:
        return false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized boolean canHook() {
        /*
            java.lang.Class<com.alipay.mobile.quinox.utils.SystemUtil> r0 = com.alipay.mobile.quinox.utils.SystemUtil.class
            monitor-enter(r0)
            int r1 = canHookFlag     // Catch:{ all -> 0x0065 }
            r2 = 1
            if (r1 != r2) goto L_0x000a
            monitor-exit(r0)
            return r2
        L_0x000a:
            int r1 = canHookFlag     // Catch:{ all -> 0x0065 }
            r3 = 0
            r4 = 2
            if (r1 != r4) goto L_0x0012
            monitor-exit(r0)
            return r3
        L_0x0012:
            canHookFlag = r4     // Catch:{ all -> 0x0065 }
            int r1 = android.os.Build.VERSION.SDK_INT     // Catch:{ all -> 0x0065 }
            r5 = 14
            if (r1 < r5) goto L_0x0063
            int r1 = android.os.Build.VERSION.SDK_INT     // Catch:{ all -> 0x0065 }
            r5 = 19
            if (r1 <= r5) goto L_0x0021
            goto L_0x0063
        L_0x0021:
            boolean r1 = isArt()     // Catch:{ all -> 0x0065 }
            boolean r5 = supportARM()     // Catch:{ all -> 0x0065 }
            boolean r6 = isX86()     // Catch:{ all -> 0x0065 }
            boolean r7 = isYunOs()     // Catch:{ all -> 0x0065 }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ all -> 0x0065 }
            java.lang.String r9 = "isArt="
            r8.<init>(r9)     // Catch:{ all -> 0x0065 }
            r8.append(r1)     // Catch:{ all -> 0x0065 }
            java.lang.String r9 = ", supportArm="
            r8.append(r9)     // Catch:{ all -> 0x0065 }
            r8.append(r5)     // Catch:{ all -> 0x0065 }
            java.lang.String r9 = ", isX86="
            r8.append(r9)     // Catch:{ all -> 0x0065 }
            r8.append(r6)     // Catch:{ all -> 0x0065 }
            java.lang.String r9 = ", isYunOs="
            r8.append(r9)     // Catch:{ all -> 0x0065 }
            r8.append(r7)     // Catch:{ all -> 0x0065 }
            if (r1 != 0) goto L_0x005f
            if (r5 == 0) goto L_0x005f
            if (r6 != 0) goto L_0x005f
            if (r7 != 0) goto L_0x005f
            canHookFlag = r2     // Catch:{ all -> 0x0065 }
            monitor-exit(r0)
            return r2
        L_0x005f:
            canHookFlag = r4     // Catch:{ all -> 0x0065 }
            monitor-exit(r0)
            return r3
        L_0x0063:
            monitor-exit(r0)
            return r3
        L_0x0065:
            r1 = move-exception
            monitor-exit(r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.quinox.utils.SystemUtil.canHook():boolean");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:31:?, code lost:
        isUILaunch = 1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean isUIEntryLaunch(android.content.Context r6) {
        /*
            int r0 = isUILaunch
            r1 = 1
            if (r0 != r1) goto L_0x0006
            return r1
        L_0x0006:
            int r0 = isUILaunch
            r2 = 2
            r3 = 0
            if (r0 != r2) goto L_0x000d
            return r3
        L_0x000d:
            isUILaunch = r2
            java.util.Map r0 = com.alipay.mobile.quinox.utils.MonitorLogger.getStartupReason()     // Catch:{ Throwable -> 0x00ab }
            java.lang.String r2 = ""
            if (r0 == 0) goto L_0x0020
            java.lang.String r2 = "ComponentName"
            java.lang.Object r0 = r0.get(r2)     // Catch:{ Throwable -> 0x00ab }
            r2 = r0
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ Throwable -> 0x00ab }
        L_0x0020:
            boolean r0 = IS_ALIPAY     // Catch:{ Throwable -> 0x00ab }
            if (r0 == 0) goto L_0x0041
            java.lang.String r6 = "com.eg.android.AlipayGphone.AlipayLogin"
            boolean r6 = android.text.TextUtils.equals(r2, r6)     // Catch:{ Throwable -> 0x00ab }
            if (r6 != 0) goto L_0x003c
            java.lang.String r6 = "com.alipay.mobile.quinox.LauncherActivity.alias"
            boolean r6 = android.text.TextUtils.equals(r2, r6)     // Catch:{ Throwable -> 0x00ab }
            if (r6 != 0) goto L_0x003c
            java.lang.String r6 = "com.alipay.mobile.quinox.SchemeLauncherActivity"
            boolean r6 = android.text.TextUtils.equals(r2, r6)     // Catch:{ Throwable -> 0x00ab }
            if (r6 == 0) goto L_0x00a9
        L_0x003c:
            isUILaunch = r1     // Catch:{ Throwable -> 0x003f }
            goto L_0x00b2
        L_0x003f:
            r6 = move-exception
            goto L_0x00ad
        L_0x0041:
            android.content.pm.PackageManager r0 = r6.getPackageManager()     // Catch:{ Throwable -> 0x00ab }
            android.content.Intent r4 = new android.content.Intent     // Catch:{ Throwable -> 0x00ab }
            r4.<init>()     // Catch:{ Throwable -> 0x00ab }
            java.lang.String r5 = r6.getPackageName()     // Catch:{ Throwable -> 0x00ab }
            r4.setPackage(r5)     // Catch:{ Throwable -> 0x00ab }
            java.lang.String r5 = "android.intent.action.MAIN"
            r4.setAction(r5)     // Catch:{ Throwable -> 0x00ab }
            java.lang.String r5 = "android.intent.category.LAUNCHER"
            r4.addCategory(r5)     // Catch:{ Throwable -> 0x00ab }
            java.util.List r4 = r0.queryIntentActivities(r4, r3)     // Catch:{ Throwable -> 0x00ab }
            android.content.Intent r5 = new android.content.Intent     // Catch:{ Throwable -> 0x00ab }
            r5.<init>()     // Catch:{ Throwable -> 0x00ab }
            java.lang.String r6 = r6.getPackageName()     // Catch:{ Throwable -> 0x00ab }
            r5.setPackage(r6)     // Catch:{ Throwable -> 0x00ab }
            java.lang.String r6 = "android.intent.action.VIEW"
            r5.setAction(r6)     // Catch:{ Throwable -> 0x00ab }
            java.lang.String r6 = "android.intent.category.DEFAULT"
            r5.addCategory(r6)     // Catch:{ Throwable -> 0x00ab }
            java.lang.String r6 = "android.intent.category.BROWSABLE"
            r5.addCategory(r6)     // Catch:{ Throwable -> 0x00ab }
            java.lang.String r6 = "alipays://test"
            android.net.Uri r6 = android.net.Uri.parse(r6)     // Catch:{ Throwable -> 0x00ab }
            r5.setData(r6)     // Catch:{ Throwable -> 0x00ab }
            java.util.List r6 = r0.queryIntentActivities(r5, r3)     // Catch:{ Throwable -> 0x00ab }
            r4.addAll(r6)     // Catch:{ Throwable -> 0x00ab }
            java.util.Iterator r6 = r4.iterator()     // Catch:{ Throwable -> 0x00ab }
        L_0x008e:
            boolean r0 = r6.hasNext()     // Catch:{ Throwable -> 0x00ab }
            if (r0 == 0) goto L_0x00a9
            java.lang.Object r0 = r6.next()     // Catch:{ Throwable -> 0x00ab }
            android.content.pm.ResolveInfo r0 = (android.content.pm.ResolveInfo) r0     // Catch:{ Throwable -> 0x00ab }
            if (r2 == 0) goto L_0x008e
            android.content.pm.ActivityInfo r0 = r0.activityInfo     // Catch:{ Throwable -> 0x00ab }
            java.lang.String r0 = r0.name     // Catch:{ Throwable -> 0x00ab }
            boolean r0 = r2.contains(r0)     // Catch:{ Throwable -> 0x00ab }
            if (r0 == 0) goto L_0x008e
            isUILaunch = r1     // Catch:{ Throwable -> 0x003f }
            goto L_0x00b2
        L_0x00a9:
            r1 = 0
            goto L_0x00b2
        L_0x00ab:
            r6 = move-exception
            r1 = 0
        L_0x00ad:
            java.lang.String r0 = "SystemUtil"
            com.alipay.mobile.quinox.utils.TraceLogger.w(r0, r6)
        L_0x00b2:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.quinox.utils.SystemUtil.isUIEntryLaunch(android.content.Context):boolean");
    }

    public static boolean canOptGC() {
        return VERSION.SDK_INT == 19;
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x001e A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x001f A[SYNTHETIC, Splitter:B:12:0x001f] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void checkAndDisableArrayMapCache(java.lang.String r15) {
        /*
            r0 = 0
            r1 = 1
            java.lang.String r2 = "android.util.ArrayMap.allocArrays"
            boolean r2 = r15.contains(r2)     // Catch:{ Throwable -> 0x0014 }
            if (r2 != 0) goto L_0x0012
            java.lang.String r2 = "android.util.ArrayMap.freeArrays"
            boolean r15 = r15.contains(r2)     // Catch:{ Throwable -> 0x0014 }
            if (r15 == 0) goto L_0x001b
        L_0x0012:
            r15 = 1
            goto L_0x001c
        L_0x0014:
            java.lang.String r15 = "ArrayMap"
            java.lang.String r2 = "checkAndDisableArrayMapCache error 1"
            com.alipay.mobile.quinox.utils.TraceLogger.e(r15, r2)
        L_0x001b:
            r15 = 0
        L_0x001c:
            if (r15 != 0) goto L_0x001f
            return
        L_0x001f:
            int r15 = android.os.Build.VERSION.SDK_INT     // Catch:{ Throwable -> 0x0100 }
            r2 = 19
            if (r15 >= r2) goto L_0x0026
            return
        L_0x0026:
            java.lang.String r15 = "ArrayMap"
            java.lang.String r2 = "start"
            com.alipay.mobile.quinox.utils.TraceLogger.d(r15, r2)     // Catch:{ Throwable -> 0x0100 }
            java.lang.Class<android.util.ArrayMap> r15 = android.util.ArrayMap.class
            java.lang.String r2 = "mBaseCache"
            java.lang.reflect.Field r2 = r15.getDeclaredField(r2)     // Catch:{ Throwable -> 0x0100 }
            r2.setAccessible(r1)     // Catch:{ Throwable -> 0x0100 }
            java.lang.String r3 = "mBaseCacheSize"
            java.lang.reflect.Field r3 = r15.getDeclaredField(r3)     // Catch:{ Throwable -> 0x0100 }
            r3.setAccessible(r1)     // Catch:{ Throwable -> 0x0100 }
            java.lang.String r4 = "mTwiceBaseCache"
            java.lang.reflect.Field r4 = r15.getDeclaredField(r4)     // Catch:{ Throwable -> 0x0100 }
            r4.setAccessible(r1)     // Catch:{ Throwable -> 0x0100 }
            java.lang.String r5 = "mTwiceBaseCacheSize"
            java.lang.reflect.Field r5 = r15.getDeclaredField(r5)     // Catch:{ Throwable -> 0x0100 }
            r5.setAccessible(r1)     // Catch:{ Throwable -> 0x0100 }
            java.lang.String r6 = "CACHE_SIZE"
            java.lang.reflect.Field r15 = r15.getDeclaredField(r6)     // Catch:{ Throwable -> 0x0100 }
            r15.setAccessible(r1)     // Catch:{ Throwable -> 0x0100 }
            r6 = 0
            int r15 = r15.getInt(r6)     // Catch:{ Throwable -> 0x0100 }
            int r15 = r15 + r1
            java.lang.Class<android.util.ArrayMap> r1 = android.util.ArrayMap.class
            monitor-enter(r1)     // Catch:{ Throwable -> 0x00de }
            java.lang.Object r7 = r2.get(r6)     // Catch:{ all -> 0x00db }
            java.lang.Object[] r7 = (java.lang.Object[]) r7     // Catch:{ all -> 0x00db }
            int r8 = r3.getInt(r6)     // Catch:{ all -> 0x00db }
            java.lang.Object r9 = r4.get(r6)     // Catch:{ all -> 0x00db }
            java.lang.Object[] r9 = (java.lang.Object[]) r9     // Catch:{ all -> 0x00db }
            int r10 = r5.getInt(r6)     // Catch:{ all -> 0x00db }
        L_0x007a:
            if (r7 == 0) goto L_0x00a9
            r11 = 0
        L_0x007d:
            int r12 = r7.length     // Catch:{ all -> 0x00db }
            if (r11 >= r12) goto L_0x009b
            java.lang.String r12 = "ArrayMap"
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ all -> 0x00db }
            java.lang.String r14 = "mBaseCache ArrayItem "
            r13.<init>(r14)     // Catch:{ all -> 0x00db }
            r13.append(r11)     // Catch:{ all -> 0x00db }
            r14 = r7[r11]     // Catch:{ all -> 0x00db }
            r13.append(r14)     // Catch:{ all -> 0x00db }
            java.lang.String r13 = r13.toString()     // Catch:{ all -> 0x00db }
            com.alipay.mobile.quinox.utils.TraceLogger.i(r12, r13)     // Catch:{ all -> 0x00db }
            int r11 = r11 + 1
            goto L_0x007d
        L_0x009b:
            r11 = r7[r0]     // Catch:{ all -> 0x00db }
            boolean r11 = r11 instanceof java.lang.Object[]     // Catch:{ all -> 0x00db }
            if (r11 == 0) goto L_0x00a5
            r7 = r7[r0]     // Catch:{ all -> 0x00db }
            java.lang.Object[] r7 = (java.lang.Object[]) r7     // Catch:{ all -> 0x00db }
        L_0x00a5:
            int r8 = r8 + -1
            if (r8 > 0) goto L_0x007a
        L_0x00a9:
            if (r9 == 0) goto L_0x00d9
            r7 = 0
        L_0x00ac:
            int r8 = r9.length     // Catch:{ all -> 0x00db }
            if (r7 >= r8) goto L_0x00ca
            java.lang.String r8 = "ArrayMap"
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ all -> 0x00db }
            java.lang.String r12 = "mTwiceBaseCache ArrayItem "
            r11.<init>(r12)     // Catch:{ all -> 0x00db }
            r11.append(r7)     // Catch:{ all -> 0x00db }
            r12 = r9[r7]     // Catch:{ all -> 0x00db }
            r11.append(r12)     // Catch:{ all -> 0x00db }
            java.lang.String r11 = r11.toString()     // Catch:{ all -> 0x00db }
            com.alipay.mobile.quinox.utils.TraceLogger.i(r8, r11)     // Catch:{ all -> 0x00db }
            int r7 = r7 + 1
            goto L_0x00ac
        L_0x00ca:
            r7 = r9[r0]     // Catch:{ all -> 0x00db }
            boolean r7 = r7 instanceof java.lang.Object[]     // Catch:{ all -> 0x00db }
            if (r7 == 0) goto L_0x00d5
            r7 = r9[r0]     // Catch:{ all -> 0x00db }
            java.lang.Object[] r7 = (java.lang.Object[]) r7     // Catch:{ all -> 0x00db }
            r9 = r7
        L_0x00d5:
            int r10 = r10 + -1
            if (r10 > 0) goto L_0x00a9
        L_0x00d9:
            monitor-exit(r1)     // Catch:{ all -> 0x00db }
            goto L_0x00e5
        L_0x00db:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x00db }
            throw r0     // Catch:{ Throwable -> 0x00de }
        L_0x00de:
            java.lang.String r0 = "ArrayMap"
            java.lang.String r1 = "checkAndDisableArrayMapCache error 2"
            com.alipay.mobile.quinox.utils.TraceLogger.e(r0, r1)     // Catch:{ Throwable -> 0x0100 }
        L_0x00e5:
            java.lang.Class<android.util.ArrayMap> r0 = android.util.ArrayMap.class
            monitor-enter(r0)     // Catch:{ Throwable -> 0x0100 }
            r2.set(r6, r6)     // Catch:{ all -> 0x00fd }
            r3.setInt(r6, r15)     // Catch:{ all -> 0x00fd }
            r4.set(r6, r6)     // Catch:{ all -> 0x00fd }
            r5.setInt(r6, r15)     // Catch:{ all -> 0x00fd }
            monitor-exit(r0)     // Catch:{ all -> 0x00fd }
            java.lang.String r15 = "ArrayMap"
            java.lang.String r0 = "end"
            com.alipay.mobile.quinox.utils.TraceLogger.d(r15, r0)     // Catch:{ Throwable -> 0x0100 }
            return
        L_0x00fd:
            r15 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x00fd }
            throw r15     // Catch:{ Throwable -> 0x0100 }
        L_0x0100:
            java.lang.String r15 = "ArrayMap"
            java.lang.String r0 = "checkAndDisableArrayMapCache error 3"
            com.alipay.mobile.quinox.utils.TraceLogger.e(r15, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.quinox.utils.SystemUtil.checkAndDisableArrayMapCache(java.lang.String):void");
    }

    public static boolean isUILaunch() {
        try {
            Map<String, String> startupReason = MonitorLogger.getStartupReason();
            String str = "";
            if (startupReason != null) {
                str = startupReason.get(ProcessInfo.SR_RECORD_TYPE);
            }
            return ProcessInfo.RECORD_ACTIVITY.equals(str);
        } catch (Throwable th) {
            TraceLogger.w((String) TAG, th);
            return false;
        }
    }

    public static boolean isPreloadLaunch() {
        if (sPreloadLaunch == null) {
            sPreloadLaunch = Boolean.valueOf(!TextUtils.isEmpty(getCommonPreloadBy()));
        }
        return sPreloadLaunch.booleanValue();
    }

    public static boolean isHuaweiPreloadLaunch() {
        if (sHuaweiPreloadLaunch == null) {
            try {
                Map<String, String> startupReason = MonitorLogger.getStartupReason();
                String str = "";
                if (startupReason != null) {
                    str = startupReason.get(ProcessInfo.SR_RECORD_TYPE);
                }
                sHuaweiPreloadLaunch = Boolean.valueOf("HuaweiPreload".equals(str));
            } catch (Throwable th) {
                TraceLogger.w((String) TAG, th);
            }
        }
        if (sHuaweiPreloadLaunch == null) {
            return false;
        }
        return sHuaweiPreloadLaunch.booleanValue();
    }

    public static boolean isBerserkerPreloadLaunch() {
        try {
            Map<String, String> startupReason = MonitorLogger.getStartupReason();
            if (startupReason != null && TextUtils.equals(startupReason.get(ProcessInfo.SR_COMPONENT_NAME), "com.alipay.berserker.service.PreloadService")) {
                return true;
            }
        } catch (Throwable th) {
            TraceLogger.w((String) TAG, th);
        }
        return false;
    }

    public static String getCommonPreloadBy() {
        return sPreloadBy;
    }

    public static boolean isDynamicReleaseLaunch() {
        try {
            Map<String, String> startupReason = MonitorLogger.getStartupReason();
            String str = "";
            if (startupReason != null) {
                str = startupReason.get(ProcessInfo.SR_COMPONENT_NAME);
            }
            return "com.alipay.android.phone.mobilecommon.dynamicrelease.DynamicReleaseProcessService".equals(str);
        } catch (Throwable th) {
            TraceLogger.w((String) TAG, th);
            return false;
        }
    }

    public static String reflectGetReferrer(Activity activity) {
        try {
            if (VERSION.SDK_INT > 21) {
                if (activity != null) {
                    return (String) ReflectUtil.getFieldValue(Class.forName("android.app.Activity"), activity, "mReferrer");
                }
            }
            return null;
        } catch (Throwable th) {
            TraceLogger.w((String) TAG, th);
            return null;
        }
    }

    public static boolean isNubiaDevice() {
        try {
            if (DeviceProperty.ALIAS_NUBIA.equals(Build.BRAND.toLowerCase()) || DeviceProperty.ALIAS_NUBIA.equals(Build.MANUFACTURER.toLowerCase())) {
                return true;
            }
        } catch (Throwable th) {
            TraceLogger.w((String) TAG, th);
        }
        return false;
    }

    public static boolean isNubiaBugDevice() {
        if (!isNubiaDevice() || VERSION.SDK_INT < 27) {
            return false;
        }
        try {
            return !TextUtils.equals((String) Class.forName("android.os.SystemProperties").getMethod("get", new Class[]{String.class}).invoke(null, new Object[]{"ro.nubia.AlipayGphone"}), "true");
        } catch (Throwable th) {
            TraceLogger.w((String) TAG, th);
        }
    }

    public static boolean isOppoPreload(Context context) {
        try {
            if (isOppoDevice()) {
                String packageName = context.getPackageName();
                ActivityManager activityManager = (ActivityManager) context.getSystemService(WidgetType.ACTIVITY);
                if (activityManager == null) {
                    TraceLogger.w((String) TAG, (String) "am is null.");
                }
                return ((Boolean) ActivityManager.class.getMethod("isOppoPreload", new Class[]{String.class}).invoke(activityManager, new Object[]{packageName})).booleanValue();
            }
        } catch (Throwable th) {
            TraceLogger.w((String) TAG, th);
        }
        return false;
    }

    public static boolean isOppoDevice() {
        return DeviceProperty.ALIAS_OPPO.equals(Build.BRAND.toLowerCase()) || DeviceProperty.ALIAS_OPPO.equals(Build.MANUFACTURER.toLowerCase());
    }

    public static int getUserId(int i) {
        return i / 100000;
    }

    public static boolean isParallelUserId() {
        return getUserId(Process.myUid()) == 999;
    }

    public static boolean shouldPreLoadCookieManager() {
        boolean z = false;
        try {
            if (!"5.1.1".equals(VERSION.RELEASE)) {
                z = true;
            }
            return z;
        } catch (Throwable th) {
            TraceLogger.w((String) TAG, th);
            return false;
        }
    }
}
