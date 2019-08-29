package anet.channel.strategy;

import anet.channel.strategy.utils.SerialLruCache;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map.Entry;

class StrategyCollection implements Serializable {
    private static final long serialVersionUID = 1454976454894208229L;
    String a;
    StrategyList b = null;
    volatile long c = 0;
    volatile String d = null;
    boolean e = false;
    private transient long f = 0;

    public StrategyCollection() {
    }

    protected StrategyCollection(String str) {
        this.a = str;
        this.e = cb.a(str);
    }

    public final void a() {
        if (System.currentTimeMillis() - this.c > 172800000) {
            this.b = null;
            return;
        }
        if (this.b != null) {
            StrategyList strategyList = this.b;
            if (strategyList.a == null) {
                strategyList.a = new ArrayList();
            }
            if (strategyList.b == null) {
                strategyList.b = new SerialLruCache(40);
            }
            Iterator<Entry<Integer, ConnHistoryItem>> it = strategyList.b.entrySet().iterator();
            while (it.hasNext()) {
                ConnHistoryItem connHistoryItem = (ConnHistoryItem) it.next().getValue();
                long j = connHistoryItem.b > connHistoryItem.c ? connHistoryItem.b : connHistoryItem.c;
                if (j != 0 && System.currentTimeMillis() - j > 86400000) {
                    it.remove();
                }
            }
            for (IPConnStrategy next : strategyList.a) {
                if (!strategyList.b.containsKey(Integer.valueOf(next.hashCode()))) {
                    strategyList.b.put(Integer.valueOf(next.hashCode()), new ConnHistoryItem());
                }
            }
            Collections.sort(strategyList.a, strategyList.a());
        }
    }

    public final synchronized List<bo> b() {
        if (this.b == null) {
            return Collections.EMPTY_LIST;
        }
        StrategyList strategyList = this.b;
        if (strategyList.a.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        List<bo> list = null;
        for (IPConnStrategy next : strategyList.a) {
            ConnHistoryItem connHistoryItem = strategyList.b.get(Integer.valueOf(next.hashCode()));
            if (connHistoryItem != null) {
                if (connHistoryItem.a() >= 3 && System.currentTimeMillis() - connHistoryItem.c <= 300000) {
                    cl.b("awcn.StrategyList", "strategy ban!", null, "strategy", next);
                }
            }
            if (list == null) {
                list = new LinkedList<>();
            }
            list.add(next);
        }
        if (list != null) {
            return list;
        }
        return Collections.EMPTY_LIST;
    }

    public final synchronized void a(bo boVar, bm bmVar) {
        if (this.b != null) {
            StrategyList strategyList = this.b;
            boolean z = true;
            if ((boVar instanceof IPConnStrategy) && strategyList.a.indexOf(boVar) != -1) {
                ConnHistoryItem connHistoryItem = strategyList.b.get(Integer.valueOf(((IPConnStrategy) boVar).hashCode()));
                boolean z2 = bmVar.a;
                long currentTimeMillis = System.currentTimeMillis();
                if (currentTimeMillis - (z2 ? connHistoryItem.b : connHistoryItem.c) > 10000) {
                    connHistoryItem.a = (connHistoryItem.a << 1) | (z2 ^ true) ? (byte) 1 : 0;
                    if (z2) {
                        connHistoryItem.b = currentTimeMillis;
                    } else {
                        connHistoryItem.c = currentTimeMillis;
                    }
                }
                Collections.sort(strategyList.a, strategyList.d);
            }
            if (!bmVar.a) {
                StrategyList strategyList2 = this.b;
                boolean z3 = true;
                boolean z4 = true;
                for (IPConnStrategy next : strategyList2.a) {
                    if (!((strategyList2.b.get(Integer.valueOf(next.hashCode())).a & 1) == 1)) {
                        if (next.h == 0) {
                            z3 = false;
                        }
                        z4 = false;
                    }
                }
                if (!strategyList2.c || !z3) {
                    if (!z4) {
                        z = false;
                    }
                }
                if (z) {
                    long currentTimeMillis2 = System.currentTimeMillis();
                    if (currentTimeMillis2 - this.f > 60000) {
                        bu.a().d(this.a);
                        this.f = currentTimeMillis2;
                    }
                }
            }
        }
    }

    public final boolean c() {
        return System.currentTimeMillis() > this.c;
    }

    public final synchronized void a(b bVar) {
        this.c = System.currentTimeMillis() + (((long) bVar.b) * 1000);
        if (!bVar.a.equalsIgnoreCase(this.a)) {
            cl.d("StrategyCollection", "update error!", null, "host", this.a, "dnsInfo.host", bVar.a);
            return;
        }
        this.d = bVar.d;
        if (bVar.f == null || bVar.f.length == 0 || bVar.h == null || bVar.h.length == 0) {
            if (bVar.i != null) {
                if (bVar.i.length == 0) {
                }
            }
            this.b = null;
            return;
        }
        if (this.b == null) {
            this.b = new StrategyList();
        }
        StrategyList strategyList = this.b;
        for (IPConnStrategy iPConnStrategy : strategyList.a) {
            iPConnStrategy.j = true;
        }
        if (bVar.i != null) {
            for (e eVar : bVar.i) {
                strategyList.a(eVar.a, ci.c(eVar.a) ? -1 : 1, eVar.b);
            }
        }
        for (int i = 0; i < bVar.h.length; i++) {
            for (String a2 : bVar.f) {
                strategyList.a(a2, 1, bVar.h[i]);
            }
            if (bVar.g != null) {
                strategyList.c = true;
                for (String a3 : bVar.g) {
                    strategyList.a(a3, 0, bVar.h[i]);
                }
            } else {
                strategyList.c = false;
            }
        }
        ListIterator<IPConnStrategy> listIterator = strategyList.a.listIterator();
        while (listIterator.hasNext()) {
            if (listIterator.next().j) {
                listIterator.remove();
            }
        }
        Collections.sort(strategyList.a, strategyList.a());
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(32);
        sb.append("\nStrategyList = ");
        sb.append(this.c);
        if (this.b != null) {
            sb.append(this.b.toString());
        } else if (this.d != null) {
            sb.append('[');
            sb.append(this.a);
            sb.append("=>");
            sb.append(this.d);
            sb.append(']');
        } else {
            sb.append("[]");
        }
        return sb.toString();
    }
}
