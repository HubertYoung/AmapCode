package defpackage;

/* renamed from: cwh reason: default package */
/* compiled from: Singleton */
public abstract class cwh<T> {
    private T a;

    /* access modifiers changed from: protected */
    public abstract T a();

    public final T b() {
        T t;
        synchronized (this) {
            try {
                if (this.a == null) {
                    this.a = a();
                }
                t = this.a;
            }
        }
        return t;
    }
}
