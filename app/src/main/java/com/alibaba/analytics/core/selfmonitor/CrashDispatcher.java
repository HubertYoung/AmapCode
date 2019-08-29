package com.alibaba.analytics.core.selfmonitor;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CrashDispatcher implements UncaughtExceptionHandler {
    private static CrashDispatcher instance = new CrashDispatcher();
    private UncaughtExceptionHandler handler;
    private List<CrashListener> mlisteners = Collections.synchronizedList(new ArrayList());

    public static CrashDispatcher getInstance() {
        return instance;
    }

    public void init() {
        this.handler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x002e, code lost:
        if (r2.handler == null) goto L_0x0031;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0031, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0019, code lost:
        if (r2.handler != null) goto L_0x001b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x001b, code lost:
        r2.handler.uncaughtException(r3, r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0020, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void uncaughtException(java.lang.Thread r3, java.lang.Throwable r4) {
        /*
            r2 = this;
            r0 = 0
        L_0x0001:
            java.util.List<com.alibaba.analytics.core.selfmonitor.CrashListener> r1 = r2.mlisteners     // Catch:{ Throwable -> 0x002c, all -> 0x0021 }
            int r1 = r1.size()     // Catch:{ Throwable -> 0x002c, all -> 0x0021 }
            if (r0 >= r1) goto L_0x0017
            java.util.List<com.alibaba.analytics.core.selfmonitor.CrashListener> r1 = r2.mlisteners     // Catch:{ Throwable -> 0x002c, all -> 0x0021 }
            java.lang.Object r1 = r1.get(r0)     // Catch:{ Throwable -> 0x002c, all -> 0x0021 }
            com.alibaba.analytics.core.selfmonitor.CrashListener r1 = (com.alibaba.analytics.core.selfmonitor.CrashListener) r1     // Catch:{ Throwable -> 0x002c, all -> 0x0021 }
            r1.onCrash(r3, r4)     // Catch:{ Throwable -> 0x002c, all -> 0x0021 }
            int r0 = r0 + 1
            goto L_0x0001
        L_0x0017:
            java.lang.Thread$UncaughtExceptionHandler r0 = r2.handler
            if (r0 == 0) goto L_0x0031
        L_0x001b:
            java.lang.Thread$UncaughtExceptionHandler r0 = r2.handler
            r0.uncaughtException(r3, r4)
            return
        L_0x0021:
            r0 = move-exception
            java.lang.Thread$UncaughtExceptionHandler r1 = r2.handler
            if (r1 == 0) goto L_0x002b
            java.lang.Thread$UncaughtExceptionHandler r1 = r2.handler
            r1.uncaughtException(r3, r4)
        L_0x002b:
            throw r0
        L_0x002c:
            java.lang.Thread$UncaughtExceptionHandler r0 = r2.handler
            if (r0 == 0) goto L_0x0031
            goto L_0x001b
        L_0x0031:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.analytics.core.selfmonitor.CrashDispatcher.uncaughtException(java.lang.Thread, java.lang.Throwable):void");
    }

    public void addCrashListener(CrashListener crashListener) {
        this.mlisteners.add(crashListener);
    }
}
