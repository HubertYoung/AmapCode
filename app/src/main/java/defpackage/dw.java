package defpackage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/* renamed from: dw reason: default package */
/* compiled from: RepeatProcessor */
public final class dw {
    private static final ExecutorService[] a = new ExecutorService[2];
    /* access modifiers changed from: private */
    public static AtomicInteger b = new AtomicInteger(0);

    static {
        for (int i = 0; i < 2; i++) {
            a[i] = Executors.newSingleThreadExecutor(new ThreadFactory() {
                public final Thread newThread(Runnable runnable) {
                    return new Thread(runnable, String.format("RepeaterThread:%d", new Object[]{Integer.valueOf(dw.b.getAndIncrement())}));
                }
            });
        }
    }

    public static void a(int i, Runnable runnable) {
        a[Math.abs(i % 2)].submit(runnable);
    }
}
