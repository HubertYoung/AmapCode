package defpackage;

import java.util.concurrent.atomic.AtomicBoolean;

/* renamed from: ee reason: default package */
/* compiled from: Monitor */
public final class ee {
    static AtomicBoolean a = new AtomicBoolean(false);

    public static synchronized void a() {
        synchronized (ee.class) {
            if (a.compareAndSet(false, true)) {
                ar.a().b();
            }
        }
    }
}
