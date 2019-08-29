package defpackage;

import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.autonavi.annotation.BundleInterface;
import com.autonavi.bundle.busnavi.api.IBusNaviPage;
import com.autonavi.minimap.route.bus.localbus.RouteBusResultData;
import com.autonavi.minimap.route.export.model.IRouteResultData;

@BundleInterface(atb.class)
/* renamed from: dve reason: default package */
/* compiled from: BusNaviService */
public class dve extends esi implements atb {
    public final IRouteResultData d() {
        return new RouteBusResultData();
    }

    public final String f() {
        return new MapSharePreference(SharePreferenceName.user_route_method_info).getStringValue("bus_method", "0");
    }

    public final long g() {
        return dwk.a();
    }

    public final IBusNaviPage a() {
        return a.a;
    }

    public final ate b() {
        return a.a;
    }

    public final atc c() {
        return a.a;
    }

    public final atd e() {
        return a.a;
    }
}
