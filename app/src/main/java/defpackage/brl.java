package defpackage;

import java.util.ArrayList;
import java.util.List;

/* renamed from: brl reason: default package */
/* compiled from: GlobalMapEventManager */
public final class brl {
    private static final String[] a = new String[0];
    private static List<a> b = new ArrayList();

    /* renamed from: brl$a */
    /* compiled from: GlobalMapEventManager */
    public interface a {
        void a();

        void a(bty bty);

        boolean b();
    }

    public static void a(bty bty) {
        for (a a2 : b) {
            a2.a(bty);
        }
    }

    public static void a() {
        for (a a2 : b) {
            a2.a();
        }
    }

    public static void b() {
        for (a b2 : b) {
            b2.b();
        }
    }

    public static void a(a aVar) {
        b.add(aVar);
    }
}
