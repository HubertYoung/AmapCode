package defpackage;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/* renamed from: del reason: default package */
/* compiled from: AliLinkNotifyManager */
public class del {
    private static volatile del b;
    public CopyOnWriteArrayList<a> a = new CopyOnWriteArrayList<>();

    /* renamed from: del$a */
    /* compiled from: AliLinkNotifyManager */
    public interface a {
        void b();
    }

    private del() {
    }

    public static del a() {
        if (b == null) {
            synchronized (del.class) {
                try {
                    if (b == null) {
                        b = new del();
                    }
                }
            }
        }
        return b;
    }

    public final void b() {
        Iterator<a> it = this.a.iterator();
        while (it.hasNext()) {
            a next = it.next();
            if (next != null) {
                next.b();
            }
        }
    }
}
