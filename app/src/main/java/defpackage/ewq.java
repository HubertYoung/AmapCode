package defpackage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* renamed from: ewq reason: default package */
/* compiled from: ThreadPoolUtils */
public final class ewq {
    private static ExecutorService a = Executors.newSingleThreadExecutor();
    private static ExecutorService b = Executors.newFixedThreadPool(5);

    public static ExecutorService a() {
        return a;
    }

    public static ExecutorService b() {
        return b;
    }
}
