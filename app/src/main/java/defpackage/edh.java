package defpackage;

import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.autonavi.minimap.route.net.base.req.BusBaseReq;

/* renamed from: edh reason: default package */
/* compiled from: RTBusNetTask */
public final class edh extends edg {
    public edh(BusBaseReq busBaseReq, AosResponseCallback aosResponseCallback) {
        this(busBaseReq, aosResponseCallback, 30000);
    }

    public edh(BusBaseReq busBaseReq, AosResponseCallback aosResponseCallback, int i) {
        super(busBaseReq, aosResponseCallback, i);
    }
}
