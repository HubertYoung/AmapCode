package defpackage;

import android.os.Handler;
import java.lang.ref.WeakReference;

/* renamed from: ecs reason: default package */
/* compiled from: AmapHandler */
public abstract class ecs<T> extends Handler {
    private WeakReference<T> a = null;

    public ecs(T t) {
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
