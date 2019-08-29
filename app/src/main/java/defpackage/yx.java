package defpackage;

import java.util.HashMap;
import java.util.Map;

/* renamed from: yx reason: default package */
/* compiled from: AosErrorProcessor */
public class yx {
    private static volatile yx b;
    Map<Integer, a> a;

    /* renamed from: yx$a */
    /* compiled from: AosErrorProcessor */
    public interface a {
        void a();
    }

    public static yx a() {
        if (b == null) {
            synchronized (yx.class) {
                try {
                    if (b == null) {
                        b = new yx();
                    }
                }
            }
        }
        return b;
    }

    public final synchronized void a(a aVar) {
        bpv.c("network.AosErrorProcessor", "register handler, code:14");
        if (this.a == null) {
            this.a = new HashMap();
        }
        this.a.put(Integer.valueOf(14), aVar);
    }
}
