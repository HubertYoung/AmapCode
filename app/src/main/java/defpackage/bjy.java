package defpackage;

import com.autonavi.common.imageloader.ImageLoader.Priority;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* renamed from: bjy reason: default package */
/* compiled from: PicassoExecutorService */
public final class bjy extends ThreadPoolExecutor {

    /* renamed from: bjy$a */
    /* compiled from: PicassoExecutorService */
    static final class a extends FutureTask<bjk> implements Comparable<a> {
        private final bjk a;

        public final /* synthetic */ int compareTo(Object obj) {
            a aVar = (a) obj;
            Priority priority = this.a.s;
            Priority priority2 = aVar.a.s;
            return priority == priority2 ? this.a.a - aVar.a.a : priority2.ordinal() - priority.ordinal();
        }

        public a(bjk bjk) {
            super(bjk, null);
            this.a = bjk;
        }
    }

    public bjy() {
        super(3, 3, 0, TimeUnit.MILLISECONDS, new PriorityBlockingQueue(), new b());
    }

    public final void a(int i) {
        setCorePoolSize(i);
        setMaximumPoolSize(i);
    }

    public final Future<?> submit(Runnable runnable) {
        a aVar = new a((bjk) runnable);
        execute(aVar);
        return aVar;
    }
}
