package anet.channel.strategy;

import android.text.TextUtils;
import anet.channel.status.NetworkStatusHelper;
import anet.channel.strategy.utils.SerialLruCache;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

public class StrategyTable implements Serializable {
    protected static Comparator<StrategyCollection> f = new Comparator<StrategyCollection>() {
        public final /* synthetic */ int compare(Object obj, Object obj2) {
            StrategyCollection strategyCollection = (StrategyCollection) obj;
            StrategyCollection strategyCollection2 = (StrategyCollection) obj2;
            if (strategyCollection.c != strategyCollection2.c) {
                return (int) (strategyCollection.c - strategyCollection2.c);
            }
            return strategyCollection.a.compareTo(strategyCollection2.a);
        }
    };
    private static final long serialVersionUID = 6044722613437834958L;
    protected String a;
    protected volatile String b;
    public boolean c = false;
    Map<String, Long> d;
    protected transient boolean e = false;
    private HostLruCache g;
    private volatile transient int h;

    static class HostLruCache extends SerialLruCache<String, StrategyCollection> {
        private static final long serialVersionUID = -4001655685948369525L;

        public HostLruCache() {
            super(256);
        }

        public boolean entryRemoved(Entry<String, StrategyCollection> entry) {
            if (!entry.getValue().e) {
                return true;
            }
            Iterator it = entrySet().iterator();
            while (true) {
                if (it.hasNext()) {
                    if (!((StrategyCollection) ((Entry) it.next()).getValue()).e) {
                        it.remove();
                        break;
                    }
                } else {
                    break;
                }
            }
            return false;
        }
    }

    protected StrategyTable(String str) {
        this.a = str;
        a();
    }

    /* access modifiers changed from: protected */
    public final void a() {
        if (this.g == null) {
            this.g = new HostLruCache();
            b();
        }
        for (StrategyCollection a2 : this.g.values()) {
            a2.a();
        }
        int i = 0;
        cl.b("awcn.StrategyTable", "strategy map", null, "size", Integer.valueOf(this.g.size()));
        if (!m.b()) {
            i = -1;
        }
        this.h = i;
        this.d = new ConcurrentHashMap();
    }

    public final List<bo> a(String str) {
        StrategyCollection strategyCollection;
        if (TextUtils.isEmpty(str) || !ci.c(str)) {
            return Collections.EMPTY_LIST;
        }
        try {
            if (b.a.a(this.a)) {
                TreeSet treeSet = null;
                synchronized (this.g) {
                    for (String next : b.a.a()) {
                        if (!this.g.containsKey(next)) {
                            this.g.put(next, new StrategyCollection(next));
                            if (treeSet == null) {
                                treeSet = new TreeSet();
                            }
                            treeSet.add(next);
                        }
                    }
                }
                if (treeSet != null) {
                    a((Set<String>) treeSet);
                }
            }
        } catch (Exception unused) {
            cl.e("awcn.StrategyTable", "checkInitHost failed", this.a, new Object[0]);
        }
        synchronized (this.g) {
            strategyCollection = (StrategyCollection) this.g.get(str);
            if (strategyCollection == null) {
                strategyCollection = new StrategyCollection(str);
                this.g.put(str, strategyCollection);
            }
        }
        if (strategyCollection.c == 0 || (strategyCollection.c() && bz.a() == 0)) {
            c(str);
        }
        return strategyCollection.b();
    }

    public final String b(String str) {
        StrategyCollection strategyCollection;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        synchronized (this.g) {
            strategyCollection = (StrategyCollection) this.g.get(str);
        }
        if (strategyCollection != null && strategyCollection.c() && bz.a() == 0) {
            c(str);
        }
        if (strategyCollection != null) {
            return strategyCollection.d;
        }
        return null;
    }

    public final void a(d dVar) {
        cl.b("awcn.StrategyTable", "update strategyTable with httpDns response", this.a, new Object[0]);
        try {
            this.b = dVar.a;
            this.h = dVar.f;
            b[] bVarArr = dVar.b;
            if (bVarArr != null) {
                synchronized (this.g) {
                    for (b bVar : bVarArr) {
                        if (!(bVar == null || bVar.a == null)) {
                            if (bVar.j) {
                                this.g.remove(bVar.a);
                            } else {
                                StrategyCollection strategyCollection = (StrategyCollection) this.g.get(bVar.a);
                                if (strategyCollection == null) {
                                    strategyCollection = new StrategyCollection(bVar.a);
                                    this.g.put(bVar.a, strategyCollection);
                                }
                                strategyCollection.a(bVar);
                            }
                        }
                    }
                }
                this.e = true;
                if (cl.a(1)) {
                    StringBuilder sb = new StringBuilder("uniqueId : ");
                    sb.append(this.a);
                    sb.append("\n-------------------------domains:------------------------------------");
                    cl.a("awcn.StrategyTable", sb.toString(), null, new Object[0]);
                    synchronized (this.g) {
                        for (Entry entry : this.g.entrySet()) {
                            sb.setLength(0);
                            sb.append((String) entry.getKey());
                            sb.append(" = ");
                            sb.append(((StrategyCollection) entry.getValue()).toString());
                            cl.a("awcn.StrategyTable", sb.toString(), null, new Object[0]);
                        }
                    }
                }
            }
        } catch (Throwable unused) {
            cl.e("awcn.StrategyTable", "fail to update strategyTable", this.a, new Object[0]);
        }
    }

    private void c(String str) {
        TreeSet treeSet = new TreeSet();
        treeSet.add(str);
        a((Set<String>) treeSet);
    }

    public final void a(String str, boolean z) {
        StrategyCollection strategyCollection;
        if (!TextUtils.isEmpty(str)) {
            synchronized (this.g) {
                strategyCollection = (StrategyCollection) this.g.get(str);
                if (strategyCollection == null) {
                    strategyCollection = new StrategyCollection(str);
                    this.g.put(str, strategyCollection);
                }
            }
            if (z || strategyCollection.c == 0 || (strategyCollection.c() && bz.a() == 0)) {
                c(str);
            }
        }
    }

    private void a(Set<String> set) {
        if (set != null && !set.isEmpty()) {
            if ((!m.h() || cm.a <= 0) && NetworkStatusHelper.h()) {
                int a2 = bz.a();
                if (a2 != 3) {
                    long currentTimeMillis = System.currentTimeMillis();
                    synchronized (this.g) {
                        for (String str : set) {
                            StrategyCollection strategyCollection = (StrategyCollection) this.g.get(str);
                            if (strategyCollection != null) {
                                strategyCollection.c = StatisticConfig.MIN_UPLOAD_INTERVAL + currentTimeMillis;
                            }
                        }
                    }
                    if (a2 == 0) {
                        TreeSet treeSet = new TreeSet(f);
                        synchronized (this.g) {
                            treeSet.addAll(this.g.values());
                        }
                        long currentTimeMillis2 = System.currentTimeMillis();
                        Iterator it = treeSet.iterator();
                        while (it.hasNext()) {
                            StrategyCollection strategyCollection2 = (StrategyCollection) it.next();
                            if (!strategyCollection2.c() || set.size() >= 40) {
                                break;
                            }
                            strategyCollection2.c = currentTimeMillis2 + StatisticConfig.MIN_UPLOAD_INTERVAL;
                            set.add(strategyCollection2.a);
                        }
                    }
                    cf cfVar = b.a;
                    int i = this.h;
                    if (!cfVar.b || set == null || set.isEmpty()) {
                        cl.d("awcn.HttpDispatcher", "invalid parameter", null, new Object[0]);
                        return;
                    }
                    if (cl.a(2)) {
                        cl.b("awcn.HttpDispatcher", "sendAmdcRequest", null, "hosts", set.toString());
                    }
                    HashMap hashMap = new HashMap();
                    hashMap.put("hosts", set);
                    hashMap.put("cv", String.valueOf(i));
                    ca caVar = cfVar.a;
                    try {
                        hashMap.put("Env", m.d());
                        synchronized (caVar) {
                            if (caVar.a == null) {
                                caVar.a = hashMap;
                                int nextInt = ca.b.nextInt(3000) + 2000;
                                cl.b("awcn.AmdcThreadPoolExecutor", "merge amdc request", null, "delay", Integer.valueOf(nextInt));
                                ch.a(new a(), (long) nextInt);
                            } else {
                                Set set2 = (Set) caVar.a.get("hosts");
                                Set set3 = (Set) hashMap.get("hosts");
                                if (hashMap.get("Env") != caVar.a.get("Env")) {
                                    caVar.a = hashMap;
                                } else if (set2.size() + set3.size() <= 40) {
                                    set3.addAll(set2);
                                    caVar.a = hashMap;
                                } else {
                                    ch.a(new a(hashMap));
                                }
                            }
                        }
                    } catch (Exception unused) {
                    }
                }
            } else {
                cl.b("awcn.StrategyTable", "app in background or no network", this.a, new Object[0]);
            }
        }
    }

    public final void a(String str, bo boVar, bm bmVar) {
        StrategyCollection strategyCollection;
        if (cl.a(1)) {
            cl.a("awcn.StrategyTable", "[notifyConnEvent]", null, "Host", str, "IConnStrategy", boVar, "ConnEvent", bmVar);
        }
        String str2 = boVar.e().protocol;
        if ("quic".equals(str2) || "quicplain".equals(str2)) {
            this.c = bmVar.a;
            cl.d("awcn.StrategyTable", "enbale quic", null, "uniqueId", this.a, "enable", Boolean.valueOf(bmVar.a));
        }
        if (!bmVar.a && ci.b(boVar.a())) {
            this.d.put(str, Long.valueOf(System.currentTimeMillis()));
            cl.d("awcn.StrategyTable", "disable ipv6", null, "uniqueId", this.a, "host", str);
        }
        synchronized (this.g) {
            strategyCollection = (StrategyCollection) this.g.get(str);
        }
        if (strategyCollection != null) {
            strategyCollection.a(boVar, bmVar);
        }
    }

    public final boolean a(String str, long j) {
        Long l = this.d.get(str);
        if (l == null) {
            return false;
        }
        if (l.longValue() + j >= System.currentTimeMillis()) {
            return true;
        }
        this.d.remove(str);
        return false;
    }

    private void b() {
        if (b.a.a(this.a)) {
            for (String next : b.a.a()) {
                this.g.put(next, new StrategyCollection(next));
            }
        }
    }
}
