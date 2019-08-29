package defpackage;

import com.autonavi.common.model.GeoPoint;

/* renamed from: dyu reason: default package */
/* compiled from: OverlayItemAngleUtil */
public final class dyu {
    public GeoPoint a;
    public GeoPoint[] b;

    public dyu(GeoPoint geoPoint, GeoPoint[] geoPointArr) {
        this.a = geoPoint;
        this.b = geoPointArr;
    }

    public static double a(GeoPoint geoPoint, GeoPoint geoPoint2) {
        double d = (double) (geoPoint.x - geoPoint2.x);
        double d2 = (double) (geoPoint.y - geoPoint2.y);
        return Math.sqrt((d * d) + (d2 * d2));
    }
}
