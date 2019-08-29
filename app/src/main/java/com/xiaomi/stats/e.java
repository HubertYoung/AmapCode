package com.xiaomi.stats;

import android.net.TrafficStats;
import android.os.Process;
import android.os.SystemClock;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.service.XMPushService;
import com.xiaomi.smack.a;
import com.xiaomi.smack.d;
import com.xiaomi.smack.g;

public class e implements d {
    XMPushService a;
    a b;
    private int c;
    private Exception d;
    private String e;
    private long f = 0;
    private long g = 0;
    private long h = 0;
    private long i = 0;
    private long j = 0;
    private long k = 0;

    e(XMPushService xMPushService) {
        this.a = xMPushService;
        this.e = com.xiaomi.channel.commonutils.network.d.k(xMPushService);
        c();
        int myUid = Process.myUid();
        this.k = TrafficStats.getUidRxBytes(myUid);
        this.j = TrafficStats.getUidTxBytes(myUid);
    }

    private void c() {
        this.g = 0;
        this.i = 0;
        this.f = 0;
        this.h = 0;
        long elapsedRealtime = SystemClock.elapsedRealtime();
        if (com.xiaomi.channel.commonutils.network.d.c(this.a)) {
            this.f = elapsedRealtime;
        }
        if (this.a.f()) {
            this.h = elapsedRealtime;
        }
    }

    private synchronized void d() {
        StringBuilder sb = new StringBuilder("stat connpt = ");
        sb.append(this.e);
        sb.append(" netDuration = ");
        sb.append(this.g);
        sb.append(" ChannelDuration = ");
        sb.append(this.i);
        sb.append(" channelConnectedTime = ");
        sb.append(this.h);
        b.c(sb.toString());
        com.xiaomi.push.thrift.b bVar = new com.xiaomi.push.thrift.b();
        bVar.a = 0;
        bVar.a(com.xiaomi.push.thrift.a.CHANNEL_ONLINE_RATE.a());
        bVar.a(this.e);
        bVar.d((int) (System.currentTimeMillis() / 1000));
        bVar.b((int) (this.g / 1000));
        bVar.c((int) (this.i / 1000));
        f.a().a(bVar);
        c();
    }

    /* access modifiers changed from: 0000 */
    public Exception a() {
        return this.d;
    }

    public void a(a aVar) {
        b();
        this.h = SystemClock.elapsedRealtime();
        h.a(0, com.xiaomi.push.thrift.a.CONN_SUCCESS.a(), aVar.e(), aVar.m());
    }

    public void a(a aVar, int i2, Exception exc) {
        if (this.c == 0 && this.d == null) {
            this.c = i2;
            this.d = exc;
            h.b(aVar.e(), exc);
        }
        if (i2 == 22 && this.h != 0) {
            long h2 = aVar.h() - this.h;
            if (h2 < 0) {
                h2 = 0;
            }
            this.i += h2 + ((long) (g.c() / 2));
            this.h = 0;
        }
        b();
        int myUid = Process.myUid();
        long uidRxBytes = TrafficStats.getUidRxBytes(myUid);
        long uidTxBytes = TrafficStats.getUidTxBytes(myUid);
        StringBuilder sb = new StringBuilder("Stats rx=");
        sb.append(uidRxBytes - this.k);
        sb.append(", tx=");
        sb.append(uidTxBytes - this.j);
        b.c(sb.toString());
        this.k = uidRxBytes;
        this.j = uidTxBytes;
    }

    public void a(a aVar, Exception exc) {
        h.a(0, com.xiaomi.push.thrift.a.CHANNEL_CON_FAIL.a(), 1, aVar.e(), com.xiaomi.channel.commonutils.network.d.c(this.a) ? 1 : 0);
        b();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0070, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void b() {
        /*
            r11 = this;
            monitor-enter(r11)
            com.xiaomi.push.service.XMPushService r0 = r11.a     // Catch:{ all -> 0x0071 }
            if (r0 != 0) goto L_0x0007
            monitor-exit(r11)
            return
        L_0x0007:
            com.xiaomi.push.service.XMPushService r0 = r11.a     // Catch:{ all -> 0x0071 }
            java.lang.String r0 = com.xiaomi.channel.commonutils.network.d.k(r0)     // Catch:{ all -> 0x0071 }
            com.xiaomi.push.service.XMPushService r1 = r11.a     // Catch:{ all -> 0x0071 }
            boolean r1 = com.xiaomi.channel.commonutils.network.d.c(r1)     // Catch:{ all -> 0x0071 }
            long r2 = android.os.SystemClock.elapsedRealtime()     // Catch:{ all -> 0x0071 }
            long r4 = r11.f     // Catch:{ all -> 0x0071 }
            r6 = 0
            int r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r4 <= 0) goto L_0x002b
            long r4 = r11.g     // Catch:{ all -> 0x0071 }
            long r8 = r11.f     // Catch:{ all -> 0x0071 }
            r10 = 0
            long r8 = r2 - r8
            long r4 = r4 + r8
            r11.g = r4     // Catch:{ all -> 0x0071 }
            r11.f = r6     // Catch:{ all -> 0x0071 }
        L_0x002b:
            long r4 = r11.h     // Catch:{ all -> 0x0071 }
            int r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r4 == 0) goto L_0x003d
            long r4 = r11.i     // Catch:{ all -> 0x0071 }
            long r8 = r11.h     // Catch:{ all -> 0x0071 }
            r10 = 0
            long r8 = r2 - r8
            long r4 = r4 + r8
            r11.i = r4     // Catch:{ all -> 0x0071 }
            r11.h = r6     // Catch:{ all -> 0x0071 }
        L_0x003d:
            if (r1 == 0) goto L_0x006f
            java.lang.String r1 = r11.e     // Catch:{ all -> 0x0071 }
            boolean r1 = android.text.TextUtils.equals(r1, r0)     // Catch:{ all -> 0x0071 }
            if (r1 != 0) goto L_0x004f
            long r4 = r11.g     // Catch:{ all -> 0x0071 }
            r8 = 30000(0x7530, double:1.4822E-319)
            int r1 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
            if (r1 > 0) goto L_0x0058
        L_0x004f:
            long r4 = r11.g     // Catch:{ all -> 0x0071 }
            r8 = 5400000(0x5265c0, double:2.6679545E-317)
            int r1 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
            if (r1 <= 0) goto L_0x005b
        L_0x0058:
            r11.d()     // Catch:{ all -> 0x0071 }
        L_0x005b:
            r11.e = r0     // Catch:{ all -> 0x0071 }
            long r0 = r11.f     // Catch:{ all -> 0x0071 }
            int r0 = (r0 > r6 ? 1 : (r0 == r6 ? 0 : -1))
            if (r0 != 0) goto L_0x0065
            r11.f = r2     // Catch:{ all -> 0x0071 }
        L_0x0065:
            com.xiaomi.push.service.XMPushService r0 = r11.a     // Catch:{ all -> 0x0071 }
            boolean r0 = r0.f()     // Catch:{ all -> 0x0071 }
            if (r0 == 0) goto L_0x006f
            r11.h = r2     // Catch:{ all -> 0x0071 }
        L_0x006f:
            monitor-exit(r11)
            return
        L_0x0071:
            r0 = move-exception
            monitor-exit(r11)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.stats.e.b():void");
    }

    public void b(a aVar) {
        this.c = 0;
        this.d = null;
        this.b = aVar;
        h.a(0, com.xiaomi.push.thrift.a.CONN_SUCCESS.a());
    }
}
