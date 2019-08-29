package defpackage;

import java.util.HashSet;
import java.util.Set;

/* renamed from: axq reason: default package */
/* compiled from: NaviStateManager */
public final class axq {
    public static int a = 0;
    public static int b = 1;
    public static int c = 2;
    public static int d = 3;
    public static int e = 4;
    public static int f = 5;
    public static int g = 6;
    private static final Set<Integer> h;
    private static axq j;
    private final Set<Integer> i = new HashSet();

    static {
        HashSet hashSet = new HashSet();
        h = hashSet;
        hashSet.add(Integer.valueOf(b));
        h.add(Integer.valueOf(c));
        h.add(Integer.valueOf(d));
        h.add(Integer.valueOf(e));
        h.add(Integer.valueOf(f));
        h.add(Integer.valueOf(g));
    }

    private axq() {
    }

    public static synchronized axq a() {
        axq axq;
        synchronized (axq.class) {
            try {
                if (j == null) {
                    j = new axq();
                }
                axq = j;
            }
        }
        return axq;
    }

    public final synchronized boolean b() {
        return this.i.size() > 0;
    }

    public final synchronized void a(int i2) {
        if (h.contains(Integer.valueOf(i2))) {
            this.i.add(Integer.valueOf(i2));
        }
    }

    public final synchronized void b(int i2) {
        if (this.i.contains(Integer.valueOf(i2))) {
            this.i.remove(Integer.valueOf(i2));
        }
    }
}
