package defpackage;

import java.util.concurrent.Future;

/* renamed from: ax reason: default package */
/* compiled from: FutureCancelable */
public final class ax implements aw {
    public static final ax a = new ax(null, null);
    private final Future<?> b;
    private final String c;

    public ax(Future<?> future, String str) {
        this.b = future;
        this.c = str;
    }

    public final void a() {
        if (this.b != null) {
            cl.b("awcn.FutureCancelable", "cancel request", this.c, new Object[0]);
            this.b.cancel(true);
        }
    }
}
