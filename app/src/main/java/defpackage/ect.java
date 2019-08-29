package defpackage;

import java.lang.ref.WeakReference;

/* renamed from: ect reason: default package */
/* compiled from: AmapRunnable */
public abstract class ect<T> implements Runnable {
    private WeakReference<T> a = null;

    public ect(T t) {
        if (t != null) {
            this.a = new WeakReference<>(t);
        }
    }

    /* access modifiers changed from: protected */
    public final T a() {
        if (this.a != null) {
            return this.a.get();
        }
        return null;
    }
}
