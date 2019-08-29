package defpackage;

import android.content.Context;
import defpackage.sw;

/* renamed from: su reason: default package */
/* compiled from: DriveBaseModel */
public abstract class su<Presenter extends sw> {
    public Presenter a;
    protected Context b;

    public su(Presenter presenter) {
        if (presenter == null) {
            throw new IllegalArgumentException("presenter cannot be null");
        }
        this.a = presenter;
    }

    public final void a(Context context) {
        this.b = context;
    }

    public final Context a() {
        return this.b;
    }
}
