package defpackage;

import com.autonavi.common.model.POI;
import com.autonavi.jni.eyrie.amap.tbt.bus.BusRequestType;
import com.autonavi.jni.eyrie.amap.tbt.bus.BusServiceManager;
import com.autonavi.jni.eyrie.amap.tbt.bus.response.BusPath;
import com.autonavi.jni.eyrie.amap.tbt.bus.response.BusPath.BusSegment;
import com.autonavi.jni.eyrie.amap.tbt.bus.response.BusRealTimeResponse;
import com.autonavi.jni.eyrie.amap.tbt.bus.response.BusRouteResponse;
import com.autonavi.jni.eyrie.amap.tbt.bus.response.TaxiComparatorResponse;
import java.util.ArrayList;

/* renamed from: ayi reason: default package */
/* compiled from: BusCommuteResult */
public class ayi {
    private static ayi h;
    public int a = 0;
    public int b = -1;
    public BusRouteResponse c;
    public TaxiComparatorResponse d;
    public BusRealTimeResponse e;
    public POI f;
    public POI g;

    public static ayi a() {
        synchronized (ayi.class) {
            try {
                if (h == null) {
                    h = new ayi();
                }
            }
        }
        return h;
    }

    public final void a(int i, String str) {
        if (i == BusRequestType.BusRequestTypeRoute.ordinal()) {
            this.c = BusServiceManager.getInstance().parseBusRoute(str);
        } else if (i == BusRequestType.BusRequestTypeTaxiComparator.ordinal()) {
            this.d = BusServiceManager.getInstance().parseTaxiComparator(str);
        } else {
            if (i == BusRequestType.BusRequestTypeRealTime.ordinal()) {
                this.e = BusServiceManager.getInstance().parseRealTime(str);
            }
        }
    }

    public final BusPath b() {
        if (this.c == null || this.c.buslist == null || this.c.buslist.size() <= 0 || this.a < 0 || this.a >= this.c.buslist.size()) {
            return null;
        }
        return this.c.buslist.get(this.a);
    }

    public final BusSegment c() {
        BusPath b2 = b();
        if (b2 == null) {
            return null;
        }
        ArrayList<BusSegment> arrayList = b2.segmentlist;
        if (arrayList == null || arrayList.isEmpty()) {
            return null;
        }
        return arrayList.get(0);
    }

    public final void d() {
        this.c = null;
        this.e = null;
        this.a = 0;
        this.b = -1;
    }
}
