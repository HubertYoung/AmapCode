package defpackage;

import android.content.Context;
import android.content.Intent;
import anet.channel.entity.ConnType;
import anet.channel.statist.SessionConnStat;
import anet.channel.statist.StatObject;
import anet.channel.status.NetworkStatusHelper;
import com.taobao.accs.common.Constants;
import com.taobao.accs.utl.AdapterUtilityImpl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map.Entry;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

/* renamed from: v reason: default package */
/* compiled from: SessionRequest */
final class v {
    String a;
    r b;
    u c;
    volatile boolean d = false;
    volatile p e;
    volatile boolean f = false;
    HashMap<s, d> g = new HashMap<>();
    SessionConnStat h = null;
    private String i;
    private t j;
    private volatile Future k;
    private Object l = new Object();

    /* renamed from: v$a */
    /* compiled from: SessionRequest */
    class a implements c {
        Context a;
        boolean b = false;
        private List<af> d;
        private af e;

        a(Context context, List<af> list, af afVar) {
            this.a = context;
            this.d = list;
            this.e = afVar;
        }

        public final void a(p pVar, int i, int i2) {
            if (cl.a(1)) {
                cl.a("awcn.SessionRequest", "Connect failed", this.e.c, "session", pVar, "host", v.this.a, "isHandleFinish", Boolean.valueOf(this.b));
            }
            if (v.this.f) {
                v.this.f = false;
            } else if (!this.b) {
                this.b = true;
                v.this.c.a(v.this, pVar);
                if (!pVar.u || !NetworkStatusHelper.h() || this.d.isEmpty()) {
                    v.this.c();
                    v.a(v.this, pVar, i, i2);
                    synchronized (v.this.g) {
                        for (Entry next : v.this.g.entrySet()) {
                            d dVar = (d) next.getValue();
                            if (dVar.b.compareAndSet(false, true)) {
                                ck.b(dVar);
                                ((s) next.getKey()).a();
                            }
                        }
                        v.this.g.clear();
                    }
                    return;
                }
                if (cl.a(1)) {
                    cl.a("awcn.SessionRequest", "use next connInfo to create session", this.e.c, "host", v.this.a);
                }
                if (this.e.d == this.e.e && (i2 == -2003 || i2 == -2410)) {
                    ListIterator<af> listIterator = this.d.listIterator();
                    while (listIterator.hasNext()) {
                        if (pVar.f().equals(listIterator.next().a.a())) {
                            listIterator.remove();
                        }
                    }
                }
                if (ci.b(pVar.f())) {
                    ListIterator<af> listIterator2 = this.d.listIterator();
                    while (listIterator2.hasNext()) {
                        if (ci.b(listIterator2.next().a.a())) {
                            listIterator2.remove();
                        }
                    }
                }
                if (this.d.isEmpty()) {
                    v.this.c();
                    v.a(v.this, pVar, i, i2);
                    return;
                }
                af remove = this.d.remove(0);
                v.this.a(this.a, remove, (c) new a(this.a, this.d, remove), remove.c);
            }
        }

        public final void a(p pVar) {
            u uVar;
            WriteLock writeLock;
            cl.a("awcn.SessionRequest", "Connect Success", this.e.c, "session", pVar, "host", v.this.a);
            try {
                if (v.this.f) {
                    v.this.f = false;
                    pVar.a(false);
                    v.this.c();
                    return;
                }
                uVar = v.this.c;
                v vVar = v.this;
                if (!(vVar == null || vVar.a == null)) {
                    if (pVar != null) {
                        uVar.b.lock();
                        List list = uVar.a.get(vVar);
                        if (list == null) {
                            list = new ArrayList();
                            uVar.a.put(vVar, list);
                        }
                        if (list.indexOf(pVar) != -1) {
                            writeLock = uVar.b;
                        } else {
                            list.add(pVar);
                            Collections.sort(list);
                            writeLock = uVar.b;
                        }
                        writeLock.unlock();
                    }
                }
                v vVar2 = v.this;
                bj bjVar = new bj();
                bjVar.e = "networkPrefer";
                bjVar.f = "policy";
                bjVar.b = vVar2.a;
                bjVar.a = true;
                x.a().a(bjVar);
                vVar2.h.syncValueFromSession(pVar);
                vVar2.h.ret = 1;
                vVar2.h.totalTime = System.currentTimeMillis() - vVar2.h.start;
                x.a().a((StatObject) vVar2.h);
                synchronized (v.this.g) {
                    for (Entry next : v.this.g.entrySet()) {
                        d dVar = (d) next.getValue();
                        if (dVar.b.compareAndSet(false, true)) {
                            ck.b(dVar);
                            ((s) next.getKey()).a(pVar);
                        }
                    }
                    v.this.g.clear();
                }
                v.this.c();
            } catch (Exception unused) {
                try {
                    cl.e("awcn.SessionRequest", "[onSuccess]:", this.e.c, new Object[0]);
                } finally {
                    v.this.c();
                }
            } catch (Throwable th) {
                uVar.b.unlock();
                throw th;
            }
        }

        public final void b(final p pVar) {
            boolean h = m.h();
            cl.a("awcn.SessionRequest", "Connect Disconnect", this.e.c, "session", pVar, "host", v.this.a, "appIsBg", Boolean.valueOf(h), "isHandleFinish", Boolean.valueOf(this.b));
            v.this.c.a(v.this, pVar);
            if (!this.b) {
                this.b = true;
                if (pVar.t) {
                    if (h) {
                        cl.d("awcn.SessionRequest", "[onDisConnect]app background, don't Recreate", this.e.c, "session", pVar);
                    } else if (!NetworkStatusHelper.h()) {
                        cl.d("awcn.SessionRequest", "[onDisConnect]no network, don't Recreate", this.e.c, "session", pVar);
                    } else {
                        try {
                            cl.a("awcn.SessionRequest", "session disconnected, try to recreate session", this.e.c, new Object[0]);
                            ck.a(new Runnable() {
                                public final void run() {
                                    try {
                                        v.this.a(a.this.a, pVar.h().d(), cy.a(v.this.b.c), null, 0);
                                    } catch (Exception unused) {
                                    }
                                }
                            }, (long) (Math.random() * 10.0d * 1000.0d), TimeUnit.MILLISECONDS);
                        } catch (Exception unused) {
                        }
                    }
                }
            }
        }
    }

    /* renamed from: v$b */
    /* compiled from: SessionRequest */
    class b implements Runnable {
        String a = null;

        b(String str) {
            this.a = str;
        }

        public final void run() {
            if (v.this.d) {
                cl.d("awcn.SessionRequest", "Connecting timeout!!! reset status!", this.a, new Object[0]);
                v.this.h.ret = 2;
                v.this.h.totalTime = System.currentTimeMillis() - v.this.h.start;
                if (v.this.e != null) {
                    v.this.e.u = false;
                    v.this.e.b();
                    v.this.h.syncValueFromSession(v.this.e);
                }
                x.a().a((StatObject) v.this.h);
                v.this.a(false);
            }
        }
    }

    /* renamed from: v$c */
    /* compiled from: SessionRequest */
    interface c {
        void a(p pVar);

        void a(p pVar, int i, int i2);

        void b(p pVar);
    }

    /* renamed from: v$d */
    /* compiled from: SessionRequest */
    public class d implements Runnable {
        s a = null;
        AtomicBoolean b = new AtomicBoolean(false);

        protected d(s sVar) {
            this.a = sVar;
        }

        public final void run() {
            if (this.b.compareAndSet(false, true)) {
                cl.d("awcn.SessionRequest", "get session timeout", null, new Object[0]);
                synchronized (v.this.g) {
                    v.this.g.remove(this.a);
                }
                this.a.a();
            }
        }
    }

    v(String str, r rVar) {
        this.a = str;
        this.i = this.a.substring(this.a.indexOf("://") + 3);
        this.b = rVar;
        this.j = rVar.g.a(this.i);
        this.c = rVar.e;
    }

    /* access modifiers changed from: 0000 */
    public final void a(boolean z) {
        this.d = z;
        if (!z) {
            if (this.k != null) {
                this.k.cancel(true);
                this.k = null;
            }
            this.e = null;
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x007d, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x012b, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0019, code lost:
        return;
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:66:0x012c */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void a(android.content.Context r10, int r11, java.lang.String r12, defpackage.s r13, long r14) {
        /*
            r9 = this;
            monitor-enter(r9)
            u r0 = r9.c     // Catch:{ all -> 0x0131 }
            p r0 = r0.a(r9, r11)     // Catch:{ all -> 0x0131 }
            r1 = 0
            if (r0 == 0) goto L_0x001a
            java.lang.String r10 = "awcn.SessionRequest"
            java.lang.String r11 = "Available Session exist!!!"
            java.lang.Object[] r14 = new java.lang.Object[r1]     // Catch:{ all -> 0x0131 }
            defpackage.cl.a(r10, r11, r12, r14)     // Catch:{ all -> 0x0131 }
            if (r13 == 0) goto L_0x0018
            r13.a(r0)     // Catch:{ all -> 0x0131 }
        L_0x0018:
            monitor-exit(r9)
            return
        L_0x001a:
            boolean r0 = android.text.TextUtils.isEmpty(r12)     // Catch:{ all -> 0x0131 }
            if (r0 == 0) goto L_0x0025
            r12 = 0
            java.lang.String r12 = defpackage.cy.a(r12)     // Catch:{ all -> 0x0131 }
        L_0x0025:
            java.lang.String r0 = "awcn.SessionRequest"
            java.lang.String r2 = "SessionRequest start"
            r3 = 4
            java.lang.Object[] r4 = new java.lang.Object[r3]     // Catch:{ all -> 0x0131 }
            java.lang.String r5 = "host"
            r4[r1] = r5     // Catch:{ all -> 0x0131 }
            java.lang.String r5 = r9.a     // Catch:{ all -> 0x0131 }
            r6 = 1
            r4[r6] = r5     // Catch:{ all -> 0x0131 }
            java.lang.String r5 = "type"
            r7 = 2
            r4[r7] = r5     // Catch:{ all -> 0x0131 }
            java.lang.Integer r5 = java.lang.Integer.valueOf(r11)     // Catch:{ all -> 0x0131 }
            r8 = 3
            r4[r8] = r5     // Catch:{ all -> 0x0131 }
            defpackage.cl.a(r0, r2, r12, r4)     // Catch:{ all -> 0x0131 }
            boolean r0 = r9.d     // Catch:{ all -> 0x0131 }
            if (r0 == 0) goto L_0x007e
            java.lang.String r10 = "awcn.SessionRequest"
            java.lang.String r0 = "session connecting"
            java.lang.Object[] r2 = new java.lang.Object[r7]     // Catch:{ all -> 0x0131 }
            java.lang.String r3 = "host"
            r2[r1] = r3     // Catch:{ all -> 0x0131 }
            java.lang.String r1 = r9.a     // Catch:{ all -> 0x0131 }
            r2[r6] = r1     // Catch:{ all -> 0x0131 }
            defpackage.cl.a(r10, r0, r12, r2)     // Catch:{ all -> 0x0131 }
            if (r13 == 0) goto L_0x007c
            int r10 = r9.b()     // Catch:{ all -> 0x0131 }
            if (r10 != r11) goto L_0x0079
            v$d r10 = new v$d     // Catch:{ all -> 0x0131 }
            r10.<init>(r13)     // Catch:{ all -> 0x0131 }
            java.util.HashMap<s, v$d> r11 = r9.g     // Catch:{ all -> 0x0131 }
            monitor-enter(r11)     // Catch:{ all -> 0x0131 }
            java.util.HashMap<s, v$d> r12 = r9.g     // Catch:{ all -> 0x0076 }
            r12.put(r13, r10)     // Catch:{ all -> 0x0076 }
            monitor-exit(r11)     // Catch:{ all -> 0x0076 }
            java.util.concurrent.TimeUnit r11 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch:{ all -> 0x0131 }
            defpackage.ck.a(r10, r14, r11)     // Catch:{ all -> 0x0131 }
            monitor-exit(r9)
            return
        L_0x0076:
            r10 = move-exception
            monitor-exit(r11)     // Catch:{ all -> 0x0076 }
            throw r10     // Catch:{ all -> 0x0131 }
        L_0x0079:
            r13.a()     // Catch:{ all -> 0x0131 }
        L_0x007c:
            monitor-exit(r9)
            return
        L_0x007e:
            r9.a(r6)     // Catch:{ all -> 0x0131 }
            v$b r0 = new v$b     // Catch:{ all -> 0x0131 }
            r0.<init>(r12)     // Catch:{ all -> 0x0131 }
            r4 = 45
            java.util.concurrent.TimeUnit r2 = java.util.concurrent.TimeUnit.SECONDS     // Catch:{ all -> 0x0131 }
            java.util.concurrent.Future r0 = defpackage.ck.a(r0, r4, r2)     // Catch:{ all -> 0x0131 }
            r9.k = r0     // Catch:{ all -> 0x0131 }
            anet.channel.statist.SessionConnStat r0 = new anet.channel.statist.SessionConnStat     // Catch:{ all -> 0x0131 }
            r0.<init>()     // Catch:{ all -> 0x0131 }
            r9.h = r0     // Catch:{ all -> 0x0131 }
            anet.channel.statist.SessionConnStat r0 = r9.h     // Catch:{ all -> 0x0131 }
            long r4 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x0131 }
            r0.start = r4     // Catch:{ all -> 0x0131 }
            boolean r0 = anet.channel.status.NetworkStatusHelper.h()     // Catch:{ all -> 0x0131 }
            if (r0 != 0) goto L_0x00cd
            boolean r10 = defpackage.cl.a(r6)     // Catch:{ all -> 0x0131 }
            if (r10 == 0) goto L_0x00c2
            java.lang.String r10 = "awcn.SessionRequest"
            java.lang.String r11 = "network is not available, can't create session"
            java.lang.Object[] r13 = new java.lang.Object[r7]     // Catch:{ all -> 0x0131 }
            java.lang.String r14 = "isConnected"
            r13[r1] = r14     // Catch:{ all -> 0x0131 }
            boolean r14 = anet.channel.status.NetworkStatusHelper.h()     // Catch:{ all -> 0x0131 }
            java.lang.Boolean r14 = java.lang.Boolean.valueOf(r14)     // Catch:{ all -> 0x0131 }
            r13[r6] = r14     // Catch:{ all -> 0x0131 }
            defpackage.cl.a(r10, r11, r12, r13)     // Catch:{ all -> 0x0131 }
        L_0x00c2:
            r9.c()     // Catch:{ all -> 0x0131 }
            java.lang.RuntimeException r10 = new java.lang.RuntimeException     // Catch:{ all -> 0x0131 }
            java.lang.String r11 = "no network"
            r10.<init>(r11)     // Catch:{ all -> 0x0131 }
            throw r10     // Catch:{ all -> 0x0131 }
        L_0x00cd:
            java.util.List r0 = r9.a(r11, r12)     // Catch:{ all -> 0x0131 }
            boolean r2 = r0.isEmpty()     // Catch:{ all -> 0x0131 }
            if (r2 == 0) goto L_0x00fd
            java.lang.String r10 = "awcn.SessionRequest"
            java.lang.String r13 = "no avalible strategy, can't create session"
            java.lang.Object[] r14 = new java.lang.Object[r3]     // Catch:{ all -> 0x0131 }
            java.lang.String r15 = "host"
            r14[r1] = r15     // Catch:{ all -> 0x0131 }
            java.lang.String r15 = r9.a     // Catch:{ all -> 0x0131 }
            r14[r6] = r15     // Catch:{ all -> 0x0131 }
            java.lang.String r15 = "type"
            r14[r7] = r15     // Catch:{ all -> 0x0131 }
            java.lang.Integer r11 = java.lang.Integer.valueOf(r11)     // Catch:{ all -> 0x0131 }
            r14[r8] = r11     // Catch:{ all -> 0x0131 }
            defpackage.cl.b(r10, r13, r12, r14)     // Catch:{ all -> 0x0131 }
            r9.c()     // Catch:{ all -> 0x0131 }
            anet.channel.NoAvailStrategyException r10 = new anet.channel.NoAvailStrategyException     // Catch:{ all -> 0x0131 }
            java.lang.String r11 = "no avalible strategy"
            r10.<init>(r11)     // Catch:{ all -> 0x0131 }
            throw r10     // Catch:{ all -> 0x0131 }
        L_0x00fd:
            java.util.List r11 = r9.a(r0, r12)     // Catch:{ all -> 0x0131 }
            java.lang.Object r12 = r11.remove(r1)     // Catch:{ Throwable -> 0x012c }
            af r12 = (defpackage.af) r12     // Catch:{ Throwable -> 0x012c }
            v$a r0 = new v$a     // Catch:{ Throwable -> 0x012c }
            r0.<init>(r10, r11, r12)     // Catch:{ Throwable -> 0x012c }
            java.lang.String r11 = r12.c     // Catch:{ Throwable -> 0x012c }
            r9.a(r10, r12, r0, r11)     // Catch:{ Throwable -> 0x012c }
            if (r13 == 0) goto L_0x012a
            v$d r10 = new v$d     // Catch:{ Throwable -> 0x012c }
            r10.<init>(r13)     // Catch:{ Throwable -> 0x012c }
            java.util.HashMap<s, v$d> r11 = r9.g     // Catch:{ Throwable -> 0x012c }
            monitor-enter(r11)     // Catch:{ Throwable -> 0x012c }
            java.util.HashMap<s, v$d> r12 = r9.g     // Catch:{ all -> 0x0127 }
            r12.put(r13, r10)     // Catch:{ all -> 0x0127 }
            monitor-exit(r11)     // Catch:{ all -> 0x0127 }
            java.util.concurrent.TimeUnit r11 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch:{ Throwable -> 0x012c }
            defpackage.ck.a(r10, r14, r11)     // Catch:{ Throwable -> 0x012c }
            goto L_0x012a
        L_0x0127:
            r10 = move-exception
            monitor-exit(r11)     // Catch:{ all -> 0x0127 }
            throw r10     // Catch:{ Throwable -> 0x012c }
        L_0x012a:
            monitor-exit(r9)
            return
        L_0x012c:
            r9.c()     // Catch:{ all -> 0x0131 }
            monitor-exit(r9)
            return
        L_0x0131:
            r10 = move-exception
            monitor-exit(r9)
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.v.a(android.content.Context, int, java.lang.String, s, long):void");
    }

    private List<bo> a(int i2, String str) {
        List list;
        try {
            cs a2 = cs.a(this.a);
            if (a2 == null) {
                return Collections.EMPTY_LIST;
            }
            list = bu.a().a(a2.b);
            try {
                if (!list.isEmpty()) {
                    boolean equalsIgnoreCase = "https".equalsIgnoreCase(a2.a);
                    boolean b2 = ct.b();
                    ListIterator listIterator = list.listIterator();
                    while (listIterator.hasNext()) {
                        bo boVar = (bo) listIterator.next();
                        ConnType a3 = ConnType.a(boVar.e());
                        if (a3 != null) {
                            if (!(a3.c() == equalsIgnoreCase && (i2 == ai.c || a3.d() == i2))) {
                                listIterator.remove();
                            }
                            if (b2 && ci.b(boVar.a())) {
                                listIterator.remove();
                            }
                        }
                    }
                }
                if (cl.a(1)) {
                    cl.a("awcn.SessionRequest", "[getAvailStrategy]", str, "strategies", list);
                }
            } catch (Throwable unused) {
                cl.e("awcn.SessionRequest", "", str, new Object[0]);
                return list;
            }
            return list;
        } catch (Throwable unused2) {
            list = Collections.EMPTY_LIST;
            cl.e("awcn.SessionRequest", "", str, new Object[0]);
            return list;
        }
    }

    private List<af> a(List<bo> list, String str) {
        if (list.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        ArrayList arrayList = new ArrayList();
        int i2 = 0;
        int i3 = 0;
        while (i2 < list.size()) {
            bo boVar = list.get(i2);
            int h2 = boVar.h();
            int i4 = i3;
            for (int i5 = 0; i5 <= h2; i5++) {
                i4++;
                String str2 = this.a;
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append("_");
                sb.append(i4);
                af afVar = new af(str2, sb.toString(), boVar);
                afVar.d = i5;
                afVar.e = h2;
                arrayList.add(afVar);
            }
            i2++;
            i3 = i4;
        }
        return arrayList;
    }

    /* access modifiers changed from: 0000 */
    public final void a(Context context, af afVar, c cVar, String str) {
        ConnType c2 = afVar.c();
        if (context == null || c2.b()) {
            this.e = new bh(context, afVar);
        } else {
            bi biVar = new bi(context, afVar);
            biVar.a(this.b.d);
            biVar.a(this.j);
            biVar.a(this.b.g.b(this.i));
            this.e = biVar;
        }
        cl.b("awcn.SessionRequest", "create connection...", str, "Host", this.a, "Type", afVar.c(), "IP", afVar.a(), "Port", Integer.valueOf(afVar.b()), "heartbeat", Integer.valueOf(afVar.d()), "session", this.e);
        a(this.e, cVar, System.currentTimeMillis());
        this.e.a();
        this.h.retryTimes++;
        this.h.startConnect = System.currentTimeMillis();
        if (this.h.retryTimes == 0) {
            this.h.putExtra("firstIp", afVar.a());
        }
    }

    private void a(final p pVar, final c cVar, final long j2) {
        if (cVar != null) {
            pVar.a(4095, (ah) new ah() {
                public final void a(p pVar, int i, ag agVar) {
                    String str;
                    p pVar2 = pVar;
                    int i2 = i;
                    ag agVar2 = agVar;
                    if (pVar2 != null) {
                        int i3 = agVar2 == null ? 0 : agVar2.b;
                        if (agVar2 == null) {
                            str = "";
                        } else {
                            str = agVar2.c;
                        }
                        if (i2 != 2) {
                            if (i2 == 256) {
                                cl.a("awcn.SessionRequest", null, pVar2 != null ? pVar2.p : null, "Session", pVar2, "EventType", Integer.valueOf(i), "Event", agVar2);
                                v.a(v.this, pVar2, i3, str);
                                cVar.a(pVar2, i2, i3);
                            } else if (i2 == 512) {
                                cl.a("awcn.SessionRequest", null, pVar2 != null ? pVar2.p : null, "Session", pVar2, "EventType", Integer.valueOf(i), "Event", agVar2);
                                v.a(v.this, pVar2, 0, (String) null);
                                cVar.a(pVar2);
                                return;
                            }
                            return;
                        }
                        cl.a("awcn.SessionRequest", null, pVar2 != null ? pVar2.p : null, "Session", pVar2, "EventType", Integer.valueOf(i), "Event", agVar2);
                        v.a(v.this, pVar2, i3, str);
                        if (v.this.c.b(v.this, pVar2)) {
                            cVar.b(pVar2);
                        } else {
                            cVar.a(pVar2, i2, i3);
                        }
                    }
                }
            });
            pVar.a(1792, (ah) new ah() {
                public final void a(p pVar, int i, ag agVar) {
                    cl.a("awcn.SessionRequest", "Receive session event", null, "eventType", Integer.valueOf(i));
                    bm bmVar = new bm();
                    if (i == 512) {
                        bmVar.a = true;
                    }
                    bu.a().a(pVar.d, pVar.k, bmVar);
                }
            });
        }
    }

    /* access modifiers changed from: protected */
    public final void b(boolean z) {
        cl.a("awcn.SessionRequest", "closeSessions", this.b.c, "host", this.a, "autoCreate", Boolean.valueOf(z));
        if (!z && this.e != null) {
            this.e.u = false;
            this.e.a(false);
        }
        List<p> a2 = this.c.a(this);
        if (a2 != null) {
            for (p next : a2) {
                if (next != null) {
                    next.a(z);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public final void a() {
        cl.a("awcn.SessionRequest", "reCreateSession", null, "host", this.a);
        b(true);
    }

    /* access modifiers changed from: protected */
    public final void a(long j2) throws InterruptedException, TimeoutException {
        cl.a("awcn.SessionRequest", "[await]", null, "timeoutMs", Long.valueOf(j2));
        if (j2 > 0) {
            synchronized (this.l) {
                long currentTimeMillis = System.currentTimeMillis() + j2;
                while (this.d) {
                    long currentTimeMillis2 = System.currentTimeMillis();
                    if (currentTimeMillis2 >= currentTimeMillis) {
                        break;
                    }
                    this.l.wait(currentTimeMillis - currentTimeMillis2);
                }
                if (this.d) {
                    throw new TimeoutException();
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public final int b() {
        p pVar = this.e;
        if (pVar != null) {
            return pVar.j.d();
        }
        return -1;
    }

    /* access modifiers changed from: 0000 */
    public final void c() {
        a(false);
        synchronized (this.l) {
            this.l.notifyAll();
        }
    }

    static /* synthetic */ void a(v vVar, p pVar, int i2, int i3) {
        if (256 == i2 && i3 != -2613 && i3 != -2601) {
            bj bjVar = new bj();
            bjVar.e = "networkPrefer";
            bjVar.f = "policy";
            bjVar.b = vVar.a;
            bjVar.c = String.valueOf(i3);
            bjVar.a = false;
            x.a().a(bjVar);
            vVar.h.ret = 0;
            vVar.h.appendErrorTrace(i3);
            vVar.h.errorCode = String.valueOf(i3);
            vVar.h.totalTime = System.currentTimeMillis() - vVar.h.start;
            vVar.h.syncValueFromSession(pVar);
            x.a().a((StatObject) vVar.h);
        }
    }

    static /* synthetic */ void a(v vVar, p pVar, int i2, String str) {
        Context a2 = m.a();
        if (a2 != null && vVar.j != null && vVar.j.c) {
            try {
                Intent intent = new Intent(Constants.ACTION_RECEIVE);
                intent.setPackage(a2.getPackageName());
                intent.setClassName(a2, AdapterUtilityImpl.msgService);
                intent.putExtra("command", 103);
                intent.putExtra("host", pVar.i());
                intent.putExtra(Constants.KEY_CENTER_HOST, true);
                boolean e2 = pVar.e();
                if (!e2) {
                    intent.putExtra("errorCode", i2);
                    intent.putExtra(Constants.KEY_ERROR_DETAIL, str);
                }
                intent.putExtra(Constants.KEY_CONNECT_AVAILABLE, e2);
                intent.putExtra(Constants.KEY_TYPE_INAPP, true);
                a2.startService(intent);
            } catch (Throwable unused) {
                cl.e("awcn.SessionRequest", "sendConnectInfoBroadCastToAccs", null, new Object[0]);
            }
        }
    }
}
