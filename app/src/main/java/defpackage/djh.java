package defpackage;

import android.os.Handler;
import java.lang.ref.WeakReference;

/* renamed from: djh reason: default package */
/* compiled from: AmapHandler */
public abstract class djh<T> extends Handler {
    private WeakReference<T> a = null;

    public djh(T t) {
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
