package defpackage;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* renamed from: ch reason: default package */
/* compiled from: AmdcThreadPoolExecutor */
public class ch {
    /* access modifiers changed from: private */
    public static AtomicInteger a = new AtomicInteger(0);
    private static ScheduledThreadPoolExecutor b;

    private static ScheduledThreadPoolExecutor b() {
        if (b == null) {
            synchronized (ch.class) {
                if (b == null) {
                    ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(2, new ThreadFactory() {
                        public final Thread newThread(Runnable runnable) {
                            StringBuilder sb = new StringBuilder("AMDC");
                            sb.append(ch.a.incrementAndGet());
                            Thread thread = new Thread(runnable, sb.toString());
                            cl.b("awcn.AmdcThreadPoolExecutor", "thread created!", null, "name", thread.getName());
                            thread.setPriority(5);
                            return thread;
                        }
                    });
                    b = scheduledThreadPoolExecutor;
                    scheduledThreadPoolExecutor.setKeepAliveTime(60, TimeUnit.SECONDS);
                    b.allowCoreThreadTimeOut(true);
                }
            }
        }
        return b;
    }

    public static void a(Runnable runnable) {
        try {
            b().submit(runnable);
        } catch (Exception unused) {
            cl.e("awcn.AmdcThreadPoolExecutor", "submit task failed", null, new Object[0]);
        }
    }

    public static void a(Runnable runnable, long j) {
        try {
            b().schedule(runnable, j, TimeUnit.MILLISECONDS);
        } catch (Exception unused) {
            cl.e("awcn.AmdcThreadPoolExecutor", "schedule task failed", null, new Object[0]);
        }
    }
}
