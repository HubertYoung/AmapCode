package defpackage;

import android.content.Context;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.route.bus.realtimebus.model.RTBusData;
import com.autonavi.minimap.route.bus.realtimebus.model.RealTimeBusStation;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* renamed from: dxr reason: default package */
/* compiled from: BusRadarDataManager */
public final class dxr {
    public Context a;
    public Map<String, RealTimeBusStation> b;
    public RTBusData c;

    /* renamed from: dxr$a */
    /* compiled from: BusRadarDataManager */
    public static class a implements Comparator<btd> {
        public final /* synthetic */ int compare(Object obj, Object obj2) {
            btd btd = (btd) obj;
            btd btd2 = (btd) obj2;
            float a = cfe.a(LocationInstrument.getInstance().getLatestPosition(), new GeoPoint(btd.station_lon.doubleValue(), btd.station_lat.doubleValue()));
            float a2 = cfe.a(LocationInstrument.getInstance().getLatestPosition(), new GeoPoint(btd2.station_lon.doubleValue(), btd2.station_lat.doubleValue()));
            if (a2 <= a || !btd2.isFollow || a2 > 200.0f) {
                return Float.compare(a2, a);
            }
            return Float.compare(a, a2);
        }
    }

    public dxr(Context context) {
        this.a = context;
    }

    public final void a() {
        this.c = new RTBusData();
    }

    public final void a(List<RealTimeBusStation> list) {
        Map<String, RealTimeBusStation> map;
        if (!dxx.a((Collection<T>) list)) {
            if (dxx.a((Collection<T>) list)) {
                map = null;
            } else {
                ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
                for (RealTimeBusStation next : list) {
                    RealTimeBusStation realTimeBusStation = new RealTimeBusStation(next.bus_id, next.bus_name, next.station_id, next.station_name, next.bus_describe, next.station_lon, next.station_lat);
                    next.isFollow = bso.a().a(next.bus_id, next.station_id);
                    realTimeBusStation.setIsFollow(next.isFollow);
                    realTimeBusStation.setPOIId(next.poiid1);
                    realTimeBusStation.adcode = next.adcode;
                    realTimeBusStation.is_realtime = next.is_realtime;
                    concurrentHashMap.put(realTimeBusStation.station_id, realTimeBusStation);
                }
                map = concurrentHashMap;
            }
            this.b = map;
        }
    }
}
