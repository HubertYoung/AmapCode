package com.autonavi.miniapp.monitor.biz.logmonitor.util.stacktrace;

public class StackTracer {
    private static StackTracer INSTANCE = null;
    private static final int MAX_STACKTRACER_COUNT = 100;

    public static synchronized StackTracer getInstance() {
        StackTracer stackTracer;
        synchronized (StackTracer.class) {
            try {
                if (INSTANCE == null) {
                    INSTANCE = new StackTracer();
                }
                stackTracer = INSTANCE;
            }
        }
        return stackTracer;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0023, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00d8, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x011e, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x0165, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void startStackTracer(android.content.Context r12, com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UserDiagnostician.DiagnoseTask r13, final com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus r14) {
        /*
            r11 = this;
            monitor-enter(r11)
            if (r12 == 0) goto L_0x0164
            java.lang.String r0 = r13.taskType     // Catch:{ all -> 0x0161 }
            boolean r0 = android.text.TextUtils.isEmpty(r0)     // Catch:{ all -> 0x0161 }
            if (r0 == 0) goto L_0x000d
            goto L_0x0164
        L_0x000d:
            long r0 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x0161 }
            java.lang.String r2 = r13.taskType     // Catch:{ all -> 0x0161 }
            java.io.File r2 = r12.getExternalFilesDir(r2)     // Catch:{ all -> 0x0161 }
            if (r2 != 0) goto L_0x0024
            if (r14 == 0) goto L_0x0022
            com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus$Code r12 = com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus.Code.NO_SDCARD     // Catch:{ all -> 0x0161 }
            java.lang.String r13 = "[StackTracer.startStackTracer] has no sdcard"
            r14.onFail(r12, r13)     // Catch:{ all -> 0x0161 }
        L_0x0022:
            monitor-exit(r11)
            return
        L_0x0024:
            boolean r3 = r2.isDirectory()     // Catch:{ all -> 0x0161 }
            if (r3 == 0) goto L_0x0030
            boolean r3 = r2.exists()     // Catch:{ all -> 0x0161 }
            if (r3 != 0) goto L_0x0033
        L_0x0030:
            r2.mkdirs()     // Catch:{ all -> 0x0161 }
        L_0x0033:
            java.util.ArrayList r3 = new java.util.ArrayList     // Catch:{ all -> 0x0161 }
            r3.<init>()     // Catch:{ all -> 0x0161 }
            r4 = 1
            r5 = 1
        L_0x003a:
            long r6 = r13.stackTracerTime     // Catch:{ all -> 0x0161 }
            long r8 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x0161 }
            r10 = 0
            long r8 = r8 - r0
            int r6 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r6 < 0) goto L_0x00c8
            r6 = 100
            if (r5 >= r6) goto L_0x00c8
            java.lang.String r6 = com.autonavi.miniapp.monitor.util.MonitorUtils.acquireThreadsStackTrace()     // Catch:{ Exception -> 0x00bd }
            com.alipay.mobile.common.logging.api.trace.TraceLogger r7 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Exception -> 0x00bd }
            java.lang.String r8 = "tracer"
            java.lang.String r9 = "startStackTracer"
            java.lang.String r10 = java.lang.String.valueOf(r5)     // Catch:{ Exception -> 0x00bd }
            java.lang.String r9 = r9.concat(r10)     // Catch:{ Exception -> 0x00bd }
            r7.info(r8, r9)     // Catch:{ Exception -> 0x00bd }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00bd }
            r7.<init>()     // Catch:{ Exception -> 0x00bd }
            r7.append(r5)     // Catch:{ Exception -> 0x00bd }
            java.lang.String r8 = "_"
            r7.append(r8)     // Catch:{ Exception -> 0x00bd }
            com.alipay.mobile.common.logging.api.ProcessInfo r8 = com.alipay.mobile.common.logging.api.LoggerFactory.getProcessInfo()     // Catch:{ Exception -> 0x00bd }
            java.lang.String r8 = r8.getProcessTag()     // Catch:{ Exception -> 0x00bd }
            r7.append(r8)     // Catch:{ Exception -> 0x00bd }
            java.lang.String r7 = r7.toString()     // Catch:{ Exception -> 0x00bd }
            int r5 = r5 + 1
            java.io.File r8 = new java.io.File     // Catch:{ Exception -> 0x00bd }
            r8.<init>(r2, r7)     // Catch:{ Exception -> 0x00bd }
            boolean r7 = android.text.TextUtils.isEmpty(r6)     // Catch:{ Exception -> 0x00bd }
            if (r7 != 0) goto L_0x00a7
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00bd }
            r7.<init>()     // Catch:{ Exception -> 0x00bd }
            long r9 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x00bd }
            r7.append(r9)     // Catch:{ Exception -> 0x00bd }
            java.lang.String r9 = "\r\n"
            r7.append(r9)     // Catch:{ Exception -> 0x00bd }
            r7.append(r6)     // Catch:{ Exception -> 0x00bd }
            java.lang.String r6 = r7.toString()     // Catch:{ Exception -> 0x00bd }
            com.autonavi.miniapp.monitor.util.FileUtils.writeFile(r8, r6, r4)     // Catch:{ Exception -> 0x00bd }
        L_0x00a7:
            boolean r6 = r8.exists()     // Catch:{ Exception -> 0x00bd }
            if (r6 == 0) goto L_0x00b6
            boolean r6 = r8.isFile()     // Catch:{ Exception -> 0x00bd }
            if (r6 == 0) goto L_0x00b6
            r3.add(r8)     // Catch:{ Exception -> 0x00bd }
        L_0x00b6:
            long r6 = r13.stackTracerInterval     // Catch:{ Exception -> 0x00bd }
            java.lang.Thread.sleep(r6)     // Catch:{ Exception -> 0x00bd }
            goto L_0x003a
        L_0x00bd:
            r0 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r1 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x0161 }
            java.lang.String r2 = "tracer"
            r1.error(r2, r0)     // Catch:{ all -> 0x0161 }
        L_0x00c8:
            boolean r0 = r3.isEmpty()     // Catch:{ all -> 0x0161 }
            if (r0 == 0) goto L_0x00d9
            if (r14 == 0) goto L_0x00d7
            com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus$Code r12 = com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus.Code.NO_TARGET_FILE     // Catch:{ all -> 0x0161 }
            java.lang.String r13 = "[StackTracer.startStackTracer] uploadFiles is null"
            r14.onFail(r12, r13)     // Catch:{ all -> 0x0161 }
        L_0x00d7:
            monitor-exit(r11)
            return
        L_0x00d9:
            java.io.File r0 = new java.io.File     // Catch:{ all -> 0x0161 }
            java.io.File r1 = r12.getExternalCacheDir()     // Catch:{ all -> 0x0161 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0161 }
            r2.<init>()     // Catch:{ all -> 0x0161 }
            java.lang.String r4 = r13.taskType     // Catch:{ all -> 0x0161 }
            r2.append(r4)     // Catch:{ all -> 0x0161 }
            java.lang.String r4 = ".zip"
            r2.append(r4)     // Catch:{ all -> 0x0161 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x0161 }
            r0.<init>(r1, r2)     // Catch:{ all -> 0x0161 }
            java.lang.String r5 = r0.getAbsolutePath()     // Catch:{ all -> 0x0161 }
            r1 = 0
            com.autonavi.miniapp.monitor.util.ZipUtils.zipFile(r3, r5, r1, r1)     // Catch:{ Throwable -> 0x0136 }
            boolean r0 = r0.exists()     // Catch:{ all -> 0x0161 }
            if (r0 != 0) goto L_0x011f
            if (r14 == 0) goto L_0x011d
            com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus$Code r12 = com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus.Code.ZIPPING_ERROR     // Catch:{ all -> 0x0161 }
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ all -> 0x0161 }
            java.lang.String r0 = "[StackTracer.startStackTracer] "
            r13.<init>(r0)     // Catch:{ all -> 0x0161 }
            r13.append(r5)     // Catch:{ all -> 0x0161 }
            java.lang.String r0 = " is not exist"
            r13.append(r0)     // Catch:{ all -> 0x0161 }
            java.lang.String r13 = r13.toString()     // Catch:{ all -> 0x0161 }
            r14.onFail(r12, r13)     // Catch:{ all -> 0x0161 }
        L_0x011d:
            monitor-exit(r11)
            return
        L_0x011f:
            r0 = 0
            java.lang.String r6 = com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UploadConstants.getUploadFileUrl(r0)     // Catch:{ all -> 0x0161 }
            com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.HttpUpload r0 = new com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.HttpUpload     // Catch:{ all -> 0x0161 }
            com.autonavi.miniapp.monitor.biz.logmonitor.util.stacktrace.StackTracer$1 r9 = new com.autonavi.miniapp.monitor.biz.logmonitor.util.stacktrace.StackTracer$1     // Catch:{ all -> 0x0161 }
            r9.<init>(r14, r5)     // Catch:{ all -> 0x0161 }
            r4 = r0
            r7 = r12
            r8 = r13
            r4.<init>(r5, r6, r7, r8, r9)     // Catch:{ all -> 0x0161 }
            r0.run()     // Catch:{ all -> 0x0161 }
            monitor-exit(r11)
            return
        L_0x0136:
            r12 = move-exception
            boolean r13 = r3.isEmpty()     // Catch:{ all -> 0x0161 }
            if (r13 == 0) goto L_0x0140
            java.lang.String r12 = "[no files to upload] contains none file."
            goto L_0x0144
        L_0x0140:
            java.lang.String r12 = android.util.Log.getStackTraceString(r12)     // Catch:{ all -> 0x0161 }
        L_0x0144:
            com.alipay.mobile.common.logging.api.trace.TraceLogger r13 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x0161 }
            java.lang.String r0 = "tracer"
            r13.error(r0, r12)     // Catch:{ all -> 0x0161 }
            if (r14 == 0) goto L_0x015f
            com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus$Code r13 = com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus.Code.ZIPPING_ERROR     // Catch:{ all -> 0x0161 }
            java.lang.String r0 = "[StackTracer.startStackTracer] "
            java.lang.String r12 = java.lang.String.valueOf(r12)     // Catch:{ all -> 0x0161 }
            java.lang.String r12 = r0.concat(r12)     // Catch:{ all -> 0x0161 }
            r14.onFail(r13, r12)     // Catch:{ all -> 0x0161 }
        L_0x015f:
            monitor-exit(r11)
            return
        L_0x0161:
            r12 = move-exception
            monitor-exit(r11)
            throw r12
        L_0x0164:
            monitor-exit(r11)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.miniapp.monitor.biz.logmonitor.util.stacktrace.StackTracer.startStackTracer(android.content.Context, com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UserDiagnostician$DiagnoseTask, com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus):void");
    }
}
