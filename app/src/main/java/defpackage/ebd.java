package defpackage;

import com.autonavi.minimap.R;

/* renamed from: ebd reason: default package */
/* compiled from: RouteGpsTextureProvider */
public final class ebd extends a {
    private static ebd b;
    private static b c = new b(0, 0);
    private static b d = new b(0, 0);
    private static b e = new b(210106, R.drawable.self_pos_locked_grey);
    private static b f = new b(210107, R.drawable.self_pos_locked);
    private static b g = new b(210108, R.drawable.self_pos_locked);
    private static b h = new b(210109, R.drawable.self_pos_locked_grey);
    private static b i = new b(210110, R.drawable.self_pos_locked_grey);
    private static b j = new b(210111, R.drawable.self_pos_locked_grey);

    private ebd() {
    }

    public static synchronized ebd i() {
        ebd ebd;
        synchronized (ebd.class) {
            try {
                if (b == null) {
                    b = new ebd();
                }
                ebd = b;
            }
        }
        return ebd;
    }

    public final b a() {
        return c;
    }

    public final b b() {
        return d;
    }

    public final b c() {
        return e;
    }

    public final b d() {
        return f;
    }

    public final b e() {
        return g;
    }

    public final b f() {
        return h;
    }

    public final b g() {
        return i;
    }

    public final b h() {
        return j;
    }
}
