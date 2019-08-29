package defpackage;

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;

/* renamed from: bqb reason: default package */
/* compiled from: ThreadPoolWaitQueue */
final class bqb {
    final PriorityBlockingQueue<c> a = new PriorityBlockingQueue<>(5, new Comparator<c>() {
        public final /* bridge */ /* synthetic */ int compare(Object obj, Object obj2) {
            c cVar = (c) obj;
            c cVar2 = (c) obj2;
            int i = cVar2.b - cVar.b;
            return i == 0 ? cVar.c - cVar2.c : i;
        }
    });

    bqb() {
    }

    public final c a() {
        c cVar;
        if (this.a.size() <= 0) {
            return null;
        }
        try {
            cVar = this.a.take();
        } catch (InterruptedException unused) {
            cVar = null;
        }
        return cVar;
    }

    public final c b() {
        if (this.a.size() <= 0) {
            return null;
        }
        return (c) this.a.element();
    }
}
