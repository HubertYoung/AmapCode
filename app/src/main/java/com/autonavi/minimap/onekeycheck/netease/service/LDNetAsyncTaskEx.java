package com.autonavi.minimap.onekeycheck.netease.service;

import android.os.Handler;
import android.os.Message;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;
import proguard.annotation.KeepName;
import proguard.annotation.KeepPublicClassMembers;

@KeepPublicClassMembers
@KeepName
public abstract class LDNetAsyncTaskEx<Params, Progress, Result> {
    /* access modifiers changed from: private */
    public static final b b = new b(0);
    private volatile Status a = Status.PENDING;
    private final c<Params, Result> c = new c<Params, Result>() {
        public final Result call() throws Exception {
            return LDNetAsyncTaskEx.this.a();
        }
    };
    private final FutureTask<Result> d = new FutureTask<Result>(this.c) {
        /* access modifiers changed from: protected */
        public final void done() {
            Object obj = null;
            try {
                obj = get();
            } catch (InterruptedException unused) {
                getClass().getSimpleName();
            } catch (ExecutionException e) {
                throw new RuntimeException("An error occured while executing doInBackground()", e.getCause());
            } catch (CancellationException unused2) {
                LDNetAsyncTaskEx.b.obtainMessage(3, new a(LDNetAsyncTaskEx.this, null)).sendToTarget();
                return;
            } catch (Throwable th) {
                throw new RuntimeException("An error occured while executing doInBackground()", th);
            }
            LDNetAsyncTaskEx.b.obtainMessage(1, new a(LDNetAsyncTaskEx.this, obj)).sendToTarget();
        }
    };

    public enum Status {
        PENDING,
        RUNNING,
        FINISHED
    }

    static class a<Data> {
        final LDNetAsyncTaskEx a;
        final Data[] b;

        a(LDNetAsyncTaskEx lDNetAsyncTaskEx, Data... dataArr) {
            this.a = lDNetAsyncTaskEx;
            this.b = dataArr;
        }
    }

    static class b extends Handler {
        private b() {
        }

        /* synthetic */ b(byte b) {
            this();
        }

        public final void handleMessage(Message message) {
            a aVar = (a) message.obj;
            switch (message.what) {
                case 1:
                    aVar.a.b(aVar.b[0]);
                    return;
                case 2:
                    aVar.a.a((Progress[]) aVar.b);
                    return;
                case 3:
                    aVar.a.b();
                    break;
            }
        }
    }

    static abstract class c<Params, Result> implements Callable<Result> {
        Params[] b;

        private c() {
        }

        /* synthetic */ c(byte b2) {
            this();
        }
    }

    /* access modifiers changed from: protected */
    public abstract Result a();

    /* access modifiers changed from: protected */
    public void a(Result result) {
    }

    /* access modifiers changed from: protected */
    public void a(Progress... progressArr) {
    }

    /* access modifiers changed from: protected */
    public void b() {
    }

    /* access modifiers changed from: protected */
    public abstract ThreadPoolExecutor c();

    public final Status getStatus() {
        return this.a;
    }

    public final boolean isCancelled() {
        return this.d.isCancelled();
    }

    public final boolean cancel(boolean z) {
        return this.d.cancel(z);
    }

    public final LDNetAsyncTaskEx<Params, Progress, Result> execute(Params... paramsArr) {
        if (this.a != Status.PENDING) {
            switch (this.a) {
                case RUNNING:
                    throw new IllegalStateException("Cannot execute task: the task is already running.");
                case FINISHED:
                    throw new IllegalStateException("Cannot execute task: the task has already been executed (a task can be executed only once)");
            }
        }
        this.a = Status.RUNNING;
        this.c.b = paramsArr;
        ThreadPoolExecutor c2 = c();
        if (c2 == null) {
            return null;
        }
        c2.execute(this.d);
        return this;
    }

    /* access modifiers changed from: protected */
    public final void b(Progress... progressArr) {
        b.obtainMessage(2, new a(this, progressArr)).sendToTarget();
    }

    /* access modifiers changed from: protected */
    public final void b(Result result) {
        if (isCancelled()) {
            result = null;
        }
        a(result);
        this.a = Status.FINISHED;
    }
}
