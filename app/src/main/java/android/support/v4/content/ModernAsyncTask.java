package android.support.v4.content;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

abstract class ModernAsyncTask<Params, Progress, Result> {
    private static final ThreadFactory a = new ThreadFactory() {
        private final AtomicInteger a = new AtomicInteger(1);

        public final Thread newThread(Runnable runnable) {
            StringBuilder sb = new StringBuilder("ModernAsyncTask #");
            sb.append(this.a.getAndIncrement());
            return new Thread(runnable, sb.toString());
        }
    };
    private static final BlockingQueue<Runnable> b = new LinkedBlockingQueue(10);
    private static InternalHandler c;
    public static final Executor d;
    private static volatile Executor h;
    final WorkerRunnable<Params, Result> e = new WorkerRunnable<Params, Result>() {
        public Result call() throws Exception {
            ModernAsyncTask.this.i.set(true);
            Process.setThreadPriority(10);
            return ModernAsyncTask.this.c(ModernAsyncTask.this.a());
        }
    };
    final FutureTask<Result> f = new FutureTask<Result>(this.e) {
        /* access modifiers changed from: protected */
        public void done() {
            try {
                ModernAsyncTask.b(ModernAsyncTask.this, get());
            } catch (InterruptedException unused) {
            } catch (ExecutionException e) {
                throw new RuntimeException("An error occurred while executing doInBackground()", e.getCause());
            } catch (CancellationException unused2) {
                ModernAsyncTask.b(ModernAsyncTask.this, null);
            } catch (Throwable th) {
                throw new RuntimeException("An error occurred while executing doInBackground()", th);
            }
        }
    };
    volatile Status g = Status.PENDING;
    /* access modifiers changed from: private */
    public final AtomicBoolean i = new AtomicBoolean();

    /* renamed from: android.support.v4.content.ModernAsyncTask$4 reason: invalid class name */
    static /* synthetic */ class AnonymousClass4 {
        static final /* synthetic */ int[] a = new int[Status.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(6:0|1|2|3|4|6) */
        /* JADX WARNING: Code restructure failed: missing block: B:7:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        static {
            /*
                android.support.v4.content.ModernAsyncTask$Status[] r0 = android.support.v4.content.ModernAsyncTask.Status.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                a = r0
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0014 }
                android.support.v4.content.ModernAsyncTask$Status r1 = android.support.v4.content.ModernAsyncTask.Status.RUNNING     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x001f }
                android.support.v4.content.ModernAsyncTask$Status r1 = android.support.v4.content.ModernAsyncTask.Status.FINISHED     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.v4.content.ModernAsyncTask.AnonymousClass4.<clinit>():void");
        }
    }

    static class AsyncTaskResult<Data> {
        final ModernAsyncTask a;
        final Data[] b;

        AsyncTaskResult(ModernAsyncTask modernAsyncTask, Data... dataArr) {
            this.a = modernAsyncTask;
            this.b = dataArr;
        }
    }

    static class InternalHandler extends Handler {
        public InternalHandler() {
            super(Looper.getMainLooper());
        }

        public void handleMessage(Message message) {
            AsyncTaskResult asyncTaskResult = (AsyncTaskResult) message.obj;
            if (message.what == 1) {
                ModernAsyncTask.c(asyncTaskResult.a, asyncTaskResult.b[0]);
            }
        }
    }

    public enum Status {
        PENDING,
        RUNNING,
        FINISHED
    }

    static abstract class WorkerRunnable<Params, Result> implements Callable<Result> {
        Params[] b;

        private WorkerRunnable() {
        }

        /* synthetic */ WorkerRunnable(byte b2) {
            this();
        }
    }

    /* access modifiers changed from: protected */
    public abstract Result a();

    /* access modifiers changed from: protected */
    public void a(Result result) {
    }

    /* access modifiers changed from: protected */
    public void b(Result result) {
    }

    static {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 128, 1, TimeUnit.SECONDS, b, a);
        d = threadPoolExecutor;
        h = threadPoolExecutor;
    }

    private static Handler b() {
        InternalHandler internalHandler;
        synchronized (ModernAsyncTask.class) {
            if (c == null) {
                c = new InternalHandler();
            }
            internalHandler = c;
        }
        return internalHandler;
    }

    /* access modifiers changed from: private */
    public Result c(Result result) {
        b().obtainMessage(1, new AsyncTaskResult(this, result)).sendToTarget();
        return result;
    }

    static /* synthetic */ void b(ModernAsyncTask modernAsyncTask, Object obj) {
        if (!modernAsyncTask.i.get()) {
            modernAsyncTask.c(obj);
        }
    }

    static /* synthetic */ void c(ModernAsyncTask modernAsyncTask, Object obj) {
        if (modernAsyncTask.f.isCancelled()) {
            modernAsyncTask.b(obj);
        } else {
            modernAsyncTask.a((Result) obj);
        }
        modernAsyncTask.g = Status.FINISHED;
    }
}
