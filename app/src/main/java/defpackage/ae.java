package defpackage;

import com.alipay.mobile.beehive.eventbus.Subscribe;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/* renamed from: ae reason: default package */
/* compiled from: NetworkDetector */
public final class ae {
    /* access modifiers changed from: private */
    public static final TreeMap<String, c> a = new TreeMap<>();
    private static final AtomicInteger b = new AtomicInteger(1);
    /* access modifiers changed from: private */
    public static final ReentrantLock c;
    /* access modifiers changed from: private */
    public static final Condition d;
    /* access modifiers changed from: private */
    public static final Condition e = c.newCondition();
    /* access modifiers changed from: private */
    public static volatile Thread f;
    /* access modifiers changed from: private */
    public static final Runnable g = new Runnable() {
        public final void run() {
            Entry entry;
            cl.d("awcn.NetworkDetector", "network detect thread start", null, new Object[0]);
            while (true) {
                try {
                    ae.c.lock();
                    if (!m.h()) {
                        ae.d.await();
                    }
                    if (ae.a.isEmpty()) {
                        ae.e.await();
                    }
                } catch (Exception unused) {
                } catch (Throwable th) {
                    ae.c.unlock();
                    throw th;
                }
                ae.c.unlock();
                while (m.h()) {
                    synchronized (ae.a) {
                        if (!j.d()) {
                            ae.a.clear();
                            entry = null;
                        } else {
                            entry = ae.a.pollFirstEntry();
                        }
                    }
                    if (entry == null) {
                        break;
                    }
                    try {
                        ae.a((c) entry.getValue());
                    } catch (Exception unused2) {
                        cl.e("awcn.NetworkDetector", "start hr task failed", null, new Object[0]);
                    }
                }
            }
        }
    };

    static {
        ReentrantLock reentrantLock = new ReentrantLock();
        c = reentrantLock;
        d = reentrantLock.newCondition();
    }

    public static void a() {
        cl.b("awcn.NetworkDetector", "registerListener", null, new Object[0]);
        bu.a().a((br) new br() {
            public final void a(d dVar) {
                cl.b("awcn.NetworkDetector", "onStrategyUpdated", null, new Object[0]);
                if (j.d() && dVar.c != null && dVar.c.length != 0) {
                    if (ae.f == null) {
                        ae.f = new Thread(ae.g);
                        ae.f.setName("AWCN HR");
                        ae.f.start();
                        cl.b("awcn.NetworkDetector", "start horse race thread", null, new Object[0]);
                    }
                    synchronized (ae.a) {
                        for (c cVar : dVar.c) {
                            ae.a.put(cVar.a, cVar);
                        }
                    }
                    ae.c.lock();
                    try {
                        ae.e.signal();
                    } finally {
                        ae.c.unlock();
                    }
                }
            }
        });
        cm.a((a) new a() {
            public final void a() {
            }

            public final void b() {
                cl.b("awcn.NetworkDetector", Subscribe.THREAD_BACKGROUND, null, new Object[0]);
                ae.c.lock();
                try {
                    ae.d.signal();
                } finally {
                    ae.c.unlock();
                }
            }
        });
    }

    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:48:0x01a1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ void a(defpackage.bw.c r19) {
        /*
            r1 = r19
            bw$e[] r2 = r1.b
            if (r2 == 0) goto L_0x0268
            bw$e[] r2 = r1.b
            int r2 = r2.length
            if (r2 != 0) goto L_0x000d
            goto L_0x0268
        L_0x000d:
            java.lang.String r2 = r1.a
            r3 = 0
            r4 = 0
        L_0x0011:
            bw$e[] r5 = r1.b
            int r5 = r5.length
            if (r4 >= r5) goto L_0x0267
            bw$e[] r5 = r1.b
            r5 = r5[r4]
            bw$a r6 = r5.b
            java.lang.String r6 = r6.b
            java.lang.String r7 = "http"
            boolean r7 = r6.equalsIgnoreCase(r7)
            r8 = 0
            r9 = 2
            r10 = 1
            if (r7 != 0) goto L_0x01ac
            java.lang.String r7 = "https"
            boolean r7 = r6.equalsIgnoreCase(r7)
            if (r7 == 0) goto L_0x0033
            goto L_0x01ac
        L_0x0033:
            java.lang.String r7 = "http2"
            boolean r7 = r6.equalsIgnoreCase(r7)
            r11 = 3
            r12 = 4
            if (r7 != 0) goto L_0x00d9
            java.lang.String r7 = "spdy"
            boolean r7 = r6.equalsIgnoreCase(r7)
            if (r7 != 0) goto L_0x00d9
            java.lang.String r7 = "quic"
            boolean r7 = r6.equalsIgnoreCase(r7)
            if (r7 == 0) goto L_0x004f
            goto L_0x00d9
        L_0x004f:
            java.lang.String r7 = "tcp"
            boolean r6 = r6.equalsIgnoreCase(r7)
            if (r6 == 0) goto L_0x01a9
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r7 = "HR"
            r6.<init>(r7)
            java.util.concurrent.atomic.AtomicInteger r7 = b
            int r7 = r7.getAndIncrement()
            r6.append(r7)
            java.lang.String r6 = r6.toString()
            java.lang.String r7 = "awcn.NetworkDetector"
            java.lang.String r8 = "startTcpTask"
            java.lang.Object[] r12 = new java.lang.Object[r12]
            java.lang.String r14 = "ip"
            r12[r3] = r14
            java.lang.String r14 = r5.a
            r12[r10] = r14
            java.lang.String r14 = "port"
            r12[r9] = r14
            bw$a r9 = r5.b
            int r9 = r9.a
            java.lang.Integer r9 = java.lang.Integer.valueOf(r9)
            r12[r11] = r9
            defpackage.cl.b(r7, r8, r6, r12)
            anet.channel.statist.HorseRaceStat r7 = new anet.channel.statist.HorseRaceStat
            r7.<init>(r2, r5)
            long r8 = java.lang.System.currentTimeMillis()
            java.net.Socket r11 = new java.net.Socket     // Catch:{ IOException -> 0x00c5 }
            java.lang.String r12 = r5.a     // Catch:{ IOException -> 0x00c5 }
            bw$a r14 = r5.b     // Catch:{ IOException -> 0x00c5 }
            int r14 = r14.a     // Catch:{ IOException -> 0x00c5 }
            r11.<init>(r12, r14)     // Catch:{ IOException -> 0x00c5 }
            bw$a r12 = r5.b     // Catch:{ IOException -> 0x00c5 }
            int r12 = r12.c     // Catch:{ IOException -> 0x00c5 }
            if (r12 != 0) goto L_0x00a7
            r13 = 10000(0x2710, float:1.4013E-41)
            goto L_0x00ab
        L_0x00a7:
            bw$a r5 = r5.b     // Catch:{ IOException -> 0x00c5 }
            int r13 = r5.c     // Catch:{ IOException -> 0x00c5 }
        L_0x00ab:
            r11.setSoTimeout(r13)     // Catch:{ IOException -> 0x00c5 }
            java.lang.String r5 = "awcn.NetworkDetector"
            java.lang.String r12 = "socket connect success"
            java.lang.Object[] r13 = new java.lang.Object[r3]     // Catch:{ IOException -> 0x00c5 }
            defpackage.cl.b(r5, r12, r6, r13)     // Catch:{ IOException -> 0x00c5 }
            r7.connRet = r10     // Catch:{ IOException -> 0x00c5 }
            long r5 = java.lang.System.currentTimeMillis()     // Catch:{ IOException -> 0x00c5 }
            r10 = 0
            long r5 = r5 - r8
            r7.connTime = r5     // Catch:{ IOException -> 0x00c5 }
            r11.close()     // Catch:{ IOException -> 0x00c5 }
            goto L_0x00d0
        L_0x00c5:
            long r5 = java.lang.System.currentTimeMillis()
            long r5 = r5 - r8
            r7.connTime = r5
            r5 = -404(0xfffffffffffffe6c, float:NaN)
            r7.connErrorCode = r5
        L_0x00d0:
            z r5 = defpackage.x.a()
            r5.a(r7)
            goto L_0x01a9
        L_0x00d9:
            bw$a r6 = r5.b
            anet.channel.strategy.ConnProtocol r6 = anet.channel.strategy.ConnProtocol.valueOf(r6)
            anet.channel.entity.ConnType r7 = anet.channel.entity.ConnType.a(r6)
            if (r7 == 0) goto L_0x01a9
            java.lang.String r14 = "awcn.NetworkDetector"
            java.lang.String r15 = "startLongLinkTask"
            r13 = 8
            java.lang.Object[] r13 = new java.lang.Object[r13]
            java.lang.String r16 = "host"
            r13[r3] = r16
            r13[r10] = r2
            java.lang.String r10 = "ip"
            r13[r9] = r10
            java.lang.String r9 = r5.a
            r13[r11] = r9
            java.lang.String r9 = "port"
            r13[r12] = r9
            r9 = 5
            bw$a r10 = r5.b
            int r10 = r10.a
            java.lang.Integer r10 = java.lang.Integer.valueOf(r10)
            r13[r9] = r10
            r9 = 6
            java.lang.String r10 = "protocol"
            r13[r9] = r10
            r9 = 7
            r13[r9] = r6
            defpackage.cl.b(r14, r15, r8, r13)
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            java.lang.String r9 = "HR"
            r8.<init>(r9)
            java.util.concurrent.atomic.AtomicInteger r9 = b
            int r9 = r9.getAndIncrement()
            r8.append(r9)
            java.lang.String r10 = r8.toString()
            bi r13 = new bi
            android.content.Context r8 = defpackage.m.a()
            af r9 = new af
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            boolean r7 = r7.c()
            if (r7 == 0) goto L_0x013f
            java.lang.String r7 = "https://"
            goto L_0x0141
        L_0x013f:
            java.lang.String r7 = "http://"
        L_0x0141:
            r11.append(r7)
            r11.append(r2)
            java.lang.String r7 = r11.toString()
            ae$5 r11 = new ae$5
            r11.<init>(r5, r6)
            r9.<init>(r7, r10, r11)
            r13.<init>(r8, r9)
            anet.channel.statist.HorseRaceStat r14 = new anet.channel.statist.HorseRaceStat
            r14.<init>(r2, r5)
            long r16 = java.lang.System.currentTimeMillis()
            r15 = 257(0x101, float:3.6E-43)
            ae$4 r12 = new ae$4
            r6 = r12
            r7 = r14
            r8 = r16
            r11 = r5
            r3 = r12
            r12 = r13
            r6.<init>(r7, r8, r10, r11, r12)
            r13.a(r15, r3)
            r13.a()
            monitor-enter(r14)
            bw$a r3 = r5.b     // Catch:{ InterruptedException -> 0x01a1 }
            int r3 = r3.c     // Catch:{ InterruptedException -> 0x01a1 }
            if (r3 != 0) goto L_0x017d
            r3 = 10000(0x2710, float:1.4013E-41)
            goto L_0x0181
        L_0x017d:
            bw$a r3 = r5.b     // Catch:{ InterruptedException -> 0x01a1 }
            int r3 = r3.c     // Catch:{ InterruptedException -> 0x01a1 }
        L_0x0181:
            long r5 = (long) r3     // Catch:{ InterruptedException -> 0x01a1 }
            r14.wait(r5)     // Catch:{ InterruptedException -> 0x01a1 }
            long r5 = r14.connTime     // Catch:{ InterruptedException -> 0x01a1 }
            r7 = 0
            int r3 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r3 != 0) goto L_0x0196
            long r5 = java.lang.System.currentTimeMillis()     // Catch:{ InterruptedException -> 0x01a1 }
            r3 = 0
            long r5 = r5 - r16
            r14.connTime = r5     // Catch:{ InterruptedException -> 0x01a1 }
        L_0x0196:
            z r3 = defpackage.x.a()     // Catch:{ InterruptedException -> 0x01a1 }
            r3.a(r14)     // Catch:{ InterruptedException -> 0x01a1 }
            goto L_0x01a1
        L_0x019e:
            r0 = move-exception
            r1 = r0
            goto L_0x01a7
        L_0x01a1:
            monitor-exit(r14)     // Catch:{ all -> 0x019e }
            r3 = 0
            r13.a(r3)
            goto L_0x01a9
        L_0x01a7:
            monitor-exit(r14)     // Catch:{ all -> 0x019e }
            throw r1
        L_0x01a9:
            r6 = 0
            goto L_0x0262
        L_0x01ac:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            bw$a r6 = r5.b
            java.lang.String r6 = r6.b
            r3.<init>(r6)
            java.lang.String r6 = "://"
            r3.append(r6)
            r3.append(r2)
            java.lang.String r6 = r5.c
            r3.append(r6)
            java.lang.String r3 = r3.toString()
            cs r3 = defpackage.cs.a(r3)
            if (r3 == 0) goto L_0x01a9
            java.lang.String r6 = "awcn.NetworkDetector"
            java.lang.String r7 = "startShortLinkTask"
            java.lang.Object[] r9 = new java.lang.Object[r9]
            java.lang.String r11 = "url"
            r12 = 0
            r9[r12] = r11
            r9[r10] = r3
            defpackage.cl.b(r6, r7, r8, r9)
            ay$a r6 = new ay$a
            r6.<init>()
            ay$a r3 = r6.a(r3)
            java.lang.String r6 = "Connection"
            java.lang.String r7 = "close"
            ay$a r3 = r3.a(r6, r7)
            bw$a r6 = r5.b
            int r6 = r6.c
            ay$a r3 = r3.b(r6)
            bw$a r6 = r5.b
            int r6 = r6.d
            ay$a r3 = r3.a(r6)
            r6 = 0
            r3.h = r6
            da r7 = new da
            r7.<init>(r2)
            r3.k = r7
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            java.lang.String r9 = "HR"
            r7.<init>(r9)
            java.util.concurrent.atomic.AtomicInteger r9 = b
            int r9 = r9.getAndIncrement()
            r7.append(r9)
            java.lang.String r7 = r7.toString()
            r3.m = r7
            ay r3 = r3.a()
            java.lang.String r7 = r5.a
            bw$a r9 = r5.b
            int r9 = r9.a
            r3.a(r7, r9)
            long r11 = java.lang.System.currentTimeMillis()
            bg$a r3 = defpackage.bg.a(r3, r8)
            long r7 = java.lang.System.currentTimeMillis()
            long r7 = r7 - r11
            anet.channel.statist.HorseRaceStat r9 = new anet.channel.statist.HorseRaceStat
            r9.<init>(r2, r5)
            r9.connTime = r7
            int r5 = r3.a
            if (r5 > 0) goto L_0x0247
            int r3 = r3.a
            r9.connErrorCode = r3
            goto L_0x025b
        L_0x0247:
            r9.connRet = r10
            int r5 = r3.a
            r7 = 200(0xc8, float:2.8E-43)
            if (r5 != r7) goto L_0x0250
            goto L_0x0251
        L_0x0250:
            r10 = 0
        L_0x0251:
            r9.reqRet = r10
            int r3 = r3.a
            r9.reqErrorCode = r3
            long r7 = r9.connTime
            r9.reqTime = r7
        L_0x025b:
            z r3 = defpackage.x.a()
            r3.a(r9)
        L_0x0262:
            int r4 = r4 + 1
            r3 = 0
            goto L_0x0011
        L_0x0267:
            return
        L_0x0268:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ae.a(bw$c):void");
    }
}
