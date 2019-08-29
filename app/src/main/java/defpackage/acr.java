package defpackage;

import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.autonavi.bundle.routecommon.model.RouteType;

/* renamed from: acr reason: default package */
/* compiled from: RoutePlanTypeRecord */
public final class acr {
    public static RouteType a() {
        switch (new MapSharePreference(SharePreferenceName.user_route_method_info).getIntValue("last_route_type_900", RouteType.CAR.getValue())) {
            case 0:
                return RouteType.CAR;
            case 1:
                return RouteType.BUS;
            case 2:
                return RouteType.ONFOOT;
            case 3:
                return RouteType.RIDE;
            case 4:
                return RouteType.TRAIN;
            case 5:
                return RouteType.COACH;
            case 6:
                return RouteType.TAXI;
            case 7:
                return RouteType.TRUCK;
            case 8:
                return RouteType.ETRIP;
            case 9:
                return RouteType.FREERIDE;
            case 10:
                return RouteType.AIRTICKET;
            case 11:
                return RouteType.MOTOR;
            default:
                return RouteType.CAR;
        }
    }

    public static void a(RouteType routeType) {
        new MapSharePreference(SharePreferenceName.user_route_method_info).putIntValue("last_route_type_900", routeType.getValue());
    }
}
