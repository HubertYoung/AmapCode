package defpackage;

import android.support.annotation.NonNull;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;

/* renamed from: agk reason: default package */
/* compiled from: WeakArrayList */
public final class agk<E> implements Iterable<E> {
    public ArrayList<WeakReference<E>> a = new ArrayList<>();
    int b = 0;

    /* renamed from: agk$a */
    /* compiled from: WeakArrayList */
    static class a<E> implements Iterator<E> {
        Iterator<WeakReference<E>> a;
        agk<E> b;

        public a(agk<E> agk) {
            this.a = agk.a.iterator();
            this.b = agk;
        }

        public final boolean hasNext() {
            return this.a.hasNext();
        }

        public final E next() {
            return this.a.next().get();
        }

        public final void remove() {
            this.b.b++;
            this.a.remove();
        }
    }

    public final boolean a(E e) {
        b();
        if (e == null) {
            return false;
        }
        this.b++;
        return this.a.add(new WeakReference(e));
    }

    @NonNull
    public final Iterator<E> iterator() {
        b();
        return new a(this);
    }

    public final int a() {
        b();
        return this.a.size();
    }

    public final void b() {
        Iterator<WeakReference<E>> it = this.a.iterator();
        if (it != null) {
            while (it.hasNext()) {
                WeakReference next = it.next();
                if (next == null || next.get() == null) {
                    it.remove();
                }
            }
        }
    }
}
