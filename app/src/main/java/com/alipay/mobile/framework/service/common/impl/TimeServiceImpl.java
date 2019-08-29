package com.alipay.mobile.framework.service.common.impl;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.content.LocalBroadcastManager;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.rpc.RpcHeaderListener;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.service.common.RpcService;
import com.alipay.mobile.framework.service.common.TimeService;
import com.alipay.mobile.quinox.utils.SharedPreferenceUtil;

public class TimeServiceImpl extends TimeService {
    /* access modifiers changed from: private */
    public long a = 0;
    /* access modifiers changed from: private */
    public long b = 0;
    /* access modifiers changed from: private */
    public boolean c = false;

    public class HeaderUpdateListener implements RpcHeaderListener {
        protected HeaderUpdateListener() {
        }

        /* JADX WARNING: Code restructure failed: missing block: B:42:?, code lost:
            return;
         */
        /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onRpcHeaderUpdateEvent(com.alipay.mobile.common.rpc.RpcHeader r17) {
            /*
                r16 = this;
                java.lang.Class<com.alipay.mobile.framework.service.common.impl.TimeServiceImpl> r9 = com.alipay.mobile.framework.service.common.impl.TimeServiceImpl.class
                monitor-enter(r9)
                r0 = r16
                com.alipay.mobile.framework.service.common.impl.TimeServiceImpl r8 = com.alipay.mobile.framework.service.common.impl.TimeServiceImpl.this     // Catch:{ all -> 0x003c }
                r10 = 1
                r8.c = r10     // Catch:{ all -> 0x003c }
                if (r17 == 0) goto L_0x00d8
                r0 = r17
                com.alipay.mobile.common.transport.http.HttpUrlHeader r8 = r0.httpUrlHeader     // Catch:{ all -> 0x003c }
                if (r8 == 0) goto L_0x00d8
                r0 = r17
                com.alipay.mobile.common.transport.http.HttpUrlHeader r8 = r0.httpUrlHeader     // Catch:{ all -> 0x003c }
                java.lang.String r10 = "Server-Time"
                java.lang.String r4 = r8.getHead(r10)     // Catch:{ all -> 0x003c }
                r2 = 0
                boolean r8 = android.text.TextUtils.isEmpty(r4)     // Catch:{ all -> 0x003c }
                if (r8 != 0) goto L_0x0029
                long r2 = java.lang.Long.parseLong(r4)     // Catch:{ NumberFormatException -> 0x0031 }
            L_0x0029:
                r10 = 0
                int r8 = (r2 > r10 ? 1 : (r2 == r10 ? 0 : -1))
                if (r8 > 0) goto L_0x003f
                monitor-exit(r9)     // Catch:{ all -> 0x003c }
            L_0x0030:
                return
            L_0x0031:
                r1 = move-exception
                com.alipay.mobile.common.logging.api.trace.TraceLogger r8 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x003c }
                java.lang.String r10 = "TimeService"
                r8.error(r10, r1)     // Catch:{ all -> 0x003c }
                goto L_0x0029
            L_0x003c:
                r8 = move-exception
                monitor-exit(r9)     // Catch:{ all -> 0x003c }
                throw r8
            L_0x003f:
                r7 = 0
                r6 = 0
                r0 = r16
                com.alipay.mobile.framework.service.common.impl.TimeServiceImpl r8 = com.alipay.mobile.framework.service.common.impl.TimeServiceImpl.this     // Catch:{ all -> 0x003c }
                boolean r8 = r8.a()     // Catch:{ all -> 0x003c }
                if (r8 != 0) goto L_0x00db
                r7 = 1
                r6 = 1
            L_0x004d:
                if (r7 == 0) goto L_0x00d8
                com.alipay.mobile.common.logging.api.trace.TraceLogger r8 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x003c }
                java.lang.String r10 = "TimeService"
                java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ all -> 0x003c }
                java.lang.String r12 = "stored serverTime:"
                r11.<init>(r12)     // Catch:{ all -> 0x003c }
                r0 = r16
                com.alipay.mobile.framework.service.common.impl.TimeServiceImpl r12 = com.alipay.mobile.framework.service.common.impl.TimeServiceImpl.this     // Catch:{ all -> 0x003c }
                long r12 = r12.a     // Catch:{ all -> 0x003c }
                java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ all -> 0x003c }
                java.lang.String r12 = " stored updateTime:"
                java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ all -> 0x003c }
                r0 = r16
                com.alipay.mobile.framework.service.common.impl.TimeServiceImpl r12 = com.alipay.mobile.framework.service.common.impl.TimeServiceImpl.this     // Catch:{ all -> 0x003c }
                long r12 = r12.b     // Catch:{ all -> 0x003c }
                java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ all -> 0x003c }
                java.lang.String r12 = " new serverTime:"
                java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ all -> 0x003c }
                java.lang.StringBuilder r11 = r11.append(r2)     // Catch:{ all -> 0x003c }
                java.lang.String r12 = " new updateTime:"
                java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ all -> 0x003c }
                long r12 = android.os.SystemClock.elapsedRealtime()     // Catch:{ all -> 0x003c }
                java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ all -> 0x003c }
                java.lang.String r11 = r11.toString()     // Catch:{ all -> 0x003c }
                r8.info(r10, r11)     // Catch:{ all -> 0x003c }
                r0 = r16
                com.alipay.mobile.framework.service.common.impl.TimeServiceImpl r8 = com.alipay.mobile.framework.service.common.impl.TimeServiceImpl.this     // Catch:{ all -> 0x003c }
                r8.a = r2     // Catch:{ all -> 0x003c }
                r0 = r16
                com.alipay.mobile.framework.service.common.impl.TimeServiceImpl r8 = com.alipay.mobile.framework.service.common.impl.TimeServiceImpl.this     // Catch:{ all -> 0x003c }
                long r10 = android.os.SystemClock.elapsedRealtime()     // Catch:{ all -> 0x003c }
                r8.b = r10     // Catch:{ all -> 0x003c }
                if (r6 == 0) goto L_0x00d8
                r0 = r16
                com.alipay.mobile.framework.service.common.impl.TimeServiceImpl r8 = com.alipay.mobile.framework.service.common.impl.TimeServiceImpl.this     // Catch:{ all -> 0x003c }
                boolean r8 = r8.a()     // Catch:{ all -> 0x003c }
                if (r8 == 0) goto L_0x00d8
                android.content.Intent r5 = new android.content.Intent     // Catch:{ all -> 0x003c }
                java.lang.String r8 = "com.alipay.mobile.timeservice.SERVER_TIME_UPDATED"
                r5.<init>(r8)     // Catch:{ all -> 0x003c }
                com.alipay.mobile.framework.LauncherApplicationAgent r8 = com.alipay.mobile.framework.LauncherApplicationAgent.getInstance()     // Catch:{ all -> 0x003c }
                android.app.Application r8 = r8.getApplicationContext()     // Catch:{ all -> 0x003c }
                android.support.v4.content.LocalBroadcastManager r8 = android.support.v4.content.LocalBroadcastManager.getInstance(r8)     // Catch:{ all -> 0x003c }
                r8.sendBroadcast(r5)     // Catch:{ all -> 0x003c }
                com.alipay.mobile.common.logging.api.trace.TraceLogger r8 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x003c }
                java.lang.String r10 = "TimeService"
                java.lang.String r11 = "com.alipay.mobile.timeservice.SERVER_TIME_UPDATED is sent."
                r8.info(r10, r11)     // Catch:{ all -> 0x003c }
            L_0x00d8:
                monitor-exit(r9)     // Catch:{ all -> 0x003c }
                goto L_0x0030
            L_0x00db:
                r0 = r16
                com.alipay.mobile.framework.service.common.impl.TimeServiceImpl r8 = com.alipay.mobile.framework.service.common.impl.TimeServiceImpl.this     // Catch:{ all -> 0x003c }
                long r10 = r8.a     // Catch:{ all -> 0x003c }
                long r12 = android.os.SystemClock.elapsedRealtime()     // Catch:{ all -> 0x003c }
                r0 = r16
                com.alipay.mobile.framework.service.common.impl.TimeServiceImpl r8 = com.alipay.mobile.framework.service.common.impl.TimeServiceImpl.this     // Catch:{ all -> 0x003c }
                long r14 = r8.b     // Catch:{ all -> 0x003c }
                long r12 = r12 - r14
                long r10 = r10 + r12
                int r8 = (r2 > r10 ? 1 : (r2 == r10 ? 0 : -1))
                if (r8 <= 0) goto L_0x00f8
                r7 = 1
                goto L_0x004d
            L_0x00f8:
                r0 = r16
                com.alipay.mobile.framework.service.common.impl.TimeServiceImpl r8 = com.alipay.mobile.framework.service.common.impl.TimeServiceImpl.this     // Catch:{ all -> 0x003c }
                long r10 = r8.a     // Catch:{ all -> 0x003c }
                long r12 = android.os.SystemClock.elapsedRealtime()     // Catch:{ all -> 0x003c }
                r0 = r16
                com.alipay.mobile.framework.service.common.impl.TimeServiceImpl r8 = com.alipay.mobile.framework.service.common.impl.TimeServiceImpl.this     // Catch:{ all -> 0x003c }
                long r14 = r8.b     // Catch:{ all -> 0x003c }
                long r12 = r12 - r14
                long r10 = r10 + r12
                long r10 = r10 - r2
                java.util.concurrent.TimeUnit r8 = java.util.concurrent.TimeUnit.MINUTES     // Catch:{ all -> 0x003c }
                r12 = 1
                long r12 = r8.toMillis(r12)     // Catch:{ all -> 0x003c }
                int r8 = (r10 > r12 ? 1 : (r10 == r12 ? 0 : -1))
                if (r8 <= 0) goto L_0x004d
                r7 = 1
                goto L_0x004d
            */
            throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.framework.service.common.impl.TimeServiceImpl.HeaderUpdateListener.onRpcHeaderUpdateEvent(com.alipay.mobile.common.rpc.RpcHeader):void");
        }
    }

    public TimeServiceImpl() {
        ((RpcService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(RpcService.class.getName())).addRpcHeaderListener(new HeaderUpdateListener());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.alipay.mobile.framework.USERLEAVEHINT");
        LocalBroadcastManager.getInstance(LauncherApplicationAgent.getInstance().getApplicationContext()).registerReceiver(new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                TimeServiceImpl.this.b();
            }
        }, intentFilter);
    }

    public long getServerTime() {
        long rTime;
        synchronized (TimeServiceImpl.class) {
            if (!a()) {
                this.a = 0;
                this.b = 0;
                if (this.c) {
                    LoggerFactory.getMonitorLogger().keyBizTrace("BIZ_TIME_SERVICE", "SERVER_TIME_NOT_AVAILABLE", "1000", null);
                }
                rTime = System.currentTimeMillis();
                LoggerFactory.getTraceLogger().info("TimeService", "no server time, return local time:" + rTime);
            } else {
                long localGap = SystemClock.elapsedRealtime() - this.b;
                rTime = this.a + localGap;
                LoggerFactory.getTraceLogger().info("TimeService", "return server time:" + rTime + " and stored server time:" + this.a + " localGap:" + localGap + " localTime:" + System.currentTimeMillis());
            }
        }
        return rTime;
    }

    public long getServerTime(boolean timeSensitive) {
        long serverTime;
        synchronized (TimeServiceImpl.class) {
            serverTime = getServerTime();
        }
        return serverTime;
    }

    public long getServerTimeMayOffline() {
        long rTime;
        synchronized (TimeServiceImpl.class) {
            if (a()) {
                rTime = getServerTime();
            } else {
                SharedPreferences spf = SharedPreferenceUtil.getInstance().getDefaultSharedPreference(LauncherApplicationAgent.getInstance().getApplicationContext());
                if (spf.contains("STORED_SERVER_LOCAL_TIME_GAP")) {
                    long storedServerLocalGap = spf.getLong("STORED_SERVER_LOCAL_TIME_GAP", 0);
                    long localTime = System.currentTimeMillis();
                    rTime = localTime + storedServerLocalGap;
                    LoggerFactory.getTraceLogger().info("TimeService", "no server time, return offline time:" + localTime + " + storedGap:" + storedServerLocalGap);
                } else {
                    if (this.c) {
                        LoggerFactory.getMonitorLogger().keyBizTrace("BIZ_TIME_SERVICE", "SERVER_TIME_NOT_AVAILABLE", "1000", null);
                    }
                    rTime = System.currentTimeMillis();
                    LoggerFactory.getTraceLogger().info("TimeService", "no server time, return local time:" + rTime);
                }
            }
        }
        return rTime;
    }

    /* access modifiers changed from: private */
    public boolean a() {
        boolean z;
        synchronized (TimeServiceImpl.class) {
            z = this.a > 0 && this.b > 0;
        }
        return z;
    }

    /* access modifiers changed from: private */
    public void b() {
        synchronized (TimeServiceImpl.class) {
            if (a()) {
                SharedPreferenceUtil.getInstance().getDefaultSharedPreference(LauncherApplicationAgent.getInstance().getApplicationContext()).edit().putLong("STORED_SERVER_LOCAL_TIME_GAP", getServerTime() - System.currentTimeMillis()).apply();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
    }

    /* access modifiers changed from: protected */
    public void onDestroy(Bundle bundle) {
    }
}
