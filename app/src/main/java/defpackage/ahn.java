package defpackage;

import java.util.Comparator;
import java.util.concurrent.Executor;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/* renamed from: ahn reason: default package */
/* compiled from: ThreadPool */
public class ahn implements Executor {
    /* access modifiers changed from: private */
    public static final AtomicLong c = new AtomicLong(1);
    private static final ThreadFactory d = new ThreadFactory() {
        public final Thread newThread(Runnable runnable) {
            StringBuilder sb = new StringBuilder("ThreadPool-");
            sb.append(ahn.c.getAndIncrement());
            return new Thread(runnable, sb.toString());
        }
    };
    private static final Comparator<Runnable> e = new Comparator<Runnable>() {
        public final /* synthetic */ int compare(Object obj, Object obj2) {
            Runnable runnable = (Runnable) obj;
            Runnable runnable2 = (Runnable) obj2;
            if (!(runnable instanceof a) || !(runnable2 instanceof a)) {
                return 0;
            }
            return ((a) runnable).b - ((a) runnable2).b;
        }
    };
    private static volatile ahn f;
    public final ThreadPoolExecutor a;
    public final PriorityBlockingQueue<Runnable> b;

    /* renamed from: ahn$a */
    /* compiled from: ThreadPool */
    static class a implements Runnable {
        private Runnable a;
        /* access modifiers changed from: private */
        public int b;

        public a(Runnable runnable, int i) {
            this.a = runnable;
            this.b = i;
        }

        public final void run() {
            if (this.a != null) {
                this.a.run();
            }
        }
    }

    public ahn() {
        this(5);
    }

    public ahn(int i) {
        this.b = new PriorityBlockingQueue<>(256, e);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(i, 256, 1, TimeUnit.SECONDS, this.b, d);
        this.a = threadPoolExecutor;
    }

    public void execute(Runnable runnable) {
        if (runnable != null) {
            a(runnable, 2);
        }
    }

    public final void a(Runnable runnable, int i) {
        final a aVar = new a(runnable, i);
        try {
            this.a.execute(aVar);
        } catch (RejectedExecutionException e2) {
            if (!a()) {
                e2.printStackTrace();
            } else {
                aho.a(new Runnable() {
                    public final void run() {
                        try {
                            ahn.this.a.execute(aVar);
                        } catch (RejectedExecutionException unused) {
                            aho.a(this);
                        }
                    }
                });
            }
        }
    }

    public final boolean a() {
        return this.a.getActiveCount() >= this.a.getCorePoolSize();
    }

    public static ahn b() {
        if (f == null) {
            synchronized (ahn.class) {
                try {
                    if (f == null) {
                        f = new ahn();
                    }
                }
            }
        }
        return f;
    }
}
