package defpackage;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.autonavi.minimap.route.net.base.req.BusBaseReq;

/* renamed from: edg reason: default package */
/* compiled from: BaseNetTask */
public class edg {
    boolean a = false;
    Runnable b = new Runnable() {
        public final void run() {
            if (edg.this.a) {
                ebr.a(false).removeCallbacks(edg.this.b);
            } else {
                edg.this.c();
            }
        }
    };
    private boolean c = false;
    private BusBaseReq d;
    private AosResponseCallback e;
    private final int f;

    public edg(BusBaseReq busBaseReq, AosResponseCallback aosResponseCallback, int i) {
        this.d = busBaseReq;
        this.e = aosResponseCallback;
        this.f = i;
    }

    public final synchronized void a() {
        if (!this.c) {
            this.c = true;
            this.a = false;
            c();
        }
    }

    public final synchronized void b() {
        this.a = true;
        this.e = null;
        if (this.d != null) {
            BusBaseReq busBaseReq = this.d;
            yq.a();
            yq.a((AosRequest) busBaseReq);
            this.d = null;
        }
        if (this.f > 0) {
            ebr.a(false).removeCallbacks(this.b);
        }
    }

    /* access modifiers changed from: 0000 */
    public final synchronized void c() {
        if (!this.a) {
            yq.a();
            yq.a((AosRequest) this.d, this.e);
            if (this.f > 0) {
                ebr.a(false).removeCallbacks(this.b);
                ebr.a(false).postDelayed(this.b, (long) this.f);
            }
        }
    }
}
