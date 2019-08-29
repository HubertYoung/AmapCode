package com.tencent.open.utils;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;

/* compiled from: ProGuard */
public final class ThreadManager {
    public static final Executor NETWORK_EXECUTOR = a();
    private static Handler a;
    private static Object b = new Object();
    private static Handler c;
    private static HandlerThread d;
    private static Handler e;
    private static HandlerThread f;

    /* compiled from: ProGuard */
    static class SerialExecutor implements Executor {
        final Queue<Runnable> a;
        Runnable b;

        private SerialExecutor() {
            this.a = new LinkedList();
        }

        public synchronized void execute(final Runnable runnable) {
            this.a.offer(new Runnable() {
                public void run() {
                    try {
                        runnable.run();
                    } finally {
                        SerialExecutor.this.a();
                    }
                }
            });
            if (this.b == null) {
                a();
            }
        }

        /* access modifiers changed from: protected */
        public synchronized void a() {
            Runnable poll = this.a.poll();
            this.b = poll;
            if (poll != null) {
                ThreadManager.NETWORK_EXECUTOR.execute(this.b);
            }
        }
    }

    public static void init() {
    }

    /* JADX WARNING: type inference failed for: r0v1, types: [java.util.concurrent.Executor] */
    /* JADX WARNING: type inference failed for: r1v2 */
    /* JADX WARNING: type inference failed for: r0v6, types: [java.util.concurrent.Executor] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 2 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.util.concurrent.Executor a() {
        /*
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 11
            if (r0 < r1) goto L_0x0018
            java.util.concurrent.ThreadPoolExecutor r0 = new java.util.concurrent.ThreadPoolExecutor
            r3 = 1
            r4 = 1
            r5 = 0
            java.util.concurrent.TimeUnit r7 = java.util.concurrent.TimeUnit.SECONDS
            java.util.concurrent.LinkedBlockingQueue r8 = new java.util.concurrent.LinkedBlockingQueue
            r8.<init>()
            r2 = r0
            r2.<init>(r3, r4, r5, r7, r8)
            goto L_0x003d
        L_0x0018:
            java.lang.Class<android.os.AsyncTask> r0 = android.os.AsyncTask.class
            java.lang.String r1 = "sExecutor"
            java.lang.reflect.Field r0 = r0.getDeclaredField(r1)     // Catch:{ Exception -> 0x002c }
            r1 = 1
            r0.setAccessible(r1)     // Catch:{ Exception -> 0x002c }
            r1 = 0
            java.lang.Object r0 = r0.get(r1)     // Catch:{ Exception -> 0x002c }
            java.util.concurrent.Executor r0 = (java.util.concurrent.Executor) r0     // Catch:{ Exception -> 0x002c }
            goto L_0x003d
        L_0x002c:
            java.util.concurrent.ThreadPoolExecutor r0 = new java.util.concurrent.ThreadPoolExecutor
            r2 = 1
            r3 = 1
            r4 = 0
            java.util.concurrent.TimeUnit r6 = java.util.concurrent.TimeUnit.SECONDS
            java.util.concurrent.LinkedBlockingQueue r7 = new java.util.concurrent.LinkedBlockingQueue
            r7.<init>()
            r1 = r0
            r1.<init>(r2, r3, r4, r6, r7)
        L_0x003d:
            boolean r1 = r0 instanceof java.util.concurrent.ThreadPoolExecutor
            if (r1 == 0) goto L_0x0048
            r1 = r0
            java.util.concurrent.ThreadPoolExecutor r1 = (java.util.concurrent.ThreadPoolExecutor) r1
            r2 = 3
            r1.setCorePoolSize(r2)
        L_0x0048:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.open.utils.ThreadManager.a():java.util.concurrent.Executor");
    }

    public static void executeOnNetWorkThread(Runnable runnable) {
        try {
            NETWORK_EXECUTOR.execute(runnable);
        } catch (RejectedExecutionException unused) {
        }
    }

    public static Handler getMainHandler() {
        if (a == null) {
            synchronized (b) {
                if (a == null) {
                    a = new Handler(Looper.getMainLooper());
                }
            }
        }
        return a;
    }

    public static Handler getFileThreadHandler() {
        if (e == null) {
            synchronized (ThreadManager.class) {
                HandlerThread handlerThread = new HandlerThread("SDK_FILE_RW");
                f = handlerThread;
                handlerThread.start();
                e = new Handler(f.getLooper());
            }
        }
        return e;
    }

    public static Looper getFileThreadLooper() {
        return getFileThreadHandler().getLooper();
    }

    public static Thread getSubThread() {
        if (d == null) {
            getSubThreadHandler();
        }
        return d;
    }

    public static Handler getSubThreadHandler() {
        if (c == null) {
            synchronized (ThreadManager.class) {
                HandlerThread handlerThread = new HandlerThread("SDK_SUB");
                d = handlerThread;
                handlerThread.start();
                c = new Handler(d.getLooper());
            }
        }
        return c;
    }

    public static Looper getSubThreadLooper() {
        return getSubThreadHandler().getLooper();
    }

    public static void executeOnSubThread(Runnable runnable) {
        getSubThreadHandler().post(runnable);
    }

    public static void executeOnFileThread(Runnable runnable) {
        getFileThreadHandler().post(runnable);
    }

    public static Executor newSerialExecutor() {
        return new SerialExecutor();
    }
}
