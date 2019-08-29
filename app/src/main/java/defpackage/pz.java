package defpackage;

import com.alipay.mobile.beehive.util.MiscUtil;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;

/* renamed from: pz reason: default package */
/* compiled from: RouteResultUtils */
public final class pz {
    private static final GeoPoint a = new GeoPoint(0, 0);

    public static String a(POI poi) {
        if (poi == null) {
            return MiscUtil.NULL_STR;
        }
        GeoPoint point = poi.getPoint();
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder("[name:");
        sb2.append(String.valueOf(poi.getName()));
        sb.append(sb2.toString());
        if (point == null) {
            sb.append(",point:NULL");
            sb.append("]");
            return sb.toString();
        }
        StringBuilder sb3 = new StringBuilder(",lat:");
        sb3.append(point.getLatitude());
        sb3.append(",lon:");
        sb3.append(point.getLongitude());
        sb.append(sb3.toString());
        sb.append("]");
        return sb.toString();
    }
}
