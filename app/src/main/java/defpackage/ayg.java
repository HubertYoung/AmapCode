package defpackage;

import com.alibaba.fastjson.JSON;
import com.autonavi.common.model.POI;
import com.autonavi.jni.eyrie.amap.tbt.bus.BusServiceManager;
import com.autonavi.jni.eyrie.amap.tbt.bus.BusServiceObserver;
import com.autonavi.jni.eyrie.amap.tbt.bus.param.BusRouteRequestParam;
import com.autonavi.jni.eyrie.amap.tbt.bus.param.TaxiComparatorRequestParam;

/* renamed from: ayg reason: default package */
/* compiled from: BusCommuteEyrieManager */
public final class ayg {
    public aye a = new aye();
    public BusServiceObserver b;

    public final void a() {
        if (this.a != null) {
            BusServiceManager.getInstance().unregisterBusService(this.b);
        }
    }

    public static void a(int i, POI poi, POI poi2) {
        if (poi != null && poi2 != null) {
            double longitude = poi.getPoint().getLongitude();
            double latitude = poi.getPoint().getLatitude();
            double longitude2 = poi2.getPoint().getLongitude();
            double latitude2 = poi2.getPoint().getLatitude();
            BusRouteRequestParam busRouteRequestParam = new BusRouteRequestParam();
            busRouteRequestParam.ad1 = poi.getAdCode() == null ? "" : poi.getAdCode();
            busRouteRequestParam.ad2 = poi2.getAdCode() == null ? "" : poi2.getAdCode();
            busRouteRequestParam.poiid1 = poi.getId() == null ? "" : poi.getId();
            busRouteRequestParam.poiid2 = poi2.getId() == null ? "" : poi2.getId();
            StringBuilder sb = new StringBuilder();
            sb.append(System.currentTimeMillis());
            busRouteRequestParam.timestamp = sb.toString();
            busRouteRequestParam.x1 = longitude;
            busRouteRequestParam.x2 = longitude2;
            busRouteRequestParam.y1 = latitude;
            busRouteRequestParam.y2 = latitude2;
            busRouteRequestParam.poitype1 = poi.getType() == null ? "" : poi.getType();
            busRouteRequestParam.poitype2 = poi2.getType() == null ? "" : poi2.getType();
            busRouteRequestParam.poiext1 = poi.getEndPoiExtension() == null ? "" : poi.getEndPoiExtension();
            busRouteRequestParam.poiext2 = poi2.getEndPoiExtension() == null ? "" : poi2.getEndPoiExtension();
            busRouteRequestParam.req_cli = "1";
            busRouteRequestParam.eta = "9";
            busRouteRequestParam.server_ver = "0";
            StringBuilder sb2 = new StringBuilder("start request route = ");
            sb2.append(JSON.toJSONString(busRouteRequestParam));
            azb.a("song---", sb2.toString());
            aye.a(i, busRouteRequestParam);
        }
    }

    public static void a(POI poi, POI poi2) {
        if (poi != null && poi2 != null && poi.getPoint() != null && poi2.getPoint() != null) {
            TaxiComparatorRequestParam taxiComparatorRequestParam = new TaxiComparatorRequestParam();
            taxiComparatorRequestParam.start_name = poi.getName();
            taxiComparatorRequestParam.start_x = String.valueOf(poi.getPoint().getLongitude());
            taxiComparatorRequestParam.start_y = String.valueOf(poi.getPoint().getLatitude());
            taxiComparatorRequestParam.end_name = poi2.getName();
            taxiComparatorRequestParam.end_x = String.valueOf(poi2.getPoint().getLongitude());
            taxiComparatorRequestParam.end_y = String.valueOf(poi2.getPoint().getLatitude());
            taxiComparatorRequestParam.highway_cost = "0";
            taxiComparatorRequestParam.scenario = "1";
            taxiComparatorRequestParam.mode = "simple";
            aye.a(ayo.e, taxiComparatorRequestParam);
        }
    }

    public final void a(int i) {
        if (this.a != null) {
            BusServiceManager.getInstance().cancel(i);
        }
    }

    public final void b() {
        if (this.a != null) {
            BusServiceManager.getInstance().cancelAll();
        }
    }
}
