package defpackage;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* renamed from: cj reason: default package */
/* compiled from: PriorityExecutor */
final class cj extends ThreadPoolExecutor {

    /* renamed from: cj$a */
    /* compiled from: PriorityExecutor */
    class a<V> extends FutureTask<V> implements Comparable<a<V>> {
        private Object b;

        public final /* synthetic */ int compareTo(Object obj) {
            a aVar = (a) obj;
            if (this != aVar) {
                if (aVar == null) {
                    return -1;
                }
                if (this.b != null && aVar.b != null && this.b.getClass().equals(aVar.b.getClass()) && (this.b instanceof Comparable)) {
                    return ((Comparable) this.b).compareTo(aVar.b);
                }
            }
            return 0;
        }

        public a(Callable<V> callable) {
            super(callable);
            this.b = callable;
        }

        public a(Runnable runnable, V v) {
            super(runnable, v);
            this.b = runnable;
        }
    }

    public cj(TimeUnit timeUnit, BlockingQueue<Runnable> blockingQueue, ThreadFactory threadFactory) {
        super(16, 16, 60, timeUnit, blockingQueue, threadFactory);
    }

    /* access modifiers changed from: protected */
    public final <T> RunnableFuture<T> newTaskFor(Runnable runnable, T t) {
        return new a(runnable, t);
    }

    /* access modifiers changed from: protected */
    public final <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
        return new a(callable);
    }
}
