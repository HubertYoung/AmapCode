package defpackage;

import com.autonavi.minimap.route.bus.model.Bus;
import com.autonavi.minimap.route.bus.realtimebus.model.RealTimeBusline;

/* renamed from: dyh reason: default package */
/* compiled from: RTDetailData */
public final class dyh {
    public Bus a;
    public int b;
    public String c;
    public String d;
    public String e;
    public int f;
    public boolean g;
    public RealTimeBusline h;

    public dyh(Bus bus, int i) {
        this.a = bus;
        this.b = i;
        this.c = this.a.stations[i];
        this.d = this.a.stationIds[i];
        this.e = this.a.stationpoiid1[i];
        this.f = this.a.stationstatus[i];
    }
}
