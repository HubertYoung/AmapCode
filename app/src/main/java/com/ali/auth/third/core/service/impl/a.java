package com.ali.auth.third.core.service.impl;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import com.ali.auth.third.core.service.MemberExecutorService;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public final class a implements MemberExecutorService {
    private final Handler a = new Handler(Looper.getMainLooper());
    private ThreadPoolExecutor b;
    private final BlockingQueue<Runnable> c = new LinkedBlockingQueue(128);
    private final HandlerThread d = new HandlerThread("SDK Looper Thread");
    private final Handler e;

    /* renamed from: com.ali.auth.third.core.service.impl.a$a reason: collision with other inner class name */
    public static class C0001a implements RejectedExecutionHandler {
        private BlockingQueue<Runnable> a;

        public C0001a(BlockingQueue<Runnable> blockingQueue) {
            this.a = blockingQueue;
        }

        private Object a(Object obj) {
            try {
                Field declaredField = obj.getClass().getDeclaredField("this$0");
                declaredField.setAccessible(true);
                return declaredField.get(obj);
            } catch (Exception e) {
                e.printStackTrace();
                return obj;
            }
        }

        public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
            Object[] array = this.a.toArray();
            StringBuilder sb = new StringBuilder();
            sb.append('[');
            int length = array.length;
            for (int i = 0; i < length; i++) {
                Object obj = array[i];
                sb.append(obj.getClass().isAnonymousClass() ? a(obj) : obj.getClass());
                sb.append(',');
                sb.append(' ');
            }
            sb.append(']');
            StringBuilder sb2 = new StringBuilder("Task ");
            sb2.append(runnable.toString());
            sb2.append(" rejected from ");
            sb2.append(threadPoolExecutor.toString());
            sb2.append(" in ");
            sb2.append(sb.toString());
            throw new RejectedExecutionException(sb2.toString());
        }
    }

    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:2:0x0028 */
    /* JADX WARNING: Removed duplicated region for block: B:2:0x0028 A[LOOP:0: B:2:0x0028->B:15:0x0028, LOOP_START, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public a() {
        /*
            r11 = this;
            r11.<init>()
            android.os.Handler r0 = new android.os.Handler
            android.os.Looper r1 = android.os.Looper.getMainLooper()
            r0.<init>(r1)
            r11.a = r0
            java.util.concurrent.LinkedBlockingQueue r0 = new java.util.concurrent.LinkedBlockingQueue
            r1 = 128(0x80, float:1.794E-43)
            r0.<init>(r1)
            r11.c = r0
            android.os.HandlerThread r0 = new android.os.HandlerThread
            java.lang.String r1 = "SDK Looper Thread"
            r0.<init>(r1)
            r11.d = r0
            android.os.HandlerThread r0 = r11.d
            r0.start()
            android.os.HandlerThread r0 = r11.d
            monitor-enter(r0)
        L_0x0028:
            android.os.HandlerThread r1 = r11.d     // Catch:{ all -> 0x0063 }
            android.os.Looper r1 = r1.getLooper()     // Catch:{ all -> 0x0063 }
            if (r1 != 0) goto L_0x0036
            android.os.HandlerThread r1 = r11.d     // Catch:{ InterruptedException -> 0x0028 }
            r1.wait()     // Catch:{ InterruptedException -> 0x0028 }
            goto L_0x0028
        L_0x0036:
            monitor-exit(r0)     // Catch:{ all -> 0x0063 }
            android.os.Handler r0 = new android.os.Handler
            android.os.HandlerThread r1 = r11.d
            android.os.Looper r1 = r1.getLooper()
            r0.<init>(r1)
            r11.e = r0
            com.ali.auth.third.core.service.impl.b r9 = new com.ali.auth.third.core.service.impl.b
            r9.<init>(r11)
            java.util.concurrent.ThreadPoolExecutor r0 = new java.util.concurrent.ThreadPoolExecutor
            r3 = 8
            r4 = 16
            r5 = 1
            java.util.concurrent.TimeUnit r7 = java.util.concurrent.TimeUnit.SECONDS
            java.util.concurrent.BlockingQueue<java.lang.Runnable> r8 = r11.c
            com.ali.auth.third.core.service.impl.a$a r10 = new com.ali.auth.third.core.service.impl.a$a
            java.util.concurrent.BlockingQueue<java.lang.Runnable> r1 = r11.c
            r10.<init>(r1)
            r2 = r0
            r2.<init>(r3, r4, r5, r7, r8, r9, r10)
            r11.b = r0
            return
        L_0x0063:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0063 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ali.auth.third.core.service.impl.a.<init>():void");
    }

    public final boolean awaitTermination(long j, TimeUnit timeUnit) throws InterruptedException {
        return this.b.awaitTermination(j, timeUnit);
    }

    public final void execute(Runnable runnable) {
        this.b.execute(runnable);
    }

    public final Looper getDefaultLooper() {
        return this.d.getLooper();
    }

    public final <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> collection) throws InterruptedException {
        return this.b.invokeAll(collection);
    }

    public final <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> collection, long j, TimeUnit timeUnit) throws InterruptedException {
        return this.b.invokeAll(collection, j, timeUnit);
    }

    public final <T> T invokeAny(Collection<? extends Callable<T>> collection) throws InterruptedException, ExecutionException {
        return this.b.invokeAny(collection);
    }

    public final <T> T invokeAny(Collection<? extends Callable<T>> collection, long j, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        return this.b.invokeAny(collection, j, timeUnit);
    }

    public final boolean isShutdown() {
        return this.b.isShutdown();
    }

    public final boolean isTerminated() {
        return this.b.isTerminated();
    }

    public final void postHandlerTask(Runnable runnable) {
        this.e.post(runnable);
    }

    public final void postTask(Runnable runnable) {
        this.b.execute(runnable);
    }

    public final void postUITask(Runnable runnable) {
        this.a.post(new c(this, runnable));
    }

    public final void shutdown() {
        this.b.shutdown();
    }

    public final List<Runnable> shutdownNow() {
        return this.b.shutdownNow();
    }

    public final Future<?> submit(Runnable runnable) {
        return this.b.submit(runnable);
    }

    public final <T> Future<T> submit(Runnable runnable, T t) {
        return this.b.submit(runnable, t);
    }

    public final <T> Future<T> submit(Callable<T> callable) {
        return this.b.submit(callable);
    }
}
