package defpackage;

import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import com.amap.api.service.IndoorLocationProvider;
import com.autonavi.bundle.routecommon.inter.IRouteUI;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.sdk.location.LocationInstrument;

/* renamed from: eal reason: default package */
/* compiled from: RoutePlanningUtil */
public final class eal {
    public static boolean a(POI poi) {
        return poi != null && !TextUtils.isEmpty(poi.getName());
    }

    public static boolean a(IRouteUI iRouteUI, RouteType routeType) {
        return iRouteUI != null && iRouteUI.g() == routeType;
    }

    public static void a(IRouteUI iRouteUI, PageBundle pageBundle) {
        if (pageBundle != null) {
            String string = pageBundle.getString("bundle_key_end_poi_name_passed_in", "");
            if (!TextUtils.isEmpty(string)) {
                iRouteUI.a(string);
            }
        }
    }

    public static POI a(ResultType resultType, PageBundle pageBundle) {
        if (ResultType.OK != resultType || pageBundle == null || !pageBundle.containsKey("result_poi")) {
            return null;
        }
        return (POI) pageBundle.getObject("result_poi");
    }

    public static boolean a(IRouteUI iRouteUI) {
        if (iRouteUI.d() == null || TextUtils.isEmpty(iRouteUI.d().getName()) || iRouteUI.f() == null || TextUtils.isEmpty(iRouteUI.f().getName())) {
            return false;
        }
        return true;
    }

    public static void b(POI poi) {
        if (poi != null && TextUtils.equals(poi.getName(), "我的位置")) {
            GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition(5);
            if (latestPosition != null) {
                poi.setPoint(latestPosition);
            }
            Location latestLocation = LocationInstrument.getInstance().getLatestLocation();
            if (latestLocation == null || !latestLocation.getProvider().equals(IndoorLocationProvider.NAME)) {
                poi.setPid("");
                poi.setInoorFloorNoName("");
            } else {
                Bundle extras = latestLocation.getExtras();
                if (extras != null) {
                    String string = extras.getString(LocationInstrument.LOCATION_EXTRAS_KEY_POIID);
                    if (!TextUtils.isEmpty(string)) {
                        poi.setPid(string);
                    }
                    String string2 = extras.getString("floor");
                    if (!TextUtils.isEmpty(string2)) {
                        poi.setInoorFloorNoName(string2);
                    }
                }
            }
        }
    }

    public static boolean a(POI poi, POI poi2) {
        if (poi == null || TextUtils.isEmpty(poi.getName())) {
            return false;
        }
        GeoPoint point = poi.getPoint();
        GeoPoint point2 = poi2.getPoint();
        if (point.getLongitude() == point2.getLongitude() && point.getLatitude() == point2.getLatitude()) {
            return false;
        }
        if (TextUtils.isEmpty(poi.getId()) || TextUtils.isEmpty(poi2.getId()) || !poi.getId().equals(poi2.getId())) {
            return true;
        }
        return false;
    }
}
