package com.alipay.mobile.common.nativecrash;

import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.ProcessInfo;
import java.util.regex.Pattern;

public class CrashFilterUtils {
    public static final String MPAAS_PRODUCT_VERSION = "mPaaSProductVersion";

    public static int isIgnoreCrash(String crashInfo) {
        if (TextUtils.isEmpty(crashInfo)) {
            return -1;
        }
        try {
            long time = SystemClock.uptimeMillis();
            int crashSignal = UcCrashInfo.parse(crashInfo).getCrashSignal();
            LoggerFactory.getTraceLogger().info("CrashFilter", "isIgnoreCrash spend " + (SystemClock.uptimeMillis() - time));
            return crashSignal;
        } catch (Throwable e) {
            LoggerFactory.getTraceLogger().error((String) "CrashFilter", "isIgnoreCrash: " + e);
            return -1;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:42:0x008e A[SYNTHETIC, Splitter:B:42:0x008e] */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x00a2 A[SYNTHETIC, Splitter:B:48:0x00a2] */
    /* JADX WARNING: Removed duplicated region for block: B:64:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean isCurrentVersion(java.lang.String r10, java.lang.String r11, boolean r12) {
        /*
            r7 = 0
            r8 = 1
            if (r12 != 0) goto L_0x0005
        L_0x0004:
            return r8
        L_0x0005:
            boolean r9 = android.text.TextUtils.isEmpty(r11)
            if (r9 != 0) goto L_0x0004
            boolean r9 = android.text.TextUtils.isEmpty(r10)
            if (r9 != 0) goto L_0x0004
            r2 = 0
            java.io.StringReader r3 = new java.io.StringReader     // Catch:{ Throwable -> 0x0082 }
            r3.<init>(r10)     // Catch:{ Throwable -> 0x0082 }
            java.io.BufferedReader r0 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x00b4, all -> 0x00b1 }
            r9 = 8192(0x2000, float:1.14794E-41)
            r0.<init>(r3, r9)     // Catch:{ Throwable -> 0x00b4, all -> 0x00b1 }
        L_0x001e:
            java.lang.String r1 = r0.readLine()     // Catch:{ Throwable -> 0x00b4, all -> 0x00b1 }
            if (r1 == 0) goto L_0x0072
            java.lang.String r9 = "mPaaSProductVersion"
            boolean r9 = r1.startsWith(r9)     // Catch:{ Throwable -> 0x00b4, all -> 0x00b1 }
            if (r9 == 0) goto L_0x001e
            java.lang.String r9 = ":"
            int r4 = r1.lastIndexOf(r9)     // Catch:{ Throwable -> 0x00b4, all -> 0x00b1 }
            if (r4 < 0) goto L_0x0063
            int r9 = r1.length()     // Catch:{ Throwable -> 0x00b4, all -> 0x00b1 }
            int r9 = r9 + -1
            if (r4 >= r9) goto L_0x0063
            int r9 = r4 + 1
            java.lang.String r9 = r1.substring(r9)     // Catch:{ Throwable -> 0x00b4, all -> 0x00b1 }
            java.lang.String r6 = r9.trim()     // Catch:{ Throwable -> 0x00b4, all -> 0x00b1 }
            boolean r9 = android.text.TextUtils.isEmpty(r6)     // Catch:{ Throwable -> 0x00b4, all -> 0x00b1 }
            if (r9 != 0) goto L_0x0052
            boolean r9 = r6.equals(r11)     // Catch:{ Throwable -> 0x00b4, all -> 0x00b1 }
            if (r9 == 0) goto L_0x0053
        L_0x0052:
            r7 = r8
        L_0x0053:
            r3.close()     // Catch:{ Throwable -> 0x0058 }
        L_0x0056:
            r8 = r7
            goto L_0x0004
        L_0x0058:
            r5 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r8 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r9 = "CrashFilter"
            r8.warn(r9, r5)
            goto L_0x0056
        L_0x0063:
            r3.close()     // Catch:{ Throwable -> 0x0067 }
            goto L_0x0004
        L_0x0067:
            r5 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r7 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r9 = "CrashFilter"
            r7.warn(r9, r5)
            goto L_0x0004
        L_0x0072:
            r3.close()     // Catch:{ Throwable -> 0x0077 }
        L_0x0075:
            r8 = r7
            goto L_0x0004
        L_0x0077:
            r5 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r8 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r9 = "CrashFilter"
            r8.warn(r9, r5)
            goto L_0x0075
        L_0x0082:
            r5 = move-exception
        L_0x0083:
            com.alipay.mobile.common.logging.api.trace.TraceLogger r7 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x009f }
            java.lang.String r9 = "CrashFilter"
            r7.warn(r9, r5)     // Catch:{ all -> 0x009f }
            if (r2 == 0) goto L_0x0004
            r2.close()     // Catch:{ Throwable -> 0x0093 }
            goto L_0x0004
        L_0x0093:
            r5 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r7 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r9 = "CrashFilter"
            r7.warn(r9, r5)
            goto L_0x0004
        L_0x009f:
            r7 = move-exception
        L_0x00a0:
            if (r2 == 0) goto L_0x00a5
            r2.close()     // Catch:{ Throwable -> 0x00a6 }
        L_0x00a5:
            throw r7
        L_0x00a6:
            r5 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r8 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r9 = "CrashFilter"
            r8.warn(r9, r5)
            goto L_0x00a5
        L_0x00b1:
            r7 = move-exception
            r2 = r3
            goto L_0x00a0
        L_0x00b4:
            r5 = move-exception
            r2 = r3
            goto L_0x0083
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.nativecrash.CrashFilterUtils.isCurrentVersion(java.lang.String, java.lang.String, boolean):boolean");
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x005c  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x00b7 A[SYNTHETIC, Splitter:B:50:0x00b7] */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x00c9 A[SYNTHETIC, Splitter:B:56:0x00c9] */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x00de  */
    /* JADX WARNING: Removed duplicated region for block: B:95:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean isKnownInvalidCrash(java.lang.String r14) {
        /*
            r11 = 1
            r10 = 0
            boolean r12 = android.text.TextUtils.isEmpty(r14)
            if (r12 == 0) goto L_0x0009
        L_0x0008:
            return r10
        L_0x0009:
            r8 = 0
            r5 = 0
            r2 = 0
            java.io.StringReader r6 = new java.io.StringReader     // Catch:{ Throwable -> 0x00ab }
            r6.<init>(r14)     // Catch:{ Throwable -> 0x00ab }
            java.io.BufferedReader r3 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x00ab }
            r12 = 8192(0x2000, float:1.14794E-41)
            r3.<init>(r6, r12)     // Catch:{ Throwable -> 0x00ab }
        L_0x0018:
            java.lang.String r4 = r3.readLine()     // Catch:{ Throwable -> 0x013c, all -> 0x0139 }
            if (r4 == 0) goto L_0x0052
            java.lang.String r12 = "StartupComponent"
            boolean r12 = r4.startsWith(r12)     // Catch:{ Throwable -> 0x013c, all -> 0x0139 }
            if (r12 == 0) goto L_0x0076
            r12 = 58
            int r7 = r4.lastIndexOf(r12)     // Catch:{ Throwable -> 0x013c, all -> 0x0139 }
            if (r7 < 0) goto L_0x0046
            int r12 = r4.length()     // Catch:{ Throwable -> 0x013c, all -> 0x0139 }
            int r12 = r12 + -1
            if (r7 >= r12) goto L_0x0046
            int r12 = r7 + 1
            java.lang.String r0 = r4.substring(r12)     // Catch:{ Throwable -> 0x013c, all -> 0x0139 }
            boolean r12 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Throwable -> 0x013c, all -> 0x0139 }
            if (r12 != 0) goto L_0x0046
            java.lang.String r8 = r0.trim()     // Catch:{ Throwable -> 0x013c, all -> 0x0139 }
        L_0x0046:
            boolean r12 = android.text.TextUtils.isEmpty(r8)     // Catch:{ Throwable -> 0x013c, all -> 0x0139 }
            if (r12 != 0) goto L_0x0018
            boolean r12 = android.text.TextUtils.isEmpty(r5)     // Catch:{ Throwable -> 0x013c, all -> 0x0139 }
            if (r12 != 0) goto L_0x0018
        L_0x0052:
            r3.close()     // Catch:{ Throwable -> 0x009f }
            r2 = r3
        L_0x0056:
            boolean r12 = android.text.TextUtils.isEmpty(r8)
            if (r12 != 0) goto L_0x00d8
            java.lang.String r12 = "com.lbe.doubleagent.client.proxy.ActivityProxy"
            boolean r12 = r8.contains(r12)
            if (r12 != 0) goto L_0x0074
            java.lang.String r12 = "com.lbe.doubleagent.client.proxy.ServiceProxy"
            boolean r12 = r8.contains(r12)
            if (r12 != 0) goto L_0x0074
            java.lang.String r12 = "com.morgoo.droidplugin.stub.ServiceStub"
            boolean r12 = r8.contains(r12)
            if (r12 == 0) goto L_0x00d8
        L_0x0074:
            r10 = r11
            goto L_0x0008
        L_0x0076:
            java.lang.String r12 = "[DEBUG] Check logs in directory: "
            boolean r12 = r4.startsWith(r12)     // Catch:{ Throwable -> 0x013c, all -> 0x0139 }
            if (r12 == 0) goto L_0x0046
            r12 = 58
            int r7 = r4.lastIndexOf(r12)     // Catch:{ Throwable -> 0x013c, all -> 0x0139 }
            if (r7 < 0) goto L_0x0046
            int r12 = r4.length()     // Catch:{ Throwable -> 0x013c, all -> 0x0139 }
            int r12 = r12 + -1
            if (r7 >= r12) goto L_0x0046
            int r12 = r7 + 1
            java.lang.String r1 = r4.substring(r12)     // Catch:{ Throwable -> 0x013c, all -> 0x0139 }
            boolean r12 = android.text.TextUtils.isEmpty(r1)     // Catch:{ Throwable -> 0x013c, all -> 0x0139 }
            if (r12 != 0) goto L_0x0046
            java.lang.String r5 = r1.trim()     // Catch:{ Throwable -> 0x013c, all -> 0x0139 }
            goto L_0x0046
        L_0x009f:
            r9 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r12 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r13 = "CrashFilter"
            r12.warn(r13, r9)
            r2 = r3
            goto L_0x0056
        L_0x00ab:
            r9 = move-exception
        L_0x00ac:
            com.alipay.mobile.common.logging.api.trace.TraceLogger r12 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x00c6 }
            java.lang.String r13 = "CrashFilter"
            r12.warn(r13, r9)     // Catch:{ all -> 0x00c6 }
            if (r2 == 0) goto L_0x0056
            r2.close()     // Catch:{ Throwable -> 0x00bb }
            goto L_0x0056
        L_0x00bb:
            r9 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r12 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r13 = "CrashFilter"
            r12.warn(r13, r9)
            goto L_0x0056
        L_0x00c6:
            r10 = move-exception
        L_0x00c7:
            if (r2 == 0) goto L_0x00cc
            r2.close()     // Catch:{ Throwable -> 0x00cd }
        L_0x00cc:
            throw r10
        L_0x00cd:
            r9 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r11 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r12 = "CrashFilter"
            r11.warn(r12, r9)
            goto L_0x00cc
        L_0x00d8:
            boolean r12 = android.text.TextUtils.isEmpty(r5)
            if (r12 != 0) goto L_0x0008
            java.lang.String r12 = "/com.lbe.parallel/parallel/"
            boolean r12 = r5.contains(r12)
            if (r12 != 0) goto L_0x0136
            java.lang.String r12 = "/com.qihoo.magic/Plugin/"
            boolean r12 = r5.contains(r12)
            if (r12 != 0) goto L_0x0136
            java.lang.String r12 = "/com.dobe.sandbox/"
            boolean r12 = r5.contains(r12)
            if (r12 != 0) goto L_0x0136
            java.lang.String r12 = "/com.excelliance.dualaid/"
            boolean r12 = r5.contains(r12)
            if (r12 != 0) goto L_0x0136
            java.lang.String r12 = "/com.ppt.double_assistant/"
            boolean r12 = r5.contains(r12)
            if (r12 != 0) goto L_0x0136
            java.lang.String r12 = "/com.doubleopen"
            boolean r12 = r5.contains(r12)
            if (r12 != 0) goto L_0x0136
            java.lang.String r12 = "/com.bfire.da.nui/"
            boolean r12 = r5.contains(r12)
            if (r12 != 0) goto L_0x0136
            java.lang.String r12 = "/com.plda.dualapp/"
            boolean r12 = r5.contains(r12)
            if (r12 != 0) goto L_0x0136
            java.lang.String r12 = "com.qgwapp.shadowside"
            boolean r12 = r5.contains(r12)
            if (r12 != 0) goto L_0x0136
            java.lang.String r12 = "dkmodel"
            boolean r12 = r5.contains(r12)
            if (r12 == 0) goto L_0x0008
            java.lang.String r12 = "virtual"
            boolean r12 = r5.contains(r12)
            if (r12 == 0) goto L_0x0008
        L_0x0136:
            r10 = r11
            goto L_0x0008
        L_0x0139:
            r10 = move-exception
            r2 = r3
            goto L_0x00c7
        L_0x013c:
            r9 = move-exception
            r2 = r3
            goto L_0x00ac
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.nativecrash.CrashFilterUtils.isKnownInvalidCrash(java.lang.String):boolean");
    }

    private static boolean a(String crashInfo, String processName) {
        if (TextUtils.isEmpty(crashInfo) || TextUtils.isEmpty(processName)) {
            return false;
        }
        try {
            long time = SystemClock.uptimeMillis();
            boolean find = Pattern.compile(String.format(">>> %s <<<", new Object[]{processName})).matcher(crashInfo).find();
            LoggerFactory.getTraceLogger().info("CrashFilter", processName + " call 'isTargetProcess' spend " + (SystemClock.uptimeMillis() - time));
            return find;
        } catch (Throwable e) {
            LoggerFactory.getTraceLogger().error((String) "CrashFilter", "isTargetProcess: " + e);
            return false;
        }
    }

    public static String getProcessAlias(String crashInfo) {
        if (a(crashInfo, LoggerFactory.getProcessInfo().getMainProcessName())) {
            return "main";
        }
        if (a(crashInfo, LoggerFactory.getProcessInfo().getPushProcessName())) {
            return "push";
        }
        if (a(crashInfo, LoggerFactory.getProcessInfo().getToolsProcessName())) {
            return ProcessInfo.ALIAS_TOOLS;
        }
        if (a(crashInfo, LoggerFactory.getProcessInfo().getExtProcessName())) {
            return ProcessInfo.ALIAS_EXT;
        }
        if (a(crashInfo, new StringBuilder().append(LoggerFactory.getProcessInfo().getPackageName()).append(":lite1").toString()) || a(crashInfo, new StringBuilder().append(LoggerFactory.getProcessInfo().getPackageName()).append(":lite2").toString()) || a(crashInfo, new StringBuilder().append(LoggerFactory.getProcessInfo().getPackageName()).append(":lite3").toString()) || a(crashInfo, new StringBuilder().append(LoggerFactory.getProcessInfo().getPackageName()).append(":lite4").toString()) || a(crashInfo, new StringBuilder().append(LoggerFactory.getProcessInfo().getPackageName()).append(":lite5").toString())) {
            return ProcessInfo.ALIAS_LITE;
        }
        return "unknown";
    }

    /* JADX WARNING: Removed duplicated region for block: B:28:0x0061 A[SYNTHETIC, Splitter:B:28:0x0061] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0073 A[SYNTHETIC, Splitter:B:34:0x0073] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getNativeCrashInfo(java.lang.String r11, java.lang.String r12) {
        /*
            java.lang.String r3 = ""
            boolean r8 = android.text.TextUtils.isEmpty(r11)
            if (r8 != 0) goto L_0x000e
            boolean r8 = android.text.TextUtils.isEmpty(r12)
            if (r8 == 0) goto L_0x0010
        L_0x000e:
            r4 = r3
        L_0x000f:
            return r4
        L_0x0010:
            r0 = 0
            java.io.StringReader r5 = new java.io.StringReader     // Catch:{ Throwable -> 0x0055 }
            r5.<init>(r11)     // Catch:{ Throwable -> 0x0055 }
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x0055 }
            r8 = 8192(0x2000, float:1.14794E-41)
            r1.<init>(r5, r8)     // Catch:{ Throwable -> 0x0055 }
        L_0x001d:
            java.lang.String r2 = r1.readLine()     // Catch:{ Throwable -> 0x0085, all -> 0x0082 }
            if (r2 == 0) goto L_0x0043
            boolean r8 = r2.startsWith(r12)     // Catch:{ Throwable -> 0x0085, all -> 0x0082 }
            if (r8 == 0) goto L_0x001d
            r8 = 58
            int r6 = r2.lastIndexOf(r8)     // Catch:{ Throwable -> 0x0085, all -> 0x0082 }
            if (r6 < 0) goto L_0x001d
            int r8 = r2.length()     // Catch:{ Throwable -> 0x0085, all -> 0x0082 }
            int r8 = r8 + -1
            if (r6 >= r8) goto L_0x001d
            int r8 = r6 + 1
            java.lang.String r8 = r2.substring(r8)     // Catch:{ Throwable -> 0x0085, all -> 0x0082 }
            java.lang.String r3 = r8.trim()     // Catch:{ Throwable -> 0x0085, all -> 0x0082 }
        L_0x0043:
            r1.close()     // Catch:{ Throwable -> 0x0049 }
            r0 = r1
        L_0x0047:
            r4 = r3
            goto L_0x000f
        L_0x0049:
            r7 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r8 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r9 = "CrashFilter"
            r8.warn(r9, r7)
            r0 = r1
            goto L_0x0047
        L_0x0055:
            r7 = move-exception
        L_0x0056:
            com.alipay.mobile.common.logging.api.trace.TraceLogger r8 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x0070 }
            java.lang.String r9 = "CrashFilter"
            r8.warn(r9, r7)     // Catch:{ all -> 0x0070 }
            if (r0 == 0) goto L_0x0047
            r0.close()     // Catch:{ Throwable -> 0x0065 }
            goto L_0x0047
        L_0x0065:
            r7 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r8 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r9 = "CrashFilter"
            r8.warn(r9, r7)
            goto L_0x0047
        L_0x0070:
            r8 = move-exception
        L_0x0071:
            if (r0 == 0) goto L_0x0076
            r0.close()     // Catch:{ Throwable -> 0x0077 }
        L_0x0076:
            throw r8
        L_0x0077:
            r7 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r9 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r10 = "CrashFilter"
            r9.warn(r10, r7)
            goto L_0x0076
        L_0x0082:
            r8 = move-exception
            r0 = r1
            goto L_0x0071
        L_0x0085:
            r7 = move-exception
            r0 = r1
            goto L_0x0056
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.nativecrash.CrashFilterUtils.getNativeCrashInfo(java.lang.String, java.lang.String):java.lang.String");
    }

    /* JADX WARNING: Removed duplicated region for block: B:104:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x018e  */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x01d0 A[SYNTHETIC, Splitter:B:72:0x01d0] */
    /* JADX WARNING: Removed duplicated region for block: B:78:0x01e8 A[SYNTHETIC, Splitter:B:78:0x01e8] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getNativeCrashClientStatus(java.lang.String r29) {
        /*
            java.lang.String r8 = ""
            boolean r24 = android.text.TextUtils.isEmpty(r29)
            if (r24 == 0) goto L_0x0009
        L_0x0008:
            return r8
        L_0x0009:
            r15 = -1
            r10 = -1
            r6 = 0
            r12 = 0
            r20 = 0
            r21 = 0
            r4 = 0
            java.io.StringReader r18 = new java.io.StringReader     // Catch:{ Throwable -> 0x01be }
            r0 = r18
            r1 = r29
            r0.<init>(r1)     // Catch:{ Throwable -> 0x01be }
            java.io.BufferedReader r5 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x01be }
            r24 = 8192(0x2000, float:1.14794E-41)
            r0 = r18
            r1 = r24
            r5.<init>(r0, r1)     // Catch:{ Throwable -> 0x01be }
        L_0x002a:
            java.lang.String r14 = r5.readLine()     // Catch:{ Throwable -> 0x0228, all -> 0x0225 }
            if (r14 == 0) goto L_0x0184
            java.lang.String r24 = "processSetupTimestamp"
            r0 = r24
            boolean r24 = r14.startsWith(r0)     // Catch:{ Throwable -> 0x0228, all -> 0x0225 }
            if (r24 == 0) goto L_0x0064
            r24 = 58
            r0 = r24
            int r19 = r14.lastIndexOf(r0)     // Catch:{ Throwable -> 0x0228, all -> 0x0225 }
            if (r19 < 0) goto L_0x0064
            int r24 = r14.length()     // Catch:{ Throwable -> 0x0228, all -> 0x0225 }
            int r24 = r24 + -1
            r0 = r19
            r1 = r24
            if (r0 >= r1) goto L_0x0064
            int r24 = r19 + 1
            r0 = r24
            java.lang.String r24 = r14.substring(r0)     // Catch:{ Throwable -> 0x0228, all -> 0x0225 }
            java.lang.String r24 = r24.trim()     // Catch:{ Throwable -> 0x0228, all -> 0x0225 }
            java.lang.Long r24 = java.lang.Long.valueOf(r24)     // Catch:{ Throwable -> 0x0228, all -> 0x0225 }
            long r15 = r24.longValue()     // Catch:{ Throwable -> 0x0228, all -> 0x0225 }
        L_0x0064:
            java.lang.String r24 = "clientLaunchTimestamp"
            r0 = r24
            boolean r24 = r14.startsWith(r0)     // Catch:{ Throwable -> 0x0228, all -> 0x0225 }
            if (r24 == 0) goto L_0x0098
            r24 = 58
            r0 = r24
            int r19 = r14.lastIndexOf(r0)     // Catch:{ Throwable -> 0x0228, all -> 0x0225 }
            if (r19 < 0) goto L_0x0098
            int r24 = r14.length()     // Catch:{ Throwable -> 0x0228, all -> 0x0225 }
            int r24 = r24 + -1
            r0 = r19
            r1 = r24
            if (r0 >= r1) goto L_0x0098
            int r24 = r19 + 1
            r0 = r24
            java.lang.String r24 = r14.substring(r0)     // Catch:{ Throwable -> 0x0228, all -> 0x0225 }
            java.lang.String r24 = r24.trim()     // Catch:{ Throwable -> 0x0228, all -> 0x0225 }
            java.lang.Long r24 = java.lang.Long.valueOf(r24)     // Catch:{ Throwable -> 0x0228, all -> 0x0225 }
            long r6 = r24.longValue()     // Catch:{ Throwable -> 0x0228, all -> 0x0225 }
        L_0x0098:
            java.lang.String r24 = "gotoBackgroundTimestamp"
            r0 = r24
            boolean r24 = r14.startsWith(r0)     // Catch:{ Throwable -> 0x0228, all -> 0x0225 }
            if (r24 == 0) goto L_0x00cc
            r24 = 58
            r0 = r24
            int r19 = r14.lastIndexOf(r0)     // Catch:{ Throwable -> 0x0228, all -> 0x0225 }
            if (r19 < 0) goto L_0x00cc
            int r24 = r14.length()     // Catch:{ Throwable -> 0x0228, all -> 0x0225 }
            int r24 = r24 + -1
            r0 = r19
            r1 = r24
            if (r0 >= r1) goto L_0x00cc
            int r24 = r19 + 1
            r0 = r24
            java.lang.String r24 = r14.substring(r0)     // Catch:{ Throwable -> 0x0228, all -> 0x0225 }
            java.lang.String r24 = r24.trim()     // Catch:{ Throwable -> 0x0228, all -> 0x0225 }
            java.lang.Long r24 = java.lang.Long.valueOf(r24)     // Catch:{ Throwable -> 0x0228, all -> 0x0225 }
            long r12 = r24.longValue()     // Catch:{ Throwable -> 0x0228, all -> 0x0225 }
        L_0x00cc:
            java.lang.String r24 = "log end: "
            r0 = r24
            boolean r24 = r14.startsWith(r0)     // Catch:{ Throwable -> 0x0228, all -> 0x0225 }
            if (r24 == 0) goto L_0x011a
            r24 = 58
            r0 = r24
            int r19 = r14.lastIndexOf(r0)     // Catch:{ Throwable -> 0x0228, all -> 0x0225 }
            if (r19 < 0) goto L_0x011a
            int r24 = r14.length()     // Catch:{ Throwable -> 0x0228, all -> 0x0225 }
            int r24 = r24 + -1
            r0 = r19
            r1 = r24
            if (r0 >= r1) goto L_0x011a
            int r24 = r19 + 1
            r0 = r24
            java.lang.String r24 = r14.substring(r0)     // Catch:{ Throwable -> 0x0228, all -> 0x0225 }
            java.lang.String r22 = r24.trim()     // Catch:{ Throwable -> 0x0228, all -> 0x0225 }
            java.text.SimpleDateFormat r17 = new java.text.SimpleDateFormat     // Catch:{ Throwable -> 0x0228, all -> 0x0225 }
            java.lang.String r24 = "yyyyMMddHHmmss"
            r0 = r17
            r1 = r24
            r0.<init>(r1)     // Catch:{ Throwable -> 0x0228, all -> 0x0225 }
            java.util.TimeZone r24 = java.util.TimeZone.getDefault()     // Catch:{ Throwable -> 0x0228, all -> 0x0225 }
            r0 = r17
            r1 = r24
            r0.setTimeZone(r1)     // Catch:{ Throwable -> 0x0228, all -> 0x0225 }
            r0 = r17
            r1 = r22
            java.util.Date r24 = r0.parse(r1)     // Catch:{ Throwable -> 0x0228, all -> 0x0225 }
            long r10 = r24.getTime()     // Catch:{ Throwable -> 0x0228, all -> 0x0225 }
        L_0x011a:
            java.lang.String r24 = "StartupAction"
            r0 = r24
            boolean r24 = r14.startsWith(r0)     // Catch:{ Throwable -> 0x0228, all -> 0x0225 }
            if (r24 == 0) goto L_0x014e
            r24 = 58
            r0 = r24
            int r19 = r14.lastIndexOf(r0)     // Catch:{ Throwable -> 0x0228, all -> 0x0225 }
            if (r19 < 0) goto L_0x014e
            int r24 = r14.length()     // Catch:{ Throwable -> 0x0228, all -> 0x0225 }
            int r24 = r24 + -1
            r0 = r19
            r1 = r24
            if (r0 >= r1) goto L_0x014e
            int r24 = r19 + 1
            r0 = r24
            java.lang.String r24 = r14.substring(r0)     // Catch:{ Throwable -> 0x0228, all -> 0x0225 }
            java.lang.String r3 = r24.trim()     // Catch:{ Throwable -> 0x0228, all -> 0x0225 }
            boolean r24 = android.text.TextUtils.isEmpty(r3)     // Catch:{ Throwable -> 0x0228, all -> 0x0225 }
            if (r24 != 0) goto L_0x014e
            r20 = r3
        L_0x014e:
            java.lang.String r24 = "StartupComponent"
            r0 = r24
            boolean r24 = r14.startsWith(r0)     // Catch:{ Throwable -> 0x0228, all -> 0x0225 }
            if (r24 == 0) goto L_0x002a
            r24 = 58
            r0 = r24
            int r19 = r14.lastIndexOf(r0)     // Catch:{ Throwable -> 0x0228, all -> 0x0225 }
            if (r19 < 0) goto L_0x002a
            int r24 = r14.length()     // Catch:{ Throwable -> 0x0228, all -> 0x0225 }
            int r24 = r24 + -1
            r0 = r19
            r1 = r24
            if (r0 >= r1) goto L_0x002a
            int r24 = r19 + 1
            r0 = r24
            java.lang.String r24 = r14.substring(r0)     // Catch:{ Throwable -> 0x0228, all -> 0x0225 }
            java.lang.String r9 = r24.trim()     // Catch:{ Throwable -> 0x0228, all -> 0x0225 }
            boolean r24 = android.text.TextUtils.isEmpty(r9)     // Catch:{ Throwable -> 0x0228, all -> 0x0225 }
            if (r24 != 0) goto L_0x002a
            r21 = r9
            goto L_0x002a
        L_0x0184:
            r5.close()     // Catch:{ Throwable -> 0x01ac }
            r4 = r5
        L_0x0188:
            r24 = 0
            int r24 = (r15 > r24 ? 1 : (r15 == r24 ? 0 : -1))
            if (r24 <= 0) goto L_0x0008
            r24 = 0
            int r24 = (r10 > r24 ? 1 : (r10 == r24 ? 0 : -1))
            if (r24 <= 0) goto L_0x0008
            r24 = 0
            int r24 = (r12 > r24 ? 1 : (r12 == r24 ? 0 : -1))
            if (r24 <= 0) goto L_0x01fd
            long r24 = r10 - r12
            java.util.concurrent.TimeUnit r26 = java.util.concurrent.TimeUnit.MINUTES
            r27 = 5
            long r26 = r26.toMillis(r27)
            int r24 = (r24 > r26 ? 1 : (r24 == r26 ? 0 : -1))
            if (r24 <= 0) goto L_0x01fd
            java.lang.String r8 = "background"
            goto L_0x0008
        L_0x01ac:
            r23 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r24 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r25 = "CrashFilter"
            r0 = r24
            r1 = r25
            r2 = r23
            r0.warn(r1, r2)
            r4 = r5
            goto L_0x0188
        L_0x01be:
            r23 = move-exception
        L_0x01bf:
            com.alipay.mobile.common.logging.api.trace.TraceLogger r24 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x01e5 }
            java.lang.String r25 = "CrashFilter"
            r0 = r24
            r1 = r25
            r2 = r23
            r0.warn(r1, r2)     // Catch:{ all -> 0x01e5 }
            if (r4 == 0) goto L_0x0188
            r4.close()     // Catch:{ Throwable -> 0x01d4 }
            goto L_0x0188
        L_0x01d4:
            r23 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r24 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r25 = "CrashFilter"
            r0 = r24
            r1 = r25
            r2 = r23
            r0.warn(r1, r2)
            goto L_0x0188
        L_0x01e5:
            r24 = move-exception
        L_0x01e6:
            if (r4 == 0) goto L_0x01eb
            r4.close()     // Catch:{ Throwable -> 0x01ec }
        L_0x01eb:
            throw r24
        L_0x01ec:
            r23 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r25 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r26 = "CrashFilter"
            r0 = r25
            r1 = r26
            r2 = r23
            r0.warn(r1, r2)
            goto L_0x01eb
        L_0x01fd:
            r24 = 0
            int r24 = (r6 > r24 ? 1 : (r6 == r24 ? 0 : -1))
            if (r24 > 0) goto L_0x0221
            r24 = 0
            int r24 = (r15 > r24 ? 1 : (r15 == r24 ? 0 : -1))
            if (r24 <= 0) goto L_0x0221
            long r24 = r10 - r15
            java.util.concurrent.TimeUnit r26 = java.util.concurrent.TimeUnit.MINUTES
            r27 = 1
            long r26 = r26.toMillis(r27)
            int r24 = (r24 > r26 ? 1 : (r24 == r26 ? 0 : -1))
            if (r24 > 0) goto L_0x021d
            boolean r24 = isBackgroundLaunch(r20, r21)
            if (r24 == 0) goto L_0x0221
        L_0x021d:
            java.lang.String r8 = "background"
            goto L_0x0008
        L_0x0221:
            java.lang.String r8 = "foreground"
            goto L_0x0008
        L_0x0225:
            r24 = move-exception
            r4 = r5
            goto L_0x01e6
        L_0x0228:
            r23 = move-exception
            r4 = r5
            goto L_0x01bf
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.nativecrash.CrashFilterUtils.getNativeCrashClientStatus(java.lang.String):java.lang.String");
    }

    public static boolean isBackgroundLaunch(String actionName, String componentName) {
        try {
            if (TextUtils.isEmpty(actionName) && TextUtils.isEmpty(componentName)) {
                return true;
            }
            boolean isBackgroundAction = false;
            boolean isBackgroundComponent = false;
            if ("android.intent.action.USER_PRESENT".equals(actionName) || "android.intent.action.BOOT_COMPLETED".equals(actionName) || "android.net.conn.CONNECTIVITY_CHANGE".equals(actionName) || "android.intent.action.ACTION_POWER_CONNECTED".equals(actionName) || "android.intent.action.TIME_SET".equals(actionName) || "android.intent.action.PACKAGE_ADDED".equals(actionName) || "android.intent.action.PACKAGE_REMOVED".equals(actionName) || "android.provider.Telephony.SMS_RECEIVED".equals(actionName) || "org.rome.android.ipp.intent.action.PINGA".equals(actionName) || "org.rome.android.IPP_CALL".equals(actionName) || "com.alipay.mobile.commonbiz.biz.SET_SCHEME".equals(actionName) || "com.alipay.mobile.notification".equals(actionName)) {
                isBackgroundAction = true;
            }
            if ("com.alipay.android.launcher.service.LauncherService".equals(componentName) || "com.alipay.android.phone.mobilesdk.apm.service.APMInnerService".equals(componentName) || "org.rome.android.ipp.binder.IppService".equals(componentName) || "com.amap.api.location.APSService".equals(componentName) || "com.taobao.android.sso.internal.AlipayAuthenticationService".equals(componentName) || "com.taobao.infsword.receiver.SmsIntercept".equals(componentName) || "com.alipay.mobile.command.engine.TaskExeService".equals(componentName) || "com.alipay.pushsdk.push.NotificationService".equals(componentName) || "com.alipay.android.launcher.service.DummyService".equals(componentName) || "com.alipay.mobile.rome.pushservice.integration.RecvMsgIntentService".equals(componentName)) {
                isBackgroundComponent = true;
            }
            if (isBackgroundAction || isBackgroundComponent) {
                return true;
            }
            return false;
        } catch (Throwable tr) {
            Log.w("CrashFilter", tr);
        }
    }

    public static boolean isPotentialBackgroundCrash(String exceptionInfo) {
        if (!TextUtils.isEmpty(exceptionInfo) && exceptionInfo.contains("java.lang.SecurityException") && exceptionInfo.contains("Unable to find app for caller android.app.ApplicationThreadProxy") && exceptionInfo.contains("when publishing content providers") && exceptionInfo.contains("android.os.Parcel.readException") && exceptionInfo.contains("android.app.ActivityManagerProxy.publishContentProviders") && exceptionInfo.contains("android.app.ActivityThread.installContentProviders") && exceptionInfo.contains("android.app.ActivityThread.handleBindApplication")) {
            return true;
        }
        return false;
    }
}
