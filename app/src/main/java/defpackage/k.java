package defpackage;

import anet.channel.entity.ENV;
import java.util.HashMap;
import java.util.Map;

/* renamed from: k reason: default package */
/* compiled from: Config */
public final class k {
    public static final k a;
    /* access modifiers changed from: private */
    public static Map<String, k> e = new HashMap();
    public String b;
    /* access modifiers changed from: 0000 */
    public ENV c = ENV.ONLINE;
    public ba d;
    /* access modifiers changed from: private */
    public String f;

    /* renamed from: k$a */
    /* compiled from: Config */
    public static class a {
        public String a;
        public String b;
        public ENV c = ENV.ONLINE;
        public String d;
        public String e;

        /* JADX WARNING: Code restructure failed: missing block: B:18:0x0072, code lost:
            return r2;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:0x0074, code lost:
            r0 = new defpackage.k();
            defpackage.k.a(r0, r8.b);
            defpackage.k.a(r0, r8.c);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x0089, code lost:
            if (android.text.TextUtils.isEmpty(r8.a) == false) goto L_0x009d;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x008b, code lost:
            defpackage.k.b(r0, defpackage.cz.a(r8.b, "$", r8.c.toString()));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x009d, code lost:
            defpackage.k.b(r0, r8.a);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:0x00a8, code lost:
            if (android.text.TextUtils.isEmpty(r8.e) != false) goto L_0x00b8;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:26:0x00aa, code lost:
            defpackage.k.a(r0, defpackage.be.a().b(r8.e));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:0x00b8, code lost:
            defpackage.k.a(r0, defpackage.be.a().a(r8.d));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:28:0x00c5, code lost:
            r1 = defpackage.k.a();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:29:0x00c9, code lost:
            monitor-enter(r1);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:31:?, code lost:
            defpackage.k.a().put(defpackage.k.c(r0), r0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:32:0x00d5, code lost:
            monitor-exit(r1);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:33:0x00d6, code lost:
            return r0;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final defpackage.k a() {
            /*
                r8 = this;
                java.lang.String r0 = r8.b
                boolean r0 = android.text.TextUtils.isEmpty(r0)
                if (r0 == 0) goto L_0x0010
                java.lang.RuntimeException r0 = new java.lang.RuntimeException
                java.lang.String r1 = "appkey can not be null or empty!"
                r0.<init>(r1)
                throw r0
            L_0x0010:
                java.util.Map r0 = defpackage.k.e
                monitor-enter(r0)
                java.util.Map r1 = defpackage.k.e     // Catch:{ all -> 0x00da }
                java.util.Collection r1 = r1.values()     // Catch:{ all -> 0x00da }
                java.util.Iterator r1 = r1.iterator()     // Catch:{ all -> 0x00da }
            L_0x0021:
                boolean r2 = r1.hasNext()     // Catch:{ all -> 0x00da }
                if (r2 == 0) goto L_0x0073
                java.lang.Object r2 = r1.next()     // Catch:{ all -> 0x00da }
                k r2 = (defpackage.k) r2     // Catch:{ all -> 0x00da }
                anet.channel.entity.ENV r3 = r2.c     // Catch:{ all -> 0x00da }
                anet.channel.entity.ENV r4 = r8.c     // Catch:{ all -> 0x00da }
                if (r3 != r4) goto L_0x0021
                java.lang.String r3 = r2.b     // Catch:{ all -> 0x00da }
                java.lang.String r4 = r8.b     // Catch:{ all -> 0x00da }
                boolean r3 = r3.equals(r4)     // Catch:{ all -> 0x00da }
                if (r3 == 0) goto L_0x0021
                java.lang.String r1 = "awcn.Config"
                java.lang.String r3 = "duplicated config exist!"
                r4 = 0
                r5 = 4
                java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ all -> 0x00da }
                r6 = 0
                java.lang.String r7 = "appkey"
                r5[r6] = r7     // Catch:{ all -> 0x00da }
                r6 = 1
                java.lang.String r7 = r8.b     // Catch:{ all -> 0x00da }
                r5[r6] = r7     // Catch:{ all -> 0x00da }
                r6 = 2
                java.lang.String r7 = "env"
                r5[r6] = r7     // Catch:{ all -> 0x00da }
                r6 = 3
                anet.channel.entity.ENV r7 = r8.c     // Catch:{ all -> 0x00da }
                r5[r6] = r7     // Catch:{ all -> 0x00da }
                defpackage.cl.c(r1, r3, r4, r5)     // Catch:{ all -> 0x00da }
                java.lang.String r1 = r8.a     // Catch:{ all -> 0x00da }
                boolean r1 = android.text.TextUtils.isEmpty(r1)     // Catch:{ all -> 0x00da }
                if (r1 != 0) goto L_0x0071
                java.util.Map r1 = defpackage.k.e     // Catch:{ all -> 0x00da }
                java.lang.String r3 = r8.a     // Catch:{ all -> 0x00da }
                r1.put(r3, r2)     // Catch:{ all -> 0x00da }
            L_0x0071:
                monitor-exit(r0)     // Catch:{ all -> 0x00da }
                return r2
            L_0x0073:
                monitor-exit(r0)     // Catch:{ all -> 0x00da }
                k r0 = new k
                r0.<init>()
                java.lang.String r1 = r8.b
                r0.b = r1
                anet.channel.entity.ENV r1 = r8.c
                r0.c = r1
                java.lang.String r1 = r8.a
                boolean r1 = android.text.TextUtils.isEmpty(r1)
                if (r1 == 0) goto L_0x009d
                java.lang.String r1 = r8.b
                java.lang.String r2 = "$"
                anet.channel.entity.ENV r3 = r8.c
                java.lang.String r3 = r3.toString()
                java.lang.String r1 = defpackage.cz.a(r1, r2, r3)
                r0.f = r1
                goto L_0x00a2
            L_0x009d:
                java.lang.String r1 = r8.a
                r0.f = r1
            L_0x00a2:
                java.lang.String r1 = r8.e
                boolean r1 = android.text.TextUtils.isEmpty(r1)
                if (r1 != 0) goto L_0x00b8
                bb r1 = defpackage.be.a()
                java.lang.String r2 = r8.e
                ba r1 = r1.b(r2)
                r0.d = r1
                goto L_0x00c5
            L_0x00b8:
                bb r1 = defpackage.be.a()
                java.lang.String r2 = r8.d
                ba r1 = r1.a(r2)
                r0.d = r1
            L_0x00c5:
                java.util.Map r1 = defpackage.k.e
                monitor-enter(r1)
                java.util.Map r2 = defpackage.k.e     // Catch:{ all -> 0x00d7 }
                java.lang.String r3 = r0.f     // Catch:{ all -> 0x00d7 }
                r2.put(r3, r0)     // Catch:{ all -> 0x00d7 }
                monitor-exit(r1)     // Catch:{ all -> 0x00d7 }
                return r0
            L_0x00d7:
                r0 = move-exception
                monitor-exit(r1)     // Catch:{ all -> 0x00d7 }
                throw r0
            L_0x00da:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x00da }
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: defpackage.k.a.a():k");
        }
    }

    static {
        a aVar = new a();
        aVar.a = "[default]";
        aVar.b = "[default]";
        aVar.c = ENV.ONLINE;
        a = aVar.a();
    }

    protected k() {
    }

    public static k a(String str) {
        k kVar;
        synchronized (e) {
            kVar = e.get(str);
        }
        return kVar;
    }

    public static k a(String str, ENV env) {
        synchronized (e) {
            for (k next : e.values()) {
                if (next.c == env && next.b.equals(str)) {
                    return next;
                }
            }
            return null;
        }
    }

    public final String toString() {
        return this.f;
    }
}
