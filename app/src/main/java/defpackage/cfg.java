package defpackage;

import android.graphics.Point;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.map.DPoint;

/* renamed from: cfg reason: default package */
/* compiled from: VirtualEarthProjection */
public final class cfg {
    private static double a(double d, double d2, double d3) {
        return Math.min(Math.max(d, d2), d3);
    }

    public static Point a(double d, double d2) {
        Point point = new Point();
        double sin = Math.sin((a(d, -85.0511287798d, 85.0511287798d) * 3.141592653589793d) / 180.0d);
        point.x = (int) a((((((a(d2, -180.0d, 180.0d) * 3.141592653589793d) / 180.0d) * 6378137.0d) + 2.0037508342789244E7d) / 0.14929107086948487d) + 0.5d, 0.0d, 2.68435455E8d);
        point.y = (int) a(((2.0037508342789244E7d - (Math.log((sin + 1.0d) / (1.0d - sin)) * 3189068.0d)) / 0.14929107086948487d) + 0.5d, 0.0d, 2.68435455E8d);
        return point;
    }

    public static DPoint a(long j, long j2) {
        DPoint dPoint = new DPoint();
        dPoint.y = 1.5707963267948966d - (Math.atan(Math.exp((-(2.0037508342789244E7d - (((double) j2) * 0.14929107086948487d))) / 6378137.0d)) * 2.0d);
        dPoint.y *= 57.29577951308232d;
        dPoint.x = ((((double) j) * 0.14929107086948487d) - 2.0037508342789244E7d) / 6378137.0d;
        dPoint.x *= 57.29577951308232d;
        return dPoint;
    }

    public static GeoPoint b(long j, long j2) {
        GeoPoint geoPoint = new GeoPoint();
        geoPoint.x = (int) (j / 256);
        geoPoint.y = (int) (j2 / 256);
        return geoPoint;
    }

    public static GeoPoint a(bty bty, GeoPoint geoPoint, int i, int i2, float f, int i3) {
        if (bty == null || geoPoint == null) {
            return null;
        }
        float U = bty.U();
        double d = (double) (20.0f - f);
        int pow = (int) (((double) ((int) (((float) i) * U))) * Math.pow(2.0d, d));
        int pow2 = (int) (((double) ((int) (((float) i2) * U))) * Math.pow(2.0d, d));
        double radians = Math.toRadians((double) (-i3));
        double d2 = (double) pow2;
        return new GeoPoint(geoPoint.x + ((int) ((((double) pow) * Math.cos(radians)) + (Math.sin(radians) * d2))), geoPoint.y + ((int) ((((double) (-pow)) * Math.sin(radians)) + (d2 * Math.cos(radians)))));
    }
}
