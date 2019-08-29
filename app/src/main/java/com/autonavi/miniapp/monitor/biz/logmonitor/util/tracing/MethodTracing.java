package com.autonavi.miniapp.monitor.biz.logmonitor.util.tracing;

import com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus;
import com.autonavi.miniapp.monitor.biz.apm.util.APMTimer;

public class MethodTracing {
    private static final String TAG = "MethodTracing";
    private static MethodTracing methodTracing;

    private MethodTracing() {
    }

    public static synchronized MethodTracing getInstance() {
        MethodTracing methodTracing2;
        synchronized (MethodTracing.class) {
            try {
                if (methodTracing == null) {
                    methodTracing = new MethodTracing();
                }
                methodTracing2 = methodTracing;
            }
        }
        return methodTracing2;
    }

    public void startMethodTracing(String str, long j, UploadTaskStatus uploadTaskStatus, int i) {
        APMTimer instance = APMTimer.getInstance();
        final String str2 = str;
        final long j2 = j;
        final UploadTaskStatus uploadTaskStatus2 = uploadTaskStatus;
        final int i2 = i;
        AnonymousClass1 r1 = new Runnable() {
            public void run() {
                MethodTracing.this.doMethodTracing(str2, j2, uploadTaskStatus2, i2);
            }
        };
        instance.post(r1);
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:18:0x0080=Splitter:B:18:0x0080, B:8:0x0047=Splitter:B:8:0x0047} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void doMethodTracing(java.lang.String r4, long r5, com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus r7, int r8) {
        /*
            r3 = this;
            monitor-enter(r3)
            com.alipay.mobile.common.logging.api.trace.TraceLogger r0 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Throwable -> 0x0057 }
            java.lang.String r1 = TAG     // Catch:{ Throwable -> 0x0057 }
            java.lang.String r2 = "startMethodTracing"
            r0.info(r1, r2)     // Catch:{ Throwable -> 0x0057 }
            boolean r0 = com.autonavi.miniapp.monitor.util.FileUtils.isCanUseSdCard()     // Catch:{ Throwable -> 0x0057 }
            if (r0 == 0) goto L_0x0040
            long r0 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x0057 }
            android.os.Debug.startMethodTracing(r4, r8)     // Catch:{ Throwable -> 0x0057 }
            java.lang.Thread.sleep(r5)     // Catch:{ Throwable -> 0x0057 }
            android.os.Debug.stopMethodTracing()     // Catch:{ Throwable -> 0x0057 }
            long r4 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x0057 }
            r6 = 0
            long r4 = r4 - r0
            java.lang.String r6 = "tracing: ok, spendTime: "
            java.lang.String r4 = java.lang.String.valueOf(r4)     // Catch:{ Throwable -> 0x0057 }
            java.lang.String r4 = r6.concat(r4)     // Catch:{ Throwable -> 0x0057 }
            com.alipay.mobile.common.logging.api.trace.TraceLogger r5 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Throwable -> 0x0057 }
            java.lang.String r6 = TAG     // Catch:{ Throwable -> 0x0057 }
            r5.info(r6, r4)     // Catch:{ Throwable -> 0x0057 }
            if (r7 == 0) goto L_0x0047
            r7.onSuccess(r4)     // Catch:{ Throwable -> 0x0057 }
            goto L_0x0047
        L_0x0040:
            com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus$Code r4 = com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus.Code.NO_SDCARD     // Catch:{ Throwable -> 0x0057 }
            java.lang.String r5 = "[MethodTracing.doMethodTracing] has no sdcard"
            r7.onFail(r4, r5)     // Catch:{ Throwable -> 0x0057 }
        L_0x0047:
            com.alipay.mobile.common.logging.api.trace.TraceLogger r4 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x009b }
            java.lang.String r5 = TAG     // Catch:{ all -> 0x009b }
            java.lang.String r6 = "startMethodTracing end"
            r4.info(r5, r6)     // Catch:{ all -> 0x009b }
            monitor-exit(r3)
            return
        L_0x0055:
            r4 = move-exception
            goto L_0x008e
        L_0x0057:
            r4 = move-exception
            java.lang.String r4 = android.util.Log.getStackTraceString(r4)     // Catch:{ all -> 0x0055 }
            com.alipay.mobile.common.logging.api.trace.TraceLogger r5 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x0055 }
            java.lang.String r6 = TAG     // Catch:{ all -> 0x0055 }
            java.lang.String r8 = "doMethodTracing: "
            java.lang.String r0 = java.lang.String.valueOf(r4)     // Catch:{ all -> 0x0055 }
            java.lang.String r8 = r8.concat(r0)     // Catch:{ all -> 0x0055 }
            r5.error(r6, r8)     // Catch:{ all -> 0x0055 }
            if (r7 == 0) goto L_0x0080
            com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus$Code r5 = com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus.Code.UNKNOWN_ERROR     // Catch:{ all -> 0x0055 }
            java.lang.String r6 = "[MethodTracing.doMethodTracing] "
            java.lang.String r4 = java.lang.String.valueOf(r4)     // Catch:{ all -> 0x0055 }
            java.lang.String r4 = r6.concat(r4)     // Catch:{ all -> 0x0055 }
            r7.onFail(r5, r4)     // Catch:{ all -> 0x0055 }
        L_0x0080:
            com.alipay.mobile.common.logging.api.trace.TraceLogger r4 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x009b }
            java.lang.String r5 = TAG     // Catch:{ all -> 0x009b }
            java.lang.String r6 = "startMethodTracing end"
            r4.info(r5, r6)     // Catch:{ all -> 0x009b }
            monitor-exit(r3)
            return
        L_0x008e:
            com.alipay.mobile.common.logging.api.trace.TraceLogger r5 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x009b }
            java.lang.String r6 = TAG     // Catch:{ all -> 0x009b }
            java.lang.String r7 = "startMethodTracing end"
            r5.info(r6, r7)     // Catch:{ all -> 0x009b }
            throw r4     // Catch:{ all -> 0x009b }
        L_0x009b:
            r4 = move-exception
            monitor-exit(r3)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.miniapp.monitor.biz.logmonitor.util.tracing.MethodTracing.doMethodTracing(java.lang.String, long, com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus, int):void");
    }
}
