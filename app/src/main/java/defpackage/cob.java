package defpackage;

import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.map.DPoint;

/* renamed from: cob reason: default package */
/* compiled from: BasicProjection */
public abstract class cob {
    private static double a(double d, double d2, double d3) {
        return Math.min(Math.max(d, d2), d3);
    }

    public static GeoPoint a(double d, double d2) {
        return a(null, d, d2, 20);
    }

    public static GeoPoint a(GeoPoint geoPoint, double d, double d2, int i) {
        if (geoPoint == null) {
            geoPoint = new GeoPoint();
        }
        DPoint a = a(d, d2, i);
        geoPoint.x = (int) a.x;
        geoPoint.y = (int) a.y;
        return geoPoint;
    }

    public static DPoint a(long j, long j2, int i) {
        DPoint dPoint = new DPoint();
        double d = 4.007501668557849E7d / ((double) ((1 << i) * 256));
        dPoint.y = 1.5707963267948966d - (Math.atan(Math.exp((-(2.0037508342789244E7d - (((double) j2) * d))) / 6378137.0d)) * 2.0d);
        dPoint.y *= 57.29577951308232d;
        dPoint.x = ((((double) j) * d) - 2.0037508342789244E7d) / 6378137.0d;
        dPoint.x *= 57.29577951308232d;
        return dPoint;
    }

    public static DPoint a(double d, double d2, int i) {
        DPoint dPoint = new DPoint();
        double sin = Math.sin((a(d, -85.0511287798d, 85.0511287798d) * 3.141592653589793d) / 180.0d);
        long j = 256 << i;
        double d3 = 4.007501668557849E7d / ((double) j);
        double d4 = (double) (j - 1);
        dPoint.x = a((((((a(d2, -180.0d, 180.0d) * 3.141592653589793d) / 180.0d) * 6378137.0d) + 2.0037508342789244E7d) / d3) + 0.5d, 0.0d, d4);
        dPoint.y = a(((2.0037508342789244E7d - (Math.log((sin + 1.0d) / (1.0d - sin)) * 3189068.0d)) / d3) + 0.5d, 0.0d, d4);
        return dPoint;
    }
}
