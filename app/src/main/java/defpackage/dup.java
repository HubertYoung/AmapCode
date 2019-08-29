package defpackage;

import android.text.TextUtils;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.autonavi.minimap.route.bus.busline.newmodel.RouteRealTimeRequestNewModel$1;
import com.autonavi.minimap.route.bus.model.Bus;
import com.autonavi.minimap.route.net.base.req.BusBaseReq;
import com.autonavi.minimap.route.net.module.RTBusLocation;
import java.util.HashMap;

/* renamed from: dup reason: default package */
/* compiled from: RouteRealTimeRequestNewModel */
public final class dup {
    public HashMap<String, RTBusLocation> a;
    public a b = this;
    public int c = 0;
    public boolean d = false;
    private final String e;
    private edh f;
    private Bus g;
    private String h;
    private AosResponseCallback i = new RouteRealTimeRequestNewModel$1(this);

    /* renamed from: dup$a */
    /* compiled from: RouteRealTimeRequestNewModel */
    public interface a {
        void b();
    }

    public dup(String str) {
        this.e = str;
        this.a = new HashMap<>();
    }

    public final void a(Bus bus, String str, boolean z) {
        this.g = bus;
        this.h = str;
        a(bus, str, z, -1);
    }

    private void a(Bus bus, String str, boolean z, int i2) {
        a();
        if (bus != null && !TextUtils.isEmpty(str)) {
            this.d = z;
            BusBaseReq a2 = edj.a(bus, str, this.e, z);
            if (i2 > 0) {
                this.f = new edh(a2, this.i, i2);
            } else {
                this.f = new edh(a2, this.i);
            }
            this.f.a();
        }
    }

    public final void a(int i2) {
        a(this.g, this.h, this.d, i2);
    }

    public final void a() {
        if (this.f != null) {
            this.f.b();
            this.f = null;
        }
    }
}
