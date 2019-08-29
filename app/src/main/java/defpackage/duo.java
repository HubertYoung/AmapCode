package defpackage;

import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.map.DPoint;
import com.autonavi.minimap.route.bus.model.Bus;
import com.autonavi.sdk.location.LocationInstrument;

/* renamed from: duo reason: default package */
/* compiled from: RouteNearStationModel */
public final class duo {
    public String a = "";
    public boolean b = true;
    public a c = this;

    /* renamed from: duo$a */
    /* compiled from: RouteNearStationModel */
    public interface a {
        void a(int i, String str);
    }

    public final void a(final Bus bus) {
        ahm.a(new Runnable() {
            public final void run() {
                float f;
                float f2;
                int i;
                if (bus == null || !(bus.type == 2 || bus.type == 3 || bus.type == 10)) {
                    f2 = 1000.0f;
                    f = 1001.0f;
                } else {
                    f2 = 20000.0f;
                    f = 20001.0f;
                }
                if (LocationInstrument.getInstance().getLatestPosition(5) != null) {
                    GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
                    DPoint a2 = cfg.a((long) latestPosition.x, (long) latestPosition.y);
                    int i2 = 0;
                    if (bus == null || bus.stationX == null) {
                        i = 0;
                    } else {
                        float f3 = f;
                        i = 0;
                        while (i2 < bus.stationX.length) {
                            if (duo.this.b) {
                                DPoint a3 = cfg.a((long) bus.stationX[i2], (long) bus.stationY[i2]);
                                float a4 = cfe.a(a2.y, a2.x, a3.y, a3.x);
                                if (a4 < f2 && a4 < f3) {
                                    duo.this.a = bus.stations[i2];
                                    i = i2;
                                    f3 = a4;
                                }
                                i2++;
                            } else {
                                return;
                            }
                        }
                    }
                    if (duo.this.c != null) {
                        duo.this.c.a(i, duo.this.a);
                    }
                }
            }
        });
    }
}
