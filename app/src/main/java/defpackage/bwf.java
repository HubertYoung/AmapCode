package defpackage;

import defpackage.bwe;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/* renamed from: bwf reason: default package */
/* compiled from: Store */
public final class bwf<T extends bwe> {
    public T a;
    public T b;
    public bwd<T> c;
    List<a> d = new Vector();

    /* renamed from: bwf$a */
    /* compiled from: Store */
    public class a implements bwg {
        bwc<T> a;
        final /* synthetic */ bwf b;
        private bwb c = null;

        /* JADX WARNING: type inference failed for: r1v0, types: [bwf, bwc<T>] */
        /* JADX WARNING: type inference failed for: r2v0, types: [bwc<T>, bwb] */
        /* JADX WARNING: Unknown variable types count: 2 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public a(defpackage.bwc<T> r1, defpackage.bwb r2) {
            /*
                r0 = this;
                r0.b = r1
                r0.<init>()
                r0.a = r2
                r2 = 0
                r0.c = r2
                java.util.List<bwf$a<>> r1 = r1.d
                r1.add(r0)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: defpackage.bwf.a.<init>(bwf, bwc):void");
        }

        public final void a() {
            this.b.d.remove(this);
        }

        public final boolean b() {
            return this.b.d.contains(this);
        }
    }

    public final void a(bwa bwa) {
        T t;
        if (bwa == null) {
            throw new IllegalArgumentException("action is null");
        }
        bwh.a();
        T t2 = this.a;
        try {
            t = this.c.a(t2, bwa);
        } catch (Throwable unused) {
            a();
            t = t2;
        }
        if (t == null) {
            throw new IllegalArgumentException("reduce error: the state reduce method returned is null");
        } else if (this.a != t) {
            this.b = this.a;
            this.a = t;
            a(this.a, this.b);
        }
    }

    public bwf(T t) {
        this.a = t;
        this.b = t;
    }

    private void a(T t, T t2) {
        for (a next : this.d) {
            if (next.a != null) {
                next.a.a(t, t2);
            }
        }
    }

    private void a() {
        Iterator<a> it = this.d.iterator();
        while (it.hasNext()) {
            it.next();
        }
    }
}
