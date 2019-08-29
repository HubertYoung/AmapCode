package defpackage;

import android.text.TextUtils;
import anet.channel.status.NetworkStatusHelper;
import anet.channel.strategy.IPConnStrategy;
import anet.channel.strategy.StrategyConfig;
import anet.channel.strategy.StrategyInfoHolder;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.CopyOnWriteArraySet;
import org.json.JSONObject;

/* renamed from: bv reason: default package */
/* compiled from: StrategyInstance */
final class bv implements bq, a {
    boolean a = false;
    StrategyInfoHolder b = null;
    long c = 0;
    CopyOnWriteArraySet<br> d = new CopyOnWriteArraySet<>();
    private bp e = new bp() {
        public final boolean a(bo boVar) {
            boolean g = j.g();
            boolean z = bv.this.b.b().c;
            String str = boVar.e().protocol;
            if ((g && z) || (!"quic".equals(str) && !"quicplain".equals(str))) {
                return true;
            }
            cl.b("awcn.StrategyCenter", "quic strategy disabled", null, "strategy", boVar);
            return false;
        }
    };

    bv() {
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x003f, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void a(android.content.Context r6) {
        /*
            r5 = this;
            monitor-enter(r5)
            boolean r0 = r5.a     // Catch:{ all -> 0x0040 }
            if (r0 != 0) goto L_0x003e
            if (r6 != 0) goto L_0x0008
            goto L_0x003e
        L_0x0008:
            r0 = 0
            r1 = 0
            java.lang.String r2 = "awcn.StrategyCenter"
            java.lang.String r3 = "StrategyCenter initialize started."
            java.lang.Object[] r4 = new java.lang.Object[r0]     // Catch:{ Exception -> 0x0033 }
            defpackage.cl.b(r2, r3, r1, r4)     // Catch:{ Exception -> 0x0033 }
            defpackage.bz.a(r6)     // Catch:{ Exception -> 0x0033 }
            defpackage.bx.a(r6)     // Catch:{ Exception -> 0x0033 }
            cf r6 = defpackage.cf.b.a     // Catch:{ Exception -> 0x0033 }
            r6.a(r5)     // Catch:{ Exception -> 0x0033 }
            anet.channel.strategy.StrategyInfoHolder r6 = new anet.channel.strategy.StrategyInfoHolder     // Catch:{ Exception -> 0x0033 }
            r6.<init>()     // Catch:{ Exception -> 0x0033 }
            r5.b = r6     // Catch:{ Exception -> 0x0033 }
            r6 = 1
            r5.a = r6     // Catch:{ Exception -> 0x0033 }
            java.lang.String r6 = "awcn.StrategyCenter"
            java.lang.String r2 = "StrategyCenter initialize finished."
            java.lang.Object[] r3 = new java.lang.Object[r0]     // Catch:{ Exception -> 0x0033 }
            defpackage.cl.b(r6, r2, r1, r3)     // Catch:{ Exception -> 0x0033 }
            monitor-exit(r5)
            return
        L_0x0033:
            java.lang.String r6 = "awcn.StrategyCenter"
            java.lang.String r2 = "StrategyCenter initialize failed."
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x0040 }
            defpackage.cl.e(r6, r2, r1, r0)     // Catch:{ all -> 0x0040 }
            monitor-exit(r5)
            return
        L_0x003e:
            monitor-exit(r5)
            return
        L_0x0040:
            r6 = move-exception
            monitor-exit(r5)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.bv.a(android.content.Context):void");
    }

    public final synchronized void a() {
        bx.a();
        cf cfVar = b.a;
        cfVar.c.clear();
        cfVar.d.clear();
        cfVar.e.set(false);
        if (this.b != null) {
            NetworkStatusHelper.b(this.b);
            this.b = new StrategyInfoHolder();
        }
    }

    public final String a(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (c()) {
            return str2;
        }
        String a2 = this.b.b.a(str);
        if (a2 != null || TextUtils.isEmpty(str2)) {
            str2 = a2;
        }
        if (r8 == null) {
            bt btVar = a.a;
            if (!btVar.b) {
                r8 = null;
            } else {
                String str3 = btVar.a.get(str);
                if (str3 == null) {
                    str3 = "https";
                    btVar.a.put(str, str3);
                }
                r8 = str3;
            }
            if (r8 == null) {
                r8 = "http";
            }
        }
        cl.a("awcn.StrategyCenter", "getSchemeByHost", null, "host", str, "scheme", r8);
        return r8;
    }

    public final String b(String str) {
        if (c() || TextUtils.isEmpty(str)) {
            return null;
        }
        return this.b.b().b(str);
    }

    public final List<bo> a(String str) {
        return a(str, this.e);
    }

    public final List<bo> a(String str, bp bpVar) {
        if (TextUtils.isEmpty(str) || c()) {
            return Collections.EMPTY_LIST;
        }
        String b2 = this.b.b().b(str);
        if (!TextUtils.isEmpty(b2)) {
            str = b2;
        }
        List<bo> a2 = this.b.b().a(str);
        if (a2.isEmpty()) {
            a2 = this.b.c.a(str);
        }
        if (a2.isEmpty() || bpVar == null) {
            cl.a("getConnStrategyListByHost", null, "host", str, "result", a2);
            return a2;
        }
        boolean z = !j.i() || (j.j() && this.b.b().a(str, j.k()));
        ListIterator<bo> listIterator = a2.listIterator();
        while (listIterator.hasNext()) {
            bo next = listIterator.next();
            if (!bpVar.a(next)) {
                listIterator.remove();
            }
            if (z && ci.b(next.a())) {
                listIterator.remove();
            }
        }
        if (cl.a(1)) {
            cl.a("getConnStrategyListByHost", null, "host", str, "result", a2);
        }
        return a2;
    }

    public final void d(String str) {
        if (!c() && !TextUtils.isEmpty(str)) {
            cl.b("awcn.StrategyCenter", "force refresh strategy", null, "host", str);
            this.b.b().a(str, true);
        }
    }

    public final void a(br brVar) {
        cl.d("awcn.StrategyCenter", "registerListener", null, "listener", this.d);
        this.d.add(brVar);
    }

    public final void b(br brVar) {
        cl.d("awcn.StrategyCenter", "unregisterListener", null, "listener", this.d);
        this.d.remove(brVar);
    }

    public final String c(String str) {
        if (c()) {
            return null;
        }
        StrategyConfig strategyConfig = this.b.b;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return strategyConfig.b.get(str);
    }

    public final void a(String str, bo boVar, bm bmVar) {
        if (!c() && boVar != null && (boVar instanceof IPConnStrategy)) {
            IPConnStrategy iPConnStrategy = (IPConnStrategy) boVar;
            if (iPConnStrategy.i == 1) {
                bs bsVar = this.b.c;
                if (!bmVar.a && !TextUtils.isEmpty(str)) {
                    List list = bsVar.a.get(str);
                    if (!(list == null || list == Collections.EMPTY_LIST)) {
                        Iterator it = list.iterator();
                        while (it.hasNext()) {
                            if (it.next() == boVar) {
                                it.remove();
                            }
                        }
                        if (list.isEmpty()) {
                            bsVar.a.put(str, Collections.EMPTY_LIST);
                        }
                    }
                }
            } else if (iPConnStrategy.i == 0) {
                this.b.b().a(str, boVar, bmVar);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final boolean c() {
        if (this.b != null) {
            return false;
        }
        cl.c("StrategyCenter not initialized", null, "isInitialized", Boolean.valueOf(this.a));
        return true;
    }

    public final void onEvent(cd cdVar) {
        if (cdVar.a == 1 && this.b != null) {
            cl.a("awcn.StrategyCenter", "receive amdc event", null, new Object[0]);
            d a2 = bw.a((JSONObject) cdVar.b);
            if (a2 != null) {
                StrategyInfoHolder strategyInfoHolder = this.b;
                if (a2.g != 0) {
                    bz.a(a2.g, a2.h);
                }
                strategyInfoHolder.b().a(a2);
                StrategyConfig strategyConfig = strategyInfoHolder.b;
                if (a2.b != null) {
                    synchronized (strategyConfig) {
                        TreeMap treeMap = null;
                        for (b bVar : a2.b) {
                            if (bVar.j) {
                                strategyConfig.a.remove(bVar.a);
                            } else if (bVar.d != null) {
                                if (treeMap == null) {
                                    treeMap = new TreeMap();
                                }
                                treeMap.put(bVar.a, bVar.d);
                            } else {
                                if ("http".equalsIgnoreCase(bVar.c) || "https".equalsIgnoreCase(bVar.c)) {
                                    strategyConfig.a.put(bVar.a, bVar.c);
                                } else {
                                    strategyConfig.a.put(bVar.a, "No_Result");
                                }
                                if (!TextUtils.isEmpty(bVar.e)) {
                                    strategyConfig.b.put(bVar.a, bVar.e);
                                } else {
                                    strategyConfig.b.remove(bVar.a);
                                }
                            }
                        }
                        if (treeMap != null) {
                            for (Entry entry : treeMap.entrySet()) {
                                String str = (String) entry.getValue();
                                if (strategyConfig.a.containsKey(str)) {
                                    strategyConfig.a.put(entry.getKey(), strategyConfig.a.get(str));
                                } else {
                                    strategyConfig.a.put(entry.getKey(), "No_Result");
                                }
                            }
                        }
                    }
                    if (cl.a(1)) {
                        cl.a("awcn.StrategyConfig", "", null, "SchemeMap", strategyConfig.a.toString());
                        cl.a("awcn.StrategyConfig", "", null, "UnitMap", strategyConfig.b.toString());
                    }
                }
                b();
                Iterator<br> it = this.d.iterator();
                while (it.hasNext()) {
                    try {
                        it.next().a(a2);
                    } catch (Exception unused) {
                        cl.e("awcn.StrategyCenter", "onStrategyUpdated failed", null, new Object[0]);
                    }
                }
            }
        }
    }

    public final synchronized void b() {
        cl.b("awcn.StrategyCenter", "saveData", null, new Object[0]);
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - this.c > StatisticConfig.MIN_UPLOAD_INTERVAL) {
            this.c = currentTimeMillis;
            ch.a(new Runnable() {
                public final void run() {
                    if (!bv.this.c()) {
                        bv.this.b.a();
                    }
                }
            }, 500);
        }
    }
}
