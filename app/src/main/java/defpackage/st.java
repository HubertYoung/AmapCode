package defpackage;

import android.content.Context;
import defpackage.sv;

/* renamed from: st reason: default package */
/* compiled from: DriveBaseMapModel */
public abstract class st<Presenter extends sv> {
    public Presenter b;
    protected Context c;

    public st(Presenter presenter) {
        if (presenter == null) {
            throw new IllegalArgumentException("presenter cannot be null");
        }
        this.b = presenter;
    }

    public final void a(Context context) {
        this.c = context;
    }
}
