package defpackage;

import android.text.TextUtils;
import anet.channel.strategy.ConnProtocol;
import anet.channel.strategy.IPConnStrategy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/* renamed from: bs reason: default package */
/* compiled from: LocalDnsStrategyTable */
public final class bs {
    final ConcurrentHashMap<String, List<IPConnStrategy>> a = new ConcurrentHashMap<>();
    final HashMap<String, Object> b = new HashMap<>();

    /* access modifiers changed from: 0000 */
    public final List a(final String str) {
        final Object obj;
        if (TextUtils.isEmpty(str) || !ci.c(str) || cb.a().equalsIgnoreCase(str)) {
            return Collections.EMPTY_LIST;
        }
        if (cl.a(1)) {
            cl.a("awcn.LocalDnsStrategyTable", "try resolve ip with local dns", null, "host", str);
        }
        List list = Collections.EMPTY_LIST;
        if (!this.a.containsKey(str)) {
            synchronized (this.b) {
                if (!this.b.containsKey(str)) {
                    obj = new Object();
                    this.b.put(str, obj);
                    ch.a(new Runnable() {
                        /* JADX WARNING: Code restructure failed: missing block: B:36:0x00c0, code lost:
                            r0 = move-exception;
                         */
                        /* JADX WARNING: Code restructure failed: missing block: B:39:0x00c6, code lost:
                            if (defpackage.cl.a(1) != false) goto L_0x00c8;
                         */
                        /* JADX WARNING: Code restructure failed: missing block: B:40:0x00c8, code lost:
                            defpackage.cl.a("awcn.LocalDnsStrategyTable", "resolve ip by local dns failed", null, "host", r10);
                         */
                        /* JADX WARNING: Code restructure failed: missing block: B:41:0x00d9, code lost:
                            r13.c.a.put(r10, java.util.Collections.EMPTY_LIST);
                         */
                        /* JADX WARNING: Code restructure failed: missing block: B:43:0x00e8, code lost:
                            monitor-enter(r13.c.b);
                         */
                        /* JADX WARNING: Code restructure failed: missing block: B:45:?, code lost:
                            r13.c.b.remove(r10);
                         */
                        /* JADX WARNING: Code restructure failed: missing block: B:48:0x00f5, code lost:
                            monitor-enter(r6);
                         */
                        /* JADX WARNING: Code restructure failed: missing block: B:50:?, code lost:
                            r6.notifyAll();
                         */
                        /* JADX WARNING: Code restructure failed: missing block: B:52:0x00fc, code lost:
                            return;
                         */
                        /* JADX WARNING: Code restructure failed: missing block: B:61:0x0107, code lost:
                            monitor-enter(r13.c.b);
                         */
                        /* JADX WARNING: Code restructure failed: missing block: B:63:?, code lost:
                            r13.c.b.remove(r10);
                         */
                        /* JADX WARNING: Code restructure failed: missing block: B:66:0x0114, code lost:
                            monitor-enter(r6);
                         */
                        /* JADX WARNING: Code restructure failed: missing block: B:68:?, code lost:
                            r6.notifyAll();
                         */
                        /* JADX WARNING: Code restructure failed: missing block: B:70:0x011b, code lost:
                            throw r0;
                         */
                        /* JADX WARNING: Failed to process nested try/catch */
                        /* JADX WARNING: Missing exception handler attribute for start block: B:37:0x00c2 */
                        /* JADX WARNING: Removed duplicated region for block: B:12:0x003a A[Catch:{ Exception -> 0x00c2 }] */
                        /* JADX WARNING: Removed duplicated region for block: B:13:0x003f A[Catch:{ Exception -> 0x00c2 }] */
                        /* Code decompiled incorrectly, please refer to instructions dump. */
                        public final void run() {
                            /*
                                r13 = this;
                                r0 = 2
                                r1 = 0
                                r2 = 0
                                r3 = 1
                                java.lang.String r4 = r10     // Catch:{ Exception -> 0x00c2 }
                                java.net.InetAddress r4 = java.net.InetAddress.getByName(r4)     // Catch:{ Exception -> 0x00c2 }
                                java.lang.String r4 = r4.getHostAddress()     // Catch:{ Exception -> 0x00c2 }
                                java.util.LinkedList r12 = new java.util.LinkedList     // Catch:{ Exception -> 0x00c2 }
                                r12.<init>()     // Catch:{ Exception -> 0x00c2 }
                                by r5 = defpackage.by.a.a     // Catch:{ Exception -> 0x00c2 }
                                java.lang.String r6 = r10     // Catch:{ Exception -> 0x00c2 }
                                java.util.Map<java.lang.String, anet.channel.strategy.ConnProtocol> r5 = r5.a     // Catch:{ Exception -> 0x00c2 }
                                java.lang.Object r5 = r5.get(r6)     // Catch:{ Exception -> 0x00c2 }
                                r7 = r5
                                anet.channel.strategy.ConnProtocol r7 = (anet.channel.strategy.ConnProtocol) r7     // Catch:{ Exception -> 0x00c2 }
                                if (r7 == 0) goto L_0x0051
                                java.lang.String r5 = r7.protocol     // Catch:{ Exception -> 0x00c2 }
                                java.lang.String r6 = "https"
                                boolean r5 = r5.equalsIgnoreCase(r6)     // Catch:{ Exception -> 0x00c2 }
                                if (r5 != 0) goto L_0x0037
                                java.lang.String r5 = r7.publicKey     // Catch:{ Exception -> 0x00c2 }
                                boolean r5 = android.text.TextUtils.isEmpty(r5)     // Catch:{ Exception -> 0x00c2 }
                                if (r5 != 0) goto L_0x0035
                                goto L_0x0037
                            L_0x0035:
                                r5 = 0
                                goto L_0x0038
                            L_0x0037:
                                r5 = 1
                            L_0x0038:
                                if (r5 != 0) goto L_0x003f
                                r5 = 80
                                r6 = 80
                                goto L_0x0043
                            L_0x003f:
                                r5 = 443(0x1bb, float:6.21E-43)
                                r6 = 443(0x1bb, float:6.21E-43)
                            L_0x0043:
                                r8 = 0
                                r9 = 0
                                r10 = 1
                                r11 = 45000(0xafc8, float:6.3058E-41)
                                r5 = r4
                                anet.channel.strategy.IPConnStrategy r5 = anet.channel.strategy.IPConnStrategy.a(r5, r6, r7, r8, r9, r10, r11)     // Catch:{ Exception -> 0x00c2 }
                                r12.add(r5)     // Catch:{ Exception -> 0x00c2 }
                            L_0x0051:
                                r6 = 80
                                anet.channel.strategy.ConnProtocol r7 = anet.channel.strategy.ConnProtocol.HTTP     // Catch:{ Exception -> 0x00c2 }
                                r8 = 0
                                r9 = 0
                                r10 = 0
                                r11 = 0
                                r5 = r4
                                anet.channel.strategy.IPConnStrategy r5 = anet.channel.strategy.IPConnStrategy.a(r5, r6, r7, r8, r9, r10, r11)     // Catch:{ Exception -> 0x00c2 }
                                r12.add(r5)     // Catch:{ Exception -> 0x00c2 }
                                r6 = 443(0x1bb, float:6.21E-43)
                                anet.channel.strategy.ConnProtocol r7 = anet.channel.strategy.ConnProtocol.HTTPS     // Catch:{ Exception -> 0x00c2 }
                                r8 = 0
                                r9 = 0
                                r10 = 0
                                r11 = 0
                                r5 = r4
                                anet.channel.strategy.IPConnStrategy r5 = anet.channel.strategy.IPConnStrategy.a(r5, r6, r7, r8, r9, r10, r11)     // Catch:{ Exception -> 0x00c2 }
                                r12.add(r5)     // Catch:{ Exception -> 0x00c2 }
                                bs r5 = defpackage.bs.this     // Catch:{ Exception -> 0x00c2 }
                                java.util.concurrent.ConcurrentHashMap<java.lang.String, java.util.List<anet.channel.strategy.IPConnStrategy>> r5 = r5.a     // Catch:{ Exception -> 0x00c2 }
                                java.lang.String r6 = r10     // Catch:{ Exception -> 0x00c2 }
                                r5.put(r6, r12)     // Catch:{ Exception -> 0x00c2 }
                                boolean r5 = defpackage.cl.a(r3)     // Catch:{ Exception -> 0x00c2 }
                                if (r5 == 0) goto L_0x00a1
                                java.lang.String r5 = "awcn.LocalDnsStrategyTable"
                                java.lang.String r6 = "resolve ip by local dns"
                                r7 = 6
                                java.lang.Object[] r7 = new java.lang.Object[r7]     // Catch:{ Exception -> 0x00c2 }
                                java.lang.String r8 = "host"
                                r7[r2] = r8     // Catch:{ Exception -> 0x00c2 }
                                java.lang.String r8 = r10     // Catch:{ Exception -> 0x00c2 }
                                r7[r3] = r8     // Catch:{ Exception -> 0x00c2 }
                                java.lang.String r8 = "ip"
                                r7[r0] = r8     // Catch:{ Exception -> 0x00c2 }
                                r8 = 3
                                r7[r8] = r4     // Catch:{ Exception -> 0x00c2 }
                                r4 = 4
                                java.lang.String r8 = "list"
                                r7[r4] = r8     // Catch:{ Exception -> 0x00c2 }
                                r4 = 5
                                r7[r4] = r12     // Catch:{ Exception -> 0x00c2 }
                                defpackage.cl.a(r5, r6, r1, r7)     // Catch:{ Exception -> 0x00c2 }
                            L_0x00a1:
                                bs r0 = defpackage.bs.this
                                java.util.HashMap<java.lang.String, java.lang.Object> r0 = r0.b
                                monitor-enter(r0)
                                bs r1 = defpackage.bs.this     // Catch:{ all -> 0x00bd }
                                java.util.HashMap<java.lang.String, java.lang.Object> r1 = r1.b     // Catch:{ all -> 0x00bd }
                                java.lang.String r2 = r10     // Catch:{ all -> 0x00bd }
                                r1.remove(r2)     // Catch:{ all -> 0x00bd }
                                monitor-exit(r0)     // Catch:{ all -> 0x00bd }
                                java.lang.Object r1 = r6
                                monitor-enter(r1)
                                java.lang.Object r0 = r6     // Catch:{ all -> 0x00ba }
                                r0.notifyAll()     // Catch:{ all -> 0x00ba }
                                monitor-exit(r1)     // Catch:{ all -> 0x00ba }
                                return
                            L_0x00ba:
                                r0 = move-exception
                                monitor-exit(r1)     // Catch:{ all -> 0x00ba }
                                throw r0
                            L_0x00bd:
                                r1 = move-exception
                                monitor-exit(r0)     // Catch:{ all -> 0x00bd }
                                throw r1
                            L_0x00c0:
                                r0 = move-exception
                                goto L_0x0103
                            L_0x00c2:
                                boolean r4 = defpackage.cl.a(r3)     // Catch:{ all -> 0x00c0 }
                                if (r4 == 0) goto L_0x00d9
                                java.lang.String r4 = "awcn.LocalDnsStrategyTable"
                                java.lang.String r5 = "resolve ip by local dns failed"
                                java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x00c0 }
                                java.lang.String r6 = "host"
                                r0[r2] = r6     // Catch:{ all -> 0x00c0 }
                                java.lang.String r2 = r10     // Catch:{ all -> 0x00c0 }
                                r0[r3] = r2     // Catch:{ all -> 0x00c0 }
                                defpackage.cl.a(r4, r5, r1, r0)     // Catch:{ all -> 0x00c0 }
                            L_0x00d9:
                                bs r0 = defpackage.bs.this     // Catch:{ all -> 0x00c0 }
                                java.util.concurrent.ConcurrentHashMap<java.lang.String, java.util.List<anet.channel.strategy.IPConnStrategy>> r0 = r0.a     // Catch:{ all -> 0x00c0 }
                                java.lang.String r1 = r10     // Catch:{ all -> 0x00c0 }
                                java.util.List r2 = java.util.Collections.EMPTY_LIST     // Catch:{ all -> 0x00c0 }
                                r0.put(r1, r2)     // Catch:{ all -> 0x00c0 }
                                bs r0 = defpackage.bs.this
                                java.util.HashMap<java.lang.String, java.lang.Object> r0 = r0.b
                                monitor-enter(r0)
                                bs r1 = defpackage.bs.this     // Catch:{ all -> 0x0100 }
                                java.util.HashMap<java.lang.String, java.lang.Object> r1 = r1.b     // Catch:{ all -> 0x0100 }
                                java.lang.String r2 = r10     // Catch:{ all -> 0x0100 }
                                r1.remove(r2)     // Catch:{ all -> 0x0100 }
                                monitor-exit(r0)     // Catch:{ all -> 0x0100 }
                                java.lang.Object r1 = r6
                                monitor-enter(r1)
                                java.lang.Object r0 = r6     // Catch:{ all -> 0x00fd }
                                r0.notifyAll()     // Catch:{ all -> 0x00fd }
                                monitor-exit(r1)     // Catch:{ all -> 0x00fd }
                                return
                            L_0x00fd:
                                r0 = move-exception
                                monitor-exit(r1)     // Catch:{ all -> 0x00fd }
                                throw r0
                            L_0x0100:
                                r1 = move-exception
                                monitor-exit(r0)     // Catch:{ all -> 0x0100 }
                                throw r1
                            L_0x0103:
                                bs r1 = defpackage.bs.this
                                java.util.HashMap<java.lang.String, java.lang.Object> r1 = r1.b
                                monitor-enter(r1)
                                bs r2 = defpackage.bs.this     // Catch:{ all -> 0x011f }
                                java.util.HashMap<java.lang.String, java.lang.Object> r2 = r2.b     // Catch:{ all -> 0x011f }
                                java.lang.String r3 = r10     // Catch:{ all -> 0x011f }
                                r2.remove(r3)     // Catch:{ all -> 0x011f }
                                monitor-exit(r1)     // Catch:{ all -> 0x011f }
                                java.lang.Object r2 = r6
                                monitor-enter(r2)
                                java.lang.Object r1 = r6     // Catch:{ all -> 0x011c }
                                r1.notifyAll()     // Catch:{ all -> 0x011c }
                                monitor-exit(r2)     // Catch:{ all -> 0x011c }
                                throw r0
                            L_0x011c:
                                r0 = move-exception
                                monitor-exit(r2)     // Catch:{ all -> 0x011c }
                                throw r0
                            L_0x011f:
                                r0 = move-exception
                                monitor-exit(r1)     // Catch:{ all -> 0x011f }
                                throw r0
                            */
                            throw new UnsupportedOperationException("Method not decompiled: defpackage.bs.AnonymousClass1.run():void");
                        }
                    });
                } else {
                    obj = this.b.get(str);
                }
            }
            if (obj != null) {
                try {
                    synchronized (obj) {
                        obj.wait(500);
                    }
                } catch (InterruptedException unused) {
                }
            }
        }
        List list2 = this.a.get(str);
        if (!(list2 == null || list2 == Collections.EMPTY_LIST)) {
            list = new ArrayList(list2);
        }
        cl.b("awcn.LocalDnsStrategyTable", "get local strategy", null, "strategyList", list2);
        return list;
    }

    /* access modifiers changed from: 0000 */
    public final void a(String str, ConnProtocol connProtocol) {
        List<IPConnStrategy> list = this.a.get(str);
        if (list != null && !list.isEmpty()) {
            for (IPConnStrategy iPConnStrategy : list) {
                if (iPConnStrategy.c.equals(connProtocol)) {
                    return;
                }
            }
            list.add(IPConnStrategy.a(((IPConnStrategy) list.get(0)).a, !(connProtocol.protocol.equalsIgnoreCase("https") || !TextUtils.isEmpty(connProtocol.publicKey)) ? 80 : 443, connProtocol, 0, 0, 1, 45000));
            cl.b("awcn.LocalDnsStrategyTable", "setProtocolForHost", null, "strategyList", list);
        }
    }
}
