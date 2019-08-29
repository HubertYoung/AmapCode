package defpackage;

import com.autonavi.annotation.BundleInterface;
import com.autonavi.bundle.realtimebus.api.IRTPage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.route.bus.realtimebus.model.BusEndPointData;
import java.util.List;

@BundleInterface(awv.class)
/* renamed from: dyf reason: default package */
/* compiled from: RealTimeBusService */
public class dyf extends esi implements awv {
    public final void a(BusEndPointData busEndPointData) {
        dyv.a(busEndPointData);
    }

    public final List<btd> a(String str) {
        AMapPageUtil.getAppContext();
        return bso.a().c(str);
    }

    public final IRTPage a() {
        return a.a;
    }

    public final awu b() {
        return a.a;
    }
}
