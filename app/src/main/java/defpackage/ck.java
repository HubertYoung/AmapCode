package defpackage;

import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* renamed from: ck reason: default package */
/* compiled from: ThreadPoolExecutorFactory */
public final class ck {
    private static ScheduledThreadPoolExecutor a = new ScheduledThreadPoolExecutor(1, new b("AWCN Scheduler"));
    private static ThreadPoolExecutor b;
    private static ThreadPoolExecutor c = new cj(TimeUnit.SECONDS, new PriorityBlockingQueue(), new b("AWCN Worker(M)"));
    private static ThreadPoolExecutor d;
    private static ThreadPoolExecutor e;

    /* renamed from: ck$a */
    /* compiled from: ThreadPoolExecutorFactory */
    static class a implements Comparable<a>, Runnable {
        Runnable a = null;
        int b = 0;
        long c = System.currentTimeMillis();

        public final /* bridge */ /* synthetic */ int compareTo(Object obj) {
            a aVar = (a) obj;
            if (this.b != aVar.b) {
                return this.b - aVar.b;
            }
            return (int) (aVar.c - this.c);
        }

        public a(Runnable runnable, int i) {
            this.a = runnable;
            this.b = i;
            this.c = System.currentTimeMillis();
        }

        public final void run() {
            this.a.run();
        }
    }

    /* renamed from: ck$b */
    /* compiled from: ThreadPoolExecutorFactory */
    static class b implements ThreadFactory {
        AtomicInteger a = new AtomicInteger(0);
        String b;

        b(String str) {
            this.b = str;
        }

        public final Thread newThread(Runnable runnable) {
            StringBuilder sb = new StringBuilder();
            sb.append(this.b);
            sb.append(this.a.incrementAndGet());
            Thread thread = new Thread(runnable, sb.toString());
            cl.b("awcn.ThreadPoolExecutorFactory", "thread created!", null, "name", thread.getName());
            thread.setPriority(5);
            return thread;
        }
    }

    /* renamed from: ck$c */
    /* compiled from: ThreadPoolExecutorFactory */
    public static class c {
        public static int a = 0;
        public static int b = 1;
        public static int c = 9;
    }

    static {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 2, 60, TimeUnit.SECONDS, new LinkedBlockingDeque(), new b("AWCN Worker(H)"));
        b = threadPoolExecutor;
        ThreadPoolExecutor threadPoolExecutor2 = new ThreadPoolExecutor(2, 2, 60, TimeUnit.SECONDS, new LinkedBlockingDeque(), new b("AWCN Worker(L)"));
        d = threadPoolExecutor2;
        ThreadPoolExecutor threadPoolExecutor3 = new ThreadPoolExecutor(32, 32, 60, TimeUnit.SECONDS, new LinkedBlockingDeque(), new b("AWCN Worker(Backup)"));
        e = threadPoolExecutor3;
        b.allowCoreThreadTimeOut(true);
        c.allowCoreThreadTimeOut(true);
        d.allowCoreThreadTimeOut(true);
        e.allowCoreThreadTimeOut(true);
    }

    public static Future<?> a(Runnable runnable) {
        return a.submit(runnable);
    }

    public static Future<?> a(Runnable runnable, long j, TimeUnit timeUnit) {
        return a.schedule(runnable, j, timeUnit);
    }

    public static void b(Runnable runnable) {
        a.remove(runnable);
    }

    public static Future<?> a(Runnable runnable, int i) {
        if (cl.a(1)) {
            cl.a("awcn.ThreadPoolExecutorFactory", "submit priority task", null, "priority", Integer.valueOf(i));
        }
        if (i < c.a || i > c.c) {
            i = c.c;
        }
        if (i == c.a) {
            return b.submit(runnable);
        }
        if (i == c.c) {
            return d.submit(runnable);
        }
        return c.submit(new a(runnable, i));
    }

    public static Future<?> c(Runnable runnable) {
        return e.submit(runnable);
    }
}
