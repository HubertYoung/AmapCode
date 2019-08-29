package com.alipay.deviceid.module.rpc.mrpc.core;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

final class m extends FutureTask<u> {
    final /* synthetic */ q a;
    final /* synthetic */ l b;

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    m(l lVar, Callable callable, q qVar) {
        // this.b = lVar;
        // this.a = qVar;
        super(callable);
    }

    /* access modifiers changed from: protected */
    public final void done() {
        o a2 = this.a.a();
        if (a2.f() == null) {
            super.done();
            return;
        }
        try {
            get();
            if (isCancelled() || a2.h()) {
                a2.g();
                if (!isCancelled() || !isDone()) {
                    cancel(false);
                }
            }
        } catch (InterruptedException e) {
            new StringBuilder().append(e);
        } catch (ExecutionException e2) {
            if (e2.getCause() == null || !(e2.getCause() instanceof HttpException)) {
                new StringBuilder().append(e2);
                return;
            }
            HttpException httpException = (HttpException) e2.getCause();
            httpException.getCode();
            httpException.getMsg();
        } catch (CancellationException unused) {
            a2.g();
        } catch (Throwable th) {
            throw new RuntimeException("An error occured while executing http request", th);
        }
    }
}
