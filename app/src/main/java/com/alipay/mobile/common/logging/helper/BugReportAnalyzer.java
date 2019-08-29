package com.alipay.mobile.common.logging.helper;

import android.os.SystemClock;
import android.text.TextUtils;
import com.alibaba.wireless.security.SecExceptionCode;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import java.util.HashSet;
import java.util.Set;

public class BugReportAnalyzer {
    private static BugReportAnalyzer a;
    private long b = 0;

    public static synchronized BugReportAnalyzer a() {
        BugReportAnalyzer bugReportAnalyzer;
        synchronized (BugReportAnalyzer.class) {
            try {
                if (a == null) {
                    a = new BugReportAnalyzer();
                }
                bugReportAnalyzer = a;
            }
        }
        return bugReportAnalyzer;
    }

    private BugReportAnalyzer() {
    }

    public final void a(Object feedback_msg) {
        try {
            String feedback_msg_str = (String) feedback_msg;
            if (!TextUtils.isEmpty(feedback_msg_str)) {
                a(feedback_msg_str);
                b(feedback_msg_str);
            }
        } catch (Throwable tr) {
            LoggerFactory.getTraceLogger().warn((String) "BugReportAnalyzer", tr);
        }
    }

    private void a(String feedback_msg) {
        boolean fit = false;
        if (feedback_msg.contains("卡死") || feedback_msg.contains("死机") || feedback_msg.contains("闪退") || feedback_msg.contains("崩溃") || feedback_msg.contains("crash") || feedback_msg.contains("Crash") || feedback_msg.contains("停止运行") || feedback_msg.contains("强行停止") || feedback_msg.contains("强制停止") || feedback_msg.contains("停止服务") || ((feedback_msg.contains("黑屏") || feedback_msg.contains("白屏")) && !feedback_msg.contains("扫") && !feedback_msg.contains("二维"))) {
            fit = true;
        }
        if (fit) {
            a(SecExceptionCode.SEC_ERROR_SIMULATORDETECT, false);
        }
    }

    private void b(String feedback_msg) {
        Set<String> pat1 = new HashSet<>();
        pat1.add("安装");
        pat1.add("升级");
        Set<String> pat2 = new HashSet<>();
        pat2.add("无法");
        pat2.add("不了");
        pat2.add("失败");
        pat2.add("不能");
        boolean fit1 = false;
        for (String key : pat1) {
            if (feedback_msg.contains(key)) {
                fit1 = true;
            }
        }
        boolean fit2 = false;
        for (String key2 : pat2) {
            if (feedback_msg.contains(key2)) {
                fit2 = true;
            }
        }
        if (fit1 && fit2) {
            a(SecExceptionCode.SEC_ERROR_SIMULATORDETECT, false);
        }
    }

    public final void a(int lines, boolean sync) {
        long now = SystemClock.elapsedRealtime();
        if (now - this.b >= 1000) {
            this.b = now;
            Runnable reportRunnable = new a(this, lines, sync);
            if (sync) {
                reportRunnable.run();
            } else {
                new Thread(reportRunnable, "reportLogcat").run();
            }
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0067, code lost:
        r2 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0068, code lost:
        r0 = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x00e9, code lost:
        r6 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x00ea, code lost:
        r0 = r1;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x00c6 A[SYNTHETIC, Splitter:B:41:0x00c6] */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x00cb A[SYNTHETIC, Splitter:B:44:0x00cb] */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x00e9 A[ExcHandler: all (th java.lang.Throwable), Splitter:B:3:0x0045] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String b(int r10) {
        /*
            com.alipay.mobile.common.logging.api.trace.TraceLogger r6 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r7 = "BugReportAnalyzer"
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            java.lang.String r9 = "start getLogcat for "
            r8.<init>(r9)
            java.lang.StringBuilder r8 = r8.append(r10)
            java.lang.String r9 = " lines."
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.String r8 = r8.toString()
            r6.info(r7, r8)
            r4 = 0
            r0 = 0
            java.lang.Runtime r6 = java.lang.Runtime.getRuntime()     // Catch:{ Throwable -> 0x00ec }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00ec }
            java.lang.String r8 = "logcat -v time -d -t "
            r7.<init>(r8)     // Catch:{ Throwable -> 0x00ec }
            java.lang.StringBuilder r7 = r7.append(r10)     // Catch:{ Throwable -> 0x00ec }
            java.lang.String r7 = r7.toString()     // Catch:{ Throwable -> 0x00ec }
            java.lang.Process r4 = r6.exec(r7)     // Catch:{ Throwable -> 0x00ec }
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x00ec }
            java.io.InputStreamReader r6 = new java.io.InputStreamReader     // Catch:{ Throwable -> 0x00ec }
            java.io.InputStream r7 = r4.getInputStream()     // Catch:{ Throwable -> 0x00ec }
            r6.<init>(r7)     // Catch:{ Throwable -> 0x00ec }
            r1.<init>(r6)     // Catch:{ Throwable -> 0x00ec }
            java.lang.StringBuffer r5 = new java.lang.StringBuffer     // Catch:{ Throwable -> 0x0067, all -> 0x00e9 }
            r5.<init>()     // Catch:{ Throwable -> 0x0067, all -> 0x00e9 }
        L_0x004a:
            java.lang.String r3 = r1.readLine()     // Catch:{ Throwable -> 0x0067, all -> 0x00e9 }
            if (r3 == 0) goto L_0x0081
            java.lang.StringBuffer r6 = r5.append(r3)     // Catch:{ Throwable -> 0x005a, all -> 0x00e9 }
            java.lang.String r7 = "\n"
            r6.append(r7)     // Catch:{ Throwable -> 0x005a, all -> 0x00e9 }
            goto L_0x004a
        L_0x005a:
            r2 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r6 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Throwable -> 0x0067, all -> 0x00e9 }
            java.lang.String r7 = "BugReportAnalyzer"
            java.lang.String r8 = "skip"
            r6.warn(r7, r8, r2)     // Catch:{ Throwable -> 0x0067, all -> 0x00e9 }
            goto L_0x004a
        L_0x0067:
            r2 = move-exception
            r0 = r1
        L_0x0069:
            com.alipay.mobile.common.logging.api.trace.TraceLogger r6 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x00c3 }
            java.lang.String r7 = "BugReportAnalyzer"
            java.lang.String r8 = "getLogcat"
            r6.error(r7, r8, r2)     // Catch:{ all -> 0x00c3 }
            if (r4 == 0) goto L_0x0079
            r4.destroy()     // Catch:{ Throwable -> 0x00a9 }
        L_0x0079:
            if (r0 == 0) goto L_0x007e
            r0.close()     // Catch:{ Throwable -> 0x00b6 }
        L_0x007e:
            java.lang.String r6 = ""
        L_0x0080:
            return r6
        L_0x0081:
            java.lang.String r6 = r5.toString()     // Catch:{ Throwable -> 0x0067, all -> 0x00e9 }
            if (r4 == 0) goto L_0x008a
            r4.destroy()     // Catch:{ Throwable -> 0x008f }
        L_0x008a:
            r1.close()     // Catch:{ Throwable -> 0x009c }
        L_0x008d:
            r0 = r1
            goto L_0x0080
        L_0x008f:
            r2 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r7 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r8 = "BugReportAnalyzer"
            java.lang.String r9 = "close logcatProc"
            r7.warn(r8, r9, r2)
            goto L_0x008a
        L_0x009c:
            r2 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r7 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r8 = "BugReportAnalyzer"
            java.lang.String r9 = "close bufferedReader"
            r7.warn(r8, r9, r2)
            goto L_0x008d
        L_0x00a9:
            r2 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r6 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r7 = "BugReportAnalyzer"
            java.lang.String r8 = "close logcatProc"
            r6.warn(r7, r8, r2)
            goto L_0x0079
        L_0x00b6:
            r2 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r6 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r7 = "BugReportAnalyzer"
            java.lang.String r8 = "close bufferedReader"
            r6.warn(r7, r8, r2)
            goto L_0x007e
        L_0x00c3:
            r6 = move-exception
        L_0x00c4:
            if (r4 == 0) goto L_0x00c9
            r4.destroy()     // Catch:{ Throwable -> 0x00cf }
        L_0x00c9:
            if (r0 == 0) goto L_0x00ce
            r0.close()     // Catch:{ Throwable -> 0x00dc }
        L_0x00ce:
            throw r6
        L_0x00cf:
            r2 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r7 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r8 = "BugReportAnalyzer"
            java.lang.String r9 = "close logcatProc"
            r7.warn(r8, r9, r2)
            goto L_0x00c9
        L_0x00dc:
            r2 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r7 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r8 = "BugReportAnalyzer"
            java.lang.String r9 = "close bufferedReader"
            r7.warn(r8, r9, r2)
            goto L_0x00ce
        L_0x00e9:
            r6 = move-exception
            r0 = r1
            goto L_0x00c4
        L_0x00ec:
            r2 = move-exception
            goto L_0x0069
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.logging.helper.BugReportAnalyzer.b(int):java.lang.String");
    }
}
