package com.autonavi.sdk.location.monitor;

import android.content.BroadcastReceiver;

public class LocationContext$4 extends BroadcastReceiver {
    final /* synthetic */ eox a;

    public LocationContext$4(eox eox) {
        this.a = eox;
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onReceive(android.content.Context r7, android.content.Intent r8) {
        /*
            r6 = this;
            if (r8 == 0) goto L_0x0147
            java.lang.String r7 = r8.getAction()
            if (r7 != 0) goto L_0x000a
            goto L_0x0147
        L_0x000a:
            boolean r7 = defpackage.epk.a     // Catch:{ Throwable -> 0x013e }
            if (r7 == 0) goto L_0x0026
            java.lang.String r7 = "LMLocationContext"
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x013e }
            java.lang.String r1 = "系统状态开关发生变化 收到广播 ："
            r0.<init>(r1)     // Catch:{ Throwable -> 0x013e }
            java.lang.String r1 = r8.getAction()     // Catch:{ Throwable -> 0x013e }
            r0.append(r1)     // Catch:{ Throwable -> 0x013e }
            java.lang.String r0 = r0.toString()     // Catch:{ Throwable -> 0x013e }
            defpackage.epk.a(r7, r0)     // Catch:{ Throwable -> 0x013e }
        L_0x0026:
            java.lang.String r7 = r8.getAction()     // Catch:{ Throwable -> 0x013e }
            int r0 = r7.hashCode()     // Catch:{ Throwable -> 0x013e }
            r1 = 2
            r2 = 1
            r3 = 3
            r4 = 4
            r5 = -1
            switch(r0) {
                case -1875733435: goto L_0x005f;
                case -1184851779: goto L_0x0055;
                case -1172645946: goto L_0x004b;
                case -1076576821: goto L_0x0041;
                case 1878357501: goto L_0x0037;
                default: goto L_0x0036;
            }     // Catch:{ Throwable -> 0x013e }
        L_0x0036:
            goto L_0x0069
        L_0x0037:
            java.lang.String r0 = "android.net.wifi.SCAN_RESULTS"
            boolean r7 = r7.equals(r0)     // Catch:{ Throwable -> 0x013e }
            if (r7 == 0) goto L_0x0069
            r7 = 0
            goto L_0x006a
        L_0x0041:
            java.lang.String r0 = "android.intent.action.AIRPLANE_MODE"
            boolean r7 = r7.equals(r0)     // Catch:{ Throwable -> 0x013e }
            if (r7 == 0) goto L_0x0069
            r7 = 4
            goto L_0x006a
        L_0x004b:
            java.lang.String r0 = "android.net.conn.CONNECTIVITY_CHANGE"
            boolean r7 = r7.equals(r0)     // Catch:{ Throwable -> 0x013e }
            if (r7 == 0) goto L_0x0069
            r7 = 3
            goto L_0x006a
        L_0x0055:
            java.lang.String r0 = "android.location.PROVIDERS_CHANGED"
            boolean r7 = r7.equals(r0)     // Catch:{ Throwable -> 0x013e }
            if (r7 == 0) goto L_0x0069
            r7 = 1
            goto L_0x006a
        L_0x005f:
            java.lang.String r0 = "android.net.wifi.WIFI_STATE_CHANGED"
            boolean r7 = r7.equals(r0)     // Catch:{ Throwable -> 0x013e }
            if (r7 == 0) goto L_0x0069
            r7 = 2
            goto L_0x006a
        L_0x0069:
            r7 = -1
        L_0x006a:
            switch(r7) {
                case 0: goto L_0x0119;
                case 1: goto L_0x00f5;
                case 2: goto L_0x00c0;
                case 3: goto L_0x0095;
                case 4: goto L_0x006f;
                default: goto L_0x006d;
            }     // Catch:{ Throwable -> 0x013e }
        L_0x006d:
            goto L_0x013d
        L_0x006f:
            eox r7 = r6.a     // Catch:{ Throwable -> 0x013e }
            java.util.concurrent.locks.ReentrantReadWriteLock r7 = r7.d     // Catch:{ Throwable -> 0x013e }
            java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r7 = r7.readLock()     // Catch:{ Throwable -> 0x013e }
            r7.lock()     // Catch:{ Throwable -> 0x013e }
            eox r7 = r6.a     // Catch:{ Throwable -> 0x013e }
            eox$a r7 = r7.c     // Catch:{ Throwable -> 0x013e }
            if (r7 == 0) goto L_0x0088
            eox r7 = r6.a     // Catch:{ Throwable -> 0x013e }
            eox$a r7 = r7.c     // Catch:{ Throwable -> 0x013e }
            r8 = 5
            r7.sendEmptyMessage(r8)     // Catch:{ Throwable -> 0x013e }
        L_0x0088:
            eox r7 = r6.a     // Catch:{ Throwable -> 0x013e }
            java.util.concurrent.locks.ReentrantReadWriteLock r7 = r7.d     // Catch:{ Throwable -> 0x013e }
            java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r7 = r7.readLock()     // Catch:{ Throwable -> 0x013e }
            r7.unlock()     // Catch:{ Throwable -> 0x013e }
            goto L_0x013d
        L_0x0095:
            eox r7 = r6.a     // Catch:{ Throwable -> 0x013e }
            java.util.concurrent.locks.ReentrantReadWriteLock r7 = r7.d     // Catch:{ Throwable -> 0x013e }
            java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r7 = r7.readLock()     // Catch:{ Throwable -> 0x013e }
            r7.lock()     // Catch:{ Throwable -> 0x013e }
            eox r7 = r6.a     // Catch:{ Throwable -> 0x013e }
            eox$a r7 = r7.c     // Catch:{ Throwable -> 0x013e }
            if (r7 == 0) goto L_0x00b4
            eox r7 = r6.a     // Catch:{ Throwable -> 0x013e }
            eox$a r7 = r7.c     // Catch:{ Throwable -> 0x013e }
            long r0 = android.os.SystemClock.uptimeMillis()     // Catch:{ Throwable -> 0x013e }
            r2 = 500(0x1f4, double:2.47E-321)
            long r0 = r0 + r2
            r7.sendEmptyMessageAtTime(r4, r0)     // Catch:{ Throwable -> 0x013e }
        L_0x00b4:
            eox r7 = r6.a     // Catch:{ Throwable -> 0x013e }
            java.util.concurrent.locks.ReentrantReadWriteLock r7 = r7.d     // Catch:{ Throwable -> 0x013e }
            java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r7 = r7.readLock()     // Catch:{ Throwable -> 0x013e }
            r7.unlock()     // Catch:{ Throwable -> 0x013e }
            return
        L_0x00c0:
            eox r7 = r6.a     // Catch:{ Throwable -> 0x013e }
            java.util.concurrent.locks.ReentrantReadWriteLock r7 = r7.d     // Catch:{ Throwable -> 0x013e }
            java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r7 = r7.readLock()     // Catch:{ Throwable -> 0x013e }
            r7.lock()     // Catch:{ Throwable -> 0x013e }
            eox r7 = r6.a     // Catch:{ Throwable -> 0x013e }
            eox$a r7 = r7.c     // Catch:{ Throwable -> 0x013e }
            if (r7 == 0) goto L_0x00e9
            eox r7 = r6.a     // Catch:{ Throwable -> 0x013e }
            eox$a r7 = r7.c     // Catch:{ Throwable -> 0x013e }
            android.os.Message r7 = r7.obtainMessage(r3)     // Catch:{ Throwable -> 0x013e }
            java.lang.String r0 = "wifi_state"
            int r8 = r8.getIntExtra(r0, r5)     // Catch:{ Throwable -> 0x013e }
            r7.arg1 = r8     // Catch:{ Throwable -> 0x013e }
            eox r8 = r6.a     // Catch:{ Throwable -> 0x013e }
            eox$a r8 = r8.c     // Catch:{ Throwable -> 0x013e }
            r8.sendMessage(r7)     // Catch:{ Throwable -> 0x013e }
        L_0x00e9:
            eox r7 = r6.a     // Catch:{ Throwable -> 0x013e }
            java.util.concurrent.locks.ReentrantReadWriteLock r7 = r7.d     // Catch:{ Throwable -> 0x013e }
            java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r7 = r7.readLock()     // Catch:{ Throwable -> 0x013e }
            r7.unlock()     // Catch:{ Throwable -> 0x013e }
            return
        L_0x00f5:
            eox r7 = r6.a     // Catch:{ Throwable -> 0x013e }
            java.util.concurrent.locks.ReentrantReadWriteLock r7 = r7.d     // Catch:{ Throwable -> 0x013e }
            java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r7 = r7.readLock()     // Catch:{ Throwable -> 0x013e }
            r7.lock()     // Catch:{ Throwable -> 0x013e }
            eox r7 = r6.a     // Catch:{ Throwable -> 0x013e }
            eox$a r7 = r7.c     // Catch:{ Throwable -> 0x013e }
            if (r7 == 0) goto L_0x010d
            eox r7 = r6.a     // Catch:{ Throwable -> 0x013e }
            eox$a r7 = r7.c     // Catch:{ Throwable -> 0x013e }
            r7.sendEmptyMessage(r1)     // Catch:{ Throwable -> 0x013e }
        L_0x010d:
            eox r7 = r6.a     // Catch:{ Throwable -> 0x013e }
            java.util.concurrent.locks.ReentrantReadWriteLock r7 = r7.d     // Catch:{ Throwable -> 0x013e }
            java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r7 = r7.readLock()     // Catch:{ Throwable -> 0x013e }
            r7.unlock()     // Catch:{ Throwable -> 0x013e }
            return
        L_0x0119:
            eox r7 = r6.a     // Catch:{ Throwable -> 0x013e }
            java.util.concurrent.locks.ReentrantReadWriteLock r7 = r7.d     // Catch:{ Throwable -> 0x013e }
            java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r7 = r7.readLock()     // Catch:{ Throwable -> 0x013e }
            r7.lock()     // Catch:{ Throwable -> 0x013e }
            eox r7 = r6.a     // Catch:{ Throwable -> 0x013e }
            eox$a r7 = r7.c     // Catch:{ Throwable -> 0x013e }
            if (r7 == 0) goto L_0x0131
            eox r7 = r6.a     // Catch:{ Throwable -> 0x013e }
            eox$a r7 = r7.c     // Catch:{ Throwable -> 0x013e }
            r7.sendEmptyMessage(r2)     // Catch:{ Throwable -> 0x013e }
        L_0x0131:
            eox r7 = r6.a     // Catch:{ Throwable -> 0x013e }
            java.util.concurrent.locks.ReentrantReadWriteLock r7 = r7.d     // Catch:{ Throwable -> 0x013e }
            java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r7 = r7.readLock()     // Catch:{ Throwable -> 0x013e }
            r7.unlock()     // Catch:{ Throwable -> 0x013e }
            return
        L_0x013d:
            return
        L_0x013e:
            r7 = move-exception
            boolean r8 = defpackage.epk.a
            if (r8 == 0) goto L_0x0146
            defpackage.epk.a(r7)
        L_0x0146:
            return
        L_0x0147:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.sdk.location.monitor.LocationContext$4.onReceive(android.content.Context, android.content.Intent):void");
    }
}
