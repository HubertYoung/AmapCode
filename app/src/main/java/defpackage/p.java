package defpackage;

import android.content.Context;
import android.text.TextUtils;
import anet.channel.entity.ConnType;
import anet.channel.statist.SessionStatistic;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.android.spdy.SpdyAgent;
import org.android.spdy.SpdySessionKind;
import org.android.spdy.SpdyVersion;

/* renamed from: p reason: default package */
/* compiled from: Session */
public abstract class p implements Comparable<p> {
    static ExecutorService v = Executors.newSingleThreadExecutor();
    public Context a;
    Map<ah, Integer> b = new LinkedHashMap();
    protected String c;
    public String d;
    protected String e;
    protected String f;
    protected int g;
    protected String h;
    protected int i;
    protected ConnType j;
    public bo k;
    protected String l;
    protected boolean m;
    protected int n;
    protected Runnable o;
    public final String p;
    public final SessionStatistic q;
    protected int r;
    protected int s;
    protected boolean t;
    protected boolean u;
    private boolean w;
    private Future<?> x;
    private List<Long> y;
    private long z;

    /* renamed from: p$a */
    /* compiled from: Session */
    public static class a {
        static final String[] a = {"CONNECTED", "CONNECTING", "CONNETFAIL", "AUTHING", "AUTH_SUCC", "AUTH_FAIL", "DISCONNECTED", "DISCONNECTING"};

        static String a(int i) {
            return a[i];
        }
    }

    public abstract aw a(ay ayVar, o oVar);

    public void a() {
    }

    public void a(int i2, byte[] bArr) {
    }

    public abstract void b();

    /* access modifiers changed from: protected */
    public abstract Runnable c();

    public void d() {
    }

    public abstract boolean e();

    /* access modifiers changed from: protected */
    public void k() {
    }

    public /* synthetic */ int compareTo(Object obj) {
        return ConnType.a(this.j, ((p) obj).j);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0073, code lost:
        r1 = r7.a.f();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x008c, code lost:
        r0 = true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public p(android.content.Context r6, defpackage.af r7) {
        /*
            r5 = this;
            r5.<init>()
            java.util.LinkedHashMap r0 = new java.util.LinkedHashMap
            r0.<init>()
            r5.b = r0
            r0 = 0
            r5.w = r0
            r1 = 0
            r5.l = r1
            r5.m = r0
            r2 = 6
            r5.n = r2
            r5.t = r0
            r2 = 1
            r5.u = r2
            r5.y = r1
            r3 = 0
            r5.z = r3
            r5.a = r6
            java.lang.String r6 = r7.a()
            r5.e = r6
            java.lang.String r6 = r5.e
            r5.f = r6
            int r6 = r7.b()
            r5.g = r6
            anet.channel.entity.ConnType r6 = r7.c()
            r5.j = r6
            java.lang.String r6 = r7.b
            r5.c = r6
            java.lang.String r6 = r5.c
            java.lang.String r1 = r5.c
            java.lang.String r3 = "://"
            int r1 = r1.indexOf(r3)
            int r1 = r1 + 3
            java.lang.String r6 = r6.substring(r1)
            r5.d = r6
            bo r6 = r7.a
            r1 = 20000(0x4e20, float:2.8026E-41)
            if (r6 == 0) goto L_0x0063
            bo r6 = r7.a
            int r6 = r6.g()
            if (r6 == 0) goto L_0x0063
            bo r6 = r7.a
            int r6 = r6.g()
            goto L_0x0065
        L_0x0063:
            r6 = 20000(0x4e20, float:2.8026E-41)
        L_0x0065:
            r5.s = r6
            bo r6 = r7.a
            if (r6 == 0) goto L_0x0079
            bo r6 = r7.a
            int r6 = r6.f()
            if (r6 == 0) goto L_0x0079
            bo r6 = r7.a
            int r1 = r6.f()
        L_0x0079:
            r5.r = r1
            bo r6 = r7.a
            r5.k = r6
            bo r6 = r5.k
            if (r6 == 0) goto L_0x008d
            bo r6 = r5.k
            int r6 = r6.b()
            r1 = -1
            if (r6 != r1) goto L_0x008d
            r0 = 1
        L_0x008d:
            r5.m = r0
            java.lang.String r6 = r7.c
            r5.p = r6
            anet.channel.statist.SessionStatistic r6 = new anet.channel.statist.SessionStatistic
            r6.<init>(r7)
            r5.q = r6
            anet.channel.statist.SessionStatistic r6 = r5.q
            java.lang.String r7 = r5.d
            r6.host = r7
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.p.<init>(android.content.Context, af):void");
    }

    public static void a(Context context, String str) {
        SpdyAgent instance = SpdyAgent.getInstance(context, SpdyVersion.SPDY3, SpdySessionKind.NONE_SESSION);
        if (instance == null || !SpdyAgent.checkLoadSucc()) {
            cl.d("agent null or configTnetALog load so fail!!!", null, "loadso", Boolean.valueOf(SpdyAgent.checkLoadSucc()));
            return;
        }
        instance.configLogFile(str, 5242880, 5);
    }

    public void a(boolean z2) {
        this.t = z2;
        b();
    }

    public final void a(int i2, ah ahVar) {
        if (this.b != null) {
            this.b.put(ahVar, Integer.valueOf(i2));
        }
    }

    public final String f() {
        return this.e;
    }

    public final int g() {
        return this.g;
    }

    public final ConnType h() {
        return this.j;
    }

    public final String i() {
        return this.c;
    }

    public final String j() {
        return this.l;
    }

    public final void a(final int i2, final ag agVar) {
        v.submit(new Runnable() {
            public final void run() {
                try {
                    if (p.this.b != null) {
                        for (ah next : p.this.b.keySet()) {
                            if (!(next == null || (p.this.b.get(next).intValue() & i2) == 0)) {
                                try {
                                    next.a(p.this, i2, agVar);
                                } catch (Exception e) {
                                    cl.d("awcn.Session", e.toString(), p.this.p, new Object[0]);
                                }
                            }
                        }
                    }
                } catch (Exception unused) {
                    cl.e("awcn.Session", "handleCallbacks", p.this.p, new Object[0]);
                }
            }
        });
    }

    /* JADX WARNING: Code restructure failed: missing block: B:39:0x006a, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void b(int r9, defpackage.ag r10) {
        /*
            r8 = this;
            monitor-enter(r8)
            java.lang.String r0 = "awcn.Session"
            java.lang.String r1 = "notifyStatus"
            java.lang.String r2 = r8.p     // Catch:{ all -> 0x006b }
            r3 = 2
            java.lang.Object[] r4 = new java.lang.Object[r3]     // Catch:{ all -> 0x006b }
            java.lang.String r5 = "status"
            r6 = 0
            r4[r6] = r5     // Catch:{ all -> 0x006b }
            java.lang.String r5 = defpackage.p.a.a(r9)     // Catch:{ all -> 0x006b }
            r7 = 1
            r4[r7] = r5     // Catch:{ all -> 0x006b }
            defpackage.cl.d(r0, r1, r2, r4)     // Catch:{ all -> 0x006b }
            int r0 = r8.n     // Catch:{ all -> 0x006b }
            if (r9 != r0) goto L_0x002a
            java.lang.String r9 = "awcn.Session"
            java.lang.String r10 = "ignore notifyStatus"
            java.lang.String r0 = r8.p     // Catch:{ all -> 0x006b }
            java.lang.Object[] r1 = new java.lang.Object[r6]     // Catch:{ all -> 0x006b }
            defpackage.cl.b(r9, r10, r0, r1)     // Catch:{ all -> 0x006b }
            monitor-exit(r8)
            return
        L_0x002a:
            r8.n = r9     // Catch:{ all -> 0x006b }
            int r9 = r8.n     // Catch:{ all -> 0x006b }
            switch(r9) {
                case 0: goto L_0x0064;
                case 1: goto L_0x0062;
                case 2: goto L_0x005b;
                case 3: goto L_0x0059;
                case 4: goto L_0x0046;
                case 5: goto L_0x0040;
                case 6: goto L_0x0034;
                case 7: goto L_0x0032;
                default: goto L_0x0031;
            }
        L_0x0031:
            goto L_0x0069
        L_0x0032:
            monitor-exit(r8)
            return
        L_0x0034:
            r8.k()     // Catch:{ all -> 0x006b }
            boolean r9 = r8.w     // Catch:{ all -> 0x006b }
            if (r9 != 0) goto L_0x0069
            r8.a(r3, r10)     // Catch:{ all -> 0x006b }
            monitor-exit(r8)
            return
        L_0x0040:
            r9 = 1024(0x400, float:1.435E-42)
            r8.a(r9, r10)     // Catch:{ all -> 0x006b }
            goto L_0x0069
        L_0x0046:
            bq r9 = defpackage.bu.a()     // Catch:{ all -> 0x006b }
            java.lang.String r0 = r8.d     // Catch:{ all -> 0x006b }
            java.lang.String r9 = r9.c(r0)     // Catch:{ all -> 0x006b }
            r8.l = r9     // Catch:{ all -> 0x006b }
            r9 = 512(0x200, float:7.175E-43)
            r8.a(r9, r10)     // Catch:{ all -> 0x006b }
            monitor-exit(r8)
            return
        L_0x0059:
            monitor-exit(r8)
            return
        L_0x005b:
            r9 = 256(0x100, float:3.59E-43)
            r8.a(r9, r10)     // Catch:{ all -> 0x006b }
            monitor-exit(r8)
            return
        L_0x0062:
            monitor-exit(r8)
            return
        L_0x0064:
            r8.a(r7, r10)     // Catch:{ all -> 0x006b }
            monitor-exit(r8)
            return
        L_0x0069:
            monitor-exit(r8)
            return
        L_0x006b:
            r9 = move-exception
            monitor-exit(r8)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.p.b(int, ag):void");
    }

    /* access modifiers changed from: protected */
    public final void l() {
        if (this.o == null) {
            this.o = c();
        }
        if (!(this.o == null || this.x == null)) {
            this.x.cancel(true);
        }
        if (this.o != null) {
            this.x = ck.a(this.o, (long) this.s, TimeUnit.MILLISECONDS);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Session@[");
        sb.append(this.p);
        sb.append('|');
        sb.append(this.j);
        sb.append(']');
        return sb.toString();
    }

    public final void a(ay ayVar, Map<String, List<String>> map) {
        try {
            if (map.containsKey("x-switch-unit")) {
                String a2 = cq.a(map, (String) "x-switch-unit");
                if (TextUtils.isEmpty(a2)) {
                    a2 = null;
                }
                if (!cz.c(this.l, a2)) {
                    long currentTimeMillis = System.currentTimeMillis();
                    if (currentTimeMillis - this.z > 60000) {
                        bu.a().d(ayVar.a.b);
                        this.z = currentTimeMillis;
                    }
                }
            }
        } catch (Exception unused) {
        }
    }

    public final void a(ay ayVar, int i2) {
        if (Collections.unmodifiableMap(ayVar.c).containsKey("x-pv") && i2 >= 500 && i2 < 600) {
            synchronized (this) {
                if (this.y == null) {
                    this.y = new LinkedList();
                }
                if (this.y.size() < 5) {
                    this.y.add(Long.valueOf(System.currentTimeMillis()));
                } else {
                    long longValue = this.y.remove(0).longValue();
                    long currentTimeMillis = System.currentTimeMillis();
                    if (currentTimeMillis - longValue <= 60000) {
                        bu.a().d(ayVar.a.b);
                        this.y.clear();
                    } else {
                        this.y.add(Long.valueOf(currentTimeMillis));
                    }
                }
            }
        }
    }
}
