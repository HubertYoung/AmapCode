package defpackage;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/* renamed from: box reason: default package */
/* compiled from: HttpService */
public class box {
    private static volatile box a;
    private static volatile boy b;

    public static box a() {
        if (a == null) {
            synchronized (box.class) {
                if (a == null) {
                    if (b == null) {
                        b = new boy();
                    }
                    a = new box();
                }
            }
        }
        return a;
    }

    public static synchronized void a(boy boy) {
        synchronized (box.class) {
            if (b != null) {
                bpv.d("ANet-HttpService", "setNetworkClient error, NetworkClient has initialized!");
            } else {
                b = boy;
            }
        }
    }

    public static <T extends bpk> T a(@NonNull bph bph, Class<T> cls) {
        return b.a(bph, cls);
    }

    public static <T extends bpk> void a(@NonNull bph bph, @Nullable bpl<T> bpl) {
        b.a(bph, bpl);
    }

    public static void a(@NonNull bjg bjg, @Nullable bjf bjf) {
        bjg.setChannel(1);
        bjg.setPriority(50);
        bjh.a().a(bjg, bjf);
    }

    public static void a(bph bph) {
        if (bph instanceof bjg) {
            bjh.a().a(((bjg) bph).d);
        } else {
            b.a(bph);
        }
    }
}
