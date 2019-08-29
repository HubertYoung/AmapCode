package com.autonavi.bundle.amaphome.components.luban;

import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.SearchURLBuilder;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.minimap.map.DPoint;
import com.autonavi.sdk.location.LocationInstrument;

@Path(builder = SearchURLBuilder.class, sign = {"diu"}, url = "ws/transfer/auth/poi/searchwordinbox/?")
public class LuBanHotWordWrapper implements ParamEntity {
    public String user_loc = getLocateInfo();

    private String getLocateInfo() {
        GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
        if (latestPosition != null) {
            DPoint a = cfg.a((long) latestPosition.x, (long) latestPosition.y);
            StringBuilder sb = new StringBuilder();
            sb.append(a.x);
            sb.append(",");
            sb.append(a.y);
            return sb.toString();
        }
        bty mapView = DoNotUseTool.getMapManager().getMapView();
        if (mapView != null) {
            GeoPoint glGeoPoint2GeoPoint = GeoPoint.glGeoPoint2GeoPoint(mapView.n());
            if (glGeoPoint2GeoPoint != null) {
                DPoint a2 = cfg.a((long) glGeoPoint2GeoPoint.x, (long) glGeoPoint2GeoPoint.y);
                StringBuilder sb2 = new StringBuilder();
                sb2.append(a2.x);
                sb2.append(",");
                sb2.append(a2.y);
                return sb2.toString();
            }
        }
        return "";
    }
}
