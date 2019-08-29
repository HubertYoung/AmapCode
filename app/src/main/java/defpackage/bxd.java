package defpackage;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/* renamed from: bxd reason: default package */
/* compiled from: SearchResultProcessController */
public final class bxd {
    private static Map<Long, bxd> j = new HashMap();
    public e a;
    public bxf b;
    public bxc c;
    public bzh d;
    public cce e;
    public bxh f;
    public int g;
    public Set<String> h;
    public boolean i;
    private List<Integer> k = new LinkedList();

    private bxd() {
    }

    public static bxd a(Long l, Integer num) {
        bxd bxd = j.get(l);
        a(num.intValue());
        if (bxd == null) {
            for (Entry<Long, bxd> value : j.entrySet()) {
                bxd bxd2 = (bxd) value.getValue();
                if (bxd2 != null) {
                    bzh bzh = bxd2.d;
                    if (bzh != null) {
                        bzh.d();
                    }
                    cce cce = bxd2.e;
                    if (cce != null) {
                        cce.b();
                    }
                    bxd2.k.remove(num);
                }
            }
            bxd = new bxd();
            j.put(l, bxd);
        }
        if (!bxd.k.contains(num)) {
            bxd.k.add(num);
        }
        Iterator<Entry<Long, bxd>> it = j.entrySet().iterator();
        while (it.hasNext()) {
            bxd bxd3 = (bxd) it.next().getValue();
            if (bxd3 != null && bxd3.k.isEmpty()) {
                it.remove();
            }
        }
        return bxd;
    }

    public static void b(Long l, Integer num) {
        bxd bxd = j.get(l);
        if (bxd != null) {
            a(num.intValue());
            if (bxd.k.isEmpty()) {
                j.remove(l);
                if (bxd.d != null) {
                    bxd.d.d();
                    bxd.d.r();
                }
            }
            if (j.isEmpty()) {
                if (bxd.e != null) {
                    bxd.e.b();
                }
                if (bxd.a != null) {
                    bxd.a.b();
                }
            }
        }
    }

    private static void a(int i2) {
        for (Entry<Long, bxd> value : j.entrySet()) {
            bxd bxd = (bxd) value.getValue();
            if (bxd != null) {
                List<Integer> list = bxd.k;
                if (list.contains(Integer.valueOf(i2))) {
                    list.remove(Integer.valueOf(i2));
                }
            }
        }
    }
}
