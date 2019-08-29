package defpackage;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* renamed from: ej reason: default package */
/* compiled from: FutureResponse */
final class ej implements Future<dk> {
    private en a;
    private boolean b;

    public ej(en enVar) {
        this.a = enVar;
    }

    public final boolean cancel(boolean z) {
        if (!this.b) {
            this.a.b();
            this.b = true;
        }
        return true;
    }

    public final boolean isCancelled() {
        return this.b;
    }

    public final boolean isDone() {
        throw new RuntimeException("NOT SUPPORT!");
    }

    public final /* synthetic */ Object get(long j, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        throw new RuntimeException("NOT SUPPORT!");
    }

    public final /* synthetic */ Object get() throws InterruptedException, ExecutionException {
        throw new RuntimeException("NOT SUPPORT!");
    }
}
