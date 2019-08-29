package anet.channel.strategy;

import anet.channel.strategy.utils.SerialLruCache;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

class StrategyList implements Serializable {
    private static final long serialVersionUID = -258058881561327174L;
    List<IPConnStrategy> a = new ArrayList();
    /* access modifiers changed from: 0000 */
    public Map<Integer, ConnHistoryItem> b = new SerialLruCache(40);
    boolean c = false;
    transient Comparator<IPConnStrategy> d = null;

    interface a<T> {
        boolean a(T t);
    }

    public String toString() {
        return this.a.toString();
    }

    /* access modifiers changed from: 0000 */
    public final void a(final String str, int i, final defpackage.bw.a aVar) {
        final ConnProtocol valueOf = ConnProtocol.valueOf(aVar);
        int a2 = a(this.a, new a<IPConnStrategy>() {
            public final /* synthetic */ boolean a(Object obj) {
                IPConnStrategy iPConnStrategy = (IPConnStrategy) obj;
                return iPConnStrategy.b == aVar.a && iPConnStrategy.a.equals(str) && iPConnStrategy.c.equals(valueOf);
            }
        });
        if (a2 != -1) {
            IPConnStrategy iPConnStrategy = this.a.get(a2);
            iPConnStrategy.d = aVar.c;
            iPConnStrategy.e = aVar.d;
            iPConnStrategy.g = aVar.f;
            iPConnStrategy.h = i;
            iPConnStrategy.i = 0;
            iPConnStrategy.j = false;
            return;
        }
        IPConnStrategy a3 = IPConnStrategy.a(str, aVar);
        if (a3 != null) {
            a3.h = i;
            a3.i = 0;
            if (!this.b.containsKey(Integer.valueOf(a3.hashCode()))) {
                this.b.put(Integer.valueOf(a3.hashCode()), new ConnHistoryItem());
            }
            this.a.add(a3);
        }
    }

    /* access modifiers changed from: 0000 */
    public final Comparator a() {
        if (this.d == null) {
            this.d = new Comparator<IPConnStrategy>() {
                public final /* synthetic */ int compare(Object obj, Object obj2) {
                    IPConnStrategy iPConnStrategy = (IPConnStrategy) obj;
                    IPConnStrategy iPConnStrategy2 = (IPConnStrategy) obj2;
                    int a2 = ((ConnHistoryItem) StrategyList.this.b.get(Integer.valueOf(iPConnStrategy.hashCode()))).a();
                    int a3 = ((ConnHistoryItem) StrategyList.this.b.get(Integer.valueOf(iPConnStrategy2.hashCode()))).a();
                    if (a2 != a3) {
                        return a2 - a3;
                    }
                    if (iPConnStrategy.h != iPConnStrategy2.h) {
                        return iPConnStrategy.h - iPConnStrategy2.h;
                    }
                    return iPConnStrategy.c.a - iPConnStrategy2.c.a;
                }
            };
        }
        return this.d;
    }

    private static <T> int a(Collection<T> collection, a<T> aVar) {
        if (collection == null) {
            return -1;
        }
        int i = 0;
        Iterator<T> it = collection.iterator();
        while (it.hasNext() && !aVar.a(it.next())) {
            i++;
        }
        if (i == collection.size()) {
            return -1;
        }
        return i;
    }
}
