package defpackage;

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* renamed from: bpx reason: default package */
/* compiled from: DefaultThreadPoolExecutor */
final class bpx implements b {
    private final ThreadPoolExecutor a;

    public bpx(String str, int i) {
        int i2 = i;
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(Math.max((int) ((((double) i) / 3.0d) + 0.5d), 1), i2, 60, TimeUnit.MILLISECONDS, new PriorityBlockingQueue(i, new Comparator<Runnable>() {
            public final /* bridge */ /* synthetic */ int compare(Object obj, Object obj2) {
                Runnable runnable = (Runnable) obj;
                Runnable runnable2 = (Runnable) obj2;
                if (!(runnable instanceof c) || !(runnable2 instanceof c)) {
                    return 0;
                }
                return ((c) runnable2).b - ((c) runnable).b;
            }
        }), new a(str));
        this.a = threadPoolExecutor;
    }

    public final void a(Runnable runnable) {
        this.a.execute(runnable);
    }

    public final void a() {
        this.a.shutdown();
    }
}
