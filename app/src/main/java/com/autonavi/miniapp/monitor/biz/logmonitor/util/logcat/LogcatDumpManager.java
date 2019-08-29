package com.autonavi.miniapp.monitor.biz.logmonitor.util.logcat;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.os.Process;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import java.util.List;

public class LogcatDumpManager {
    private static LogcatDumpManager INSTANCE = null;
    private static final int MAX_LINE_NUM = 10000;
    private static final String TAG = "LogcatDumpManager";
    private static final String USELESS_LOG_REG = ".*(GC_EXPLICIT|GC_ALLOC|GC_CONCURRENT|GC_FOR_ALLOC|WAIT_FOR_CONCURRENT_GC|Grow heap).*";
    private Context mContext;

    public static LogcatDumpManager getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (LogcatDumpManager.class) {
                try {
                    if (INSTANCE == null) {
                        INSTANCE = new LogcatDumpManager(context);
                    }
                }
            }
        }
        return INSTANCE;
    }

    private LogcatDumpManager(Context context) {
        this.mContext = context;
    }

    private String getValidPids() {
        StringBuilder sb = new StringBuilder();
        sb.append(Process.myPid());
        try {
            List<RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) this.mContext.getSystemService(WidgetType.ACTIVITY)).getRunningAppProcesses();
            int userId = LoggerFactory.getProcessInfo().getUserId();
            for (RunningAppProcessInfo next : runningAppProcesses) {
                if (next.uid == userId || next.processName.equals("system")) {
                    sb.append('|');
                    sb.append(next.pid);
                }
            }
        } catch (Throwable th) {
            LoggerFactory.getTraceLogger().error(TAG, "getValidPids", th);
        }
        return sb.toString();
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(12:1|2|3|4|5|(2:6|7)|(5:8|9|10|(3:11|12|(2:14|(5:16|17|18|(2:20|76)(2:21|(2:23|77)(2:24|78))|28)(2:74|31))(1:73))|(2:33|34))|35|36|56|57|58) */
    /* JADX WARNING: Can't wrap try/catch for region: R(5:60|(0)|(0)|68|69) */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x00d5, code lost:
        if (r3 != null) goto L_0x00a9;
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:35:0x00a9 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:56:0x00d8 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:68:0x0101 */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x00d2 A[SYNTHETIC, Splitter:B:52:0x00d2] */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x00f9 A[SYNTHETIC, Splitter:B:62:0x00f9] */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x00fe A[SYNTHETIC, Splitter:B:66:0x00fe] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:68:0x0101=Splitter:B:68:0x0101, B:56:0x00d8=Splitter:B:56:0x00d8} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void dumpLogMatchPid() {
        /*
            r13 = this;
            monitor-enter(r13)
            com.alipay.mobile.common.logging.api.trace.TraceLogger r0 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x0102 }
            java.lang.String r1 = TAG     // Catch:{ all -> 0x0102 }
            java.lang.String r2 = "dumpLogMatchPid, start logcatDump"
            r0.info(r1, r2)     // Catch:{ all -> 0x0102 }
            r0 = 0
            r1 = 0
            java.lang.Runtime r2 = java.lang.Runtime.getRuntime()     // Catch:{ Throwable -> 0x00c0, all -> 0x00bb }
            java.lang.String r3 = "logcat -v time -d -b main -b system"
            java.lang.Process r2 = r2.exec(r3)     // Catch:{ Throwable -> 0x00c0, all -> 0x00bb }
            java.io.BufferedReader r3 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x00b5, all -> 0x00b2 }
            java.io.InputStreamReader r4 = new java.io.InputStreamReader     // Catch:{ Throwable -> 0x00b5, all -> 0x00b2 }
            java.io.InputStream r5 = r2.getInputStream()     // Catch:{ Throwable -> 0x00b5, all -> 0x00b2 }
            r4.<init>(r5)     // Catch:{ Throwable -> 0x00b5, all -> 0x00b2 }
            r3.<init>(r4)     // Catch:{ Throwable -> 0x00b5, all -> 0x00b2 }
            java.lang.String r0 = r13.getValidPids()     // Catch:{ Throwable -> 0x00af }
            r4 = 0
        L_0x002b:
            java.lang.String r5 = r3.readLine()     // Catch:{ Throwable -> 0x00ad }
            if (r5 == 0) goto L_0x00a4
            int r6 = r4 + 1
            r7 = 10000(0x2710, float:1.4013E-41)
            if (r4 > r7) goto L_0x00a3
            java.lang.String r4 = ".*(GC_EXPLICIT|GC_ALLOC|GC_CONCURRENT|GC_FOR_ALLOC|WAIT_FOR_CONCURRENT_GC|Grow heap).*"
            boolean r4 = r5.matches(r4)     // Catch:{ Throwable -> 0x007e }
            if (r4 == 0) goto L_0x0040
            goto L_0x009e
        L_0x0040:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x007e }
            java.lang.String r7 = ".*\\([\\s]{0,10}("
            r4.<init>(r7)     // Catch:{ Throwable -> 0x007e }
            r4.append(r0)     // Catch:{ Throwable -> 0x007e }
            java.lang.String r7 = ")\\).*"
            r4.append(r7)     // Catch:{ Throwable -> 0x007e }
            java.lang.String r4 = r4.toString()     // Catch:{ Throwable -> 0x007e }
            boolean r4 = r5.matches(r4)     // Catch:{ Throwable -> 0x007e }
            if (r4 != 0) goto L_0x005a
            goto L_0x009e
        L_0x005a:
            com.alipay.mobile.common.logging.api.LogEvent r4 = new com.alipay.mobile.common.logging.api.LogEvent     // Catch:{ Throwable -> 0x007e }
            java.lang.String r7 = "logcat"
            java.lang.String r8 = ""
            com.alipay.mobile.common.logging.api.LogEvent$Level r9 = com.alipay.mobile.common.logging.api.LogEvent.Level.INFO     // Catch:{ Throwable -> 0x007e }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x007e }
            r10.<init>()     // Catch:{ Throwable -> 0x007e }
            r10.append(r5)     // Catch:{ Throwable -> 0x007e }
            java.lang.String r11 = "\r\n"
            r10.append(r11)     // Catch:{ Throwable -> 0x007e }
            java.lang.String r10 = r10.toString()     // Catch:{ Throwable -> 0x007e }
            r4.<init>(r7, r8, r9, r10)     // Catch:{ Throwable -> 0x007e }
            com.alipay.mobile.common.logging.api.LogContext r7 = com.alipay.mobile.common.logging.api.LoggerFactory.getLogContext()     // Catch:{ Throwable -> 0x007e }
            r7.appendLogEvent(r4)     // Catch:{ Throwable -> 0x007e }
            goto L_0x009e
        L_0x007e:
            r4 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r7 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Throwable -> 0x00a0 }
            java.lang.String r8 = TAG     // Catch:{ Throwable -> 0x00a0 }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00a0 }
            java.lang.String r10 = "dumpLogMatchPid, skip this line: "
            r9.<init>(r10)     // Catch:{ Throwable -> 0x00a0 }
            r9.append(r5)     // Catch:{ Throwable -> 0x00a0 }
            java.lang.String r5 = "\n"
            r9.append(r5)     // Catch:{ Throwable -> 0x00a0 }
            r9.append(r4)     // Catch:{ Throwable -> 0x00a0 }
            java.lang.String r4 = r9.toString()     // Catch:{ Throwable -> 0x00a0 }
            r7.error(r8, r4)     // Catch:{ Throwable -> 0x00a0 }
        L_0x009e:
            r4 = r6
            goto L_0x002b
        L_0x00a0:
            r0 = move-exception
            r4 = r6
            goto L_0x00c5
        L_0x00a3:
            r4 = r6
        L_0x00a4:
            if (r2 == 0) goto L_0x00a9
            r2.destroy()     // Catch:{ Throwable -> 0x00a9 }
        L_0x00a9:
            r3.close()     // Catch:{ Throwable -> 0x00d8 }
            goto L_0x00d8
        L_0x00ad:
            r0 = move-exception
            goto L_0x00c5
        L_0x00af:
            r0 = move-exception
            r4 = 0
            goto L_0x00c5
        L_0x00b2:
            r1 = move-exception
            r3 = r0
            goto L_0x00be
        L_0x00b5:
            r3 = move-exception
            r4 = 0
            r12 = r3
            r3 = r0
            r0 = r12
            goto L_0x00c5
        L_0x00bb:
            r1 = move-exception
            r2 = r0
            r3 = r2
        L_0x00be:
            r0 = r1
            goto L_0x00f7
        L_0x00c0:
            r2 = move-exception
            r3 = r0
            r4 = 0
            r0 = r2
            r2 = r3
        L_0x00c5:
            com.alipay.mobile.common.logging.api.trace.TraceLogger r5 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x00f6 }
            java.lang.String r6 = TAG     // Catch:{ all -> 0x00f6 }
            java.lang.String r7 = "dumpLogMatchPid"
            r5.error(r6, r7, r0)     // Catch:{ all -> 0x00f6 }
            if (r2 == 0) goto L_0x00d5
            r2.destroy()     // Catch:{ Throwable -> 0x00d5 }
        L_0x00d5:
            if (r3 == 0) goto L_0x00d8
            goto L_0x00a9
        L_0x00d8:
            com.alipay.mobile.common.logging.api.trace.TraceLogger r0 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x0102 }
            java.lang.String r2 = TAG     // Catch:{ all -> 0x0102 }
            java.lang.String r3 = "dumpLogMatchPid, end logcatDump, count="
            java.lang.String r4 = java.lang.String.valueOf(r4)     // Catch:{ all -> 0x0102 }
            java.lang.String r3 = r3.concat(r4)     // Catch:{ all -> 0x0102 }
            r0.info(r2, r3)     // Catch:{ all -> 0x0102 }
            com.alipay.mobile.common.logging.api.LogContext r0 = com.alipay.mobile.common.logging.api.LoggerFactory.getLogContext()     // Catch:{ all -> 0x0102 }
            java.lang.String r2 = "logcat"
            r0.flush(r2, r1)     // Catch:{ all -> 0x0102 }
            monitor-exit(r13)
            return
        L_0x00f6:
            r0 = move-exception
        L_0x00f7:
            if (r2 == 0) goto L_0x00fc
            r2.destroy()     // Catch:{ Throwable -> 0x00fc }
        L_0x00fc:
            if (r3 == 0) goto L_0x0101
            r3.close()     // Catch:{ Throwable -> 0x0101 }
        L_0x0101:
            throw r0     // Catch:{ all -> 0x0102 }
        L_0x0102:
            r0 = move-exception
            monitor-exit(r13)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.miniapp.monitor.biz.logmonitor.util.logcat.LogcatDumpManager.dumpLogMatchPid():void");
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(5:12|13|28|29|30) */
    /* JADX WARNING: Can't wrap try/catch for region: R(5:17|18|(0)|35|36) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:28:0x0089 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:35:0x00af */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0085 A[SYNTHETIC, Splitter:B:24:0x0085] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00ac A[SYNTHETIC, Splitter:B:33:0x00ac] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:28:0x0089=Splitter:B:28:0x0089, B:35:0x00af=Splitter:B:35:0x00af} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void dumpLogUnfiltered() {
        /*
            r11 = this;
            monitor-enter(r11)
            com.alipay.mobile.common.logging.api.trace.TraceLogger r0 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x00b0 }
            java.lang.String r1 = TAG     // Catch:{ all -> 0x00b0 }
            java.lang.String r2 = "dumpLogAllLines, start logcatDump"
            r0.info(r1, r2)     // Catch:{ all -> 0x00b0 }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x00b0 }
            java.lang.String r1 = "dumpLogcat_"
            r0.<init>(r1)     // Catch:{ all -> 0x00b0 }
            long r1 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x00b0 }
            r0.append(r1)     // Catch:{ all -> 0x00b0 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x00b0 }
            java.io.File r1 = new java.io.File     // Catch:{ all -> 0x00b0 }
            android.content.Context r2 = r11.mContext     // Catch:{ all -> 0x00b0 }
            java.io.File r2 = r2.getCacheDir()     // Catch:{ all -> 0x00b0 }
            r1.<init>(r2, r0)     // Catch:{ all -> 0x00b0 }
            r0 = 10000(0x2710, float:1.4013E-41)
            com.autonavi.miniapp.monitor.util.TransUtils.dumpLogcat(r1, r0)     // Catch:{ all -> 0x00b0 }
            r0 = 0
            r2 = 0
            java.io.BufferedReader r3 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x0076 }
            java.io.FileReader r4 = new java.io.FileReader     // Catch:{ Throwable -> 0x0076 }
            r4.<init>(r1)     // Catch:{ Throwable -> 0x0076 }
            r3.<init>(r4)     // Catch:{ Throwable -> 0x0076 }
            r0 = 0
        L_0x003b:
            java.lang.String r4 = r3.readLine()     // Catch:{ Throwable -> 0x006d, all -> 0x006b }
            if (r4 == 0) goto L_0x0067
            com.alipay.mobile.common.logging.api.LogEvent r5 = new com.alipay.mobile.common.logging.api.LogEvent     // Catch:{ Throwable -> 0x006d, all -> 0x006b }
            java.lang.String r6 = "logcat"
            java.lang.String r7 = ""
            com.alipay.mobile.common.logging.api.LogEvent$Level r8 = com.alipay.mobile.common.logging.api.LogEvent.Level.INFO     // Catch:{ Throwable -> 0x006d, all -> 0x006b }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x006d, all -> 0x006b }
            r9.<init>()     // Catch:{ Throwable -> 0x006d, all -> 0x006b }
            r9.append(r4)     // Catch:{ Throwable -> 0x006d, all -> 0x006b }
            java.lang.String r4 = "\n"
            r9.append(r4)     // Catch:{ Throwable -> 0x006d, all -> 0x006b }
            java.lang.String r4 = r9.toString()     // Catch:{ Throwable -> 0x006d, all -> 0x006b }
            r5.<init>(r6, r7, r8, r4)     // Catch:{ Throwable -> 0x006d, all -> 0x006b }
            com.alipay.mobile.common.logging.api.LogContext r4 = com.alipay.mobile.common.logging.api.LoggerFactory.getLogContext()     // Catch:{ Throwable -> 0x006d, all -> 0x006b }
            r4.appendLogEvent(r5)     // Catch:{ Throwable -> 0x006d, all -> 0x006b }
            int r0 = r0 + 1
            goto L_0x003b
        L_0x0067:
            r3.close()     // Catch:{ Throwable -> 0x0089 }
            goto L_0x0089
        L_0x006b:
            r0 = move-exception
            goto L_0x00aa
        L_0x006d:
            r4 = move-exception
            r10 = r3
            r3 = r0
            r0 = r10
            goto L_0x0078
        L_0x0072:
            r1 = move-exception
            r3 = r0
            r0 = r1
            goto L_0x00aa
        L_0x0076:
            r4 = move-exception
            r3 = 0
        L_0x0078:
            com.alipay.mobile.common.logging.api.trace.TraceLogger r5 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x0072 }
            java.lang.String r6 = TAG     // Catch:{ all -> 0x0072 }
            java.lang.String r7 = "dumpLogAllLines"
            r5.error(r6, r7, r4)     // Catch:{ all -> 0x0072 }
            if (r0 == 0) goto L_0x0088
            r0.close()     // Catch:{ Throwable -> 0x0088 }
        L_0x0088:
            r0 = r3
        L_0x0089:
            com.autonavi.miniapp.monitor.util.FileUtils.deleteFileNotDir(r1)     // Catch:{ all -> 0x00b0 }
            com.alipay.mobile.common.logging.api.trace.TraceLogger r1 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x00b0 }
            java.lang.String r3 = TAG     // Catch:{ all -> 0x00b0 }
            java.lang.String r4 = "dumpLogAllLines, end logcatDump, count="
            java.lang.String r0 = java.lang.String.valueOf(r0)     // Catch:{ all -> 0x00b0 }
            java.lang.String r0 = r4.concat(r0)     // Catch:{ all -> 0x00b0 }
            r1.info(r3, r0)     // Catch:{ all -> 0x00b0 }
            com.alipay.mobile.common.logging.api.LogContext r0 = com.alipay.mobile.common.logging.api.LoggerFactory.getLogContext()     // Catch:{ all -> 0x00b0 }
            java.lang.String r1 = "logcat"
            r0.flush(r1, r2)     // Catch:{ all -> 0x00b0 }
            monitor-exit(r11)
            return
        L_0x00aa:
            if (r3 == 0) goto L_0x00af
            r3.close()     // Catch:{ Throwable -> 0x00af }
        L_0x00af:
            throw r0     // Catch:{ all -> 0x00b0 }
        L_0x00b0:
            r0 = move-exception
            monitor-exit(r11)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.miniapp.monitor.biz.logmonitor.util.logcat.LogcatDumpManager.dumpLogUnfiltered():void");
    }
}
