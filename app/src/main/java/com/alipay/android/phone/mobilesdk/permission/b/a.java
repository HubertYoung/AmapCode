package com.alipay.android.phone.mobilesdk.permission.b;

import com.alipay.android.phone.mobilesdk.permission.utils.h;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: AsyncTaskExecutor */
public final class a {
    static final int a;
    static final int b;
    static final int c = ((a * 3) + 1);
    /* access modifiers changed from: private */
    public static volatile AtomicBoolean i = new AtomicBoolean(false);
    private static final ThreadFactory j = new ThreadFactory() {
        private final AtomicInteger a = new AtomicInteger(0);

        public final Thread newThread(Runnable runnable) {
            if (runnable == null) {
                throw new IllegalArgumentException("null == runnable");
            }
            Thread thread = new Thread(new C0095a(runnable), "AsyTskExecutor_thread#" + this.a.incrementAndGet() + "_");
            thread.setPriority(1);
            return thread;
        }
    };
    private static a k = new a();
    final ThreadPoolExecutor d = new d(TimeUnit.SECONDS, new SynchronousQueue(), j);
    final ScheduledThreadPoolExecutor e = new c(b, j);
    final h f = new h();
    final ConcurrentHashMap<String, i> g = new ConcurrentHashMap<>();
    i h;

    /* renamed from: com.alipay.android.phone.mobilesdk.permission.b.a$a reason: collision with other inner class name */
    /* compiled from: AsyncTaskExecutor */
    private static class C0095a implements Runnable {
        private Runnable a;

        C0095a(Runnable runnable) {
            this.a = runnable;
        }

        public final void run() {
            try {
                if (a.i.get()) {
                    Thread.sleep((long) (new Random().nextInt(500) + 100));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.a.run();
        }
    }

    static {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        a = availableProcessors;
        b = availableProcessors + 1;
    }

    private a() {
        this.e.setKeepAliveTime(60, TimeUnit.SECONDS);
        this.e.allowCoreThreadTimeOut(true);
        this.e.setRejectedExecutionHandler(new CallerRunsPolicy());
        this.d.setRejectedExecutionHandler(new CallerRunsPolicy());
    }

    public static a a() {
        return k;
    }

    public final Executor b() {
        return this.d;
    }

    public final void a(String group, Runnable runnable, String threadName) {
        if (h.a((CharSequence) threadName)) {
            throw new IllegalArgumentException("The parameter \"threadName\" can't be empty.");
        } else if (h.a((CharSequence) group) || group.equalsIgnoreCase("GlobalStandardPipeline")) {
            d().a(f.a.b(runnable, threadName));
        } else {
            i standardPipeline = this.g.get(group);
            if (standardPipeline == null) {
                standardPipeline = new i(group, this.d);
                standardPipeline.b();
                this.g.put(group, standardPipeline);
            }
            standardPipeline.a(f.a.b(runnable, threadName));
        }
    }

    private i d() {
        if (this.h != null) {
            return this.h;
        }
        synchronized (this) {
            if (this.h != null) {
                i iVar = this.h;
                return iVar;
            }
            this.h = new i("GlobalStandardPipeline", this.d);
            this.h.b();
            i iVar2 = this.h;
            return iVar2;
        }
    }
}
