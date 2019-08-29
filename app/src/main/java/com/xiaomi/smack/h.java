package com.xiaomi.smack;

import android.os.SystemClock;
import android.support.v4.view.MotionEventCompat;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.channel.commonutils.network.d;
import com.xiaomi.network.Fallback;
import com.xiaomi.network.HostManager;
import com.xiaomi.push.service.XMPushService;
import com.xiaomi.smack.util.e;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public abstract class h extends a {
    protected Exception p = null;
    protected Socket q;
    String r = null;
    protected XMPushService s;
    protected volatile long t = 0;
    protected volatile long u = 0;
    protected volatile long v = 0;
    private String w;
    private int x;

    public h(XMPushService xMPushService, b bVar) {
        super(xMPushService, bVar);
        this.s = xMPushService;
    }

    private void a(b bVar) {
        a(bVar.e(), bVar.d());
    }

    /* JADX WARNING: Removed duplicated region for block: B:33:0x00f6  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x0119 A[SYNTHETIC, Splitter:B:42:0x0119] */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x012c A[Catch:{ all -> 0x018b }] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x015f  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x0178 A[SYNTHETIC, Splitter:B:52:0x0178] */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x0190 A[Catch:{ all -> 0x018b }] */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x01c3  */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x01db  */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x01fc  */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x0206 A[RETURN] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(java.lang.String r22, int r23) {
        /*
            r21 = this;
            r1 = r21
            r2 = r23
            r3 = 0
            r1.p = r3
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            java.lang.String r4 = "get bucket for host : "
            java.lang.String r5 = java.lang.String.valueOf(r22)
            java.lang.String r4 = r4.concat(r5)
            java.lang.Integer r4 = com.xiaomi.channel.commonutils.logger.b.e(r4)
            int r4 = r4.intValue()
            com.xiaomi.network.Fallback r12 = r21.b(r22)
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            com.xiaomi.channel.commonutils.logger.b.a(r4)
            r4 = 1
            if (r12 == 0) goto L_0x0030
            java.util.ArrayList r3 = r12.a(r4)
        L_0x0030:
            boolean r5 = r3.isEmpty()
            if (r5 == 0) goto L_0x003b
            r5 = r22
            r3.add(r5)
        L_0x003b:
            r5 = 0
            r1.v = r5
            com.xiaomi.push.service.XMPushService r5 = r1.s
            java.lang.String r13 = com.xiaomi.channel.commonutils.network.d.k(r5)
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            java.util.Iterator r3 = r3.iterator()
            r5 = 0
        L_0x004f:
            boolean r6 = r3.hasNext()
            if (r6 == 0) goto L_0x01f1
            java.lang.Object r6 = r3.next()
            r15 = r6
            java.lang.String r15 = (java.lang.String) r15
            long r16 = java.lang.System.currentTimeMillis()
            int r6 = r1.b
            int r6 = r6 + r4
            r1.b = r6
            java.lang.String r6 = "begin to connect to "
            java.lang.String r7 = java.lang.String.valueOf(r15)     // Catch:{ IOException -> 0x0172, l -> 0x0113, Throwable -> 0x00e5, all -> 0x00e0 }
            java.lang.String r6 = r6.concat(r7)     // Catch:{ IOException -> 0x0172, l -> 0x0113, Throwable -> 0x00e5, all -> 0x00e0 }
            com.xiaomi.channel.commonutils.logger.b.a(r6)     // Catch:{ IOException -> 0x0172, l -> 0x0113, Throwable -> 0x00e5, all -> 0x00e0 }
            java.net.Socket r6 = r21.u()     // Catch:{ IOException -> 0x0172, l -> 0x0113, Throwable -> 0x00e5, all -> 0x00e0 }
            r1.q = r6     // Catch:{ IOException -> 0x0172, l -> 0x0113, Throwable -> 0x00e5, all -> 0x00e0 }
            java.net.InetSocketAddress r6 = com.xiaomi.network.Host.b(r15, r2)     // Catch:{ IOException -> 0x0172, l -> 0x0113, Throwable -> 0x00e5, all -> 0x00e0 }
            java.net.Socket r7 = r1.q     // Catch:{ IOException -> 0x0172, l -> 0x0113, Throwable -> 0x00e5, all -> 0x00e0 }
            r8 = 8000(0x1f40, float:1.121E-41)
            r7.connect(r6, r8)     // Catch:{ IOException -> 0x0172, l -> 0x0113, Throwable -> 0x00e5, all -> 0x00e0 }
            java.lang.String r6 = "tcp connected"
            com.xiaomi.channel.commonutils.logger.b.a(r6)     // Catch:{ IOException -> 0x0172, l -> 0x0113, Throwable -> 0x00e5, all -> 0x00e0 }
            java.net.Socket r6 = r1.q     // Catch:{ IOException -> 0x0172, l -> 0x0113, Throwable -> 0x00e5, all -> 0x00e0 }
            r6.setTcpNoDelay(r4)     // Catch:{ IOException -> 0x0172, l -> 0x0113, Throwable -> 0x00e5, all -> 0x00e0 }
            r1.w = r15     // Catch:{ IOException -> 0x0172, l -> 0x0113, Throwable -> 0x00e5, all -> 0x00e0 }
            r21.c()     // Catch:{ IOException -> 0x0172, l -> 0x0113, Throwable -> 0x00e5, all -> 0x00e0 }
            long r5 = java.lang.System.currentTimeMillis()     // Catch:{ IOException -> 0x00da, l -> 0x00d5, Throwable -> 0x00d1, all -> 0x00cd }
            r7 = 0
            long r5 = r5 - r16
            r1.c = r5     // Catch:{ IOException -> 0x00da, l -> 0x00d5, Throwable -> 0x00d1, all -> 0x00cd }
            r1.k = r13     // Catch:{ IOException -> 0x00da, l -> 0x00d5, Throwable -> 0x00d1, all -> 0x00cd }
            if (r12 == 0) goto L_0x00a8
            long r7 = r1.c     // Catch:{ IOException -> 0x00da, l -> 0x00d5, Throwable -> 0x00d1, all -> 0x00cd }
            r9 = 0
            r5 = r12
            r6 = r15
            r5.b(r6, r7, r9)     // Catch:{ IOException -> 0x00da, l -> 0x00d5, Throwable -> 0x00d1, all -> 0x00cd }
        L_0x00a8:
            long r5 = android.os.SystemClock.elapsedRealtime()     // Catch:{ IOException -> 0x00da, l -> 0x00d5, Throwable -> 0x00d1, all -> 0x00cd }
            r1.v = r5     // Catch:{ IOException -> 0x00da, l -> 0x00d5, Throwable -> 0x00d1, all -> 0x00cd }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00da, l -> 0x00d5, Throwable -> 0x00d1, all -> 0x00cd }
            java.lang.String r6 = "connected to "
            r5.<init>(r6)     // Catch:{ IOException -> 0x00da, l -> 0x00d5, Throwable -> 0x00d1, all -> 0x00cd }
            r5.append(r15)     // Catch:{ IOException -> 0x00da, l -> 0x00d5, Throwable -> 0x00d1, all -> 0x00cd }
            java.lang.String r6 = " in "
            r5.append(r6)     // Catch:{ IOException -> 0x00da, l -> 0x00d5, Throwable -> 0x00d1, all -> 0x00cd }
            long r6 = r1.c     // Catch:{ IOException -> 0x00da, l -> 0x00d5, Throwable -> 0x00d1, all -> 0x00cd }
            r5.append(r6)     // Catch:{ IOException -> 0x00da, l -> 0x00d5, Throwable -> 0x00d1, all -> 0x00cd }
            java.lang.String r5 = r5.toString()     // Catch:{ IOException -> 0x00da, l -> 0x00d5, Throwable -> 0x00d1, all -> 0x00cd }
            com.xiaomi.channel.commonutils.logger.b.a(r5)     // Catch:{ IOException -> 0x00da, l -> 0x00d5, Throwable -> 0x00d1, all -> 0x00cd }
            r18 = 1
            goto L_0x01f3
        L_0x00cd:
            r0 = move-exception
            r2 = r0
            goto L_0x01d9
        L_0x00d1:
            r0 = move-exception
            r5 = r0
            r6 = 1
            goto L_0x00e8
        L_0x00d5:
            r0 = move-exception
            r11 = r0
            r18 = 1
            goto L_0x0117
        L_0x00da:
            r0 = move-exception
            r11 = r0
            r18 = 1
            goto L_0x0176
        L_0x00e0:
            r0 = move-exception
            r2 = r0
            r4 = r5
            goto L_0x01d9
        L_0x00e5:
            r0 = move-exception
            r6 = r5
            r5 = r0
        L_0x00e8:
            java.lang.Exception r7 = new java.lang.Exception     // Catch:{ all -> 0x010e }
            java.lang.String r8 = "abnormal exception"
            r7.<init>(r8, r5)     // Catch:{ all -> 0x010e }
            r1.p = r7     // Catch:{ all -> 0x010e }
            com.xiaomi.channel.commonutils.logger.b.a(r5)     // Catch:{ all -> 0x010e }
            if (r6 != 0) goto L_0x010b
            java.lang.Exception r5 = r1.p
            com.xiaomi.stats.h.a(r15, r5)
            com.xiaomi.push.service.XMPushService r5 = r1.s
            java.lang.String r5 = com.xiaomi.channel.commonutils.network.d.k(r5)
            boolean r5 = android.text.TextUtils.equals(r13, r5)
            if (r5 != 0) goto L_0x010b
            r18 = r6
            goto L_0x01f3
        L_0x010b:
            r5 = r6
            goto L_0x004f
        L_0x010e:
            r0 = move-exception
            r2 = r0
            r4 = r6
            goto L_0x01d9
        L_0x0113:
            r0 = move-exception
            r11 = r0
            r18 = r5
        L_0x0117:
            if (r12 == 0) goto L_0x012c
            long r5 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x018b }
            r7 = 0
            long r7 = r5 - r16
            r9 = 0
            r5 = r12
            r6 = r15
            r19 = r11
            r5.b(r6, r7, r9, r11)     // Catch:{ all -> 0x018b }
            r5 = r19
            goto L_0x012d
        L_0x012c:
            r5 = r11
        L_0x012d:
            r1.p = r5     // Catch:{ all -> 0x018b }
            java.lang.String r6 = "SMACK: Could not connect to:"
            java.lang.String r7 = java.lang.String.valueOf(r15)     // Catch:{ all -> 0x018b }
            java.lang.String r6 = r6.concat(r7)     // Catch:{ all -> 0x018b }
            com.xiaomi.channel.commonutils.logger.b.d(r6)     // Catch:{ all -> 0x018b }
            java.lang.String r6 = "SMACK: Could not connect to "
            r14.append(r6)     // Catch:{ all -> 0x018b }
            r14.append(r15)     // Catch:{ all -> 0x018b }
            java.lang.String r6 = " port:"
            r14.append(r6)     // Catch:{ all -> 0x018b }
            r14.append(r2)     // Catch:{ all -> 0x018b }
            java.lang.String r6 = " "
            r14.append(r6)     // Catch:{ all -> 0x018b }
            java.lang.String r5 = r5.getMessage()     // Catch:{ all -> 0x018b }
            r14.append(r5)     // Catch:{ all -> 0x018b }
            java.lang.String r5 = "\n"
            r14.append(r5)     // Catch:{ all -> 0x018b }
            if (r18 != 0) goto L_0x01d5
            java.lang.Exception r5 = r1.p
            com.xiaomi.stats.h.a(r15, r5)
            com.xiaomi.push.service.XMPushService r5 = r1.s
            java.lang.String r5 = com.xiaomi.channel.commonutils.network.d.k(r5)
            boolean r5 = android.text.TextUtils.equals(r13, r5)
            if (r5 != 0) goto L_0x01d5
            goto L_0x01f3
        L_0x0172:
            r0 = move-exception
            r11 = r0
            r18 = r5
        L_0x0176:
            if (r12 == 0) goto L_0x0190
            long r5 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x018b }
            r7 = 0
            long r7 = r5 - r16
            r9 = 0
            r5 = r12
            r6 = r15
            r20 = r11
            r5.b(r6, r7, r9, r11)     // Catch:{ all -> 0x018b }
            r5 = r20
            goto L_0x0191
        L_0x018b:
            r0 = move-exception
            r2 = r0
            r4 = r18
            goto L_0x01d9
        L_0x0190:
            r5 = r11
        L_0x0191:
            r1.p = r5     // Catch:{ all -> 0x018b }
            java.lang.String r6 = "SMACK: Could not connect to:"
            java.lang.String r7 = java.lang.String.valueOf(r15)     // Catch:{ all -> 0x018b }
            java.lang.String r6 = r6.concat(r7)     // Catch:{ all -> 0x018b }
            com.xiaomi.channel.commonutils.logger.b.d(r6)     // Catch:{ all -> 0x018b }
            java.lang.String r6 = "SMACK: Could not connect to "
            r14.append(r6)     // Catch:{ all -> 0x018b }
            r14.append(r15)     // Catch:{ all -> 0x018b }
            java.lang.String r6 = " port:"
            r14.append(r6)     // Catch:{ all -> 0x018b }
            r14.append(r2)     // Catch:{ all -> 0x018b }
            java.lang.String r6 = " "
            r14.append(r6)     // Catch:{ all -> 0x018b }
            java.lang.String r5 = r5.getMessage()     // Catch:{ all -> 0x018b }
            r14.append(r5)     // Catch:{ all -> 0x018b }
            java.lang.String r5 = "\n"
            r14.append(r5)     // Catch:{ all -> 0x018b }
            if (r18 != 0) goto L_0x01d5
            java.lang.Exception r5 = r1.p
            com.xiaomi.stats.h.a(r15, r5)
            com.xiaomi.push.service.XMPushService r5 = r1.s
            java.lang.String r5 = com.xiaomi.channel.commonutils.network.d.k(r5)
            boolean r5 = android.text.TextUtils.equals(r13, r5)
            if (r5 != 0) goto L_0x01d5
            goto L_0x01f3
        L_0x01d5:
            r5 = r18
            goto L_0x004f
        L_0x01d9:
            if (r4 != 0) goto L_0x01f0
            java.lang.Exception r3 = r1.p
            com.xiaomi.stats.h.a(r15, r3)
            com.xiaomi.push.service.XMPushService r3 = r1.s
            java.lang.String r3 = com.xiaomi.channel.commonutils.network.d.k(r3)
            boolean r3 = android.text.TextUtils.equals(r13, r3)
            if (r3 == 0) goto L_0x01ed
            goto L_0x01f0
        L_0x01ed:
            r18 = r4
            goto L_0x01f3
        L_0x01f0:
            throw r2
        L_0x01f1:
            r18 = r5
        L_0x01f3:
            com.xiaomi.network.HostManager r2 = com.xiaomi.network.HostManager.getInstance()
            r2.persist()
            if (r18 != 0) goto L_0x0206
            com.xiaomi.smack.l r2 = new com.xiaomi.smack.l
            java.lang.String r3 = r14.toString()
            r2.<init>(r3)
            throw r2
        L_0x0206:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.smack.h.a(java.lang.String, int):void");
    }

    /* access modifiers changed from: protected */
    public synchronized void a(int i, Exception exc) {
        if (n() != 2) {
            a(2, i, exc);
            this.j = "";
            try {
                this.q.close();
            } catch (Throwable unused) {
            }
            this.t = 0;
            this.u = 0;
        }
    }

    /* access modifiers changed from: protected */
    public void a(Exception exc) {
        if (SystemClock.elapsedRealtime() - this.v < 300000) {
            if (d.c(this.s)) {
                this.x++;
                if (this.x >= 2) {
                    String e = e();
                    b.a("max short conn time reached, sink down current host:".concat(String.valueOf(e)));
                    a(e, 0, exc);
                } else {
                    return;
                }
            } else {
                return;
            }
        }
        this.x = 0;
    }

    /* access modifiers changed from: protected */
    public void a(String str, long j, Exception exc) {
        Fallback fallbacksByHost = HostManager.getInstance().getFallbacksByHost(b.b(), false);
        if (fallbacksByHost != null) {
            fallbacksByHost.b(str, j, 0, exc);
            HostManager.getInstance().persist();
        }
    }

    /* access modifiers changed from: protected */
    public abstract void a(boolean z);

    public void a(com.xiaomi.slim.b[] bVarArr) {
        throw new l((String) "Don't support send Blob");
    }

    /* access modifiers changed from: 0000 */
    public Fallback b(String str) {
        Fallback fallbacksByHost = HostManager.getInstance().getFallbacksByHost(str, false);
        if (!fallbacksByHost.b()) {
            e.a((Runnable) new k(this, str));
        }
        this.f = 0;
        try {
            byte[] address = InetAddress.getByName(fallbacksByHost.f).getAddress();
            this.f = address[0] & 255;
            this.f |= (address[1] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK;
            this.f |= (address[2] << 16) & 16711680;
            this.f = ((address[3] << 24) & -16777216) | this.f;
        } catch (UnknownHostException unused) {
        }
        return fallbacksByHost;
    }

    public void b(int i, Exception exc) {
        a(i, exc);
        if ((exc != null || i == 18) && this.v != 0) {
            a(exc);
        }
    }

    public void b(boolean z) {
        long currentTimeMillis = System.currentTimeMillis();
        a(z);
        if (!z) {
            this.s.a((com.xiaomi.push.service.XMPushService.h) new i(this, 13, currentTimeMillis), 10000);
        }
    }

    /* access modifiers changed from: protected */
    public synchronized void c() {
    }

    public void c(int i, Exception exc) {
        this.s.a((com.xiaomi.push.service.XMPushService.h) new j(this, 2, i, exc));
    }

    public String e() {
        return this.w;
    }

    public String s() {
        return this.j;
    }

    public synchronized void t() {
        try {
            if (!l()) {
                if (!k()) {
                    a(0, 0, null);
                    a(this.m);
                    return;
                }
            }
            b.a((String) "WARNING: current xmpp has connected");
        } catch (IOException e) {
            throw new l((Throwable) e);
        }
    }

    public Socket u() {
        return new Socket();
    }

    public void v() {
        this.t = SystemClock.elapsedRealtime();
    }

    public void w() {
        this.u = SystemClock.elapsedRealtime();
    }
}
