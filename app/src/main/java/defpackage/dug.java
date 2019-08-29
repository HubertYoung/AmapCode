package defpackage;

import com.amap.bundle.aosservice.request.AosRequest;
import com.autonavi.bundle.routecommon.model.IRouteBusLineResult;
import com.autonavi.common.Callback;
import com.autonavi.minimap.route.bus.busline.impl.BusLineRequestImpl$1;
import com.autonavi.minimap.route.bus.busline.impl.BusLineRequestImpl$2;
import com.autonavi.minimap.route.bus.inter.IBusLineSearchResult;
import com.autonavi.minimap.route.bus.inter.impl.BusLineSearch;

/* renamed from: dug reason: default package */
/* compiled from: BusLineRequestImpl */
public final class dug implements ata {

    /* renamed from: dug$a */
    /* compiled from: BusLineRequestImpl */
    static class a {
        static dug a = new dug();
    }

    public final AosRequest a(String str, String str2, Callback<IRouteBusLineResult> callback) {
        return BusLineSearch.a(str, 1, str2, (Callback<IBusLineSearchResult>) new BusLineRequestImpl$1<IBusLineSearchResult>(this, callback));
    }

    public final AosRequest b(String str, String str2, Callback<IRouteBusLineResult> callback) {
        return BusLineSearch.a(str, str2, (Callback<IBusLineSearchResult>) new BusLineRequestImpl$2<IBusLineSearchResult>(this, callback), true);
    }
}
