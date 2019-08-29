package defpackage;

import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

/* renamed from: em reason: default package */
/* compiled from: RequestContext */
final class em {
    public final dy a;
    public eb b;
    public final String c;
    public volatile AtomicBoolean d = new AtomicBoolean();
    public volatile ek e = null;
    public volatile Future f = null;

    public em(dy dyVar, eb ebVar) {
        this.a = dyVar;
        this.c = dyVar.i;
        this.b = ebVar;
    }

    public final void a() {
        Future future = this.f;
        if (future != null) {
            future.cancel(true);
            this.f = null;
        }
    }

    public final void b() {
        if (this.e != null) {
            this.e.a();
            this.e = null;
        }
    }
}
