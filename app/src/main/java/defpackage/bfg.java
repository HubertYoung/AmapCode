package defpackage;

import java.util.ArrayList;
import java.util.List;

/* renamed from: bfg reason: default package */
/* compiled from: VUILifeCycleManager */
public final class bfg {
    private static final List<bfc> a = new ArrayList();
    private static boolean b;

    public static void a(bfc bfc) {
        synchronized (a) {
            if (!a.contains(bfc)) {
                a.add(bfc);
            }
        }
    }

    public static void a(int i) {
        if (!a.isEmpty()) {
            synchronized (a) {
                for (bfc next : a) {
                    switch (i) {
                        case 1:
                            next.b();
                            break;
                        case 2:
                            break;
                        case 3:
                            b = true;
                            next.c();
                            break;
                        case 4:
                            b = false;
                            next.d();
                            break;
                        case 5:
                            next.e();
                            break;
                    }
                }
                if (5 == i) {
                    a.clear();
                }
            }
        }
    }

    public static boolean a() {
        return b;
    }
}
