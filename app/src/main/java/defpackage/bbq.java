package defpackage;

import com.amap.bundle.aosservice.request.AosRequest;
import com.autonavi.common.Callback.a;

/* renamed from: bbq reason: default package */
/* compiled from: NetWorkCancel */
public class bbq implements bbm {
    private a a;
    private adx b;
    private AosRequest c;

    public bbq(a aVar) {
        this.a = aVar;
    }

    public bbq(adx adx) {
        this.b = adx;
    }

    public void a() {
        if (this.a != null) {
            this.a.cancel();
        }
        if (this.b != null) {
            this.b.a();
        }
        if (this.c != null) {
            in.a().a(this.c);
        }
    }

    public boolean b() {
        if (this.a != null) {
            return this.a.isCancelled();
        }
        if (this.b != null) {
            return this.b.b();
        }
        if (this.c != null) {
            return this.c.isCanceled();
        }
        return false;
    }
}
