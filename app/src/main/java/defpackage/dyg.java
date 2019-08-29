package defpackage;

import com.autonavi.minimap.route.bus.model.Bus;
import com.autonavi.minimap.route.bus.realtimebus.model.RealtimeBuses.RealtimeBus;
import com.autonavi.minimap.route.bus.realtimebus.model.RecommenStationLines;
import java.util.Collection;
import java.util.List;

/* renamed from: dyg reason: default package */
/* compiled from: BusLineListData */
public final class dyg {
    public List<RecommenStationLines> a;
    public int b;
    public List<RealtimeBus> c;
    public Bus d;

    public final RecommenStationLines a() {
        this.b = this.b < 0 ? 0 : this.b;
        if (dxx.a((Collection<T>) this.a) || this.b < 0 || this.b >= this.a.size()) {
            return null;
        }
        return this.a.get(this.b);
    }
}
