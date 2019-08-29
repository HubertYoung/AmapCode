package defpackage;

import android.os.Looper;

/* renamed from: elp reason: default package */
/* compiled from: RequestCallbackDecorator */
class elp<T> implements bbs<T> {
    bbs<T> a;

    elp(bbs<T> bbs) {
        this.a = bbs;
    }

    public void a(final T t) {
        if (this.a != null) {
            if (!(this.a instanceof elq) || Looper.getMainLooper().getThread() == Thread.currentThread()) {
                this.a.a(t);
            } else {
                aho.a(new Runnable() {
                    public final void run() {
                        elp.this.a.a(t);
                    }
                });
            }
        }
    }

    public void a(final int i) {
        if (this.a != null) {
            if (!(this.a instanceof elq) || Looper.getMainLooper().getThread() == Thread.currentThread()) {
                this.a.a(i);
            } else {
                aho.a(new Runnable() {
                    public final void run() {
                        elp.this.a.a(i);
                    }
                });
            }
        }
    }
}
