package defpackage;

import com.autonavi.common.model.GeoPoint;

/* renamed from: djn reason: default package */
/* compiled from: LineUtil */
public final class djn {
    static GeoPoint a(GeoPoint geoPoint, GeoPoint geoPoint2, GeoPoint geoPoint3) {
        GeoPoint geoPoint4;
        if (geoPoint.x == geoPoint2.x) {
            geoPoint4 = new GeoPoint(geoPoint.x, geoPoint3.y);
        } else if (geoPoint.y == geoPoint2.y) {
            geoPoint4 = new GeoPoint(geoPoint3.x, geoPoint.y);
        } else {
            float f = (((float) (geoPoint2.y - geoPoint.y)) * 1.0f) / ((float) (geoPoint.x - geoPoint2.x));
            float f2 = -1.0f / f;
            float f3 = ((((float) (geoPoint3.y - geoPoint.y)) + (((float) geoPoint.x) * f)) - (((float) geoPoint3.x) * f2)) / (f - f2);
            geoPoint4 = new GeoPoint((int) f3, (int) (((float) geoPoint3.y) + ((f3 - ((float) geoPoint3.x)) * f2)));
        }
        return cfe.a(geoPoint, geoPoint2) > cfe.a(geoPoint4, geoPoint2) ? geoPoint4 : geoPoint;
    }
}
