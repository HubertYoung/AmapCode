package defpackage;

import android.os.Process;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import mtopsdk.common.util.TBSdkLog;

/* renamed from: ffy reason: default package */
/* compiled from: MtopSDKThreadPoolExecutorFactory */
public class ffy {
    private static int a = 10;
    private static volatile ThreadPoolExecutor b;
    private static volatile ThreadPoolExecutor c;
    private static volatile ExecutorService[] d;

    /* renamed from: ffy$a */
    /* compiled from: MtopSDKThreadPoolExecutorFactory */
    static class a implements ThreadFactory {
        int a = 10;
        private final AtomicInteger b = new AtomicInteger();
        private String c = "";

        public a(int i) {
            this.a = i;
        }

        public a(int i, String str) {
            this.a = i;
            this.c = str;
        }

        public final Thread newThread(Runnable runnable) {
            StringBuilder sb = new StringBuilder(32);
            sb.append("MTOPSDK ");
            if (fdd.a(this.c)) {
                sb.append(this.c);
                sb.append(Token.SEPARATOR);
            } else {
                sb.append("DefaultPool ");
            }
            sb.append("Thread:");
            sb.append(this.b.getAndIncrement());
            return new Thread(runnable, sb.toString()) {
                public final void run() {
                    Process.setThreadPriority(a.this.a);
                    super.run();
                }
            };
        }
    }

    private static ThreadPoolExecutor b() {
        if (b == null) {
            synchronized (ffy.class) {
                if (b == null) {
                    b = a(3, 3, 128, new a(a));
                }
            }
        }
        return b;
    }

    public static ThreadPoolExecutor a() {
        if (c == null) {
            synchronized (ffy.class) {
                try {
                    if (c == null) {
                        c = a(4, 4, 0, new a(a, "RequestPool"));
                    }
                }
            }
        }
        return c;
    }

    private static ExecutorService[] c() {
        if (d == null) {
            synchronized (ffy.class) {
                if (d == null) {
                    ExecutorService[] executorServiceArr = new ExecutorService[2];
                    for (int i = 0; i < 2; i++) {
                        executorServiceArr[i] = a(1, 1, 0, new a(a, "CallbackPool".concat(String.valueOf(i))));
                    }
                    d = executorServiceArr;
                }
            }
        }
        return d;
    }

    public static Future<?> a(Runnable runnable) {
        try {
            return b().submit(runnable);
        } catch (Throwable th) {
            StringBuilder sb = new StringBuilder("[submit]submit runnable to Mtop Default ThreadPool error ---");
            sb.append(th.toString());
            TBSdkLog.d("mtopsdk.MtopSDKThreadPoolExecutorFactory", sb.toString());
            return null;
        }
    }

    public static Future<?> a(int i, Runnable runnable) {
        try {
            ExecutorService[] c2 = c();
            return c2[Math.abs(i % c2.length)].submit(runnable);
        } catch (Throwable th) {
            StringBuilder sb = new StringBuilder("[submitCallbackTask]submit runnable to Mtop Callback ThreadPool error ---");
            sb.append(th.toString());
            TBSdkLog.d("mtopsdk.MtopSDKThreadPoolExecutorFactory", sb.toString());
            return null;
        }
    }

    public static Future<?> b(Runnable runnable) {
        try {
            return a().submit(runnable);
        } catch (Throwable th) {
            StringBuilder sb = new StringBuilder("[submitRequestTask]submit runnable to Mtop Request ThreadPool error ---");
            sb.append(th.toString());
            TBSdkLog.d("mtopsdk.MtopSDKThreadPoolExecutorFactory", sb.toString());
            return null;
        }
    }

    private static ThreadPoolExecutor a(int i, int i2, int i3, ThreadFactory threadFactory) {
        LinkedBlockingQueue linkedBlockingQueue;
        if (i3 > 0) {
            linkedBlockingQueue = new LinkedBlockingQueue(i3);
        } else {
            linkedBlockingQueue = new LinkedBlockingQueue();
        }
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(i, i2, 60, TimeUnit.SECONDS, linkedBlockingQueue, threadFactory);
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        return threadPoolExecutor;
    }
}
