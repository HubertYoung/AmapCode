package defpackage;

import com.autonavi.jni.eyrie.amap.tbt.bus.BusServiceManager;
import com.autonavi.jni.eyrie.amap.tbt.bus.param.BusRouteRequestParam;
import com.autonavi.jni.eyrie.amap.tbt.bus.param.TaxiComparatorRequestParam;

/* renamed from: aye reason: default package */
/* compiled from: BusCommuteEyrieImpl */
public final class aye {
    public boolean a = bno.c;

    public static void a(int i, BusRouteRequestParam busRouteRequestParam) {
        BusServiceManager.getInstance().requestBusRoute(i, busRouteRequestParam);
    }

    public static void a(int i, TaxiComparatorRequestParam taxiComparatorRequestParam) {
        BusServiceManager.getInstance().requestTaxiComparator(i, taxiComparatorRequestParam);
    }
}
