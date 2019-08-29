package com.autonavi.miniapp.monitor.biz.logmonitor.util.storage;

public class FileRetriever {
    private static final String TAG = "FileRetriever";
    private static FileRetriever sFileRetriever;

    public static synchronized FileRetriever getInstance() {
        FileRetriever fileRetriever;
        synchronized (FileRetriever.class) {
            try {
                if (sFileRetriever == null) {
                    sFileRetriever = new FileRetriever();
                }
                fileRetriever = sFileRetriever;
            }
        }
        return fileRetriever;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0042, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0067, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0141, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x0175, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void startFileRetrieve(android.content.Context r12, com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UserDiagnostician.DiagnoseTask r13, final com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus r14) {
        /*
            r11 = this;
            monitor-enter(r11)
            if (r12 == 0) goto L_0x0174
            java.lang.String r0 = r13.taskType     // Catch:{ all -> 0x0171 }
            boolean r0 = android.text.TextUtils.isEmpty(r0)     // Catch:{ all -> 0x0171 }
            if (r0 != 0) goto L_0x0174
            java.lang.String r0 = r13.retrieveFilePath     // Catch:{ all -> 0x0171 }
            boolean r0 = android.text.TextUtils.isEmpty(r0)     // Catch:{ all -> 0x0171 }
            if (r0 == 0) goto L_0x0015
            goto L_0x0174
        L_0x0015:
            java.lang.String r0 = "[FileRetriever.startFileRetrieve] "
            java.io.File r1 = new java.io.File     // Catch:{ all -> 0x0171 }
            java.lang.String r2 = r13.retrieveFilePath     // Catch:{ all -> 0x0171 }
            r1.<init>(r2)     // Catch:{ all -> 0x0171 }
            boolean r2 = r1.exists()     // Catch:{ all -> 0x0171 }
            if (r2 != 0) goto L_0x0043
            if (r14 == 0) goto L_0x0041
            com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus$Code r12 = com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus.Code.NO_TARGET_FILE     // Catch:{ all -> 0x0171 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0171 }
            r1.<init>()     // Catch:{ all -> 0x0171 }
            r1.append(r0)     // Catch:{ all -> 0x0171 }
            java.lang.String r13 = r13.retrieveFilePath     // Catch:{ all -> 0x0171 }
            r1.append(r13)     // Catch:{ all -> 0x0171 }
            java.lang.String r13 = " is not exist"
            r1.append(r13)     // Catch:{ all -> 0x0171 }
            java.lang.String r13 = r1.toString()     // Catch:{ all -> 0x0171 }
            r14.onFail(r12, r13)     // Catch:{ all -> 0x0171 }
        L_0x0041:
            monitor-exit(r11)
            return
        L_0x0043:
            boolean r2 = r1.isFile()     // Catch:{ all -> 0x0171 }
            if (r2 != 0) goto L_0x0068
            if (r14 == 0) goto L_0x0066
            com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus$Code r12 = com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus.Code.NO_TARGET_FILE     // Catch:{ all -> 0x0171 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0171 }
            r1.<init>()     // Catch:{ all -> 0x0171 }
            r1.append(r0)     // Catch:{ all -> 0x0171 }
            java.lang.String r13 = r13.retrieveFilePath     // Catch:{ all -> 0x0171 }
            r1.append(r13)     // Catch:{ all -> 0x0171 }
            java.lang.String r13 = " is not a file"
            r1.append(r13)     // Catch:{ all -> 0x0171 }
            java.lang.String r13 = r1.toString()     // Catch:{ all -> 0x0171 }
            r14.onFail(r12, r13)     // Catch:{ all -> 0x0171 }
        L_0x0066:
            monitor-exit(r11)
            return
        L_0x0068:
            com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UserDiagnostician r2 = com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UserDiagnostician.getInstance()     // Catch:{ all -> 0x0171 }
            com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus$Code r3 = com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus.Code.FILE_ZIPPING     // Catch:{ all -> 0x0171 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0171 }
            r4.<init>()     // Catch:{ all -> 0x0171 }
            r4.append(r0)     // Catch:{ all -> 0x0171 }
            java.lang.String r5 = "filePath: "
            r4.append(r5)     // Catch:{ all -> 0x0171 }
            java.lang.String r5 = r1.getAbsolutePath()     // Catch:{ all -> 0x0171 }
            r4.append(r5)     // Catch:{ all -> 0x0171 }
            java.lang.String r5 = ", length: "
            r4.append(r5)     // Catch:{ all -> 0x0171 }
            long r5 = r1.length()     // Catch:{ all -> 0x0171 }
            r4.append(r5)     // Catch:{ all -> 0x0171 }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x0171 }
            r2.asyncAckResult(r13, r3, r4)     // Catch:{ all -> 0x0171 }
            java.util.ArrayList r2 = new java.util.ArrayList     // Catch:{ all -> 0x0171 }
            r2.<init>()     // Catch:{ all -> 0x0171 }
            r2.add(r1)     // Catch:{ all -> 0x0171 }
            java.io.File r1 = new java.io.File     // Catch:{ all -> 0x0171 }
            java.io.File r3 = r12.getCacheDir()     // Catch:{ all -> 0x0171 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0171 }
            r4.<init>()     // Catch:{ all -> 0x0171 }
            java.lang.String r5 = r13.taskID     // Catch:{ all -> 0x0171 }
            r4.append(r5)     // Catch:{ all -> 0x0171 }
            java.lang.String r5 = "_"
            r4.append(r5)     // Catch:{ all -> 0x0171 }
            long r5 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x0171 }
            java.lang.String r5 = java.lang.String.valueOf(r5)     // Catch:{ all -> 0x0171 }
            r4.append(r5)     // Catch:{ all -> 0x0171 }
            java.lang.String r5 = ".zip"
            r4.append(r5)     // Catch:{ all -> 0x0171 }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x0171 }
            r1.<init>(r3, r4)     // Catch:{ all -> 0x0171 }
            java.lang.String r6 = r1.getAbsolutePath()     // Catch:{ all -> 0x0171 }
            r3 = 0
            com.autonavi.miniapp.monitor.util.ZipUtils.zipFile(r2, r6, r3, r3)     // Catch:{ Throwable -> 0x0142 }
            boolean r2 = r1.exists()     // Catch:{ all -> 0x0171 }
            if (r2 == 0) goto L_0x011f
            boolean r2 = r1.isFile()     // Catch:{ all -> 0x0171 }
            if (r2 == 0) goto L_0x011f
            long r2 = r1.length()     // Catch:{ all -> 0x0171 }
            r4 = 0
            int r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r2 != 0) goto L_0x00e8
            goto L_0x011f
        L_0x00e8:
            r2 = 0
            java.lang.String r7 = com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UploadConstants.getUploadFileUrl(r2)     // Catch:{ all -> 0x0171 }
            com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.HttpUpload r2 = new com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.HttpUpload     // Catch:{ all -> 0x0171 }
            com.autonavi.miniapp.monitor.biz.logmonitor.util.storage.FileRetriever$1 r10 = new com.autonavi.miniapp.monitor.biz.logmonitor.util.storage.FileRetriever$1     // Catch:{ all -> 0x0171 }
            r10.<init>(r14, r6)     // Catch:{ all -> 0x0171 }
            r5 = r2
            r8 = r12
            r9 = r13
            r5.<init>(r6, r7, r8, r9, r10)     // Catch:{ all -> 0x0171 }
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ all -> 0x0171 }
            r12.<init>()     // Catch:{ all -> 0x0171 }
            r12.append(r0)     // Catch:{ all -> 0x0171 }
            java.lang.String r13 = "zippedLength: "
            r12.append(r13)     // Catch:{ all -> 0x0171 }
            long r13 = r1.length()     // Catch:{ all -> 0x0171 }
            r12.append(r13)     // Catch:{ all -> 0x0171 }
            java.lang.String r12 = r12.toString()     // Catch:{ all -> 0x0171 }
            r2.setAckDiagnoseMessage(r12)     // Catch:{ all -> 0x0171 }
            com.autonavi.miniapp.monitor.biz.apm.util.APMTimer r12 = com.autonavi.miniapp.monitor.biz.apm.util.APMTimer.getInstance()     // Catch:{ all -> 0x0171 }
            r12.post(r2)     // Catch:{ all -> 0x0171 }
            monitor-exit(r11)
            return
        L_0x011f:
            if (r14 == 0) goto L_0x0140
            com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus$Code r12 = com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus.Code.ZIPPING_ERROR     // Catch:{ all -> 0x0171 }
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ all -> 0x0171 }
            r13.<init>()     // Catch:{ all -> 0x0171 }
            r13.append(r0)     // Catch:{ all -> 0x0171 }
            java.lang.String r0 = "zippedFile: "
            r13.append(r0)     // Catch:{ all -> 0x0171 }
            r13.append(r6)     // Catch:{ all -> 0x0171 }
            java.lang.String r0 = " is not exist OR is not file OR is empty"
            r13.append(r0)     // Catch:{ all -> 0x0171 }
            java.lang.String r13 = r13.toString()     // Catch:{ all -> 0x0171 }
            r14.onFail(r12, r13)     // Catch:{ all -> 0x0171 }
        L_0x0140:
            monitor-exit(r11)
            return
        L_0x0142:
            r12 = move-exception
            boolean r13 = r2.isEmpty()     // Catch:{ all -> 0x0171 }
            if (r13 == 0) goto L_0x014c
            java.lang.String r12 = "[no files to upload] contains none file."
            goto L_0x0150
        L_0x014c:
            java.lang.String r12 = android.util.Log.getStackTraceString(r12)     // Catch:{ all -> 0x0171 }
        L_0x0150:
            com.alipay.mobile.common.logging.api.trace.TraceLogger r13 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x0171 }
            java.lang.String r1 = TAG     // Catch:{ all -> 0x0171 }
            r13.error(r1, r12)     // Catch:{ all -> 0x0171 }
            if (r14 == 0) goto L_0x016f
            com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus$Code r13 = com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus.Code.ZIPPING_ERROR     // Catch:{ all -> 0x0171 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0171 }
            r1.<init>()     // Catch:{ all -> 0x0171 }
            r1.append(r0)     // Catch:{ all -> 0x0171 }
            r1.append(r12)     // Catch:{ all -> 0x0171 }
            java.lang.String r12 = r1.toString()     // Catch:{ all -> 0x0171 }
            r14.onFail(r13, r12)     // Catch:{ all -> 0x0171 }
        L_0x016f:
            monitor-exit(r11)
            return
        L_0x0171:
            r12 = move-exception
            monitor-exit(r11)
            throw r12
        L_0x0174:
            monitor-exit(r11)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.miniapp.monitor.biz.logmonitor.util.storage.FileRetriever.startFileRetrieve(android.content.Context, com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UserDiagnostician$DiagnoseTask, com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus):void");
    }
}
