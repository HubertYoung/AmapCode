package com.autonavi.miniapp.monitor.biz.logmonitor.util.stacktrace;

public class AnrTracer {
    private static AnrTracer INSTANCE = null;
    private static final String TAG = "AnrTracer";

    public static synchronized AnrTracer getInstance() {
        AnrTracer anrTracer;
        synchronized (AnrTracer.class) {
            try {
                if (INSTANCE == null) {
                    INSTANCE = new AnrTracer();
                }
                anrTracer = INSTANCE;
            }
        }
        return anrTracer;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00db, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x0121, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void startAnrTracer(android.content.Context r10, boolean r11, com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UserDiagnostician.DiagnoseTask r12, final com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus r13) {
        /*
            r9 = this;
            monitor-enter(r9)
            if (r10 == 0) goto L_0x0120
            java.lang.String r0 = r12.taskType     // Catch:{ all -> 0x011d }
            boolean r0 = android.text.TextUtils.isEmpty(r0)     // Catch:{ all -> 0x011d }
            if (r0 == 0) goto L_0x000d
            goto L_0x0120
        L_0x000d:
            java.lang.String r0 = r12.taskType     // Catch:{ all -> 0x011d }
            java.io.File r0 = r10.getExternalFilesDir(r0)     // Catch:{ all -> 0x011d }
            if (r0 != 0) goto L_0x0029
            java.io.File r0 = com.autonavi.miniapp.monitor.util.TransUtils.getCommonExternalStorageDir()     // Catch:{ all -> 0x011d }
            java.io.File r1 = new java.io.File     // Catch:{ all -> 0x011d }
            java.lang.String r2 = r10.getPackageName()     // Catch:{ all -> 0x011d }
            r1.<init>(r0, r2)     // Catch:{ all -> 0x011d }
            java.io.File r0 = new java.io.File     // Catch:{ all -> 0x011d }
            java.lang.String r2 = r12.taskType     // Catch:{ all -> 0x011d }
            r0.<init>(r1, r2)     // Catch:{ all -> 0x011d }
        L_0x0029:
            boolean r1 = r0.isDirectory()     // Catch:{ all -> 0x011d }
            if (r1 == 0) goto L_0x0035
            boolean r1 = r0.exists()     // Catch:{ all -> 0x011d }
            if (r1 != 0) goto L_0x0038
        L_0x0035:
            r0.mkdirs()     // Catch:{ all -> 0x011d }
        L_0x0038:
            java.util.ArrayList r1 = new java.util.ArrayList     // Catch:{ all -> 0x011d }
            r1.<init>()     // Catch:{ all -> 0x011d }
            com.autonavi.miniapp.monitor.biz.logmonitor.util.stacktrace.ThreadDumpHelper r2 = new com.autonavi.miniapp.monitor.biz.logmonitor.util.stacktrace.ThreadDumpHelper     // Catch:{ Exception -> 0x0090 }
            r2.<init>(r10)     // Catch:{ Exception -> 0x0090 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0090 }
            java.lang.String r4 = r12.taskType     // Catch:{ Exception -> 0x0090 }
            r3.<init>(r4)     // Catch:{ Exception -> 0x0090 }
            java.lang.String r4 = ":\r\n"
            r3.append(r4)     // Catch:{ Exception -> 0x0090 }
            r4 = 1
            if (r11 == 0) goto L_0x0059
            java.lang.String r11 = r2.dumpLastAnrTrace()     // Catch:{ Exception -> 0x0090 }
            r3.append(r11)     // Catch:{ Exception -> 0x0090 }
            goto L_0x0060
        L_0x0059:
            java.lang.String r11 = r2.dumpAllStackTraces(r4)     // Catch:{ Exception -> 0x0090 }
            r3.append(r11)     // Catch:{ Exception -> 0x0090 }
        L_0x0060:
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0090 }
            r11.<init>()     // Catch:{ Exception -> 0x0090 }
            long r5 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0090 }
            r11.append(r5)     // Catch:{ Exception -> 0x0090 }
            java.lang.String r2 = "_"
            r11.append(r2)     // Catch:{ Exception -> 0x0090 }
            com.alipay.mobile.common.logging.api.ProcessInfo r2 = com.alipay.mobile.common.logging.api.LoggerFactory.getProcessInfo()     // Catch:{ Exception -> 0x0090 }
            java.lang.String r2 = r2.getProcessTag()     // Catch:{ Exception -> 0x0090 }
            r11.append(r2)     // Catch:{ Exception -> 0x0090 }
            java.lang.String r11 = r11.toString()     // Catch:{ Exception -> 0x0090 }
            java.io.File r2 = new java.io.File     // Catch:{ Exception -> 0x0090 }
            r2.<init>(r0, r11)     // Catch:{ Exception -> 0x0090 }
            java.lang.String r11 = r3.toString()     // Catch:{ Exception -> 0x0090 }
            com.autonavi.miniapp.monitor.util.FileUtils.writeFile(r2, r11, r4)     // Catch:{ Exception -> 0x0090 }
            r1.add(r2)     // Catch:{ Exception -> 0x0090 }
            goto L_0x009a
        L_0x0090:
            r11 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r2 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x011d }
            java.lang.String r3 = TAG     // Catch:{ all -> 0x011d }
            r2.error(r3, r11)     // Catch:{ all -> 0x011d }
        L_0x009a:
            java.io.File r11 = new java.io.File     // Catch:{ all -> 0x011d }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x011d }
            r2.<init>()     // Catch:{ all -> 0x011d }
            java.lang.String r3 = r12.taskType     // Catch:{ all -> 0x011d }
            r2.append(r3)     // Catch:{ all -> 0x011d }
            java.lang.String r3 = ".zip"
            r2.append(r3)     // Catch:{ all -> 0x011d }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x011d }
            r11.<init>(r0, r2)     // Catch:{ all -> 0x011d }
            java.lang.String r4 = r11.getAbsolutePath()     // Catch:{ all -> 0x011d }
            r0 = 0
            com.autonavi.miniapp.monitor.util.ZipUtils.zipFile(r1, r4, r0, r0)     // Catch:{ Throwable -> 0x00f3 }
            boolean r11 = r11.exists()     // Catch:{ all -> 0x011d }
            if (r11 != 0) goto L_0x00dc
            if (r13 == 0) goto L_0x00da
            com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus$Code r10 = com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus.Code.ZIPPING_ERROR     // Catch:{ all -> 0x011d }
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ all -> 0x011d }
            java.lang.String r12 = "[AnrTracer.startAnrTracer] "
            r11.<init>(r12)     // Catch:{ all -> 0x011d }
            r11.append(r4)     // Catch:{ all -> 0x011d }
            java.lang.String r12 = " is not exist"
            r11.append(r12)     // Catch:{ all -> 0x011d }
            java.lang.String r11 = r11.toString()     // Catch:{ all -> 0x011d }
            r13.onFail(r10, r11)     // Catch:{ all -> 0x011d }
        L_0x00da:
            monitor-exit(r9)
            return
        L_0x00dc:
            r11 = 0
            java.lang.String r5 = com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UploadConstants.getUploadFileUrl(r11)     // Catch:{ all -> 0x011d }
            com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.HttpUpload r11 = new com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.HttpUpload     // Catch:{ all -> 0x011d }
            com.autonavi.miniapp.monitor.biz.logmonitor.util.stacktrace.AnrTracer$1 r8 = new com.autonavi.miniapp.monitor.biz.logmonitor.util.stacktrace.AnrTracer$1     // Catch:{ all -> 0x011d }
            r8.<init>(r13, r4)     // Catch:{ all -> 0x011d }
            r3 = r11
            r6 = r10
            r7 = r12
            r3.<init>(r4, r5, r6, r7, r8)     // Catch:{ all -> 0x011d }
            r11.run()     // Catch:{ all -> 0x011d }
            monitor-exit(r9)
            return
        L_0x00f3:
            r10 = move-exception
            boolean r11 = r1.isEmpty()     // Catch:{ all -> 0x011d }
            if (r11 == 0) goto L_0x00fd
            java.lang.String r10 = "[no files to upload] contains none file."
            goto L_0x0101
        L_0x00fd:
            java.lang.String r10 = android.util.Log.getStackTraceString(r10)     // Catch:{ all -> 0x011d }
        L_0x0101:
            com.alipay.mobile.common.logging.api.trace.TraceLogger r11 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x011d }
            java.lang.String r12 = TAG     // Catch:{ all -> 0x011d }
            r11.error(r12, r10)     // Catch:{ all -> 0x011d }
            if (r13 == 0) goto L_0x011b
            com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus$Code r11 = com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus.Code.ZIPPING_ERROR     // Catch:{ all -> 0x011d }
            java.lang.String r12 = "[AnrTracer.startAnrTracer] "
            java.lang.String r10 = java.lang.String.valueOf(r10)     // Catch:{ all -> 0x011d }
            java.lang.String r10 = r12.concat(r10)     // Catch:{ all -> 0x011d }
            r13.onFail(r11, r10)     // Catch:{ all -> 0x011d }
        L_0x011b:
            monitor-exit(r9)
            return
        L_0x011d:
            r10 = move-exception
            monitor-exit(r9)
            throw r10
        L_0x0120:
            monitor-exit(r9)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.miniapp.monitor.biz.logmonitor.util.stacktrace.AnrTracer.startAnrTracer(android.content.Context, boolean, com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UserDiagnostician$DiagnoseTask, com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus):void");
    }
}
