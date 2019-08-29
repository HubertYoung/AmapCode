package com.autonavi.minimap.route.bus.realtimebus.net.param;

import android.text.TextUtils;
import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.route.bus.realtimebus.model.BusEndPointData;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
@Path(builder = AosURLBuilder.class, host = "aos_url", sign = {"channel", "diu", "div", "_aosmd5"}, url = "/ws/shield/maps/mapapi/realtimebus/recommend/?")
public class RealTimeBusRecommendParam implements ParamEntity {
    private static final String TAG = "com.autonavi.minimap.route.bus.realtimebus.net.param.RealTimeBusRecommendParam";
    public String company_id = "";
    public String company_xy = "";
    public String home_id = "";
    public String home_xy = "";
    public String last_station_id = "";
    public String last_station_xy = "";
    public String lat = "";
    public String lon = "";
    public String nearest_col_station = "";

    public RealTimeBusRecommendParam() {
        String[] g = dyv.g();
        if (g != null) {
            this.lon = g[0];
            this.lat = g[1];
            String str = TAG;
            StringBuilder sb = new StringBuilder("当前经纬度：");
            sb.append(this.lon);
            sb.append(",");
            sb.append(this.lat);
            eao.b(str, sb.toString());
        }
        BusEndPointData c = dyv.c();
        if (c != null && !c.isBeyond2Hours()) {
            this.last_station_id = c.getLastStationID();
            this.last_station_xy = c.getLastStationLonLat();
            String str2 = TAG;
            StringBuilder sb2 = new StringBuilder("公交规划终点：");
            sb2.append(this.last_station_id);
            sb2.append(",");
            sb2.append(this.last_station_xy);
            eao.b(str2, sb2.toString());
        }
        btd f = dyv.f();
        if (f != null && !TextUtils.isEmpty(f.poiid1)) {
            this.nearest_col_station = f.poiid1;
            String str3 = TAG;
            StringBuilder sb3 = new StringBuilder("离当前位置最近：");
            sb3.append(this.nearest_col_station);
            eao.b(str3, sb3.toString());
        }
        POI d = dyv.d();
        if (d != null) {
            this.home_id = d.getId();
            StringBuilder sb4 = new StringBuilder();
            sb4.append(d.getPoint().getLongitude());
            sb4.append(",");
            sb4.append(d.getPoint().getLatitude());
            this.home_xy = sb4.toString();
            String str4 = TAG;
            StringBuilder sb5 = new StringBuilder("家的点：");
            sb5.append(this.home_id);
            sb5.append(",");
            sb5.append(this.home_xy);
            eao.b(str4, sb5.toString());
        }
        POI e = dyv.e();
        if (e != null) {
            this.company_id = e.getId();
            StringBuilder sb6 = new StringBuilder();
            sb6.append(e.getPoint().getLongitude());
            sb6.append(",");
            sb6.append(e.getPoint().getLatitude());
            this.company_xy = sb6.toString();
            String str5 = TAG;
            StringBuilder sb7 = new StringBuilder("公司的点：");
            sb7.append(this.company_id);
            sb7.append(",");
            sb7.append(this.company_xy);
            eao.b(str5, sb7.toString());
        }
    }
}
